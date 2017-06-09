package com.breakstudio.casadeoracion;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    TextView tvNombreSettings;
    ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvNombreSettings = (TextView) findViewById(R.id.tvNombreSettings);
        profilePictureView = (ProfilePictureView) findViewById(R.id.ivPhotoUser);

        FirebaseUser Firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (Firebaseuser != null) {

            String name = Firebaseuser.getDisplayName();
            String email = Firebaseuser.getEmail();
            tvNombreSettings.setText(name);

            /*Profile profile = Profile.getCurrentProfile();
            //profile.getProfilePictureUri(200,200);
            String id = profile.getId();
            profilePictureView.setProfileId(id);*/
        }
    }

    public void cerrarSettings(View view){
        SettingsActivity.super.onBackPressed();
    }
}
