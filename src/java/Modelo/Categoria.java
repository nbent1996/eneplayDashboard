package Modelo;
public class Categoria implements IObject<Categoria> {
/*Estado*/
private String nombreCategoria;
/*Estado*/

/*Constructores*/
public Categoria(String nombreCategoria){
    this.nombreCategoria = nombreCategoria;
    adaptarCampos();
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        this.nombreCategoria = Funciones.sanitizarCampo(nombreCategoria);
        
    }

    @Override
    public void validar() throws ProgramException {
        /*Campos nulos*/
        String retorno = "";
        if(this.nombreCategoria.equals("") || this.nombreCategoria == null){
            retorno+= "El nombre de la categoria no puede estar vacio.\n";
        }
        /*Largo caracteres*/
        if(this.nombreCategoria.length()>40){
            retorno+="El nombre de la categoria no puede tener más de 40 caracteres.\n";
        }
        if(!retorno.equals("")){
            throw new ProgramException(retorno);
        }
    }

    @Override
    public String toString(int modo) throws ProgramException{
        String retorno = "ERROR ToString";
        switch (modo) {
            case 1:
                retorno = this.nombreCategoria;
                break;
        }
        if(retorno.equals("ERROR ToString")){
            throw new ProgramException(retorno);
        }
        return retorno;
    }
/*Comportamiento*/

/*Getters y Setters*/
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
/*Getters y Setters*/




}
