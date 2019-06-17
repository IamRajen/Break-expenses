package com.expenses.breakexpenses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {


    TextInputEditText phone_number,password;
    TextView btn_signup;
    Button login;

    public static SharedPreferences sharedPreferences;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_login_page);
        phone_number= findViewById(R.id.loginId);
        password = findViewById(R.id.password);
        btn_signup= findViewById(R.id.btn_signup);
        login = findViewById(R.id.login);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,SigninPage.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginStatus();
            }
        });



    }

    private void checkLoginStatus() {

        if(phone_number.getText().toString().isEmpty())
        {
            phone_number.setError("Phone number is Required..!!");
            phone_number.requestFocus();

        }
        if (password.getText().toString().isEmpty())
        {
            password.setError("Password is Required..!!");
            password.requestFocus();
        }
        else {

            final String phn = phone_number.getText().toString().trim();
            final String pass = password.getText().toString().trim();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Users users = dataSnapshot1.getValue(Users.class);
                        if (users.getPhoneNumber().equals(phn) && users.getPassword().equals(pass))
                        {
                            //Homepage.orgainser_name = users.getUsername();
                            sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_phone", phn);
                            editor.putString("user_password", pass);
                            editor.putString("user_name",users.getUsername());
                            Toast.makeText(LoginPage.this, "" + users.getUsername(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginPage.this, ""+sharedPreferences.getString("user_phone",""), Toast.LENGTH_SHORT).show();
                            editor.commit();
                            finish();
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                        if (users.getPhoneNumber().equals(phone_number.getText().toString().trim()) && !users.getPassword().equals(password.getText().toString().trim())) {
                            password.setError("Invalid Password");
                            password.setText("");
                            password.requestFocus();
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
}
