package controlador.Interfaces;

import Modelo.Operador;
import java.util.ArrayList;

public interface IVistaLogin {
    public void mostrarError(String textoError);
    public void permitirAcceso(Operador operadorLogin);
    public void denegarAcceso(String mensajeError);

    public void establecerEstadisticasEnSession(ArrayList<Integer> listaResultadosEstadisticas);
    
}
