package com.example.canteen_app_merchant;





import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity  {
    static final int RC_SIGN_IN = 9001;
    static final String LAST_FRAGMENT = "userFragment";

    static GoogleSignInClient mGoogleSignInClient;
    public static String uid;
    public static String Bhawan;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    public void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //authcode
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        //authcode


    }

    @Override
    protected void onStart() {
        getSupportActionBar().hide();
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                System.out.println("BOOOOOOOOM");
                if(user!=null)
                {
                    // Sign in logic here.
                    uid = "PLACEHOLDER";
                    for (UserInfo profile : user.getProviderData())
                    {
                        uid = profile.getUid();
                        System.out.println("UID IS :" + uid);
                    }
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                }
                else
                {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                }


            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateUI(FirebaseUser account)
    {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(account==null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.your_placeholder, new LoginFragment());
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }
        else
        {
            //try {
                db.collection("Canteen-merchants").document(MainActivity.uid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.get("Bhawan") != null) {
                                        switch ((String) document.get("Bhawan")) {
                                            case "RJB":
                                                MainActivity.Bhawan = "RJB";
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft = getSupportFragmentManager().beginTransaction();
                                                // Replace the contents of the container with the new fragment
                                                ft.replace(R.id.your_placeholder, new BhawanPage());
                                                // or ft.add(R.id.your_placeholder, new FooFragment());
                                                // Complete the changes added above
                                                ft.commit();
                                                break;
                                            default:
                                                System.out.println("DEFAULT BLOCK");
                                                ft = getSupportFragmentManager().beginTransaction();
                                                // Replace the contents of the container with the new fragment
                                                ft.replace(R.id.your_placeholder, new homePageFrag());
                                                // or ft.add(R.id.your_placeholder, new FooFragment());
                                                // Complete the changes added above
                                                ft.commit();

                                        }
                                    } else {
                                        System.out.println("ELSE BLOCK");
                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        // Replace the contents of the container with the new fragment
                                        ft.replace(R.id.your_placeholder, new homePageFrag());
                                        // or ft.add(R.id.your_placeholder, new FooFragment());
                                        // Complete the changes added above
                                        ft.commit();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            /*}catch (Exception e)
            {
                System.out.println("EXCEPTION BLOCK");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.your_placeholder, new homePageFrag());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
            }*/

        }
    }
}