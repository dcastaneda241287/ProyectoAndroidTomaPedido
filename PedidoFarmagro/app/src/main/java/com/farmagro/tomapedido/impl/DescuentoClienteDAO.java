package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.DescuentoCliente;

public class DescuentoClienteDAO extends DroidDao<DescuentoCliente, Long> {
    public DescuentoClienteDAO(TableDefinition<DescuentoCliente> tableDefinition, SQLiteDatabase database) {
        super(DescuentoCliente.class, tableDefinition, database);
    }
}