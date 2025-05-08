--3: IMPORTANTE: ejecute el trigger luego de que existan roles
--Trigger para asignar usuarios a rol por defecto
CREATE TRIGGER trg_insert_usuario_asigna_rol
ON usuarios
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @rol_id INT;
    SELECT TOP 1 @rol_id = rol_id FROM roles WHERE rol = 'ROLE_USER';
    IF @rol_id IS NOT NULL
    BEGIN
        INSERT INTO usuario_roles (usuario_id, rol_id)
        SELECT usuario_id, @rol_id
        FROM inserted;
    END
    ELSE
    BEGIN
        RAISERROR('El rol "ROL_USER" no existe en la tabla roles.', 16, 1);
    END
END;