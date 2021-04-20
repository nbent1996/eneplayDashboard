package Modelo;
public class TipoDispositivo implements IObject<TipoDispositivo> {
/*Estado*/
private int idTipoDispositivo;
private String modelo;
private String nombre;
private String tipoComunicacion;
private Categoria categoria;
/*Estado*/

/*Constructores*/
/*SOLO ID*/
public TipoDispositivo(int idTipoDispositivo){
    this.idTipoDispositivo = idTipoDispositivo;
}
/*FULL*/
public TipoDispositivo(int idTipoDispositivo, String modelo, String nombre, String tipoComunicacion, Categoria categoria){
    this.idTipoDispositivo = idTipoDispositivo;
    this.modelo = modelo;
    this.nombre = nombre;
    this.tipoComunicacion = tipoComunicacion;
    this.categoria = categoria;
    adaptarCampos();
}
/*ID-1 FULL*/
public TipoDispositivo(String modelo, String nombre, String tipoComunicacion, Categoria categoria){
    this.idTipoDispositivo = -1;
    this.modelo = modelo;
    this.nombre = nombre;
    this.tipoComunicacion = tipoComunicacion;
    this.categoria = categoria;
    adaptarCampos();
}
/*FULL SIN CATEGORIA*/
public TipoDispositivo(int idTipoDispositivo, String modelo, String nombre, String tipoComunicacion){
    this.idTipoDispositivo = idTipoDispositivo;
    this.modelo = modelo;
    this.nombre = nombre;
    this.tipoComunicacion = tipoComunicacion;
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.modelo = Funciones.sanitizarCampo(this.modelo);
        this.nombre = Funciones.sanitizarCampo(this.nombre);
        this.tipoComunicacion = Funciones.sanitizarCampo(this.tipoComunicacion);
        
        this.nombre = this.nombre.toUpperCase();
        
        
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        if(this.modelo.equals("") || this.modelo==null){
            retorno+="El modelo es un campo obligatorio.\n";
        }
        if(this.nombre.equals("") || this.nombre==null){
            retorno+="El nombre es un campo obligatorio.\n";
        }
        if(this.tipoComunicacion.equals("") || this.tipoComunicacion==null){
            retorno+="El tipo de comunicación es un campo obligatorio.\n";
        }
        /*Enum*/
        if(this.tipoComunicacion.equals("Ethernet") || this.tipoComunicacion.equals("Wifi") || this.tipoComunicacion.equals("Ambos")){
            retorno+="Los posibles valores para tipo de comunicación son Ethernet, Wifi o Ambos.\n";
        }
        /*Largo caracteres*/
        if(this.modelo.length()>15){
            retorno+="El modelo no puede tener más de 15 caracteres.\n";
        }
        if(this.nombre.length()>90){
            retorno+="El nombre no puede tener más de 90 caracteres.\n";
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
                retorno = this.modelo;
            break;
            
            case 2:
                retorno = this.nombre;
            break;
            
            case 3:
                retorno = this.nombre + " ("+this.modelo+")";
            break;
            case 4:
                retorno = this.getModelo() + " ("+this.tipoComunicacion+")";
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
public int getIdTipoDispositivo() {
        return idTipoDispositivo;
    }

    public void setIdTipoDispositivo(int idTipoDispositivo) {
        this.idTipoDispositivo = idTipoDispositivo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoComunicacion() {
        return tipoComunicacion;
    }

    public void setTipoComunicacion(String tipoComunicacion) {
        this.tipoComunicacion = tipoComunicacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
/*Getters y Setters*/



    
}
