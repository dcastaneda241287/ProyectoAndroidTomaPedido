package com.farmagro.tomapedido;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.util.Constante;

import java.util.ArrayList;


public class MenuPrincipal extends AppCompatActivity {

    /*
 DECLARACIONES
 */
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        this.ab = getSupportActionBar();
        this.ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        //this.setTitle((CharSequence) "Menu Principal");
        //this.ab.setIcon((int) R.drawable.ic_header);
        this.sqLibraryApp = (SQLibraryApp) getApplication();
        this.page = getIntent().getExtras().getInt("page");
        //itemTitle = activityTitle = getTitle();
        tagTitles = getResources().getStringArray(R.array.Tags);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Crear elementos de la lista
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(Constante.PEDIDOS, R.drawable.order2));
        items.add(new DrawerItem(Constante.LINEA_CRED, R.drawable.lineacredito));
        items.add(new DrawerItem(Constante.CONSULTA, R.drawable.consulta));
        items.add(new DrawerItem("Enviar / Eliminar pendientes (" + this.sqLibraryApp.getDataManager().getPedidosPendList().size() + ")", R.drawable.pendiente));
        items.add(new DrawerItem(Constante.SINCRONIZACION, R.drawable.sincronizacion2));
        items.add(new DrawerItem(Constante.SALIR, R.drawable.salir));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Habilitar el icono de la app por si hay algún estilo que lo deshabilitó
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Crear ActionBarDrawerToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                //R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(itemTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(activityTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }
        };
        //Seteamos la escucha
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(page);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Reemplazar el contenido del layout principal por un fragmento
        ArticleFragment fragment = new ArticleFragment();
        SincroActivity fragmentSincro = new SincroActivity();
        BusqClienteActivity fragmentBusqCli = new BusqClienteActivity();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_ARTICLES_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        Bundle datosAEnviar = new Bundle();
        // Aquí pon todos los datos que quieras en formato clave, valor
        datosAEnviar.putLong("id", 123L);
        // Y puedes pasarle más datos..
        datosAEnviar.putInt("edad", 21);
        datosAEnviar.putString("nombre", "Parzibyte");


        if (position == 0) {
            if (MenuPrincipal.this.sqLibraryApp.getDataManager().getTotalClientes() > 0) {
                args.putInt("page", 1);
                fragmentBusqCli.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBusqCli).commit();
            } else {
                Toast.makeText(MenuPrincipal.this.getApplicationContext(), "No hay clientes, por favor sincronizar", Toast.LENGTH_SHORT).show();
            }

        }
        if (position == 1) {
            /*if (MenuActivity.this.sqLibraryApp.getDataManager().getTotalClientes() > 0) {
                Intent intent2 = new Intent(MenuActivity.this, BusqClienteActivity.class);
                intent2.putExtra("page", 2);
                MenuActivity.this.startActivity(intent2);
            } else {
                Toast.makeText(MenuActivity.this.getApplicationContext(), "No hay clientes, por favor sincronizar", 0).show();
            }*/

        }
        if (position == 2) {
           /* Intent intent3 = new Intent(MenuActivity.this, BusqProductoActivity.class);
            intent3.putExtra("page", 2);
            MenuActivity.this.startActivity(intent3);*/

        }
        if (position == 3) {
            if (MenuPrincipal.this.sqLibraryApp.getDataManager().getPedidosPendList().size() > 0) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, PendienteActivity.class));
            } else {
                Toast.makeText(MenuPrincipal.this.getApplicationContext(), "No hay pedidos pendientes", Toast.LENGTH_SHORT).show();
            }

        }
        if (position == 4) {
            //MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, SincronizacionActivity.class));
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSincro).commit();
        }
        if (position == 5) {
            MenuPrincipal.this.exitApp();
        }



        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    /* Método auxiliar para setear el titulo de la action bar */
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void exitApp() {
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage("Desea salir de la aplicacion?").setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MenuPrincipal.this.finish();
            }
        }).setNegativeButton(R.string.lblCancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).create().show();
    }
}