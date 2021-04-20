package controlador;

import controlador.Interfaces.IVistaManejoUsuarios;
import Datos.OpEmpresa;
import Datos.OpPais;
import Datos.OpPersona;
import Datos.OpTipoUsuario;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Persona;
import Modelo.ProgramException;
import Modelo.TipoUsuario;
import java.util.ArrayList;




public class ControladorManejoUsuarios{
    /*Estado*/
    private IVistaManejoUsuarios vista;
    private OpPersona opPersona;
    private OpEmpresa opEmpresa;
    private OpPais opPais;
    private OpTipoUsuario opTipoUsuario;
    private Operador userLogueado;
    /*Estado*/
    
    /*Constructores*/
    public ControladorManejoUsuarios(IVistaManejoUsuarios vista) {
        this.vista = vista;
        this.userLogueado = (Operador) vista.getSession().getAttribute("OperadorLogueado");
        this.opPersona = new OpPersona(this.userLogueado);
        this.opPais = new OpPais(this.userLogueado);
        this.opTipoUsuario = new OpTipoUsuario(this.userLogueado);
    }
    /*Constructores*/

    /*Comportamiento*/
    public void altaUsuario(String usuarioAltaUsr, String nombreCompletoAltaUsr, String codPaisAltaUsr, String generoAltaUsr, String tipoUsuarioAltaUsr){
        try {
            Empresa e = new Empresa(userLogueado.getEmpresaAsociada().getIdentificacionTributaria());
            Operador operador = new Operador(usuarioAltaUsr, usuarioAltaUsr,nombreCompletoAltaUsr, e,new Pais(codPaisAltaUsr), new TipoUsuario(tipoUsuarioAltaUsr), generoAltaUsr );
            operador.validar();
            opPersona.guardar(null, operador);
            vista.mensajeAltaUsuarioOK("Usuario dado de alta correctamente");
            //vista.mensajeExito("usuario_Alta.jsp","Usuario dado de alta correctamente");
        } catch (ProgramException ex) { 
            vista.mensajeAltaUsuarioError(ex.getMessage());
            //vista.mensajeError("usuario_Alta.jsp",ex.getMessage()); 
        } catch (Exception ex) {      
            vista.mensajeAltaUsuarioError("Ocurrió un error, póngase en contacto con el administrador");
            //vista.mensajeError("usuario_Alta.jsp","Error al dar de alta el usuario"); 
        }
    }
    
    //se borran los usuarios seleccionados de los checkboxes
    public void borrarUsuariosSeleccionados(String[] listaNombresDeUsuarios) { //ACÁ ME LLEGA LA LISTA DE NOMBRES DE USUARIO QUE SE SELECCIONARON EN LOS CHECKBOXES

        if(!listaNombresDeUsuarios[0].equals("")){ //se seleccionó al menos un usuario para borrar
            //en el frontend tira todos los nombres de usuarios de los check en la posición [0], por eso convierto a string y luego a array para poder recorrer
            String cadena = listaNombresDeUsuarios[0].toString();
            String[]cadenaConvertida = cadena.split(",");
            for (String nombreUsuario : cadenaConvertida) { //recorro cada nombre de usuario, me traigo la persona que tiene ese usuario y lo borro                               
                try{
                    Persona personaBuscada = opPersona.buscar(" WHERE OperadoresDashboard.usuarioSistema='" + nombreUsuario + "' ", "Modelo.Operador").get(0);
                    opPersona.borrar(personaBuscada);
                }catch(Exception ex){
                    vista.mensajeErrorBajaUsuarios("Ocurrió un error al borrar el usuario");
                    System.out.println(ex.getMessage());
                }
  
            }
            vista.mostrarMensajeExitoPersonaBorrada("Se eliminaron los usuarios: " + cadena); //devuelvo cadena que es el string que tiene los nombres de usuarios a borrar
        }else{
            vista.mensajeNoSeleccionasteUsuarios("Debes seleccionar al menos un usuario para borrar");
        }
        
    }
    //cuando filtro por nombre de usuario o nombre completo muestro tabla solamente con esos datos
    public void mostrarUsuariosFiltradosTabla(String nombreUsuarioBaja, String nombreCompletoUsuarioBaja) {
        ArrayList<Persona> listaUsuarios = new ArrayList();
            try{
            listaUsuarios = opPersona.buscar(" WHERE OperadoresDashboard.usuarioSistema like '%"+nombreUsuarioBaja+"%' AND Personas.nombreCompleto LIKE '%"+nombreCompletoUsuarioBaja+"%' " , "Modelo.Operador");
            vista.mostrarTablaConUsuariosABorrar(listaUsuarios);
            } catch (Exception ex) {
                vista.mensajeError("usuario_BajaModificacion.jsp","Error al dar de baja el usuario");
            }
        }
    
    public void modificarUsuario(String usuarioModUsr, String nombreCompletoModUsr, String nombreEmpresaModUsr, String nombrePaisModUsr, String passwordModUsr) {
        
        //CONTINUAR ACÁ, EVALUAR SITUACION YA QUE ALGUNOS DE LOS PARAMETROS PUEDEN SER NULOS, VER DONDE EVALUAR DICHA CONDICION
   
    }
   
    public void cargarTiposUsuario() {
        try {
            vista.mostrarTiposUsuario(opTipoUsuario.obtenerTodos());
        } catch (Exception ex) {
            vista.mensajeError("usuario_Alta.jsp","Se ha producido un error");
        }
    }

    public void cargarPaises() {
        try {
            vista.mostrarPaises(opPais.obtenerTodos());
        } catch (Exception ex) {
            //vista.mensajeError("usuario_Alta.jsp","Error en la carga de paises");// si devuelvo un mensaje en el mismo span que si hay error en la carga de tipos de usuario se duplica vista
        }
    }

    public void cargarTablaUsuariosBajaInicio() {
        try {
            vista.mostrarTablaUsuariosBaja(opPersona.buscar(null, "Modelo.Operador"));
        } catch (Exception ex) {
            vista.mensajeError("usuario_Baja.jsp","Error en la carga de usuarios"); //reuso el método de error del clic del botón buscar
        }
    }    
}
