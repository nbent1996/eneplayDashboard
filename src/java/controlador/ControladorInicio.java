package controlador;

import Datos.OpEstadisticas;
import Datos.OpImagen;
import Datos.OpPaquete;
import Datos.OpPersona;
import Datos.OpTipoDispositivo;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.TipoDispositivo;
import controlador.Interfaces.IVistaInicio;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorInicio {
/*Estado*/
private IVistaInicio vista;
private OpImagen opImagen;
private OpPersona opPersona;
private OpEstadisticas opEstadisticas;
private OpTipoDispositivo opTipoDispositivo;
private OpPaquete opPaquete;

/*Estado*/

/*Constructores*/
public ControladorInicio(IVistaInicio vista, Operador operadorLogueado){
    this.vista = vista;
    this.opImagen = new OpImagen(operadorLogueado);
    this.opPersona = new OpPersona(operadorLogueado);
    this.opTipoDispositivo = new OpTipoDispositivo(operadorLogueado);
    this.opPaquete = new OpPaquete(operadorLogueado);
    //this.opEstadisticas = new OpEstadisticas(operadorLogueado);
}
/*Constructores*/

/*Comportamiento*/
    public void obtenerEstadisticasA() throws Exception, SQLException{ /*Estadisticas para los 4 recuadros celestes*/
        ArrayList<Integer> lista = opEstadisticas.getEstadisticasInicioA();
        /*Se generó el OpEstadisticas para hacer estas query, el metodo de la linea anterior retorna un arraylist con los 4 numeros siendo los siguientes cada uno de ellos:
            0 -  Clientes registrados
            1 -  Cuentas secundarias
            2 -  Dispositivos registrados
            3 -  Suscripciones activas
        De acá en adelante hay que ver como meterlo en el frontend.
        */
        
        if(lista!=null){
            vista.mostrarEstadisticas(lista);
        }
        
    }
    public void generarTablaTiposDispositivos() {
        try{
        ArrayList<TipoDispositivo> items = opTipoDispositivo.buscar(null, null);
        vista.mostrarTablaTiposDispositivos(items);
        }catch(SQLException ex){
            vista.mensajeError("index.jsp", "Error en la carga del catálogo de dispositivos.");
        }catch(Exception ex){
            vista.mensajeError("index.jsp", "Error en la carga del catálogo de dispositivos.");

        }
    }
    public void generarTablaPaquetes() {
        try{
        ArrayList<Paquete> items = opPaquete.buscar(null, null);
        vista.mostrarTablaPaquetes(items);
        }catch(SQLException ex){
            vista.mensajeError("index.jsp", "Error en la carga del listado de paquetes.");
        }catch(Exception ex){
            vista.mensajeError("index.jsp", "Error en la carga del listado de paquetes.");

        }
    }
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/


}
