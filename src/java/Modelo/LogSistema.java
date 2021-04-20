package Modelo;

import Resources.DTOs.DTOFechas;
import Resources.DTOs.Fecha;
import java.util.ArrayList;

public class LogSistema {
    /*Estado*/
    private int idLog;
    private String operacion;
    private String textoError;
    private DTOFechas fechaHora;
    private String usuarioSistema;
    private ArrayList<QueryEjecutada> listaQuerys;
    /*Estado*/
    
    /*Constructores*/
    public LogSistema(int idLog){
        this.idLog = idLog;
        this.operacion = this.textoError = usuarioSistema ="";
        this.fechaHora = new DTOFechas(new Fecha());
    }
       
    public LogSistema(int idLog,String usuarioSistema, String operacion, String textoError, ArrayList<QueryEjecutada> listaQuerys){
        this.idLog = idLog;
        this.operacion=operacion;
        this.usuarioSistema = usuarioSistema;
        this.textoError = textoError;
        this.listaQuerys = listaQuerys;
    }
        public LogSistema(String usuarioSistema, String operacion, String textoError, ArrayList<QueryEjecutada> listaQuerys){
        this.idLog = -1;
        this.operacion=operacion;
        this.usuarioSistema = usuarioSistema;
        this.textoError = textoError;
        this.listaQuerys = listaQuerys;
    }
    public LogSistema(String usuarioSistema, String operacion, String textoError){
        this.idLog = -1;
        this.operacion=operacion;
        this.textoError = textoError;
        this.usuarioSistema = usuarioSistema;
    }
    
    /*Constructores*/
    
    /*Comportamiento*/
    
    /*Comportamiento*/
    
    /*Getters y Setters*/
     public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getTextoError() {
        return textoError;
    }

    public void setTextoError(String textoError) {
        this.textoError = textoError;
    }

    public DTOFechas getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(DTOFechas fechaHora) {
        this.fechaHora = fechaHora;
    }
    public ArrayList<QueryEjecutada> getListaQuerys() {
        return listaQuerys;
    }

    public void setListaQuerys(ArrayList<QueryEjecutada> listaQuerys) {
        this.listaQuerys = listaQuerys;
    }
   public String getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(String usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }
    /*Getters y Setters*/

 


   
    
}
