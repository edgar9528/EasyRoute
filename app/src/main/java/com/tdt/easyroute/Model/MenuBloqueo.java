package com.tdt.easyroute.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuBloqueo {

    public boolean grupos[];
    public ArrayList<boolean[]> hijos;

    public MenuBloqueo() {

        grupos = new boolean[8];
        hijos = new ArrayList<>();

        for(int i=0; i< grupos.length;i++)
            grupos[i]=true;

        grupos[3]=false;

        boolean[] hijos0 = {true,true};
        boolean[] hijos1 = {true,true,true,true,true};
        boolean[] hijos2 = {true};
        boolean[] hijos3 = {true,true,true};
        boolean[] hijos4 = {true,true};
        boolean[] hijos5 = {true,true,true};
        boolean[] hijos6 = {true,true,true,true};
        boolean[] hijos7 = {true,true};

        hijos.add(hijos0);
        hijos.add(hijos1);
        hijos.add(hijos2);
        hijos.add(hijos3);
        hijos.add(hijos4);
        hijos.add(hijos5);
        hijos.add(hijos6);
        hijos.add(hijos7);

    }
}

