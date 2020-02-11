package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Model.DataTableLC;

import java.util.ArrayList;

public class VentasDiaVM extends ViewModel {

    private final MutableLiveData<ArrayList<DataTableLC.Dtcobros> > dtCobros = new MutableLiveData<>();
    private final MutableLiveData<String > botonClick = new MutableLiveData<>();

    public LiveData<ArrayList<DataTableLC.Dtcobros>> getDtCobros(){
        return dtCobros;
    }

    public void setDtCobros(ArrayList<DataTableLC.Dtcobros> Dtcobros){
        this.dtCobros.setValue(Dtcobros);
    }

    public LiveData<String> getBotonClick(){
        return botonClick;
    }

    public void setBotonClick(String BotonClick){
        this.botonClick.setValue(BotonClick);
    }

}