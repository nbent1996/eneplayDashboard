package Resources.DTOs;

import Resources.DTOs.Fecha;

public class DTOFechas {
    /*Atributos*/
    Fecha fechaA;
    Fecha fechaB;
    /*Atributos*/
    /*Constructores*/
    public DTOFechas(Fecha fechaA){
        this.fechaA = fechaA;
    }
    public DTOFechas(Fecha fechaA, Fecha fechaB){
        this.fechaA = fechaA;
        this.fechaB = fechaB;
    }
    /*Constructores*/
    /*Setters y Getters*/
    public String getFechaAStr(int tipo){
        String retorno ="";
         if(this.fechaA !=null){
                retorno = this.fechaA.toString(tipo);
         }
        return retorno;
    }
    public String getFechaBStr(int tipo){
        String retorno = "";
        if(this.fechaB!=null){
            retorno = this.fechaB.toString(tipo);
        }
        return retorno;
    }
    public Fecha getFechaA() {
        return fechaA;
    }

    public void setFechaA(Fecha fechaA) {
        this.fechaA = fechaA;
    }

    public Fecha getFechaB() {
        return fechaB;
    }

    public void setFechaB(Fecha fechaB) {
        this.fechaB = fechaB;
    }
    /*Setters y Getters*/

   
}
