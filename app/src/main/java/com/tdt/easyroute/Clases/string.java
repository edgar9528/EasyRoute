package com.tdt.easyroute.Clases;

public class string {

    public static String formatSql(String consulta, String... parametros)
    {
        String ant="",des="";
        int con=0;

        do {
            for (int i = 0; i < consulta.length(); i++) {
                if (consulta.charAt(i) == '{') {
                    ant = consulta.substring(0, i);
                }

                if (consulta.charAt(i) == '}') {
                    des = consulta.substring(i + 1);
                    consulta = ant + parametros[con] + des;

                    break; //se termina el ciclo
                }
            }
            con++;

        }while (con<parametros.length);

        return  consulta;

    }
}
