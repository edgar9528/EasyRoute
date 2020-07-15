package com.tdt.easyroute.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;

import java.util.ArrayList;

public class PedidosVM extends ViewModel
{
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dtProductos = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableLC.ProductosPed> > dgPro2 = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableWS.FormasPago> > formasPago = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableLC.EnvasesAdeudo>> dgDeudaEnv = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.EnvasesPed>> dgEnvase = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.EnvasesPreventa>> dgEnvasePrev = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.DgPagos> > dgPagos = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.DgAbonos> > dgAbonos = new MutableLiveData<>();
    private final MutableLiveData< ArrayList<DataTableLC.Creditos> > dgCreditos = new MutableLiveData<>();

    private final MutableLiveData<String> producto = new MutableLiveData<>();
    private final MutableLiveData<String> estadoCredito = new MutableLiveData<>();
    private final MutableLiveData<String> adeudoN = new MutableLiveData<>();
    private final MutableLiveData<String> adeudoE = new MutableLiveData<>();
    private final MutableLiveData<String> totAbono = new MutableLiveData<>();
    private final MutableLiveData<String> txtSubtotal2 = new MutableLiveData<>();
    private final MutableLiveData<String> txtSaldoDeudaEnv = new MutableLiveData<>();
    private final MutableLiveData<String> txtContado = new MutableLiveData<>();
    private final MutableLiveData<String> txtLimCred = new MutableLiveData<>();
    private final MutableLiveData<String> txtVencido = new MutableLiveData<>();
    private final MutableLiveData<String> txtSaldoCredito = new MutableLiveData<>();
    private final MutableLiveData<Double> txtSubEnv = new MutableLiveData<>();
    private final MutableLiveData<String> txtVenta = new MutableLiveData<>();
    private final MutableLiveData<String> txtSaldo = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<DataTableLC.AdeudoNormal>> dgANormal = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<DataTableLC.AdeudoNormal>> dgAEspecial = new MutableLiveData<>();

    public LiveData<ArrayList<DataTableLC.ProductosPed>> getDtProductos(){
        return dtProductos;
    }
    public void setDtProductos(ArrayList<DataTableLC.ProductosPed> DtProductos){
        dtProductos.setValue(DtProductos);
    }

    public LiveData<ArrayList<DataTableLC.ProductosPed>> getDgPro2(){
        return dgPro2;
    }
    public void setDgPro2(ArrayList<DataTableLC.ProductosPed> DgPro2){
        dgPro2.setValue(DgPro2);
    }

    public LiveData<String> getProducto(){
        return producto;
    }
    public void setProducto(String Producto){
        producto.setValue(Producto);
    }

    public LiveData<ArrayList<DataTableWS.FormasPago>> getFormasPago() {
        return formasPago;
    }
    public void setFormasPago(ArrayList<DataTableWS.FormasPago> formasPago){
        this.formasPago.setValue(formasPago);
    }

    public LiveData<ArrayList<DataTableLC.EnvasesPed>> getDgEnvase(){
        return dgEnvase;
    }
    public void setDgEnvase(ArrayList<DataTableLC.EnvasesPed> DgEnvase){
        dgEnvase.setValue(DgEnvase);
    }

    public LiveData<ArrayList<DataTableLC.EnvasesPreventa>> getDgEnvasePrev(){
        return dgEnvasePrev;
    }
    public void setDgEnvasePrev(ArrayList<DataTableLC.EnvasesPreventa> DgEnvasePrev){
        dgEnvasePrev.setValue(DgEnvasePrev);
    }

    public LiveData<String> getEstadoCredito(){
        return estadoCredito;
    }
    public void setEstadoCredito(String estadoCredito){
        this.estadoCredito.setValue(estadoCredito);
    }

    public LiveData<String> getAdeudoN(){
        return adeudoN;
    }
    public void setAdeudoN(String adeudoN){
        this.adeudoN.setValue(adeudoN);
    }

