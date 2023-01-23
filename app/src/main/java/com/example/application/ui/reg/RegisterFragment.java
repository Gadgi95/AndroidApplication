package com.example.application.ui.reg;

import android.annotation.SuppressLint;
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
import com.example.application.R;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends AppCompatActivity {

    Button btn_register;
    Button btn_SwipeForLogin;

    RelativeLayout root;

    RegHelper regHelper;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        btn_register = findViewById(R.id.btn_register);

        btn_SwipeForLogin = findViewById(R.id.btn_SwipeForLogin);

        root = findViewById(R.id.root_element);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegWindow();
            }
        });

    }

    private void showRegWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите все необходимые данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View regWindow = inflater.inflate(R.layout.fragment_register, null);
        dialog.setView(regWindow);

        final EditText fullName = regWindow.findViewById(R.id.et_name);
        final EditText email = regWindow.findViewById(R.id.et_email);
        final EditText password = regWindow.findViewById(R.id.et_password);
        final EditText reTypePassword = regWindow.findViewById(R.id.et_repassword);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Регистрация", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(fullName.getText().toString())) {
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите ваш email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(root, "Введите пароль", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(reTypePassword.getText().toString())) {
                    Snackbar.make(root, "Повторите введенный пароль пароль", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(!password.getText().toString().equals(reTypePassword.getText().toString())) {
                    Snackbar.make(root, "Введенные пароли не совпадают", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //ToDo регистрация пользователя

                regHelper.regHelper(fullName.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString());
            }
        });

        dialog.show();

    }

}