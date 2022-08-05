package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.Producto;

import java.util.List;

public class ProductoListAdapter extends ArrayAdapter<Producto> {
    private Activity activity;
    private int idLayout;
    private List<Producto> lstProducto;

    public ProductoListAdapter(Activity activity2, int idLayout2, List<Producto> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.lstProducto = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ProductoView productoView;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            productoView = new ProductoView();
            productoView.txtCodAlmacen = (TextView) rowView.findViewById(R.id.txtcodalmacen);
            productoView.txtName = (TextView) rowView.findViewById(R.id.txtProductoName);
            rowView.setTag(productoView);
        } else {
            productoView = (ProductoView) rowView.getTag();
        }
        productoView.txtName.setText(this.lstProducto.get(position).getDescripcion());
        productoView.txtCodAlmacen.setText(this.lstProducto.get(position).getvCodBodega() + " - ");
        return rowView;
    }

    protected static class ProductoView {
        protected TextView txtName;
        protected TextView txtCodAlmacen;

        protected ProductoView() {
        }
    }
}
