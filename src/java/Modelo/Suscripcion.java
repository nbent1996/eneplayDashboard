package Modelo;

import Resources.DTOs.DTOFechas;
import java.util.ArrayList;

public class Suscripcion implements Comparable<Suscripcion>, IObject<Suscripcion> {
/*Estado*/
private int idSuscripcion;
private DTOFechas fechaInicio;
private float tiempoContrato;
private DTOFechas fechaFin;
private boolean activa;
private ArrayList<Paquete> listaPaquetes;
private Principal clientePrincipal;
private Empresa empresaAsociada;
/*Estado*/

/*Constructores*/
/*FULL SIN PRINCIPAL*/ //SE USA EN BUSCAR DE OPSUSCRIPCION
public Suscripcion(int idSuscripcion, DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, ArrayList<Paquete> listaPaquetes, Empresa empresaAsociada) {
        this.idSuscripcion = idSuscripcion;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.listaPaquetes = listaPaquetes;
        this.empresaAsociada = empresaAsociada;
    }
/*FULL CON PRINCIPAL AGREGADO*/
public Suscripcion(int idSuscripcion, DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, ArrayList<Paquete> listaPaquetes, Principal principal, Empresa empresaAsociada) {
        this.idSuscripcion = idSuscripcion;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.listaPaquetes = listaPaquetes;
        this.clientePrincipal = principal;//AGREGADO
        this.empresaAsociada = empresaAsociada;    
}

/*ID -1 FULL*/
public Suscripcion(DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, ArrayList<Paquete> listaPaquetes, Principal principal, Empresa empresaAsociada) {
        this.idSuscripcion = -1;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.listaPaquetes = listaPaquetes;
        this.clientePrincipal = principal;//AGREGADO
        this.empresaAsociada = empresaAsociada;
    }
/*ID -1 SIN PRINCIPAL*/
public Suscripcion(DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, ArrayList<Paquete> listaPaquetes, Empresa empresaAsociada) {
        this.idSuscripcion = -1;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.listaPaquetes = listaPaquetes;
        this.empresaAsociada = empresaAsociada;
    }
/*FULL SIN PAQUETES*/
public Suscripcion(int idSuscripcion, DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, Empresa empresaAsociada) {
        this.idSuscripcion = idSuscripcion;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.empresaAsociada = empresaAsociada; 
    }
/*ID -1 SIN PAQUETES*/
public Suscripcion(DTOFechas fechaInicio, float tiempoContrato, DTOFechas fechaFin, boolean activa, Empresa empresaAsociada) {
        this.idSuscripcion = -1;
        this.fechaInicio = fechaInicio;
        this.tiempoContrato = tiempoContrato;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.empresaAsociada = empresaAsociada;
    }
/*Constructores*/
/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        /*No hay campos string para sanitizar*/
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        
        /*Largo caracteres*/
        
        /*Campos expresamente numéricos*/
        if(!(this.tiempoContrato>0 && this.tiempoContrato<=4)){
            retorno +="El tiempo de contrato debe tener una duración entre 1 y 4 años.\n";
        }
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException {
        String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = "ID: " + this.idSuscripcion;
            break;
        }   
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
    @Override
    public int compareTo(Suscripcion o) {
        int resultado = 0;
        if(this.getIdSuscripcion()<(o.getIdSuscripcion())){
            resultado = -1;
        }
        if(this.getIdSuscripcion()>(o.getIdSuscripcion())){
            resultado = 1;
        }
        return resultado;
    }
    public String getTiempoContratoStr(){
        String retorno="6 meses ";
        switch((int)this.tiempoContrato){
            case 1:
                retorno= "1 año ";
            break;
            
            case 2:
                retorno= "2 años ";
            break;
            
            case 3:
                retorno= "3 años ";
            break;
            
            case 4:
                retorno= "4 años ";
            break;
        }
        return retorno;
    }
    public String getActivaStr(){
        String retorno = "Si ";
        if(!this.activa){
            retorno="No ";
        }
        return retorno;
                
    }
            
    /*Comportamiento*/
/*Getters y Setters*/
    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public DTOFechas getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(DTOFechas fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public float getTiempoContrato() {
        return tiempoContrato;
    }

    public void setTiempoContrato(float tiempoContrato) {
        this.tiempoContrato = tiempoContrato;
    }

    public DTOFechas getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(DTOFechas fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public ArrayList<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(ArrayList<Paquete> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }
    
    public Principal getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Principal clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }
    
/*Getters y Setters*/

    





    
}
