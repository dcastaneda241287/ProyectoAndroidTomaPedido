package com.farmagro.tomapedido.application;

import android.app.Application;

import com.farmagro.tomapedido.dao.DaoManegerEj;
import com.farmagro.tomapedido.dao.DataManager;

public class SQLibraryApp extends Application {
    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        setDataManager(new DataManager(this));
    }

    public void onTerminate() {
        super.onTerminate();
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public void setDataManager(DataManager dataManager2) {
        this.dataManager = dataManager2;
    }
}
