package com.expenses.breakexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SigninPage extends AppCompatActivity{



    EditText editTextPhone;
    Button verify;

    String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin_page);

        editTextPhone = findViewById(R.id.editTextPhone);
        verify = findViewById(R.id.verify);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = editTextPhone.getText().toString().trim();

                if(phone_number.isEmpty())
                {
                    editTextPhone.setError("Phone Number Required");
                    editTextPhone.requestFocus();
                    return;
                }

                Intent intent = new Intent(SigninPage.this,VerifyPhoneNumber.class);
                intent.putExtra("phone_number",phone_number);
                startActivity(intent);



            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent = new Intent(this,UserDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }



}
