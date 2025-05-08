GO
CREATE DATABASE Test
GO
GO
USE Test
GO
CREATE TABLE Roles (
    rol_ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    rol NVARCHAR(255) NOT NULL
);
CREATE TABLE Usuarios (
    usuario_ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre_completo NVARCHAR(255) NOT NULL,
    username NVARCHAR(50) NOT NULL,
    clave NVARCHAR(255) NOT NULL,
    fecha_de_creacion DATETIME2,
    ultimo_acceso DATETIME2,
    activo BIT NOT NULL
);
CREATE TABLE Usuario_Roles (
    usuario_ID BIGINT NOT NULL,
    rol_ID BIGINT NOT NULL,
    PRIMARY KEY (usuario_ID, rol_ID),
    FOREIGN KEY (usuario_ID) REFERENCES Usuarios(usuario_ID) ON DELETE CASCADE,
    FOREIGN KEY (rol_ID) REFERENCES Roles(rol_ID) ON DELETE CASCADE
);
CREATE TABLE Categorias (
    categoria_ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    fecha_de_creacion DATETIME2,
    ultima_actualizacion DATETIME2,
    activo BIT NOT NULL
);
CREATE TABLE Productos (
    producto_ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    tags TEXT,
    fecha_de_creacion DATETIME2,
    ultima_actualizacion DATETIME2,
    activo BIT NOT NULL
);
CREATE TABLE Productos_Categorias (
    producto_ID BIGINT NOT NULL,
    categoria_ID BIGINT NOT NULL,
    PRIMARY KEY (producto_ID, categoria_ID),
    FOREIGN KEY (producto_ID) REFERENCES Productos(producto_ID) ON DELETE CASCADE,
    FOREIGN KEY (categoria_ID) REFERENCES Categorias(categoria_ID) ON DELETE CASCADE
);