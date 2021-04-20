package WebServices;

import Datos.Database;
import java.sql.ResultSet;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
@WebService(serviceName = "IntegracionWebService")
public class IntegracionWebService {
    private Database database;
    @WebMethod(operationName = "validarAcceso")
    public boolean clienteTieneAcceso(@WebParam(name = "email") String txt) {
        database = Database.getInstancia();
        try {
            ResultSet rs = database.consultar("SELECT * from Principales, Clientes WHERE Principales.usuarioSistema = Clientes.usuarioSistema AND Principales.eliminado='N' AND Clientes.email='"+txt+"' AND Principales.servicioActivo='S';");
            return rs.next();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return false;
    }
}
