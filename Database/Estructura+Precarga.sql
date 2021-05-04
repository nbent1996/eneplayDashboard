drop database if exists alfacomPlatform;
create database if not exists alfacomPlatform
 DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
use alfacomPlatform;
/*SET SQL_SAFE_UPDATES=0;
SET GLOBAL MAX_CONNECTIONS = 1500;*/
/*Entidades*/
create table Imagenes(
	idImagen int primary key auto_increment,
    imagen mediumblob not null
);
create table Categorias( /*OK*/
	nombreCategoria varchar(40) primary key,
	eliminado varchar(1) not null default 'N'
);
create table Paises( /*OK*/
	codigo varchar(4) primary key not null,
    nombre varchar(60) not null,
    indice int unique auto_increment,
	eliminado varchar(1) not null default 'N'
);
create table Idiomas( /*OK*/
    nombreIdioma varchar(40) primary key not null,
    eliminado varchar(1) not null default 'N'
);
create table Traducciones( /*OK*/
	idTraduccion int primary key auto_increment,
    plataforma enum('Dashboard','Mobile') not null,
    idItemGUI varchar(90) not null,
    texto varchar(150) not null,
    textoSecundario varchar(150),
	nombreIdioma varchar(40) not null,
    eliminado varchar(1) not null default 'N',
	Foreign key (nombreIdioma) references Idiomas(nombreIdioma)
);
create table Empresas( /*OK*/
	identificacionTributaria varchar(20) primary key not null,
    nombre varchar(50) not null,
    razonSocial varchar(30) not null,
    impuestos float not null,
    nombreIdioma varchar(40) not null,
    codigo varchar(4) not null,
    idImagen int,
    eliminado varchar(1) not null default 'N',
    Foreign key(nombreIdioma) references Idiomas(nombreIdioma),
    Foreign key(codigo) references Paises(codigo),
    Foreign key(idImagen) references Imagenes(IdImagen)
);

create table Personas( /*OK*/
	usuarioSistema varchar(35) primary key,
    nombreCompleto varchar(50) not null,
    codigo varchar(4) not null,
    identificacionTributaria varchar(20) not null,
    eliminado varchar(1) not null default 'N', 
    Foreign key (codigo) references Paises(codigo),
	Foreign key (identificacionTributaria) references Empresas(identificacionTributaria)
);



create table Clientes( /*OK*/
	nroCliente int primary key auto_increment,
	email varchar(45) unique not null,
    telefono varchar(50) unique not null,
    usuarioSistema varchar(40) not null,
    eliminado varchar(1) not null default 'N',
    Foreign key (usuarioSistema) references Personas(usuarioSistema)
);

create table TiposUsuarios( /*OK*/
	nombre varchar(30) primary key,
	eliminado varchar(1) not null default 'N'
);

create table OperadoresDashboard( /*OK*/
	usuarioSistema varchar(35),
    clave varchar(40) not null,
    nombre varchar(30) not null, 
	genero enum("Masculino", "Femenino") not null,
    eliminado varchar(1) not null default 'N',
    Foreign key (usuarioSistema) references Personas(usuarioSistema),
    Foreign key (nombre) references TiposUsuarios(nombre)
);
create table TiposDispositivos( /*OK*/
	idTipoDispositivo int primary key auto_increment,
    modelo varchar(15) unique not null,
    nombre varchar(90) not null,
    tipoComunicacion enum('Ethernet','Wifi','Ambos'),
	eliminado varchar(1) not null default 'N',
    nombreCategoria varchar(40) not null,
    Foreign key(nombreCategoria) references Categorias(nombreCategoria)
);
create table Dispositivos(/*OK*/
	nroSerie varchar(20) primary key not null,
    estado enum('Nuevo','Usado-OK','Usado-Reparar') default 'Nuevo',
    idTipoDispositivo int not null,
    identificacionTributaria varchar(20) not null,
    nroCliente int default null,
	eliminado varchar(1) not null default 'N',
    Foreign key (identificacionTributaria) references Empresas(identificacionTributaria),
    Foreign key (idTipoDispositivo) references TiposDispositivos(idTipoDispositivo),
    Foreign key (nroCliente) references Clientes(nroCliente)
);


create table Monedas( /*OK*/
	codigo varchar(4) primary key not null,
    nombreMoneda varchar(50) not null,
    simbolo varchar(4) not null,
    eliminado varchar(1) not null default 'N'
);

create table Facturas( /*OK*/
	idFactura int primary key auto_increment,
    fechaPago Date not null default '1970-01-01',
    fechaEmision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechaVencimiento Date not null default '1970-01-01', 
    periodoServicioInicio Date not null default '1970-01-01',
    periodoServicioFin Date not null default '1970-01-01',
    tipoRecibo enum('Periodica', 'A demanda') not null,
    monto float not null,
    codigo varchar(4) not null,
    nroCliente int not null,
    identificacionTributaria varchar(20) not null,
    eliminado varchar(1) not null default 'N',
    Foreign key(codigo) references Monedas(codigo),
    Foreign key(nroCliente) references Clientes(nroCliente),
    Foreign key(identificacionTributaria) references Empresas(identificacionTributaria)
);

