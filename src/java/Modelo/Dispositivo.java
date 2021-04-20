package Modelo;
public class Dispositivo implements IObject<Dispositivo> {
/*Estado*/
private String nroSerie;
private String estado;
private TipoDispositivo tipoDispositivo;
private Empresa empresaAsociada;
private Cliente clienteAsociado;
/*Estado*/

/*Constructores*/
    /*SOLO NROSERIE*/
    public Dispositivo(String nroSerie){
        this.nroSerie = nroSerie;
    }
 /*SIN CLIENTE NI EMPRESA*/
    public Dispositivo(String nroSerie, String estado, TipoDispositivo tipoDispositivo) {
        this.nroSerie = nroSerie;
        this.estado = estado;
        this.tipoDispositivo = tipoDispositivo;
        adaptarCampos();
    }

    /*FULL*/
    public Dispositivo(String nroSerie, String estado, TipoDispositivo tipoDispositivo, Empresa empresaAsociada, Cliente clienteAsociado) {
        this.nroSerie = nroSerie;
        this.estado = estado;
        this.tipoDispositivo = tipoDispositivo;
        this.empresaAsociada = empresaAsociada;
        this.clienteAsociado = clienteAsociado;
        adaptarCampos();
    }

    /*SIN cliente*/
    public Dispositivo(String nroSerie, String estado, TipoDispositivo tipoDispositivo, Empresa empresaAsociada) {
        this.nroSerie = nroSerie;
        this.estado = estado;
        this.tipoDispositivo = tipoDispositivo;
        this.empresaAsociada = empresaAsociada;
        adaptarCampos();
    }

/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        this.nroSerie = Funciones.sanitizarCampo(this.nroSerie);
        
    }

    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        if(this.nroSerie.equals("") || this.nroSerie==null){
            retorno += "El nro de serie es un campo obligatorio.\n";
        }
        if(this.tipoDispositivo==null){
            retorno+="El tipo de dispositivo es un campo obligatorio (se debe seleccionar uno).\n";
        }
        //La empresa asociada se toma del session, del usuario logueado
        //Puede o no tener cliente asociado.
        /*Largo caracteres*/
        if(this.nroSerie!=null && this.nroSerie.length()>20){
            retorno+="El nro de serie no puede tener más de 20 caracteres.\n";
        }
        
        /*Campos Expresamente numéricos*/
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException {
       String retorno = "ERROR ToString";
       switch(modo){
           case 1:
               retorno = this.nroSerie;
           break;
           case 2:
               retorno = this.nroSerie + " (" +this.estado +")";
           break;
           case 3:
               retorno = this.nroSerie + " (Nro Cliente: "+this.getClienteAsociado().getNroCliente()+")";
       }
       return retorno;
    }

/*Comportamiento*/
    
/*Getters y Setters*/
    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoDispositivo getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(TipoDispositivo tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }

    public Empresa getEmpresaAsociada() {
        return empresaAsociada;
    }

    public void setEmpresaAsociada(Empresa empresaAsociada) {
        this.empresaAsociada = empresaAsociada;
    }

    public Cliente getClienteAsociado() {
        return clienteAsociado;
    }

    public void setClienteAsociado(Cliente clienteAsociado) {
        this.clienteAsociado = clienteAsociado;
    }
/*Getters y Setters*/


    
}
