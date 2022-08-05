package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.farmagro.tomapedido.adapters.ProductoListAdapter;
import com.farmagro.tomapedido.adapters.StockListAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Bodega;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Existencia;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.Stock;

import java.util.ArrayList;
import java.util.List;

public class ProdStockActivity extends Fragment {
    private Button btnAceptar;
    private Producto currentProducto;
    private ListView listStock;
    private TextView txtCodigo;
    private TextView txtDescripcion;
    private TextView txtUnidVta;
    protected SQLibraryApp sqLibraryApp;
    private CabPedido currentPedido;
    private StockListAdapter lstadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.producto_stock, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_stock_detalle));

        this.txtCodigo = view.findViewById(R.id.txtCodigo);
        this.txtDescripcion = view.findViewById(R.id.txtDescripcion);
        this.txtUnidVta = view.findViewById(R.id.txtUnidVta);
        this.listStock = view.findViewById(R.id.listStock);
        this.btnAceptar = view.findViewById(R.id.btnAceptar);
        this.currentProducto = (Producto) getArguments().get("currentProducto");
        //this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.txtCodigo.setText(this.currentProducto.getCodproducto());
        this.txtDescripcion.setText(this.currentProducto.getDescripcion());
        this.txtUnidVta.setText(this.currentProducto.getUnidadventa());
        List<Stock> lista = new ArrayList<>();
        int i = 0;
        //for (Bodega beanBodega : this.sqLibraryApp.getDataManager().getBodegaList()) {
        List<Existencia> stock = this.sqLibraryApp.getDataManager().getStockById(this.currentProducto.getCodproducto(), "cantB" + i);

        /*if (stock. != null && !stock.equals("0.00") && !stock.equals("")) {
            Stock beanStock = new Stock();
            beanStock.setBodega();
            beanStock.setCantidad(stock);
            lista.add(beanStock);
        }*/

        //}
        ProdStockActivity.this.lstadapter = new StockListAdapter(getActivity(),R.layout.stock_item,ProdStockActivity.this.sqLibraryApp.getDataManager().getStockById(this.currentProducto.getCodproducto(), "cantB" + i));
        ProdStockActivity.this.listStock.setAdapter(ProdStockActivity.this.lstadapter);

        //ProdStockActivity.this.listStock.setAdapter(new StockListAdapter(getActivity(), R.layout.stock_item, lista));
        //BusqProductoActivity.this.lstAdapter = new ProductoListAdapter(getActivity(), R.layout.producto_item, BusqProductoActivity.this.sqLibraryApp.getDataManager().getProductoList(BusqProductoActivity.this.editFilter.getText().toString(), BusqProductoActivity.this.spnSearch.getSelectedItemPosition(), BusqProductoActivity.this.nprecio));
        //BusqProductoActivity.this.listProducto.setAdapter(BusqProductoActivity.this.lstAdapter);



        /*this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //ProdStockActivity.this.finish();
                //getActivity().getFragmentManager().popBackStack();
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentPedido", (CabPedido) getArguments().get("currentPedido"));

                DetalleActivity fragmentDetalleActivity = new DetalleActivity();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentDetalleActivity.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDetalleActivity).commit();
                getActivity().finish();
            }
        });*/
    }
}
