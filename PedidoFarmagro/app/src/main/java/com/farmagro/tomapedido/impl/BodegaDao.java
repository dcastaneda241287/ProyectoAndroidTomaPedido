package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Bodega;

public class BodegaDao extends DroidDao<Bodega, Long> {
    public BodegaDao(TableDefinition<Bodega> tableDefinition, SQLiteDatabase database) {
        super(Bodega.class, tableDefinition, database);
    }
}
