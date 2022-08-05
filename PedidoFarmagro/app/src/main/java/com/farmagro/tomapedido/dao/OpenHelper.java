package com.farmagro.tomapedido.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.farmagro.tomapedido.TableDefinition.BodegaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.CabPedidoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ClienteTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ContadoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.CreditoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.DescuentoClienteTableDefinition;
import com.farmagro.tomapedido.TableDefinition.DetPedidoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ExistenciaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.LetraTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ProductoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ProveedorTableDefinition;
import com.farmagro.tomapedido.TableDefinition.TmpDetalleTableDefinition;
import com.farmagro.tomapedido.TableDefinition.UsuarioTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorBodegaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorCorrelativoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorMailTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ZonaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ZonaVendedorTableDefinition;

public class OpenHelper extends SQLiteOpenHelper {
    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    public void onCreate(SQLiteDatabase db) {
        try {
            new UsuarioTableDefinition();
            UsuarioTableDefinition.onCreate(db);
            new ProductoTableDefinition();
            ProductoTableDefinition.onCreate(db);
            new BodegaTableDefinition();
            BodegaTableDefinition.onCreate(db);
            new ExistenciaTableDefinition();
            ExistenciaTableDefinition.onCreate(db);
            new ClienteTableDefinition();
            ClienteTableDefinition.onCreate(db);
            new ContadoTableDefinition();
            ContadoTableDefinition.onCreate(db);
            new CreditoTableDefinition();
            CreditoTableDefinition.onCreate(db);
            new LetraTableDefinition();
            LetraTableDefinition.onCreate(db);
            new ProveedorTableDefinition();
            ProveedorTableDefinition.onCreate(db);
            new TmpDetalleTableDefinition();
            TmpDetalleTableDefinition.onCreate(db);
            new CabPedidoTableDefinition();
            CabPedidoTableDefinition.onCreate(db);
            new DetPedidoTableDefinition();
            DetPedidoTableDefinition.onCreate(db);
            new ZonaVendedorTableDefinition();
            ZonaVendedorTableDefinition.onCreate(db);
            new ZonaTableDefinition();
            ZonaTableDefinition.onCreate(db);
            new VendedorBodegaTableDefinition();
            VendedorBodegaTableDefinition.onCreate(db);
            new VendedorCorrelativoTableDefinition();
            VendedorCorrelativoTableDefinition.onCreate(db);
            new VendedorMailTableDefinition();
            VendedorMailTableDefinition.onCreate(db);
            new DescuentoClienteTableDefinition();
            DescuentoClienteTableDefinition.onCreate(db);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            new UsuarioTableDefinition();
            UsuarioTableDefinition.onUpgrade(db, oldVersion, newVersion);
            //new BodegaTableDefinition();
            //BodegaTableDefinition.onUpgrade(db, oldVersion, newVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
