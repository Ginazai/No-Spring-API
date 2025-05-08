--4: LUEGO de registrarse, ejecutar este query para conferir permisos de admin:
BEGIN
    DECLARE @usuario BIGINT
    DECLARE @rol BIGINT
    SELECT @usuario = usuario_ID FROM usuarios WHERE username = 'admin'
    SELECT @rol = rol_id FROM roles WHERE rol = 'ROLE_ADMIN'
    INSERT INTO usuario_roles(usuario_id,rol_id)
    VALUES (@usuario,@rol)
END