package TestingDatos;

import Datos.OpEmpresa;
import Modelo.Empresa;
import Modelo.Idioma;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestEmpresas {
    private OpEmpresa op;
    public TestEmpresas() {
    }
    @Before
    public void setUp(){
        this.op = new OpEmpresa(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        try{
            Empresa e1 = new Empresa("IDTR1", "Nuevo Siglo Costa de Oro", "nuevosiglo s.a", 0.22F, new Idioma("Español"), new Pais("URU"));
            Empresa e2 = new Empresa("IDTR2", "TVC Costa de Oro", "TVC SRL", 0.22F, new Idioma("Español"), new Pais("URU"));
            Empresa e3 = new Empresa("IDTR3", "Cable Plus Costa de Oro", "CPCO SRL", 0.22F, new Idioma("Español"), new Pais("URU"));
            
            //Inserciones
            assertEquals("NOERROR", this.op.insertar(e1).getTextoError());
            assertEquals("NOERROR", this.op.insertar(e2).getTextoError());
            assertEquals("NOERROR", this.op.insertar(e3).getTextoError());

        }catch (SQLException ex) {
            fail("Fallo en testINSERT");
        } catch (Exception ex) {
            fail("Fallo en testINSERT");
        }
    }
    @Test
    public void testSELECT(){
        try{
            //Inserciones previas
            Empresa e1 = new Empresa("IDTR4", "Nuevo Siglo Maldonado", "nuevosiglo maldonado s.a", 0.22F, new Idioma("Español"), new Pais("URU"));
            this.op.insertar(e1);
            
            //Búsqueda de una empresa
            assertTrue(this.op.buscar(" WHERE identificacionTributaria='IDTR4' ",null).size()==1);
            //Búsqueda de todas las empresas
            assertTrue(!this.op.buscar(null, null).isEmpty());
            //Búsqueda sin resultados
            assertTrue(this.op.buscar(" WHERE identificacionTributaria='ID NO EXISTENTE'", null).isEmpty());
            
        }catch (SQLException ex) {
            fail("Fallo en testSELECT");
        } catch (Exception ex) {
            fail("Fallo en testSELECT");
        }
    }
    @Test
    public void testUPDATE(){
        try{
        /*INSERCIONES PREVIAS*/
        Empresa e1Anterior = new Empresa("IDTR5", "Cable 1 Costa de Oro", "nuevosiglo1 s.a", 0.22F, new Idioma("Español"), new Pais("URU"));
        Empresa e2Anterior = new Empresa("IDTR6", "Cable 2 Costa de Oro", "TVC2 SRL", 0.22F, new Idioma("Español"), new Pais("URU"));
        Empresa e3Anterior = new Empresa("IDTR7", "Cable 3 Plus Costa de Oro", "CPCO3 SRL", 0.22F, new Idioma("Español"), new Pais("URU"));

        //Inserciones
        this.op.insertar(e1Anterior);
        this.op.insertar(e2Anterior);
        this.op.insertar(e3Anterior);
        
        /*CAMBIOS PLANTEADOS*/
        Empresa e1 = e1Anterior;
        Empresa e2 = e2Anterior;
        Empresa e3 = e3Anterior;
        
        e1.setNombre("Cable 1modificado Costa de Oro");
        e1.setRazonSocial("nuevosiglo1 modificado");
        e1.setImpuestos(0.30F);
        e1.setIdiomaAsociado(new Idioma("Portugués"));
        e1.setPaisAsociado(new Pais("BRA"));
        
        e2.setNombre("Cable 2modificado Costa de Oro");
        e2.setRazonSocial("TVC2 SRL modificado");
        e2.setImpuestos(0.35F);
        e2.setIdiomaAsociado(new Idioma("Portugués"));
        e2.setPaisAsociado(new Pais("BRA"));
        
        e3.setNombre("Cable 3modificado Costa de Oro");
        e3.setRazonSocial("CPCO32 SRL modificado");
        e3.setImpuestos(0.40F);
        e3.setIdiomaAsociado(new Idioma("Portugués"));
        e3.setPaisAsociado(new Pais("BRA"));
        
        //REALIZANDO MODIFICACIONES
        assertEquals("NOERROR", this.op.modificar(e1Anterior, e1).getTextoError());
        assertEquals("NOERROR", this.op.modificar(e2Anterior, e2).getTextoError());
        assertEquals("NOERROR", this.op.modificar(e3Anterior, e3).getTextoError());
        
        
        }catch (SQLException ex) {
            fail("Fallo en testUPDATE");
        } catch (Exception ex) {
            fail("Fallo en testUPDATE");
        }
    }
    @Test
    public void testDELETE(){
        try {
            /*INSERCIONES PREVIAS*/
            Empresa e1 = new Empresa("IDTR9", "Cable 1 Costa de Oro", "nuevosiglo1 s.a", 0.22F, new Idioma("Español"), new Pais("URU"));
            Empresa e2 = new Empresa("IDTR10", "Cable 2 Costa de Oro", "TVC2 SRL", 0.22F, new Idioma("Español"), new Pais("URU"));
            Empresa e3 = new Empresa("IDTR11", "Cable 3 Plus Costa de Oro", "CPCO3 SRL", 0.22F, new Idioma("Español"), new Pais("URU"));
            this.op.insertar(e1);
            this.op.insertar(e2);
            this.op.insertar(e3);
            
            /*PRUEBAS DELETE*/
            assertEquals("NOERROR", this.op.borrar(e1).getTextoError());
            assertEquals("NOERROR", this.op.borrar(e2).getTextoError());
            assertEquals("NOERROR", this.op.borrar(e3).getTextoError());
        } catch (SQLException ex) {
            fail("Fallo en testDELETE");
        } catch (Exception ex) {
            fail("Fallo en testDELETE");
        }
    }
    @Ignore
    public void testDELETEMULTIPLE(){
        /*FUNCIONALIDAD NO IMPLEMENTADA EN EL OBJETO DAO*/
    }
    
    
    
    
}
