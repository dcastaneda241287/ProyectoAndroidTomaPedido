package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Credito;

public class CreditoDao extends DroidDao<Credito, Long> {
    public CreditoDao(TableDefinition<Credito> tableDefinition, SQLiteDatabase database) {
        super(Credito.class, tableDefinition, database);
    }
}