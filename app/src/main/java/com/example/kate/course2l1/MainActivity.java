package com.example.kate.course2l1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.kate.course2l1.SQLite.DBEmployee;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.view_list);

        Cursor c = App.getDB().getCursorForLinkedDBs();

        String[] from = {DBEmployee.TableEmployee.C_NAME, DBEmployee.TableEmployee.C_INFO, DBEmployee.TableDepartment.C_DEP, DBEmployee.TableDepartment.C_LOCA};
        int[] to = {R.id.empName, R.id.empInfo, R.id.depName, R.id.depLocation};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, c, from, to, 1);
        listView.setAdapter(adapter);

        App.getDB().addEmpl("Инокентий", "Тестировщик", 3);
        App.getDB().deleteEmpl(4);

    }
}
