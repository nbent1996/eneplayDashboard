package TestingDatos;

import Datos.OpTipoDispositivo;
import Modelo.Categoria;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoDispositivo;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
public class TestOpTipoDispositivo {
    private OpTipoDispositivo op;
    public TestOpTipoDispositivo() {
    }
    
    @Before
    public void setUp(){
        this.op = new OpTipoDispositivo(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        try{
            assertEquals("NOERROR", this.op.insertar(new TipoDispositivo("C500", "Cámara IP Interior 3MP", "Ambos", new Categoria("Indoor Cameras"))).getTextoError());
            assertEquals("NOERROR", this.op.insertar(new TipoDispositivo("A600", "Cámara IP Exterior 3MP", "Wifi", new Categoria("Outdoor Cameras"))).getTextoError());
            assertEquals("NOERROR", this.op.insertar(new TipoDispositivo("Z900", "Cámara IP Interior 7MP", "Ethernet", new Categoria("Indoor Cameras"))).getTextoError());
            
        } catch (SQLException ex) {
            fail("Fallo en testINSERT");
        } catch (Exception ex) {
            fail("Fallo en testINSERT");
        }
    }
    
    @Test
    public void testSELECT(){
        try{
            /*INSERCIONES PREVIAS*/
            TipoDispositivo t1 = new TipoDispositivo("M111", "Cámara IP Interior 3MP", "Ambos", new Categoria("Indoor Cameras"));
            this.op.insertar(t1);
            
            //Búsqueda de un Tipo de dispositivo
            assertTrue(this.op.buscar(" WHERE modelo='M111' ", null).size()==1);
            //Búsqueda de todos los tipos de dispositivos
            assertTrue(!this.op.buscar(null, null).isEmpty());
            //Búsqueda sin resultados
            assertTrue(this.op.buscar(" WHERE modelo='MODELO INEXISTENTE' ", null).isEmpty());

        } catch (SQLException ex) {
            fail("Fallo en testSELECT");
        } catch (Exception ex) {
            fail("Fallo en testSELECT");
        }
    }
    
    @Test
    public void testUPDATE(){
        try{
            TipoDispositivo t1Anterior = new TipoDispositivo("C501", "Cámara IP Interior 3MP", "Ambos", new Categoria("Indoor Cameras"));
            TipoDispositivo t2Anterior = new TipoDispositivo("A602", "Cámara IP Exterior 3MP", "Ambos", new Categoria("Outdoor Cameras"));
            TipoDispositivo t3Anterior = new TipoDispositivo("Z903", "Cámara IP Interior 7MP", "Ambos", new Categoria("Indoor Cameras"));
            
            /*TiposDispositivos con cambios seteados*/
            TipoDispositivo t1 = t1Anterior;
            TipoDispositivo t2 = t2Anterior;
            TipoDispositivo t3 = t3Anterior;
            
            t1.setModelo("C509");
            t2.setModelo("A609");
            t3.setModelo("Z909");
            
            t1.setNombre("Cámara IP 3MP");
            t2.setNombre("Cámara IPb 3MP");
            t3.setNombre("Cámara IP 7MP");
            
            t1.setTipoComunicacion("Ethernet");
            t2.setTipoComunicacion("Ethernet");
            t3.setTipoComunicacion("Ethernet");
            /*INSERCIONES PREVIAS*/
            this.op.insertar(t1Anterior);
            this.op.insertar(t2Anterior);
            this.op.insertar(t3Anterior);
            
            /*Obtener los ID autogenerados*/
            t1Anterior = this.op.buscar(" WHERE modelo='"+t1.getModelo()+"' ", null).get(0);
            t2Anterior = this.op.buscar(" WHERE modelo='"+t2.getModelo()+"' ", null).get(0);
            t3Anterior = this.op.buscar(" WHERE modelo='"+t3.getModelo()+"' ", null).get(0);
            
            
            /*UPDATES*/
            assertEquals("NOERROR", this.op.modificar(t1Anterior, t1).getTextoError());
            assertEquals("NOERROR", this.op.modificar(t2Anterior, t2).getTextoError());
            assertEquals("NOERROR", this.op.modificar(t3Anterior, t3).getTextoError());

            
        } catch (SQLException ex) {
            fail("Fallo en testUPDATE");
        } catch (Exception ex) {
            fail("Fallo en testUPDATE");
        }
    }
    
    @Test
    public void testDELETE(){
        try{
            TipoDispositivo t1 = new TipoDispositivo("D501", "Cámara IP Interior 3MP", "Ambos", new Categoria("Indoor Cameras"));
            TipoDispositivo t2 = new TipoDispositivo("Y602", "Cámara IP Exterior 3MP", "Ambos", new Categoria("Outdoor Cameras"));
            TipoDispositivo t3 = new TipoDispositivo("B903", "Cámara IP Interior 7MP", "Ambos", new Categoria("Indoor Cameras"));
            /*INSERCIONES PREVIAS*/
            this.op.insertar(t1);
            this.op.insertar(t2);
            this.op.insertar(t3);
            
            /*Obtener los ID autogenerados*/
            t1 = this.op.buscar(" WHERE modelo='"+t1.getModelo()+"' ", null).get(0);
            t2 = this.op.buscar(" WHERE modelo='"+t2.getModelo()+"' ", null).get(0);
            t3 = this.op.buscar(" WHERE modelo='"+t3.getModelo()+"' ", null).get(0);
            
            /*PRUEBAS DELETE*/
            assertEquals("NOERROR", this.op.borrar(t1).getTextoError());
            assertEquals("NOERROR", this.op.borrar(t2).getTextoError());
            assertEquals("NOERROR", this.op.borrar(t3).getTextoError());

        } catch (SQLException ex) {
            fail("Fallo en testDELETE");
        } catch (Exception ex) {
            fail("Fallo en testDELETE");
        }   
    }
    
    @Test
    public void testDELETEMULTIPLE(){
            try{
            TipoDispositivo t1 = new TipoDispositivo("Z111", "Cámara IP Interior 3MP", "Ambos", new Categoria("Indoor Cameras"));
            TipoDispositivo t2 = new TipoDispositivo("Z222", "Cámara IP Exterior 3MP", "Ambos", new Categoria("Outdoor Cameras"));
            TipoDispositivo t3 = new TipoDispositivo("Z333", "Cámara IP Interior 7MP", "Ambos", new Categoria("Indoor Cameras"));
            
            /*INSERCIONES PREVIAS*/
            this.op.insertar(t1);
            this.op.insertar(t2);
            this.op.insertar(t3);
           
            /*Obtener los ID autogenerados*/
            t1 = this.op.buscar(" WHERE modelo='"+t1.getModelo()+"' ", null).get(0);
            t2 = this.op.buscar(" WHERE modelo='"+t2.getModelo()+"' ", null).get(0);
            t3 = this.op.buscar(" WHERE modelo='"+t3.getModelo()+"' ", null).get(0);

            ArrayList<Integer> listaIds = new ArrayList<>();
            listaIds.add(t1.getIdTipoDispositivo());
            listaIds.add(t2.getIdTipoDispositivo());
            listaIds.add(t3.getIdTipoDispositivo());
            
            /*DELETE MULTIPLE*/
            assertEquals("NOERROR", this.op.borradoMultiplePorIds(listaIds).getTextoError());
            
            } catch (SQLException ex) {
            fail("Fallo en testDELETEMULTIPLE");
            } catch (Exception ex) {
            fail("Fallo en testDELETEMULTIPLE");
        } 
    }
    
}
