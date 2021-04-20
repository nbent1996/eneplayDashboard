package Modelo;
public class Idioma implements IObject<Idioma> {
/*Estado*/
private String nombre;
/*Estado*/

/*Constructores*/
public Idioma(String nombre){
    this.nombre = nombre;
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
 @Override
    public void adaptarCampos() {
        this.nombre = Funciones.sanitizarCampo(this.nombre);
        this.nombre = Funciones.FirstLetterUpperCase(this.nombre);
    }

    @Override
    public void validar() throws ProgramException {
        String retorno ="";
        
        /*Campos nulos*/
        if(this.nombre.equals("") || this.nombre == null){
            retorno+="El idioma es un campo obligatorio.\n";
        }
        /*Largo caracteres*/
        if(this.nombre.length()>40){
            retorno+="El idioma no puede tener más de 40 caracteres.\n";
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
/*Getters y Setters*/

   


}
