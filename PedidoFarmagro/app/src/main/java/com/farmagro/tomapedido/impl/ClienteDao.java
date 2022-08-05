package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Cliente;

public class ClienteDao extends DroidDao<Cliente, Long> {
    public ClienteDao(TableDefinition<Cliente> tableDefinition, SQLiteDatabase database) {
        super(Cliente.class, tableDefinition, database);
    }
}
