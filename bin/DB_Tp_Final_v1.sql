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
('Caja de Ahorro'),
('Cuenta de Inversión');

INSERT INTO movimiento_tipo (descripcion) VALUES 
('Alta de cuenta'),
('Alta de prestamo'),
('Pago de prestamo'),
('Transferencia'),
('Depósito'),
('Extracción'),
('Débito automático'),
('Acreditación de sueldo');
