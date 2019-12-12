package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Fragments.Clientes.OrdenaCliente.ClientesNodia;
import com.tdt.easyroute.Model.DataTableLC;

import java.util.ArrayList;

public class SugeridoVM extends ViewModel {

    private final MutableLiveData< ArrayList<DataTableLC.Productos_Sug> > dtProd = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<String>  > lvPresentacion = new MutableLiveData<>();
    private MutableLiveData<String> familia = new MutableLiveData<>();
    private MutableLiveData<String> presentacion = new MutableLiveData<>();

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

    public LiveData<ArrayList<String>> getLvPresentacion(){
        return lvPresentacion;
    }

    public void setLvPresentacion(ArrayList<String> lvPresentacion){
        this.lvPresentacion.setValue(lvPresentacion);
    }


    public LiveData<ArrayList<DataTableLC.Productos_Sug>> getDtProd(){
        return dtProd;
    }

    public void setDtProd(ArrayList<DataTableLC.Productos_Sug> dtProd){
        this.dtProd.setValue(dtProd);
    }



}