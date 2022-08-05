package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.Existencia;
import com.farmagro.tomapedido.modelo.Stock;

import java.util.List;

public class StockListAdapter extends ArrayAdapter<Existencia> {
    private Activity activity;
    private int idLayout;
    private List<Existencia> lstStock;

    public StockListAdapter(Activity activity2, int idLayout2, List<Existencia> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.lstStock = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        StockView stockView;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            stockView = new StockView();
            stockView.txtName = (TextView) rowView.findViewById(R.id.txtBodega);
            stockView.txtStock = (TextView) rowView.findViewById(R.id.txtStock);
            rowView.setTag(stockView);
        } else {
            stockView = (StockView) rowView.getTag();
        }
        Existencia stockBean = this.lstStock.get(position);
        stockView.txtName.setText(stockBean.getvCodBodega());
        stockView.txtStock.setText(stockBean.getdCanDisponible());
        return rowView;
    }

    protected static class StockView {
        protected TextView txtName;
        protected TextView txtStock;

        protected StockView() {
        }
    }
}
