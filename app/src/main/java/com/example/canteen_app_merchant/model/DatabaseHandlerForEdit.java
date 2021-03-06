package com.example.canteen_app_merchant.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.canteen_app_merchant.MenuFrag;
import com.example.canteen_app_merchant.MenuViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.canteen_app_merchant.MainActivity.Bhawan;

public class DatabaseHandlerForEdit {
    public static void initialize()
    {
        final Map<String, Object> Bhawanitems = new HashMap<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Bhawan + "-items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bhawanitems.put((String)document.get("Name"), document);

                            }
                            try{
                                MenuViewModel mViewModel = ViewModelProviders.of(MenuFrag.frag).get(MenuViewModel.class);
                                mViewModel.getCurrentMenu().setValue(Bhawanitems);
                            }catch (Exception e)
                            {}

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection(Bhawan+"-items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot document : value) {
                            Bhawanitems.put((String)document.get("Name"), document);

                        }
                        try{
                            MenuViewModel mViewModel = ViewModelProviders.of(MenuFrag.frag).get(MenuViewModel.class);
                            mViewModel.getCurrentMenu().setValue(Bhawanitems);
                        }catch (Exception e2)
                        {System.out.println(e2);}
                    }
                });


        return;
    }
}
