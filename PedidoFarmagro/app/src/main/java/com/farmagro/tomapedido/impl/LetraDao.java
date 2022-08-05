package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Letra;

public class LetraDao extends DroidDao<Letra, Long> {
    public LetraDao(TableDefinition<Letra> tableDefinition, SQLiteDatabase database) {
        super(Letra.class, tableDefinition, database);
    }
}