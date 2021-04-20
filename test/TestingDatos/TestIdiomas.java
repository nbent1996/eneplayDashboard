package TestingDatos;

import Datos.OpIdioma;
import Modelo.Empresa;
import Modelo.Idioma;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestIdiomas {
    private OpIdioma op;
    public TestIdiomas() {
    }
    
    @Before
    public void setUp() {
        op = new OpIdioma(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        try{
        assertEquals("NOERROR", op.insertar(new Idioma("Holandés")).getTextoError());
        assertEquals("NOERROR", op.insertar(new Idioma("Alemán")).getTextoError());
        assertEquals("NOERROR", op.insertar(new Idioma("Hebreo")).getTextoError());
        }catch(SQLException ex){
            fail("FALLO en testINSERT");
        }catch(Exception ex){
            fail("FALLO en testINSERT");
        }
    }
    @Test
    public void testSELECT(){
        
        try{
        /*Inserciones previas*/
        op.insertar(new Idioma("Latin"));
            
        /*Búsqueda de un idioma*/
        assertTrue(op.buscar(" WHERE nombreIdioma = 'Latin' ",null).size()==1 );
        
        /*Búsqueda de idioma que no existe*/
        assertTrue(op.buscar(" WHERE nombreIdioma = 'IDIOMA QUE NO EXISTE' ",null).isEmpty());
        
        /*Búsqueda de todos los idiomas*/
        assertTrue(!op.buscar(null,null).isEmpty() ); 
        
        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
}
