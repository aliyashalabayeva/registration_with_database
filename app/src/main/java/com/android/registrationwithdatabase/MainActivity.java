package com.android.registrationwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.android.registrationwithdatabase.DatabaseStore.COLUMN_EMAIL;
import static com.android.registrationwithdatabase.DatabaseStore.COLUMN_FULL_NAME;
import static com.android.registrationwithdatabase.DatabaseStore.COLUMN_PASSWORD;
import static com.android.registrationwithdatabase.DatabaseStore.TABLE_USER;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFullName, etEmail, etPassword;
    Button btnCreateAccount, btnLogin;
    DatabaseStore databaseStore;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    public void initViews(){
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLogin = findViewById(R.id.btnLogin);

        btnCreateAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        databaseStore = new DatabaseStore(this);
        sqLiteDatabase = databaseStore.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateAccount:
                boolean createAccount = true;

                if (etFullName.getText().toString().isEmpty()){
                    etFullName.setError("Please, enter your full name");
                    createAccount = false;
                }
                if (etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Please, enter your email");
                    createAccount = false;
                }
                if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Please, enter your password");
                    createAccount = false;
                }


                if (createAccount){

                    ContentValues userValue = new ContentValues();
                    userValue.put(COLUMN_FULL_NAME, etFullName.getText().toString());
                    userValue.put(COLUMN_EMAIL, etEmail.getText().toString());
                    userValue.put(COLUMN_PASSWORD, etPassword.getText().toString());


                    sqLiteDatabase.insert(TABLE_USER, null, userValue);

                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    showDatabaseData();
                } else {
                    Toast.makeText(this, "Please, fill all information", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnLogin:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;

        }
    }
    public void showDatabaseData(){
        Cursor cursor = sqLiteDatabase.rawQuery( " SELECT * FROM " + TABLE_USER,null);

        if ((cursor != null && cursor.getCount() > 0)){
            while (cursor.moveToNext()){
                String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                Log.i("Database", "full name: " + fullName);
                Log.i("Database", "email: " + email);
                Log.i("Database","password: " + password);
            }
        }
    }
}