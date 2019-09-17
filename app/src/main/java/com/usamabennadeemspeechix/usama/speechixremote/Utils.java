package com.usamabennadeemspeechix.usama.speechixremote;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

import static com.usamabennadeemspeechix.usama.speechixremote.constants.Constants.REMOTE_APP_STATE_KEY;

public class Utils {
    public static void loggedInStatus(String status, final Context context) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                //Todo write code to handle database exceptions
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status")
                        .child(REMOTE_APP_STATE_KEY).setValue(status).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        }
    }
}
