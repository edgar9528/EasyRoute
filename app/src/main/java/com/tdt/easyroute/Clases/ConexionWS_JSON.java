package com.tdt.easyroute.Clases;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class ConexionWS_JSON extends AsyncTask<String,Integer,Boolean> {

    private Context context;
    private String respuesta;
    public AsyncResponseJSON delegate = null;
    public ArrayList<PropertyInfo> propertyInfos = null;
    private ParametrosWS parametrosWS;
    public String servidor;
    public int timeout=0;
    private ProgressDialog progreso;

    public ConexionWS_JSON(Context context, String metodo) {
        this.context = context;
        parametrosWS = new ParametrosWS(metodo, context);
    }

    @Override protected void onPreExecute() {
        progreso = new ProgressDialog(context);
        progreso.setMessage(context.getResources().getString(R.string.msg_webservice));
        progreso.setCancelable(false);
        progreso.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        boolean result;
        respuesta=null;

        if(timeout!=0) //ESTO ES EN CASO DE HACER LA PRUEBA DEL SERVIDOR
        {
            parametrosWS.setURL(servidor);
            parametrosWS.setTIMEOUT(timeout);
        }

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

            try
            {
                TransporteHttp.call(parametrosWS.getSOAP_ACTION(), Envoltorio);
                SoapPrimitive response = (SoapPrimitive) Envoltorio.getResponse();

                if(response!=null&& !response.toString().equals("null"))
                {
                    respuesta = response.toString();
                }
                result = true;

            } catch (Exception e) {
                Log.d("salida","error: "+e.getMessage());
                respuesta = "Error: "+ e.getMessage();
                result=false;
            }

        }catch (Exception e)
        {
            Log.d("salida","error: "+e.getMessage());
            respuesta = "Error: "+ e.getMessage();
            result=false;
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        progreso.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        progreso.dismiss();

        delegate.recibirPeticion(result, respuesta);

    }

}