/*Populate tables*/
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Andres', 'Hernandez', 'andres@correo.com', '2022-08-29', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Angie', 'Rodriguez', 'angie@correo.com', '2022-08-29', '');


/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 1548923 , NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Camara Digital', 358999 , NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Apple iPod', 854998, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Notebook', 689998 , NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('HP portatil', 978998 , NOW());

/* Populate tabla facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Fact equipos', null, 1, NOW())
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 2);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);