package TestingDatos;

import Datos.OpFactura;
import Modelo.Empresa;
import Modelo.Factura;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Principal;
import Modelo.TipoUsuario;
import Resources.DTOs.DTOFechas;
import Resources.DTOs.Fecha;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestFacturas {
    private OpFactura op;
    
    public TestFacturas() {
    }
    
    @Before
    public void setUp(){
        this.op = new OpFactura(new Operador("bentancor", "Nicolás Bentancor", new Empresa("526283747346"),new Pais("URU"),new TipoUsuario("administrador"), "Masculino"));
    }
    
    @Test
    public void testINSERT(){
        /*Precarga de fechas*/
        ArrayList<DTOFechas> fechasPago = new ArrayList<>();
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));

        ArrayList<DTOFechas> fechasVencimiento = new ArrayList<>();
        fechasVencimiento.add(new DTOFechas(new Fecha(25,10,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,3,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,9,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,5,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,6,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioInicio = new ArrayList<>();
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,9,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,2,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,8,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,4,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,5,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioFin = new ArrayList<>();
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(30,9,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(29,2,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(31,8,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(30,4,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(31,5,2020)));
        /*Precarga de fechas*/
        
        List<Double> montos = Arrays.asList(500.0,300.0,500.0,300.0,500.0);
        List<String> tiposRecibo = Arrays.asList("Periodica", "Periodica", "Periodica", "Periodica", "A demanda");
        List<Principal> clientes = Arrays.asList(new Principal(2),new Principal(4),new Principal(8),new Principal(3),new Principal(1));
        List<Moneda> monedas = Arrays.asList(new Moneda("UYU"),new Moneda("UYU"),new Moneda("UYU"),new Moneda("UYU"),new Moneda("UYU"));
        List<Empresa> empresas = Arrays.asList(new Empresa("526283747346"),new Empresa("526283747346"),new Empresa("526283747346"),new Empresa("526283747346"),new Empresa("526283747346"));
        
        try{
        for(int i = 0; i<=4; i++){
            assertEquals("NOERROR", this.op.insertar(new Factura(-1,fechasPago.get(i), fechasVencimiento.get(i), fechasPeriodoServicioInicio.get(i), fechasPeriodoServicioFin.get(i), montos.get(i), tiposRecibo.get(i), clientes.get(i), null, monedas.get(i), empresas.get(i))).getTextoError());
        }
        
        } catch (SQLException ex) {
            fail("Fallo en testINSERT");
        } catch (Exception ex) {
            fail("Fallo en testINSERT");
        }
    }
    
    @Test
    public void testUPDATE(){
        /*Precarga de fechas*/
        ArrayList<DTOFechas> fechasPago = new ArrayList<>();
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));

        ArrayList<DTOFechas> fechasVencimiento = new ArrayList<>();
        fechasVencimiento.add(new DTOFechas(new Fecha(25,10,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,3,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioInicio = new ArrayList<>();
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,9,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,2,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioFin = new ArrayList<>();
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(30,9,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(29,2,2020)));
        /*Precarga de fechas*/
        
        List<Integer> montos = Arrays.asList(300,500);
        List<String> tiposRecibo = Arrays.asList("Periodica", "A demanda");
        List<Principal> clientes = Arrays.asList(new Principal(4),new Principal(6));
        List<Moneda> monedas = Arrays.asList(new Moneda("UYU"),new Moneda("UYU"));
        List<Empresa> empresas = Arrays.asList(new Empresa("526283747346"),new Empresa("526283747346"));
        
        
        
        try{
            /*INSERTAR LAS 2 FACTURAS*/
            this.op.insertar(new Factura(-1,fechasPago.get(0), fechasVencimiento.get(0), fechasPeriodoServicioInicio.get(0), fechasPeriodoServicioFin.get(0), montos.get(0), tiposRecibo.get(0), clientes.get(0), null, monedas.get(0), empresas.get(0)));
            this.op.insertar(new Factura(-1,fechasPago.get(1), fechasVencimiento.get(1), fechasPeriodoServicioInicio.get(1), fechasPeriodoServicioFin.get(1), montos.get(1), tiposRecibo.get(1), clientes.get(1), null, monedas.get(1), empresas.get(1)));

            /*RECUPERAR SUS ID QUE SON AUTOINCREMENTALES*/
            int idA = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(0).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(0).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(0).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(0).getFechaAStr(1)+"' and monto = '"+montos.get(0)+"' and tipoRecibo = '"+tiposRecibo.get(0)+"' and nroCliente='"+clientes.get(0).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(0).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();
            int idB = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(1).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(1).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(1).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(1).getFechaAStr(1)+"' and monto = '"+montos.get(1)+"' and tipoRecibo = '"+tiposRecibo.get(1)+"' and nroCliente='"+clientes.get(1).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(1).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();

            /*MODIFICAR LAS FACTURAS*/
            assertEquals("NOERROR", this.op.modificar(new Factura(idA), new Factura(-1,new DTOFechas(new Fecha(1,1,1970)), fechasVencimiento.get(0), fechasPeriodoServicioInicio.get(0), fechasPeriodoServicioFin.get(0), montos.get(0), tiposRecibo.get(0), clientes.get(0), new ArrayList<>(), monedas.get(0), empresas.get(0))).getTextoError());
            assertEquals("NOERROR", this.op.modificar(new Factura(idB), new Factura(-1,new DTOFechas(new Fecha(1,1,1970)), fechasVencimiento.get(1), fechasPeriodoServicioInicio.get(1), fechasPeriodoServicioFin.get(1), 5000, tiposRecibo.get(1), clientes.get(1), new ArrayList<>(), monedas.get(1), empresas.get(1))).getTextoError());

        } catch (SQLException ex) {
            fail("Fallo en testUPDATE");
        } catch (Exception ex) {
            fail("Fallo en testUPDATE");
        }
    }
    
    @Test
    public void testSELECT() {
        try {
            assertTrue(this.op.buscar(" WHERE idFactura=5 ", null).size() == 1); /*Búsqueda de una factura*/
            assertTrue(this.op.buscar(null, null).size()>13); /*Búsqueda de todas las facturas*/
            assertTrue(this.op.buscar(" WHERE idFactura=5000 ", null).isEmpty()); /*Búsqueda sin resultados*/

        } catch (SQLException ex) {
            fail("Fallo en testSELECT");
        } catch (Exception ex) {
            fail("Fallo en testSELECT");
        }
    }
    
    @Test
    public void testDELETE(){
/*Precarga de fechas*/
        ArrayList<DTOFechas> fechasPago = new ArrayList<>();
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));

        ArrayList<DTOFechas> fechasVencimiento = new ArrayList<>();
        fechasVencimiento.add(new DTOFechas(new Fecha(25,10,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,3,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioInicio = new ArrayList<>();
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,9,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,2,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioFin = new ArrayList<>();
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(30,9,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(29,2,2020)));
        /*Precarga de fechas*/
        
        List<Double> montos = Arrays.asList(300.0,500.0);
        List<String> tiposRecibo = Arrays.asList("Periodica", "A demanda");
        List<Principal> clientes = Arrays.asList(new Principal(4),new Principal(6));
        List<Moneda> monedas = Arrays.asList(new Moneda("UYU"),new Moneda("UYU"));
        List<Empresa> empresas = Arrays.asList(new Empresa("526283747346"),new Empresa("526283747346"));
        
        
        
        try{
            /*INSERTAR LAS 2 FACTURAS*/
            this.op.insertar(new Factura(-1,fechasPago.get(0), fechasVencimiento.get(0), fechasPeriodoServicioInicio.get(0), fechasPeriodoServicioFin.get(0), montos.get(0), tiposRecibo.get(0), clientes.get(0), null, monedas.get(0), empresas.get(0)));
            this.op.insertar(new Factura(-1,fechasPago.get(1), fechasVencimiento.get(1), fechasPeriodoServicioInicio.get(1), fechasPeriodoServicioFin.get(1), montos.get(1), tiposRecibo.get(1), clientes.get(1), null, monedas.get(1), empresas.get(1)));

            /*RECUPERAR SUS ID QUE SON AUTOINCREMENTALES*/
            int idA = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(0).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(0).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(0).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(0).getFechaAStr(1)+"' and monto = '"+montos.get(0)+"' and tipoRecibo = '"+tiposRecibo.get(0)+"' and nroCliente='"+clientes.get(0).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(0).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();
            int idB = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(1).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(1).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(1).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(1).getFechaAStr(1)+"' and monto = '"+montos.get(1)+"' and tipoRecibo = '"+tiposRecibo.get(1)+"' and nroCliente='"+clientes.get(1).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(1).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();

            /*BORRAR LAS FACTURAS*/
            assertEquals("NOERROR", this.op.borrar(new Factura(idA)).getTextoError());
            assertEquals("NOERROR", this.op.borrar(new Factura(idB)).getTextoError());

        } catch (SQLException ex) {
            fail("Fallo en testDELETE");
        } catch (Exception ex) {
            fail("Fallo en testDELETE");
        }
    }
    @Test
    public void testDELETEMULTIPLE(){
        ArrayList<DTOFechas> fechasPago = new ArrayList<>();
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));
        fechasPago.add(new DTOFechas(new Fecha(1,1,1970)));

        ArrayList<DTOFechas> fechasVencimiento = new ArrayList<>();
        fechasVencimiento.add(new DTOFechas(new Fecha(25,10,2020)));
        fechasVencimiento.add(new DTOFechas(new Fecha(25,3,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioInicio = new ArrayList<>();
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,9,2020)));
        fechasPeriodoServicioInicio.add(new DTOFechas(new Fecha(1,2,2020)));
        
        ArrayList<DTOFechas> fechasPeriodoServicioFin = new ArrayList<>();
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(30,9,2020)));
        fechasPeriodoServicioFin.add(new DTOFechas(new Fecha(29,2,2020)));
        /*Precarga de fechas*/
        
        List<Integer> montos = Arrays.asList(300,500);
        List<String> tiposRecibo = Arrays.asList("Periodica", "A demanda");
        List<Principal> clientes = Arrays.asList(new Principal(4),new Principal(6));
        List<Moneda> monedas = Arrays.asList(new Moneda("UYU"),new Moneda("UYU"));
        List<Empresa> empresas = Arrays.asList(new Empresa("526283747346"),new Empresa("526283747346"));
        
        
        
        try{
            /*INSERTAR LAS 2 FACTURAS*/
            this.op.insertar(new Factura(-1,fechasPago.get(0), fechasVencimiento.get(0), fechasPeriodoServicioInicio.get(0), fechasPeriodoServicioFin.get(0), montos.get(0), tiposRecibo.get(0), clientes.get(0), new ArrayList<>(), monedas.get(0), empresas.get(0)));
            this.op.insertar(new Factura(-1,fechasPago.get(1), fechasVencimiento.get(1), fechasPeriodoServicioInicio.get(1), fechasPeriodoServicioFin.get(1), montos.get(1), tiposRecibo.get(1), clientes.get(1), new ArrayList<>(), monedas.get(1), empresas.get(1)));

            /*RECUPERAR SUS ID QUE SON AUTOINCREMENTALES*/
            int idA = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(0).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(0).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(0).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(0).getFechaAStr(1)+"' and monto = '"+montos.get(0)+"' and tipoRecibo = '"+tiposRecibo.get(0)+"' and nroCliente='"+clientes.get(0).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(0).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();
            int idB = this.op.buscar(" WHERE fechaPago='"+fechasPago.get(1).getFechaAStr(1)+"' and fechaVencimiento='"+fechasVencimiento.get(1).getFechaAStr(1)+"' and periodoServicioInicio='"+fechasPeriodoServicioInicio.get(1).getFechaAStr(1)+"' and periodoServicioFin='"+fechasPeriodoServicioFin.get(1).getFechaAStr(1)+"' and monto = '"+montos.get(1)+"' and tipoRecibo = '"+tiposRecibo.get(1)+"' and nroCliente='"+clientes.get(1).getNroCliente()+"' and identificacionTributaria = '"+empresas.get(1).getIdentificacionTributaria()+"' ", null).get(0).getIdFactura();
            
            ArrayList<Integer> listaIds = new ArrayList<>();
            listaIds.add(idA);
            listaIds.add(idB);
            /*BORRAR LAS FACTURAS*/
            assertEquals("NOERROR", this.op.borradoMultiplePorIds(listaIds).getTextoError());
            
        } catch (SQLException ex) {
            fail("Fallo en testDELETEMULTIPLE");
        } catch (Exception ex) {
            fail("Fallo en testDELETEMULTIPLE");
        }

    }
}
    
