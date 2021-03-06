package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientesVM extends ViewModel {

    private MutableLiveData<String> cli_cve_n = new MutableLiveData<>();
    private MutableLiveData<String> negocio = new MutableLiveData<>();
    private MutableLiveData<String> razonSocial = new MutableLiveData<>();

    public LiveData<String> getCli_cve() {
        return cli_cve_n;
    }

    public void setCle_cve(String Cli_cve) {
        this.cli_cve_n.setValue( Cli_cve );
    }


    public LiveData<String> getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio.setValue( negocio );
    }

    public LiveData<String> getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial.setValue( razonSocial );
    }



}