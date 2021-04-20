package controlador;

import Datos.OpCategoria;
import Datos.OpDispositivo;
import Datos.OpPersona;
import Datos.OpTipoDispositivo;
import Modelo.Dispositivo;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Principal;
import Modelo.ProgramException;
import Modelo.TipoDispositivo;
import controlador.Interfaces.IVistaManejoDispositivos;

public class ControladorManejoDispositivos{
    /*Estado*/
    private IVistaManejoDispositivos vista;
    private OpDispositivo opDispositivo;
    private OpTipoDispositivo opTipoDispositivo;
    private OpCategoria opCategoria;
    private OpPersona opPersona;
    private Operador userLogueado;
    //private Principal clienteEncontradoAltaDispositivo;
    /*Estado*/
    
    /*Constructores*/
    public ControladorManejoDispositivos(IVistaManejoDispositivos vista) {
        this.vista = vista;
        this.userLogueado = (Operador) vista.getSession().getAttribute("OperadorLogueado");
        this.opDispositivo = new OpDispositivo(this.userLogueado);
        this.opTipoDispositivo = new OpTipoDispositivo(this.userLogueado);
        this.opCategoria = new OpCategoria(this.userLogueado);
        this.opPersona = new OpPersona(this.userLogueado);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    
    
    public void buscarClienteParaAsociarDispositivo(String nroDocCliente) {
        
        if(nroDocCliente.isEmpty()){
            vista.noSeIngresoDocumentoCliente("No se ingresó un número de documento para buscar");
        }else{
            try {
                Principal p = (Principal) opPersona.buscar(" WHERE Principales.nroDocumento='"+nroDocCliente+"' ","Modelo.Principal").get(0);
                
                vista.mostrarClienteEncontradoAltaDisp(p);//mando el cliente para mostrar el nombre completo y guardar en la session el objeto cliente y despues asociarlo con el dispositivo a dar de alta

            } catch (Exception ex) {
                vista.errorBuscarCliente("No se encontró un cliente con el documento ingresado");
            }
            
        }
        
        
    }
    
    //el cliente principal puede ser nulo ya que se puede dar de alta un dispositivo sin cliente asociado
    public void altaDispositivo(String nroSerie, String estado, String tipoDispositivo, Principal clientePrincipalSeleccionado){
        try{
            Empresa e = new Empresa(userLogueado.getEmpresaAsociada().getIdentificacionTributaria());
            TipoDispositivo tD = opTipoDispositivo.buscar(" WHERE TiposDispositivos.modelo='"+tipoDispositivo+"' ",null).get(0);
            Dispositivo d = new Dispositivo(nroSerie, estado, tD, e, clientePrincipalSeleccionado);
            d.validar();
            opDispositivo.guardar(null,d);
            vista.mensajeAltaDispositivoOk("Dispositivo dado de alta correctamente.");
        } catch (ProgramException ex) {
            vista.mensajeErrorValidacionesAltaDispositivo(ex.getMessage());
        } catch (Exception ex) {
            vista.mensajeErrorSqlAltaDispositivo("Ocurrió un error al dar de alta el dispositivo");
        }
    }
    public String getFiltroProcesado(String nroSerie, String estado){
        String filtro = " WHERE ";
       
        filtro+=" dis.nroSerie LIKE '%"+nroSerie+"%' AND ";
        filtro+=" dis.estado LIKE '%"+estado+"%' ";
        
        if(filtro.endsWith("AND ")){
            filtro = filtro.substring(0, filtro.length()-5);
        }
        if(filtro.equals(" WHERE ")){
            filtro=null;
        }

        return filtro;
    }
    public void generarTablaDispositivos(String filtro){
        try{
            vista.generarTablaDispositivos("tblDispositivosDispositivoBaja", opDispositivo.buscar(filtro, null));
        }catch(Exception ex){
            vista.mensajeError("dispositivo_BajaModificacion.jsp", "Error al generar la tabla de dispositivos.");
        }
    }
        public void cargarTiposDispositivos() {
            try{
                //vista.mostrarTiposDispositivos(opTipoDispositivo.buscar(" WHERE nombreCategoria='"+categoria+"' ",null));
                vista.mostrarTiposDispositivos(opTipoDispositivo.obtenerTodos());
            }catch(Exception ex){
                //vista.mensajeError("dispositivo_Alta.jsp","Error en la carga de tipos de dispositivos.");
            }
        }
        public void cargarCategorias(){
            try{
                vista.mostrarCategorias(opCategoria.obtenerTodos());
            }catch(Exception ex){
                vista.mensajeError("dispositivo_Alta.jsp","Error en la carga de categorias.");
            }
        }
    /*Comportamiento*/

    public void borrarDispositivosSeleccionados(String[] listaNroSerieDispositivos) {
        
        String nrosSerieBorrados = "";
        
        if (!listaNroSerieDispositivos[0].equals("")) { //se seleccionó al menos un dispositivo para borrar
            //en el frontend tira todos los nro de serie de los check en la posición [0], por eso convierto a string y luego a array para poder recorrer
            String cadena = listaNroSerieDispositivos[0].toString();
            String[] cadenaConvertida = cadena.split(",");

            for (String nroSerieDisp : cadenaConvertida) {                
                try {          
                    opDispositivo.borrar(new Dispositivo(nroSerieDisp));
                    nrosSerieBorrados+= nroSerieDisp + "- ";
                } catch (Exception ex) {
                    vista.mensajeErrorBajaDispositivos("Ocurrió un error al borrar el dispositivo");                    
                }

            }
            vista.mostrarMensajeExitoDispositivoBorrado("Se eliminaron los dispositivos: " + " " + nrosSerieBorrados); 
        } else {
            vista.mensajeNoSeleccionasteDispositivos("Debes seleccionar al menos un dispositivo para borrar");
        }
        
    }

    
    
    
    
    
}
