package com.example.firebaseuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText edtUsername,edtEmail,edtPass,edtConfirmPW;
    Button btnRegister;
    ImageView imageRegister;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    static DatabaseReference mData;
    private void init(){
        imageRegister = findViewById(R.id.imageRegister);
        edtUsername = findViewById(R.id.userName);
        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.password);
        edtConfirmPW = findViewById(R.id.confirmPW);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        mData = FirebaseDatabase.getInstance().getReference("User");
    //    auth = FirebaseAuth.getInstance();
        final String userName = edtUsername.getText().toString();
        final String email = edtEmail.getText().toString();
        final String password = edtPass.getText().toString();
        final String confirm = edtConfirmPW.getText().toString();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                || email.isEmpty() || password.isEmpty() || confirm.isEmpty()
                if (edtUsername.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()
                        || edtPass.getText().toString().isEmpty() || edtConfirmPW.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().length() < 6){
                    Toast.makeText(RegisterActivity.this,"Password must be least 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerNewUser(mData,edtUsername.getText().toString(),edtEmail.getText().toString(),edtPass.getText().toString());
                }

            }
        });
    }
    private void registerNewUser(final DatabaseReference mData, final String userNameT, String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    mData.child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userId);
                    hashMap.put("userName",userNameT);
                    hashMap.put("imageURL","default");
                    mData.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"Don't register plz check info insert",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerByFB(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(!bundle.isEmpty()){
            edtEmail.setText(bundle.getString(LoginActivity.KEY_EMAIL));
            edtUsername.setText(bundle.getString(LoginActivity.KEY_NAME));
            String ID = LoginActivity.KEY_ID;
            String photo_url = "https://graph.facebook.com/"+ID+"/picture?type=normal";
            Picasso.get().load(photo_url).into(imageRegister);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"RegisterActivity Start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"RegisterActivity Resume",Toast.LENGTH_SHORT).show();
    }
}