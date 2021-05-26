package com.example.testdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btLogin,btRegister;
    private EditText email,password;
    protected FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        initView();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if(e.isEmpty()){
                    email.setError("email is not blank");
                    return;
                }
                if(p.isEmpty()){
                    password.setError("password is not blank");
                    return;
                }
                if(p.length()<6){
                    password.setError("password must be >= 6 characters");
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(e,p).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,
                                        MainActivity.class);

                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,
                                "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        btLogin=findViewById(R.id.btLogin);
        btRegister=findViewById(R.id.btRegister);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPass);
    }
}
