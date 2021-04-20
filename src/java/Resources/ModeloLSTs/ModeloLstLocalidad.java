//package Resources.ModeloLSTs;
//
//import Modelo.Localidad;
//import java.util.ArrayList;
//import javax.swing.AbstractListModel;
//
//public class ModeloLstLocalidad extends AbstractListModel {
//
//    ArrayList<Localidad> lista;
//
//    public ModeloLstLocalidad() {
//        lista = new ArrayList<>();
//    }
//
//    @Override
//    public int getSize() {
//        return lista.size();
//    }
//
//    @Override
//    public Object getElementAt(int index) {
//        return lista.get(index);
//    }
//
//    public void addLocalidad(Localidad cat) {
//        lista.add(cat);
//        this.fireIntervalAdded(this, getSize(), getSize() + 1);
//    }
//    public void deleteLocalidad(Localidad cat){
//        lista.remove(cat);
//        this.fireIntervalRemoved(cat, getSize(), getSize() +1);
//    }
//    public void cargarLocalidades(ArrayList<Localidad> tiposHab){
//        for(Localidad h: tiposHab){
//            this.addLocalidad(h);
//        }
//    }
//
//    public ArrayList<Localidad> getLista() {
//        return lista;
//    }
//    
//}