package Modelo;


public class Secundario extends Cliente implements IObject<Secundario>{
    
    
/*Estado*/
private Principal principalAsociado;
/*Estado*/

/*Constructores*/
    /*FULL SIN USUARIO SISTEMA*/
public Secundario(String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, int nroCliente, String email, Principal principalAsociado, String telefono){
    this.usuarioSistema ="";
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.empresaAsociada = empresaAsociada;
    this.paisResidencia = paisResidencia;
    this.principalAsociado = principalAsociado;
    this.telefono = telefono;
}
    /*FULL*/
public Secundario(String usuarioSistema, String nombreCompleto, Empresa empresaAsociada, Pais paisResidencia, int nroCliente, String email, Principal principalAsociado, String telefono){
    this.usuarioSistema = usuarioSistema;
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.empresaAsociada = empresaAsociada;
    this.paisResidencia = paisResidencia;
    this.principalAsociado = principalAsociado;
    this.telefono = telefono;
}
/*SIN EMPRESA, PAIS*/
public Secundario(String usuarioSistema, String nombreCompleto, int nroCliente,String email, Principal principalAsociado, String telefono){
    this.usuarioSistema = usuarioSistema;
    this.nombreCompleto = nombreCompleto;
    this.nroCliente = nroCliente;
    this.email = email;
    this.principalAsociado = principalAsociado;
    this.telefono = telefono;
}
/*SOLO USUARIO SISTEMA*/
public Secundario(String usuarioSistema){
    this.usuarioSistema = usuarioSistema;
}
/*SOLO NRO CLIENTE*/
public Secundario(int nroCliente){
    this.usuarioSistema ="";
    this.nroCliente = nroCliente;
    
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos(){
    super.adaptarCampos();
    }
    @Override
    public void validar() throws ProgramException{
    super.adaptarCampos(); 
    }
    @Override
    public String toString(int modo) throws ProgramException {
        String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombreCompleto;
            break;
            
            case 2:
                retorno = this.nombreCompleto + " (" + this.email + ")";
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
    public Principal getPrincipalAsociado() {
        return principalAsociado;
    }

    public void setPrincipalAsociado(Principal principalAsociado) {
        this.principalAsociado = principalAsociado;
    }
        public int getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(int nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

/*Getters y Setters*/

    @Override
    public String retornarTipoCli() {
        return "Secundario";
    }






}
