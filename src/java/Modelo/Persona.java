package Modelo;

public abstract class Persona{

    /*Estado*/
    protected String usuarioSistema;
    protected String nombreCompleto;
    protected Empresa empresaAsociada;
    protected Pais paisResidencia;

    /*Estado*/

 /*Constructores*/
 /*-Clase abstracta-*/
 /*Constructores*/

 /*Comportamiento*/
    public void adaptarCampos(){
        /*Sanitizar campos*/
        this.usuarioSistema = Funciones.sanitizarCampo(this.usuarioSistema);
        this.nombreCompleto = Funciones.sanitizarCampo(this.nombreCompleto);
    }
    
    public void validar() throws ProgramException {
        String retorno = "";
       /*Campos Nulos*/
        if(this.nombreCompleto.equals("") || this.nombreCompleto==null){
            retorno+="El nombre del cliente es un campo obligatorio.\n";
        }
        if(this.getPaisResidencia()==null || this.getPaisResidencia().getCodigo().equals("") || this.getPaisResidencia().getCodigo()==null){
            retorno+="El pais de residencia es un campo obligatorio.\n";
        }
        if(this.getEmpresaAsociada()==null || this.getEmpresaAsociada().getIdentificacionTributaria().equals("") || this.getEmpresaAsociada().getIdentificacionTributaria()== null){
            retorno+="La empresa asociada es un campo obligatorio.\n";//Esto se guarda en el session, es un atributo propio del usuario operador del dashboard que se loguea.
        }
        //usuarioSistema no se valida porque si es = "" se autogenera validando en la base que no se repita.
        /*Largo de caracteres*/
        if(this.nombreCompleto.length()>50){
            retorno+="El nombre del cliente no puede tener más de 50 caracteres.\n";
        }
        
        if (!retorno.equals("")) {
            throw new ProgramException(retorno);
        }
    }

    /*Comportamiento*/

    
    
    
 /*Getters y Setters*/
    public String getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(String usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Empresa getEmpresaAsociada() {
        return empresaAsociada;
    }

    public void setEmpresaAsociada(Empresa empresaAsociada) {
        this.empresaAsociada = empresaAsociada;
    }

    public Pais getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(Pais paisResidencia) {
        this.paisResidencia = paisResidencia;
    }
    /*Getters y Setters*/

    public abstract int retornarNroCliente();
    public abstract String retornarEmail();
    public abstract String retornarTipoCli();
}
