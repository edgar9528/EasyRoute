package com.tdt.easyroute.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente.ClientesNodia;

public class OrdenaClientesVM extends ViewModel {

    private final MutableLiveData<ClientesNodia> clientesNoDia = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectItemDia = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectItemFuera = new MutableLiveData<>();
    private final MutableLiveData<Integer> moverItem = new MutableLiveData<>();
    private final MutableLiveData<Boolean> enviar = new MutableLiveData<>();

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

    public LiveData<Integer> getMoverItem(){
        return moverItem;
    }

    public void setMoverItem(int moverItem){
        this.moverItem.setValue(moverItem);
    }

    public LiveData<Boolean> getEnviar(){
        return enviar;
    }

    public void setEnviar(boolean enviar){
        this.enviar.setValue(enviar);
    }


}