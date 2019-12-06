package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Fragments.Clientes.OrdenaCliente.CambioDia;
import com.tdt.easyroute.Fragments.Clientes.OrdenaCliente.ClientesNodia;

public class OrdenaClientesVM extends ViewModel {

    private final MutableLiveData<ClientesNodia> clientesNoDia = new MutableLiveData<>();
    private final MutableLiveData<CambioDia> cambioDia = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectItemDia = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectItemFuera = new MutableLiveData<>();

    public LiveData<ClientesNodia> getClientesNodia(){
        return clientesNoDia;
    }

    public void setClientesNodia(ClientesNodia ClientesNodia){
        this.clientesNoDia.setValue(ClientesNodia);
    }

    public LiveData<Integer> getSelectItemDia(){
        return selectItemDia;
    }

    public void setSelectItemDia(int selectItem){
        this.selectItemDia.setValue(selectItem);
    }

    public LiveData<Integer> getSelectItemFuera(){
        return selectItemFuera;
    }

    public void setSelectItemFuera(int selectItem){
        this.selectItemFuera.setValue(selectItem);
    }


    public LiveData<CambioDia> getCambioDia(){
        return this.cambioDia;
    }

    public void setCambioDia(CambioDia cambioDia){
        this.cambioDia.setValue(cambioDia);
    }


}