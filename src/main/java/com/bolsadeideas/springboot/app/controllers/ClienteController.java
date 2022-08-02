package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value = "id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		 Cliente cliente = clienteService.fetchByIdWithFacturas(id); //clienteService.findOne(id);
		 if(cliente == null) {
			 flash.addFlashAttribute("error", "El cliente no existe");
			 return "redirect:/listar";
		 }
		 model.put("cliente", cliente);
		 model.put("titulo", "Detalle cliente: " +cliente.getNombre() + ' ' + cliente.getApellido());
		return "ver";
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String Listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.finAll());
		
		return "listar";
	}
	 
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario crear cliente");
		model.put("boton", "Crear");
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto ,RedirectAttributes flash ,SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario crear cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) {
			
			String rootPath = "C://Temp//uploads";//Se saca el root para guardar archivos en esa carpeta
			try {
				byte[] bytes = foto.getBytes();//Se pregunta por el tamaño del archivo en este caso la foto
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());//Se crea la ruta completa
				Files.write(rutaCompleta, bytes);//Y se escribe el archivo en la carpeta
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado" : "Cliente creado";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id,Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El id del cliente no existe!");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del cliente no existe!");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar usuario");
		model.put("boton", "Editar");
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			clienteService.delete(id);
			flash.addFlashAttribute("warning", "Cliente eliminado exitosamente!");
		}
		return "redirect:/listar";
	}
}
