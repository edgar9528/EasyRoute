package com.tdt.easyroute.Ventanas.Pedidos.DetallesCliente;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.DetallesCliVM;
import java.util.ArrayList;

public class DatosdetFragment extends Fragment {

    private DataTableLC.DtCliVenta rc;
    private ArrayList<DataTableLC.Estatus> dtEst=null;
    private Spinner sp_estado;

    private EditText et_codigo, et_razon, et_negocio,et_rfc, et_limCredito, et_plazo, et_envases, et_coord;
    private RadioButton rb_reqfact, rb_prospectp, rb_fba,rb_comodato;

    private DetallesCliVM detallesCliVM;

    public DatosdetFragment() {
        // Required empty public constructor
    }

    public static DatosdetFragment newInstance() {
        DatosdetFragment fragment = new DatosdetFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detallesCliVM = ViewModelProviders.of ( getActivity() ).get(DetallesCliVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_datosdet, container, false);

        try
        {
            MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
            rc = mainDetallesActivity.getCliente();
            Button button_salir, button_ruta;

            button_salir = view.findViewById(R.id.button_salir);
            button_ruta = view.findViewById(R.id.button_ruta);

            sp_estado = view.findViewById(R.id.spinnerEstadoCred);
            sp_estado.setEnabled(false);

            et_codigo = view.findViewById(R.id.et_codigo);
            et_razon = view.findViewById(R.id.et_razon);
            et_negocio = view.findViewById(R.id.et_negocio);
            et_rfc = view.findViewById(R.id.et_rfc);
            et_limCredito = view.findViewById(R.id.et_limite);
            et_plazo = view.findViewById(R.id.et_plazo);
            et_envases = view.findViewById(R.id.et_envases);
            et_coord = view.findViewById(R.id.et_coordenada);

            rb_reqfact = view.findViewById(R.id.rb_reqfact);
            rb_prospectp = view.findViewById(R.id.rb_prospectp);
            rb_fba = view.findViewById(R.id.rb_fba);
            rb_comodato = view.findViewById(R.id.rb_comodato);


            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            button_ruta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detallesCliVM.setBt_ruta(true);
                }
            });

            inicializar();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_det1), e.getMessage());
        }

        return view;
    }

    private void inicializar()
    {
        try
        {
            et_codigo.setText(rc.getCli_cveext_str().equals("null")?"":rc.getCli_cveext_str());
            et_razon.setText(rc.getCli_razonsocial_str().equals("null")?"":rc.getCli_razonsocial_str());
            et_negocio.setText(rc.getCli_nombrenegocio_str().equals("null")?"":rc.getCli_nombrenegocio_str() );
            et_rfc.setText(rc.getCli_rfc_str().equals("null")?"":rc.getCli_rfc_str() );
            et_limCredito.setText( rc.getCli_montocredito_n().equals("null")?"":rc.getCli_montocredito_n() );
            et_plazo.setText( rc.getCli_plazocredito_n().equals("null")?"":rc.getCli_plazocredito_n() );
            et_envases.setText( rc.getCli_credenvases_n().equals("null")?"":rc.getCli_credenvases_n() );
            et_coord.setText( rc.getCli_coordenadaini_str().equals("null")?"":rc.getCli_coordenadaini_str() );

            detallesCliVM.setUbiCliente( rc.getCli_coordenadaini_str() );

            if(rc.getCli_prospecto_n().equals("0")) rb_prospectp.setChecked(true);
            if(rc.getCli_fba_n().equals("0")) rb_fba.setChecked(true);
            if(rc.getCli_reqfact_n().equals("0")) rb_reqfact.setChecked(true);
            if(rc.getCli_comodato_n().equals("0")) rb_comodato.setChecked(true);

            Log.d("salida","CLI_REQFAC: "+rc.getCli_reqfact_n());

            String json,con;
            con= "select est_cve_str,est_desc_str from estatus where est_cve_str in ('A','I','B')";
            json = BaseLocal.Select(con,getContext());
            dtEst = ConvertirRespuesta.getEstatusDetJson(json);
            if(dtEst!=null)
            {
                ArrayList<String> estatus = new ArrayList<>();
                int indice=-1;
                for(int i=0; i<dtEst.size();i++) {
                    estatus.add(dtEst.get(i).getEst_desc_str());
                    if(rc.getEst_cve_str().equals( dtEst.get(i).getEst_cve_str() ))
                        indice=i;
                }

                sp_estado.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_item, estatus));

                if(indice!=-1)
                    sp_estado.setSelection(indice);
            }
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Utils.msgError(getContext(),getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

}
