package com.tdt.easyroute.Clases;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.tdt.easyroute.Interface.AsyncResponseSO;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class ConexionWS_SO extends AsyncTask<String,Integer,SoapObject> {

    private Context context;

    private String conexion="true";
    public AsyncResponseSO delegate = null;
    public ArrayList<PropertyInfo> propertyInfos = null;
    private ParametrosWS parametrosWS;

    public ConexionWS_SO(Context context, String metodo) {
        this.context = context;
        parametrosWS = new ParametrosWS(metodo, context);
    }

    private ProgressDialog progreso;

    @Override protected void onPreExecute() {
        progreso = new ProgressDialog(context);
        progreso.setMessage("Conectando...");
        progreso.setCancelable(false);
        progreso.show();
    }

    @Override
    protected SoapObject doInBackground(String... strings) {

        SoapObject result = null;

        conexion="true";

        try {
            //Metodo al que se accede
            SoapObject Solicitud = new SoapObject(parametrosWS.getNAMESPACES(), parametrosWS.getMETODO());

            //Agrega los parametros que recibe el metodo (si es que recibe)
            if(propertyInfos!=null)
            {
                for (int i = 0; i < propertyInfos.size(); i++) {
                    Solicitud.addProperty(propertyInfos.get(i));
                }
            }

            SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            Envoltorio.dotNet = true;
            Envoltorio.setOutputSoapObject(Solicitud);
            HttpTransportSE TransporteHttp = new HttpTransportSE(parametrosWS.getURL(),parametrosWS.getTIMEOUT());

            try {

                TransporteHttp.call(parametrosWS.getSOAP_ACTION(), Envoltorio);
                SoapObject response = (SoapObject) Envoltorio.getResponse();

                if(response!=null)
                {
                    result=response;
                }

            } catch (Exception e) {
                Log.d("salida","error: "+e.getMessage());
                conexion = "Error: "+ e.getMessage();
            }

        }catch (Exception e)
        {
            Log.d("salida","error: "+e.getMessage());
            conexion = "Error: "+ e.getMessage();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        progreso.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(SoapObject result) {
        super.onPostExecute(result);

        progreso.dismiss();

        delegate.processFinish(result, conexion);

    }
}
