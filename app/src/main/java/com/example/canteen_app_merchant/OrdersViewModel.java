package com.example.canteen_app_merchant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.canteen_app_merchant.model.DatabaseHandlerForOrders;

import java.util.Map;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<Map<String, Object>> orders;
    public MutableLiveData<Map<String, Object>> getOrders() {
        if (orders == null) {
            orders = new MutableLiveData<Map<String, Object>>();
        }
        return orders;
    }

    public OrdersViewModel()
    {
        DatabaseHandlerForOrders.initialize();
    }



}
