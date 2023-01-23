package com.example.application.ui.reg;

import com.example.application.model.Role;
import com.example.application.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;

public class RegHelper {

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;


    public void regHelper(String name , String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                User user = new User();

                //ToDo id - null (id создается в БД автоматически???)

                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setRoles(Collections.singleton(Role.USER));

                //ToDo передать объект user для сохранения данных в БД
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        });

    }
}
