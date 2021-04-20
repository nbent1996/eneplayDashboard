package Modelo;

import java.util.ArrayList;

public class Paquete implements IObject<Paquete> {
/*Estado*/
private int idPaquete;
private String nombre;
private float costoBruto;
private Empresa empresaAsociada;
private ArrayList<TieneTP> listaTieneTP;
/*Estado*/

/*Constructores*/
/*SOLO ID*/
public Paquete(int idPaquete){
    this.idPaquete = idPaquete;
    listaTieneTP = new ArrayList<>();
}
/*CON ID Y FULL*/
public Paquete(int idPaquete, String nombre, float costoBruto, Empresa empresaAsociada, ArrayList<TieneTP> listaTieneTP){
    this.idPaquete = idPaquete;
    this.nombre = nombre;
    this.costoBruto = costoBruto;
    this.empresaAsociada = empresaAsociada;
    this.listaTieneTP = listaTieneTP;
    adaptarCampos();
}
/*ID -1 FULL*/
public Paquete(float costoBruto, String nombre, Empresa empresaAsociada, ArrayList<TieneTP> listaTieneTP){
    this.idPaquete = -1;
    this.costoBruto = costoBruto;
    this.nombre = nombre;
    this.empresaAsociada = empresaAsociada;
    this.listaTieneTP = listaTieneTP;
    adaptarCampos();
}
/*CON ID SIN LISTA TIENETP*/
public Paquete(int idPaquete, float costoBruto, String nombre, Empresa empresaAsociada){
    this.idPaquete = idPaquete;
    this.costoBruto = costoBruto;
    this.nombre = nombre;
    this.empresaAsociada = empresaAsociada;
    listaTieneTP = new ArrayList<>();
    adaptarCampos();

}
/*ID -1 sin LISTA TIENETP*/
public Paquete(float costoBruto,String nombre, Empresa empresaAsociada){
    this.idPaquete = -1;
    this.costoBruto = costoBruto;
    this.nombre = nombre;
    this.empresaAsociada = empresaAsociada;
    listaTieneTP = new ArrayList<>();
    adaptarCampos();

}
/*SOLO ID PAQUETE + NOMBRE*/
public Paquete(int idPaquete, String nombre){
    this.idPaquete = idPaquete;
    this.nombre = nombre;
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*La empresa asociada se trae del session, del usuario logueado del dashboard*/
        /*Sanitizar campos*/
        this.nombre = Funciones.sanitizarCampo(this.nombre);
        
        this.nombre = Funciones.FirstLetterUpperCase(this.nombre);
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        if(this.nombre.equals("") || this.nombre == null){
            retorno+="El nombre del paquete es un campo obligatorio.\n";
        }
        /*Largo caracteres*/
        if(this.nombre.length()>50){
            retorno+="El nombre del paquete no puede tener más de 50 caracteres.\n";
        }
        /*Campos expresamente numéricos*/
        if(this.costoBruto<0){
            retorno+="El costo del paquete debe ser mayor a 0.\n";
        }
        /*La empresa no se valida, se trae del usuario logueado*/
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException {
                String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombre + " ("+this.idPaquete+")";
            break;
            case 2:
                retorno = "ID: "+ this.idPaquete + " ("+this.empresaAsociada.getIdentificacionTributaria()+")";
            break;
            
            case 3:
                retorno = "ID: "+ this.idPaquete + " ( Costo: "+this.costoBruto+")";
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public float getCostoBruto() {
        return costoBruto;
    }

    public void setCostoBruto(float costoBruto) {
        this.costoBruto = costoBruto;
    }

    public Empresa getEmpresaAsociada() {
        return empresaAsociada;
    }

    public void setEmpresaAsociada(Empresa empresaAsociada) {
        this.empresaAsociada = empresaAsociada;
    }

    public ArrayList<TieneTP> getListaTieneTP() {
        return listaTieneTP;
    }

    public void setListaTieneTP(ArrayList<TieneTP> listaTieneTP) {
        this.listaTieneTP = listaTieneTP;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

/*Getters y Setters*/



    
}
