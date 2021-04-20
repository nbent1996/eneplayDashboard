<%@page import="Modelo.Operador"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@page import="Modelo.Funciones, Datos.OpSuscripcion"%>
<%
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    OpSuscripcion op = new OpSuscripcion(operador);
    String nombreArchivo = "listaSuscripciones.xls";
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Reporte total de suscripciones</title>

    </head>
    
    <body class="w3-light-grey">  
        <div class="form">
            <div class="margin-top20">
                <div>
                    <h1 class="nb-title-center">Reporte total de suscripciones</h1>
                </div>
                <span id="spanDispositivosPlanilla"><%= Funciones.tablaSuscripciones("tblSuscripciones", op.obtenerTodos(), true)%></span>
            </div>  
        </div>
    </body>
</html>
