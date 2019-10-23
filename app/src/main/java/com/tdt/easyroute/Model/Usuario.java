package com.tdt.easyroute.Model;

import java.util.List;

public class Usuario {

    private String usuario;
    private String contrasena;
    private int rol;
    private String nombrerol;
    private String estatus;
    private byte bloqueado;
    private String nombre;
    private String appat;
    private String apmat;
    private byte esadmin;
    private List<Permisos> permisos;

    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if(usuario.contains("anyType{}"))
            this.usuario = "";
        else
            this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if(contrasena.contains("anyType{}"))
            this.contrasena = "";
        else
            this.contrasena = contrasena;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        if(nombrerol.contains("anyType{}"))
            this.nombrerol = "";
        else
            this.nombrerol = nombrerol;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        if(estatus.contains("anyType{}"))
            this.estatus = "";
        else
            this.estatus = estatus;
    }

    public byte getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(byte bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.contains("anyType{}"))
            this.nombre = "";
        else
            this.nombre = nombre;
    }

    public String getAppat() {
        return appat;
    }

    public void setAppat(String appat) {
        if(appat.contains("anyType{}"))
            this.appat = "";
        else
            this.appat = appat;
    }

    public String getApmat() {
        return apmat;
    }

    public void setApmat(String apmat) {
        if(apmat.contains("anyType{}"))
            this.apmat = "";
        else
            this.apmat = apmat;
    }

    public byte getEsadmin() {
        return esadmin;
    }

    public void setEsadmin(byte esadmin) {
        this.esadmin = esadmin;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }
}
