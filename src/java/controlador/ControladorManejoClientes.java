package controlador;

import Datos.OpEmpresa;
import Datos.OpPais;
import Datos.OpPersona;
import Datos.OpTipoDocumento;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Persona;
import Modelo.Principal;
import Modelo.ProgramException;
import Modelo.Secundario;
import Modelo.TipoDocumento;
import controlador.Interfaces.IVistaManejoClientes;
import java.util.ArrayList;

public class ControladorManejoClientes {
    /*Estado*/
    private IVistaManejoClientes vista;
    private OpPersona opPersona;
    private OpEmpresa opEmpresa;
    private OpPais opPais;
    private OpTipoDocumento opTipoDocumento;
    private Operador userLogueado;
    /*Estado*/
    /*Constructores*/
    public ControladorManejoClientes(IVistaManejoClientes vista){
        this.vista = vista;
        this.userLogueado = (Operador) vista.getSession().getAttribute("OperadorLogueado");
        this.opPersona = new OpPersona(this.userLogueado);
        this.opEmpresa = new OpEmpresa(this.userLogueado);
        this.opPais = new OpPais(this.userLogueado);
        this.opTipoDocumento = new OpTipoDocumento(this.userLogueado);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public void altaPrincipal(String nroDocumento, String nombreCompleto, String codPais, String email, String telefono, boolean servicioActivo, String tipoDocumento) {
        try {
            Empresa e = new Empresa(userLogueado.getEmpresaAsociada().getIdentificacionTributaria());
            Principal p = new Principal("", nombreCompleto, e, new Pais(codPais), -1, email, nroDocumento, servicioActivo, new TipoDocumento(tipoDocumento), telefono);
            p.validar();
            opPersona.guardar(null, p);
            //vista.mensajeExito("cliente_Alta.jsp", "Cliente del tipo titular dado de alta correctamente.");
            vista.mensajeAltaClienteOK("Cliente titular dado de alta correctamente");
        } catch (ProgramException ex) {
            vista.mensajeAltaClienteError(ex.getMessage());
        } catch (Exception ex) {
            vista.mensajeAltaClienteError("Ocurrió un error, póngase en contacto con el administrador");
        }
    }
    
    public void altaSecundario(String nombreCompleto, String codPais, String email, String telefono, String nroDocumento, String nroDocumentoPrincipal) {
        try {
            Empresa e = new Empresa(userLogueado.getEmpresaAsociada().getIdentificacionTributaria());
            Secundario s = new Secundario("", nombreCompleto, e, new Pais(codPais), -1, email, new Principal(nroDocumentoPrincipal), telefono);
            s.validar();
            opPersona.guardar(null, s);
            //vista.mensajeExito("cliente_Alta.jsp", "Cliente del tipo cuenta secundaria dado de alta correctamente.");
            vista.mensajeAltaClienteOK("Cliente secundario dado de alta correctamente");
        } catch (ProgramException ex) {
            vista.mensajeAltaClienteError(ex.getMessage());
        } catch (Exception ex) {
            vista.mensajeAltaClienteError("Ocurrió un error, póngase en contacto con el administrador");
        }
    }
  public void cargarPaises() {
      
        ArrayList<Pais> listaPaises = new ArrayList();
      
        try {
            listaPaises = opPais.obtenerTodos();
            vista.mostrarPaises(listaPaises);
        } catch (Exception ex) {
            //vista.mensajeError("cliente_Alta.jsp", "Error en la carga de paises");// si devuelvo un mensaje en el mismo span que si hay error en la carga de tipos de documento se duplica vista
        }
    }
  public void cargarTiposDocumento(){
      
        ArrayList<TipoDocumento> listaTiposDocumento = new ArrayList();
      
        try {
            listaTiposDocumento = opTipoDocumento.obtenerTodos();
            vista.mostrarTiposDocumento(listaTiposDocumento);
        } catch (Exception ex) {
            //vista.mensajeError("cliente_Alta.jsp", "Se ha producido un error");
        }
  }
  public void generarUsuarioSistema(){
        try {
            String usr = opPersona.getNuevoUsuarioSistema();
            vista.mostrarUsuarioSistema(usr);
        } catch (Exception ex) {
            vista.mensajeError("cliente_Alta.jsp", "Error al generar usuario de sistema del cliente.");
        }
  }
  
    /*Comportamiento*/
    
    /*Getters y Setters*/
    
    /*Getters y Setters*/

    public void cargarTablaClientesBajaInicio() {
        //traerme todos los principales y los secundarios y devolverlos en un array de personas
        ArrayList<Persona> principalesYSecundarios = new ArrayList();
        
        try {
            principalesYSecundarios.addAll(opPersona.buscar(null, "Modelo.Principal"));
            principalesYSecundarios.addAll(opPersona.buscar(null, "Modelo.Secundario"));
            vista.mostrarTablaClientesBajaInicio(principalesYSecundarios);
        } catch (Exception ex) {
            vista.mensajeError("cliente_Baja.jsp", "Error en la carga de clientes");
        }
        
    }

    //muestro tabla solamente con los clientes segun filtros ingresados
    public void mostrarClientesFiltradosTabla(int nroClienteBaja, String emailClienteBaja, String nombreCompletoClienteBaja) {        

        ArrayList<Persona> listaClientes = new ArrayList();
        
        try {
            
            if(nroClienteBaja == -1){//quiere decir que no se ingresó un nro cliente en el filtro
                listaClientes = opPersona.buscar(" WHERE Personas.nombreCompleto LIKE '%"+nombreCompletoClienteBaja+"%' AND Clientes.email LIKE '%"+emailClienteBaja+"%' " , "Modelo.Principal");
                listaClientes.addAll(opPersona.buscar(" WHERE Personas.nombreCompleto LIKE '%"+nombreCompletoClienteBaja+"%' AND Clientes.email LIKE '%"+emailClienteBaja+"%' " , "Modelo.Secundario"));
                vista.mostrarTablaClientesBajaInicio(listaClientes);//ver de reusar siempre el mismo metodo cuando se quiere mostrar tabla
            }else{
                listaClientes = opPersona.buscar(" WHERE Clientes.nroCliente like '%"+nroClienteBaja+"%' AND Personas.nombreCompleto LIKE '%"+nombreCompletoClienteBaja+"%' AND Clientes.email LIKE '%"+emailClienteBaja+"%' " , "Modelo.Principal");
                listaClientes.addAll(opPersona.buscar(" WHERE Clientes.nroCliente like '%"+nroClienteBaja+"%' AND Personas.nombreCompleto LIKE '%"+nombreCompletoClienteBaja+"%' AND Clientes.email LIKE '%"+emailClienteBaja+"%' " , "Modelo.Secundario"));
                vista.mostrarTablaClientesBajaInicio(listaClientes);//ver de reusar siempre el mismo metodo cuando se quiere mostrar tabla
                //ver acá que si el array de clientes es vacio devolver mensaje de que no se encontraron en vez de mostrar tabla vacia
            }
            
        } catch (Exception ex) {
            vista.mensajeError("cliente_Baja.jsp", "Error al dar de baja el cliente");
        }
        
        
    }

    //Borro los clientes seleccionados de los checkboxes (pueden ser principales y/o secundarios)
    public void borrarClientesSeleccionados(String[] listaNombresDeUsuariosDeClientes) {

        int cantCliBorrados = 0;
        
        if (!listaNombresDeUsuariosDeClientes[0].equals("")){ //se seleccionó al menos un cliente para borrar
            //en el frontend tira todos los nombres de usuarios de los check en la posición [0], por eso convierto a string y luego a array para poder recorrer
            String cadena = listaNombresDeUsuariosDeClientes[0].toString();
            String[] cadenaConvertida = cadena.split(",");

            for (String nombreUsuarioCli : cadenaConvertida) {                
                try {                    
                    opPersona.borrar(new Secundario(nombreUsuarioCli));//tanto secundarios como principales
                    cantCliBorrados+=1;
                } catch (Exception ex) {
                    vista.mensajeErrorBajaClientes("Ocurrió un error al borrar el cliente");                    
                }

            }
            vista.mostrarMensajeExitoClienteBorrado("Se eliminaron" + " " + cantCliBorrados + " " + "clientes."); 
        } else {
            vista.mensajeNoSeleccionasteClientes("Debes seleccionar al menos un cliente para borrar");
        }
  
    }
    
    
    
    
    
}
