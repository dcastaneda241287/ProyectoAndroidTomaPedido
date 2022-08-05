package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Usuario;

public class UsuarioDao extends DroidDao<Usuario, Long> {
    public UsuarioDao(TableDefinition<Usuario> tableDefinition, SQLiteDatabase database) {
        super(Usuario.class, tableDefinition, database);
    }
}
