package Modelo;
public class Empresa implements IObject<Empresa> {
/*Estado*/
private String identificacionTributaria;
private String nombre;
private String razonSocial;
private float impuestos;
private Idioma idiomaAsociado;
private Pais paisAsociado;
/*Estado*/

/*Constructores*/
    public Empresa(String identificacionTributaria, String nombre, String razonSocial, float impuestos, Idioma idiomaAsociado, Pais paisAsociado) {
        this.identificacionTributaria = identificacionTributaria;
        this.razonSocial = razonSocial;
        this.impuestos = impuestos;
        this.idiomaAsociado = idiomaAsociado;
        this.paisAsociado = paisAsociado;
        this.nombre = nombre;
        adaptarCampos();
    }
    public Empresa(String identificacionTributaria){
        this.identificacionTributaria = identificacionTributaria;
    }
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.identificacionTributaria = Funciones.sanitizarCampo(this.identificacionTributaria);
        this.razonSocial = Funciones.sanitizarCampo(this.razonSocial);
        this.nombre = Funciones.sanitizarCampo(this.nombre);
        if(Funciones.isNumeric(String.valueOf(this.impuestos))){
            this.impuestos = this.impuestos/100;
        }
    }

    @Override
    public void validar() throws ProgramException {
        String retorno="";
        /*Campos nulos*/
        if(this.identificacionTributaria.equals("") || this.identificacionTributaria==null){
            retorno+="La identificación tributaria de la empresa es un campo obligatorio.\n";
        }
        if(this.nombre.equals("") || this.nombre==null){
            retorno+="El nombre de la empresa es un campo obligatorio.\n";
        }
        if(this.razonSocial.equals("") || this.razonSocial == null){
            retorno+="La razón social de la empresa es un campo obligatorio.\n";
        }
        
        /*Largo caracteres*/
        if(this.identificacionTributaria!=null && this.identificacionTributaria.length()>20){
            retorno+="La identificación tributaria de la empresa no puede tener más de 20 caracteres.\n";
        }
        if(this.nombre!=null && this.nombre.length()>50){
            retorno+="El nombre de la empresa no puede tener más de 50 caracteres.\n";
        }
        if(this.razonSocial!=null && this.razonSocial.length()>30){
            retorno+="La razón social de la empresa no puede tener más de 30 caracteres.";
        }
        /*Campos expresamente numéricos*/
        if(!Funciones.isNumeric(String.valueOf(this.impuestos*100))){
            retorno+="El campo impuestos debe ser numérico.\n";
        }else{
            int impuestosInteger = Integer.parseInt(String.valueOf(this.impuestos*100));
            if(!(impuestosInteger>0 && impuestosInteger<=50)){
                retorno+="El campo impuestos debe estar entre 0 y 50.\n";
            }
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
                retorno = this.identificacionTributaria;
            break;
            case 2:
                retorno = this.identificacionTributaria + "("+this.razonSocial+")";
            break;
            case 3:
                retorno = this.identificacionTributaria + "("+this.nombre+")";
            break;     
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
public String getIdentificacionTributaria() {
        return identificacionTributaria;
    }

    public void setIdentificacionTributaria(String identificacionTributaria) {
        this.identificacionTributaria = identificacionTributaria;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public float getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(float impuestos) {
        this.impuestos = impuestos;
    }

    public Idioma getIdiomaAsociado() {
        return idiomaAsociado;
    }

    public void setIdiomaAsociado(Idioma idiomaAsociado) {
        this.idiomaAsociado = idiomaAsociado;
    }

    public Pais getPaisAsociado() {
        return paisAsociado;
    }

    public void setPaisAsociado(Pais paisAsociado) {
        this.paisAsociado = paisAsociado;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
/*Getters y Setters*/





    

}
