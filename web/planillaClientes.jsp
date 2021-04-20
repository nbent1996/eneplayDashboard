<%@page import="Modelo.Operador"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@page import="Modelo.Funciones, Datos.OpPersona"%>
<%
    Operador operador = (Operador) request.getSession().getAttribute("OperadorLogueado");
    OpPersona op = new OpPersona(operador);
    String tipo = request.getParameter("tipo");
    String nombreArchivo = "";
    if(tipo.equals("Principal")){
        nombreArchivo = "listaClientesTitulares.xls";
    }else if(tipo.equals("Secundario")){
        nombreArchivo = "listaCuentasSecundarias.xls";
    }
    response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo);
%>
<!DOCTYPE html>
<html>
    <head>
        <%if (tipo.equals("Principal")) {%>
        <title>Listado de Clientes titulares</title>
        <%} else if (tipo.equals("Secundario")) {%>
        <title>Listado de Clientes titulares</title >
        <%}%>
    </head>
    
    <body class="w3-light-grey">  
        <div class="form">
            <div class="margin-top20">
                <div>
                    <%if (tipo.equals("Principal")) {%>
                    <h1 class="nb-title-center">Listado de Clientes titulares</h1>
                    <%} else if (tipo.equals("Secundario")) {%>
                    <h1 class="nb-title-center">Listado de Cuentas secundarias</h1>
                    <%}%>

                </div>
                    <span id="spanClientesPlanilla">
                        <%if (tipo.equals("Principal")) {%>
                        <%= Funciones.tablaClientesCompleta("tblPrincipales", op.buscar(null, "Modelo.Principal"), "Principal", true)%>
                        <%} else if (tipo.equals("Secundario")) {%>
                        <%= Funciones.tablaClientesCompleta("tblSecundarios", op.buscar(null, "Modelo.Secundario"), "Secundario", true)%>
                        <%}%>

                    </span>
            </div>  
        </div>
    </body>
</html>
