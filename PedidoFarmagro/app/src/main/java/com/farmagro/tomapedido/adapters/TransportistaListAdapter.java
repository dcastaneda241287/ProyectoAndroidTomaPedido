package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.Proveedor;

import java.util.List;

public class TransportistaListAdapter extends ArrayAdapter<Proveedor> {
    private Activity activity;
    private int idLayout;
    private List<Proveedor> lstProveedor;

    public TransportistaListAdapter(Activity activity2, int idLayout2, List<Proveedor> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.lstProveedor = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ProveedorView proveedorView;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            proveedorView = new ProveedorView();
            proveedorView.txtTransportistaName = (TextView) rowView.findViewById(R.id.txtTransportistaName);
            proveedorView.txtRucTransportista = (TextView) rowView.findViewById(R.id.txtructrans);
            rowView.setTag(proveedorView);
        } else {
            proveedorView = (ProveedorView) rowView.getTag();
        }
        proveedorView.txtTransportistaName.setText(this.lstProveedor.get(position).getDescripcion());
        proveedorView.txtRucTransportista.setText(this.lstProveedor.get(position).getvRuc());
        return rowView;
    }

    protected static class ProveedorView {
        protected TextView txtTransportistaName;
        protected TextView txtRucTransportista;

        protected ProveedorView() {
        }
    }
}

