-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-07-2021 a las 15:31:18
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fruver`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `borrarProducto` (IN `producto` VARCHAR(30))  BEGIN
DECLARE exit handler for SQLEXCEPTION
 BEGIN
  GET DIAGNOSTICS CONDITION 1 @errno = MYSQL_ERRNO;
  SELECT @errno;
 END;

DELETE FROM producto WHERE nombre like producto;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarEmpleado` (`iddd` INT)  BEGIN
SELECT id INTO @idd FROM empleado WHERE id = iddd;
IF @idd is NOT NULL THEN
SELECT * FROM empleado WHERE id = iddd;
ELSE
SELECT 0,0,0,0,0;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editarProducto` (IN `nom` VARCHAR(50), IN `med` VARCHAR(50), IN `prec` INT)  BEGIN
SELECT @codigo := codigo from producto WHERE nombre LIKE nom;
SELECT @codMedida := codigo from unidadespeso where medida LIKE med;
UPDATE producto SET codigo = @codigo, nombre = nom, unidadMedida = @codMedida, precio = prec WHERE codigo = @codigo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registrarEmpleado` (IN `idd` INT, IN `nom` VARCHAR(30), IN `pass` VARCHAR(30))  BEGIN
SELECT @idd := id FROM empleado WHERE id = idd;
IF @idd IS null THEN
INSERT INTO empleado VALUE(idd, nom, NOW(), 1, pass);
SELECT 1;
ELSE
SELECT 0;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registrarProducto` (IN `nom` VARCHAR(20), IN `med` VARCHAR(20), IN `precio` INT(20))  BEGIN  
SELECT @idd := max(codigo) FROM producto;
IF @idd IS null THEN
SET @idd := 0;
ELSE
SET @idd := @idd + 1;
END IF;
SELECT @codMedida :=  codigo from unidadespeso where medida LIKE med;
INSERT INTO producto value(@idd, nom, @codMedida, precio);
END$$

--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `registrarFactura` (`idEmpleado` INT) RETURNS INT(11) BEGIN
SELECT max(codigo) INTO @cod FROM factura;
IF @cod IS null THEN
SET @cod = 1;
INSERT INTO factura VALUE(@cod, NOW(), idEmpleado);
ELSE
SET @cod = 1 + @cod;
INSERT INTO factura VALUE((@cod), NOW(), idEmpleado);
END IF;
RETURN @cod;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `registroEmpleado` (`idd` INT, `nom` VARCHAR(30), `pass` VARCHAR(30)) RETURNS INT(11) BEGIN
SELECT id INTO @idd FROM empleado WHERE id = idd;
IF @idd IS null THEN
INSERT INTO empleado VALUE(idd, nom, NOW(), 1, pass);
END IF;
RETURN @idd;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

CREATE TABLE `cargo` (
  `codigo` int(11) NOT NULL,
  `etiqueta` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`codigo`, `etiqueta`) VALUES
(1, 'Cajero'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `fechaIngreso` date DEFAULT NULL,
  `cargo` int(11) NOT NULL,
  `contrasena` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id`, `nombre`, `fechaIngreso`, `cargo`, `contrasena`) VALUES
