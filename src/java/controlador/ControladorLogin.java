package controlador;


import Datos.OpEstadisticas;
import controlador.Interfaces.IVistaLogin;
import Datos.OpImagen;
import Datos.OpPersona;
import Datos.OpTipoUsuario;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.util.ArrayList;

public class ControladorLogin {
    /*Estado*/
    private IVistaLogin vista;
    private OpPersona opPersona;
    private OpTipoUsuario opTipoUsuario;
    private OpImagen opImagen;
    
    /*Estado*/
    /*Constructores*/
    public ControladorLogin(IVistaLogin vista){
        this.vista = vista;
        this.opPersona = new OpPersona(new Operador("loginUser", "Bot", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
        this.opTipoUsuario = new OpTipoUsuario(new Operador("loginUser", "Bot", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
        this.opImagen = new OpImagen(new Operador("loginUser", "Bot", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
        
    }
    /*Constructores*/
    
    /*Comportamiento*/
    
    
    
    public void login(String nombreUsuario, String password) {
        
        
        try {//matchea datos correctamente
            Operador operadorLogin = (Operador) opPersona.buscar(" WHERE OperadoresDashboard.usuarioSistema='"+nombreUsuario+"' AND OperadoresDashboard.clave=SHA('"+password+"') " , "Modelo.Operador").get(0);  
            vista.permitirAcceso(operadorLogin); //ver como manejar el acceso dependiendo del tipo de usuario (lista de privilegios)
            
        } catch (Exception ex) { //no encontró datos en BD
            vista.denegarAcceso("Usuario y/o clave incorrectos.");
        }
        //LLEVARME A LA BASE DE DATOS LOS DATOS PARA VER SI COINCIDEN CON LOS INGRESADOS
        //PERMITIR ACCESO Y GUARDAR EL USUARIO EN LA SESSION O DENEGAR Y MOSTRAR MENSAJE
    }
    
    
    
    
    /*Comportamiento*/
    /*Getters y Setters*/
    
    /*Getters y Setters*/

    //cuando el login es exitoso, antes de redirigir al index, llama a este método para crear las estadisticas para mostrar en el index
    public void crearEstadisticas(Operador operadorLogin) throws Exception {
        
        if(operadorLogin!=null){
            OpEstadisticas opEstadisticas = new OpEstadisticas(operadorLogin); //clase de manejo de estadisticas que se muestran en el index 
            
            ArrayList<Integer> listaResultadosEstadisticas = opEstadisticas.getEstadisticasInicioA();
            /*Se generó el OpEstadisticas para hacer estas query, el metodo de la linea anterior retorna un arraylist con los 4 numeros siendo los siguientes cada uno de ellos:
            0 -  Clientes registrados
            1 -  Cuentas secundarias
            2 -  Dispositivos registrados
            3 -  Suscripciones activas
            De acá en adelante hay que ver como meterlo en el frontend.
            */
        
            if(listaResultadosEstadisticas!=null){
                vista.establecerEstadisticasEnSession(listaResultadosEstadisticas);
            }
        }
        
        
    }

    
}
