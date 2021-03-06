package com.reactive.fyp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.ActivitySignInActivityeBinding;
import com.reactive.fyp.model.SignInModel;


public class SignInActivitye extends AppCompatActivity {

    final String TAG = SignInActivitye.class.getSimpleName();
    ActivitySignInActivityeBinding binding;
    SignInModel model;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference(Constants.USERS);
    private Uri filePath;
    private String downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_activitye);
        model = new SignInModel();
        binding.setData(model);
        binding.setVisibility(true);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_color));

        mAuth = FirebaseAuth.getInstance();


        binding.done.setOnClickListener(v -> {
            if (isValid()){
                binding.setVisibility(false);
                signIn();
            }
        });

        binding.signUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivitye.this,RegisterActivity.class);
            startActivity(intent);
        });

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    boolean isValid(){
        model.setDisplayError(true);
        if (!model.getEmailError().isEmpty()){
            return false;
        }
        if (!model.getPasswordError().isEmpty()){
            return false;
        }
        model.setDisplayError(false);
        return true;
    }

    void signIn(){
        mAuth.signInWithEmailAndPassword(binding.email.getText().toString(),
                binding.password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        openScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                        binding.setVisibility(true);
                        Toast.makeText(SignInActivitye.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void openScreen(){
        Helper.setLogin(this,true);
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


}