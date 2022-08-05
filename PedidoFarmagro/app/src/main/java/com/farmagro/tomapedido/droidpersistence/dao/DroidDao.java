package com.farmagro.tomapedido.droidpersistence.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.farmagro.tomapedido.droidpersistence.model.FieldModel;
import com.farmagro.tomapedido.droidpersistence.util.DroidUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DroidDao<T, ID extends Serializable> {
    private String[] arrayColumns;
    private SQLiteDatabase database;
    private Field[] fieldDefinition;
    private String idColumn;
    private String insertStatement;
    private List<FieldModel> listFieldModels;
    private final Class<T> model;
    private SQLiteStatement statement;
    private TableDefinition<T> tableDefinition;
    private String tableName;

    public DroidDao(Class<T> model2, TableDefinition<T> tableDefinition2, SQLiteDatabase database2) {
        this.model = model2;
        this.database = database2;
        try {
            this.tableDefinition = tableDefinition2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        getTableDefinition();
        setArrayColumns(TableDefinition.getArrayColumns());
        getTableDefinition();
        setTableName(TableDefinition.getTableName());
        getTableDefinition();
        setFieldDefinition(TableDefinition.getFieldDefinition());
        getTableDefinition();
        String tableName2 = TableDefinition.getTableName();
        getTableDefinition();
        createInsertStatement(tableName2, TableDefinition.getArrayColumns());
        getTableDefinition();
        setIdColumn(TableDefinition.getPK());
        getTableDefinition();
        setListFieldModels(TableDefinition.getLIST_FIELD_MODEL());
        if (getInsertStatement().trim() != "") {
            this.statement = this.database.compileStatement(getInsertStatement());
        }
    }

    public boolean delete(ID id) {
        try {
            this.database.delete(getTableName(), String.valueOf(getIdColumn()) + " = ?", new String[]{String.valueOf(id)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public T get(String id) {
        T object = null;
        Cursor cursor = this.database.query(getTableName(), getArrayColumns(), String.valueOf(getIdColumn()) + " = ?", new String[]{String.valueOf(id)}, (String) null, (String) null, "1");
        if (cursor.moveToFirst()) {
            try {
                object = buildDataFromCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return object;
    }

    public List<T> getAll() {
        List<T> objectList = new ArrayList<>();
        Cursor cursor = this.database.query(getTableName(), getArrayColumns(), (String) null, (String[]) null, (String) null, (String) null, "1");
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                try {
                    T object = buildDataFromCursor(cursor);
                    if (object != null) {
                        objectList.add(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return objectList;
    }

    public List<T> getAllbyClause(String clause, String[] clauseArgs, String groupBy, String having, String orderBy) {
        List<T> objectList = new ArrayList<>();
        Cursor cursor = this.database.query(getTableName(), getArrayColumns(), clause, clauseArgs, groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            do {
                try {
                    T object = buildDataFromCursor(cursor);
                    if (object != null) {
                        objectList.add(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return objectList;
    }

    public T getByClause(String clause, String[] clauseArgs) {
        T object = null;
        Cursor cursor = this.database.query(getTableName(), getArrayColumns(), clause, clauseArgs, (String) null, (String) null, "1");
        if (cursor.moveToFirst()) {
            try {
                object = buildDataFromCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return object;
    }

    public long save(T object) throws Exception {
        String value;
        getTableDefinition();
        if (TableDefinition.getPK() == "") {
            this.statement.clearBindings();
            for (int e = 0; e < getArrayColumns().length; e++) {
                int i = 0;
                while (i < object.getClass().getDeclaredMethods().length) {
                    Method method = object.getClass().getDeclaredMethods()[i];
                    if (method.getName().equalsIgnoreCase("get" + getArrayColumns()[e])) {
                        i = object.getClass().getDeclaredMethods().length;
                        Type type = method.getReturnType();
                        try {
                            if (type == Integer.TYPE) {
                                this.statement.bindLong(e + 1, ((Integer) method.invoke(object, new Object[0])).longValue());
                            } else if (type == Long.class || type == Short.class || type == Long.TYPE) {
                                this.statement.bindLong(e + 1, ((Long) method.invoke(object, new Object[0])).longValue());
                            } else {
                                if (type != Double.class) {
                                    if (!(type == Double.TYPE || type == Float.TYPE)) {
                                        if (type == String.class) {
                                            SQLiteStatement sQLiteStatement = this.statement;
                                            int i2 = e + 1;
                                            sQLiteStatement.bindString(i2, (String) method.invoke(object, new Object[0]));
                                        } else if (type == Date.class) {
                                            this.statement.bindString(e + 1, DroidUtils.convertDateToString((Date) method.invoke(object, new Object[0])));
                                        } else if (type == byte[].class) {
                                            SQLiteStatement sQLiteStatement2 = this.statement;
                                            int i3 = e + 1;
                                            sQLiteStatement2.bindBlob(i3, (byte[]) method.invoke(object, new Object[0]));
                                        } else {
                                            this.statement.bindNull(e + 1);
                                        }
                                    }
                                }
                                this.statement.bindDouble(e + 1, ((Double) method.invoke(object, new Object[0])).doubleValue());
                            }
                        } catch (Exception ex) {
                            throw new Exception(" Failed to invoke the method " + method.getName() + ", cause:" + ex.getMessage());
                        }
                    }
                    i++;
                }
            }
            return this.statement.executeInsert();
        }
        ContentValues values = new ContentValues();
        for (int e2 = 0; e2 < getListFieldModels().size(); e2++) {
            FieldModel fieldModel = getListFieldModels().get(e2);
            int i4 = 0;
            while (i4 < object.getClass().getDeclaredMethods().length) {
                Method method2 = object.getClass().getDeclaredMethods()[i4];
                if (method2.getName().equalsIgnoreCase("get" + fieldModel.getFieldName())) {
                    i4 = object.getClass().getDeclaredMethods().length;
                    Object outputMethod = method2.invoke(object, new Object[0]);
                    if (method2.getReturnType() == Date.class) {
                        value = DroidUtils.convertDateToString((Date) outputMethod);
                    } else {
                        value = outputMethod.toString();
                    }
                    values.put(fieldModel.getColumnName(), value);
                }
                i4++;
            }
        }
        return this.database.insert(getTableName(), (String) null, values);
    }

    public void update(T object, ID id) throws Exception {
        String value;
        ContentValues values = new ContentValues();
        for (int e = 0; e < getListFieldModels().size(); e++) {
            FieldModel fieldModel = getListFieldModels().get(e);
            int i = 0;
            while (i < object.getClass().getDeclaredMethods().length) {
                Method method = object.getClass().getDeclaredMethods()[i];
                if (method.getName().equalsIgnoreCase("get" + fieldModel.getFieldName())) {
                    i = object.getClass().getDeclaredMethods().length;
                    Object outputMethod = method.invoke(object, new Object[0]);
                    if (method.getReturnType() == Date.class) {
                        value = DroidUtils.convertDateToString((Date) outputMethod);
                    } else {
                        value = outputMethod.toString();
                    }
                    values.put(fieldModel.getColumnName(), value);
                }
                i++;
            }
        }
        this.database.update(getTableName(), values, String.valueOf(getIdColumn()) + " = ?", new String[]{String.valueOf(id)});
    }

    public void update(T object, String clause, String[] clauseArgs) throws Exception {
        String value;
        ContentValues values = new ContentValues();
        for (int e = 0; e < getListFieldModels().size(); e++) {
            FieldModel fieldModel = getListFieldModels().get(e);
            int i = 0;
            while (i < object.getClass().getDeclaredMethods().length) {
                Method method = object.getClass().getDeclaredMethods()[i];
                if (method.getName().equalsIgnoreCase("get" + fieldModel.getFieldName())) {
                    i = object.getClass().getDeclaredMethods().length;
                    Object outputMethod = method.invoke(object, new Object[0]);
                    if (method.getReturnType() == Date.class) {
                        value = DroidUtils.convertDateToString((Date) outputMethod);
                    } else {
                        value = outputMethod.toString();
                    }
                    values.put(fieldModel.getColumnName(), value);
                }
                i++;
            }
        }
        this.database.update(getTableName(), values, clause, clauseArgs);
    }

    public String getInsertStatement() {
        return this.insertStatement;
    }

    public void setInsertStatement(String insertStatement2) {
        this.insertStatement = insertStatement2;
    }

    public TableDefinition<T> getTableDefinition() {
        return this.tableDefinition;
    }

    public void setTableDefinition(TableDefinition<T> tableDefinition2) {
        this.tableDefinition = tableDefinition2;
    }

    private void createInsertStatement(String tableName2, String[] columns) {
        StringBuffer values = new StringBuffer();
        StringBuffer tableColumns = new StringBuffer();
        for (int i = 0; i < columns.length; i++) {
            if (i > 0 && i < columns.length) {
                values.append(",");
                tableColumns.append(",");
            }
            values.append("?");
            tableColumns.append(columns[i]);
        }
        setInsertStatement("insert into " + tableName2 + "(" + tableColumns + ") " + "values ( " + values + ")");
    }

    public T buildDataFromCursor(Cursor cursor) throws Exception {
        T object = null;
        Field[] fields = getFieldDefinition();
        if (cursor != null) {
            object = this.model.newInstance();
            Method[] methods = object.getClass().getMethods();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                int e = 0;
                while (e < methods.length) {
                    try {
                        if (methods[e].getName().trim().equalsIgnoreCase("set" + fields[i].getName())) {
                            Method method = methods[e];
                            e = methods.length;
                            Type type = method.getParameterTypes()[0];
                            if (type == Integer.TYPE || type == Integer.class) {
                                method.invoke(object, new Object[]{Integer.valueOf(Long.valueOf(cursor.getLong(i)).intValue())});
                            } else if (type == Long.class || type == Long.TYPE) {
                                method.invoke(object, new Object[]{Long.valueOf(cursor.getLong(i))});
                            } else {
                                if (type != Double.class) {
                                    if (type != Double.TYPE) {
                                        if (type == Float.TYPE) {
                                            method.invoke(object, new Object[]{Float.valueOf(cursor.getFloat(i))});
                                        } else if (type == String.class) {
                                            method.invoke(object, new Object[]{cursor.getString(i)});
                                        } else if (type == Date.class) {
                                            method.invoke(object, new Object[]{DroidUtils.convertStringToDate(cursor.getString(i))});
                                        } else if (type == Short.class) {
                                            method.invoke(object, new Object[]{Short.valueOf(cursor.getShort(i))});
                                        } else if (type == Boolean.class || type == Boolean.TYPE) {
                                            Object[] objArr = new Object[1];
                                            objArr[0] = Boolean.valueOf(cursor.getInt(i) == 1);
                                            method.invoke(object, objArr);
                                        } else {
                                            method.invoke(object, new Object[]{cursor.getBlob(i)});
                                        }
                                    }
                                }
                                method.invoke(object, new Object[]{String.valueOf(cursor.getString(i))});
                            }
                        }
                        e++;
                    } catch (Exception e2) {
                        int i2 = e;
                        throw new Exception(" Failed to cast a object, maybe a method not declared, cause:" + e2.getMessage());
                    }
                }
            }
        }
        if (object.getClass().getDeclaredFields().length != 0) {
            return object;
        }
        throw new Exception("Cannot be cast a no field object!");
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public String[] getArrayColumns() {
        return this.arrayColumns;
    }

    public void setArrayColumns(String[] arrayColumns2) {
        this.arrayColumns = arrayColumns2;
    }

    public Field[] getFieldDefinition() {
        return this.fieldDefinition;
    }

    public void setFieldDefinition(Field[] fieldDefinition2) {
        this.fieldDefinition = fieldDefinition2;
    }

    public String getIdColumn() {
        return this.idColumn;
    }

    public void setIdColumn(String idColumn2) {
        this.idColumn = idColumn2;
    }

    public List<FieldModel> getListFieldModels() {
        return this.listFieldModels;
    }

    public void setListFieldModels(List<FieldModel> listFieldModels2) {
        this.listFieldModels = listFieldModels2;
    }
}
