package Modelo;
public class TipoDocumento implements IObject<TipoDocumento> {
/*Estado*/
private String codDocumento;
private String nombreDocumento;
/*Estado*/

/*Constructores*/
public TipoDocumento(String codDocumento, String nombreDocumento){
    this.codDocumento = codDocumento;
    this.nombreDocumento = nombreDocumento;
}
public TipoDocumento(String codDocumento){
    this.codDocumento = codDocumento;
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.codDocumento = Funciones.sanitizarCampo(this.codDocumento);
        this.nombreDocumento = Funciones.sanitizarCampo(this.nombreDocumento);
        
        this.codDocumento = this.codDocumento.toUpperCase();
        this.nombreDocumento = Funciones.FirstLetterUpperCase(this.nombreDocumento);
    }

    @Override
    public void validar() throws ProgramException {
         String retorno = "";
        /*Campos nulos*/
        if(this.codDocumento.equals("") || this.codDocumento==null){
            retorno+="El código es un campo obligatorio.\n";
        }
        if(this.nombreDocumento.equals("") || this.nombreDocumento==null){
            retorno+="El nombre es un campo obligatorio.\n";
        }
        /*Largo caracteres*/
        if(this.codDocumento.length()>10){
            retorno+="El código no puede tener más de 4 caracteres.\n";
        }
        if(this.nombreDocumento.length()>50){
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
                retorno = this.codDocumento;
                break;
            case 2:
                retorno = this.nombreDocumento + " (" + this.codDocumento + ")";
                break;
        }
        if (retorno.equals("ERROR ToString")) {
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }
/*Getters y Setters*/



}
