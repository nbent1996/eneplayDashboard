<%@page import="Modelo.Operador"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@page import="Modelo.Funciones, Datos.OpDispositivo"%>
<%
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    OpDispositivo op = new OpDispositivo(operador);
    String nombreArchivo = "listaDispositivos.xls";
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Reporte de dispositivos en existencia</title>

    </head>
    
    <body class="w3-light-grey">  
        <div class="form">
            <div class="margin-top20">
                <div>
                    <h1 class="nb-title-center">Lista de Dispositivos</h1>
                </div>
                <span id="spanDispositivosPlanilla"><%= Funciones.tablaDispositivos("tblDispositivos", op.obtenerTodos(), true)%></span>
            </div>  
        </div>
    </body>
</html>
