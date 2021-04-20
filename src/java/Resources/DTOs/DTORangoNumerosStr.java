package Resources.DTOs;
public class DTORangoNumerosStr {
    /*Atributos*/
        String numA;
        String numB;
    /*Atributos*/

 /*Constructores*/
    public DTORangoNumerosStr(String numA) {
        if(numA.equals("")){
            this.numA = "-1";
        }else{
        this.numA = numA;
        }
        }

    public DTORangoNumerosStr(String numA, String numB) {
        if(numA.equals("")){
            this.numA = "-1";
        }else{
        this.numA = numA;
        }        
        if(numB.equals("")){
            this.numB = "-1";
        }else{
        this.numB = numB;
        }
    }
    /*Constructores*/
    /*Comportamiento*/
    public static boolean isNumeric(String cadena) {

        boolean resultado = false;
        if(cadena.equals("")){
           return true;
        }
        try {
            for(char c: cadena.toCharArray()){
                Integer.parseInt(c+"");
            }
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
            
        }

        return resultado;
    }
    /*Comportamiento*/
    /*Setters y Getters*/
    public boolean esRango(){
        if(isNumeric(this.numA) && isNumeric(this.numB) && !this.numA.equals("-1") && !this.numB.equals("-1")){
            return true;
        }
        return false;
    }
    public String getNumA() {
        return numA;
    }

    public void setNumA(String numA) {
        this.numA = numA;
    }

    public String getNumB() {
        return numB;
    }

    public void setNumB(String numB) {
        this.numB = numB;
    }    
 /*Setters y Getters*/

}
