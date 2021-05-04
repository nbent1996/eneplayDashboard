use alfacomPlatform;
drop database alfacomPlatform;
create database alfacomPlatform;
update Suscripciones set eliminado = 'Z' WHERE idSuscripcion not in(1,2,3)

UPDATE tiposusuarios SET nombre='MASTER1' WHERE TiposUsuarios.nombre='MASTER' and not exists (SELECT * FROM tiposusuarios where nombre='MASTER1')
insert into TiposUsuarios (nombre) values ('MASTER1') 
select * from TiposUsuarios where nombre='MASTER'

use alfacomPlatform;
select * from Facturas

show variables like 'char%'
select * from Categorias
select QuerysEjecutadas.idLog, QuerysEjecutadas.textoQuery from LogsSistema, QuerysEjecutadas WHERE LogsSistema.idLog = QuerysEjecutadas.idLog;
select * from Dispositivos WHERE eliminado='N';
select * from TiposDispositivos;
select * from Empresas where identificacionTributaria in('902.237.970-14','526283747346','189.68045.54-8','246299533496');
select * from LogsSistema;
select * from Empresas;
select * from QuerysEjecutadas;
select * from Facturas;
select * from Personas where identificacionTributaria in('902.237.970-14','526283747346','189.68045.54-8','246299533496');
select * from Personas, Clientes where Personas.usuarioSistema = Clientes.usuarioSistema and nroCliente between 0 and 8;
select * from Paises;
select count(*) from Dispositivos where identificacionTributaria='526283747346';
select * from Paquetes;
select * from Suscripciones;
select * from Principales;
select * from TiposUsuarios;

select count(*) from Dispositivos;
select * from TiposDispositivos;
select * from Secundarios;
select * from Clientes;
select * from TiposDocumentos order by indice asc;
select * from Monedas;
select * from OperadoresDashboard;
select * from Imagenes;

select * from Personas, Clientes WHERE Personas.usuarioSistema = Clientes.usuarioSistema AND Personas.identificacionTributaria='526283747346' AND Personas.usuarioSistema!='loginUser'; /*Clientes totales de una empresa determinada*/
select count(*) from Personas, Secundarios WHERE Personas.usuarioSistema = Secundarios.usuarioSistema AND Personas.identificacionTributaria='526283747346' AND Personas.usuarioSistema!='loginUser'; /*Cuentas secundarias de una empresa determinada*/
select count(*) from Dispositivos WHERE Dispositivos.identificacionTributaria='526283747346';/*Dispositivos registrados por la empresa*/
select count(*) from Suscripciones, Principales, Personas WHERE Suscripciones.nroDocumentoTitular = Principales.nroDocumento AND Principales.usuarioSistema = Personas.usuarioSistema AND Suscripciones.activa='S' AND Personas.identificacionTributaria='526283747346' AND Personas.usuarioSistema!='loginUser'; /*Suscripciones activas de una empresa determinada*/

 INSERT INTO Personas (usuarioSistema, nombreCompleto, codigo, identificacionTributaria) values ('dmKvRTFcZ8','Alejandra Bentancor','URU','526283747346')
 INSERT INTO Clientes (email, usuarioSistema, telefono) values ('bentancorALE@ort.edu.uy','dmKvRTFcZ8', '+59897803629')
 INSERT INTO Secundarios (nroCliente, nroDocumento) values (?,'14675501')
UPDATE Personas, Clientes, Principales SET Personas.eliminado='Y', Clientes.eliminado='Y', Principales.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Principales.usuarioSistema AND Personas.usuarioSistema='i8ALUqwkhR'
UPDATE Personas, Clientes, Secundarios SET Personas.eliminado='Y', Clientes.eliminado='Y', Secundarios.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Secundarios.usuarioSistema AND Personas.usuarioSistema='i8ALUqwkhR'

