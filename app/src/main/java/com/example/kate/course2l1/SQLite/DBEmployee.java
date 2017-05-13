package com.example.kate.course2l1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.kate.course2l1.App;
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

        Log.d("employee", TableEmployee.SQL_CREATE);
        Log.d("TableDepartment", TableDepartment.SQL_CREATE);


        DBSQLite.execSQL(db, TableEmployee.SQL_CREATE);
        DBSQLite.execSQL(db, TableDepartment.SQL_CREATE);

        String[] deps = getContext().getResources().getStringArray(
                R  .array.dep_items);
        ContentValues values = new ContentValues(deps.length);

        for (int i = 0; i < deps.length; i++) {

            String[] dep = deps[i].split("-");

            values.put(TableDepartment.C_DEP, dep[0]);
            values.put(TableDepartment.C_LOCA, dep[1]);

            db.insert(TableDepartment.T_NAME, null, values);
        }

        String[] empls = getContext().getResources().getStringArray(
                R  .array.empl_items);
        ContentValues contentValues = new ContentValues(empls.length);

        for (int i = 0; i < empls.length; i++) {

            String[] empl = empls[i].split("-");

            contentValues.put(TableEmployee.C_NAME, empl[0]);
            contentValues.put(TableEmployee.C_INFO, empl[1]);
            contentValues.put(TableEmployee.C_DEP_ID, empl[2]);

            db.insert(TableEmployee.T_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBSQLite.dropTable(db, TableEmployee.T_NAME);
        DBSQLite.dropTable(db, TableDepartment.T_NAME);

        this.onCreate(db);
    }

    public Cursor getCursorForLinkedDBs(){

        String SQLQuery = "SELECT " +
                DBEmployee.TableEmployee.T_NAME +  "." + DBEmployee.TableEmployee._ID + ", " +
                DBEmployee.TableEmployee.T_NAME +  "." + DBEmployee.TableEmployee.C_NAME + ", " +
                DBEmployee.TableEmployee.C_INFO + ", " +
                DBEmployee.TableDepartment.C_DEP + ", " +
                DBEmployee.TableDepartment.C_LOCA +
                " FROM " +
                DBEmployee.TableEmployee.T_NAME +
                " INNER JOIN " + DBEmployee.TableDepartment.T_NAME +
                " ON " + DBEmployee.TableEmployee.T_NAME +  "." + DBEmployee.TableEmployee.C_DEP_ID + " = " +
                DBEmployee.TableDepartment.T_NAME + "." + DBEmployee.TableDepartment._ID;

        return App.getDB().getReadableDatabase().rawQuery(SQLQuery, null);
    }

    public long addDep(String name, String location) {

        ContentValues v = new ContentValues();

        v.put(TableDepartment.C_DEP, name);
        v.put(TableDepartment.C_LOCA, location);

        return this.getWritableDatabase().insert(TableDepartment.T_NAME, null, v);

    }

    public boolean updateDep(String name, String location, long id) {

        ContentValues v = new ContentValues();

        v.put(TableDepartment.C_DEP, name);
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
        public static final String C_NAME = "name";
        public static final String C_INFO = "info";
        public static final String C_DEP_ID  = "dep_id";

        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_INFO + " TEXT," +
                C_DEP_ID + " INTEGER," +
                "FOREIGN KEY ("+C_DEP_ID+") REFERENCES "+TableDepartment.T_NAME+" ("+TableDepartment._ID+")"
        + ")";
    }

    public static class TableDepartment implements BaseColumns {

        public static final String T_NAME = "tDep";
        public static final String C_DEP = "dep";
        public static final String C_LOCA = "location";

        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_DEP + " TEXT," +
                C_LOCA + " TEXT)";
    }
}