(1, 'janino', '2020-11-01', 2, '1'),
(2, 'Cajero', '2020-12-28', 1, '2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `codigo` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `idEmpleado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`codigo`, `fecha`, `idEmpleado`) VALUES
(1, '2020-12-22', 1),
(2, '2021-01-04', 1),
(3, '2021-02-02', 1),
(4, '2021-02-02', 1),
(5, '2021-02-02', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturaproducto`
--

CREATE TABLE `facturaproducto` (
  `idProducto` int(11) NOT NULL,
  `idFactura` int(11) NOT NULL,
  `peso` decimal(10,2) NOT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidadMedidaVenta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `facturaproducto`
--

INSERT INTO `facturaproducto` (`idProducto`, `idFactura`, `peso`, `precio`, `unidadMedidaVenta`) VALUES
(1, 1, '1400.00', '840.00', 1),
(0, 1, '5.00', '3000.00', 2),
(2, 2, '1000.00', '300.00', 1),
(16, 3, '3000.00', '4800.00', 1),
(9, 3, '3.00', '4500.00', 4),
(11, 3, '900.00', '360.00', 1),
(10, 3, '7000.00', '8400.00', 1),
(18, 3, '800.00', '2400.00', 1),
(19, 3, '500.00', '1500.00', 1),
(15, 3, '300.00', '241.80', 1),
(7, 3, '10.00', '8000.00', 4),
(6, 4, '3222.00', '2577.60', 1),
(18, 4, '4.00', '12.00', 1),
(10, 4, '5.00', '6.00', 1),
(16, 4, '5000.00', '8000.00', 1),
(12, 5, '400.00', '720.00', 1),
(18, 5, '500.00', '1500.00', 1),
(14, 5, '500.00', '300.00', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `unidadMedida` int(11) NOT NULL,
  `precio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codigo`, `nombre`, `unidadMedida`, `precio`) VALUES
(0, 'Papa', 2, 600),
(1, 'Zanahoria', 2, 300),
(2, 'Kiwi', 3, 300),
(3, 'Pera', 2, 500),
(4, 'Manzana agria', 2, 300),
(5, 'Aguacate', 4, 400),
(6, 'Ajo', 2, 400),
(7, 'Banano', 4, 800),
(8, 'Berenjena', 4, 500),
(9, 'Calabaza', 4, 1500),
(10, 'Carambolo', 2, 600),
(11, 'Cebolla', 3, 400),
(12, 'Cereza', 2, 900),
(13, 'Chile', 3, 400),
(14, 'Ciruela', 2, 300),
(15, 'Durazno', 2, 403),
(16, 'Fresa', 2, 800),
(17, 'Lulo', 2, 409),
(18, 'Mango', 3, 3000),
(19, 'Limon', 3, 3000),
(20, 'Papaya', 2, 400);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidadespeso`
--

CREATE TABLE `unidadespeso` (
  `codigo` int(11) NOT NULL,
  `medida` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `unidadespeso`
--

INSERT INTO `unidadespeso` (`codigo`, `medida`) VALUES
(1, 'Gramo(g)'),
(2, 'Libra(Lb)'),
(3, 'Kilogramo(Kg)'),
(4, 'Unidad');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKCargoEmpleado` (`cargo`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKEmpleadoFactura` (`idEmpleado`);

--
-- Indices de la tabla `facturaproducto`
--
ALTER TABLE `facturaproducto`
  ADD KEY `FKFacturaProducto` (`idProducto`),
  ADD KEY `FKProductoFactura` (`idFactura`),
  ADD KEY `unidadesPeso_facturaProducto` (`unidadMedidaVenta`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKMedidaProducto` (`unidadMedida`);

--
-- Indices de la tabla `unidadespeso`
--
ALTER TABLE `unidadespeso`
  ADD PRIMARY KEY (`codigo`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `FKCargoEmpleado` FOREIGN KEY (`cargo`) REFERENCES `cargo` (`codigo`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `FKEmpleadoFactura` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`id`);

--
-- Filtros para la tabla `facturaproducto`
--
ALTER TABLE `facturaproducto`
  ADD CONSTRAINT `FKFacturaProducto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`codigo`),
  ADD CONSTRAINT `FKProductoFactura` FOREIGN KEY (`idFactura`) REFERENCES `factura` (`codigo`),
  ADD CONSTRAINT `unidadesPeso_facturaProducto` FOREIGN KEY (`unidadMedidaVenta`) REFERENCES `unidadespeso` (`codigo`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FKMedidaProducto` FOREIGN KEY (`unidadMedida`) REFERENCES `unidadespeso` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
