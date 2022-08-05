package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.DetPedido;

public class DetPedidoDao extends DroidDao<DetPedido, Long> {
    public DetPedidoDao(TableDefinition<DetPedido> tableDefinition, SQLiteDatabase database) {
        super(DetPedido.class, tableDefinition, database);
    }
}