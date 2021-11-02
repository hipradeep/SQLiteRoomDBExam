package com.sqliteexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqliteexam.adapters.ManualAdapter;
import com.sqliteexam.databasehelper.DatabaseHelper;
import com.sqliteexam.models.ManualCustomerModel;

import java.util.List;

public class AddManually extends AppCompatActivity {
    EditText et_customer_name, et_customer_age;
    SwitchCompat sc_active_inactive;
    Button btn_add, btn_view;
    RecyclerView rv_view_sqlite_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);
        et_customer_name = findViewById(R.id.et_customer_name);
        et_customer_age = findViewById(R.id.et_customer_age);
        sc_active_inactive = findViewById(R.id.sc_active_inactive);
        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        rv_view_sqlite_data = findViewById(R.id.rv_view_sqlite_data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_view_sqlite_data.setLayoutManager(linearLayoutManager);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualCustomerModel manualCustomerModel;
                try {
                    manualCustomerModel = new ManualCustomerModel(-1, et_customer_name.getText().toString().trim(),
                            Integer.parseInt(String.valueOf(et_customer_age.getText())), sc_active_inactive.isChecked());

                } catch (Exception e) {
                    Toast.makeText(AddManually.this, "Values Not Added!", Toast.LENGTH_SHORT).show();
                    manualCustomerModel = new ManualCustomerModel(-1, "error", 0, false);
                }

                Log.e("TAG", manualCustomerModel.toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(AddManually.this);

                boolean success = databaseHelper.addOne(manualCustomerModel, DatabaseHelper.TYPE1);
                Toast.makeText(AddManually.this, "Success = " + success, Toast.LENGTH_SHORT).show();
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddManually.this);
                List<ManualCustomerModel> everyone = databaseHelper.getEveryone();


                ManualAdapter adapter=new ManualAdapter(everyone, AddManually.this);
                rv_view_sqlite_data.setAdapter(adapter);

            }
        });

    }
}