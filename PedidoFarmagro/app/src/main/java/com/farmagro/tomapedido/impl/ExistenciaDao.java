package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Existencia;

public class ExistenciaDao extends DroidDao<Existencia, Long> {
    public ExistenciaDao(TableDefinition<Existencia> tableDefinition, SQLiteDatabase database) {
        super(Existencia.class, tableDefinition, database);
    }
}
