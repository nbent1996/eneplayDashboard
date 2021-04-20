package TestingDatos;

import Datos.OpImagen;
import Modelo.Empresa;
import Modelo.Funciones;
import Modelo.Imagen;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
public class TestImagenes {
    private OpImagen op;
    public TestImagenes() {
    }
    
    @Before
    public void setUp(){
        this.op = new OpImagen(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    @Test
    public void testINSERT(){
        try{
            byte[] bytes1 = Funciones.getArrayBytes("C:\\Users\\nicol\\Desktop\\Proyecto\\dashboard\\web\\resources\\cablevisionLogo1.png");
            byte[] bytes2 = Funciones.getArrayBytes("C:\\Users\\nicol\\Desktop\\Proyecto\\dashboard\\web\\resources\\Colcable-logo2.png");
            byte[] bytes3 = Funciones.getArrayBytes("C:\\Users\\nicol\\Desktop\\Proyecto\\dashboard\\web\\resources\\directvLogo3.png");
            byte[] bytes4 = Funciones.getArrayBytes("C:\\Users\\nicol\\Desktop\\Proyecto\\dashboard\\web\\resources\\skyLogo4.png");

            Imagen c1 = new Imagen(bytes1);
            Imagen c2 = new Imagen(bytes2);
            Imagen c3 = new Imagen(bytes3);
            Imagen c4 = new Imagen(bytes4);
            op.insertar(c1);
            op.insertar(c2);
            op.insertar(c3);
            op.insertar(c4);
        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
    @Test
    public void testSELECT(){
        
    }
    @Test
    public void testUPDATE(){
    
    }
    @Test
    public void testDELETE(){
    
    }
}
