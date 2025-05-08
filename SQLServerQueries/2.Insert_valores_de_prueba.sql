--2: Inserts:
INSERT INTO Roles(rol) VALUES ('ROLE_ADMIN');
INSERT INTO Roles(rol) VALUES ('ROLE_USER');
INSERT INTO Productos(nombre,precio,tags,fecha_de_creacion,ultima_actualizacion,activo) VALUES ('test product',10.99,'sample,tag',getutcdate(),getutcdate(),1);
INSERT INTO Categorias(nombre,fecha_de_creacion,ultima_actualizacion,activo) VALUES ('test category',getutcdate(),getutcdate(),1);
INSERT INTO Categorias(nombre,fecha_de_creacion,ultima_actualizacion,activo) VALUES ('test category 2',getutcdate(),getutcdate(),1);
INSERT INTO Productos_categorias(producto_id,categoria_id) VALUES (1,1);