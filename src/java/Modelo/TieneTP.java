package Modelo;
public class TieneTP implements Comparable<TieneTP>, IObject<TieneTP> {
/*Estado*/
private int cantidadDispositivos;
private TipoDispositivo tipoDispositivo;
/*Estado*/

/*Constructores*/
 public TieneTP(int cantidadDispositivos, TipoDispositivo tipoDispositivo) {
        this.cantidadDispositivos = cantidadDispositivos;
        this.tipoDispositivo = tipoDispositivo;
    }

    public TieneTP(int cantidadDispositivos) {
        this.cantidadDispositivos = cantidadDispositivos;
    }
/*Constructores*/
/*Comportamiento*/
    @Override
    public int compareTo(TieneTP o) {
        int resultado = 0;
        if (this.getTipoDispositivo().getIdTipoDispositivo()< (o.getTipoDispositivo().getIdTipoDispositivo())) {
            resultado = -1;
        }
        if (this.getTipoDispositivo().getIdTipoDispositivo() > (o.getTipoDispositivo().getIdTipoDispositivo())) {
            resultado = 1;
        }
        return resultado;
    }
    @Override
    public void adaptarCampos() {
        /*Sanitizar campos*/
        /*No hay campos string para sanitizar*/
    }
    
    @Override
    public void validar() throws ProgramException {
        String retorno = "";
        /*Campos nulos*/
        
        /*Largo caracteres*/
        
        /*Campos expresamente numéricos*/
        if(!(this.cantidadDispositivos>0)){
            retorno +="La cantidad de dispositivos debe ser mayor a 0.\n";
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
                retorno = tipoDispositivo.toString(3) + " x " + this.cantidadDispositivos;
            break;
            case 2:
                retorno = "Cantidad: " + this.cantidadDispositivos;
            break;

        }   
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/
/*Getters y Setters*/
    public int getCantidadDispositivos() {
        return cantidadDispositivos;
    }

    public void setCantidadDispositivos(int cantidadDispositivos) {
        this.cantidadDispositivos = cantidadDispositivos;
    }

    public TipoDispositivo getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(TipoDispositivo tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }
/*Getters y Setters*/





   
}
