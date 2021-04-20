package TestingDatos;

import Datos.OpDispositivo;
import Modelo.Dispositivo;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Principal;
import Modelo.TipoDispositivo;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
public class TestDispositivos {
    private OpDispositivo op;

    public TestDispositivos() {
    }

    @Before
    public void setUp() {
        this.op = new OpDispositivo(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }

    @Test
    public void testINSERT() {
        try {
            assertEquals("NOERROR", this.op.insertar(new Dispositivo("R3NB54YQE9H2B44", "Nuevo", new TipoDispositivo(1),new Empresa("902.237.970-14"),null)).getTextoError());
            assertEquals("NOERROR", this.op.insertar(new Dispositivo("FZ7Q577PPXZFK8C", "Usado-OK", new TipoDispositivo(1),new Empresa("902.237.970-14"),null)).getTextoError());
            assertEquals("NOERROR", this.op.insertar(new Dispositivo("ZN7SXEVJPYP2XMX", "Usado-Reparar", new TipoDispositivo(1),new Empresa("189.68045.54-8"),new Principal(2))).getTextoError());
            assertEquals("NOERROR", this.op.insertar(new Dispositivo("5FAADVZRJBMKSVL", "Nuevo", new TipoDispositivo(1),new Empresa("189.68045.54-8"), new Principal(4))).getTextoError()); 
        } catch (SQLException ex) {
            fail("Fallo en testINSERT");
        } catch (Exception ex) {
            fail("Fallo en testINSERT");
        }

    }

    @Test
    public void testUPDATE() {
        try {
            ArrayList<Dispositivo> lista = new ArrayList<>();
            lista.add(new Dispositivo("S7A4XV6BDA72HV9", "Nuevo", new TipoDispositivo(1),new Empresa("902.237.970-14"),null));
            lista.add(new Dispositivo("967ZHUK7V5DPMYH", "Usado-OK", new TipoDispositivo(1),new Empresa("902.237.970-14"),null));
            lista.add(new Dispositivo("WYKFWQSYU5KXQR5", "Usado-Reparar", new TipoDispositivo(1),new Empresa("189.68045.54-8"),new Principal(2)));
            lista.add(new Dispositivo("684QC3L37Z8TBTT", "Nuevo", new TipoDispositivo(1),new Empresa("189.68045.54-8"), new Principal(4)));
            
            for(Dispositivo d : lista){
                this.op.insertar(d);
            }
            
            assertEquals("NOERROR", this.op.modificar(lista.get(0), new Dispositivo("S7A4XV6BDA72HV9", "Nuevo", new TipoDispositivo(1),new Empresa("902.237.970-14"),new Principal(2))).getTextoError()); /*CAMBIA NROCLIENTE*/
            assertEquals("NOERROR", this.op.modificar(lista.get(1), new Dispositivo("967ZHUK7V5DPMYH", "Usado-Reparar", new TipoDispositivo(1),new Empresa("902.237.970-14"),null)).getTextoError()); /*CAMBIA ESTADO*/
            assertEquals("NOERROR", this.op.modificar(lista.get(2), new Dispositivo("WYKFWQSYU5KXQR5", "Usado-Reparar", new TipoDispositivo(2),new Empresa("189.68045.54-8"),new Principal(2))).getTextoError()); /*CAMBIA ID TIPO DISPOSITIVO*/
            assertEquals("NOERROR", this.op.modificar(lista.get(3), new Dispositivo("684QC3L37Z8TBTT", "Usado-OK", new TipoDispositivo(3),new Empresa("189.68045.54-8"), new Principal(1))).getTextoError()); /*CAMBIA NROCLIENTE, ESTADO Y IDTIPODISPOSITIVO*/
            
        } catch (SQLException ex) {
            fail("Fallo en testUPDATE");
        } catch (Exception ex) {
            fail("Fallo en testUPDATE");
        }

    }

    @Test
    public void testDELETE() {
        try {
            ArrayList<Dispositivo> lista = new ArrayList<>();
            lista.add(new Dispositivo("PW2PGV6VUN9N72Y","Usado-Reparar", new TipoDispositivo(1),new Empresa("902.237.970-14"),null));
            lista.add(new Dispositivo("PJTNX5NQMY4BTPP", "Usado-OK", new TipoDispositivo(1),new Empresa("902.237.970-14"),null));

            for(Dispositivo d : lista){
                this.op.insertar(d);
            }
            
            assertEquals("NOERROR", this.op.borrar(new Dispositivo("PW2PGV6VUN9N72Y")).getTextoError());
            assertEquals("NOERROR", this.op.borrar(lista.get(1)).getTextoError()); /*BORRAMOS UN DISPOSITIVO CON TODOS LOS DATOS Y OTRO CON SOLO EL NRO DE SERIE*/
            
        } catch (SQLException ex) {
            fail("Fallo en testDELETE");
        } catch (Exception ex) {
            fail("Fallo en testDELETE");
        }

    }

    @Test
    public void testSELECT() {
        try {
            assertEquals(op.buscar(" WHERE dis.identificacionTributaria='EMPRESA QUE NO EXISTE' ", null).size(), 0); /*BÚSQUEDA SIN RESULTADOS*/
            assertTrue(op.buscar(null, null).size()>8); /*BÚSQUEDA DE TODO*/

        } catch (SQLException ex) {
            fail("Fallo en testSELECT");
        } catch (Exception ex) {
            fail("Fallo en testSELECT");
        }

    }

    @Test
    public void testDELETEMULTIPLE() {
        try {
            ArrayList<Dispositivo> lista = new ArrayList<>();
            ArrayList<String> listaStr = new ArrayList<>();
            lista.add(new Dispositivo("QGZLALB9SGEPSH6", "Usado-Reparar", new TipoDispositivo(1),new Empresa("189.68045.54-8"),new Principal(2)));
            lista.add(new Dispositivo("BUSDEJERZYSZBAY","Usado-OK", new TipoDispositivo(1),new Empresa("902.237.970-14"),null));
            
            for(Dispositivo d : lista){
                this.op.insertar(d);
            }
            
            listaStr.add("QGZLALB9SGEPSH6");
            listaStr.add("BUSDEJERZYSZBAY");
            
            assertEquals("NOERROR", this.op.borradoMultiplePorIds(listaStr).getTextoError());
            
          } catch (SQLException ex) {
            fail("Fallo en testDELETEMULTIPLE");
        } catch (Exception ex) {
            fail("Fallo en testDELETEMULTIPLE");
        }

    }

}
