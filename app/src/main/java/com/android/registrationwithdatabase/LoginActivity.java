package com.android.registrationwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;
    DatabaseStore databaseStore;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }
    public void initViews(){
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        databaseStore = new DatabaseStore(this);
        sqLiteDatabase = databaseStore.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                boolean LoginAccount = true;
                String userEmail = etEmailLogin.getText().toString();
                String userPassword = etPasswordLogin.getText().toString();

                if (userEmail.isEmpty()){
                    etEmailLogin.setError("Please, enter your email");
                    LoginAccount = false;
                }
                if (userPassword.isEmpty()){
                    etPasswordLogin.setError("Please, enter your password");
                    LoginAccount = false;
                }

                if (LoginAccount){
                    Cursor loginCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " +
                            COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=? ", new String[]{userEmail, userPassword});
                    if (loginCursor != null & loginCursor.getCount() > 0){
                        loginCursor.moveToFirst();

                        Toast.makeText(this, "Welcome " + userEmail, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}