package com.example.kate.course2l1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.kate.course2l1.R;

/**
 * Created by Kate on 12.05.2017.
 */

public class DBEmployee extends DBSQLite {

    private static final String SQL_WHERE_BY_ID = BaseColumns._ID + "=?";
    private static final String DB_NAME = "DBEmployee.db";
    private static final int DB_VERSION = 2;

    public DBEmployee(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        DBSQLite.execSQL(db, TableEmployee.SQL_CREATE);
        DBSQLite.execSQL(db, TableDepartment.SQL_CREATE);

        String[] deps = getContext().getResources().getStringArray(
                R  .array.dep_items);
        ContentValues values = new ContentValues(deps.length);

        for (int i = 0; i < deps.length; i++) {

            String[] dep = deps[i].split("-");

            values.put(TableDepartment.C_NAME, dep[0]);
            values.put(TableDepartment.C_LOCA, dep[1]);

            db.insert(TableDepartment.T_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBSQLite.dropTable(db, TableEmployee.T_NAME);
        DBSQLite.dropTable(db, TableDepartment.T_NAME);

        this.onCreate(db);
    }

    public long addDep(String name, String location) {

        ContentValues v = new ContentValues();

        v.put(TableDepartment.C_NAME, name);
        v.put(TableDepartment.C_LOCA, location);

        return this.getWritableDatabase().insert(TableDepartment.T_NAME, null, v);

    }

    public boolean updateDep(String name, String location, long id) {

        ContentValues v = new ContentValues();

        v.put(TableDepartment.C_NAME, name);
        v.put(TableDepartment.C_LOCA, location);

        return 1 == this.getWritableDatabase().update(TableDepartment.T_NAME, v,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }

    public boolean deleteDep(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableDepartment.T_NAME, SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)});
    }

    public long addEmpl(String name, String info, long dep_id) {

        ContentValues v = new ContentValues();

        v.put(TableEmployee.C_NAME, name);
        v.put(TableEmployee.C_INFO, info);
        v.put(TableEmployee.C_DEP_ID, dep_id);

        return this.getWritableDatabase().insert(TableEmployee.T_NAME, null, v);

    }

    public boolean updateEmpl(String name, String info, long dep_id, long id) {

        ContentValues v = new ContentValues();

        v.put(TableEmployee.C_NAME, name);
        v.put(TableEmployee.C_INFO, info);
        v.put(TableEmployee.C_DEP_ID, dep_id);

        return 1 == this.getWritableDatabase().update(TableEmployee.T_NAME, v,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }

    public boolean deleteEmpl(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableEmployee.T_NAME, SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)});
    }


    public static class TableEmployee implements BaseColumns {

        public static final String T_NAME = "tEmpl";
        public static final String C_NAME = "NAME";
        public static final String C_INFO = "INFO";
        public static final String C_DEP_ID  = "DEP_ID";

        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_INFO + " TEXT," +
                C_DEP_ID + " INTEGER)";
    }

    public static class TableDepartment implements BaseColumns {

        public static final String T_NAME = "tDep";
        public static final String C_NAME = "NAME";
        public static final String C_LOCA = "LOCATION";

        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_LOCA + " TEXT)";
    }
}