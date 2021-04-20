package Modelo;
public abstract class Cliente extends Persona{
/*Estado*/
protected int nroCliente;
protected String email;
protected String telefono;
/*Estado*/

/*Constructores*/
/*-Clase Abstracta-*/
/*Constructores*/

/*Comportamiento*/
@Override
public void adaptarCampos(){
    super.adaptarCampos();
    /*Sanitizar campos*/
    this.email = Funciones.sanitizarCampo(this.email);
    this.telefono = Funciones.sanitizarCampo(this.telefono);
}
@Override
public void validar() throws ProgramException{
    super.validar();
    String retorno = "";
    /*Campos Nulos*/
    if (this.email.equals("") || this.email == null) {
        retorno += "El email es un campo obligatorio.\n";
    }
    if (this.telefono.equals("") || this.telefono == null) {
        retorno += "El teléfono es un campo obligatorio.\n";
    }
    /*Largo caracteres*/

    /*Campos expresamente numéricos*/
    
    
    if (!retorno.equals("")) {
        throw new ProgramException(retorno);
    }
}
public String toString(int modo) throws ProgramException{
            String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombreCompleto + " (" + this.nroCliente + ")";
            break;
            case 2:
                retorno = this.nombreCompleto;
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
}
/*Comportamiento*/

/*Getters y Setters*/
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public int retornarNroCliente(){
        return this.nroCliente;
    }
    
    @Override
    public String retornarEmail(){
        return this.email;
    }
    
    @Override
    public abstract String retornarTipoCli(); 


/*Getters y Setters*/

}
