package vistaWeb;

import Modelo.Funciones;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.ProgramException;
import Modelo.TipoDispositivo;
import controlador.ControladorInicio;
import controlador.Interfaces.IVistaInicio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VistaInicioWeb implements IVistaInicio {
/*Estado*/
    private ControladorInicio controlador;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession sesion;
    private PrintWriter out;
    private String destino;   
    
    private Operador operadorLogueado;
/*Estado*/

/*Constructores*/
    public VistaInicioWeb(HttpServletRequest request, HttpServletResponse response) throws IOException{
        this.response = response;
        this.request = request;
        this.out = response.getWriter();
        this.sesion = request.getSession();
        this.operadorLogueado = (Operador) request.getSession().getAttribute("OperadorLogueado");
        this.controlador = new ControladorInicio(this, operadorLogueado);
    }
/*Constructores*/

/*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response){
        String accion= request.getParameter("accion");
        switch(accion){
            case "estadisticasInicio":
                  obtenerEstadisticas();
            break;
            case "mostrarTablaPaquetes":
                this.generarTablaPaquetes();
            break;
            case "mostrarTablaTiposDispositivos":
                this.generarTablaTiposDispositivos();
            break;
        }
    }
    private void obtenerEstadisticas(){
        try {
            controlador.obtenerEstadisticasA();
        } catch (Exception ex) {
            Logger.getLogger(VistaInicioWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void generarTablaPaquetes(){
       controlador.generarTablaPaquetes();
    }
    private void generarTablaTiposDispositivos(){
        controlador.generarTablaTiposDispositivos();
    }
    @Override
    public void mensajeError(String nombreJSP, String texto) {
        destino = nombreJSP+"?msg=" + texto;
        try{
            response.sendRedirect(destino);
        }catch(IOException ex){
            System.out.println(texto);
        }        
    }

    @Override
    public void mensajeExito(String nombreJSP, String texto) {
        destino = nombreJSP+"?msg=" + texto;
        try{
            response.sendRedirect(destino);
        }catch(IOException ex){
            System.out.println(texto);
        }      
    }
    @Override
    public Operador getOperadorLogueado() {
        return (Operador) sesion.getAttribute("operadorLogueado");
    }
    
    
    @Override
    public void mostrarEstadisticas(ArrayList<Integer> items) {
        
        if(items.size()==4){//compruebo que se hayan cargado todas las estadísticas
            
            for (int i = 0; i < items.size(); i++) {
                
                if(i==0){
                    request.getSession(false).setAttribute("estadisticaClientes", items.get(i));
                }
                else if(i==1){
                    request.getSession(false).setAttribute("estadisticaCuentasSecundarias", items.get(i));
                }
                else if(i==2){
                    request.getSession(false).setAttribute("estadisticaDispositivos", items.get(i));
                }
                else if(i==3){
                    request.getSession(false).setAttribute("estadisticaSuscripciones", items.get(i));
                }
                
                
                
            }
            
                //request.getSession(false).setAttribute("estadisticas", items);//guardo en la session el arraylist conteniendo las estadisticas para agarrarlo en el jsp
                out.write(items.toString());
        }
    }
    @Override
    public void mostrarTablaPaquetes(ArrayList<Paquete> items) {
        try{
            String componente = Funciones.tablaPaquetes("tblPaquetesInicio", items, new Moneda("UYU","Pesos Uruguayos","$"), true);
            out.write(componente + "\n\n");                    
        }catch(ProgramException ex){
            System.out.println("ERROR");
        }
    }

    @Override
    public void mostrarTablaTiposDispositivos(ArrayList<TipoDispositivo> items) {
        try{
            String componente = Funciones.tablaTiposDispositivos("tblTiposDispositivos",items,true);
            out.write(componente + "\n\n");  
        }catch(ProgramException ex){
            System.out.println("ERROR");
        }
    }
    
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/



}




