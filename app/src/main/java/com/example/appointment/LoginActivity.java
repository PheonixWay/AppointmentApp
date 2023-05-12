package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btn;
    TextInputEditText gmail,pass;
    TextView createAccount,goooo,loginForBusiness;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.login_btn);
        gmail=findViewById(R.id.gmail_id);
        pass=findViewById(R.id.pass_id);
        createAccount=findViewById(R.id.createAc);
        loginForBusiness=findViewById(R.id.Tvlogin_id);
        mAuth=FirebaseAuth.getInstance();


        loginForBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginUsingPhoneNumber.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gmail_text=gmail.getText().toString();
                String pass_text=pass.getText().toString();

                mAuth.signInWithEmailAndPassword(gmail_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){


                            Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,CustomerDashboardActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Pls enter valid email and Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ModeSelect.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=mAuth.getCurrentUser();
        if (firebaseUser!=null){
            String email=firebaseUser.getEmail();
            String phoneNumber=firebaseUser.getPhoneNumber();
            if (phoneNumber.contains("+91")){
                Intent intent1=new Intent(getApplicationContext(),BussinesDashboardActivity.class);
                startActivity(intent1);
                finishAffinity();
            } else if (email.contains("@gmail.com")) {
                Intent intent2=new Intent(getApplicationContext(),CustomerDashboardActivity.class);
                startActivity(intent2);
                finishAffinity();

            }
        }
    }
}