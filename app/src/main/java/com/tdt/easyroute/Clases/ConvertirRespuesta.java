package com.tdt.easyroute.Clases;
import android.util.Log;

import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class ConvertirRespuesta {

    private SoapObject respuesta;

    public ConvertirRespuesta(SoapObject respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario()
    {
        Usuario usuario = new Usuario();

        SoapObject permisos = (SoapObject) respuesta.getProperty(0);

        int canPermisos = permisos.getPropertyCount();

        List<Permisos> listPermisos= new ArrayList<>();

        for(int i=0; i<canPermisos;i++)
        {
            SoapObject so_permiso = (SoapObject) permisos.getProperty(i);
            Permisos permiso = new Permisos();

            permiso.setMod_cve_n( Integer.parseInt(so_permiso.getProperty(0).toString()) );
            permiso.setMod_desc_str(so_permiso.getProperty(1).toString());
            permiso.setLectura( Byte.parseByte(so_permiso.getProperty(2).toString()) );
            permiso.setEscritura( Byte.parseByte(so_permiso.getProperty(3).toString()) );
            permiso.setModificacion( Byte.parseByte(so_permiso.getProperty(4).toString()));
            permiso.setEliminacion( Byte.parseByte(so_permiso.getProperty(5).toString()) );

            listPermisos.add(permiso);
        }

        usuario.setPermisos(listPermisos);



        usuario.setUsuario(respuesta.getProperty(1).toString());
        usuario.setContrasena(respuesta.getProperty(2).toString());
        usuario.setRol( Integer.parseInt(respuesta.getProperty(3).toString()) );
        usuario.setNombrerol(respuesta.getProperty(4).toString());
        usuario.setEstatus(respuesta.getProperty(5).toString());
        usuario.setBloqueado( Byte.parseByte(respuesta.getProperty(6).toString()) );
        usuario.setNombre(respuesta.getProperty(7).toString());
        usuario.setAppat(respuesta.getProperty(8).toString());
        usuario.setApmat(respuesta.getProperty(9).toString());
        usuario.setEsadmin( Byte.parseByte(respuesta.getProperty(10).toString()) );

        Log.d("convresp",usuario.getAppat());
        Log.d("convresp",usuario.getApmat());
        Log.d("convresp",""+usuario.getEsadmin());

        return  usuario;
    }


}
