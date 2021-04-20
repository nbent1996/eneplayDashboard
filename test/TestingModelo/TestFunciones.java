package TestingModelo;

import org.junit.Before;
import org.junit.Test;
import Modelo.Funciones;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Ignore;
public class TestFunciones {
    
    public TestFunciones() {
    }

    @Before
    public void setUp(){
    
    }
    @Ignore
    public void testCadenaAleatoria(){
        String str1 = Funciones.generarCadenaAleatoria(1);
        String str2 = Funciones.generarCadenaAleatoria(10);
        String str3 = Funciones.generarCadenaAleatoria(0);
        assertEquals(1, str1.length());
        assertEquals(10, str2.length());
        assertEquals(0, str3.length());
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        
    }
    @Ignore
    public void testIsNumeric(){
        assertTrue(!Funciones.isNumeric("adsfadsfads"));
        assertTrue(Funciones.isNumeric(""));
        assertTrue(Funciones.isNumeric("1156651"));
    }
    @Ignore
    public void testFirstLetterUpperCase(){
        assertEquals("", Funciones.FirstLetterUpperCase(""));
        assertEquals(null, Funciones.FirstLetterUpperCase(null));
        assertEquals("Alemán", Funciones.FirstLetterUpperCase("ALEMÁN"));
        assertEquals("Portugués", Funciones.FirstLetterUpperCase("portugués"));
        assertEquals("Español", Funciones.FirstLetterUpperCase("eSPAÑOL"));

    }

    @Test
    public void testGetArrayBytes(){
        try {
            byte[] array = Funciones.getArrayBytes("C:\\Users\\nicol\\Desktop\\Proyecto\\dashboard\\web\\resources\\cablevisionLogo1.png");
            String imagen = "";
            for(int i=0 ; i<=array.length-1;i++){
                imagen+=array[i]+"\n";
            }
            System.out.println(imagen);
        } catch (IOException ex) {
            fail("error testGetArrayBytes");
        }
    }
}
