package com.farmagro.tomapedido;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.adapters.DetallePedidoAdapter;
import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.util.Util;

import java.util.List;

public class FacturaVendedorAdapter extends ArrayAdapter<FacturaVendedor> {
    private final Activity activity;
    private final int idLayout;
    private final List<FacturaVendedor> facturaVendedors;

    public FacturaVendedorAdapter(Activity activity2, int idLayout2, List<FacturaVendedor> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.facturaVendedors = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ResumenView resumenView;
        View rowView = convertView;

        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            resumenView = new ResumenView();
            resumenView.txtPedido = (TextView) rowView.findViewById(R.id.txtPedido);
            resumenView.txtEstado = (TextView) rowView.findViewById(R.id.txtEstado);
            resumenView.txtNroFac = (TextView) rowView.findViewById(R.id.txtNroFac);
            resumenView.txtNomCli = (TextView) rowView.findViewById(R.id.txtNomCli);
            resumenView.txtMonto = (TextView) rowView.findViewById(R.id.txtMonto);
            resumenView.txtFecha = (TextView) rowView.findViewById(R.id.txtFecha);

            rowView.setTag(resumenView);
        } else {
            resumenView = (ResumenView) rowView.getTag();
        }
        FacturaVendedor fact = this.facturaVendedors.get(position);
        resumenView.txtPedido.setText(fact.getvPedido());
        resumenView.txtEstado.setText(fact.getvEstado());
        resumenView.txtNroFac.setText(fact.getvFactura());
        resumenView.txtNomCli.setText(fact.getvNomcli());
        resumenView.txtMonto.setText(Util.getTwoDecimals(Float.parseFloat(fact.getdTotVenta())));
        resumenView.txtFecha.setText(fact.getdFecha());

        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.rgb(250,250,250));
        } else{
            rowView.setBackgroundColor(Color.WHITE);
        }
        return rowView;
    }

    protected static class ResumenView {
        protected TextView txtFecha;
        protected TextView txtPedido;
        protected TextView txtEstado;
        protected TextView txtNroFac;
        protected TextView txtMonto;
        protected TextView txtNomCli;
        protected ResumenView() {
        }
    }
}

