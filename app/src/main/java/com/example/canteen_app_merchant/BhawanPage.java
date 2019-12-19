package com.example.canteen_app_merchant;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class BhawanPage extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_bhawan_page, container, false);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.BhawanPageFrame, new OrdersFrag());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        final TextView Orders = view.findViewById(R.id.orderTV);
        final TextView Add = view.findViewById(R.id.AddTV);
        final TextView Edit = view.findViewById(R.id.MenuTV);
        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.BhawanPageFrame, new OrdersFrag());
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();

                Orders.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Orders.setClickable(false);
                Orders.setTextColor(Color.parseColor("#ffffff"));
                Add.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Add.setClickable(true);

                Edit.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Edit.setClickable(true);
                Add.setTextColor(Color.parseColor("#000000"));
                Edit.setTextColor(Color.parseColor("#000000"));


            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.BhawanPageFrame, new OrdersFrag());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
                Add.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Add.setClickable(false);
                Add.setTextColor(Color.parseColor("#ffffff"));
                Orders.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Orders.setClickable(true);
                Edit.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Edit.setClickable(true);
                Orders.setTextColor(Color.parseColor("#000000"));
                Edit.setTextColor(Color.parseColor("#000000"));


            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.BhawanPageFrame, new OrdersFrag());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
                Edit.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Edit.setClickable(false);
                Edit.setTextColor(Color.parseColor("#ffffff"));
                Add.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Add.setClickable(true);
                Orders.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                Orders.setClickable(true);
                Add.setTextColor(Color.parseColor("#000000"));
                Orders.setTextColor(Color.parseColor("#000000"));

            }
        });



        return view;
    }



}
