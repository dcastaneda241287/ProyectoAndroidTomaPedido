package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.ZonaVendedor;

public class ZonaVendedorDao extends DroidDao<ZonaVendedor, Long> {
    public ZonaVendedorDao(TableDefinition<ZonaVendedor> tableDefinition, SQLiteDatabase database) {
        super(ZonaVendedor.class, tableDefinition, database);
    }
}