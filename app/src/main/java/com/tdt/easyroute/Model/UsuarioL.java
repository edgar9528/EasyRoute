package com.tdt.easyroute.Model;

import java.util.ArrayList;

public class UsuarioL extends Usuario {

    public UsuarioL()
    {
        this.usuario = "";
        this.appat= "";
        this.apmat = "";
        this.permisos = null;
        this.bloqueado = 0;
        this.contrasena = "";
        this.esadmin = 0;
        this.estatus = "";
        this.nombre = "";
        this.nombrerol = "";
        this.rol = 0;
    }

    public UsuarioL(String usu,String contra,String nom,String app,String apm,
                    int idrol,String nomrol,byte esadm,String est,byte bloq)
    {
        this.usuario = usu;
        this.contrasena = contra;
        this.nombre = nom;
        this.appat = app;
        this.apmat = apm;
        this.rol = idrol;
        this.nombrerol = nomrol;
        this.permisos = new ArrayList<Permisos>();
        this.esadmin = esadm;
        this.estatus = est;
        this.bloqueado = bloq;

    }




}
