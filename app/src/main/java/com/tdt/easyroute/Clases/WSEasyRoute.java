package com.tdt.easyroute.Clases;

import android.content.Context;
import android.support.v4.os.IResultReceiver;
import android.util.Log;

import com.google.gson.JsonDeserializer;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.Usuario;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class WSEasyRoute implements AsyncResponseJSON {

    public String url="";
    public int TimeOut=2000;
    public Context context;

    /*
    public Usuario ValidarUsario(String Usuario,String Contrasena)
    {
        ConexionWS_JSON ws=new ConexionWS_JSON(context,"ValidarUsuarioJ");
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo piUser = new PropertyInfo();
        piUser.setName("usuarioJ");
        piUser.setValue(Usuario);
        propertyInfos.add(piUser);

        PropertyInfo piPass = new PropertyInfo();
        piPass.setName("contrasenaJ");
        piPass.setValue(Contrasena);
        propertyInfos.add(piPass);

        ws.propertyInfos=propertyInfos;
        //ws.delegate=((AsyncResponseJSON)context);



        ws.execute();
        while(ws.esperar){
            try {
                Thread.sleep( 100);
                Log.d("Salida",ws.getStatus().toString());
            }
            catch (Exception e)
            { }
        }
        Log.d("Salida",ws.getStatus().toString());
        if(!ws.result2.isEmpty())
        {
            return  ConvertirRespuesta.getUsuarioJson(ws.result2);
        }
        else
            return null;


    }

     */

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

    }
}
