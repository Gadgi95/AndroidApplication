package com.example.application.ui.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.application.MainActivity;
import com.example.application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;

    Button btn_SwipeForRegister;
    Button btn_login;

    RelativeLayout root;


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        btn_login = findViewById(R.id.btn_login);

        btn_SwipeForRegister = findViewById(R.id.btn_SwipeForRegister);

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
        dialog.setTitle("Авторизация");
        dialog.setMessage("Введите все необходимые данные для авторизации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View loginWindow = inflater.inflate(R.layout.fragment_login, null);
        dialog.setView(loginWindow);

        final EditText login = loginWindow.findViewById(R.id.et_email);
        final EditText password = loginWindow.findViewById(R.id.et_password);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Авторизация", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(login.getText().toString())) {
                    Snackbar.make(root, "Введите логин/email для авторизации", Snackbar.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(root, "Введите пароль для авторизации", Snackbar.LENGTH_SHORT).show();
                }

                //ToDo авторизация пользователя
                auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(LoginFragment.this, MainActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(root, "Ошибка авторизации " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        dialog.show();
    }
}
