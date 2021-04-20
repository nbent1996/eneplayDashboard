package vistaWeb;

import Modelo.Operador;
import controlador.ControladorLogin;
import controlador.Interfaces.IVistaLogin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class VistaLoginWeb implements IVistaLogin{
    
    
    /*Estado*/
    private ControladorLogin controlador;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String destino;
    /*Estado*/
    
    
    /*Constructores*/
    public VistaLoginWeb(HttpServletRequest request, HttpServletResponse response) throws IOException{
        this.response = response;
        this.request = request;
        this.out = response.getWriter();
        this.controlador = new ControladorLogin(this);
    }
    /*Constructores*/
    
    
    /*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response){
        loginUsuarios(request, response);            
    }
    /*Comportamiento*/



    @Override
    public void mostrarError(String textoError) {
        this.out.write(textoError);
    }

    private void loginUsuarios(HttpServletRequest request, HttpServletResponse response) {
        String nombreUsuario = request.getParameter("txtbxUsuario");
        String password = request.getParameter("txtbxPassword");
        
        this.request = request;
        this.response = response;
        
        controlador.login(nombreUsuario, password);
        
    }

    @Override
    public void permitirAcceso(Operador operadorLogin) {
        
        destino = "index.jsp";
        
        request.getSession().removeAttribute("OperadorLogueado");
        request.getSession().invalidate();
        
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("OperadorLogueado", operadorLogin);
        
        try {
            controlador.crearEstadisticas(operadorLogin);//le paso el operador a metodo que se encarga de instanciar el opEstadisticas
        } catch (Exception ex) {
            Logger.getLogger(VistaLoginWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            response.sendRedirect(destino);
        } catch (IOException ex) {
            Logger.getLogger(VistaLoginWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void denegarAcceso(String mensajeError) {
        
        
        destino = "login.jsp?msg=" + mensajeError;
        
        try {
            response.sendRedirect(destino);
        } catch (IOException ex) {
            Logger.getLogger(VistaLoginWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void establecerEstadisticasEnSession(ArrayList<Integer> listaResultadosEstadisticas) {
        if(listaResultadosEstadisticas.size()==4){//compruebo que se hayan cargado todas las estadísticas
            
            for (int i = 0; i < listaResultadosEstadisticas.size(); i++) {
                
                if(i==0){
                    request.getSession(false).setAttribute("estadisticaClientes", listaResultadosEstadisticas.get(i));
                }
                else if(i==1){
                    request.getSession(false).setAttribute("estadisticaCuentasSecundarias", listaResultadosEstadisticas.get(i));
                }
                else if(i==2){
                    request.getSession(false).setAttribute("estadisticaDispositivos", listaResultadosEstadisticas.get(i));
                }
                else if(i==3){
                    request.getSession(false).setAttribute("estadisticaSuscripciones", listaResultadosEstadisticas.get(i));
                }
                
                
                
            }
            
                //request.getSession(false).setAttribute("estadisticas", items);//guardo en la session el arraylist conteniendo las estadisticas para agarrarlo en el jsp
                //out.write(listaResultadosEstadisticas.toString());
            
            
        }
    }
    
}
