package com.example.listing;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listing.AssignDriver.AssignDialogFragment;
import com.example.listing.DataViewModel.PlansDataModel;
import com.example.listing.DataViewModel.PlansDataModelFactory;
import com.example.listing.Kotlin.Login;
import com.example.listing.Material.Loader.LoaderFragment;
import com.example.listing.Material.Material;
import com.example.listing.Material.MaterialAdapter;
import com.example.listing.Material.Dispatcher.DispatcherFragment;
import com.example.listing.Plan.PlanFragment;
import com.example.listing.Plan.PlanAdapter;
import com.example.listing.Plan.Plan;
import com.example.listing.models.Material2;
import com.example.listing.models.Plan2;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity {
}




