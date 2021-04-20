package Resources.DTOs;

import java.util.Date;

public class Fecha {
    /*Atributos*/
    private int year;
    private int month;
    private int day;
    private final String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre", "Diciembre"};
    /*Atributos*/
    
    /*Constructores*/
    public Fecha(String fechaDMY){
        String Datos[] = fechaDMY.split("/");
        this.day = Integer.parseInt(Datos[0]);
        this.month = Integer.parseInt(Datos[1]);
        this.year = Integer.parseInt(Datos[2]);
    }
    public Fecha(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Fecha(){
        Date fechaActual = new Date();
        this.day = fechaActual.getDate();
        this.month = fechaActual.getMonth()+1;
        this.year = fechaActual.getYear()+1900;
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public String toString(int tipo){
        String retorno ="";
        switch(tipo){
            case -1:
                retorno = "0000-00-00"; //Retorno NULO para SQL;
                break;
            case -2:
                retorno = "No ingresada."; //Retorno NULO para Usuarios;
                break;
            case -3:
                retorno =""; //Retorno NULO de cadena vacia
                break;
            case 1:
                retorno = year + "-" + month + "-" + day; /*Retorno para SQL*/
                break;
            case 2:
                retorno = day + "/" + month + "/" + year; /*Retorno Usuario*/
                break;
            case 3:
                retorno = day + " de " + meses[month-1] + " del " + year; /*Retorno largo usuario*/
            case 4:
                String dia = "", mes="";
                
                if(day<10){
                    dia = "0"+day;
                }else{
                    dia = day+"";
                }
                
                if(month<10){
                    mes = "0"+month;
                }else{
                    mes = month+"";
                }
                retorno = dia+"/"+mes+"/"+year;
                break;
        }
        return retorno;
    }
    public boolean isNullDate(){
        return this == null || (day==1 && month==1 && year==1970);
    }
    public boolean isMostRecentToB(Fecha fechaB){
        if(this.toString(2).equals("01/01/1970") || fechaB.toString(2).equals("01/01/1970")){
            return false;
        }
        if(this.getYear()<fechaB.getYear()){
            return false;
        }
        if(this.getYear()==fechaB.getYear() && this.getMonth()<fechaB.getMonth() ){
            return false;
        }
        if(this.getYear()==fechaB.getYear() && this.getMonth()==fechaB.getMonth() && this.getDay()<fechaB.getDay()){
            return false;
        }
        if(this.getYear()==fechaB.getYear() && this.getMonth()==fechaB.getMonth() && this.getDay()==fechaB.getDay()){
            return false;
        }    
        return true;
    }    
    /*Comportamiento*/
    
    /*Setters y Getters*/
        public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    /*Setters y Getters*/


}
