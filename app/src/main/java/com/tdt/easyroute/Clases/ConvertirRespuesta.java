package com.tdt.easyroute.Clases;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdt.easyroute.Model.InfoRuta;
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;
import org.ksoap2.serialization.SoapObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConvertirRespuesta {

    public static Usuario getUsuario(SoapObject respuesta)
    {
        Usuario usuario = null;
        try
        {

            SoapObject permisos = (SoapObject) respuesta.getProperty(0);
            int canPermisos = permisos.getPropertyCount();

            List<Permisos> listPermisos = new ArrayList<>();

            for (int i = 0; i < canPermisos; i++) {
                SoapObject so_permiso = (SoapObject) permisos.getProperty(i);
                Permisos permiso = new Permisos();

                permiso.setMod_cve_n(Integer.parseInt(so_permiso.getProperty(0).toString()));
                permiso.setMod_desc_str(so_permiso.getProperty(1).toString());
                permiso.setLectura(Byte.parseByte(so_permiso.getProperty(2).toString()));
                permiso.setEscritura(Byte.parseByte(so_permiso.getProperty(3).toString()));
                permiso.setModificacion(Byte.parseByte(so_permiso.getProperty(4).toString()));
                permiso.setEliminacion(Byte.parseByte(so_permiso.getProperty(5).toString()));

                listPermisos.add(permiso);
            }

            usuario = new Usuario();

            usuario.setPermisos(listPermisos);

            usuario.setUsuario(respuesta.getProperty(1).toString());
            usuario.setContrasena(respuesta.getProperty(2).toString());
            usuario.setRol(Integer.parseInt(respuesta.getProperty(3).toString()));
            usuario.setNombrerol(respuesta.getProperty(4).toString());
            usuario.setEstatus(respuesta.getProperty(5).toString());
            usuario.setBloqueado(Byte.parseByte(respuesta.getProperty(6).toString()));
            usuario.setNombre(respuesta.getProperty(7).toString());
            usuario.setAppat(respuesta.getProperty(8).toString());
            usuario.setApmat(respuesta.getProperty(9).toString());
            usuario.setEsadmin(Byte.parseByte(respuesta.getProperty(10).toString()));

            Log.d("convresp", usuario.getAppat());
            Log.d("convresp", usuario.getApmat());
            Log.d("convresp", "" + usuario.getEsadmin());

        }catch (Exception e)
        {
            Log.d("salida","error: conv Usuario"+e.toString());
            usuario=null;
        }

        return  usuario;
    }

    public static Usuario getUsuarioJson(String respuesta)
    {
        Usuario usuario=null;

        try {
            usuario = new Gson().fromJson(respuesta, Usuario.class);
            Log.d("salida","USUARIO CREADO");
        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            usuario=null;
        }

        return usuario;
    }

    public static ArrayList<InfoRuta> getRutas(SoapObject respuesta) {
        ArrayList<InfoRuta> rutas =null;

        try {

            SoapObject soap = (SoapObject) respuesta.getProperty(1);
            SoapObject soRutas = (SoapObject) soap.getProperty(0);

            int canRutas = soRutas.getPropertyCount();

            rutas = new ArrayList<>();

            String cad,clave;
            for (int i = 0; i < canRutas; i++) {
                InfoRuta infoRuta = new InfoRuta();
                SoapObject soRuta = (SoapObject) soRutas.getProperty(i);

                cad = soRuta.toString();
                cad = cad.substring(8,cad.length()-2);


                String[] propiedades = cad.split(";");

                for(int j=0;j<propiedades.length;j++)
                {
                    String[] valores = propiedades[j].split("=");
                    clave=valores[0].trim();
                    switch (clave)
                    {
                        case "rut_cve_n":
                            infoRuta.setRut_cve_n(valores[1]);
                            break;
                        case "rut_desc_str":
                            infoRuta.setRut_desc_str(valores[1]);
                            break;
                        case "rut_orden_n":
                            infoRuta.setRut_orden_n(valores[1]);
                            break;
                        case "trut_cve_n":
                            infoRuta.setTrut_cve_n(valores[1]);
                            break;
                        case "asesor_cve_str":
                            infoRuta.setAsesor_cve_str(valores[1]);
                            break;
                        case "gerente_cve_str":
                            infoRuta.setGerente_cve_str(valores[1]);
                            break;
                        case "supervisor_cve_str":
                            infoRuta.setSupervisor_cve_str(valores[1]);
                            break;
                        case "est_cve_str":
                            infoRuta.setEst_cve_str(valores[1]);
                            break;
                        case "tco_cve_n":
                            infoRuta.setTco_cve_n(valores[1]);
                            break;
                        case "rut_prev_n":
                            infoRuta.setRut_prev_n(valores[1]);
                            break;
                        case "rut_capcamion_n":
                            infoRuta.setRut_capcamion_n(valores[1]);
                            break;
                        case "rut_idcteesp_n":
                            infoRuta.setRut_idcteesp_n(valores[1]);
                            break;
                        case "rut_tel_str":
                            infoRuta.setRut_tel_str(valores[1]);
                            break;
                        case "rut_invalidafrecuencia_n":
                            infoRuta.setRut_invalidafrecuencia_n(valores[1]);
                            break;
                        case "rut_productividad_n":
                            infoRuta.setRut_productividad_n(valores[1]);
                            break;
                        case "rut_efectividad_n":
                            infoRuta.setRut_efectividad_n(valores[1]);
                            break;
                        case "rut_mayorista_n":
                            infoRuta.setRut_mayorista_n(valores[1]);
                            break;
                        case "rut_fiestasyeventos_n":
                            infoRuta.setRut_fiestasyeventos_n(valores[1]);
                            break;
                        case "rut_auditoria_n":
                            infoRuta.setRut_auditoria_n(valores[1]);
                            break;
                        case "rut_promoce_n":
                            infoRuta.setRut_promoce_n(valores[1]);
                            break;
                    }
                }


                rutas.add(infoRuta);


                /*
                if(soRuta.getPropertyCount()==19) {

                    infoRuta.setRut_cve_n(soRuta.getProperty("rut_cve_n").toString());
                    infoRuta.setRut_desc_str(soRuta.getProperty("rut_desc_str").toString());
                    infoRuta.setRut_orden_n(soRuta.getProperty("rut_orden_n").toString());
                    infoRuta.setTrut_cve_n(soRuta.getProperty("trut_cve_n").toString());
                    infoRuta.setAsesor_cve_str(soRuta.getProperty("asesor_cve_str").toString());
                    infoRuta.setGerente_cve_str(soRuta.getProperty("gerente_cve_str").toString());
                    infoRuta.setSupervisor_cve_str(soRuta.getProperty("supervisor_cve_str").toString());
                    infoRuta.setEst_cve_str(soRuta.getProperty("est_cve_str").toString());
                    infoRuta.setRut_prev_n(soRuta.getProperty("rut_prev_n").toString());
                    infoRuta.setRut_capcamion_n(soRuta.getProperty("rut_capcamion_n").toString());
                    infoRuta.setRut_idcteesp_n(soRuta.getProperty("rut_idcteesp_n").toString());
                    infoRuta.setRut_tel_str(soRuta.getProperty("rut_tel_str").toString());
                    infoRuta.setRut_invalidafrecuencia_n(soRuta.getProperty("rut_invalidafrecuencia_n").toString());
                    infoRuta.setRut_productividad_n(soRuta.getProperty("rut_productividad_n").toString());
                    infoRuta.setRut_efectividad_n(soRuta.getProperty("rut_efectividad_n").toString());
                    infoRuta.setRut_mayorista_n(soRuta.getProperty("rut_mayorista_n").toString());
                    infoRuta.setRut_fiestasyeventos_n(soRuta.getProperty("rut_fiestasyeventos_n").toString());
                    infoRuta.setRut_auditoria_n(soRuta.getProperty("rut_auditoria_n").toString());
                    infoRuta.setRut_promoce_n(soRuta.getProperty("rut_promoce_n").toString());

                    rutas.add(infoRuta);
                }


                 */

                /*
                "rut_cve_n"
                "rut_desc_str"
                "rut_orden_n"
                "trut_cve_n"
                "asesor_cve_str"
                "gerente_cve_str"
                "supervisor_cve_str"
                "est_cve_str"
                "tco_cve_n"
                "tco_cve_n"
                "rut_prev_n"*/

                /*
                infoRuta.setRut_cve_n(soRuta.getProperty("rut_cve_n").toString());
                infoRuta.setRut_desc_str(soRuta.getProperty("rut_desc_str").toString());
                infoRuta.setRut_orden_n(soRuta.getProperty("rut_orden_n").toString());
                infoRuta.setTrut_cve_n(soRuta.getProperty("trut_cve_n").toString());
                infoRuta.setAsesor_cve_str(soRuta.getProperty("asesor_cve_str").toString());
                infoRuta.setGerente_cve_str(soRuta.getProperty("gerente_cve_str").toString());
                infoRuta.setSupervisor_cve_str(soRuta.getProperty("supervisor_cve_str").toString());
                infoRuta.setEst_cve_str(soRuta.getProperty("est_cve_str").toString());
                infoRuta.setRut_prev_n(soRuta.getProperty("rut_prev_n").toString());
                 */

            }

        }catch (Exception e)
        {
            Log.d("salida","error: conv Rutas"+e.toString());
            rutas=null;
        }

        return  rutas;
    }

    public static ArrayList<InfoRuta> getRutasJson(String respuesta)
    {

        ArrayList<InfoRuta> rutas=null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<InfoRuta> >(){}.getType();
            rutas = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            rutas=null;
        }

        return rutas;

    }


}
