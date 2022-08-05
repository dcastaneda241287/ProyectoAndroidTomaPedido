package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.farmagro.tomapedido.adapters.DetallePedidoAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.DescuentoCliente;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.util.Util;

import java.util.List;

public class DetalleActivity extends Fragment {
    private static final int SEARCH_PRODUCT_CODE = 9;
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    private ListView listProductos;
    private List<TmpDetalle> productos;
    private DescuentoCliente beanDesc;
    /* access modifiers changed from: private */
    public int selected;
    private TextView txtTotal;
    private TextView txtDesc;
    protected SQLibraryApp sqLibraryApp;

    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detalle, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_detalle));
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.listProductos = view.findViewById(R.id.listProductos);
        this.txtTotal = view.findViewById(R.id.txtTotal);
        this.txtDesc = view.findViewById(R.id.txtdescuento);

        setHasOptionsMenu(true);
        registerForContextMenu(this.listProductos);
        this.listProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                DetalleActivity.this.selected = position;
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("OPCIONES");
        menu.add(0, v.getId(), 0, "EDITAR");
        menu.add(1, v.getId(), 0, "ELIMINAR");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "EDITAR") {
            Bundle bundle = new Bundle();
            bundle.putSerializable("currentTmp", this.productos.get(this.selected));
            bundle.putString("codmoneda", this.currentPedido.getMoneda());
            //Intent intent = new Intent(getActivity(), ProductoActivity.class);
            bundle.putString("nivelPrecio", this.currentPedido.getNivelprecio());
            bundle.putSerializable("currentPedido", currentPedido);
            bundle.putSerializable("currentZona", currentZona);
            bundle.putSerializable("currentCliente", currentCliente);

            ProductoActivity fragmentProductoActivity = new ProductoActivity();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentProductoActivity.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentProductoActivity).commit();


            return true;
        } else if (item.getTitle() != "ELIMINAR") {
            return false;
        } else {
            this.sqLibraryApp.getDataManager().deleteTmpDetalleById(this.productos.get(this.selected).getCodproducto());
            this.productos.remove(this.selected);
            this.listProductos.setAdapter(new DetallePedidoAdapter(getActivity(), R.layout.pedido_item, this.productos));
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 9) {
            this.productos = this.sqLibraryApp.getDataManager().getTmpDetalleList();
            this.listProductos.setAdapter(new DetallePedidoAdapter(getActivity(), R.layout.pedido_item, this.productos));
            calcularTotales();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.productos = this.sqLibraryApp.getDataManager().getTmpDetalleList();
        this.listProductos.setAdapter(new DetallePedidoAdapter(getActivity(), R.layout.pedido_item, this.productos));
        calcularTotales();
    }

    private void calcularTotales() {
        float total = 0.0f;
        float descuento = 0.0f;
        float totaldesc = 0.0f;
        for (TmpDetalle bean : this.productos) {
            total += Util.formatearDecimales(bean.getTotal().floatValue(),2);
        }
        /*DescuentoCliente bean = DetalleActivity.this.sqLibraryApp.getDataManager().DevolverDescuentoPorCLiente(currentCliente.getCodigo());
        if(bean != null) {
            descuento = Float.parseFloat(bean.getDeDescuento());
            totaldesc = (total * descuento) / 100;
        }else{
            totaldesc = 0;
        }
        total = total - totaldesc;*/
        this.txtDesc.setText(Util.getTwoDecimals(totaldesc));
        this.txtTotal.setText(Util.getTwoDecimals(total));
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detalle, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackPedido) {
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcBuscar) {
            Bundle bundle = new Bundle();
            bundle.putString("codmoneda", this.currentPedido.getMoneda());
            bundle.putInt("page", 1);
            bundle.putString("nivelPrecio", this.currentPedido.getNivelprecio());
            bundle.putSerializable("currentPedido", this.currentPedido);
            bundle.putSerializable("currentZona", currentZona);
            bundle.putSerializable("currentCliente", currentCliente);

            BusqProductoActivity fragmentBusqProductoActivity = new BusqProductoActivity();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentBusqProductoActivity.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqProductoActivity).commit();

        }
        if (item.getItemId() == R.id.opcSavePedido) {
            if (this.sqLibraryApp.getDataManager().getTmpDetalleList().size() > 0) {
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("currentPedido", this.currentPedido);
                bundle2.putSerializable("currentZona", currentZona);
                bundle2.putSerializable("currentCliente", currentCliente);
                //Intent intent2 = new Intent(getActivity(), DireccionActivity.class);


                DireccionActivity fragmentDireccionActivity = new DireccionActivity();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentDireccionActivity.setArguments(bundle2);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDireccionActivity).commit();


            } else {
                Toast.makeText(getActivity(), "Ingrese productos al pedido", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void onBackPressed() {
        salirDetalle();
    }

    private void salirDetalle() {
        dialogPregunta("Desea salir del detalle?\nSe perderan los datos ingresados!");
    }

    public void dialogPregunta(String message) {
        TextView guardar, informacion;
        TextView cancel;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_pregunta_detalle, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);

        guardar = (TextView) alertCustomdialog.findViewById(R.id.tv_guardar_button);
        cancel  = (TextView) alertCustomdialog.findViewById(R.id.tv_cancel_button);
        informacion = (TextView) alertCustomdialog.findViewById(R.id.tv_information);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        informacion.setText(message);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetalleActivity.this.sqLibraryApp.getDataManager().deleteTmpDetalle();

                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("currentZona", currentZona);
                bundle2.putSerializable("currentCliente", currentCliente);

                PedidoActivity fragmentPedido = new PedidoActivity();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentPedido.setArguments(bundle2);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentPedido).commit();
                dialog.dismiss();;
                /*Intent intent = new Intent(getActivity(), MenuAlterno.class);
                intent.putExtra("page", 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ResumenActivity.this.startActivity(intent);
                getActivity().finish();*/
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