    public LiveData<String> getTotAbono(){
        return totAbono;
    }
    public void setTotAbono(String totAbono){
        this.totAbono.setValue(totAbono);
    }

    public LiveData<String> getTxtSubtotal2(){
        return txtSubtotal2;
    }
    public void setTxtSubtotal2(String txtSubtotal2){
        this.txtSubtotal2.setValue(txtSubtotal2);
    }

    public LiveData<String> getTxtSaldoDeudaEnv(){
        return txtSaldoDeudaEnv;
    }
    public void setTxtSaldoDeudaEnv(String txtSaldoDeudaEnv){
        this.txtSaldoDeudaEnv.setValue(txtSaldoDeudaEnv);
    }

    public LiveData<String> getTxtContado(){
        return txtContado;
    }
    public void setTxtContado(String txtContado){
        this.txtContado.setValue(txtContado);
    }

    public LiveData<String> getTxtLimCred(){
        return txtLimCred;
    }
    public void setTxtLimCred(String txtLimCred){
        this.txtLimCred.setValue(txtLimCred);
    }

    public LiveData<String> getTxtVencido(){
        return txtVencido;
    }
    public void setTxtVencido(String txtVencido){
        this.txtVencido.setValue(txtVencido);
    }

    public LiveData<ArrayList<DataTableLC.AdeudoNormal>> getDgAEspecial(){
        return dgAEspecial;
    }
    public void setDgAEspecial(ArrayList<DataTableLC.AdeudoNormal> dgAEspecial){
        this.dgAEspecial.setValue(dgAEspecial);
    }

    public LiveData<ArrayList<DataTableLC.AdeudoNormal>> getDgANormal(){
        return dgANormal;
    }
    public void setDgANormal(ArrayList<DataTableLC.AdeudoNormal> dgANormal){
        this.dgANormal.setValue(dgANormal);
    }

    public LiveData<String> getAdeudoE(){
        return adeudoE;
    }
    public void setAdeudoE(String adeudoE){
        this.adeudoE.setValue(adeudoE);
    }

    public LiveData<String> getTxtSaldoCredito(){
        return txtSaldoCredito;
    }
    public void setTxtSaldoCredito(String txtSaldoCredito){
        this.txtSaldoCredito.setValue(txtSaldoCredito);
    }

    public LiveData<ArrayList<DataTableLC.EnvasesAdeudo>> getDgDeudaEnv(){
        return dgDeudaEnv;
    }
    public void setDgDeudaEnv(ArrayList<DataTableLC.EnvasesAdeudo> dgDeudaEnv){
        this.dgDeudaEnv.setValue(dgDeudaEnv);
    }


    public LiveData<String> getTxtVenta(){
        return txtVenta;
    }
    public void setTxtVenta(String txtVenta){
        this.txtVenta.setValue(txtVenta);
    }

    public LiveData<String> getTxtSaldo(){
        return txtSaldo;
    }
    public void setTxtSaldo(String txtSaldo){
        this.txtSaldo.setValue(txtSaldo);
    }

    public LiveData<ArrayList<DataTableLC.DgPagos>> getDgPagos(){
        return dgPagos;
    }
    public void setDgPagos(ArrayList<DataTableLC.DgPagos> dgPagos){
        this.dgPagos.setValue(dgPagos);
    }

    public LiveData<ArrayList<DataTableLC.DgAbonos>> getDgAbonos(){
        return dgAbonos;
    }
    public void setDgAbonos(ArrayList<DataTableLC.DgAbonos> dgAbonos){
        this.dgAbonos.setValue(dgAbonos);
    }

    public LiveData<ArrayList<DataTableLC.Creditos>> getDgCreditos(){
        return dgCreditos;
    }
    public void setDgCreditos(ArrayList<DataTableLC.Creditos> DgCreditos){
        dgCreditos.setValue(DgCreditos);
    }

}