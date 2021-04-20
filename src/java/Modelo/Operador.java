package Modelo;

public class Operador extends Persona implements IObject<Operador> {

    /*Estado*/
    
    private String clave;
    private TipoUsuario tipoUsuario;
    private String genero;
    /*Estado*/

 /*Constructores*/
    
    public Operador(String clave, String usuarioSistema, String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, TipoUsuario tipoUsuario, String genero) {
        this.clave = clave;
        this.usuarioSistema = usuarioSistema;
        this.nombreCompleto = nombreCompleto;
        this.empresaAsociada = empresaAsociada;
        this.paisResidencia = paisResidencia;
        this.tipoUsuario = tipoUsuario;
        this.genero = genero;
        adaptarCampos();
    }
    public Operador(String usuarioSistema, String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, TipoUsuario tipoUsuario, String genero) {
        this.usuarioSistema = usuarioSistema;
        this.nombreCompleto = nombreCompleto;
        this.empresaAsociada = empresaAsociada;
        this.paisResidencia = paisResidencia;
        this.tipoUsuario = tipoUsuario;
        this.genero = genero;
        adaptarCampos();
    }
    public Operador(String usuarioSistema){
        this.usuarioSistema = usuarioSistema;
    }
    /*Constructores*/

 /*Comportamiento*/
    @Override 
    public void adaptarCampos(){
        super.adaptarCampos();
        /*Sanitizar campos*/
        this.genero = Funciones.sanitizarCampo(this.genero);
        this.clave = Funciones.sanitizarCampo(clave); //QUE PASA SI EL USUARIO PONE UNA CLAVE CON ; : O / ???
        if(this.tipoUsuario!=null){
            this.tipoUsuario.setNombre(Funciones.sanitizarCampo(this.tipoUsuario.getNombre()));
        }
    }
    @Override
    public void validar() throws ProgramException {
        super.validar();//valida los campos de Persona
        String retorno = "";
        /*Campos nulos*/
        if(this.clave.equals("") || this.clave==null){
            retorno+="La clave no puede estar vacia.\n";
        }
        if(this.tipoUsuario == null || this.tipoUsuario.getNombre().equals("")){
            retorno+="El tipo de usuario es un campo obligatorio (se debe seleccionar uno).\n";
        }
        if(this.genero.equals("") || this.genero == null){
            retorno+="El género es un campo obligatorio.\n";
        }
        /*Largo caracteres*/
        if(this.genero.length()>10){
            retorno+="El género no puede tener más de 10 caracteres.";
        }
        /*Campos expresamente numéricos*/
        
        if (tipoUsuario == null) {
            retorno += "El tipo de usuario es nulo.\n";
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
                retorno = this.nombreCompleto;
            break;
            
            case 2:
                retorno = this.usuarioSistema;
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
    /*Comportamiento*/

    
    
    
    
    
    
 /*Getters y Setters*/
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    /*Getters y Setters*/

    @Override
    public int retornarNroCliente() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String retornarEmail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String retornarTipoCli() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}
