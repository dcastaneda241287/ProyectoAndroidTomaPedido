package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.VendedorMail;

public class VendedorMailDao extends DroidDao<VendedorMail, Long> {
    public VendedorMailDao(TableDefinition<VendedorMail> tableDefinition, SQLiteDatabase database) {
        super(VendedorMail.class, tableDefinition, database);
    }
}