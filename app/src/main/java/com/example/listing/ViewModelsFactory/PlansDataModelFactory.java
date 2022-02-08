package com.example.listing.ViewModelsFactory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.listing.DataViewModel.PlansDataModel;


public class PlansDataModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String id;

    public PlansDataModelFactory(Application application, String deviceid) {
        mApplication = application;
        id = deviceid;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new PlansDataModel(mApplication, id);

    }
}