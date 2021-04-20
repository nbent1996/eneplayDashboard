package TestingDatos;

import Datos.OpPais;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestPaises {
    private OpPais op;
    public TestPaises() {
    }
    
    @Before
    public void setUp() {
        op = new OpPais(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }

    @Test
    public void testSELECT(){
        
        try{       
        /*Búsqueda de un pais*/
        assertTrue(op.buscar(" WHERE nombre = 'Uruguay' ",null).size()==1 );
        
        /*Búsqueda de pais que no existe*/
        assertTrue(op.buscar(" WHERE nombre = 'PAIS QUE NO EXISTE' ",null).isEmpty());
        
        /*Búsqueda de todos los paises*/
        assertTrue(!op.buscar(null,null).isEmpty() ); 
        
        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
}
