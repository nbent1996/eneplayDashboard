
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Empresa"%>
<%@page import="Modelo.Operador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String msg = request.getParameter("msg");
    
    HttpSession sessionLogin = request.getSession(false);
    if (sessionLogin == null) {
        response.sendRedirect("login.jsp?msg=Debe Loguearse");
        return;
    }
    
    Operador operador = (Operador) sessionLogin.getAttribute("OperadorLogueado");
    if (operador == null) {
        response.sendRedirect("login.jsp?msg=Debe Loguearse");
        return;
    }
   
    
    Empresa empresa = operador.getEmpresaAsociada();
    

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alfacom Platform</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--CSS-->  
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="css/stylesCustom.css">
        <link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway"> 
        <!--Javascript-->
        <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
        <script type="text/javascript" src="bootstrap-4.5.2-dist/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/functions.js"></script>
        <script type="text/javascript" src="js/events.js"></script>


    </head>
    <body class="w3-light-grey">
        <script>

            //ArrayList <Integer> listaEstadisticas = new ArrayList();
            
            //var listaEstadisticas = new Array();
            
            //mostrarEstadisticas();
            
            //function mostrarEstadisticas(){
              //  $.get("InicioServlet?accion=estadisticasInicio", function(data){
                    //document.getElementById("selTiposDocumentoClienteAlta").innerHTML=data;
                //    alert(data);
                  //      if(data!=null){//quiere decir que guardó las estadísticas en la session en la vistaInicioWeb (en un array de ints)
                    //        listaEstadisticas.push(data);

                      //  }
   
               // });
           // }

           //YA NO HAGO ACÁ EL TRABAJO DE LAS ESTADÍSTICAS YA QUE SE EJECUTABA PRIMERO EL CODIGO DEL SCRIPLET Y LUEGO EL CODIGO DE ESTE SCRIPT QUE CREABA LA SESSION
           //CON LOS DATOS DE LAS ESTADÍSTICAS
            mostrarTablasInicio();   
            function mostrarTablasInicio(){
                    $.get("InicioServlet?accion=mostrarTablaPaquetes", function(data){
                        document.getElementById("spanNuestrosPaquetesInicio").innerHTML=data;
                    });
                    $.get("InicioServlet?accion=mostrarTablaTiposDispositivos", function(data){
                        document.getElementById("spanCatalogoDispositivosInicio").innerHTML=data;
                    });
            }
        </script>
        <div class="w3-bar w3-top w3-black w3-large" id="divBarraSuperior">
            <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey " onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
            <span class="w3-bar-item w3-right">
                <% if (empresa.getNombre().equals("ColCable")) {%>
                <img src="resources/Colcable-logo2.png" class="imgEmpresa"/>
                <%} else if (empresa.getNombre().equals("Cablevision")) {%>
                <img src="resources/cablevisionLogo1.png" class="imgEmpresa"/>
                <%} else if (empresa.getNombre().equals("Directv")) {%>
                <img src="resources/directvLogo3.png" class="imgEmpresa"/>
                <%} else if (empresa.getNombre().equals("Sky")) {%>
                <img src="resources/skyLogo4.png" class="imgEmpresa"/>
                <%}%>
            </span>
        </div>

        <!-- Sidebar/menu -->
        <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" id="mySidebar"><br>
            <div class="w3-container w3-row">
                <div class="w3-col s4">
                    <%if (operador.getGenero().equals("Masculino")) {%>
                    <img src="resources/avatarHombre.png" class="w3-circle w3-margin-right" id="imgPerfil">
                    <%} else if (operador.getGenero().equals("Femenino")) {%>
                    <img src="resources/avatarMujer.png" class="w3-circle w3-margin-right" id="imgPerfil">
                    <%}%>
                </div>
                <div class="w3-col s8 w3-bar">
                    <%if (operador.getGenero().equals("Masculino")) {%>
                    <span>Bienvenido, <strong><%= operador.getNombreCompleto()%></strong></span><br>    
                    <%} else if (operador.getGenero().equals("Femenino")) {%>
                    <span>Bienvenida, <strong><%= operador.getNombreCompleto()%></strong></span><br>    
                    <%}%>


                    <!-- ICONOS DEBAJO DE PERFIL DE USUARIO -->
                    <a href="login.jsp" class="w3-bar-item w3-button"><i class="fa fa-sign-out"></i></a>                    
                </div>
            </div>

            <hr>

            <div class="w3-bar-block">
                <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Cerrar Menu</a>
                <a href="index.jsp" class="w3-bar-item w3-button w3-padding"><i class="fa fa-home fa-fw"></i>&nbsp; Inicio</a><br><br>
                <div class="w3-dropdown-hover w3-mobile">
                    <button class="w3-button"><i class="fa fa-users fa-fw"></i>&nbsp; Usuarios</button>
                    <div class="w3-dropdown-content w3-bar-block w3-dark-grey">
                        <a href="usuario_Alta.jsp" class="w3-bar-item w3-button w3-mobile">Alta de Usuario</a>
                        <a href="usuario_BajaModificacion.jsp?tipo=baja" class="w3-bar-item w3-button w3-mobile">Baja de Usuario</a>
                        <a href="usuario_BajaModificacion.jsp?tipo=modificacion" class="w3-bar-item w3-button w3-mobile">Modificación de Usuario</a>
                    </div>
                </div>
                <div class="w3-dropdown-hover w3-mobile">
                    <button class="w3-button"><i class="fa fa-address-card fa-fw"></i>&nbsp; Clientes</button>
                    <div class="w3-dropdown-content w3-bar-block w3-dark-grey">
                        <a href="cliente_Alta.jsp" class="w3-bar-item w3-button w3-mobile">Alta de Cliente</a>
                        <a href="cliente_BajaModificacion.jsp?tipo=baja" class="w3-bar-item w3-button w3-mobile">Baja de Cliente</a>
                        <a href="cliente_BajaModificacion.jsp?tipo=modificacion" class="w3-bar-item w3-button w3-mobile">Modificación de Cliente</a>
                    </div>
                </div>
                <div class="w3-dropdown-hover w3-mobile">
                    <button class="w3-button"><i class="fa fa fa-cubes fa-fw"></i>&nbsp; Paquetes</button>
                    <div class="w3-dropdown-content w3-bar-block w3-dark-grey">
                        <a href="paquete_Alta.jsp" class="w3-bar-item w3-button w3-mobile">Alta de Paquete</a>
                        <a href="paquete_BajaModificacion.jsp?tipo=baja" class="w3-bar-item w3-button w3-mobile">Baja de Paquete</a>
                        <a href="paquete_BajaModificacion.jsp?tipo=modificacion" class="w3-bar-item w3-button w3-mobile">Modificación de Paquete</a>
                    </div>
                </div>
                <div class="w3-dropdown-hover w3-mobile">
                    <button class="w3-button"><i class="fa fa fa-suitcase fa-fw"></i>&nbsp; Suscripciones</button>
                    <div class="w3-dropdown-content w3-bar-block w3-dark-grey">
                        <a href="suscripcion_Alta.jsp" class="w3-bar-item w3-button w3-mobile">Alta de Suscripci&oacute;n</a>
                        <a href="suscripcion_BajaModificacion.jsp?tipo=baja" class="w3-bar-item w3-button w3-mobile">Baja de Suscripci&oacute;n</a>
                        <a href="suscripcion_BajaModificacion.jsp?tipo=modificacion" class="w3-bar-item w3-button w3-mobile">Modificación de Suscripci&oacute;n</a>
                    </div>
                </div>
                <div class="w3-dropdown-hover w3-mobile">
                    <button class="w3-button"><i class="fa fa-video-camera fa-fw"></i>&nbsp; Dispositivos</button>
                    <div class="w3-dropdown-content w3-bar-block w3-dark-grey">
                        <a href="dispositivo_Alta.jsp" class="w3-bar-item w3-button w3-mobile">Alta de Dispositivo</a>
                        <a href="dispositivo_BajaModificacion.jsp?tipo=baja" class="w3-bar-item w3-button w3-mobile">Baja de Dispositivo</a>
                        <a href="dispositivo_BajaModificacion.jsp?tipo=modificacion" class="w3-bar-item w3-button w3-mobile">Modificación de Dispositivo</a>
                    </div>
                </div>
                <a href="exportarPlanillas.jsp" class="w3-bar-item w3-button w3-padding"><i class="fa fa-file-text-o fa-fw"></i>&nbsp; Exportar planillas</a>
                <a href="envioNotificacionesApp.jsp" class="w3-bar-item w3-button w3-padding"><i class="fa fa fa-bell fa-fw"></i>&nbsp; Enviar notificaciones</a>
                <a href="reporteGraficas.jsp" class="w3-bar-item w3-button w3-padding"><i class="fa fa-line-chart fa-fw"></i>&nbsp; Tablero de gráficas</a>
                <hr>
            </div>

        </nav> 
        <!-- !PAGE CONTENT! -->
        <div class="ABMContainer">
            <!-- Header -->
            <header class="w3-container estilosHeader">
                <h5><b><i class="fa fa-dashboard"></i> Dashboard <span><%= empresa.getNombre()%></span></b></h5>
            </header>
            <div class="form">
                <div class="w3-row-padding w3-margin-bottom">
                    <div class="w3-quarter">
                        <div class="w3-container w3-light-blue w3-text-white w3-padding-16">
                            <div class="w3-left"><i class="fa fa-users w3-xxlarge"></i></div>
                            <div class="w3-right">
                                <h3 name="clientesRegistrados">
                                    
                                    
                                    <%
                                        
                                        Integer estadisticaClientes = (Integer) request.getSession().getAttribute("estadisticaClientes");
                                        out.print(estadisticaClientes);
                                        
                                    %>
                                    
                                    
                                </h3> <!-- Cantidad clientes totales -->
                            </div>
                            <div class="w3-clear"></div>
                            <h4>Clientes Registrados</h4>
                        </div>
                    </div>
                    <div class="w3-quarter">
                        <div class="w3-container w3-light-blue w3-text-white w3-padding-16">
                            <div class="w3-left"><i class="fa fa-user-circle w3-xxlarge"></i></div>
                            <div class="w3-right">
                                <h3 name="cuentasSecundarias">
                                    
                                    
                                    <%
                                        
                                        Integer estadisticaCuentasSecundarias = (Integer) request.getSession().getAttribute("estadisticaCuentasSecundarias");
                                        out.print(estadisticaCuentasSecundarias);
                                        
                                    %>
                                    
                                    
                                </h3> <!-- Cantidad cuentas secundarias -->
                            </div>
                            <div class="w3-clear"></div>
                            <h4>Cuentas Secundarias</h4>
                        </div>
                    </div>
                    <div class="w3-quarter">
                        <div class="w3-container w3-light-blue w3-text-white w3-padding-16">
                            <div class="w3-left"><i class="fa fa-video-camera w3-xxlarge"></i></div>
                            <div class="w3-right">
                                <h3 name="dispositivosRegistrados">
                                    
                                    
                                    <%
                                        
                                        Integer estadisticaDispositivos = (Integer) request.getSession().getAttribute("estadisticaDispositivos");
                                        out.print(estadisticaDispositivos);
                                        
                                    %>
                                    
                                    
                                </h3> <!-- Cantidad Dispositivos en uso -->
                            </div>
                            <div class="w3-clear"></div>
                            <h4>Dispositivos Registrados</h4>
                        </div>
                    </div>
                    <div class="w3-quarter">
                        <div class="w3-container w3-light-blue w3-text-white w3-padding-16">
                            <div class="w3-left"><i class="fa fa-handshake-o w3-xxlarge"></i></div>
                            <div class="w3-right">
                                <h3 name="suscripciones">
                                    
                                    <%
                                        
                                        Integer estadisticaSuscripciones = (Integer) request.getSession().getAttribute("estadisticaSuscripciones");
                                        out.print(estadisticaSuscripciones);
                                        
                                    %>
                                    
                                </h3> <!-- Cantidad suscripciones -->
                            </div>
                            <div class="w3-clear"></div>
                            <h4>Suscripciones activas</h4>
                        </div>
                    </div>
                </div>

                <div class="w3-container">
                    <div class="w3-row-padding">

                        <h5>Nuestros paquetes de suscripci&oacute;n</h5>
                        <span id="spanNuestrosPaquetesInicio" name="spanNuestrosPaquetesInicio"></span>
                    </div>
                </div>
                <hr>
                <div class="w3-container">
                    <div class="w3-row-padding">

                        <h5>Cat&aacute;logo de dispositivos</h5>
                        <span id="spanCatalogoDispositivosInicio" name="spanCatalogoDispositivosInicio"></span>
                    </div>
                </div>
                <hr>
                <!-- End page content -->
            </div>
        </div>
    </body>
</html>
