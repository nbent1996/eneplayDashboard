package vistaWeb;

//import Modelo.Funciones;
//import controlador.ControladorExportarPlanillas;
//import controlador.Interfaces.IVistaExportarPlanillas;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//public class VistaExportarPlanillasWeb implements IVistaExportarPlanillas{
//    
//    /*Estado*/
//    private ControladorExportarPlanillas controlador;
//    private String destino;
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private PrintWriter out;
//    /*Estado*/
//    
//    /*Constructores*/
//    public VistaExportarPlanillasWeb(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        this.response = response;
//        this.out = response.getWriter();
//        controlador = new ControladorExportarPlanillas(this);
//    }
//    /*Constructores*/
//    
//    /*Comportamiento*/
//    public void procesarRequest(HttpServletRequest request, HttpServletResponse response){
//        String accion = request.getParameter("accion");
////        switch(accion){
////            case "exportarPlanillas":
////                this.exportarPlanillas(request, response);
////            break;
////
////        }
//    }    
////    public void exportarPlanillas(HttpServletRequest request, HttpServletResponse response){
////       this.request = request;
////       this.response = response;
////       String tabla = request.getParameter("selTablaExportar");
////       OutputStream out = this.controlador.exportarPlanilla(tabla);
////       
////    }
//    
//    @Override
//    public void mensajeError(String nombreJSP, String texto) {
//        destino = nombreJSP+"?msg=" + texto;
//        try{
//            response.sendRedirect(destino);
//        }catch(IOException ex){
//            System.out.println(texto);
//        }   
//    }
//    
//    /*Comportamiento*/
//
//
//    
//    
//}
