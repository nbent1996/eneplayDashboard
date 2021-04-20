package Modelo;
public class Moneda implements IObject<Moneda>{
/*Estado*/
private String codigo;
private String nombreMoneda;
private String simbolo;
/*Estado*/

/*Constructores*/
    public Moneda(String codigo, String nombreMoneda, String simbolo) {
        this.codigo = codigo;
        this.nombreMoneda = nombreMoneda;
        this.simbolo = simbolo;
        adaptarCampos();
    }
        public Moneda(String codigo) {
        this.codigo = codigo;
        this.nombreMoneda = "";
        this.simbolo = "";
        adaptarCampos();
    }
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.codigo = Funciones.sanitizarCampo(this.codigo);
        this.nombreMoneda = Funciones.sanitizarCampo(this.nombreMoneda);
        this.simbolo = Funciones.sanitizarCampo(this.simbolo);
        
        this.codigo = this.codigo.toUpperCase();
        this.nombreMoneda = Funciones.FirstLetterUpperCase(this.nombreMoneda);
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        if(this.codigo.equals("") || this.codigo==null){
            retorno+="El código es un campo obligatorio.\n";
        }
        if(this.nombreMoneda.equals("") || this.nombreMoneda==null){
            retorno+="El nombre de la moneda es un campo obligatorio.\n";
        }
        if(this.simbolo.equals("") || this.simbolo == null){
            retorno+="El simbolo es un campo obligatorio.\n";
        }
        
        /*Largo caracteres*/
        if(this.codigo.length()>4){
            retorno+="El código no puede tener más de 4 caracteres.\n";
        }
        if(this.nombreMoneda.length()>50){
            retorno+="El nombre de la moneda no puede tener más de 50 caracteres.\n";
        }
        if(this.simbolo.length()>4){
            retorno+="El simbolo no puede tener más de 4 caracteres.\n";
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
                retorno = this.codigo;
            break;
            case 2:
                retorno = this.nombreMoneda + "("+this.codigo+")";
            break;
            case 3:
                retorno = this.nombreMoneda + "("+this.simbolo+")";
            break;
        }
        if(retorno.equals("ERROR ToString")){
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

    public String getNombreMoneda() {
        return nombreMoneda;
    }

    public void setNombreMoneda(String nombreMoneda) {
        this.nombreMoneda = nombreMoneda;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
/*Getters y Setters*/



   


}
