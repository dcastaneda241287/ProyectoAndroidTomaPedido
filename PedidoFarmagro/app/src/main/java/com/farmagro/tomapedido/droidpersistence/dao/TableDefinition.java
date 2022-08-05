package com.farmagro.tomapedido.droidpersistence.dao;

import android.database.sqlite.SQLiteDatabase;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.ForeignKey;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;
import com.farmagro.tomapedido.droidpersistence.model.FieldModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class TableDefinition<T> {
    private static String[] ARRAY_COLUMNS;
    private static StringBuilder COLUMNS;
    private static StringBuilder CREATE_STATEMENT;
    private static Field[] FIELD_DEFINITION;
    private static StringBuilder FOREIGN_KEY;
    private static List<FieldModel> LIST_FIELD_MODEL;
    private static Class OBJECT;
    private static String PK = "";
    private static String TABLE_NAME;
    private static TableDefinition singleton;
    private final Class<T> model;

    public TableDefinition(Class<T> model2) {
        singleton = this;
        this.model = model2;
        try {
            OBJECT = Class.forName(model2.getName());
            createTableDefinition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTableDefinition() throws Exception {
        String type;
        if (OBJECT.isAnnotationPresent(Table.class)) {
            Annotation annotation = OBJECT.getAnnotation(Table.class);
            TABLE_NAME = annotation.getClass().getMethod("name", new Class[0]).invoke(annotation, new Object[0]).toString().toUpperCase();
            CREATE_STATEMENT = new StringBuilder();
            FOREIGN_KEY = new StringBuilder();
            COLUMNS = new StringBuilder();
            LIST_FIELD_MODEL = new ArrayList();
            FIELD_DEFINITION = OBJECT.getDeclaredFields();
            ARRAY_COLUMNS = new String[FIELD_DEFINITION.length];
            int i = 0;
            while (i < FIELD_DEFINITION.length) {
                Field field = FIELD_DEFINITION[i];
                String primaryKeyText = "";
                if (field.isAnnotationPresent(Column.class)) {
                    Annotation annotation2 = field.getAnnotation(Column.class);
                    Object objectName = annotation2.getClass().getMethod("name", new Class[0]).invoke(annotation2, new Object[0]);
                    if (objectName == null || objectName.toString() == "") {
                        objectName = field.getName();
                    }
                    if (field.isAnnotationPresent(PrimaryKey.class)) {
                        PK = objectName.toString();
                        Annotation pKey_annotation = field.getAnnotation(PrimaryKey.class);
                        primaryKeyText = " PRIMARY KEY ";
                        if (Boolean.valueOf(pKey_annotation.getClass().getMethod("autoIncrement", new Class[0]).invoke(pKey_annotation, new Object[0]).toString()).booleanValue()) {
                            primaryKeyText = String.valueOf(primaryKeyText) + " AUTOINCREMENT ";
                        }
                    }
                    if (field.isAnnotationPresent(ForeignKey.class)) {
                        Annotation fkey_annotation = field.getAnnotation(ForeignKey.class);
                        Object fkey_tableReferenceName = fkey_annotation.getClass().getMethod("tableReference", new Class[0]).invoke(fkey_annotation, new Object[0]);
                        Object fkey_OnUpCascadeValue = fkey_annotation.getClass().getMethod("onUpdateCascade", new Class[0]).invoke(fkey_annotation, new Object[0]);
                        Object fkey_OnDelCascadeValue = fkey_annotation.getClass().getMethod("onDeleteCascade", new Class[0]).invoke(fkey_annotation, new Object[0]);
                        String columnReference = fkey_annotation.getClass().getMethod("columnReference", new Class[0]).invoke(fkey_annotation, new Object[0]).toString();
                        if (columnReference == "") {
                            columnReference = "_id";
                        }
                        FOREIGN_KEY.append(", FOREIGN KEY (" + objectName.toString() + ") REFERENCES " + fkey_tableReferenceName.toString().toUpperCase() + " (" + columnReference + ")");
                        if (Boolean.valueOf(fkey_OnUpCascadeValue.toString()).booleanValue()) {
                            FOREIGN_KEY.append(" ON UPDATE CASCADE ");
                        }
                        if (Boolean.valueOf(fkey_OnDelCascadeValue.toString()).booleanValue()) {
                            FOREIGN_KEY.append(" ON DELETE CASCADE ");
                        }
                    }
                    if (field.getType() == Integer.TYPE || field.getType() == Integer.class || field.getType() == Long.class || field.getType() == Long.TYPE) {
                        type = " INTEGER ";
                    } else if (field.getType() == String.class || field.getType() == Character.TYPE) {
                        type = " TEXT ";
                    } else if (field.getType() == Double.class || field.getType() == Float.class || field.getType() == Double.TYPE) {
                        type = " REAL ";
                    } else if (field.getType() == BigDecimal.class) {
                        type = " NUMERIC ";
                    } else if (field.getType() == Date.class) {
                        type = " TIMESTAMP ";
                    } else if (field.getType() == Boolean.class || field.getType() == Boolean.TYPE) {
                        type = " BOOLEAN ";
                    } else {
                        type = " NONE ";
                    }
                    if (i == FIELD_DEFINITION.length - 1) {
                        if (objectName != null) {
                            CREATE_STATEMENT.append(String.valueOf(objectName.toString()) + " " + type + primaryKeyText);
                            COLUMNS.append(objectName.toString());
                        } else {
                            CREATE_STATEMENT = null;
                            throw new Exception("Property 'name' not declared in the field --> " + field.getName());
                        }
                    } else if (objectName != null) {
                        CREATE_STATEMENT.append(String.valueOf(objectName.toString()) + " " + type + primaryKeyText + ", ");
                        COLUMNS.append(String.valueOf(objectName.toString()) + " , ");
                    } else {
                        CREATE_STATEMENT = null;
                        throw new Exception("Property 'name' not declared in the field --> " + field.getName());
                    }
                    ARRAY_COLUMNS[i] = objectName.toString();
                    if (!primaryKeyText.contains("AUTOINCREMENT")) {
                        FieldModel fieldModel = new FieldModel();
                        fieldModel.setColumnName(objectName.toString());
                        fieldModel.setFieldName(field.getName());
                        LIST_FIELD_MODEL.add(fieldModel);
                    }
                    i++;
                } else {
                    CREATE_STATEMENT = null;
                    throw new Exception("Annotation @Column not declared in the field --> " + field.getName());
                }
            }
            if (FOREIGN_KEY.toString() != "") {
                CREATE_STATEMENT.append(FOREIGN_KEY);
            }
            CREATE_STATEMENT.append(");");
            if (getPK() == "") {
                StringBuilder sb = new StringBuilder();
                sb.append("CREATE TABLE " + TABLE_NAME + " (");
                sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
                sb.append(CREATE_STATEMENT);
                String[] columns = new String[(ARRAY_COLUMNS.length + 1)];
                columns[0] = "_id";
                for (int i2 = 0; i2 < ARRAY_COLUMNS.length; i2++) {
                    columns[i2 + 1] = ARRAY_COLUMNS[i2];
                }
                ARRAY_COLUMNS = columns;
                CREATE_STATEMENT = sb;
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("CREATE TABLE " + TABLE_NAME + " (");
            sb2.append(CREATE_STATEMENT);
            CREATE_STATEMENT = sb2;
            return;
        }
        CREATE_STATEMENT = null;
        throw new Exception("Annotation @Table not declared in class " + OBJECT.getSimpleName());
    }

    public static void onCreate(SQLiteDatabase db) throws Exception {
        if (CREATE_STATEMENT != null) {
            db.execSQL(CREATE_STATEMENT.toString());
            return;
        }
        throw new Exception("Table not created, the Create DDL not found");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public StringBuilder getColumns() {
        return COLUMNS;
    }

    public void setColumns(StringBuilder columns) {
        COLUMNS = columns;
    }

    public static String[] getArrayColumns() {
        return ARRAY_COLUMNS;
    }

    public static Field[] getFieldDefinition() {
        return FIELD_DEFINITION;
    }

    public static String getPK() {
        return PK;
    }

    public void setPK(String pk) {
        PK = pk;
    }

    public static TableDefinition getInstance() {
        return singleton;
    }

    public static List<FieldModel> getLIST_FIELD_MODEL() {
        return LIST_FIELD_MODEL;
    }

    public static void setLIST_FIELD_MODEL(List<FieldModel> lIST_FIELD_MODEL) {
        LIST_FIELD_MODEL = lIST_FIELD_MODEL;
    }
}
