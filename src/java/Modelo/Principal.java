package Modelo;


public class Principal extends Cliente implements IObject<Principal>  {
    
/*Estado*/
private String nroDocumento;
private boolean servicioActivo;
private TipoDocumento tipoDocumento;
/*Estado*/

/*Constructores*/
    /*FULL*/
public Principal(String usuarioSistema, String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, int nroCliente, String email, String nroDocumento, boolean servicioActivo, TipoDocumento tipoDocumento, String telefono){
    this.telefono = telefono;
    this.usuarioSistema = usuarioSistema;
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.empresaAsociada = empresaAsociada;
    this.paisResidencia = paisResidencia;
    this.nroDocumento = nroDocumento;
    this.servicioActivo = servicioActivo;
    this.tipoDocumento = tipoDocumento;
    adaptarCampos();
}
/*FULL SIN USUARIOSISTEMA, QUE VA A SER AUTOGENERADO*/
public Principal(String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, int nroCliente, String email, String nroDocumento, boolean servicioActivo, TipoDocumento tipoDocumento, String telefono){
    this.telefono = telefono;
    this.usuarioSistema = "";
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.empresaAsociada = empresaAsociada;
    this.paisResidencia = paisResidencia;
    this.nroDocumento = nroDocumento;
    this.servicioActivo = servicioActivo;
    this.tipoDocumento = tipoDocumento;
    adaptarCampos();
}
/*SIN EMPRESA, PAIS*/
public Principal(String usuarioSistema, String nombreCompleto, int nroCliente, String email, String nroDocumento, boolean servicioActivo, TipoDocumento tipoDocumento, String telefono){
    this.telefono = telefono;
    this.usuarioSistema = usuarioSistema;
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.nroDocumento = nroDocumento;
    this.servicioActivo = servicioActivo;
    this.tipoDocumento = tipoDocumento;
    adaptarCampos();
}
/*SOLO USUARIO SISTEMA*/
public Principal(String nroDocumento){
    this.usuarioSistema = "";
    this.nroDocumento = nroDocumento;
}
/*SOLO NROCLIENTE*/
public Principal(int nroCliente){
    this.usuarioSistema = "";
    this.nroCliente = nroCliente;
}
/*NRO CLIENTE + NOMBRE COMPLETO + EMPRESA*/
public Principal(int nroCliente, String nombreCompleto, Empresa e){
    this.nroCliente = nroCliente;
    this.nombreCompleto = nombreCompleto;
    this.empresaAsociada = e;
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        super.adaptarCampos();
        /*Sanitizar campos*/
        this.nroDocumento = Funciones.sanitizarCampo(this.nroDocumento);
        if(this.tipoDocumento!=null){
            this.tipoDocumento.setCodDocumento(Funciones.sanitizarCampo(this.tipoDocumento.getCodDocumento()));
        }
        /*Sanitizar campos*/
        this.email = this.email.toLowerCase(); /*el email debe estar totalmente en minúsculas*/
        if(!Funciones.isNumeric(this.nroDocumento)){ /*El Nro de documento debe ser solo numérico*/
            this.nroDocumento = this.nroDocumento.replace(".", "");
            this.nroDocumento = this.nroDocumento.replace(",", "");
            this.nroDocumento = this.nroDocumento.replace("-", "");               
        }     
    }

    @Override
    public String toString(int modo) throws ProgramException {
        String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombreCompleto;
            break;
            
            case 2:
                retorno = this.nombreCompleto + " (" + this.nroDocumento + ")";
            break;
            
            case 3:
                retorno = this.nombreCompleto + " (" + this.usuarioSistema + ")";
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
    @Override
    public void validar() throws ProgramException{
        super.validar();
        String retorno = "";
        /*Campos nulos*/
        if(this.nroDocumento.equals("") || this.nroDocumento==null){
            retorno+="El nro de documento es un campo obligatorio.\n";
        }
        if(this.tipoDocumento==null || this.tipoDocumento.getCodDocumento().equals("") || this.tipoDocumento.getCodDocumento()==null){
            retorno+="El tipo de documento es un campo obligatorio (se debe seleccionar uno).\n";
        }
        //usuarioSistema no se valida porque si es = "" se autogenera validando en la base que no se repita.
        
        /*Largo caracteres*/
        if(this.nroDocumento!=null && this.nroDocumento.length()>15){
            retorno+="El nro de documento no puede tener más de 15 caracteres.\n";
        }
        /*Campos expresamente numéricos*/
        if(!Funciones.isNumeric(this.nroDocumento)){
            retorno+="El nro de documento solo puede contener numeros.\n";
        }
        
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }
    public String getServicioActivoStr(){
        String retorno = "Si";
        if(!this.servicioActivo){
            retorno="No";
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public boolean getServicioActivo() {
        return servicioActivo;
    }

    public void setServicioActivo(boolean servicioActivo) {
        this.servicioActivo = servicioActivo;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
/*Getters y Setters*/

    @Override
    public String retornarTipoCli() {
        return "Principal";
    }




 

    
}
