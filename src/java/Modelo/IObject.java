package Modelo;
public interface IObject<T> {
    public void adaptarCampos();
    public void validar() throws ProgramException;
    public String toString(int modo) throws ProgramException;
    
}
