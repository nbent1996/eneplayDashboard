package TestingDatos;

import Datos.OpTipoUsuario;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Privilegio;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
public class TestTiposUsuarios {
    private OpTipoUsuario op;
    public TestTiposUsuarios() {
    }
    
    @Before
    public void setUp(){
        this.op = new OpTipoUsuario(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        try{
            /*Precargando listas de privilegios*/
            ArrayList<Privilegio> listaPrivilegiosA = new ArrayList<>();
            ArrayList<Privilegio> listaPrivilegiosB = new ArrayList<>();
            listaPrivilegiosA.add(new Privilegio("INSERT PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("DELETE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("UPDATE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("SELECT PRINCIPALES"));

            listaPrivilegiosB.add(new Privilegio("INSERT PAQUETES"));
            listaPrivilegiosB.add(new Privilegio("INSERT PRINCIPALES"));
            listaPrivilegiosB.add(new Privilegio("INSERT CATEGORIAS"));
            
            assertEquals("NOERROR", op.insertar(new TipoUsuario("gerente", listaPrivilegiosA)).getTextoError());
            assertEquals("NOERROR", op.insertar(new TipoUsuario("administrativo", listaPrivilegiosB)).getTextoError());
            assertEquals("NOERROR", op.insertar(new TipoUsuario("instalador")).getTextoError()); //TIPO DE USUARIO SIN PRIVILEGIOS ASIGNADOS
        }catch(SQLException ex){
            fail("FALLO en testINSERT");
        }catch(Exception ex){
            fail("FALLO en testINSERT");
        }
    }
    
    @Test
    public void testSELECT(){
        try{
            //INSERCIONES PREVIAS
                /*Precargando listas de privilegios*/
            ArrayList<Privilegio> listaPrivilegiosA = new ArrayList<>();
            listaPrivilegiosA.add(new Privilegio("INSERT PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("DELETE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("UPDATE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("SELECT PRINCIPALES"));
            
            op.insertar(new TipoUsuario("Vendedor", listaPrivilegiosA));
            op.insertar(new TipoUsuario("Operario"));
            
            //Búsqueda de un tipo de usuario sin privilegios cargados
            TipoUsuario tipoA = op.buscar(" WHERE nombre='Operario' ", null).get(0);
            assertTrue((!tipoA.getNombre().equals("") && tipoA.getListaPrivilegios().isEmpty()));
            
            //Búsqueda de un tipo de usuario con privilegios cargados
            TipoUsuario tipoB = op.buscar(" WHERE nombre='Vendedor' ", null).get(0);
            assertTrue((!tipoB.getNombre().equals("") && !tipoB.getListaPrivilegios().isEmpty()));
            
            //Búsqueda de todos los tipos de usuario
            assertTrue(!op.buscar(null,null).isEmpty());
            
            //Búsqueda sin resultados
            assertTrue(op.buscar(" WHERE nombre='TIPO USUARIO INEXISTENTE' ",null).isEmpty());

        }catch(SQLException ex){
            fail("FALLO en testSELECT");

        }catch(Exception ex){
            fail("FALLO en testSELECT");
        }
    }
    @Test
    public void testUPDATE(){
        try{
            /*Precargando listas de privilegios INICIALES*/
            ArrayList<Privilegio> listaPrivilegiosA = new ArrayList<>();
            ArrayList<Privilegio> listaPrivilegiosB = new ArrayList<>();
            listaPrivilegiosA.add(new Privilegio("INSERT PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("DELETE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("UPDATE PRINCIPALES"));
            listaPrivilegiosA.add(new Privilegio("SELECT PRINCIPALES"));

            listaPrivilegiosB.add(new Privilegio("INSERT PAQUETES"));
            listaPrivilegiosB.add(new Privilegio("INSERT PRINCIPALES"));
            listaPrivilegiosB.add(new Privilegio("INSERT CATEGORIAS"));

            /*Precargando listas de privilegios NUEVAS*/
            ArrayList<Privilegio> listaPrivilegiosC = new ArrayList<>();
            ArrayList<Privilegio> listaPrivilegiosD = new ArrayList<>();
            listaPrivilegiosC.add(new Privilegio("INSERT SECUNDARIOS"));
            listaPrivilegiosC.add(new Privilegio("DELETE SECUNDARIOS"));
            listaPrivilegiosC.add(new Privilegio("UPDATE SECUNDARIOS"));
            listaPrivilegiosC.add(new Privilegio("SELECT SECUNDARIOS"));

            listaPrivilegiosD.add(new Privilegio("INSERT DISPOSITIVOS"));
            listaPrivilegiosD.add(new Privilegio("INSERT SUSCRIPCIONES"));
            listaPrivilegiosD.add(new Privilegio("INSERT FACTURAS"));   
        
            /*INSERCIONES PREVIAS*/
            TipoUsuario tipoAnteriorA = new TipoUsuario("gerentePruebaUPDATE", listaPrivilegiosA);
            TipoUsuario tipoAnteriorB = new TipoUsuario("administrativoPruebaUPDATE", listaPrivilegiosB);
            TipoUsuario tipoNuevoA = tipoAnteriorA;
            TipoUsuario tipoNuevoB = tipoAnteriorB;
            tipoNuevoA.setListaPrivilegios(listaPrivilegiosC);
            tipoNuevoB.setListaPrivilegios(listaPrivilegiosD);
            op.insertar(tipoAnteriorA);
            op.insertar(tipoAnteriorB);
      
            /*MODIFICAR LISTAS DE PRIVILEGIOS*/
            assertEquals("NOERROR", op.modificar(tipoAnteriorA, tipoNuevoA).getTextoError());
            assertEquals("NOERROR", op.modificar(tipoAnteriorB, tipoNuevoB).getTextoError());

        }catch(SQLException ex){
            fail("FALLO en testUPDATE");

        }catch(Exception ex){
            fail("FALLO en testUPDATE");
        }
    }
    @Test
    public void testDELETE(){
        //FUNCIONALIDAD NO IMPLEMENTADA EN EL OBJETO DAO
    }
    @Ignore
    public void testDELETEMULTIPLE(){
        //FUNCIONALIDAD NO IMPLEMENTADA EN EL OBJETO DAO
    } 
    
}
