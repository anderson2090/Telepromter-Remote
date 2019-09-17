package com.usamabennadeemspeechix.usama.speechixremote.remote;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usamabennadeemspeechix.usama.speechixremote.MainActivity;
import com.usamabennadeemspeechix.usama.speechixremote.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.usamabennadeemspeechix.usama.speechixremote.Utils.loggedInStatus;
import static com.usamabennadeemspeechix.usama.speechixremote.constants.Constants.IS_PLAYING_KEY;
import static com.usamabennadeemspeechix.usama.speechixremote.constants.Constants.READ_ACTIVITY_REMOTE_STATE_KEY;

public class RemotePanelActivity extends AppCompatActivity {

    Button playButton;
    Button upButton;
    Button downButton;
    boolean isPlaying = false;
    Button increaseFontButton;
    Button decreaseFontButton;
    Button increaseSpeedButton;
    Button decreaseSpeedButton;
    MaterialRippleLayout upButtonLayout;
    MaterialRippleLayout downButtonLayout;
    MaterialRippleLayout increaseFontLayout;
    MaterialRippleLayout decreaseFontLayout;
    MaterialRippleLayout increaseSpeedLayout;
    MaterialRippleLayout decreaseSpeedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        remoteState("pause");
        setTitle(R.string.app_name);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        increaseSpeedButton = findViewById(R.id.increase_speed_button);
        decreaseSpeedButton = findViewById(R.id.decrease_speed_button);

        upButtonLayout = findViewById(R.id.up_ripple);
        downButtonLayout = findViewById(R.id.down_ripple);
        increaseFontLayout = findViewById(R.id.increase_font_ripple);
        decreaseFontLayout = findViewById(R.id.decrease_font_ripple);

        increaseSpeedLayout = findViewById(R.id.increase_speed_ripple);
        decreaseSpeedLayout = findViewById(R.id.decrease_speed_ripple);

        upButton = findViewById(R.id.up_button);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("Up");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("pause");
            }
        });

        downButton = findViewById(R.id.down_button);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("Down");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("pause");
            }
        });
        increaseFontButton = findViewById(R.id.increase_font_button);
        increaseFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("increase_font");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("pause");

            }
        });

        decreaseFontButton = findViewById(R.id.decrease_font_button);
        decreaseFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("decrease_font");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("pause");
            }
        });

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playButton.getText().toString().equalsIgnoreCase("Play")) {
                    pause();

                } else if (playButton.getText().toString().equalsIgnoreCase("Pause")) {
                    play();
                }
            }
        });

        increaseSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("increase_speed");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("play");
            }
        });

        decreaseSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteState("decrease_speed");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                    }
                }, 1000);
                remoteState("play");
            }
        });

    }

    private void play() {
        playButton.setText("Play");
        playButton.setBackground(getResources().getDrawable(R.drawable.ic_play_red));
        remoteState("pause");
        isPlaying = true;

        decreaseFontLayout.setVisibility(View.VISIBLE);
        increaseFontLayout.setVisibility(View.VISIBLE);
        upButtonLayout.setVisibility(View.VISIBLE);
        downButtonLayout.setVisibility(View.VISIBLE);
        decreaseSpeedLayout.setVisibility(View.GONE);
        increaseSpeedLayout.setVisibility(View.GONE);
    }

    private void pause() {
        playButton.setText("Pause");
        playButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_red));
        remoteState("play");
        isPlaying = false;
        decreaseFontLayout.setVisibility(View.GONE);
        increaseFontLayout.setVisibility(View.GONE);
        upButtonLayout.setVisibility(View.GONE);
        downButtonLayout.setVisibility(View.GONE);
        decreaseSpeedLayout.setVisibility(View.VISIBLE);
        increaseSpeedLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.panel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.panel_signout_menu_item:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    loggedInStatus("Logged out", getApplicationContext());
                    remoteState("null");

                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.help_panel_menu:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/pUMs13nQXPY")));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void remoteState(String state) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("status").child(READ_ACTIVITY_REMOTE_STATE_KEY).setValue(state).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_PLAYING_KEY, isPlaying);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            isPlaying = savedInstanceState.getBoolean(IS_PLAYING_KEY);
            if (isPlaying) {
                play();
            } else {
                pause();
            }
        }
    }


}
