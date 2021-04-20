package Modelo;
public class Imagen {
/*Estado*/
private int idImagen;
private byte[] imagen;
/*Estado*/

/*Constructores*/
    public Imagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Imagen(int idImagen, byte[] imagen) {
        this.idImagen = idImagen;
        this.imagen = imagen;
    }

/*Constructores*/
/*Comportamiento*/
/*Comportamiento*/
/*Getters y Setters*/
    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
/*Getters y Setters*/

   
}
