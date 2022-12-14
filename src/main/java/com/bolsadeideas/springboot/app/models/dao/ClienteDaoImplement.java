package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImplement implements IClienteDao {
	
	@PersistenceContext
	private EntityManager em;

	
	@Override
	public List<Cliente> finAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	
	@Override
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);			
		}
	}

	
	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}

	
	@Override
	public void delete(Long id) {
		Cliente cliente = findOne(id);
		em.remove(cliente);
	}

}
