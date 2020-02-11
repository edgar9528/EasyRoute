package com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente;
import com.tdt.easyroute.Model.DataTableLC;
import java.util.ArrayList;
public class ClientesNodia {

    private ArrayList<DataTableLC.ClientesOrdenar> clientes=null;
    private boolean conDatos;
    private String filtro;

    public ClientesNodia(ArrayList<DataTableLC.ClientesOrdenar> clientes, boolean conDatos, String filtro) {
        this.clientes = clientes;
        this.conDatos = conDatos;
        this.filtro = filtro;
    }

    public ArrayList<DataTableLC.ClientesOrdenar> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<DataTableLC.ClientesOrdenar> clientes) {
        this.clientes = clientes;
    }

    public boolean isConDatos() {
        return conDatos;
    }

    public void setConDatos(boolean conDatos) {
        this.conDatos = conDatos;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}
