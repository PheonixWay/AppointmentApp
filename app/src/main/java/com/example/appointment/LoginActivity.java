package com.example.appointment;

import static com.example.appointment.R.color.red_700;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    MaterialButton loginbtn;
    TextInputEditText gmail,pass;
    TextView createAccount,loginForBusiness,forgotPass;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=findViewById(R.id.login_btn);
        gmail=findViewById(R.id.gmail_id);
        pass=findViewById(R.id.pass_id);
        createAccount=findViewById(R.id.createAc);
        loginForBusiness=findViewById(R.id.Tvlogin_id);
        forgotPass=findViewById(R.id.forgot_pass);
        mAuth=FirebaseAuth.getInstance();

        //forgot pass alert dialog box and implementation
        LayoutInflater li = LayoutInflater.from(this);
        View forgotPassPrompt = li.inflate(R.layout.forgot_pass_alert, null);
        EditText email = (EditText) forgotPassPrompt.findViewById(R.id.forgot_gmail_id);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(forgotPassPrompt);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if (email.getText().toString().isEmpty()){
                                    Toast.makeText(LoginActivity.this, "Pls  enter your gmail", Toast.LENGTH_SHORT).show();
                                }else {
                                    mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                dialog.cancel();
                                                Toast.makeText(LoginActivity.this, "Successfully send forgot link to your email pls check your spam section in gmail", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                email.setText("");
                                            }


                                        }
                                    });
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(red_700));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(red_700));
            }
        });


        loginForBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginUsingPhoneNumber.class);
                startActivity(intent);
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gmail.getText().toString().isEmpty()&&pass.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Your Gmail and password", Toast.LENGTH_SHORT).show();
                }else {
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

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ModeSelect.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.show();

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