package com.example.canteen_app_merchant.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.canteen_app_merchant.OrdersFrag;
import com.example.canteen_app_merchant.OrdersViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.canteen_app_merchant.MainActivity.Bhawan;

public class DatabaseHandlerForOrders {

    public static void initialize()
    {
        final Map<String, Object> orderData = new HashMap<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Bhawan + "-orders").orderBy("Date", Query.Direction.DESCENDING)
                .whereEqualTo("OrderState", "Placed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            orderData.put("document", task.getResult());
                            try
                            {
                                OrdersViewModel mViewModel = ViewModelProviders.of(OrdersFrag.frag).get(OrdersViewModel.class);
                                mViewModel.getOrders().setValue(orderData);
                            }catch (Exception e2)
                            {

                            }
                            System.out.println(orderData);
                        }
                        else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

        db.collection(Bhawan+"-items")
                .whereEqualTo("OrderState", "Placed")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        orderData.put("document", value);

                        try
                        {
                            OrdersViewModel mViewModel = ViewModelProviders.of(OrdersFrag.frag).get(OrdersViewModel.class);
                            mViewModel.getOrders().setValue(orderData);
                        }catch (Exception e2)
                        {

                        }

                    }
                });


    }
}
