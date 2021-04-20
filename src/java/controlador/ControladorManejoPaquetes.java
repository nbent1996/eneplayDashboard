package controlador;

import Datos.OpPaquete;
import Datos.OpTipoDispositivo;
import Modelo.Empresa;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.ProgramException;
import Modelo.TieneTP;
import Modelo.TipoDispositivo;
import Resources.DTOs.DTORangoNumerosStr;
import controlador.Interfaces.IVistaManejoPaquetes;
import java.util.ArrayList;

public class ControladorManejoPaquetes {
    /*Estado*/
    private IVistaManejoPaquetes vista;
    private OpPaquete opPaquete;
    private OpTipoDispositivo opTipoDispositivo;
    private Operador userLogueado;
    /*Estado*/
    
    /*Constructores*/
    public ControladorManejoPaquetes(IVistaManejoPaquetes vista){
        this.vista = vista;
        this.userLogueado = (Operador) vista.getSession().getAttribute("OperadorLogueado");
        this.opPaquete = new OpPaquete(this.userLogueado);
        this.opTipoDispositivo = new OpTipoDispositivo(this.userLogueado);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public String getFiltroProcesado(int idPaquete, String nombre, String costoA, String costoB){
        String filtro = " WHERE ";
        DTORangoNumerosStr rango = new DTORangoNumerosStr(costoA, costoB);
        if(idPaquete!=-1){//si se ingresó un idPaquete en el filtro
            filtro+=" Paquetes.idPaquete='"+idPaquete+"' AND ";
        }
        filtro+=" Paquetes.nombrePaquete LIKE '%"+nombre+"%' AND ";
        if(rango.esRango()){//si se ingresaron costoA y costoB
            filtro+=" Paquetes.costoBruto BETWEEN '"+costoA+"' AND '"+costoB+"' AND ";
        }
        
        if(filtro.endsWith("AND ")){
            filtro = filtro.substring(0, filtro.length()-5);
        }
        if(filtro.equals(" WHERE ")){
            filtro=null;
        }
        return filtro;
    }
    
    public void generarTablaTiposDispositivos(){
        try{
            vista.generarTablaTiposDispositivos("tblTiposDispositivosPaqueteAlta", opTipoDispositivo.obtenerTodos());
        }catch(Exception ex){
            vista.mensajeError("paquete_Alta.jsp", "Error al generar la tabla de tipos de dispositivos.\n");
        }
    }
    public void generarTablaPaquetes(String filtro){
        ArrayList<Paquete> paquetesFiltrados = new ArrayList();
        try{
            paquetesFiltrados = opPaquete.buscar(filtro, null);
            vista.generarTablaPaquetes("tblPaquetesPaqueteBaja", paquetesFiltrados, new Moneda("UYU","Pesos Uruguayos","$")); //MONEDA HARDCODEADA, OBTENERLA DESDE LA IDENTIFICACION TRIBUTARIA DE LA SESSION
        }catch(Exception ex){
            vista.mensajeError("paquete_BajaModificacion.jsp","Error al generar la tabla de paquetes de dispositivos.");
        }
    }
    /*Comportamiento*/

    public void borrarPaquetesSeleccionados(String[] listaIdPaquetes) {
        
        String nombresPaqBorrados = "";
        
        if(!listaIdPaquetes[0].equals("")){ //se seleccionó al menos un paquete para borrar
            //en el frontend tira todos los id de paquetes de los check en la posición [0], por eso convierto a string y luego a array para poder recorrer
            String cadena = listaIdPaquetes[0].toString();
            String[]cadenaConvertida = cadena.split(",");
            for (String idP : cadenaConvertida) {                               
                try{
                    Paquete paqueteABorrar = opPaquete.buscar(" WHERE Paquetes.idPaquete='" + idP + "' ", null).get(0);
                    opPaquete.borrar(paqueteABorrar);
                    nombresPaqBorrados+=paqueteABorrar.getNombre() + "  -";
                }catch(Exception ex){
                    vista.mensajeErrorBajaPaquetes("Ocurrió un error al borrar el paquete");
                }
  
            }
            vista.mostrarMensajeExitoPaqueteBorrado("Se eliminaron los paquetes: " + nombresPaqBorrados);
        }else{
            vista.mensajeNoSeleccionastePaquetes("Debes seleccionar al menos un paquete para borrar");
        }
    }
    
    public void altaPaqueteConDispositivos(String[] listaIdDispositivos, String[] listaCantidades, String nombrePaquete, String costoBrutoPaquete) {
            String cadenaIdDispositivos = listaIdDispositivos[0].toString();
            String[] cadenaIdDispositivosConvertida = cadenaIdDispositivos.split(",");
            
            String cadenaCantidadesDispositivos = listaCantidades[0].toString();
            String[] cadenaCantidadesDispositivosConvertida = cadenaCantidadesDispositivos.split(",");

            if(cadenaIdDispositivosConvertida.length == cadenaCantidadesDispositivosConvertida.length){//verifico que tengan el mismo largo
                ArrayList<TieneTP> listaTieneTPDePaqueteACrear = new ArrayList();
                int contador = 0;
                
                for (String unIdTipoDisp : cadenaIdDispositivosConvertida){
                    try {
                        TipoDispositivo tipoDisp = opTipoDispositivo.buscar(" WHERE TiposDispositivos.idTipoDispositivo='" + unIdTipoDisp + "' ", null).get(0);
                        
                            for (int i = contador; i < cadenaCantidadesDispositivosConvertida.length; i++) {
                                int cantConvertidaAInt = Integer.valueOf(cadenaCantidadesDispositivosConvertida[i]);//convierto a entero ya que tengo que pasarle un int en el constructor de tieneTP
                                TieneTP tieneTp = new TieneTP(cantConvertidaAInt, tipoDisp);
                                tieneTp.validar();//valida que la cantidad sea mayor a cero
                                listaTieneTPDePaqueteACrear.add(tieneTp);
                                contador++;
                                break;//para que vuelva al for principal
                            }
                    } catch (ProgramException exc){ //error del validar de TieneTP
                        vista.errorEnValidacionesAltaPaquete(exc.getMessage());
                        return;
                    } catch (Exception ex){ //error de la consulta SQL al traerme el tipo de dispositivo
                        vista.errorEnBaseDeDatosAltaPaquete("Ocurrió un error al dar de alta el paquete");
                        return;
                    }     
                }         
                try {//guardo en la base el paquete
                    Empresa empresaAsociada = new Empresa("526283747346"); //SE DEBE TRAER LA EMPRESA DE LA SESSION
                    float costoBrutoConvertido = Float.valueOf(costoBrutoPaquete);//convierto a float por constructor de Paquete
                    Paquete paqACrear = new Paquete(costoBrutoConvertido, nombrePaquete, empresaAsociada, listaTieneTPDePaqueteACrear);
                    paqACrear.validar();
                    opPaquete.guardar(null, paqACrear);
                    vista.exitoAlGuardarPaquete("Paquete dado de alta correctamente");
                } catch (ProgramException exc) { //error al validar el paquete
                    vista.errorEnValidacionesAltaPaquete(exc.getMessage());
                } catch (Exception ex) { //error al insertar en paquete en la bd
                    vista.errorEnBaseDeDatosAltaPaquete("Ocurrió un error al dar de alta el paquete");
                }               
            }else{//las dos listas no tienen el mismo largo, puedo mostrar error directamente
                vista.errorLargoListasCantidadYDispositivosAltaPaquete("Debe ingresar una cantidad para cada dispositivo seleccionado");
            }  
    }

    public void cargarTablaPaquetesBajaInicio() {
        ArrayList<Paquete> paquetes = new ArrayList(); 
        try {
            paquetes = opPaquete.obtenerTodos();
            vista.generarTablaPaquetes("tblPaquetesPaqueteBaja", paquetes, new Moneda("UYU","Pesos Uruguayos","$")); //MONEDA HARDCODEADA, OBTENERLA DESDE LA IDENTIFICACION TRIBUTARIA DE LA SESSION
        } catch (Exception ex) {
            vista.mensajeError("paquetes_BajaModificacion.jsp", "Error en la carga de paquetes");
        }
    }

}
