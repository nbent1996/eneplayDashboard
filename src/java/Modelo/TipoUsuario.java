package Modelo;

import java.util.ArrayList;

public class TipoUsuario implements IObject<TipoUsuario>{
/*Estado*/
private String nombre;
private ArrayList<Privilegio> listaPrivilegios;
/*Estado*/

/*Constructores*/
public TipoUsuario(String nombre, ArrayList<Privilegio> listaPrivilegios){
    this.nombre = nombre;
    this.listaPrivilegios = listaPrivilegios;
    adaptarCampos();
}
public TipoUsuario(String nombre){
    this.nombre = nombre;
    this.listaPrivilegios = new ArrayList<>();
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.nombre = Funciones.sanitizarCampo(this.nombre);
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        if(this.nombre.equals("") || this.nombre == null){
            retorno+="El nombre del tipo de usuario es un campo obligatorio.\n";
        }
        
        /*Largo caracteres*/
        if(this.nombre.length()>30){
            retorno+="El nombre del tipo de usuario no puede tener más de 30 caracteres.\n";
        }
        /*Campos expresamente numéricos*/
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException {
        String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombre;
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;    
    }
/*Comportamiento*/

/*Getters y Setters*/
public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Privilegio> getListaPrivilegios() {
        return listaPrivilegios;
    }

    public void setListaPrivilegios(ArrayList<Privilegio> listaPrivilegios) {
        this.listaPrivilegios = listaPrivilegios;
    }
/*Getters y Setters*/

    @Override
    public String toString() {
        return this.nombre;
    }




    
    
    
}
