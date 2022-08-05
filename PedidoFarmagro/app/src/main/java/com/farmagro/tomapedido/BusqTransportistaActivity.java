package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmagro.tomapedido.adapters.TransportistaListAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Proveedor;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;

import java.util.ArrayList;

public class BusqTransportistaActivity extends Fragment {
    /* access modifiers changed from: private */
    public CabPedido currentPedido;
    /* access modifiers changed from: private */
    public EditText editFilter;
    private ImageButton imvSearch;
    /* access modifiers changed from: private */
    public ListView listTransportistas;
    /* access modifiers changed from: private */
    public int page;
    /* access modifiers changed from: private */
    public Spinner spnSearch;
    protected SQLibraryApp sqLibraryApp;

    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;
    private int x = 0;
    private Zona currentZona;
    private Cliente currentCliente;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.busq_transportista, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_busq_trans));
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.page = getArguments().getInt("page");
        this.spnSearch = view.findViewById(R.id.spnSearch);
        this.editFilter = view.findViewById(R.id.editFilter);
        this.imvSearch = view.findViewById(R.id.imvSearch);
        this.listTransportistas = view.findViewById(R.id.listTransportistas);

        //ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getActivity(), R.array.busqueda, android.R.layout.simple_spinner_item);
        //createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frameworks = new ArrayList<>();
        Resources res = getResources();
        String[] tipobusqueda = res.getStringArray(R.array.busqueda);
        //framework = new Framework(0 , "[Seleccione]",0);
        //frameworks.add(framework);
        for (x = 0; x < tipobusqueda.length; x++) {
            framework = new Framework(0 , tipobusqueda[x].toString(),0,"");
            frameworks.add(framework);
        }
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);

        this.spnSearch.setAdapter(adapter);
        this.spnSearch.setSelection(1);
        this.spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (BusqTransportistaActivity.this.spnSearch.getSelectedItemPosition() == 0) {
                    BusqTransportistaActivity.this.editFilter.setText("");
                    BusqTransportistaActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                    BusqTransportistaActivity.this.editFilter.setInputType(2);
                    return;
                }
                BusqTransportistaActivity.this.editFilter.setText("");
                BusqTransportistaActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                BusqTransportistaActivity.this.editFilter.setInputType(1);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.imvSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (BusqTransportistaActivity.this.editFilter.getText().length() < 2) {
                    Toast.makeText(getActivity(), "Ingrese minimo 2 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                BusqTransportistaActivity.this.listTransportistas.setAdapter(new TransportistaListAdapter(getActivity(), R.layout.transportista_item, BusqTransportistaActivity.this.sqLibraryApp.getDataManager().getTransportistaList(BusqTransportistaActivity.this.editFilter.getText().toString(), BusqTransportistaActivity.this.spnSearch.getSelectedItemPosition())));
            }
        });
        this.listTransportistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                Proveedor currentTransportista = (Proveedor) BusqTransportistaActivity.this.listTransportistas.getItemAtPosition(position);
                if (BusqTransportistaActivity.this.page == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("currentTransportista", currentTransportista);
                    bundle.putSerializable("currentPedido", BusqTransportistaActivity.this.currentPedido);
                    bundle.putSerializable("currentZona", currentZona);
                    bundle.putSerializable("currentCliente", currentCliente);
                    //Intent intent = new Intent(BusqTransportistaActivity.this, TransportistaActivity.class);


                    TransportistaActivity fragmentTransportistaActivity = new TransportistaActivity();
                    FragmentManager fragmentManager = getParentFragmentManager();

                    fragmentTransportistaActivity.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentTransportistaActivity).commit();


                    return;
                }
                BusqTransportistaActivity.this.currentPedido.setCodigotransp(currentTransportista.getCodigo());
                BusqTransportistaActivity.this.currentPedido.setNombretransp(currentTransportista.getDescripcion());
                BusqTransportistaActivity.this.sqLibraryApp.getDataManager().updateCabPedido(BusqTransportistaActivity.this.currentPedido);
                getActivity().finish();
            }
        });
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busq_trans, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackBTransp) {
            salirDetalle();
        }
        return true;
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);

        DireccionActivity fragmentdirecion = new DireccionActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentdirecion.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentdirecion).commit();
        return;

        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }
}
