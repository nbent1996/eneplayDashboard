package TestingDatos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Datos.OpCategoria;
import Modelo.Categoria;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestCategorias {
    private OpCategoria op;
    public TestCategorias() {
    }
    
    @Before
    public void setUp() {
        op = new OpCategoria(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        setUp();
        try{
        assertEquals("NOERROR", op.insertar(new Categoria("Categoria1")).getTextoError());
        assertEquals("NOERROR", op.insertar(new Categoria("Categoria2")).getTextoError());
        assertEquals("NOERROR", op.insertar(new Categoria("Categoria3")).getTextoError());
        }catch(SQLException ex){
            fail("FALLO en testINSERT");
        }catch(Exception ex){
            fail("FALLO en testINSERT");
        }
    }
    @Test
    public void testSELECT(){
        setUp();
        try{
        assertEquals(op.buscar(" WHERE nombreCategoria = 'Battery Powered Cameras' or nombreCategoria = 'Indoor Cameras' ",null).size(), 2 );
        assertEquals(op.buscar(" WHERE nombreCategoria = 'CATEGORIA QUE NO EXISTE' ",null).size(), 0 );
        assertEquals(op.buscar(" WHERE nombreCategoria = 'Smart Plugs' ",null).size(), 0 ); /*Categoria con borrado logico, pero que existe en la base*/
        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
}
