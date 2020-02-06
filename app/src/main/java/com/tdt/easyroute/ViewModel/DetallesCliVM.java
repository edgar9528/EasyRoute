package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetallesCliVM extends ViewModel {

    private MutableLiveData<Boolean> bt_ruta = new MutableLiveData<>();
    private MutableLiveData<String> ubiCliente = new MutableLiveData<>();

    public LiveData<String> getUbiCliente() {
        return ubiCliente;
    }

    public void setUbiCliente(String UbiCliente) {
        ubiCliente.setValue( UbiCliente );
    }

    public LiveData<Boolean> getBt_ruta() {
        return bt_ruta;
    }

    public void setBt_ruta(Boolean Bt_ruta) {
        bt_ruta.setValue( Bt_ruta );
    }





}