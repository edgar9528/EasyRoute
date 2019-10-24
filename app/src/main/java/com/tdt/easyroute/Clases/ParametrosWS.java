package com.tdt.easyroute.Clases;

import android.content.Context;
import android.content.SharedPreferences;
import com.tdt.easyroute.R;

public class ParametrosWS {

    private String URL;
    private String METODO;
    private String SOAP_ACTION;
    private String NAMESPACES;
    private int TIMEOUT;

    public ParametrosWS(String METODO, Context context) {
        this.METODO = METODO;
        SharedPreferences sharedPref = context.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);
        URL= sharedPref.getString("servidor","null");
        TIMEOUT = Integer.parseInt( sharedPref.getString("timeout","0") );
        NAMESPACES = context.getResources().getString(R.string.NAMESPACE);
        SOAP_ACTION=context.getResources().getString(R.string.NAMESPACE) + METODO;
    }

    public int getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(int TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMETODO() {
        return METODO;
    }

    public void setMETODO(String METODO) {
        this.METODO = METODO;
    }

    public String getSOAP_ACTION() {
        return SOAP_ACTION;
    }

    public void setSOAP_ACTION(String SOAP_ACTION) {
        this.SOAP_ACTION = SOAP_ACTION;
    }

    public String getNAMESPACES() {
        return NAMESPACES;
    }

    public void setNAMESPACES(String NAMESPACES) {
        this.NAMESPACES = NAMESPACES;
    }
}
