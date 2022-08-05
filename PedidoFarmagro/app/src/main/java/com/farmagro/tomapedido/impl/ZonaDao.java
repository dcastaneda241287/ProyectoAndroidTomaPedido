package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Zona;

public class ZonaDao extends DroidDao<Zona, Long> {
    public ZonaDao(TableDefinition<Zona> tableDefinition, SQLiteDatabase database) {
        super(Zona.class, tableDefinition, database);
    }
}