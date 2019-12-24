package com.tdt.easyroute.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuBloqueo {

    boolean grupos[] = new boolean[8];
    ArrayList<boolean[]> hijos = new ArrayList<>();

    public MenuBloqueo() {
        boolean[] hijos0 = new boolean[2];
        boolean[] hijos1 = new boolean[5];
        boolean[] hijos2 = new boolean[1];
        boolean[] hijos3 = new boolean[3];
        boolean[] hijos4 = new boolean[2];
        boolean[] hijos5 = new boolean[3];
        boolean[] hijos6 = new boolean[3];
        boolean[] hijos7 = new boolean[4];


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

