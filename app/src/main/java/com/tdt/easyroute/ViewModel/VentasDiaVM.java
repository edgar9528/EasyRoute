package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Fragments.Clientes.OrdenaCliente.ClientesNodia;
import com.tdt.easyroute.Model.DataTableLC;

import java.util.ArrayList;

public class VentasDiaVM extends ViewModel {

    private final MutableLiveData<ArrayList<DataTableLC.Dtcobros> > dtCobros = new MutableLiveData<>();

    public LiveData<ArrayList<DataTableLC.Dtcobros>> getDtCobros(){
        return dtCobros;
    }

    public void setDtCobros(ArrayList<DataTableLC.Dtcobros> Dtcobros){
        this.dtCobros.setValue(Dtcobros);
    }

}