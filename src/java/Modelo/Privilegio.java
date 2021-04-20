package Modelo;
public class Privilegio implements Comparable<Privilegio>, IObject<Privilegio> {
/*Estado*/
private String nombrePrivilegio;
/*Estado*/

/*Constructores*/
    /*FULL*/
public Privilegio(String nombrePrivilegio){
    this.nombrePrivilegio = nombrePrivilegio;
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public int compareTo(Privilegio o) {
        int resultado = 0;
        if(this.getNombrePrivilegio().compareTo(o.getNombrePrivilegio())==-1){
            resultado = -1;
        }
        if(this.getNombrePrivilegio().compareTo(o.getNombrePrivilegio())==1){
            resultado = 1;
        }
        return resultado;
    }
    @Override
    public void adaptarCampos() {
        throw new UnsupportedOperationException("No implementado."); //Tabla precargada, no necesita validacion de campos o adaptar los mismos, solo un toString
    }

    @Override
    public void validar() throws ProgramException {
        throw new UnsupportedOperationException("No implementado."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString(int modo) throws ProgramException {
         String retorno = "ERROR ToString";
        switch(modo){
            case 1:
                retorno = this.nombrePrivilegio;
            break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/

    public String getNombrePrivilegio() {
        return nombrePrivilegio;
    }

    public void setNombrePrivilegio(String nombrePrivilegio) {
        this.nombrePrivilegio = nombrePrivilegio;
    }
/*Getters y Setters*/






}
