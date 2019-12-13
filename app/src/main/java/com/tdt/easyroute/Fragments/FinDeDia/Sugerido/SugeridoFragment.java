package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import org.slf4j.Logger;

import java.util.ArrayList;

public class SugeridoFragment extends Fragment {

    ArrayList<DataTableLC.Productos_Sug> dtProd=null;
    ArrayList<DataTableLC.Sugerido> dgSugerido=null;

    Button b_buscar,b_borrar;
    EditText et_sku,et_cant;
    int sugeridoIndice=-1;
    String sugeridoSelec="",nombreBase;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    MainsugeridoFragment fragmentMain;

    SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_familia, container, false);

        nombreBase = getContext().getString( R.string.nombreBD );
        dgSugerido = new ArrayList<>();

        layoutInflater = inflater;
        vista=view;
        fragmentMain = (MainsugeridoFragment) getParentFragment();
        tableLayout = view.findViewById(R.id.tableLayout);

        b_buscar = view.findViewById(R.id.button_buscar);
        b_borrar = view.findViewById(R.id.button_borrar);

        et_sku = view.findViewById(R.id.et_sku);
        et_cant = view.findViewById(R.id.et_cant);

        mostrarTitulo();

        b_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar();
            }
        });

        b_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sku = et_sku.getText().toString();
                String can= et_cant.getText().toString();
                
                if(!sku.isEmpty())
                {
                    buscarSKU(sku,can);
                }
                else
                    Toast.makeText(getContext(), "Ingresa un sku a buscar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sugeridoVM.getDtProd().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Productos_Sug>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Productos_Sug> dtprod) {
                dtProd = dtprod;
            }
        });

        sugeridoVM.getDgSugerido().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Sugerido>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Sugerido> dgSug) {
                dgSugerido = dgSug;
                mostrarSugerido(-1);
            }
        });

        sugeridoVM.getProductoSKU().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String producSku) {
                buscarSKU(producSku,"0");
            }
        });

        sugeridoVM.getStrBuscar().observe(getParentFragment(), new Observer< String[] >() {
            @Override
            public void onChanged(String strbuscar[]) {
                buscarSKU(strbuscar[0],strbuscar[1]);
            }
        });

        sugeridoVM.getBorrar().observe(getParentFragment(), new Observer< Boolean >() {
            @Override
            public void onChanged(Boolean busca) {
                if(busca)
                {
                    borrar();
                }
            }
        });


        sugeridoVM.getOpcion().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String opcion) {
                switch (opcion)
                {
                    case "guardar":
                        guardar();
                        break;
                    case "enviar":
                        Log.d("salida","sugerido enviar");
                        break;
                    case "imprimir":
                        Log.d("salida","sugerido imprimir");
                        break;
                    case "salir":
                        Utils.RegresarInicio(getActivity());
                        break;
                }
            }
        });

    }

    private void guardar()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            db.beginTransaction();

            db.execSQL("delete from sugerido");

            String con= "insert into sugerido(prod_cve_n,prod_sku_str,prod_sug_n) values({0},'{1}',{2})";
            DataTableLC.Sugerido r;
            for(int i=0; i< dgSugerido.size();i++)
            {
                r= dgSugerido.get(i);
                db.execSQL(string.formatSql( con, r.getProd_cve_n(), r.getProd_sku_str(), r.getProd_sug_n() ) );
            }
            db.setTransactionSuccessful();

            Toast.makeText(getContext(), "Sugerido guardado con exito", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al guardar sugerido", Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }

    }

    private void borrar()
    {
        try
        {
            if (sugeridoIndice >= 0)
            {
                dgSugerido.remove(sugeridoIndice);


                if(sugeridoIndice<dgSugerido.size())
                {
                    sugeridoIndice=sugeridoIndice;
                    sugeridoSelec = dgSugerido.get(sugeridoIndice).getProd_sku_str();
                }
                else
                if(sugeridoIndice==dgSugerido.size())
                {
                    sugeridoIndice=sugeridoIndice-1;
                    sugeridoSelec = dgSugerido.get(sugeridoIndice-1).getProd_sku_str();
                }
                else
                {
                    sugeridoIndice=-1;
                    sugeridoSelec="";
                }

                calcularEnvase();
                mostrarSugerido(sugeridoIndice);

            }
            else
            {
                Toast.makeText(getContext(), "Selecciona un producto", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex)
        {
        }
    }

    private void buscarSKU(String sku,String cant)
    {

        Log.d("salida","SKU BUSCAR: "+sku + "   can:"+cant);
        int seleccion = -1;
        sugeridoSelec="";
        int c = -1;
        try
        {
            if( cant==null || cant.isEmpty() )
                c=0;
            else
                c = Integer.parseInt(cant);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Capture una cantidad correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        int k = -1;

        DataTableLC.Sugerido r=null;

        for(int i=0; i < dgSugerido.size();i++)
        {
            if( sku.equals( dgSugerido.get(i).getProd_sku_str() ) )
            {
                r= dgSugerido.get(i);
                k=i;
                i=dgSugerido.size();
            }
        }

        if(r!=null)
        {
            et_sku.setText("");
            et_cant.setText("");

            seleccion = k;
            dgSugerido.get(k).setProd_sug_n( String.valueOf( c )  );
        }
        else
        {
            DataTableLC.Productos_Sug ri=null;

            for(int i=0; i < dtProd.size();i++)
            {
                if( sku.equals( dtProd.get(i).getProd_sku_str() ) )
                {
                    ri= dtProd.get(i);
                    i=dtProd.size();
                }
            }

            if(ri!=null)
            {
                et_sku.setText("");
                et_cant.setText("");

                DataTableLC.Sugerido sug = new DataTableLC.Sugerido();

                sug.setProd_cve_n( ri.getProd_cve_n() );
                sug.setProd_sku_str( ri.getProd_sku_str() );
                sug.setProd_desc_str( ri.getProd_desc_str() );
                sug.setId_envase_n( ri.getId_envase_n() );
                sug.setProd_sug_n( String.valueOf( c ) );
                sug.setCat_desc_str( ri.getCat_desc_str() );


                dgSugerido.add( sug );

                seleccion = dgSugerido.size()-1;

                //int ra = dgSugerido.size()-1;

                /* Fue sustituido por la linea:  sug.setProd_sug_n( String.valueOf( c ) );
                if (cant.equals(""))
                {
                    dgSugerido.get(ra).setProd_sug_n( String.valueOf( c )  );
                }*/

                tableLayout.requestFocus();
                fragmentMain.goSugerido();

            }
            else
            {
                Log.d("salida","Entro aqui, ri nulo");

                seleccion=-1;
                sugeridoSelec = "";
                sugeridoIndice = -1;

                Toast.makeText(getContext(), "El sku no existe", Toast.LENGTH_SHORT).show();
            }
        }

        calcularEnvase();
        mostrarSugerido(seleccion);
    }

    private void calcularEnvase()
    {
        for(int i=0; i< dtProd.size(); i++)
        {
            if(dtProd.get(i).getCat_desc_str().equals("ENVASE"))
            {

                int k=-1;
                int o=0;
                DataTableLC.Sugerido e=null;
                for(int j=0; j<dgSugerido.size();j++)
                {
                    if(dgSugerido.get(j).getProd_cve_n().equals(dtProd.get(i).getProd_cve_n()))
                    {
                        e = dgSugerido.get(j);
                        k=j;
                    }

                    if(dgSugerido.get(j).getId_envase_n().equals(  dtProd.get(i).getProd_cve_n()  ))
                    {
                        o = o + Integer.parseInt(  dgSugerido.get(j).getProd_sug_n()  );
                    }

                }

                if(e!=null)
                {

                }
                else
                {
                    DataTableLC.Sugerido ri = new DataTableLC.Sugerido();
                    ri.setProd_cve_n( dtProd.get(i).getProd_cve_n() );
                    ri.setProd_sku_str( dtProd.get(i).getProd_sku_str() );
                    ri.setProd_desc_str( dtProd.get(i).getProd_desc_str()  );
                    ri.setId_envase_n( dtProd.get(i).getId_envase_n() );
                    ri.setProd_sug_n("0");
                    ri.setCat_desc_str( dtProd.get(i).getCat_desc_str() );
                    dgSugerido.add(ri);

                    k = dgSugerido.size()-1;

                }
                dgSugerido.get(k).setProd_sug_n( String.valueOf(o) );
            }
        }
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);

        ((TextView) tr.findViewById(R.id.t_sku)).setTypeface( ((TextView) tr.findViewById(R.id.t_sku)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_producto)).setTypeface( ((TextView) tr.findViewById(R.id.t_producto)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_sugerido)).setTypeface( ((TextView) tr.findViewById(R.id.t_sugerido)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);

    }

    private void mostrarSugerido(int seleccion)
    {
        mostrarTitulo();

        for(int i=0; i<dgSugerido.size();i++)
        {
            TableRow tr;

            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);
            ((TextView) tr.findViewById(R.id.t_sku)).setText( dgSugerido.get(i).getProd_sku_str() );
            ((TextView) tr.findViewById(R.id.t_producto)).setText( dgSugerido.get(i).getProd_desc_str() );
            ((TextView) tr.findViewById(R.id.t_sugerido)).setText( dgSugerido.get(i).getProd_sug_n() );

            tr.setTag(dgSugerido.get(i).getProd_sku_str());
            tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

            if(i==seleccion)
            {
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                sugeridoSelec = dgSugerido.get(i).getProd_sku_str();
                sugeridoIndice = i;
            }

            tableLayout.addView(tr);
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            sugeridoSelec = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            for(int i=0; i<dgSugerido.size();i++)
            {
                TableRow row = (TableRow)vista.findViewWithTag(dgSugerido.get(i).getProd_sku_str());
                row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );

                if(sugeridoSelec.equals( dgSugerido.get(i).getProd_sku_str() ))
                    sugeridoIndice = i;
            }

            //pinta de azul la fila y actualiza la cve de la fila seccionada
            tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
            tr.requestFocus();

        }
    };

}
