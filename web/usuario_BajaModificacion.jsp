<%@page import="Modelo.Empresa"%>
<%@page import="Modelo.Operador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String msg = request.getParameter("msg");
    String tipoJSP = request.getParameter("tipo");
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    Empresa empresa = operador.getEmpresaAsociada();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%if (tipoJSP.equals("baja")) {%>
        <title>Baja de Usuario</title>
        <%} else if (tipoJSP.equals("modificacion")) {%>
        <title>Modificaci&oacute;n de Usuario</title>
        <%}%> 
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
                    mostrarTablaUsuarios();                    
                    function mostrarTablaUsuarios(){
                        $.get("ManejoUsuariosServlet?accion=mostrarTablaUsuariosInicio", function(data){
                            document.getElementById("tblUsuariosFiltrados").innerHTML=data;
                        });               
                    }
                    function buscarUsuarioBaja(){
                            
                        var nombreUsuario = $("#txtbxUsuarioBaja").val();
                        var nombreCompleto = $("#txtbxNombreCompletoBaja").val();
                        $.get("ManejoUsuariosServlet?accion=buscarUsuariosBaja&nombreUsuario=" + nombreUsuario + "&nombreCompleto=" + nombreCompleto, function (data) {
                            document.getElementById("tblUsuariosFiltrados").innerHTML = data;
                            //limpio campo de span de mensaje de usuario borrado
                            document.getElementById("spanMensaje").innerHTML = ""; //MÉTODO PARA LIMPIAR CAMPO
                        });
                    }
                    function borrarUsuariosSeleccionados(){
                        var listaUsuariosSeleccionados = new Array();
                        $("input:checkbox:checked").each(   
                            function() {
                                listaUsuariosSeleccionados.push($(this).val());
                                //alert("El checkbox con valor " + $(this).val() + " está seleccionado");
                            }
                        );
                            $.get("ManejoUsuariosServlet?accion=borrarUsuarios&listaUsuarios=" + listaUsuariosSeleccionados, function (data) {
                            
                            document.getElementById("spanMensaje").innerHTML = data; //muestro mensaje modal
                            mostrarTablaUsuarios();//Refresco tabla
                        });
                                                
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
        
        
        <!-- Overlay effect when opening sidebar on small screens -->
        <div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" title="close side menu" id="myOverlay"></div>
  
        <!-- !PAGE CONTENT! -->
        <div class="ABMContainer">
            <!-- Header -->
            <header class="w3-container estilosHeader">
                <%if (tipoJSP.equals("baja")) {%>
                <h5><b><i class="fa fa-users"></i> Baja de Usuario</b></h5>
                <%} else if (tipoJSP.equals("modificacion")) {%>
                <h5><b><i class="fa fa-users"></i> Modificaci&oacute;n de Usuario</b></h5>
                <%}%>             
            </header>
                
                <div class="form">
                    <!--LAS BÚSQUEDAS SE HACEN POR AJAX CON BUTTONS, NO SE USAN FORMS-->
                        <div><h5 class="nb-title-left">Ingrese los filtros por los que desea buscar</h5></div>
                        
                        <div class="margin-top20"><label for="txtbxUsuarioBaja">Usuario: </label><input type="text" class="nb-input" id="txtbxUsuarioBaja" name="usuarioBaja"/></div>
                        <div class="margin-top20"><label for="txtbNombreCompletoBaja">Nombre y/o Apellido: </label><input type="text" class="nb-input" id="txtbxNombreCompletoBaja" name="nombreCompletoBaja"/></div>                        
                        <div class="botonera">
                            <input type="button" class ="submitSearch" onclick="buscarUsuarioBaja()" id="btnBuscarUsuarioBaja" value="Buscar">
                            <input type="reset" class="limpiarCampos" value="Limpiar campos">    
                        </div>
                        <%if (msg != null) {%>
                        <div>
                            <p class="message"><%=msg%></p>                        
                        </div>
                        <%}%>
                        <div id="tablaUsuarios" >
                            <table class="w3-table-all">
                                <caption><h5 class="nb-title-center">Lista de Usuarios</h5></caption>
                                <thead>
                                    <tr>
                                        <th>Nombre de usuario</th>
                                        <th>Nombre completo</th>
                                        <th>Seleccionar</th>
                                    </tr>
                                </thead>
                                <tbody id="tblUsuariosFiltrados">
                                </tbody>
                            </table>
                            <div class="margin-top20">

                                <div class="botonera">
                                    <%
                                    if(tipoJSP.equals("baja")){%>
                                        <input type="button" class="submitBaja" id="btnBorrarUsuariosSeleccionados" value="Borrar">                                      
                                    <%}else if(tipoJSP.equals("modificacion")){%>
                                        <input type="button" class="submitModificacion" id="btnModificarUsuarioSeleccionado" value="Modificar">
                                    <%}%> 
                                </div>
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
                            </div>
                        </div>
                </div>
        </div>
    </body>
</html>
