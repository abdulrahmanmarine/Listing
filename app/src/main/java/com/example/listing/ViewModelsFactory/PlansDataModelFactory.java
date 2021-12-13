package com.example.listing.ViewModelsFactory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.listing.DataViewModel.PlansDataModel;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


public class PlansDataModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;



    public PlansDataModelFactory(Application application) {
        mApplication = application;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new PlansDataModel(mApplication);

    }
}