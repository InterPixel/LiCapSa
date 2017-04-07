package com.InterPixel.LiCapSa;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;


public class MainMenuActivity extends Activity {
    @Override
    protected void onActivityResult (int requestCode , int responseCode , Intent intent){
        switch(requestCode){
            case PlayGames.RC_SIGN_IN:
                if (responseCode == Activity.RESULT_OK) {
                    PlayGames.fap().SignIn();
                }
                    break;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        PlayGames.fap().initialize(this);


    }

    public void startGameActivity(View view){
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void signIn(View view) {
        PlayGames.fap().SignIn();
    }

    public void signOut(View view) {
        PlayGames.fap().SignOut();
    }

    public void invitePlayers(View view){
        PlayGames.fap().invitePlayers();
    }
}
