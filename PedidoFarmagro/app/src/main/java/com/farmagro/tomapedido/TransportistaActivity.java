package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Proveedor;
import com.farmagro.tomapedido.modelo.Zona;

public class TransportistaActivity extends Fragment {
    private CabPedido currentPedido;
    private Proveedor currentTransportista;
    private Zona currentZona;
    private Cliente currentCliente;

    private TextView txtCodigo;
    private TextView txtRazon;
    protected SQLibraryApp sqLibraryApp;
    private Button btnSiguiente;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transportista, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentTransportista = (Proveedor) getArguments().get("currentTransportista");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_trans));
        this.txtCodigo = view.findViewById(R.id.txtCodigo);
        this.txtRazon = view.findViewById(R.id.txtRazon);

        this.txtCodigo.setText(this.currentTransportista.getCodigo());
        this.txtRazon.setText(this.currentTransportista.getDescripcion());

    }
    public void onViewResult(){

    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transp, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackTransp) {
            salirDetalle();
        }

        if (item.getItemId() == R.id.opcNextTransp) {
            Bundle bundle = new Bundle();
            this.currentPedido.setCodigotransp(this.currentTransportista.getCodigo());
            this.currentPedido.setNombretransp(this.currentTransportista.getDescripcion());
            bundle.putSerializable("currentPedido", this.currentPedido);
            bundle.putSerializable("currentZona", currentZona);
            bundle.putSerializable("currentCliente", currentCliente);
            //Intent intent = new Intent(this, AdicionalActivity.class);

            AdicionalActivity fragmentAdicionalActivity = new AdicionalActivity();
            FragmentManager fragmentManager = getParentFragmentManager();

            fragmentAdicionalActivity.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentAdicionalActivity).commit();
        }
        return true;
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);
        bundle2.putInt("page", 1);

        BusqTransportistaActivity fragmentBusqTrans = new BusqTransportistaActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentBusqTrans.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqTrans).commit();
        return;
        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();
    }
}
