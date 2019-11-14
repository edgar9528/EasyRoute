package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartdayVM extends ViewModel {

    private MutableLiveData<String> catalogos = new MutableLiveData<>();
    private MutableLiveData<String> tipoRuta = new MutableLiveData<>();
    private MutableLiveData<String> ruta = new MutableLiveData<>();
    private MutableLiveData<Boolean> sincro = new MutableLiveData<>();

    public void setCatalogos(String catalogos) {
        this.catalogos.setValue(catalogos);
    }

    public LiveData<String> getCatalogos() {
        return catalogos;
    }

    public LiveData<String> getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(String tipoRuta) {
        this.tipoRuta.setValue( tipoRuta );
    }

    public LiveData<String> getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta.setValue( ruta );
    }

    public void setSincro(Boolean sincro) {
        this.sincro.setValue( sincro );
    }

    public LiveData<Boolean> getSincro() {
        return sincro;
    }


}