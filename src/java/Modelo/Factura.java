package Modelo;

import Resources.DTOs.DTOFechas;
import java.util.ArrayList;

public class Factura implements IObject<Factura>{
/*Estado*/
private int idFactura;
private DTOFechas fechaPago;
private DTOFechas fechaEmision;
private DTOFechas fechaVencimiento;
private DTOFechas periodoServicioInicio;
private DTOFechas periodoServicioFin;
private double monto;
private String tipoRecibo;
private Cliente clienteAsociado;
private ArrayList<Suscripcion> listaSuscripciones;
private Moneda monedaAsociada;
private Empresa empresaAsociada;
/*Estado*/

/*Constructores*/
/*ID -1*/
public Factura(DTOFechas fechaPago, DTOFechas fechaEmision, DTOFechas fechaVencimiento, DTOFechas periodoServicioInicio, DTOFechas periodoServicioFin, double monto, String tipoRecibo, Cliente clienteAsociado, ArrayList<Suscripcion> listaSuscripciones, Moneda monedaAsociada, Empresa empresaAsociada) {
        this.idFactura = -1;
        this.fechaPago = fechaPago;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.periodoServicioInicio = periodoServicioInicio;
        this.periodoServicioFin = periodoServicioFin;
        this.monto = monto;
        this.tipoRecibo = tipoRecibo;
        this.clienteAsociado = clienteAsociado;
        this.listaSuscripciones = listaSuscripciones;
        this.monedaAsociada = monedaAsociada;
        this.empresaAsociada = empresaAsociada;
        adaptarCampos();
    }

/*FULL*/
    public Factura(int idFactura, DTOFechas fechaPago, DTOFechas fechaEmision, DTOFechas fechaVencimiento, DTOFechas periodoServicioInicio, DTOFechas periodoServicioFin, double monto, String tipoRecibo, Cliente clienteAsociado, ArrayList<Suscripcion> listaSuscripciones, Moneda monedaAsociada, Empresa empresaAsociada) {
        this.idFactura = idFactura;
        this.fechaPago = fechaPago;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.periodoServicioInicio = periodoServicioInicio;
        this.periodoServicioFin = periodoServicioFin;
        this.monto = monto;
        this.tipoRecibo = tipoRecibo;
        this.clienteAsociado = clienteAsociado;
        this.listaSuscripciones = listaSuscripciones;
        this.monedaAsociada = monedaAsociada;
        this.empresaAsociada = empresaAsociada;
        adaptarCampos();
    }

/*ID -1 SIN MONEDA, EMPRESA, LISTA SUSCRIPCIONES Y CLIENTE*/
    public Factura(DTOFechas fechaPago, DTOFechas fechaEmision, DTOFechas fechaVencimiento, DTOFechas periodoServicioInicio, DTOFechas periodoServicioFin, double monto, String tipoRecibo) {    
        this.fechaPago = fechaPago;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.periodoServicioInicio = periodoServicioInicio;
        this.periodoServicioFin = periodoServicioFin;
        this.monto = monto;
        this.listaSuscripciones = new ArrayList<>();
        this.tipoRecibo = tipoRecibo;
        adaptarCampos();
    }
/*TODO MENOS FECHA EMISION (QUE ES AUTOMÁTICA EN LA BASE)*/
        public Factura(int idFactura, DTOFechas fechaPago, DTOFechas fechaVencimiento, DTOFechas periodoServicioInicio, DTOFechas periodoServicioFin, double monto, String tipoRecibo, Cliente clienteAsociado, ArrayList<Suscripcion> listaSuscripciones, Moneda monedaAsociada, Empresa empresaAsociada) {
        this.idFactura = idFactura;
        this.fechaPago = fechaPago;
        this.fechaVencimiento = fechaVencimiento;
        this.periodoServicioInicio = periodoServicioInicio;
        this.periodoServicioFin = periodoServicioFin;
        this.monto = monto;
        this.tipoRecibo = tipoRecibo;
        this.clienteAsociado = clienteAsociado;
        this.listaSuscripciones = listaSuscripciones;
        this.monedaAsociada = monedaAsociada;
        this.empresaAsociada = empresaAsociada;
        adaptarCampos();
    }
/*SOLO ID*/
        public Factura(int idFactura){
            this.idFactura = idFactura;
            this.listaSuscripciones = new ArrayList<>();
        }   
/*Constructores*/
/*Comportamiento*/
    @Override
    public void adaptarCampos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validar() throws ProgramException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString(int modo) throws ProgramException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*Comportamiento*/
/*Getters y Setters*/
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public DTOFechas getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(DTOFechas fechaPago) {
        this.fechaPago = fechaPago;
    }

    public DTOFechas getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(DTOFechas fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public DTOFechas getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(DTOFechas fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public DTOFechas getPeriodoServicioInicio() {
        return periodoServicioInicio;
    }

    public void setPeriodoServicioInicio(DTOFechas periodoServicioInicio) {
        this.periodoServicioInicio = periodoServicioInicio;
    }

    public DTOFechas getPeriodoServicioFin() {
        return periodoServicioFin;
    }

    public void setPeriodoServicioFin(DTOFechas periodoServicioFin) {
        this.periodoServicioFin = periodoServicioFin;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public Cliente getClienteAsociado() {
        return clienteAsociado;
    }

    public void setClienteAsociado(Cliente clienteAsociado) {
        this.clienteAsociado = clienteAsociado;
    }

    public ArrayList<Suscripcion> getListaSuscripciones() {
        return listaSuscripciones;
    }

    public void setListaSuscripciones(ArrayList<Suscripcion> listaSuscripciones) {
        this.listaSuscripciones = listaSuscripciones;
    }

    public Moneda getMonedaAsociada() {
        return monedaAsociada;
    }

    public void setMonedaAsociada(Moneda monedaAsociada) {
        this.monedaAsociada = monedaAsociada;
    }

    public Empresa getEmpresaAsociada() {
        return empresaAsociada;
    }

    public void setEmpresaAsociada(Empresa empresaAsociada) {
        this.empresaAsociada = empresaAsociada;
    }
/*Getters y Setters*/



    
}
