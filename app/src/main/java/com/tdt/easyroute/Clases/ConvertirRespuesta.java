package com.tdt.easyroute.Clases;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.DataTableLC;
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

    public static ArrayList<DataTableWS.Ruta> getRutas(SoapObject respuesta) {
        ArrayList<DataTableWS.Ruta> rutas =null;

        try {

            SoapObject soap = (SoapObject) respuesta.getProperty(1);
            SoapObject soRutas = (SoapObject) soap.getProperty(0);

            int canRutas = soRutas.getPropertyCount();

            rutas = new ArrayList<>();

            String cad,clave;
            for (int i = 0; i < canRutas; i++) {
                DataTableWS.Ruta infoRuta = new DataTableWS.Ruta();
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

    public static ArrayList<DataTableWS.Ruta> getRutasJson(String respuesta)
    {

        ArrayList<DataTableWS.Ruta> rutas=null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Ruta> >(){}.getType();
            rutas = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            rutas=null;
        }

        return rutas;

    }

    public static ArrayList<DataTableWS.Empresa> getEmpresasJson(String respuesta)
    {
        ArrayList<DataTableWS.Empresa> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Empresa> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.Estatus> getEstatusJson(String respuesta)
    {
        ArrayList<DataTableWS.Estatus> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Estatus> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Roles> getRolesJson(String respuesta)
    {
        ArrayList<DataTableWS.Roles> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Roles> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.RolesModulos> getRolesModulosJson(String respuesta)
    {
        ArrayList<DataTableWS.RolesModulos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.RolesModulos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Modulos> getModulosJson(String respuesta)
    {
        ArrayList<DataTableWS.Modulos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Modulos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.Usuarios> getUsuariosJson(String respuesta)
    {
        ArrayList<DataTableWS.Usuarios> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Usuarios> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.TipoRuta> getTipoRutaJson(String respuesta)
    {
        ArrayList<DataTableWS.TipoRuta> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.TipoRuta> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.CondicionesVenta> getCondicionesVentaJson(String respuesta)
    {
        ArrayList<DataTableWS.CondicionesVenta> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.CondicionesVenta> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Productos> getProductosJson(String respuesta)
    {
        ArrayList<DataTableWS.Productos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Productos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.ListaPrecios> getListaPreciosJson(String respuesta)
    {
        ArrayList<DataTableWS.ListaPrecios> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.ListaPrecios> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.PrecioProductos> getPrecioProductosJson(String respuesta)
    {
        ArrayList<DataTableWS.PrecioProductos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.PrecioProductos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.FormasPago> getFormasPagoJson(String respuesta)
    {
        ArrayList<DataTableWS.FormasPago> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.FormasPago> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.FrecuenciasVisita> getFrecuenciasVisitaJson(String respuesta)
    {
        ArrayList<DataTableWS.FrecuenciasVisita> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.FrecuenciasVisita> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Categorias> getCategoriasJson(String respuesta)
    {
        ArrayList<DataTableWS.Categorias> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Categorias> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Familias> getFamiliasJson(String respuesta)
    {
        ArrayList<DataTableWS.Familias> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Familias> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Presentaciones> getPresentacionesJson(String respuesta)
    {
        ArrayList<DataTableWS.Presentaciones> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Presentaciones> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.Promociones> getPromocionesJson(String respuesta)
    {
        ArrayList<DataTableWS.Promociones> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Promociones> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.PromocionesKit> getPromocionesKitJson(String respuesta)
    {
        ArrayList<DataTableWS.PromocionesKit> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.PromocionesKit> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableLC.VentasDet> getVentasDetJson(String respuesta)
    {
        ArrayList<DataTableLC.VentasDet> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.VentasDet> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        if(al!=null) {
            if(al.size()>0)
                return al;
            else
                return null;
        }
        else
            return null;
    }


    public static DataTableLC.Pagos getPagosJson(String respuesta)
    {
        ArrayList<DataTableLC.Pagos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Pagos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        if(al!=null) {
            if(al.size()>0)
                return al.get(0);
            else
                return null;
        }
        else
            return null;
    }


    public static DataTableLC.Creditos getCreditosJson(String respuesta)
    {
        ArrayList<DataTableLC.Creditos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Creditos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        if(al!=null) {
            if(al.size()>0)
                return al.get(0);
            else
                return null;
        }
        else
            return null;
    }

    public static ArrayList<DataTableLC.RutaTipo> getRutaTipoJson(String respuesta)
    {
        ArrayList<DataTableLC.RutaTipo> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.RutaTipo> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.Marcas> getMarcasJson(String respuesta)
    {
        ArrayList<DataTableWS.Marcas> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Marcas> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;

    }

    public static ArrayList<DataTableWS.Unidades> getUnidadesJson(String respuesta)
    {
        ArrayList<DataTableWS.Unidades> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Unidades> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;

    }

    public static ArrayList<DataTableWS.NivelCliente> getNivelCJson(String respuesta)
    {
        ArrayList<DataTableWS.NivelCliente> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.NivelCliente> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.MotivosNoVenta> getMotivosNoVentaJson(String respuesta)
    {
        ArrayList<DataTableWS.MotivosNoVenta> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.MotivosNoVenta> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.MotivosNoLectura> getMotivosNoLecturaJson(String respuesta)
    {
        ArrayList<DataTableWS.MotivosNoLectura> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.MotivosNoLectura> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }

    public static ArrayList<DataTableWS.Clientes> getClientesJson(String respuesta)
    {
        ArrayList<DataTableWS.Clientes> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Clientes> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.Clientes1> getClientes1Json(String respuesta)
    {
        ArrayList<DataTableLC.Clientes1> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Clientes1> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.Creditos> getCreditosWSJson(String respuesta)
    {
        ArrayList<DataTableWS.Creditos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Creditos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Direcciones> getDireccionesJson(String respuesta)
    {
        ArrayList<DataTableWS.Direcciones> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Direcciones> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.ClientesVentaMes> getClientesVentaJson(String respuesta)
    {
        ArrayList<DataTableWS.ClientesVentaMes> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.ClientesVentaMes> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }

        return al;
    }


    public static ArrayList<DataTableWS.Consignas> getConsignasJson(String respuesta)
    {
        ArrayList<DataTableWS.Consignas> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Consignas> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.ConsignasDet> getConsignasDetJson(String respuesta)
    {
        ArrayList<DataTableWS.ConsignasDet> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.ConsignasDet> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }


    public static DataTableWS.RetValInicioDia getRetValInicioDiaJson(String respuesta)
    {
        DataTableWS.RetValInicioDia dt=null;

        try {
            dt = new Gson().fromJson(respuesta,DataTableWS.RetValInicioDia.class);
        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            dt=null;
        }

        return dt;
    }

    public static ArrayList<DataTableWS.VisitaPreventa> getVisitaPreventaJson(String respuesta)
    {
        ArrayList<DataTableWS.VisitaPreventa> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.VisitaPreventa> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.Preventa> getPreventaJson(String respuesta)
    {
        ArrayList<DataTableWS.Preventa> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Preventa> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.PreventaDet> getPreventaDetJson(String respuesta)
    {
        ArrayList<DataTableWS.PreventaDet> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.PreventaDet> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.PreventaEnv> getPreventaEnvJson(String respuesta)
    {
        ArrayList<DataTableWS.PreventaEnv> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.PreventaEnv> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.PreventaPagos> getPreventaPagosJson(String respuesta)
    {
        ArrayList<DataTableWS.PreventaPagos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.PreventaPagos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.Recargas> getRecargasJson(String respuesta)
    {
        ArrayList<DataTableWS.Recargas> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Recargas> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.RecargasDet> getRecargasDetJson(String respuesta)
    {
        ArrayList<DataTableWS.RecargasDet> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.RecargasDet> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.EnvaseAut> getEnvaseAutJson(String respuesta)
    {
        ArrayList<DataTableLC.EnvaseAut> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.EnvaseAut> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.InvP> getInvPJson(String respuesta)
    {
        ArrayList<DataTableLC.InvP> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.InvP> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.Inv> getInvJson(String respuesta)
    {
        ArrayList<DataTableLC.Inv> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Inv> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }


    public static ArrayList<DataTableLC.Inventario> getInventarioJson(String respuesta)
    {
        ArrayList<DataTableLC.Inventario> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Inventario> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }


    public static ArrayList<DataTableLC.DtEnv> getDtEnvJson(String respuesta)
    {
        ArrayList<DataTableLC.DtEnv> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.DtEnv> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.BitacoraHH> getBitacoraHHJson(String respuesta)
    {
        ArrayList<DataTableLC.BitacoraHH> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.BitacoraHH> >(){}.getType();
            al = gson.fromJson(respuesta, listType);

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.Devoluciones> getDevolucionesJson(String respuesta)
    {
        ArrayList<DataTableWS.Devoluciones> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.Devoluciones> >(){}.getType();
            al = gson.fromJson(respuesta, listType);
            if(al.size()==0) return null;
        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableWS.DevolucionesDet> getDevolucionesDetJson(String respuesta)
    {
        ArrayList<DataTableWS.DevolucionesDet> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableWS.DevolucionesDet> >(){}.getType();
            al = gson.fromJson(respuesta, listType);
            if(al.size()==0) return null;
        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }

    public static ArrayList<DataTableLC.ClientesSaldo> getClientesSaldoJson(String respuesta)
    {
        ArrayList<DataTableLC.ClientesSaldo> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.ClientesSaldo> >(){}.getType();
            al = gson.fromJson(respuesta, listType);
            if(al.size()==0) return null;

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }


    public static ArrayList<DataTableLC.Saldos> getSaldosJson(String respuesta)
    {
        ArrayList<DataTableLC.Saldos> al =null;
        try {

            Gson gson = new Gson();
            Type listType = new TypeToken< ArrayList<DataTableLC.Saldos> >(){}.getType();
            al = gson.fromJson(respuesta, listType);
            if(al.size()==0) return null;

        }catch (Exception e)
        {
            Log.d("salida","error gson: "+e.toString());
            al=null;
        }
        return al;
    }



}
