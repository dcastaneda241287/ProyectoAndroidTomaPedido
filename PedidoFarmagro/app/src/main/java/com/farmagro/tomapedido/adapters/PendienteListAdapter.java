package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.CabPedido;

import java.util.List;

public class PendienteListAdapter extends ArrayAdapter<CabPedido> {
    private Activity activity;
    private int idLayout;
    private List<CabPedido> lstPendientes;

    public PendienteListAdapter(Activity activity2, int idLayout2, List<CabPedido> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.lstPendientes = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PendienteView pendienteView;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            pendienteView = new PendienteView();
            pendienteView.txtDescripcion = (TextView) rowView.findViewById(R.id.txtDescripcion);
            rowView.setTag(pendienteView);
        } else {
            pendienteView = (PendienteView) rowView.getTag();
        }
        pendienteView.txtDescripcion.setText(this.lstPendientes.get(position).getNombcliente());
        return rowView;
    }

    protected static class PendienteView {
        protected TextView txtDescripcion;

        protected PendienteView() {
        }
    }
}

