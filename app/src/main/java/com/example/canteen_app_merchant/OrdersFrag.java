package com.example.canteen_app_merchant;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class OrdersFrag extends Fragment {

    private OrdersViewModel mViewModel;

    public static OrdersFrag newInstance() {
        return new OrdersFrag();
    }


    public static Fragment frag;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.orders_fragment, container, false);
        frag = this;
        final ProgressBar pb = view.findViewById(R.id.pBar);
        pb.setVisibility(View.VISIBLE);
        OrdersViewModel mViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        final LinearLayout ll = view.findViewById(R.id.orderLinear);
        // Create the observer which updates the UI.
        final Observer<Map<String, Object>> orderObserver = new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable final Map<String, Object> data) {

                pb.setVisibility(View.GONE);
                for (final QueryDocumentSnapshot document : (QuerySnapshot)data.get("document")) {

                    final MaterialCardView materialCardView = new MaterialCardView(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                    params.setMargins(10, 20, 10, 20);
                    materialCardView.setLayoutParams(params);
                    materialCardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2, -2);
                    param.setMargins(10, 10, 10, 10);
                    LinearLayout cardlinear = new LinearLayout(getActivity());
                    cardlinear.setLayoutParams(param);
                    cardlinear.setOrientation(LinearLayout.VERTICAL);

                    TextView userid = new TextView(getActivity());
                    userid.setText("UID : \n" + document.get("uid") );
                    userid.setTextColor(Color.parseColor("#000000"));
                    userid.setLayoutParams(param);
                    cardlinear.addView(userid);

                    TextView date = new TextView(getActivity());
                    date.setText("Date and Time : \n" + document.get("Date") );
                    date.setTextColor(Color.parseColor("#000000"));
                    date.setLayoutParams(param);
                    cardlinear.addView(date);

                    GridLayout gl = new GridLayout(getActivity());
                    gl.setColumnCount(3);
                    gl.setLayoutParams(param);
                    gl.setBackgroundColor(Color.parseColor("#e3e2de"));



                    Map<String, Object> Items =  new HashMap<>();
                    Items.put("Item", document.get("Item"));

                    Map<String, Object> itemlist = (Map)(Items.get("Item"));
                    int i = 1;
                    int total = 0;
                    for (Map.Entry<String, Object> entry : itemlist.entrySet())
                    {

                        System.out.println("Value is " + entry.getValue());
                        Map<String, Map<String, Object>> orderItem = new HashMap<>();
                        orderItem.put(entry.getKey(), (Map)entry.getValue());
                        String Name = (orderItem.get(entry.getKey()).get("Name")).toString();
                        String P = (orderItem.get(entry.getKey()).get("Price")).toString();
                        int Price = Integer.parseInt(P);
                        String Q = (orderItem.get(entry.getKey()).get("Quantity")).toString();
                        int Quantity = Integer.parseInt(Q);

                        if(Quantity!=0) {
                            //column1
                            TextView tv = new TextView(getActivity().getApplicationContext());
                            tv.setWidth(50);
                            tv.setText(Integer.toString(i++));
                            tv.setTextColor(Color.parseColor("#000000"));
                            tv.setBackgroundColor(Color.parseColor("#e3e2de"));
                            tv.setId(1 + 10 * i);
                            gl.addView(tv);

                            //column2
                            TextView tv2 = new TextView(getActivity().getApplicationContext());
                            tv2.setWidth(200);
                            tv2.setText(Name);
                            tv2.setTextColor(Color.parseColor("#000000"));
                            tv2.setBackgroundColor(Color.parseColor("#e3e2de"));
                            tv2.setId(2 + 10 * i);
                            gl.addView(tv2);

                            //column3
                            TextView tv3 = new TextView(getActivity().getApplicationContext());
                            tv3.setWidth(200);
                            tv3.setText("Rs. " + Price + " X" + Quantity);
                            tv3.setTextColor(Color.parseColor("#000000"));
                            tv3.setBackgroundColor(Color.parseColor("#e3e2de"));
                            tv3.setId(3 + 10 * i);
                            gl.addView(tv3);
                            total += Price * Quantity;
                        }

                    }
                    cardlinear.addView(gl);

                    TextView tot = new TextView(getActivity());
                    tot.setText("Total = " + total);
                    tot.setTextColor(Color.parseColor("#000000"));
                    tot.setBackgroundColor(Color.parseColor("#e3e2de"));
                    tot.setLayoutParams(param);
                    cardlinear.addView(tot);

                    Button deliver = new Button(getActivity());
                    deliver.setText("Order Completed");
                    deliver.setBackgroundColor(Color.parseColor("#63EC27"));
                    deliver.setClickable(true);


                    //deliverlistener
                    deliver.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection(MainActivity.Bhawan + "-orders")
                                    .whereEqualTo("uid", document.get("uid"))
                                    .whereEqualTo("Date", document.get("Date"))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    document.getReference().delete();
                                                    materialCardView.setVisibility(View.GONE);

                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });

                        }
                    });
                    cardlinear.addView(deliver);


                    materialCardView.addView(cardlinear);
                    ll.addView(materialCardView);
                }


            }};

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getOrders().observe(this, orderObserver);




        return view;
    }



}
