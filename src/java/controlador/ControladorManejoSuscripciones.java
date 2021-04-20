package controlador;

import Datos.OpMoneda;
import Datos.OpPaquete;
import Datos.OpPersona;
import Datos.OpSuscripcion;
import Datos.OpTipoDispositivo;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.Principal;
import Modelo.ProgramException;
import Modelo.Suscripcion;
import Resources.DTOs.DTOFechas;
import Resources.DTOs.Fecha;
import controlador.Interfaces.IVistaManejoSuscripciones;
import java.util.ArrayList;

public class ControladorManejoSuscripciones {
    /*Estado*/
    private IVistaManejoSuscripciones vista;
    private OpSuscripcion opSuscripcion;
    private OpPaquete opPaquete;
    private OpTipoDispositivo opTipoDispositivo;
    private OpMoneda opMoneda;
    private OpPersona opPersona;
    private Operador userLogueado;
    /*Estado*/
    
    /*Constructores*/
    public ControladorManejoSuscripciones(IVistaManejoSuscripciones vista){
        this.vista = vista;
        this.userLogueado = (Operador) vista.getSession().getAttribute("OperadorLogueado");
        this.opPaquete = new OpPaquete(this.userLogueado);
        this.opTipoDispositivo = new OpTipoDispositivo(this.userLogueado);
        this.opSuscripcion = new OpSuscripcion(this.userLogueado);
        this.opMoneda = new OpMoneda(this.userLogueado);
        this.opPersona = new OpPersona(this.userLogueado);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public String getFiltroProcesado(int idSuscripcion, String fechaInicioAStr, String fechaFinAStr, String fechaInicioBStr, String fechaFinBStr, String activaStr, String tiempoContratoStr){
        String retorno = " WHERE ";
        DTOFechas fechasInicio, fechasFin;
        if(!fechaInicioAStr.equals("") && !fechaInicioBStr.equals("")){
            fechasInicio = new DTOFechas(new Fecha(fechaInicioAStr), new Fecha(fechaInicioBStr));
            retorno += " Suscripciones.fechaInicio BETWEEN '"+fechasInicio.getFechaAStr(1)+"' AND '"+fechasInicio.getFechaBStr(1)+"' AND ";
        }
        if(!fechaFinAStr.equals("") && !fechaFinBStr.equals("")){
            fechasFin = new DTOFechas(new Fecha(fechaFinAStr), new Fecha(fechaFinBStr));
            retorno+=" Suscripciones.fechaFin BETWEEN '"+fechasFin.getFechaAStr(1)+"' AND '"+fechasFin.getFechaBStr(1)+"' AND ";
        }
        if(idSuscripcion!=-1){
            retorno+= " Suscripciones.idSuscripcion='"+idSuscripcion+"' AND ";
        }
        if(!tiempoContratoStr.equals("")){
            retorno+= " Suscripciones.tiempoContrato = '"+tiempoContratoStr+"' AND ";
        }
        if(!activaStr.equals("")){
            retorno+= " Suscripciones.activa = '"+activaStr+"' "; 
        }
        
        if(retorno.endsWith("AND ")){
            retorno = retorno.substring(0, retorno.length()-5);
        }
        if(retorno.equals(" WHERE ")){
            retorno = null;
        }
        
        return retorno;
    }
    
    public void generarTablaSuscripciones(String filtro){
        try{            
            if(filtro!=null){//Se seleccionó al menos un filtro
                ArrayList<Suscripcion> suscripcionesFiltradas = new ArrayList();
                suscripcionesFiltradas = opSuscripcion.buscar(filtro, null);
                vista.generarTablaSuscripciones("tblSuscripcionesSuscripcionBaja", suscripcionesFiltradas);
            }else{//filtro es nulo si no hay filtros seleccionados
                vista.generarTablaSuscripciones("tblSuscripcionesSuscripcionBaja", opSuscripcion.obtenerTodos());
            }
        }catch(Exception ex){
            //vista.mensajeError("suscripcion_BajaModificacion.jsp","Error al generar la tabla de suscripciones.");
        }
    }
    
    public void generarTablaPaquetes(){
        try{
            vista.generarTablaPaquetes("tblPaquetesSuscripcionAlta", opPaquete.obtenerTodos(), new Moneda("UYU","Pesos Uruguayos","$")); //MONEDA HARDCODEADA, OBTENERLA DESDE LA IDENTIFICACION TRIBUTARIA DE LA SESSION
        }catch(Exception ex){
            //vista.mensajeError("suscripcion_Alta.jsp","Error al generar la tabla de paquetes de dispositivos.");
        }
    }
    /*Comportamiento*/

    public void borrarSuscripcionesSeleccionadas(String[] listaIdSuscripciones) {
        
        int cantSuscripcionesBorradas = 0;
        
        if (!listaIdSuscripciones[0].equals("")){ //se seleccionó al menos un cliente para borrar
            //en el frontend tira todos los nombres de usuarios de los check en la posición [0], por eso convierto a string y luego a array para poder recorrer
            String cadena = listaIdSuscripciones[0].toString();
            String[] cadenaConvertida = cadena.split(",");

            for (String unIdSuscripcion : cadenaConvertida) {                
                try {
                    Suscripcion suscripcionBuscada = opSuscripcion.buscar(" WHERE Suscripciones.idSuscripcion='" + unIdSuscripcion + "' " , null).get(0);
                    opSuscripcion.borrar(suscripcionBuscada);
                    //opPersona.borrar(new Secundario(nombreUsuarioCli));//tanto secundarios como principales
                    cantSuscripcionesBorradas+=1;
                } catch (Exception ex) {
                    vista.mensajeErrorBajaSuscripciones("Ocurrió un error al borrar la suscripción");                    
                }

            }
            vista.mostrarMensajeExitoSuscripcionBorrada("Se eliminaron" + " " + cantSuscripcionesBorradas + " " + "suscripciones."); 
        } else {
            vista.mensajeNoSeleccionasteSuscripciones("Debes seleccionar al menos una suscripción para borrar");
        }

    }

    public void buscarClienteParaAsociarSuscripcion(String nroDocCliente) {
        
        if(nroDocCliente.isEmpty()){
            vista.noSeIngresoDocumentoCliente("No se ingresó un número de documento para buscar");
        }else{
            try {
                Principal p = (Principal) opPersona.buscar(" WHERE Principales.nroDocumento='"+nroDocCliente+"' ","Modelo.Principal").get(0);
                
                //mando el cliente para mostrar el nombre completo y guardar en la session el objeto cliente
                //para despues asociarlo con la suscripcion
                vista.mostrarClienteEncontradoAltaSuscripcion(p);
                
            } catch (Exception ex) {
                vista.errorBuscarCliente("No se encontró un cliente con el documento ingresado");
            }
            
        }
  
    }
    
    //Puede venir el clienteSeleccionado como null
    public void altaSuscripcionConPaquetes(String[] listaIdPaquetes, DTOFechas fechaInicioSuscripcion, String tiempoContrato, Principal clienteSeleccionado) {
        
        //es obligatorio tener un cliente para asociar (está en la session)
        //calcular la fecha de finalizacion del contrato de acuerdo al tiempo del contrato
        
        ArrayList<Paquete> listaPaquetes = new ArrayList();
        
        if(clienteSeleccionado!=null){
                   
            if(!listaIdPaquetes[0].equals("")){//se seleccionó al menos un paquete

                String cadenaIdPaquetes = listaIdPaquetes[0].toString();
                String[] cadenaIdPaquetesConvertida = cadenaIdPaquetes.split(",");

                
                int duracionContrato = Integer.parseInt(tiempoContrato);
                Fecha fechaInicio = fechaInicioSuscripcion.getFechaA();
                int anioVencimientoSuscripcion = fechaInicio.getYear() + duracionContrato;
                
                int diaVencimientoSuscripcion = fechaInicio.getDay();
                int mesVencimientoSuscripcion = fechaInicio.getMonth();
                
                Fecha fechaFin = new Fecha(diaVencimientoSuscripcion, mesVencimientoSuscripcion, anioVencimientoSuscripcion);
                DTOFechas fechaFinSuscripcion = new DTOFechas(fechaFin);
                
                for (String unId : cadenaIdPaquetesConvertida) {

                    try {
                        Paquete unPaquete = opPaquete.buscar(" WHERE Paquetes.idPaquete='"+ unId + "' ", null).get(0);                        
                        listaPaquetes.add(unPaquete);                        
                    } catch (Exception ex) {
                        //error en consultas sql
                        vista.errorSqlPaquetesSeleccionadosAltaSuscripcion("Ocurrió un error al obtener los paquetes seleccionados");
                    }
                }

                try {
                    //AGREGADO EL CLIENTE PRINCIPAL AL CONSTRUCTOR
                    Suscripcion nuevaSuscripcion = new Suscripcion(fechaInicioSuscripcion, (float)duracionContrato, fechaFinSuscripcion, true, listaPaquetes, clienteSeleccionado, userLogueado.getEmpresaAsociada());
                    nuevaSuscripcion.validar();
                    opSuscripcion.guardar(null, nuevaSuscripcion);
                    vista.exitoAlCrearSuscripcion("Suscripción dada de alta correctamente");
                } catch (ProgramException ex) {//error en validaciones de la suscripcion
                    vista.errorValidacionesDeSuscripcion(ex.getMessage());
                } catch (Exception exe) {//error sql al guardar la suscripcion
                    vista.errorSqlInsertarSuscripcion("Ocurrió un error al dar de alta la suscripción");
                }
                
            }else{//no se seleccionó un paquete
                vista.mensajeSeleccionarPaquetesAltaSuscripcion("Debes seleccionar al menos un paquete");
            }  
        }else{//no seleccionó un cliente
            vista.mensajeSeleccionarClienteAltaSuscripcion("Debes seleccionar un cliente");
        }
    }
    
    
    
    
    
    
    
}
