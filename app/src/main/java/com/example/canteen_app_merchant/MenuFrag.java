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
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.canteen_app_merchant.MainActivity.Bhawan;

public class MenuFrag extends Fragment {

    private MenuViewModel mViewModel;

    public static MenuFrag newInstance() {
        return new MenuFrag();
    }

    public static Fragment frag;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment, container, false);
        frag = this;
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);



        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final GridLayout gl = view.findViewById(R.id.gridmenu);
        final ProgressBar pb = view.findViewById(R.id.pBar);
        pb.setVisibility(View.VISIBLE);
        frag = this;





        // Create the observer which updates the UI.
        final Observer<Map<String, Object>> nameObserver = new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable final Map<String, Object> data) {

                int i = 1;
                pb.setVisibility(View.GONE);
                System.out.println(data);


                TextView tvsa = view.findViewById(R.id.textviewsa);
                TextView tvre = view.findViewById(R.id.textviewre);
                TextView tvga = view.findViewById(R.id.textviewga);
                TextView tvma = view.findViewById(R.id.textviewma);

                if(gl.getChildCount() > 0)
                    gl.removeAllViews();

                gl.addView(tvsa);
                gl.addView(tvre);
                gl.addView(tvga);
                gl.addView(tvma);





                for (Map.Entry<String,Object> entry : data.entrySet())
                {

                    try {
                        final String name = (String)((QueryDocumentSnapshot)(entry.getValue())).get("Name");


                        final int price =  ((Long)((QueryDocumentSnapshot)(entry.getValue())).get("Price")).intValue();



                        final boolean aty = (boolean)((QueryDocumentSnapshot)(entry.getValue())).get("Availablity");



                        //adding to frag
                        LinearLayout.LayoutParams paramstv = new LinearLayout.LayoutParams(-2, -2);
                        paramstv.setMargins(0, 0, 20, 0);
                        //column1
                        TextView tv = new TextView(getActivity().getApplicationContext());

                        tv.setText(Integer.toString(i++));
                        tv.setTextColor(Color.parseColor("#000000"));
                        //tv.setBackgroundColor(Color.parseColor("#ffffff"));
                        tv.setId(1 + 10*i);
                        tv.setLayoutParams(paramstv);
                        gl.addView(tv);


                        //column2
                        TextView tv2 = new TextView(getActivity().getApplicationContext());

                        tv2.setText(name);
                        if(aty)
                        {
                            tv2.setTextColor(Color.parseColor("#63EC27"));
                        }
                        else
                        {
                            tv2.setTextColor(Color.parseColor("#F40E3F"));
                        }

                        //tv2.setBackgroundColor(Color.parseColor("#ffffff"));
                        tv2.setId(2 + 10*i);
                        tv2.setLayoutParams(paramstv);
                        tv2.setMaxWidth(150);
                        tv2.setSingleLine(false);
                        gl.addView(tv2);

                        //column3
                        TextView tv3 = new TextView(getActivity().getApplicationContext());

                        tv3.setText("Rs. " + price);
                        tv3.setTextColor(Color.parseColor("#000000"));
                        //tv3.setBackgroundColor(Color.parseColor("#ffffff"));
                        tv3.setId(3 + 10*i);
                        tv3.setLayoutParams(paramstv);
                        gl.addView(tv3);

                        //column4
                        LinearLayout ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setPadding(0,0,2,0);

                        MaterialButton pb = new MaterialButton(getActivity(), null, R.attr.borderlessButtonStyle);


                            pb.setBackgroundColor(Color.parseColor("#D81B60"));

                        pb.setText("Edit");

                        pb.setTextColor(Color.parseColor("#000000"));



                        System.out.println("View grp layout is " + ViewGroup.LayoutParams.WRAP_CONTENT);

                        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 120);
                        //params.setMargins(0, 0, 10, 10);
                        pb.setMinWidth(80);
                        pb.setMinimumWidth(80);

                        pb.setLayoutParams(paramstv);




                        MaterialButton mb = new MaterialButton(getActivity(), null, R.attr.borderlessButtonStyle);
                        //Button mb = new Button(getActivity().getApplicationContext());


                            mb.setBackgroundColor(Color.parseColor("#D81B60"));


                        mb.setText("Delete");
                        mb.setTextColor(Color.parseColor("#000000"));
                        mb.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                        int t = ViewGroup.LayoutParams.WRAP_CONTENT;
                        mb.setMinWidth(80);
                        mb.setMinimumWidth(80);

                        mb.setLayoutParams(paramstv);



                        ll.addView(pb);
                        ll.addView(mb);
                        gl.addView(ll);







                        //ORDERING

                            pb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                MainActivity.Name = name;
                                MainActivity.Price = "" + price;
                                MainActivity.Availablity = aty;
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    // Replace the contents of the container with the new fragment
                                    ft.replace(R.id.BhawanPageFrame, new AddFrag());
                                    // or ft.add(R.id.your_placeholder, new FooFragment());
                                    // Complete the changes added above
                                    ft.commit();
                                    BhawanPage.edit_add();


                                }
                            });


                            mb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    db.collection(Bhawan + "-items")
                                            .whereEqualTo("Name", name)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    document.getReference().delete();
                                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                    // Replace the contents of the container with the new fragment
                                                    ft.replace(R.id.BhawanPageFrame, new MenuFrag());
                                                    // or ft.add(R.id.your_placeholder, new FooFragment());
                                                    // Complete the changes added above
                                                    ft.commit();

                                                }


                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });


                                }
                            });









                    }catch(Exception e) {
                    }
                }







            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getCurrentMenu().observe(this, nameObserver);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        // TODO: Use the ViewModel
    }

}
