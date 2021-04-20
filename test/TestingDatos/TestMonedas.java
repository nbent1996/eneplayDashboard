package TestingDatos;

import Datos.OpMoneda;
import Modelo.Empresa;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestMonedas {
    private OpMoneda op;
    public TestMonedas() {
    }
    
    @Before
    public void setUp() {
        op = new OpMoneda(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }

    @Test
    public void testSELECT(){
        
        try{       
        /*Búsqueda de una moneda*/
        assertTrue(op.buscar(" WHERE nombreMoneda = 'Reales' ",null).size()==1 );
        
        /*Búsqueda de moneda que no existe*/
        assertTrue(op.buscar(" WHERE nombreMoneda = 'MONEDA QUE NO EXISTE' ",null).isEmpty());
        
        /*Búsqueda de todos las monedas*/
        assertTrue(!op.buscar(null,null).isEmpty() ); 
        
        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
}
