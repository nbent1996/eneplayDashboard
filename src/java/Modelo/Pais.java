package Modelo;
public class Pais implements IObject<Pais>{
/*Estado*/
private String codigo;
private String nombre;
/*Estado*/

/*Constructores*/
public Pais(String codigo, String nombre){
    this.codigo = codigo;
    this.nombre = nombre;
    adaptarCampos();
}
public Pais(String codigo){
    this.codigo = codigo;
    this.nombre = "";
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.codigo = Funciones.sanitizarCampo(this.codigo);
        this.nombre = Funciones.sanitizarCampo(this.nombre);
        
        this.codigo = this.codigo.toUpperCase();
        this.nombre = Funciones.FirstLetterUpperCase(this.nombre);
    }

    @Override
    public void validar() throws ProgramException {
         String retorno = "";
        /*Campos nulos*/
        if(this.codigo.equals("") || this.codigo==null){
            retorno+="El código es un campo obligatorio.\n";
        }
        if(this.nombre.equals("") || this.nombre==null){
            retorno+="El nombre es un campo obligatorio.\n";
        }
        /*Largo caracteres*/
        if(this.codigo.length()>4){
            retorno+="El código no puede tener más de 4 caracteres.\n";
        }
        if(this.nombre.length()>60){
            retorno+="El nombre no puede tener más de 60 caracteres.\n";
        }
        
        /*Campos expresamente numéricos*/
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException {
        String retorno = "ERROR ToString";
        switch (modo) {
            case 1:
                retorno = this.codigo;
                break;
            case 2:
                retorno = this.nombre + "(" + this.codigo + ")";
                break;
        }
        if (retorno.equals("ERROR ToString")) {
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
/*Getters y Setters*/



    
    
    

}
