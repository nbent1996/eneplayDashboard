package Modelo;
public class Traduccion {
/*Estado*/
private int idTraduccion;
private String plataforma;
private String idItemGUI;
private String texto;
private String textoSecundario;
private Idioma idiomaAsociado;
/*Estado*/

/*Constructores*/
/*FULL*/
 public Traduccion(int idTraduccion, String plataforma, String idItemGUI, String texto, String textoSecundario, Idioma idiomaAsociado) {
        this.idTraduccion = idTraduccion;
        this.plataforma = plataforma;
        this.idItemGUI = idItemGUI;
        this.texto = texto;
        this.textoSecundario = textoSecundario;
        this.idiomaAsociado = idiomaAsociado;
    }

/*FULL SIN IDIOMA*/
  public Traduccion(int idTraduccion, String plataforma, String idItemGUI, String texto, String textoSecundario) {
        this.idTraduccion = idTraduccion;
        this.plataforma = plataforma;
        this.idItemGUI = idItemGUI;
        this.texto = texto;
        this.textoSecundario = textoSecundario;
    }
/*ID -1*/
   public Traduccion(String plataforma, String idItemGUI, String texto, String textoSecundario, Idioma idiomaAsociado) {
        this.idTraduccion = -1;
        this.plataforma = plataforma;
        this.idItemGUI = idItemGUI;
        this.texto = texto;
        this.textoSecundario = textoSecundario;
        this.idiomaAsociado = idiomaAsociado;
    }
/*Constructores*/
/*Comportamiento*/
/*Comportamiento*/
/*Getters y Setters*/
    public int getIdTraduccion() {
        return idTraduccion;
    }

    public void setIdTraduccion(int idTraduccion) {
        this.idTraduccion = idTraduccion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getIdItemGUI() {
        return idItemGUI;
    }

    public void setIdItemGUI(String idItemGUI) {
        this.idItemGUI = idItemGUI;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoSecundario() {
        return textoSecundario;
    }

    public void setTextoSecundario(String textoSecundario) {
        this.textoSecundario = textoSecundario;
    }

    public Idioma getIdiomaAsociado() {
        return idiomaAsociado;
    }

    public void setIdiomaAsociado(Idioma idiomaAsociado) {
        this.idiomaAsociado = idiomaAsociado;
    }
/*Getters y Setters*/

   
}
