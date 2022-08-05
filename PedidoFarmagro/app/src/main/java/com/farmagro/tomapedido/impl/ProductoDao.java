package com.farmagro.tomapedido.impl;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.dao.DroidDao;
import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Producto;

public class ProductoDao extends DroidDao<Producto, Long> {
    public ProductoDao(TableDefinition<Producto> tableDefinition, SQLiteDatabase database) {
        super(Producto.class, tableDefinition, database);
    }
}