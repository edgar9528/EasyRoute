package com.tdt.easyroute.Model;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Variables {
    public static class Startday
    {
        ArrayList<String> rutas,empresas,catalotos;
        String tipoRuta,ruta;
        Usuario user;
        ArrayList<DataTableLC.RutaTipo> lista_rutas;
        ArrayList<DataTableWS.Empresa> lista_empresas;

        public Startday() {
        }

        public ArrayList<String> getRutas() {
            return rutas;
        }

        public void setRutas(ArrayList<String> rutas) {
            this.rutas = rutas;
        }

        public ArrayList<String> getEmpresas() {
            return empresas;
        }

        public void setEmpresas(ArrayList<String> empresas) {
            this.empresas = empresas;
        }

        public ArrayList<String> getCatalotos() {
            return catalotos;
        }

        public void setCatalotos(ArrayList<String> catalotos) {
            this.catalotos = catalotos;
        }

        public String getTipoRuta() {
            return tipoRuta;
        }

        public void setTipoRuta(String tipoRuta) {
            this.tipoRuta = tipoRuta;
        }

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }

        public Usuario getUser() {
            return user;
        }

        public void setUser(Usuario user) {
            this.user = user;
        }

        public ArrayList<DataTableLC.RutaTipo> getLista_rutas() {
            return lista_rutas;
        }

        public void setLista_rutas(ArrayList<DataTableLC.RutaTipo> lista_rutas) {
            this.lista_rutas = lista_rutas;
        }

        public ArrayList<DataTableWS.Empresa> getLista_empresas() {
            return lista_empresas;
        }

        public void setLista_empresas(ArrayList<DataTableWS.Empresa> lista_empresas) {
            this.lista_empresas = lista_empresas;
        }
    }

}
