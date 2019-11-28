package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientesVM extends ViewModel {

    private MutableLiveData<String> cli_cve_n = new MutableLiveData<>();

    public LiveData<String> getCli_cve() {
        return cli_cve_n;
    }

    public void setCle_cve(String Cli_cve) {
        this.cli_cve_n.setValue( Cli_cve );
    }
}