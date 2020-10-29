package com.example.crudapp;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,category,serialNumber;
    TextView dbList;
    DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.e_name);
        category = findViewById(R.id.e_category);
        serialNumber = findViewById(R.id.e_serial);
        dbList = findViewById(R.id.t_dblist);

        //creation of the db
        dbController = new DatabaseController(this,"Warehouse.db",null,1);//the second parameter is the name of the db
    }

    public void btn_click(View view) {
        switch (view.getId()){

            case R.id.b_insert:
                //converting the edittext number to INT
                String serialValue = serialNumber.getText().toString();
                int serialNum = Integer.parseInt(serialValue);
                try{
                    dbController.insertItem(name.getText().toString(),category.getText().toString(),serialNum);
                    Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                }
                catch (SQLiteException e){
                    Toast.makeText(MainActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.b_delete:
                //converting the edittext number to INT
                String serialVal = serialNumber.getText().toString();
                int serialN = Integer.parseInt(serialVal);
                dbController.deleteItem(serialN);
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.b_update:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Enter new name ");
                final EditText newName = new EditText(this);
                dialog.setView(newName);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //here we call the Update method of dbcontroller
                        dbController.updateItem(name.getText().toString(),newName.getText().toString());
                        Toast.makeText(MainActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            case R.id.b_list:
                dbController.listAllProducts(dbList);
                break;

        }
    }
}
