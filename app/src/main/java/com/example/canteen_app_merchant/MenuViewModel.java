package com.example.canteen_app_merchant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.canteen_app_merchant.model.DatabaseHandlerForEdit;

import java.util.Map;

public class MenuViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Map<String, Object>> Bhawanitems;

    public MutableLiveData<Map<String, Object>> getCurrentMenu() {
        if (Bhawanitems == null) {
            Bhawanitems = new MutableLiveData<Map<String, Object>>();
        }
        return Bhawanitems;
    }



    public MenuViewModel()
    {
        DatabaseHandlerForEdit.initialize();
    }
}
