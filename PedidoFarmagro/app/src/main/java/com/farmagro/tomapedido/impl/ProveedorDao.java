package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Proveedor;

public class ProveedorDao extends DroidDao<Proveedor, Long> {
    public ProveedorDao(TableDefinition<Proveedor> tableDefinition, SQLiteDatabase database) {
        super(Proveedor.class, tableDefinition, database);
    }
}