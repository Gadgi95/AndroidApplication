package com.example.application;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button btn_SwipeForRegister;
    Button btn_login;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        btn_login = findViewById(R.id.btn_login);

//        btn_SwipeForLogin = findViewById(R.id.btn_SwipeForLogin);
//        btn_SwipeForRegister = findViewById(R.id.btn_SwipeForRegister);

        root = findViewById(R.id.root_element);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginWindow();
            }
        });

    }

    private void showLoginWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Авторизоваться");
        dialog.setMessage("Введите логин и пароль для авторизации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View loginWindow = inflater.inflate(R.layout.fragment_login, null);
        dialog.setView(loginWindow);

        final EditText login = loginWindow.findViewById(R.id.et_email);
        final EditText password = loginWindow.findViewById(R.id.et_password);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("авторизоваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(login.getText().toString())) {
                    Snackbar.make(root, "Введите ваш логин", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(root, "Введите пароль", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //ToDo авторизация пользователя

            }
        });


    }
}