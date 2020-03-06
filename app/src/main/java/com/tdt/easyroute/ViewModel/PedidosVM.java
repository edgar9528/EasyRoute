package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;

import java.util.ArrayList;

public class PedidosVM extends ViewModel
{
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dtProductos = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dgPro2 = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableWS.FormasPago> > formasPago = new MutableLiveData<>();
    private final MutableLiveData<String> producto = new MutableLiveData<>();
    private final MutableLiveData<String> estadoCredito = new MutableLiveData<>();
    private final MutableLiveData<String> adeudoN = new MutableLiveData<>();
    private final MutableLiveData<String> totAbono = new MutableLiveData<>();

    public LiveData<ArrayList<DataTableLC.ProductosPed>> getDtProductos(){
        return dtProductos;
    }
    public void setDtProductos(ArrayList<DataTableLC.ProductosPed> DtProductos){
        dtProductos.setValue(DtProductos);
    }

    public LiveData<ArrayList<DataTableLC.ProductosPed>> getDgPro2(){
        return dgPro2;
    }
    public void setDgPro2(ArrayList<DataTableLC.ProductosPed> DgPro2){
        dgPro2.setValue(DgPro2);
    }

    public LiveData<String> getProducto(){
        return producto;
    }
    public void setProducto(String Producto){
        producto.setValue(Producto);
    }

    public LiveData<ArrayList<DataTableWS.FormasPago>> getFormasPago() {
        return formasPago;
    }
    public void setFormasPago(ArrayList<DataTableWS.FormasPago> formasPago){
        this.formasPago.setValue(formasPago);
    }

    public LiveData<String> getEstadoCredito(){
        return estadoCredito;
    }
    public void setEstadoCredito(String estadoCredito){
        this.estadoCredito.setValue(estadoCredito);
    }


    public LiveData<String> getAdeudoN(){
        return adeudoN;
    }
    public void setAdeudoN(String adeudoN){
        this.adeudoN.setValue(adeudoN);
    }

    public LiveData<String> getTotAbono(){
        return totAbono;
    }
    public void setTotAbono(String totAbono){
        this.totAbono.setValue(totAbono);
    }



}