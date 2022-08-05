package com.farmagro.tomapedido.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farmagro.tomapedido.R;
import com.farmagro.tomapedido.modelo.Cliente;
import java.util.List;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    private Activity activity;
    private int idLayout;
    private List<Cliente> lstCliente;

    public ClienteListAdapter(Activity activity2, int idLayout2, List<Cliente> objects) {
        super(activity2, idLayout2, objects);
        this.activity = activity2;
        this.lstCliente = objects;
        this.idLayout = idLayout2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ClienteView clienteView;
        View rowView = convertView;
        if (rowView == null) {
            rowView = this.activity.getLayoutInflater().inflate(this.idLayout, (ViewGroup) null);
            clienteView = new ClienteView();
            clienteView.textView = (TextView) rowView.findViewById(R.id.textView);
            rowView.setTag(clienteView);
        } else {
            clienteView = (ClienteView) rowView.getTag();
        }
        clienteView.textView.setText(this.lstCliente.get(position).getNombre());
        return rowView;
    }

    protected static class ClienteView {
        protected TextView textView;

        protected ClienteView() {
        }
    }
}
