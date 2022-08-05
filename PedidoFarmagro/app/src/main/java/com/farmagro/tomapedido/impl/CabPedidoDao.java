package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.CabPedido;

public class CabPedidoDao extends DroidDao<CabPedido, Long> {
    public CabPedidoDao(TableDefinition<CabPedido> tableDefinition, SQLiteDatabase database) {
        super(CabPedido.class, tableDefinition, database);
    }
}