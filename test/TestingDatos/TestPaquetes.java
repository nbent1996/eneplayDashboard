package TestingDatos;

import Datos.OpPaquete;
import Modelo.Empresa;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Paquete;
import Modelo.TieneTP;
import Modelo.TipoDispositivo;
import Modelo.TipoUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestPaquetes {
    private OpPaquete op;
    public TestPaquetes() {
    }
    
    @Before
    public void setUp(){
        this.op = new OpPaquete(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        try{
            ArrayList<TieneTP> listaTieneTPp1 = new ArrayList<>();
            listaTieneTPp1.add(new TieneTP(5, new TipoDispositivo(3)));
            listaTieneTPp1.add(new TieneTP(2, new TipoDispositivo(2)));
            listaTieneTPp1.add(new TieneTP(1, new TipoDispositivo(1)));

            Paquete p1 = new Paquete(350,"Paquete 1", new Empresa("526283747346"), listaTieneTPp1);
            Paquete p2 = new Paquete(250,"Paquete 2", new Empresa("526283747346"));
            Paquete p3 = new Paquete(400,"Paquete 3", new Empresa("526283747346"));
            
            /*INSERCIONES DE PRUEBA*/
           assertEquals("NOERROR", this.op.insertar(p1).getTextoError());
           assertEquals("NOERROR", this.op.insertar(p2).getTextoError());
           assertEquals("NOERROR", this.op.insertar(p3).getTextoError());
        
        } catch (SQLException ex) {
            fail("Fallo en testINSERT");
        } catch (Exception ex) {
            fail("Fallo en testINSERT");
        }
    }
    @Test
    public void testSELECT(){
        try{
        //INSERCIONES PREVIAS
        ArrayList<TieneTP> listaTieneTPp2 = new ArrayList<>();
        listaTieneTPp2.add(new TieneTP(4, new TipoDispositivo(3)));
        listaTieneTPp2.add(new TieneTP(1, new TipoDispositivo(2)));
        listaTieneTPp2.add(new TieneTP(2, new TipoDispositivo(1)));
        Paquete p1 = new Paquete(350,"Paquete 4", new Empresa("526283747346"));
        Paquete p2 = new Paquete(351,"Paquete 5", new Empresa("526283747346"), listaTieneTPp2);
        this.op.insertar(p1);
        this.op.insertar(p2);
        
        //obtener los id de los paquetes insertados
        p1 = this.op.buscar(" WHERE nombrePaquete='Paquete 4' ", null).get(0);
        p2 = this.op.buscar(" WHERE nombrePaquete='Paquete 5' ", null).get(0);
       
        /*Búsqueda de un paquete sin lista TieneTP*/
        Paquete p1b = this.op.buscar(" WHERE idPaquete='"+p1.getIdPaquete()+"' ", null).get(0);
        assertTrue((this.op.buscar(" WHERE idPaquete='"+p1.getIdPaquete()+"' ", null).size()==1 && p1b.getListaTieneTP().isEmpty()));
        /*Búsqueda de un paquete con lista TieneTP*/
        Paquete p2b = this.op.buscar(" WHERE idPaquete='"+p2.getIdPaquete()+"' ", null).get(0);
        assertTrue((this.op.buscar(" WHERE idPaquete='"+p2.getIdPaquete()+"' ", null).size()==1 && p2b.getListaTieneTP().size()==3));
        /*Búsqueda de todos los paquetes*/
        assertTrue(!this.op.buscar(null,null).isEmpty());

        } catch (SQLException ex) {
            fail("Fallo en testSELECT");
        } catch (Exception ex) {
            fail("Fallo en testSELECT");
        }
    }
    @Test
    public void testUPDATE(){
        try {
            //INSERCIONES PREVIAS
            ArrayList<TieneTP> listaTieneTPp2 = new ArrayList<>();
            listaTieneTPp2.add(new TieneTP(4, new TipoDispositivo(3)));
            listaTieneTPp2.add(new TieneTP(1, new TipoDispositivo(2)));
            listaTieneTPp2.add(new TieneTP(2, new TipoDispositivo(1)));
            Paquete p1Anterior = new Paquete(451,"Paquete 6", new Empresa("526283747346"));
            Paquete p2Anterior = new Paquete(452,"Paquete 7",new Empresa("526283747346"), listaTieneTPp2);
            this.op.insertar(p1Anterior);
            this.op.insertar(p2Anterior);
            
            //Obtener los id autogenerados
            p1Anterior = this.op.buscar(" WHERE costoBruto='"+p1Anterior.getCostoBruto()+"' ", null).get(0);
            p2Anterior = this.op.buscar(" WHERE costoBruto='"+p2Anterior.getCostoBruto()+"' ", null).get(0);
            
            
            //Cambios seteados
            ArrayList<TieneTP> listaTieneTPp2b = new ArrayList<>();
            listaTieneTPp2b.add(new TieneTP(2, new TipoDispositivo(2)));
            listaTieneTPp2b.add(new TieneTP(2, new TipoDispositivo(2)));
            listaTieneTPp2b.add(new TieneTP(2, new TipoDispositivo(2)));
            Paquete p1 = p1Anterior;
            Paquete p2 = p2Anterior;
            p1.setCostoBruto(551);
            p1.setEmpresaAsociada(new Empresa("76045622-5"));
            p2.setCostoBruto(552);
            p2.setEmpresaAsociada(new Empresa("76045622-5"));
            p2.setListaTieneTP(listaTieneTPp2b);
            
           
            //UPDATES
            assertEquals("NOERROR", this.op.modificar(p1Anterior, p1).getTextoError());
            assertEquals("NOERROR", this.op.modificar(p2Anterior, p2).getTextoError());
            
            
        } catch (SQLException ex) {
            fail("Fallo en testUPDATE");
        } catch (Exception ex) {
            fail("Fallo en testUPDATE");
        }
    }
    @Test
    public void testDELETE(){
        try{
            //INSERCIONES PREVIAS
            ArrayList<TieneTP> listaTieneTPp2 = new ArrayList<>();
            listaTieneTPp2.add(new TieneTP(4, new TipoDispositivo(3)));
            listaTieneTPp2.add(new TieneTP(1, new TipoDispositivo(2)));
            listaTieneTPp2.add(new TieneTP(2, new TipoDispositivo(1)));
            Paquete p1 = new Paquete(651,"Paquete 8", new Empresa("526283747346"));
            Paquete p2 = new Paquete(652,"Paquete 9", new Empresa("526283747346"), listaTieneTPp2);
            this.op.insertar(p1);
            this.op.insertar(p2);
            
             //Obtener los id autogenerados
            p1 = this.op.buscar(" WHERE costoBruto='"+p1.getCostoBruto()+"' ", null).get(0);
            p2 = this.op.buscar(" WHERE costoBruto='"+p2.getCostoBruto()+"' ", null).get(0);
            
            //PRUEBAS DELETE
            assertEquals("NOERROR", this.op.borrar(p1).getTextoError());
            assertEquals("NOERROR", this.op.borrar(p2).getTextoError());

        
        } catch (SQLException ex) {
            fail("Fallo en testDELETE");
        } catch (Exception ex) {
            fail("Fallo en testDELETE");
        }
    }
    @Test
    public void testDELETEMULTIPLE(){
        try {
            //INSERCIONES PREVIAS
            ArrayList<TieneTP> listaTieneTPp2 = new ArrayList<>();
            listaTieneTPp2.add(new TieneTP(4, new TipoDispositivo(3)));
            listaTieneTPp2.add(new TieneTP(1, new TipoDispositivo(2)));
            listaTieneTPp2.add(new TieneTP(2, new TipoDispositivo(1)));
            Paquete p1 = new Paquete(791,"Paquete 10", new Empresa("526283747346"));
            Paquete p2 = new Paquete(792,"Paquete 11", new Empresa("526283747346"), listaTieneTPp2);
            this.op.insertar(p1);
            this.op.insertar(p2);

            //Obtener los id autogenerados
            p1 = this.op.buscar(" WHERE costoBruto='" + p1.getCostoBruto() + "' ", null).get(0);
            p2 = this.op.buscar(" WHERE costoBruto='" + p2.getCostoBruto() + "' ", null).get(0);
      
            //BORRADO EN CASCADA
            ArrayList<Integer> listaIds = new ArrayList<>();
            listaIds.add(p1.getIdPaquete());
            listaIds.add(p2.getIdPaquete());
            
            assertEquals("NOERROR", this.op.borradoMultiplePorIds(listaIds).getTextoError());
        } catch (SQLException ex) {
            fail("Fallo en testDELETEMULTIPLE");
        } catch (Exception ex) {
            fail("Fallo en testDELETEMULTIPLE");
        }
    }
}
