
package vistaWeb.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vistaWeb.VistaManejoDispositivosWeb;


@WebServlet(name = "ManejoDispositivosServlet", urlPatterns = {"/ManejoDispositivosServlet"})
public class ManejoDispositivosServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        VistaManejoDispositivosWeb vista = new VistaManejoDispositivosWeb(request, response);
        vista.procesarRequest(request, response);
    }
    
    
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        
//        String accion = request.getParameter("accion");
//        if(accion!=null && accion.equals("comboTiposDispositivo")){
//            VistaManejoDispositivosWeb vista = new VistaManejoDispositivosWeb(request, response);
//            request.getSession(true).setAttribute("vistaDispositivo", vista);//la session se debe crear en el login, modificar parametro a false en ese caso
//            vista.procesarRequest(request, response);
//        }else{//ya existe una vista
//            VistaManejoDispositivosWeb vista = (VistaManejoDispositivosWeb) request.getSession(false).getAttribute("vistaDispositivo");
//            vista.procesarRequest(request, response);
//        }
//        
//        
//        
//        
//    }
    
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }

    

}
