package com.tdt.easyroute.Model;

import java.util.ArrayList;

public class Variables {
    public static class Startday
    {
        String tipoRuta,ruta;
        Boolean sincro=false;

        public Startday() {
        }

        public Boolean getSincro() {
            return sincro;
        }

        public void setSincro(Boolean sincro) {
            this.sincro = sincro;
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

    }

}
