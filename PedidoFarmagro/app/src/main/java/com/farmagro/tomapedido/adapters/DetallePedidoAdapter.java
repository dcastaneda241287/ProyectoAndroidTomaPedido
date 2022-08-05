package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.util.Util;

import java.util.List;

public class DetallePedidoAdapter extends ArrayAdapter<TmpDetalle> {
    private final Activity activity;
    private final int idLayout;
    private final List<TmpDetalle> productos;

    public DetallePedidoAdapter(Activity activity2, int idLayout2, List<TmpDetalle> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.productos = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ResumenView resumenView;
        View rowView = convertView;

        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            resumenView = new ResumenView();
            resumenView.txtCodigo = (TextView) rowView.findViewById(R.id.txtCodigo);
            resumenView.txtDescripcion = (TextView) rowView.findViewById(R.id.txtDescripcion);
            resumenView.txtPrecio = (TextView) rowView.findViewById(R.id.txtPrecio);
            resumenView.txtCantPed = (TextView) rowView.findViewById(R.id.txtCantPed);
            resumenView.txtCantFac = (TextView) rowView.findViewById(R.id.txtCantFac);
            resumenView.txtUnidad = (TextView) rowView.findViewById(R.id.txtUnidad);
            resumenView.txtDscto = (TextView) rowView.findViewById(R.id.txtDscto);
            resumenView.txtTotal = (TextView) rowView.findViewById(R.id.txtTotal);
            resumenView.txtCodAlmacen = (TextView) rowView.findViewById(R.id.txtCodAlmacen);
            rowView.setTag(resumenView);
        } else {
            resumenView = (ResumenView) rowView.getTag();
        }
        TmpDetalle detalle = this.productos.get(position);
        resumenView.txtCodigo.setText(detalle.getCodproducto());
        resumenView.txtDescripcion.setText(detalle.getDesproducto());
        resumenView.txtPrecio.setText(Util.getTwoDecimals(detalle.getPrecio()));
        resumenView.txtCantPed.setText(Util.mostrarNumero(detalle.getCantidad()));
        resumenView.txtCantFac.setText(Util.mostrarNumero(detalle.getCantidad()));
        resumenView.txtDscto.setText(detalle.getDescuento().toString());
        resumenView.txtUnidad.setText(detalle.getUndventa());
        resumenView.txtTotal.setText(Util.getTwoDecimals(detalle.getTotal()));
        resumenView.txtCodAlmacen.setText(detalle.getVcodalmacen().toString());

        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.rgb(250,250,250));
        } else{
            rowView.setBackgroundColor(Color.WHITE);
        }
        return rowView;
    }

    protected static class ResumenView {
        protected TextView txtCantFac;
        protected TextView txtCantPed;
        protected TextView txtCodigo;
        protected TextView txtDescripcion;
        protected TextView txtDscto;
        protected TextView txtPrecio;
        protected TextView txtTotal;
        protected TextView txtUnidad;
        protected TextView txtCodAlmacen;

        protected ResumenView() {
        }
    }
}