create table LogsSistema( /*OK*/
	idLog int primary key auto_increment,
    operacion enum('Búsqueda', 'Alta','Baja','Modificación','Notificación','Login','Logout','InicioApp') not null,
    usuarioSistema varchar(35) not null, 
    textoError varchar(300),
    fechaHora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table QuerysEjecutadas(
	idLog int not null,
    textoQuery varchar(1500),
    Foreign key(idLog) references LogsSistema(idLog)
);

create table Paquetes( /*OK*/
	idPaquete int primary key auto_increment,
    nombrePaquete varchar(50) not null,
    costoBruto float not null,
	identificacionTributaria varchar(20) not null,
    eliminado varchar(1) not null default 'N',
	Foreign key(identificacionTributaria) references Empresas(identificacionTributaria)
);

create table TiposDocumentos(/*OK*/
	codDocumento varchar(10) primary key,
    indice int auto_increment unique, 
    nombreDocumento varchar(50) not null,
	eliminado varchar(1) not null default 'N'
);

create table Principales( /*OK*/
	nroDocumento varchar(15) primary key,
    servicioActivo enum ('S','N') default 'S',
    usuarioSistema varchar(35) not null,
    nroCliente int not null,
    codDocumento varchar(10) not null,
	eliminado varchar(1) not null default 'N',
    Foreign key(codDocumento) references TiposDocumentos(codDocumento),
    Foreign key(usuarioSistema) references Personas(usuarioSistema),
    Foreign key(nroCliente) references Clientes(nroCliente)
);
create table Privilegios( /*OK*/
    nombrePrivilegio varchar(100) primary key,
	eliminado varchar(1) not null default 'N'
);
create table Secundarios( /*OK*/
	usuarioSistema varchar(35) not null,
	nroCliente int not null,
    nroDocumento varchar(15) not null,
	eliminado varchar(1) not null default 'N',
    Foreign key(usuarioSistema) references Personas(usuarioSistema),
    Foreign key(nroCliente) references Clientes(nroCliente),
    Foreign key(nroDocumento) references Principales(nroDocumento)
);
create table Suscripciones( /*OK*/
	idSuscripcion int primary key auto_increment,
    fechaInicio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tiempoContrato float not null,
    fechaFin Date not null default '1970-01-01',
    activa varchar(1) not null default 'S',
    nroDocumentoTitular varchar(15) not null,
    identificacionTributaria varchar(20) not null,
	eliminado varchar(1) not null default 'N',
    Foreign key(nroDocumentoTitular) references Principales(nroDocumento),
	Foreign key(identificacionTributaria) references Empresas(identificacionTributaria)
);


/*Relaciones*/

/*Suscripciones, Facturas*/
create table GeneraSF(
	idSuscripcion int default null,
    idFactura int not null,
    eliminado varchar(1) not null default 'N',
	Foreign key(idSuscripcion) references Suscripciones(idSuscripcion),
    Foreign key(idFactura) references Facturas(idFactura)
);

/*Suscripciones, Paquetes*/
create table PoseeSP(
	idSuscripcion int,
    idPaquete int not null,
	eliminado varchar(1) not null default 'N',
	Foreign key (idPaquete) references Paquetes(idPaquete),
    Foreign key (idSuscripcion) references Suscripciones(idSuscripcion)
);

/*Paquetes, TiposDispositivos*/
create table TieneTP(
	idPaquete int not null,
    idTipoDispositivo int not null,
    cantidadDispositivos int not null default 1,
	eliminado varchar(1) not null default 'N',
	Foreign key(idPaquete) references Paquetes(idPaquete),
    Foreign key(idTipoDispositivo) references TiposDispositivos(idTipoDispositivo) 
);

/*TiposUsuarios, Privilegios*/
create table TieneTUP(
	nombreTipoUsuario varchar(30) not null,
    nombrePrivilegio varchar(100) not null,
	eliminado varchar(1) not null default 'N',
	Foreign key(nombreTipoUsuario) references TiposUsuarios(nombre),
    Foreign key(nombrePrivilegio) references Privilegios(nombrePrivilegio)
);

/*Precarga definitiva*/
insert into Paises(codigo, nombre, eliminado) values
('URU','Uruguay','N'),
('ARG','Argentina','N'),
('BRA','Brasil','N'),
('AFG','Afganistán','Y'),
('ALB','Albania','Y'),
('ALM','Alemania','Y'),
('AND','Andorra','Y'),
('ANG','Angola','Y'),
('ANB','Antigua y Barbuda','Y'),
('ARA','Arabia Saudita','Y'),
('ARGE','Argelia','Y'),
('ARM','Armenia','Y'),
('AUS','Australia','Y'),
('AUST','Austria','Y'),
('AZE','Azerbaiyán','Y'),
('BAH','Bahamas','Y'),
('BANG','Bangladés','Y'),
('BAR','Barbados','Y'),
('BARE','Baréin','Y'),
('BEL','Bélgica','Y'),
('BELI','Belice','Y'),
('BENI','Benín','Y'),
('BIEL','Bielorrusia','Y'),
('BIR','Birmania','Y'),
('BOL','Bolivia','N'),
('BOS','Bosnia y Herzegovina','Y'),
('BOT','Botsuana','Y'),
('BRU','Brunéi','Y'),
('BUL','Bulgaria','Y'),
('BUR','Burkina Faso','Y'),
('BURU','Burundi','Y'),
('BUT','Bután','Y'),
('CAB','Cabo Verde','Y'),
('CAMB','Camboya','Y'),
('CAME','Camerún','Y'),
('CANA','Canadá','Y'),
('CAT','Catar','Y'),
('CHA','Chad','Y'),
('CHI','Chile','N'),
('CHIN','China','Y'),
('CHIP','Chipre','Y'),
('VAT','Ciudad del Vaticano','Y'),
('COL','Colombia','N'),
('COM','Comoras','Y'),
('COR','Corea del Sur','Y'),
('COSM','Costa de Marfil','Y'),
('COSR','Costa Rica','N'),
('CRO','Croacia','Y'),
('CUB','Cuba','N'),
('DIN','Dinamarca','Y'),
('DOM','Dominica','N'),
('ECU','Ecuador','N'),
('EGI','Egipto','Y'),
('SAL','El Salvador','N'),
('EMI','Emiratos Árabes Unidos','Y'),
('ERI','Eritrea','Y'),
('ESL','Eslovaquia','Y'),
('ESLO','Eslovenia','Y'),
('ESP','España','N'),
('USA','Estados Unidos','N'),
('EST','Estonia','Y'),
('ETIO','Etiopía','Y'),
('FIL','Filipinas','Y'),
('FIN','Finlandia','Y'),
('FIY','Fiyi','Y'),
('FRA','Francia','Y'),
('GAB','Gabón','Y'),
('GAM','Gambia','Y'),
('GEO','Georgia','Y'),
('GHA','Ghana','Y'),
('GRA','Granada','Y'),
('GRE','Grecia','Y'),
('GUA','Guatemala','Y'),
('GUY','Guyana','Y'),
('GUI','Guinea','Y'),
('GUIN','Guinea ecuatorial','Y'),
('GUIB','Guinea-Bisáu','Y'),
('HAI','Haití','Y'),
('HON','Honduras','Y'),
('HUN','Hungría','Y'),
('IND','India','Y'),
('INDO','Indonesia','Y'),
('IRK','Irak','Y'),
('IRN','Irán','Y'),
('IRL','Irlanda','Y'),
('ISL','Islandia','Y'),
('ISM','Islas Marshall','Y'),
('ISS','Islas Salomón','Y'),
('ISR','Israel','Y'),
('ITA','Italia','N'),
('JAMA','Jamaica','Y'),
('JAP','Japón','Y'),
('JOR','Jordania','Y'),
('KAZ','Kazajistán','Y'),
('KEN','Kenia','Y'),
('KIR','Kirguistán','Y'),
('KIRI','Kiribati','Y'),
('KUW','Kuwait','Y'),
('LAO','Laos','Y'),
('LES','Lesoto','Y'),
('LETO','Letonia','Y'),
('LIBA','Líbano','Y'),
('LIBE','Liberia','Y'),
('LIB','Libia','Y'),
('LIE','Liechtenstein','Y'),
('LIT','Lituania','Y'),
('LUX','Luxemburgo','Y'),
('MAC','Macedonia del Norte','Y'),
('MAD','Madagascar','Y'),
('MALA','Malasia','Y'),
('MALU','Malaui','Y'),
('MALD','Maldivas','Y'),
('MAL','Malí','Y'),
('MALT','Malta','Y'),
('MARR','Marruecos','Y'),
('MAU','Mauricio','Y'),
('MAUR','Mauritania','Y'),
('MEX','México','N'),
('MIC','Micronesia','Y'),
('MOL','Moldavia','Y'),
('MON','Mónaco','Y'),
('MONG','Mongolia','Y'),
('MONT','Montenegro','Y'),
('MOZ','Mozambique','Y'),
('NAMI','Namibia','Y'),
('NAU','Nauru','Y'),
('NEP','Nepal','Y'),
('NIC','Nicaragua','Y'),
('NIGR','Níger','Y'),
('NIG','Nigeria','Y'),
('NOR','Noruega','Y'),
('NZL','Nueva Zelanda','Y'),
('OMA','Omán','Y'),
('HOL','Países Bajos','N'),
('PAK','Pakistán','Y'),
('PAL','Palaos','Y'),
('PAN','Panamá','Y'),
('PAP','Papúa Nueva Guinea','Y'),
('PAR','Paraguay','N'),
('PER','Perú','N'),
('POL','Polonia','Y'),
('POR','Portugal','N'),
('RUN','Reino Unido','N'),
('RCE','República Centroafricana','Y'),
('CHE','República Checa','Y'),
('RCO','República del Congo','Y'),
('RDC','República Democrática del Congo','Y'),
('RDM','República Dominicana','Y'),
('RUA','Ruanda','Y'),
('RUM','Rumanía','Y'),
('RUS','Rusia','Y'),
('SAM','Samoa','Y'),
('SCN','San Cristóbal y Nieves','Y'),
('SMA','San Marino','Y'),
('SVG','San Vicente y las Granadinas','Y'),
('SLU','Santa Lucía','Y'),
('STP','Santo Tomé y Príncipe','Y'),
('SEN','Senegal','Y'),
('SER','Serbia','Y'),
('SEY','Seychelles','Y'),
('SIE','Sierra Leona','Y'),
('SIN','Singapur','Y'),
('SIR','Siria','Y'),
('SOM','Somalia','Y'),
('SLA','Sri Lanka','Y'),
('SUA','Suazilandia','Y'),
('SUD','Sudáfrica','Y'),
('SUDA','Sudán','Y'),
('SDS','Sudán del Sur','Y'),
('SUE','Suecia','N'),
('SUI','Suiza','N'),
('SUR','Surinam','Y'),
('TAI','Tailandia','Y'),
('TAN','Tanzania','Y'),
('TAY','Tayikistán','Y'),
('TIMO','Timor Oriental','Y'),
('TOG','Togo','Y'),
('TON','Tonga','Y'),
('TTO','Trinidad y Tobago','Y'),
('TUN','Túnez','Y'),
('TURK','Turkmenistán','Y'),
('TUR','Turquía','Y'),
('TUV','Tuvalu','Y'),
('UCR','Ucrania','Y'),
('UGA','Uganda','Y'),
('UZB','Uzbekistán','Y'),
('VAN','Vanuatu','Y'),
('VEN','Venezuela','N'),
('VIET','Vietnam','Y'),
('YEM','Yemen','Y'),
('YIB','Yibuti','Y'),
('ZAM','Zambia','Y'),
('ZIM','Zimbabue','Y');


insert into Idiomas(nombreIdioma) values 
('Español'),('Portugués'),('Inglés'),('Chino Mandarín'),('Árabe'),('Ruso'),('Japonés');

insert into TiposUsuarios(nombre) values('administrador'),('operador'),('marketing');

insert into Categorias(nombreCategoria, eliminado) values
('Indoor Cameras', 'N'),
('Outdoor Cameras', 'N'),
('Battery Powered Cameras','N'),
('Indoor Pan/Tilt Cameras','N'),
('Surveillance Kits','N'),
('Smart Plugs', 'Y');
/*Falta precarga de privilegios*/

/*Precarga para testing*/
insert into Empresas(identificacionTributaria,nombre,  razonSocial, impuestos, nombreIdioma, codigo) values
('96838800-2','DirecTV','DirecTV Chile',0.19,'Español','CHI'),
('70016330-K','Entel TV HD','Entel TV HD',0.19,'Español','CHI'),
('902.237.970-14','Sky','Sky Brasil',0.17,'Portugués','BRA'),
('617.135.480-54','Claro','Claro BR',0.17,'Portugués','BRA'),
('147.11318.71-9','Alfa TV Dorada','Alfa TV Dorada',0.19,'Español','COL'),
('729.193.500-80','Teleco','Teleco',0.17,'Portugués','BRA'),
('76045622-5','Movistar TV','Movistar TV Chile',0.19,'Español','CHI'),
('161.43624.01-2','HV Televisión','HV Televisión',0.19,'Español','COL'),
('189.68045.54-8','ColCable','ColCable CORP',0.19,'Español','COL'),
('445382548297','Diamond','Cable Diamond S.R.L',0.22,'Español','URU'),
('977437632497','TV Systems','TV Systems S.R.L',0.22,'Español','URU'),
('526283747346','Directv','Directv Uruguay LTDA',0.22,'Español','URU'),
('246299533496','Cablevision','Cablevision Uruguay S.A.',0.22,'Español','URU'),
('277399585777','Oceano TV','Oceano TV S.A.',0.22,'Español','URU');

insert into Personas(usuarioSistema, nombreCompleto, codigo, identificacionTributaria) values
/*OperadorDashboard*/
('loginUser', 'Bot', 'URU', '526283747346'),
('ureta', 'Andrés Ureta','URU', '526283747346'),
('velazquez','Adriana Velazquez','CHI', '902.237.970-14'),
('martinez','Alejandro Martinez','URU', '902.237.970-14'),
('albornoz','Andrea Albornoz Peña','CHI', '902.237.970-14'),
('dalmas','Gabriela Dalmas','URU', '189.68045.54-8'),
('nola','Gerardo Nola','ARG', '189.68045.54-8'),
('diaz','Hector Martín Diaz Amarillo','URU', '246299533496'),
('villasante','Lilian Villasante','URU', '246299533496'),
('gonzalez','Maria Alejandra Gonzalez Saldivia','URU', '526283747346'),
('bentancor','Nicolás Bentancor','URU', '526283747346'),
/*Clientes*/
('V72YegUUCv','Gonzalo Hernandez','URU','76045622-5'),
('6n32ZjrHf2','Gustavo Eduardo Fajardo Gargano','BRA', '246299533496'),
('fBVZUtDZQS','Joaquín Gonzalez','URU', '189.68045.54-8'),
('7JSQWEMgkM','Richard Pais Rovira','URU', '246299533496'),
('3PSRdYh4zX','Sebastian Bianchi Kechichián','URU', '526283747346'),
('2LC2pxZXWA','Verónica Laluz','VEN', '189.68045.54-8'),
('689BcPB9YL','Virginia Reyes','COL', '526283747346'),
('YmRBQjCpXb','Sergio Castelar','COL', '902.237.970-14'),
('XFmJfxnHN2','Silvia Alpuy','URU', '902.237.970-14'),
('akVKKgtLU4','Natalia Duran','URU', '902.237.970-14'),
('i8ALUqwkhR','Damián Correa','URU','902.237.970-14'),
('VdFB5ZGinW','Daniel Dufort','URU','189.68045.54-8'),
('SZBGJB8kAQ','Claudia Viera','URU','526283747346'),
('KqTWX66XW2','Elena Silva','URU','526283747346'),
/*Cuentas de prueba para integracion con APP*/
('7JSQWEKKKM', 'Andres Ureta', 'URU', '526283747346'),
('7JSQWhhhkM', 'Alejandro Bentancor', 'URU', '526283747346'),
('byKgHzySyt','Gustavo Fernandez','URU','526283747346');




insert into Clientes (email, telefono,usuarioSistema) values
/*1*/('sergiocastelar@hotmail.com','+59895297485','YmRBQjCpXb'),
/*2*/('silviaalpuy@gmail.com','+59895047674','XFmJfxnHN2'),
/*3*/('nataliaduran@outlook.com','+59895216732','akVKKgtLU4'),
/*4*/('joaquingonzalez@hotmail.com','+59895376421','fBVZUtDZQS'),
/*5*/('veronicalaluz@gmail.com','+59895562760','2LC2pxZXWA'),
/*6*/('sebabianchi@gmail.com','+59895456503','3PSRdYh4zX'),
/*7*/('virginiareyes@hotmail.com','+59895872099','689BcPB9YL'),
/*8*/('gustavofajardo@gmail.com','+59895220696','6n32ZjrHf2'),
/*9*/('richardpais@hotmail.com','+59895280743','7JSQWEMgkM'),
/*10*/('hernandezgonzalo@hotmail.com','+59895146592','V72YegUUCv'),
/*11*/('andres.ureta@outlook.com','+59897635222','7JSQWEKKKM'), /*Cuenta de prueba para app tuya smart*/
/*12*/('nicolasbentancor1996@gmail.com','+59895605229','7JSQWhhhkM'), /*Cuenta de prueba para app tuya smart*/
/*13*/('dev1.eneplay@gmail.com','+59899083493','byKgHzySyt') /*Cuenta de prueba para app tuya smart, cuenta de prueba playstore*/
/*Cuentas secundarias*/
/*14*/('damiancorrea@hotmail.com','+59895280290','i8ALUqwkhR'),
/*15*/('danieldufort@gmail.com','+59895982823','VdFB5ZGinW'),
/*16*/('claudiaviera@hotmail.com','+59895068818','SZBGJB8kAQ'),
/*17*/('elenasilva@gmail.com','+59895029235','KqTWX66XW2'),
/*18*/('loginUser@gmail.com', '+59899580851', 'loginUser')
;



insert into OperadoresDashboard(usuarioSistema,clave,nombre, genero) values
('bentancor',SHA('alfacom123'),'administrador', 'Masculino'),
('gonzalez',SHA('alfacom123'),'administrador', 'Femenino'),
('ureta',SHA('alfacom123'),'administrador', 'Masculino'),
('diaz',SHA('peugeot'),'operador', 'Masculino'),
('nola',SHA('fiat'),'operador', 'Masculino'),
('dalmas',SHA('ferrari'),'operador', 'Femenino'),
('albornoz',SHA('volvo'),'operador', 'Femenino'),
('martinez',SHA('bmw'),'marketing', 'Masculino'),
('velazquez',SHA('chery'),'marketing', 'Femenino'),
('villasante',SHA('volkswagen'),'marketing', 'Femenino'),
('loginUser', SHA('loginUser2020!'), 'administrador', 'Masculino');

insert into TiposDispositivos(modelo, nombre, tipoComunicacion, nombreCategoria) values
/*1*/('C400','Semi-outdoor wifi security camera','Ambos', 'Outdoor Cameras'),
/*2*/('Snap 11S','1080P Weatherproof Outdoor Battery-Powered Camera','Wifi', 'Battery Powered Cameras'),
/*3*/('8W','Metal Outdoor Wi-Fi Camera 1080P','Wifi', 'Outdoor Cameras'),
/*4*/('2W9','1080P PTZ Wi-Fi Camera','Wifi', 'Indoor Pan/Tilt Cameras'),
/*5*/('Mini 14S','1080P Indoor Smart Camera','Wifi', 'Indoor Cameras'),
/*6*/('KW01UH2','Tuya 4CH 2MP Wireless NVR Kit','Wifi', 'Surveillance Kits'),
/*7*/('ML1CTY','Low Power Camera','Wifi', 'Battery Powered Cameras'),
/*8*/('LY138','FHD 1080P WiFi Smart Home Wireless Surveillance Camera Security','Wifi','Battery Powered Cameras'),
/*9*/('US-OW301','Wireless Smart 3MP outdoor WIFI AI Alarm Camera','Wifi','Outdoor Cameras'),
/*10*/('IPC128HS','1080p Outdoor Battery IP Camera','Wifi','Outdoor Cameras'),
/*11*/('TY1866 EU','Mini Smart Plug 10A/16A','Wifi','Smart Plugs'),
/*12*/('SP10','Smart Wi-Fi Plug 16A With Led Light','Wifi','Smart Plugs');

insert into Monedas(codigo, nombreMoneda, simbolo) values 
('COP','Pesos Colombianos','$'),
('USD','Dólares Americanos','$'),
('UYU','Pesos Uruguayos','$'),
('ARS','Pesos Argentinos','$'),
('BOB','Bolivianos', 'Bs.'),
('BRL','Reales','R$'),
('CLP','Pesos Chilenos', '$'),
('CRC','Colones Costarricenses', '₡'),
('CUP','Pesos Cubanos','$'),
('DOP','Pesos Dominicanos','$'),
('EUR','Euros','€'),
('MXN','Pesos Mexicanos','$'),
('PYG','Guaraníes','₲'),
('PEN','Nuevos Soles','S/.'),
('GBP','Libras Esterlinas','£');

insert into Facturas (fechaPago, fechaVencimiento, periodoServicioInicio, periodoServicioFin, tipoRecibo, monto, codigo, nroCliente, identificacionTributaria) values
/*1*/('2020-01-05','2020-01-25','2019-12-01','2019-12-31','Periodica', 500, 'UYU',6,'526283747346'),
/*2*/('2020-06-24','2020-06-25','2020-05-01','2020-05-31','Periodica', 500, 'UYU',7,'526283747346'),
/*3*/('2020-09-02','2020-05-25','2020-04-01','2020-04-30','A demanda', 300, 'UYU',8,'246299533496'),
/*4*/('2020-04-20','2020-04-25','2020-03-01','2020-03-31','A demanda', 300, 'UYU',9,'246299533496'),
/*5*/('1970-01-01','2020-08-25','2020-07-01','2020-07-31','Periodica', 25, 'BRL',1,'902.237.970-14'),
/*6*/('2020-02-20','2020-02-25','2020-01-01','2020-01-31','Periodica', 15, 'BRL',2,'902.237.970-14'),
/*7*/('2020-02-26','2020-02-25','2020-01-01','2020-01-31','Periodica', 15, 'BRL',3,'902.237.970-14'),
/*8*/('2020-07-02','2020-07-25','2020-06-01','2020-06-30','Periodica', 25, 'BRL',1,'902.237.970-14'),
/*9*/('2020-03-07','2020-03-25','2020-02-01','2020-02-29','Periodica', 15, 'BRL',2,'902.237.970-14'),
/*10*/('1970-01-01','2019-12-25','2019-12-01','2019-12-31','Periodica', 40000, 'COP',4,'189.68045.54-8'),
/*11*/('2019-05-13','2019-05-25','2019-04-01','2019-04-30','Periodica', 60000, 'COP',5,'189.68045.54-8'),
/*12*/('1970-01-01','2019-11-25','2019-10-01','2019-10-31','Periodica', 40000, 'COP',4,'189.68045.54-8'),
/*13*/('2019-04-20','2019-04-25','2019-03-01','2019-03-31','Periodica', 60000, 'COP',5,'189.68045.54-8'),
/*14*/('1970-01-01','2019-10-25','2019-09-01','2019-09-30','Periodica', 40000, 'COP',4,'189.68045.54-8');

insert into Paquetes (nombrePaquete, costoBruto, identificacionTributaria) values
/*1*/('Security Starter',500,'526283747346'),
/*2*/('Cámara IP metálica ext.',300,'246299533496'),
/*3*/('Cámara + enchufe wifi',25,'902.237.970-14'),
/*4*/('Cámara ext. alimentada por batería',15,'902.237.970-14'),
/*5*/('Paquete opcion A',40000,'189.68045.54-8'),
/*6*/('Paquete opcion B',60000,'189.68045.54-8');

insert into TiposDocumentos(codDocumento, nombreDocumento) values
('CI-UYU','Cédula de Identidad Uruguaya'),
('DNI-ARS','Documento Nacional de Identidad Argentino'),
('RG-BRA','Carteira de Identidade Brasileira'),
('PAS','Pasaporte'),
('CI-BOL','Cédula de Identidad Boliviana'),
('CI-CHL','Cédula de Identidad Chilena'),
('CI-CRC','Cédula de Identidad Costarricense'),
('CI-VZL','Cédula de Identidad Venezolana'),
('CC-COL','Cédula de Ciudadanía Colombiana'),
('CC-ECU','Cédula de Ciudadanía Ecuatoriana'),
('CI-CUB','Carné de Identidad Cubano'),
('CIE-RD','Cédula de Identidad y Electoral Dominicana'),
('DNI-ESP','Documento Nacional de Identidad Español'),
('CI-ITA','Carta d Identità Italiano'),
('DNI-PER','Documento Nacional de Identidad Peruano'),
('CIC-PAR','Cédula de Identidad Civil Paraguaya'),
('CURP-MXN','Clave Única de registro de población Mexicana'),
('CC-POR','Cartão de Cidadão Portugueis'),
('PC-USA','Passport Card'),
('DUI-SAL','Documento Unico de Identidad Salvadoreño');

insert into Principales (nroDocumento, servicioActivo, usuarioSistema, nroCliente, codDocumento) values 
('48283676','S','YmRBQjCpXb',1,'CC-COL'),
('39475896','S','XFmJfxnHN2',2,'CI-UYU'),
('51367485','S','akVKKgtLU4',3,'CI-UYU'),
('30654195','S','fBVZUtDZQS',4,'CI-UYU'),
('30523215','S','2LC2pxZXWA',5,'CI-VZL'),
('45263254','S','3PSRdYh4zX',6,'CI-UYU'),
('34758689','S','689BcPB9YL',7,'CC-COL'),
('34569632','S','6n32ZjrHf2',8,'RG-BRA'),
('56321541','S','7JSQWEMgkM',9,'CI-UYU'),
('47563251','S','V72YegUUCv',10,'CI-UYU'),
/*Cuentas de prueba para integración con APP*/
('47563260','S','7JSQWEKKKM',15,'CI-UYU'), /*Cuenta CON servicio activo*/
('47563261','N','7JSQWhhhkM',16,'CI-UYU'); /*Cuenta SIN servicio activo*/
('47273676','S', 'byKgHzySyt', 17, 'CI-UYU'), /*Cuenta CON servicio activo usada para validar en playstore*/
insert into Secundarios (usuarioSistema, nroCliente, nroDocumento) values 
('i8ALUqwkhR',11,'39475896'),
('VdFB5ZGinW',12,'30523215'),
('SZBGJB8kAQ',13,'34758689'),
('KqTWX66XW2',14,'34758689');

/*Fecha inicio por default es la fecha del sistema, aca está hardcodeada*/
insert into Suscripciones(fechaInicio,tiempoContrato,fechaFin,activa, nroDocumentoTitular, identificacionTributaria) values 
/*#Suscripcion*/ /*#Cliente*/
	/*1*/			/*1*/		('2020-06-01',2,'1970-01-01','S','48283676','902.237.970-14'),
	/*2*/			/*2*/		('2020-01-01',2,'1970-01-01','S','39475896','902.237.970-14'),
	/*3*/			/*3*/		('2020-01-01',2,'1970-01-01','S','51367485','902.237.970-14'),
	/*4*/			/*4*/		('2019-09-01',2,'1970-01-01','S','30654195','189.68045.54-8'),
	/*5*/			/*5*/		('2019-03-01',2,'1970-01-01','S','30523215','189.68045.54-8'),
	/*6*/			/*6*/		('2019-12-01',2,'1970-01-01','S','45263254','526283747346'),
	/*7*/			/*7*/		('2020-05-01',2,'1970-01-01','S','34758689','526283747346');

insert into Dispositivos (nroSerie, idTipoDispositivo, identificacionTributaria, nroCliente) values 
/*Dispositivos colocados*/
('GTY2QWF7MWZ2WT',1,'526283747346',6),
('PDQBBFT3N2CPHS',1,'526283747346',6),
('6ACQYUFWHXA2WA',1,'526283747346',7),
('XRFSY54P3TW3TV',1,'526283747346',7),
('MLH6BV3RV9W5K7',1,'902.237.970-14',1),
('C4WP96PM8TXTRQ',12,'902.237.970-14',1),
('VTSVR22T2BVFXV',3,'246299533496',8),
('UK5L97TFPTCKYB',3,'246299533496',9),
('KCRRCYAQJ3FLHZ',2,'902.237.970-14',2),
('ZBMXFC2D9TURRR',2,'902.237.970-14',3),
('4UYTYVDXBVJ4AX',5,'189.68045.54-8',4),
('ADMYD3R4T4FZ6B',5,'189.68045.54-8',5),
('BJBGYQ6NF59ML9',5,'189.68045.54-8',5),

/*Dispositivos en desuso*/
('SPB2Z6DN59W8ZS',4,'526283747346',null),
('6BZPJJGP9WA4N5',4,'526283747346',null),
('QHTZTB5XSKWXL2',4,'526283747346',null),
('GHE4BL7QQQLE3S',5,'526283747346',null),
('H8FL2X7GKS6WJV',6,'526283747346',null),
('L6XLCEMBDBJ6Y7',6,'246299533496',null),
('MPXWX8KY697KKD',6,'246299533496',null),
('KF2JT6XVH2AKCJ',7,'246299533496',null),
('QP68Q8NJUZQLAP',7,'246299533496',null),
('AJH3SD9FABEG3V',7,'246299533496',null),
('E84FXAMYYBF2YS',8,'902.237.970-14',null),
('L6UVP5A7QUNE3Q',8,'902.237.970-14',null),
('TWHGHF6XH3U7FT',8,'902.237.970-14',null),
('579WZ7V22F7T7Q',9,'902.237.970-14',null),
('BSCNF393EWJHRJ',9,'902.237.970-14',null),
('AL6K2B5X9WRY4E',9,'189.68045.54-8',null),
('69LN5YR4G6T3RP',10,'189.68045.54-8',null),
('DQZEH8CZYNTKFZ',10,'189.68045.54-8',null),
('4HRNQVVL7KMDD6',10,'189.68045.54-8',null),
('6K73CUHYHALHSN',11,'189.68045.54-8',null),
('25HMP4BPKT4P2R',11,'76045622-5',null),
('5GR2VM8KVTZHX6',11,'76045622-5',null),
('ZFWNR2DC63AG6W',12,'76045622-5',null),
('SWMM5V4QEFXAGB',1,'729.193.500-80',null),
('W7RUW8HEEBUR4L',1,'729.193.500-80',null),
('W228S6GESC3VY7',1,'729.193.500-80',null),
('AQ5JTG3LPQJS5N',1,'729.193.500-80',null);

insert into Privilegios(nombrePrivilegio) values 
/*CATEGORIAS*/
('INSERT CATEGORIAS'),
('DELETE CATEGORIAS'),
('UPDATE CATEGORIAS'),
('SELECT CATEGORIAS'),
/*DISPOSITIVOS*/
('INSERT DISPOSITIVOS'),
('DELETE DISPOSITIVOS'),
('UPDATE DISPOSITIVOS'),
('SELECT DISPOSITIVOS'),
/*EMPRESAS*/
('INSERT EMPRESAS'),
('DELETE EMPRESAS'),
('UPDATE EMPRESAS'),
('SELECT EMPRESAS'),
/*FACTURAS*/
('INSERT FACTURAS'),
('DELETE FACTURAS'),
('UPDATE FACTURAS'),
('SELECT FACTURAS'),
/*PAQUETES*/
('INSERT PAQUETES'),
('DELETE PAQUETES'),
('UPDATE PAQUETES'),
('SELECT PAQUETES'),
/*PRINCIPALES*/
('INSERT PRINCIPALES'),
('DELETE PRINCIPALES'),
('UPDATE PRINCIPALES'),
('SELECT PRINCIPALES'),
/*SECUNDARIOS*/
('INSERT SECUNDARIOS'),
('DELETE SECUNDARIOS'),
('UPDATE SECUNDARIOS'),
('SELECT SECUNDARIOS'),
/*SUSCRIPCIONES*/
('INSERT SUSCRIPCIONES'),
('DELETE SUSCRIPCIONES'),
('UPDATE SUSCRIPCIONES'),
('SELECT SUSCRIPCIONES'),
/*TIPOS DISPOSITIVOS*/
('INSERT TIPOS DISPOSITIVOS'),
('DELETE TIPOS DISPOSITIVOS'),
('UPDATE TIPOS DISPOSITIVOS'),
('SELECT TIPOS DISPOSITIVOS'),
/*TIPOS USUARIOS*/
('INSERT TIPOS USUARIOS'),
('DELETE TIPOS USUARIOS'),
('UPDATE TIPOS USUARIOS'),
('SELECT TIPOS USUARIOS');



/*Suscripciones, Facturas*/
/*Donde idSuscripcion = null es porque la factura no es recurrente y no existe suscripcion*/ 
insert into GeneraSF(idSuscripcion, idFactura) values
/*#Cliente*/
	/*6*/	(6,1),
	/*7*/	(7,2),
	/*8*/	(null,3),
	/*9*/	(null,4),
	/*1*/	(1,5),
	/*2*/	(2,6),
	/*3*/	(3,7),
	/*1*/	(1,8),
	/*2*/	(2,9),
	/*4*/	(4,10),
	/*5*/	(5,11),
	/*4*/	(4,12),
	/*5*/	(5,13),
	/*4*/	(4,14);

/*Facturas, Paquetes*/
insert into PoseeSP(idPaquete, idSuscripcion) values
(1,6),
(1,7),
(2,null),
(2,null),
(3,1),
(4,2),
(4,3),
(3,1),
(4,2),
(5,4),
(6,5),
(5,4),
(6,5),
(5,4);

/*Paquetes, TiposDispositivos*/
insert into TieneTP(idPaquete,idTipoDispositivo,cantidadDispositivos) values
(1,1,2),
(2,3,1),
(3,1,1),
(3,12,1),
(4,2,1),
(5,5,1),
(6,5,2);
/*TiposUsuarios, Privilegios*/
/*insert into TieneTUP(nombre,idPrivilegio) values ();*/
/*insert into Privilegios () values ();*/