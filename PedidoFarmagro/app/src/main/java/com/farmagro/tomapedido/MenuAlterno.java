package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MenuAlterno extends AppCompatActivity {

    private BottomNavigationView bnvMenu;
    private Fragment fragment;
    private FragmentManager manager;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;
    protected static final int BACK_FROM_GPS_ACT = 1;
    protected static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0;
    protected static final long MINIMUM_TIME_BETWEEN_UPDATES = 0;
    protected ActionBar ab;
    protected SQLibraryApp sqLibraryApp;
    public int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_alterno);
        initView();
        initValues();
        loadFirstFragment();
        initListener();
        this.ab = getSupportActionBar();
        this.ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        this.sqLibraryApp = (SQLibraryApp) getApplication();
        this.page = getIntent().getExtras().getInt("page");
    }

    private void initView(){
        bnvMenu = findViewById(R.id.bnvMenu);
    }

    private void initValues(){
        manager = getSupportFragmentManager();
    }

    private void initListener(){
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idMenu = item.getItemId();
                Bundle args = new Bundle();
                SincroActivity fragmentSincro = new SincroActivity();
                BusqClienteActivity fragmentBusqCli = new BusqClienteActivity();
                FacturaVendedorRep facturaVendedorRep = new FacturaVendedorRep();
                FragmentManager fragmentManager = getSupportFragmentManager();
                BusqProductoActivity fragmentBusqProducto = new BusqProductoActivity();
                Usuario beanUsuario = sqLibraryApp.getDataManager().getUsuario();
                switch (idMenu) {
                    case R.id.menu_home:
                        //if (MenuAlterno.this.sqLibraryApp.getDataManager().getTotalClientes() > 0) {
                        args.putInt("page", 1);
                        facturaVendedorRep.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, facturaVendedorRep).commit();
                        //} else {
                        //Toast.makeText(MenuAlterno.this.getApplicationContext(), "No hay facturas, por favor sincronizar", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.menu_pedido:
                       if (MenuAlterno.this.sqLibraryApp.getDataManager().getTotalClientes() > 0 && MenuAlterno.this.sqLibraryApp.getDataManager().getTotalZonaVendedor(beanUsuario.getClave().toString()) > 0) {

                            args.putInt("page", 1);
                            fragmentBusqCli.setArguments(args);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqCli).commit();
                        } else {
                            Toast.makeText(MenuAlterno.this.getApplicationContext(), "No hay clientes, por favor sincronizar", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    /*case R.id.menu_stock:
                        args.putInt("page", 0);
                        fragmentBusqProducto.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqProducto).commit();
                        return true;*/
                    case R.id.menu_linea_credito:
                        if (MenuAlterno.this.sqLibraryApp.getDataManager().getTotalClientes() > 0 && MenuAlterno.this.sqLibraryApp.getDataManager().getTotalZonaVendedor(beanUsuario.getClave().toString()) > 0) {
                            args.putInt("page", 2);
                            fragmentBusqCli.setArguments(args);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqCli).commit();
                        } else {
                            Toast.makeText(MenuAlterno.this.getApplicationContext(), "No hay clientes, por favor sincronizar", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.menu_dhasboard:
                        if (MenuAlterno.this.sqLibraryApp.getDataManager().getPedidosPendList().size() > 0) {
                            MenuAlterno.this.startActivity(new Intent(MenuAlterno.this, PendienteActivity.class));
                        } else {
                            Toast.makeText(MenuAlterno.this.getApplicationContext(), "No hay pedidos pendientes", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.menu_notificacion:
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSincro).commit();
                        return true;
                }
                return false;
            }
        });
    }
    private void openFragment(Fragment fragment){
        manager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void loadFirstFragment(){
        FacturaVendedorRep facturaVendedorRep = new FacturaVendedorRep();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, facturaVendedorRep).commit();
    }
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);
    }
}