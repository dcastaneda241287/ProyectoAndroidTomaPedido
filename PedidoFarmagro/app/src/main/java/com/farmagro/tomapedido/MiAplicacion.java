package com.farmagro.tomapedido;

import android.app.Application;

import com.farmagro.tomapedido.dao.DaoManegerEj;
import com.farmagro.tomapedido.dao.DataManager;

public class MiAplicacion extends Application {
    private static MiAplicacion singleton;
    private DaoManegerEj dataManager;

    public static MiAplicacion getInstance(){
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        setDataManager(new DaoManegerEj(this));
    }

    public DaoManegerEj getDataManager() {
        return dataManager;
    }

    public void setDataManager(DaoManegerEj dataManager) {
        this.dataManager = dataManager;
    }
}