Select * from Clientes, Principales WHERE Clientes.nroCliente = Principales.nroCliente and Principales.nroCliente in (1,2,3,4,5,6,7) order by Clientes.nroCliente asc
SELECT nombrePrivilegio from TieneTUP WHERE nombreTipoUsuario='gerente' AND eliminado='N' 
SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE fechaPago='1970-1-1' and fechaVencimiento='2020-10-25' and periodoServicioInicio='2020-9-1' and periodoServicioFin='2020-9-30' and monto = '300' and tipoRecibo = 'Periodica' and nroCliente='4' and identificacionTributaria = '526283747346'  AND eliminado='N' 
SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE  fechaPago='2020-10-3' and fechaVencimiento='2020-10-25' and periodoServicioInicio='2020-9-1' and periodoServicioFin='2020-9-30' and monto = '300' and tipoRecibo = 'Periodica' and nroCliente='4' and identificacionTributaria = '526283747346'  AND eliminado='N'
SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE idFactura=5  AND eliminado='N';

SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE fechaPago='1970-1-1' and fechaVencimiento='2020-10-25' and periodoServicioInicio='2020-9-1' and periodoServicioFin='2020-9-30' and monto = '300.0' and tipoRecibo = 'Periodica' and nroCliente='4' and identificacionTributaria = '526283747346'  AND eliminado='N' 
SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE fechaPago='2020-10-3' and fechaVencimiento='2020-10-25' and periodoServicioInicio='2020-9-1' and periodoServicioFin='2020-9-30' and monto = '300.0' and tipoRecibo = 'Periodica' and nroCliente='4' and identificacionTributaria = '526283747346'  AND eliminado='N' 
show grants;
insert into Categorias(null);
0	51	20:53:43	insert into Categorias(null)	Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'null)' at line 1	0.000 sec

SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago', day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision',  day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento',  day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI',  day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF',  monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas  WHERE fechaPago='1970-1-1' and fechaVencimiento='2020-10-25' and periodoServicioInicio='2020-9-1' and periodoServicioFin='2020-9-30' and monto = '300' and tipoRecibo = 'Periodica' and nroCliente='4' and identificacionTributaria = '526283747346'  AND eliminado='N' 

use alfacomPlatform;
select * from Clientes;
select * from TiposDocumentos;
use sendasUruguay;
select * from guiamigracion;

UPDATE Personas, OperadoresDashboard SET Personas.usuarioSistema='hernandez_na'Personas.nombreCompleto='Nicolás Alejandro Hernandez' , OperadoresDashboard.usuarioSistema='hernandez_na', OperadoresDashboard.clave = SHA('CLAVE NUEVA'), OperadoresDashboard.nombre='administrador'  WHERE Personas.usuarioSistema = OperadoresDashboard.usuarioSistema AND Personas.usuarioSistema='hernandez_na'


SELECT day(fechaInicio) as 'diaInicio', month(fechaInicio) as 'mesInicio', year(fechaInicio) as 'anioInicio',  tiempoContrato, activa, idSuscripcion,  day(fechaFin) as 'diaFin', month(fechaFin) as 'mesFin', year(fechaFin) as 'anioFin' from Suscripciones  WHERE fechaInicio='2020-11-15' AND tiempoContrato='0' AND fechaFin='2020-11-27' AND activa = 'N'  AND Suscripciones.eliminado='N' 


show full processlist;
kill 172;
SELECT dis.nroSerie, dis.estado, dis.idTipoDispositivo, dis.identificacionTributaria, pri.nroCliente, per.nombreCompleto 
from Principales pri RIGHT JOIN Dispositivos dis ON pri.nroCliente = dis.nroCliente LEFT JOIN Personas per ON pri.usuarioSistema = per.usuarioSistema

SELECT count(*)
from Principales pri RIGHT JOIN Dispositivos dis ON pri.nroCliente = dis.nroCliente LEFT JOIN Personas per ON pri.usuarioSistema = per.usuarioSistema

select * from Principales, Clientes where Principales.nroCliente = Clientes.nroCliente;

