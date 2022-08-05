package com.farmagro.tomapedido.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.farmagro.tomapedido.TableDefinition.UsuarioTableDefinition;
import com.farmagro.tomapedido.impl.UsuarioDao;
import com.farmagro.tomapedido.modelo.Usuario;

public class DaoManegerEj {
    private Context context;
    private SQLiteDatabase database;
    private UsuarioDao usuarioDao;/// = new UsuarioDao(new UsuarioTableDefinition(), this.database);

    public DaoManegerEj(Context context2) {
        setContext(context2);
        setDatabase(new OpenHelper(context2, "FARMAGRODATABASE", (SQLiteDatabase.CursorFactory) null, 2).getWritableDatabase());
    }


    private void openDb() {
        if (!getDatabase().isOpen()) {
            setDatabase(SQLiteDatabase.openDatabase(Environment.getDataDirectory() + "/data/com.farmagro.tomapedido/databases/farmagro.db", (SQLiteDatabase.CursorFactory) null, 0));

        }
        usuarioDao = new UsuarioDao(new UsuarioTableDefinition(), this.database);
    }
    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void setDatabase(SQLiteDatabase database2) {
        this.database = database2;
    }

    public Usuario getUsuario() {
        return (Usuario) getUsuarioDao().get(String.valueOf(1));
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
}
