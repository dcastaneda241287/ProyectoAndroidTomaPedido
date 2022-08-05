package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Contado;

public class ContadoDao extends DroidDao<Contado, Long> {
    public ContadoDao(TableDefinition<Contado> tableDefinition, SQLiteDatabase database) {
        super(Contado.class, tableDefinition, database);
    }
}
