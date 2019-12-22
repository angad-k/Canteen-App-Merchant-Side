package com.example.canteen_app_merchant;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class AddFrag extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add, container, false);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Button avail = view.findViewById(R.id.avail);
        final Button unavail = view.findViewById(R.id.unavail);
        final EditText name = view.findViewById(R.id.name);
        final EditText price = view.findViewById(R.id.price);
        final btnpresscheck obj = new btnpresscheck();

        if(!(MainActivity.Name.isEmpty()|MainActivity.Price.isEmpty()))
        {
            name.setText(MainActivity.Name);
            price.setText(MainActivity.Price);
            if(MainActivity.Availablity)
            {
                obj.pressed = true;
                obj.aty = true;
                avail.setBackgroundColor(Color.parseColor("#63EC27"));
                unavail.setBackgroundColor(Color.parseColor("#D81B60"));
                avail.setTextColor(Color.parseColor("#ffffff"));
                unavail.setTextColor(Color.parseColor("#000000"));
            }
            else
            {
                obj.pressed = true;
                obj.aty = false;
                unavail.setBackgroundColor(Color.parseColor("#F40E3F"));
                avail.setBackgroundColor(Color.parseColor("#D81B60"));
                unavail.setTextColor(Color.parseColor("#ffffff"));
                avail.setTextColor(Color.parseColor("#000000"));
            }
            MainActivity.Name = "";
            MainActivity.Price = "";
        }



        avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.pressed = true;
                obj.aty = true;
                avail.setBackgroundColor(Color.parseColor("#63EC27"));
                unavail.setBackgroundColor(Color.parseColor("#D81B60"));
                avail.setTextColor(Color.parseColor("#ffffff"));
                unavail.setTextColor(Color.parseColor("#000000"));

            }
        });
        unavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.pressed = true;
                obj.aty = false;
                unavail.setBackgroundColor(Color.parseColor("#F40E3F"));
                avail.setBackgroundColor(Color.parseColor("#D81B60"));
                unavail.setTextColor(Color.parseColor("#ffffff"));
                avail.setTextColor(Color.parseColor("#000000"));

            }
        });
        Button updater = view.findViewById(R.id.update);
        updater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty() || price.getText().toString().isEmpty() || (!obj.pressed) )
                {
                    return;
                }
                final Map<String, Object> item = new HashMap<>();
                item.put("Name", name.getText().toString());
                item.put("Price", Long.parseLong(price.getText().toString()));
                item.put("Availablity", obj.aty);

                db.collection(MainActivity.Bhawan + "-items")
                        .whereEqualTo("Name",name.getText().toString() )
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            db.collection(MainActivity.Bhawan + "-items")
                                    .add(item);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.BhawanPageFrame, new AddFrag());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
                Toast.makeText(getContext(), "Menu successfully updated", Toast.LENGTH_SHORT);






            }
        });





        return view;
    }





}
class btnpresscheck
{
    boolean pressed = false;
    boolean aty;
}
