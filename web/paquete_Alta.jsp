<%@page import="Modelo.Empresa"%>
<%@page import="Modelo.Operador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String msg = request.getParameter("msg");
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    Empresa empresa = operador.getEmpresaAsociada();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alta Paquete</title>
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
            
           mostrarTablaTiposDispositivos();
           function mostrarTablaTiposDispositivos(){
               $.get("ManejoPaquetesServlet?accion=generarTablaTiposDispositivos", function(data){
                  document.getElementById("spanTiposDispositivoPaqueteAlta").innerHTML=data; 
               });
               
           }
           
           function altaPaquete(){
               
               var nombrePaquete = $("#txtbxNombrePaqueteAlta").val();
               var costoPaquete = $("#txtbxCostoBrutoPaqueteAlta").val();
               
               
               var listaSeleccionados = new Array();
               var listaCantidades = new Array();
               
                $("input:checkbox:checked").each(
                        function () {
                            listaSeleccionados.push($(this).val());                            
                        }
                ); 
        
                for (var i = 0; i < listaSeleccionados.length; i++) {//recorro la lista de ids y me guardo en una lista el valor de la cantidad ingresada 

                    var nombreIdInput = "#txtbxCant"+listaSeleccionados[i];//armo el nombre del id del input number
                    var valueInput = $(nombreIdInput).val();//agarro el valor ingresado del input
                    listaCantidades.push(valueInput);//agrego el valor a la lista. Después paso la lista por parámetro 
                    
                }
        
                $.get("ManejoPaquetesServlet?accion=altaPaqueteConDispositivos&listaDispositivos=" + listaSeleccionados + "&nombrePaquete=" + nombrePaquete + "&costoPaquete=" + costoPaquete + "&listaCantidades=" + listaCantidades, function (data) {
                    document.getElementById("spanMensaje").innerHTML = data; //muestro mensaje modal
                        if(data!=null){
                            limpiarCampos();
                        }
                });
                
           }
           
           function limpiarCampos(){
                $("#txtbxNombrePaqueteAlta").val("");
                $("#txtbxCostoBrutoPaqueteAlta").val("");
                //limpiar los check y cantidad
           }
           
           
           
        </script>

        <div class="w3-bar w3-top w3-black w3-large" id="divBarraSuperior">
            <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey " onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
            <span class="w3-bar-item w3-right">
                <% if(empresa.getNombre().equals("ColCable")){%>
                    <img src="resources/Colcable-logo2.png" class="imgEmpresa"/>
                <%}else if (empresa.getNombre().equals("Cablevision")){%>
                    <img src="resources/cablevisionLogo1.png" class="imgEmpresa"/>
                <%}else if (empresa.getNombre().equals("Directv")){%>
                    <img src="resources/directvLogo3.png" class="imgEmpresa"/>
                <%}else if (empresa.getNombre().equals("Sky")){%>
                    <img src="resources/skyLogo4.png" class="imgEmpresa"/>
                <%}%>
            </span>
        </div>
        
        <!-- Sidebar/menu -->
         <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" id="mySidebar"><br>
            <div class="w3-container w3-row">
                <div class="w3-col s4">
                    <%if(operador.getGenero().equals("Masculino")){%>
                    <img src="resources/avatarHombre.png" class="w3-circle w3-margin-right" id="imgPerfil">
                    <%}else if(operador.getGenero().equals("Femenino")){%>
                    <img src="resources/avatarMujer.png" class="w3-circle w3-margin-right" id="imgPerfil">
                    <%}%>
                </div>
                <div class="w3-col s8 w3-bar">
                    <%if(operador.getGenero().equals("Masculino")){%>
                        <span>Bienvenido, <strong><%= operador.getNombreCompleto()%></strong></span><br>    
                    <%}else if(operador.getGenero().equals("Femenino")){%>
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
                <h5><b><i class="fa fa fa-cubes fa-fw"></i> Alta de Paquetes</b></h5>
            </header>
            
                <div class="form">
                    <!--<form id="formAltaPaquete" name="formAltaPaquete" action="ManejoPaquetesServlet" method="post" onsubmit="return validarAltaPaquete(this)">-->
                    <form>
                        <div>
                            <label for="txtbxNombrePaqueteAlta">Nombre: </label>
                            <input type="text" class="nb-input" id="txtbxNombrePaqueteAlta" name="txtbxNombrePaqueteAlta" required="true"/>
                        </div>
                        <div class="margin-top20">
                            <label for="txtbxCostoBruto">Costo bruto: </label>
                            <span id="spanMonedaCosto"></span>
                            <input type="text" class="nb-input" id="txtbxCostoBrutoPaqueteAlta" name="txtbxCostoBrutoPaqueteAlta" required="true"/>
                        </div>
                        <div class="margin-top20">
                            <div>
                                <h5 class="nb-title-center">Dispositivos asignados al Paquete</h5>
                            </div>
                            <span id="spanTiposDispositivoPaqueteAlta" name="generarTablaTiposDispositivos"></span>
                        </div>

                        <hr>        
                        <div class="botonera">
                            <input type="button" class="submitAlta" id="btnConfirmarAltaPaquete" value="confirmar">
                            <input type="reset" class="limpiarCampos" value="Limpiar campos">    
                        </div>
                        
                                <span id="mensajeAlta"></span>
                                <input type="hidden" name="accion" value="formAltaCliente">
                        
                        <div id="divModal" class="w3-modal">
                            <div class="w3-modal-content w3-animate-zoom" >
                                <div class="w3-container">
                                    <span id="spanBtnCerrar" class="w3-button w3-display-topright">&times;</span>
                                    <br>
                                        <span id="spanMensaje"></span>
                                    <br>
                                    <br>
                                </div>
                            </div>
                        </div>
                        
                        <%if (msg != null) {%>
                        <div>
                            <p class="message"><%=msg%></p>                        
                        </div>
                        <%}%>
                    </form>
                </div>

            </div>
        </div>
    </body>
</html>