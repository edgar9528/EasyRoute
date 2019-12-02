package com.tdt.easyroute.Fragments.Clientes;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BuscarclientesFragment extends Fragment implements AsyncResponseJSON {

    private static boolean buscar;
    public static BuscarclientesFragment newInstance(boolean Buscar) {
        BuscarclientesFragment fragment = new BuscarclientesFragment();
        Bundle args = new Bundle();
        buscar=Buscar;
        fragment.setArguments(args);
        return fragment;
    }

    Button button_buscar, button_borrar, button_reimp, button_act, button_salir,button_seleccionar;
    EditText et_filtro;

    Configuracion conf;

    String cli_cve_nSelec="";

    private String Day="";

    ArrayList<DataTableWS.Clientes> bsClientes = null;
    ArrayList<DataTableWS.Clientes> dgClientes = null;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buscarclientes, container, false);
        layoutInflater=inflater;
        vista=view;


        tableLayout = view.findViewById(R.id.tableLayout);
        button_buscar = view.findViewById(R.id.button_buscar);
        button_borrar = view.findViewById(R.id.button_borrar);
        button_reimp = view.findViewById(R.id.button_reimp);
        button_act = view.findViewById(R.id.button_actualizar);
        button_seleccionar = view.findViewById(R.id.button_seleccionar);
        button_salir = view.findViewById(R.id.button_salir);
        et_filtro = view.findViewById(R.id.et_filtro);


        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro= et_filtro.getText().toString();
                mostrarClientes(filtro.toUpperCase());
            }
        });

        button_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_filtro.setText("");
                mostrarClientes("");
            }
        });

        button_reimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimir();
            }
        });

        button_seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionar(cli_cve_nSelec);
            }
        });

        inicializar();


        return  view;
    }

    private void inicializar()
    {
        conf = Utils.ObtenerConf2(getActivity().getApplication());
        Day = diaActual();
        mostrarTitulo();
        cargarClientes();
        if (buscar) {
            button_seleccionar.setEnabled(false);
        }

    }

    private String diaActual()
    {
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};

        Calendar cal= Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK)-1;

        String dia = dias[numeroDia];
        String FieldDay="";

        if(conf.getPreventa()==1)
        {
            if(dias[numeroDia].equals("Sábado"))
            {
                dia = "Lunes";
            }
            else
                dia= dias[numeroDia+1];
        }


        switch (dia) {
            case "Lunes":
                FieldDay = "cli_lun_n";
                break;
            case "Martes":
                FieldDay = "cli_mar_n";
                break;
            case "Miércoles":
                FieldDay = "cli_mie_n";
                break;
            case "Jueves":
                FieldDay = "cli_jue_n";
                break;
            case "Viernes":
                FieldDay = "cli_vie_n";
                break;
            case "Sábado":
                FieldDay = "cli_sab_n";
                break;
            case "Domingo":
                FieldDay = "cli_dom_n";
                break;
        }

        return FieldDay;

    }

    private void cargarClientes()
    {

        String consulta;
        try
        {
            if(conf.getPreventa()==1)
            {
                consulta = string.formatSql("Select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str,cli_especial_n " +
                        "from clientes where rut_cve_n in (select rut_cve_n from rutas where rut_prev_n={0}) and est_cve_str='A'", String.valueOf(conf.getRuta()));
            }
            else
            {
                consulta = string.formatSql("Select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str,cli_especial_n " +
                        "from clientes where rut_cve_n={0} and est_cve_str='A'", String.valueOf(conf.getRuta()));

            }

            String json = BaseLocal.Select( consulta ,getContext());

            bsClientes = ConvertirRespuesta.getClientesJson(json);

            mostrarClientes("");

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_buscarclientes, null);

        ((TextView) tr.findViewById(R.id.t_idExt)).setTypeface(((TextView) tr.findViewById(R.id.t_idExt)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_Negocio)).setTypeface(((TextView) tr.findViewById(R.id.t_Negocio)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_Razon)).setTypeface(((TextView) tr.findViewById(R.id.t_Razon)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void mostrarClientes(String filtro)
    {
        mostrarTitulo();
        dgClientes=null;
        cli_cve_nSelec="";
        if(bsClientes!=null && bsClientes.size()>0)
        {
            TableRow tr;
            DataTableWS.Clientes c;

            dgClientes = new ArrayList<>();

            for(int i=0; i<bsClientes.size();i++)
            {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_buscarclientes, null);
                c= bsClientes.get(i);

                if(!filtro.equals(""))
                {

                    if(c.getCli_cveext_str().toUpperCase().contains(filtro) ||
                       c.getCli_nombrenegocio_str().toUpperCase().contains(filtro) ||
                       c.getCli_razonsocial_str().toUpperCase().contains(filtro)   )
                    {

                        ((TextView) tr.findViewById(R.id.t_idExt)).setText(c.getCli_cveext_str());
                        ((TextView) tr.findViewById(R.id.t_Negocio)).setText(c.getCli_nombrenegocio_str());
                        ((TextView) tr.findViewById(R.id.t_Razon)).setText(c.getCli_razonsocial_str());
                        tr.setTag( c.getCli_cve_n());
                        tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                        tableLayout.addView(tr);
                        dgClientes.add(c);
                    }
                }
                else
                {
                    ((TextView) tr.findViewById(R.id.t_idExt)).setText(c.getCli_cveext_str());
                    ((TextView) tr.findViewById(R.id.t_Negocio)).setText(c.getCli_nombrenegocio_str());
                    ((TextView) tr.findViewById(R.id.t_Razon)).setText(c.getCli_razonsocial_str());
                    tr.setTag( c.getCli_cve_n());
                    tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                    tableLayout.addView(tr);
                    dgClientes.add(c);
                }
            }
        }

    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(cli_cve_nSelec.equals(tag)) //si coincide con el anterior, quiere decir que dio doble clic
            {
                seleccionar(cli_cve_nSelec);
            }
            else
            {
                //si no coincide, pinta todas de blanca
                for(int i=0; i<dgClientes.size();i++)
                {
                    TableRow row = (TableRow)vista.findViewWithTag(dgClientes.get(i).getCli_cve_n());
                    row.setBackgroundColor(Color.WHITE);
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                cli_cve_nSelec=tag;
                Log.d("salida","Un clic: "+cli_cve_nSelec);
            }

        }
    };

    private void imprimir()
    {
        try
        {

            if(cli_cve_nSelec!="")
            {
                DataTableWS.Clientes rc=null;

                for(int i=0; i<bsClientes.size(); i++)
                    if(bsClientes.get(i).getCli_cve_n().equals(cli_cve_nSelec))
                        rc= bsClientes.get(i);


                String cliente = rc.getCli_cve_n();
                String IdExt = rc.getCli_cveext_str();

                if (conf.getPreventa()==1);
                    //Utils.ImprimirPreVentaBebidas(Utils.ObtenerVisitaPrevBebidas(cliente), true, "a s e s o r");
                //else
                    //Utils.ImprimirVentaBebidas(Utils.ObtenerVisitaBebidas(cliente), true);


            }
            else
                Toast.makeText(getContext(), "No ha seleccionado ningún cliente", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void seleccionar(String cle_cve_n)
    {
        if(!cle_cve_n.isEmpty())
        {
            Toast.makeText(getContext(), "Cliente: "+cle_cve_n, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private void peticionClientes()
    {

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

    }
}
