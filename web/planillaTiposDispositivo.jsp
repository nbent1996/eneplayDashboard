<%@page import="Modelo.Operador"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@page import="Modelo.Funciones, Datos.OpTipoDispositivo"%>
<%
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    OpTipoDispositivo op = new OpTipoDispositivo(operador);
    String nombreArchivo = "catalogoDispositivos.xls";
    response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cat&aacute;logo de dispositivos</title>
    </head>
    
    <body class="w3-light-grey">  
        <div class="form">
            <div class="margin-top20">
                <div>
                    <h1 class="nb-title-center">Cat&aacute;logo de dispositivos</h1>
                </div>
                <span id="spanPaquetesPlanilla"><%= Funciones.tablaTiposDispositivos("tblTiposDispositivo", op.obtenerTodos(), true) %></span>
            </div>  
        </div>
    </body>
</html>
