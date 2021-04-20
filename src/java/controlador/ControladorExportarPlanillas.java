package controlador;

//import Datos.OpDispositivo;
//import Datos.OpPersona;
//import Datos.OpSuscripcion;
//import Datos.OpTipoDispositivo;
//import Modelo.Dispositivo;
//import Modelo.Persona;
//import Modelo.Principal;
//import Modelo.Secundario;
//import Modelo.Suscripcion;
//import Modelo.TipoDispositivo;
//import controlador.Interfaces.IVistaExportarPlanillas;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//public class ControladorExportarPlanillas {
//    /*Estado*/
//    private IVistaExportarPlanillas vista;
//    private OpDispositivo opDispositivo;
//    private OpTipoDispositivo opTipoDispositivo;
//    private OpSuscripcion opSuscripcion;
//    private OpPersona opPersona;
//    /*Estado*/
//    
//    /*Constructores*/
//    public ControladorExportarPlanillas(IVistaExportarPlanillas vista){
//        this.vista = vista;
//        this.opPersona = new OpPersona("bentancor");
//        this.opSuscripcion = new OpSuscripcion("bentancor");
//        this.opTipoDispositivo = new OpTipoDispositivo("bentancor");
//        this.opDispositivo = new OpDispositivo("bentancor");
//    }
    /*Constructores*/
    
    /*Comportamiento*/
//    public OutputStream exportarPlanilla(String tabla){
//        String nombreArchivo = "";
//        String hoja = "";
//        XSSFWorkbook libro = new XSSFWorkbook();
//        XSSFSheet hoja1 = libro.createSheet(hoja);
//        String[] header = new String[]{};
//        String[][] document = new String[][]{};
//        OutputStream fileOut = null;
//        //Poner en negrita la cabecera
//        CellStyle style = libro.createCellStyle();
//        Font font = libro.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        try{
//        //Pasando datos al libro
//        switch(tabla){
//            case "Dispositivos":
//                nombreArchivo = "ListaDispositivos.xlsx";
//                hoja = "Dispositivos";
//
//                header = new String[]{"Nro de Serie", "Estado", "Tipo de Dispositivo", "Cliente asociado"};
//                ArrayList<Dispositivo> dispositivos = opDispositivo.obtenerTodos();
//                int m = 0;
//                for (Dispositivo d : dispositivos) {
//                    document[m][0] = d.getNroSerie();
//                    document[m][1] = d.getEstado();
//                    document[m][2] = d.getTipoDispositivo().toString(3);
//                    document[m][3] = d.getClienteAsociado().toString(2);
//                    m++;
//                }
//                break;
//            case "Principales":
//                nombreArchivo = "ListaTitulares.xlsx";
//                hoja = "Clientes Titulares";
//                header = new String[]{"Nro de Documento", "Nro de Cliente", "Nombre Completo", "Email", "Tipo de Documento"};
//                ArrayList<Persona> principales = opPersona.buscar(null, "Modelo.Principal");
//                int n = 0;
//                for (Persona p : principales) {
//                    Principal pr = (Principal) p;
//                    document[n][0] = pr.getNroDocumento();
//                    document[n][1] = pr.getNroCliente() + "";
//                    document[n][2] = pr.getNombreCompleto();
//                    document[n][3] = pr.getEmail();
//                    document[n][4] = pr.getTipoDocumento().toString(2);
//                    n++;
//                }
//                break;
//            case "Secundarios":
//                nombreArchivo = "ListaCuentasSecundarias.xlsx";
//                hoja="Cuentas Secundarias";
//                header = new String[]{"Documento Titular", "Nro de Cliente", "Nombre Completo", "Email"};
//                ArrayList<Persona> secundarios = opPersona.buscar(null, "Modelo.Secundario");
//                int b = 0;
//                for(Persona p: secundarios){
//                    Secundario se = (Secundario) p;
//                    document[b][0] = se.getPrincipalAsociado().getNroDocumento();
//                    document[b][1] = se.getNroCliente()+"";
//                    document[b][2] = se.getNombreCompleto();
//                    document[b][3] = se.getEmail();
//                    b++;
//                }
//            break;
//            case "TiposDispositivos":
//                nombreArchivo = "CatalogoDispositivos.xlsx";
//                hoja="Catalogo Dispositivos";
//                header = new String[]{"ID", "Modelo", "Nombre", "Tipo Comunicación"};
//                ArrayList<TipoDispositivo> tiposDispositivos = opTipoDispositivo.buscar(null, null);
//                int p = 0;
//                for(TipoDispositivo tp : tiposDispositivos){
//                    document[p][0] = tp.getIdTipoDispositivo()+"";
//                    document[p][1] = tp.getModelo();
//                    document[p][2] = tp.getNombre();
//                    document[p][3] = tp.getTipoComunicacion();
//                    p++;
//                }
//            break;
//            case "Suscripciones":
//                nombreArchivo = "ListaSuscripciones.xlsx";
//                hoja="Suscripciones";
//                header = new String[]{"ID", "Fecha inicio", "Fecha fin", "Tiempo contrato", "Activa"};
//                ArrayList<Suscripcion> suscripciones = opSuscripcion.buscar(null, null);
//                int u = 0;
//                for(Suscripcion s: suscripciones){
//                    document[u][0] = s.getIdSuscripcion()+"";
//                    document[u][1] = s.getFechaInicio().getFechaAStr(2);
//                    document[u][2] = s.getFechaFin().getFechaAStr(2);
//                    document[u][3] = s.getTiempoContratoStr();
//                    document[u][4] = s.getActivaStr();
//                    u++;
//                }  
//            break;
//        }
//            for (int i = 0; i <= document.length; i++) {
//                XSSFRow row = hoja1.createRow(i); //Creamos la fila
//                for (int j = 0; j < header.length; j++) {
//                    if (i == 0) { //Cabecera
//                        XSSFCell cell = row.createCell(j); //creamos celdas para la cabecera
//                        cell.setCellValue(header[j]); // contenido cabecera
//                    } else { //Contenido
//                        XSSFCell cell = row.createCell(j); //creamos celdas para el contenido
//                        cell.setCellValue(document[i - 1][j]); //Se agrega contenido
//                    }
//                }
//            }
//        //Generando el fichero
//            fileOut = new FileOutputStream(nombreArchivo);
//            libro.write(fileOut);
//            fileOut.close();
//            System.out.println("SE CREO EL FICHERO");
//        //Descargando el fichero
//            
//        }catch(Exception ex){
//            vista.mensajeError("exportarPlanillas.jsp", "Error al exportar la planilla Excel.");
//        }
//            return fileOut;
//
//    }
    /*Comportamiento*/
//}
