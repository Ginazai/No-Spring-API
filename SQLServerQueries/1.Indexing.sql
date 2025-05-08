-- 1: nombre de usuario unico / indexing por usos constantes
ALTER TABLE Usuarios ADD CONSTRAINT uq_username UNIQUE(username); 
CREATE INDEX idx_usuario_nombre ON Usuarios(username); 
CREATE INDEX idx_categoria_nombre ON Categorias(nombre);