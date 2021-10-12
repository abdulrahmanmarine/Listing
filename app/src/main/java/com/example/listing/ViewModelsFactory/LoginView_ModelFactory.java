package com.example.listing.ViewModelsFactory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.listing.DataViewModel.LoginView_Model;

import org.jetbrains.annotations.NotNull;

public class LoginView_ModelFactory  implements ViewModelProvider.Factory  {

    private final Application mApplication;


    public LoginView_ModelFactory(Application application) {
        mApplication = application;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {

            return (T) new LoginView_Model(mApplication);

    }
}
