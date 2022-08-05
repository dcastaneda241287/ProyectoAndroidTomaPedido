package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.VendedorBodega;


public class VendedorBodegaDao extends DroidDao<VendedorBodega, Long> {
    public VendedorBodegaDao(TableDefinition<VendedorBodega> tableDefinition, SQLiteDatabase database) {
        super(VendedorBodega.class, tableDefinition, database);
    }
}