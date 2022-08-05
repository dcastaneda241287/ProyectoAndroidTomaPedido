package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.VendedorCorrelativo;


public class VendedorCorrelativoDao extends DroidDao<VendedorCorrelativo, Long> {
    public VendedorCorrelativoDao(TableDefinition<VendedorCorrelativo> tableDefinition, SQLiteDatabase database) {
        super(VendedorCorrelativo.class, tableDefinition, database);
    }
}