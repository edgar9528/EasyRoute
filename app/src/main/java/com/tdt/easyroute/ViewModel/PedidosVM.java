package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Model.DataTableLC;

import java.util.ArrayList;

public class PedidosVM extends ViewModel
{
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dtProductos = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dgPro2 = new MutableLiveData<>();
    private final MutableLiveData<String> producto = new MutableLiveData<>();

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



}