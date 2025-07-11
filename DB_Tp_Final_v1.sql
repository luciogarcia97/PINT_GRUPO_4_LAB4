-- Script para Base de Datos Bancaria

CREATE DATABASE IF NOT EXISTS banco_db;
USE banco_db;

SET NAMES utf8mb4;
SET character_set_client = utf8mb4;

DROP TABLE IF EXISTS movimiento;
DROP TABLE IF EXISTS prestamo_cuota;
DROP TABLE IF EXISTS prestamo;
DROP TABLE IF EXISTS cuenta;
DROP TABLE IF EXISTS cuenta_tipo;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS telefono;
DROP TABLE IF EXISTS movimiento_tipo;
DROP TABLE IF EXISTS cliente;

-- Tabla cliente
CREATE TABLE cliente (
    id_cliente INT NOT NULL AUTO_INCREMENT,
    dni BIGINT NOT NULL,
    cuil VARCHAR(30) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    nacionalidad VARCHAR(80) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    localidad VARCHAR(50) NOT NULL,
    provincia VARCHAR(80) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_cliente_dni (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla telefono
CREATE TABLE telefono (
    id_telefono INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_telefono),
    KEY fk_telefono_cliente (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla usuario
CREATE TABLE usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NULL,
    usuario VARCHAR(50) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL,
    eliminado TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATE NOT NULL,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuario_usuario (usuario),
    KEY fk_usuario_cliente (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla cuenta_tipo
CREATE TABLE cuenta_tipo (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla cuenta
CREATE TABLE cuenta (
    id_cuenta INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    fecha_creacion DATE NOT NULL,
    id_tipo_cuenta INT NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    cbu VARCHAR(22) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 10000.00,
    activa TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id_cuenta),
    UNIQUE KEY uk_cuenta_numero (numero_cuenta),
    UNIQUE KEY uk_cuenta_cbu (cbu),
    KEY fk_cuenta_cliente (id_cliente),
    KEY fk_cuenta_tipo (id_tipo_cuenta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla prestamo
CREATE TABLE prestamo (
    id_prestamo INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_cuenta INT NOT NULL,
    fecha_solicitud DATE NOT NULL,
    fecha_aprobacion DATE NULL,
    importe_solicitado DECIMAL(15,2) NOT NULL,
    plazo_pago_meses INT NOT NULL,
    importe_por_cuota DECIMAL(15,2) NOT NULL,
    cantidad_cuotas INT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_prestamo),
    KEY fk_prestamo_cliente (id_cliente),
    KEY fk_prestamo_cuenta (id_cuenta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla prestamo_cuota
CREATE TABLE prestamo_cuota (
    id_prestamo_cuota INT NOT NULL AUTO_INCREMENT,
    id_prestamo INT NOT NULL,
    numero_cuota INT NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    fecha_pago DATE NULL,
    pagada TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_prestamo_cuota),
    KEY fk_cuota_prestamo (id_prestamo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla movimiento_tipo
CREATE TABLE movimiento_tipo (
    id_tipo_movimiento INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_tipo_movimiento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla movimiento
CREATE TABLE movimiento (
    id_movimiento INT NOT NULL AUTO_INCREMENT,
    id_cuenta INT NOT NULL,
    id_tipo_movimiento INT NOT NULL,
    fecha DATE NOT NULL,
    detalle VARCHAR(200) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    id_cuenta_destino INT NULL,
    id_prestamo INT NULL,
    PRIMARY KEY (id_movimiento),
    KEY fk_movimiento_cuenta (id_cuenta),
    KEY fk_movimiento_tipo (id_tipo_movimiento),
    KEY fk_movimiento_cuenta_destino (id_cuenta_destino),
    KEY fk_movimiento_prestamo (id_prestamo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE telefono 
    ADD CONSTRAINT fk_telefono_cliente 
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);

ALTER TABLE usuario 
    ADD CONSTRAINT fk_usuario_cliente 
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);

ALTER TABLE cuenta 
    ADD CONSTRAINT fk_cuenta_cliente 
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    ADD CONSTRAINT fk_cuenta_tipo 
    FOREIGN KEY (id_tipo_cuenta) REFERENCES cuenta_tipo(id);

ALTER TABLE prestamo 
    ADD CONSTRAINT fk_prestamo_cliente 
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    ADD CONSTRAINT fk_prestamo_cuenta 
    FOREIGN KEY (id_cuenta) REFERENCES cuenta(id_cuenta);

ALTER TABLE prestamo_cuota 
    ADD CONSTRAINT fk_cuota_prestamo 
    FOREIGN KEY (id_prestamo) REFERENCES prestamo(id_prestamo);

ALTER TABLE movimiento 
    ADD CONSTRAINT fk_movimiento_cuenta 
    FOREIGN KEY (id_cuenta) REFERENCES cuenta(id_cuenta),
    ADD CONSTRAINT fk_movimiento_tipo 
    FOREIGN KEY (id_tipo_movimiento) REFERENCES movimiento_tipo(id_tipo_movimiento),
    ADD CONSTRAINT fk_movimiento_cuenta_destino 
    FOREIGN KEY (id_cuenta_destino) REFERENCES cuenta(id_cuenta),
    ADD CONSTRAINT fk_movimiento_prestamo 
    FOREIGN KEY (id_prestamo) REFERENCES prestamo(id_prestamo);

-- Datos de prueba
INSERT INTO cuenta_tipo (nombre) VALUES 
('Cuenta Corriente'),
('Caja de Ahorro');

INSERT INTO movimiento_tipo (descripcion) VALUES 
('Alta de cuenta'),
('Alta de prestamo'),
('Pago de prestamo'),
('Transferencia'),
('Depósito'),
('Extracción'),
('Débito automático'),
('Acreditación de sueldo');

-- CORRECCIONES 
/* CREACIÓN DE TABLAS PROVINCIA Y LOCALIDADES*/
use `banco_db`;
create table transferencia (
id_transferencia int auto_increment primary key,
id_tipo_movimiento int,
cuenta_origen int ,
cuenta_destino int,
foreign key (id_tipo_movimiento) references movimiento_tipo(id_tipo_movimiento)
);
Create table provincias (
id_provincia int AUTO_INCREMENT PRIMARY KEY,
nombre_provincia varchar(100) not null unique
);

CREATE TABLE localidades (
    id_localidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_localidad VARCHAR(100) NOT NULL,
    id_provincia INT NOT NULL,
    FOREIGN KEY (id_provincia) REFERENCES provincias(id_provincia)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
/*INSERT DE DATOS*/
INSERT INTO provincias (nombre_provincia) VALUES
('Buenos Aires'),
('Córdoba'),
('Santa Fe'),
('Mendoza'),
('Salta');

-- Localidades de Buenos Aires (id_provincia = 1)
INSERT INTO localidades (nombre_localidad, id_provincia) VALUES
('La Plata', 1),
('Mar del Plata', 1),
('Bahía Blanca', 1),
('Quilmes', 1);

-- Localidades de Córdoba (id_provincia = 2)
INSERT INTO localidades (nombre_localidad, id_provincia) VALUES
('Córdoba Capital', 2),
('Villa Carlos Paz', 2),
('Río Cuarto', 2);

-- Localidades de Santa Fe (id_provincia = 3)
INSERT INTO localidades (nombre_localidad, id_provincia) VALUES
('Rosario', 3),
('Santa Fe Capital', 3),
('Venado Tuerto', 3);

-- Localidades de Mendoza (id_provincia = 4)
INSERT INTO localidades (nombre_localidad, id_provincia) VALUES
('Mendoza Capital', 4),
('San Rafael', 4),
('Godoy Cruz', 4);

-- Localidades de Salta (id_provincia = 5)
INSERT INTO localidades (nombre_localidad, id_provincia) VALUES
('Salta Capital', 5),
('Orán', 5),
('Tartagal', 5);

-- ELIMINO COLUMNA ID_CUENTA_DESTINO 
alter table movimiento
drop foreign key  `fk_movimiento_cuenta_destino` ;


ALTER TABLE movimiento
DROP COLUMN id_cuenta_destino;

--sp pago cuota prestamo
DELIMITER //

CREATE PROCEDURE sp_pagar_cuota(
    IN p_id_cuota INT,
    IN p_id_cuenta INT,
    IN p_monto DECIMAL(10,2)
)
BEGIN
    DECLARE v_saldo_actual DECIMAL(10,2);
    DECLARE v_cuota_pendiente DECIMAL(10,2);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    -- Iniciar transacción
    START TRANSACTION;
    
    -- Obtener saldo actual de la cuenta
    SELECT saldo INTO v_saldo_actual 
    FROM cuenta 
    WHERE id_cuenta = p_id_cuenta;
    
    -- Obtener monto de la cuota
    SELECT monto INTO v_cuota_pendiente 
    FROM prestamo_cuota 
    WHERE id_prestamo_cuota = p_id_cuota AND pagada = 0;
    
    -- Verificar si hay suficiente saldo
    IF v_saldo_actual >= p_monto THEN
        -- Actualizar saldo de la cuenta
        UPDATE cuenta 
        SET saldo = saldo - p_monto 
        WHERE id_cuenta = p_id_cuenta;
        
        -- Marcar cuota como pagada
        UPDATE prestamo_cuota 
        SET pagada = 1, 
            fecha_pago = curdate() 
        WHERE id_prestamo_cuota = p_id_cuota;
        
        COMMIT;
        SELECT 'Pago realizado exitosamente' AS mensaje;
    ELSE
        ROLLBACK;
        SELECT 'Saldo insuficiente' AS mensaje;
    END IF;
    
END //

-- Restaurar el delimitador original
DELIMITER ;


--procedimiento almacenado elimina cliente, usuario y cuentas
DELIMITER $$
CREATE PROCEDURE eliminar_usuario_completo ( IN p_idUsuario INT, IN p_idCliente INT )
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;
    START TRANSACTION;    
    UPDATE usuario SET eliminado = 1 WHERE id_usuario = p_idUsuario;
   
    UPDATE cliente  SET eliminado = 1 WHERE id_cliente = p_idCliente;
   
    UPDATE cuenta SET activa = 0 WHERE id_cliente = p_idCliente;
    
    COMMIT;
END$$
DELIMITER ;


-- procedimiento almacenado valida cliente activo antes de aceptar el prestamo 
DELIMITER $$
CREATE PROCEDURE aceptar_prestamo_valida_cliente_activo (
    IN idPrestamo INT, 
    OUT resultado INT
)
salida: BEGIN
    DECLARE eliminado_cliente TINYINT DEFAULT NULL;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET resultado = -1;
    END;

    START TRANSACTION;
   
    SELECT c.eliminado INTO eliminado_cliente FROM prestamo p
    JOIN cliente c ON p.id_cliente = c.id_cliente WHERE p.id_prestamo = idPrestamo;
    
    IF eliminado_cliente IS NULL OR eliminado_cliente = 1 THEN
        ROLLBACK;
        SET resultado = 0;
        LEAVE salida;
    END IF;
  
    UPDATE prestamo SET estado='aceptado',fecha_aprobacion=DATE(CURRENT_TIMESTAMP()) WHERE id_prestamo = idPrestamo;

    COMMIT;
    SET resultado = 1;
END salida$$
DELIMITER ;


-- CORRECCIONES EN TABLA Y CONSTRAIN FK
ALTER TABLE transferencia 
CHANGE COLUMN id_tipo_movimiento id_movimiento INT(11);

ALTER TABLE transferencia 
ADD CONSTRAINT fk_transferencia_movimiento 
FOREIGN KEY (id_movimiento) REFERENCES movimiento(id_movimiento);

--SP HISTORIAL DE TRANSFERENCIAS
DELIMITER $
CREATE PROCEDURE sp_historial_transferencias_cliente(
    IN p_id_cliente INT
)
BEGIN
    SELECT 
        t.id_transferencia,
        t.id_movimiento,
        t.cuenta_origen,
        t.cuenta_destino,
        m.fecha,
        m.detalle,
        m.importe,
        co.numero_cuenta as num_cuenta_origen,
        cd.numero_cuenta as num_cuenta_destino,
        CONCAT(co.numero_cuenta, ' → ', cd.numero_cuenta) as transferencia_detalle,
        mt.descripcion as tipo_movimiento,
        CONCAT(cli.nombre, ' ', cli.apellido) as cliente_nombre
    FROM movimiento m
    INNER JOIN cuenta co ON m.id_cuenta = co.id_cuenta
    INNER JOIN cliente cli ON co.id_cliente = cli.id_cliente
    INNER JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento
    INNER JOIN transferencia t ON m.id_movimiento = t.id_movimiento
    LEFT JOIN cuenta cd ON t.cuenta_destino = cd.id_cuenta
    WHERE co.id_cliente = p_id_cliente
    AND t.cuenta_origen = co.id_cuenta
    ORDER BY m.fecha DESC;
END$
DELIMITER ;





