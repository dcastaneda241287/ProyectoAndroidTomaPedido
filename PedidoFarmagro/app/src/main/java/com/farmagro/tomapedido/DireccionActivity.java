package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Zona;

public class DireccionActivity extends Fragment {
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    private TextView txtDescripcion;
    private TextView txtDirCobro;
    private TextView txtDireccion;
    private TextView txtEmbarque;
    protected SQLibraryApp sqLibraryApp;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.direccion, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_direccion));
        setHasOptionsMenu(true);
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.txtEmbarque = view.findViewById(R.id.txtEmbarque);
        this.txtDireccion = view.findViewById(R.id.txtDireccion);
        this.txtDescripcion = view.findViewById(R.id.txtDescripcion);
        this.txtDirCobro = view.findViewById(R.id.txtDirCobro);
        this.txtEmbarque.setText(this.currentPedido.getNombcliente());
        this.txtDireccion.setText(this.currentPedido.getCoddefault());
        this.txtDescripcion.setText(this.currentPedido.getDireccion());
        this.txtDirCobro.setText(this.currentPedido.getDirecccobro());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_direccion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackDirec) {
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcNext) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("currentPedido", this.currentPedido);
            bundle.putSerializable("currentZona", currentZona);
            bundle.putSerializable("currentCliente", currentCliente);
            //Intent intent = new Intent(this, BusqTransportistaActivity.class);
            bundle.putInt("page", 1);


            BusqTransportistaActivity fragmentBusqTransportistaActivity = new BusqTransportistaActivity();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentBusqTransportistaActivity.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqTransportistaActivity).commit();
        }
        return true;
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);

        DetalleActivity fragmentdetalle = new DetalleActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentdetalle.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentdetalle).commit();

        return;
        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }


}
