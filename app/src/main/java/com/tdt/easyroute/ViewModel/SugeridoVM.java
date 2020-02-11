package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Model.DataTableLC;

import java.util.ArrayList;

public class SugeridoVM extends ViewModel {

    private final MutableLiveData< ArrayList<DataTableLC.Productos_Sug> > dtProd = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.Sugerido> > dgSugerido = new MutableLiveData<>();

    private MutableLiveData<String> familia = new MutableLiveData<>();
    private MutableLiveData<String> presentacion = new MutableLiveData<>();
    private MutableLiveData<String> productoSKU = new MutableLiveData<>();
    private MutableLiveData<String> opcion = new MutableLiveData<>();

    private MutableLiveData<String[]> strBuscar = new MutableLiveData<>();
    private MutableLiveData<Boolean> borrar = new MutableLiveData<>();

    public void setOpcion(String opcion) {
        this.opcion.setValue(opcion);
    }

    public LiveData<String> getOpcion() {
        return opcion;
    }

    public void setBorrar(Boolean borrar) {
        this.borrar.setValue(borrar);
    }

    public LiveData<Boolean> getBorrar() {
        return borrar;
    }


    public void setStrBuscar(String[] strBuscar) {
        this.strBuscar.setValue(strBuscar);
    }

    public LiveData<String[]> getStrBuscar() {
        return strBuscar;
    }

    public void setProductoSKU(String productoSKU) {
        this.productoSKU.setValue(productoSKU);
    }

    public LiveData<String> getProductoSKU() {
        return productoSKU;
    }

    public void setFamilia(String familia) {
        this.familia.setValue(familia);
    }

    public LiveData<String> getFamilia() {
        return familia;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion.setValue(presentacion);
    }

    public LiveData<String> getPresentacion() {
        return presentacion;
    }

    public LiveData<ArrayList<DataTableLC.Productos_Sug>> getDtProd(){
        return dtProd;
    }

    public void setDtProd(ArrayList<DataTableLC.Productos_Sug> dtProd){
        this.dtProd.setValue(dtProd);
    }

    public LiveData<ArrayList<DataTableLC.Sugerido>> getDgSugerido(){
        return dgSugerido;
    }

    public void setDgSugerido(ArrayList<DataTableLC.Sugerido> dgSugerido){
        this.dgSugerido.setValue(dgSugerido);
    }

}