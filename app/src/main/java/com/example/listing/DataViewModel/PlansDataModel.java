package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listing.R;
import com.example.listing.Utils.AppExecutors;
import com.example.listing.Utils.Loginsession;
import com.example.listing.Utils.OfflineDatabaseClient;
import com.example.listing.Utils.RestApiClient;
import com.example.listing.Utils.RetrofitInterface;
import com.example.listing.models.AssignmentUnpack;
import com.example.listing.models.Device;
import com.example.listing.models.Deviceunpack;
import com.example.listing.models.Driver;
import com.example.listing.models.DriverUnpack;
import com.example.listing.models.ImageList;
import com.example.listing.models.Material;
import com.example.listing.models.MatrialDispatching;
import com.example.listing.models.Plan;
import com.example.listing.models.PlanUnpack;
import com.example.listing.models.VechAssignLoader;
import com.example.listing.models.VehAssign;
import com.example.listing.models.Vehicle;
import com.example.listing.models.VehicleUnpack;
import com.example.listing.models.imagenode;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlansDataModel extends ViewModel {

    private static RetrofitInterface retrofitInterface;
    private final SharedPreferences preferences;
    private final String Mode;
    private final OfflineDatabaseClient db;
    public MutableLiveData<List<Plan>> Plans = new MutableLiveData<>();
    public MutableLiveData<Plan> plan = new MutableLiveData<>();
    public MutableLiveData<List<com.example.listing.models.Material>> MatrialsList = new MutableLiveData<>();
    public MutableLiveData<Boolean> UserRule = new MutableLiveData<>();
    public MutableLiveData<List<Driver>> MasterdriversList = new MutableLiveData<>();
    public MutableLiveData<List<Vehicle>> MastervehiclesList = new MutableLiveData<>();
    public MutableLiveData<List<imagenode>> MatrialImageList = new MutableLiveData<>();
    public MutableLiveData<List<VehAssign>> VechAssignDispatchList = new MutableLiveData<>();
    public MutableLiveData<List<VechAssignLoader>> VechAssignLoaderList = new MutableLiveData<>();
    public MutableLiveData<Boolean> flag = new MutableLiveData<>(true);
    Application application;


    public PlansDataModel(Application application, String id) {
        super();
        this.application = application;

        retrofitInterface = RestApiClient.getInstance(application).getRetrofitInterface();

        preferences = application.getSharedPreferences(application.getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        Mode = preferences.getString(application.getResources().getString(R.string.UserMode), "");
        db = OfflineDatabaseClient.getInstance(application.getApplicationContext());

    }

    public void getplansLoader(LifecycleOwner owner) throws IOException {
        //OFFLINE DATA RETRIEVAL

        if (Mode.equals("offline")) {


            db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId().toUpperCase()).observe(owner, itemlist -> {

                for (int i = 0; i < itemlist.size(); i++) {
                    Plan plan = itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist -> {
                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i, plan);
                }
                Plans.postValue(itemlist);
            });

        }


        //ONLINE DATA RETRIEVAL
        else {
            retrofitInterface.getPlansLoader("Fetch").enqueue(new Callback<PlanUnpack>() {
                @Override
                public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
                    if (response.isSuccessful()) {
                        List<Plan> temp = response.body().getItems();
                        Plans.postValue(temp);
                        AppExecutors.getInstance().diskIO().execute(() -> {
                            db.planitem().deleteplan(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                            db.Matrial().Delete(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                            for (int i = 0; i < temp.size(); i++) {
                                Plan plan = temp.get(i);
                                plan.setZuphrFpName(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                                String id = String.valueOf(db.planitem().insertplan(plan));
                                for (int j = 0; j < temp.get(i).getPlanToItems().size(); j++) {
                                    Material material = temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    material.setZuphrFpName(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                                    String Mid = String.valueOf(db.Matrial().insertMatrial(material));
                                }
                            }
                        });

                    }

                }

                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {

                }
            });
        }


    }

    public void getplansDispatcher(Application application, LifecycleOwner owner) throws IOException {
        //OFFLINE DATA RETRIEVAL
        if (Mode.equals("offline")) {

            db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId().toUpperCase()).observe(owner, itemlist -> {
                for (int i = 0; i < itemlist.size(); i++) {
                    Plan plan = itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist -> {
                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i, plan);
                }
                Plans.postValue(itemlist);
            });

        } else {

            retrofitInterface.getPlansDispatcher("Fetch").enqueue(new Callback<PlanUnpack>() {
                @Override
                public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {

                    if (response.isSuccessful()) {
                        List<Plan> temp = response.body().getItems();
                        Plans.postValue(temp);
                        AppExecutors.getInstance().diskIO().execute(() -> {
                            db.planitem().deleteplan(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                            db.Matrial().Delete(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                            for (int i = 0; i < temp.size(); i++) {
                                Plan plan = temp.get(i);
                                plan.setZuphrFpName(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                                String id = String.valueOf(db.planitem().insertplan(plan));
                                for (int j = 0; j < temp.get(i).getPlanToItems().size(); j++) {
                                    Material material = temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    material.setZuphrFpName(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                                    String Mid = String.valueOf(db.Matrial().insertMatrial(material));
                                }
                            }
                        });

                    }

                }

                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {

                }
            });


        }
    }

    public void getVechiles(LifecycleOwner owner) {

        Driver driver1 = new Driver("1", "Abdul", "Heavy Vehicle Driving",
                "456324", "Saudi", "91 66778899", "driver.test@gmail.com");
        Driver driver2 = new Driver("2", "Ahmed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver3 = new Driver("3", "Ali", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver4 = new Driver("4", "Murada", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver5 = new Driver("5", "Yousef", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");
        Driver driver6 = new Driver("6", "Mohammed", "Small Vehicle Driving",
                "456324", "Kuwaiti", "91 66778899", "Ahmed.test@gmail.com");


        Vehicle vehicle1 = new Vehicle("1", "Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);
        Vehicle vehicle2 = new Vehicle("2", "Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);
        Vehicle vehicle3 = new Vehicle("3", "Medium", "Two Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);
        Vehicle vehicle4 = new Vehicle("4", "Medium", "Four Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);
        Vehicle vehicle5 = new Vehicle("5", "Medium", "Small Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);
        Vehicle vehicle6 = new Vehicle("6", "Medium", "Huge Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456", null);

        ArrayList<Driver> driversList = new ArrayList<>();
        ArrayList<Vehicle> vehiclesList = new ArrayList<>();

        driversList.add(driver1);
        driversList.add(driver2);
        driversList.add(driver3);
        driversList.add(driver4);
        driversList.add(driver5);
        driversList.add(driver6);
        // MasterdriversList.setValue(driversList);


        vehiclesList.add(vehicle1);
        vehiclesList.add(vehicle2);
        vehiclesList.add(vehicle3);
        vehiclesList.add(vehicle4);
        vehiclesList.add(vehicle5);
        vehiclesList.add(vehicle6);
        //MastervehiclesList.setValue(vehiclesList);


        if (Mode.equals("offline")) {
            db.MatrialVehicles().GetItemAll().observe(owner, itemlist -> {
                MastervehiclesList.postValue(itemlist);
            });

        } else {
            retrofitInterface.GetVehicle().enqueue(new Callback<VehicleUnpack>() {
                @Override
                public void onResponse(Call<VehicleUnpack> call, Response<VehicleUnpack> response) {

                    if (response.errorBody() != null) {


                    }
                    if (response.isSuccessful()) {
                        MastervehiclesList.setValue(response.body().getvehiclelist());

                        AppExecutors.getInstance().diskIO().execute(() -> {

                            db.MatrialVehicles().nukeTable();
                            for (int i = 0; i < response.body().getvehiclelist().size(); i++) {
                                String id = String.valueOf(db.MatrialVehicles().insertV(response.body().getvehiclelist().get(i)));
                            }
                        });

                    }


                }

                @Override
                public void onFailure(Call<VehicleUnpack> call, Throwable t) {


                }
            });

        }

    }

    public void getDevice() {
        retrofitInterface.GetDevice().enqueue(new Callback<Deviceunpack>() {
            @Override
            public void onResponse(Call<Deviceunpack> call, Response<Deviceunpack> response) {

                if (response.errorBody() != null) {


                }
                if (response.isSuccessful()) {

                }


            }

            @Override
            public void onFailure(Call<Deviceunpack> call, Throwable t) {


            }
        });


    }

    public void getdrivers(LifecycleOwner owner) {

        if (Mode.equalsIgnoreCase("offline")) {
            db.MatrialDrivers().GetItemAll().observe(owner, itemlist -> {
                MasterdriversList.postValue(itemlist);

            });

        } else {
            retrofitInterface.GetLoader().enqueue(new Callback<DriverUnpack>() {
                @Override
                public void onResponse(Call<DriverUnpack> call, Response<DriverUnpack> response) {

                    if (response.errorBody() != null) {

                    }
                    if (response.isSuccessful()) {

                        AppExecutors.getInstance().diskIO().execute(() -> {

                            db.MatrialDrivers().nukeTable();
                            for (int i = 0; i < response.body().getriverlist().size(); i++) {
                                String id = String.valueOf(db.MatrialDrivers().insertDriver(response.body().getriverlist().get(i)));

                            }
                        });


                        MasterdriversList.setValue(response.body().getriverlist());
                    }


                }

                @Override
                public void onFailure(Call<DriverUnpack> call, Throwable t) {


                }
            });

        }
    }

    public void getDispatchMtr(String Lpid, LifecycleOwner owner) {

        if (Mode.equals("offline")) {

            db.Assignment().GetItemAll(Lpid).observe(owner, itemlist -> {
                MatchingVechAssgin(itemlist, false);

            });

        } else {

            retrofitInterface.getDispatch("LpHdrSet('" + Lpid + "')?$expand=NavLpToVehAssign").enqueue(new Callback<AssignmentUnpack>() {
                @Override
                public void onResponse(Call<AssignmentUnpack> call, Response<AssignmentUnpack> response) {

                    if (response.errorBody() != null) {

                    }
                    if (response.isSuccessful()) {

                        for (int i = 0; i < response.body().getAssignment().getVehassign().size(); i++) {
                            VehAssign vehAssign = response.body().getAssignment().getVehassign().get(i);
                            vehAssign.AddtoDB(true);
                            response.body().getAssignment().getVehassign().set(i, vehAssign);
                        }

                        MatchingVechAssgin(response.body().getAssignment().getVehassign(), true);


                    }


                }


                @Override
                public void onFailure(Call<AssignmentUnpack> call, Throwable t) {


                }
            });

        }

    }

    private void MatchingVechAssgin(List<VehAssign> vehAssigns, boolean flagonline) {

        List<Material> materials = plan.getValue().getPlanToItems();

        Driver driver = new Driver();

        if (flagonline) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                if (vehAssigns.size() > 0)
                    db.Assignment().DeleteByPlanID(vehAssigns.get(0).getZuphrLpid());
                for (int i = 0; i < vehAssigns.size(); i++) {
                    VehAssign vechAssign = vehAssigns.get(i);
                    vechAssign.AddtoDB(true);
                    String id = String.valueOf(db.Assignment().insertAssginment(vechAssign));

                }
            });


        }
        for (int i = 0; i < vehAssigns.size(); i++) { // for vehassigns
            VehAssign vehAssignObj = vehAssigns.get(i);
            for (int j = 0; j < materials.size(); j++) { // for materials
                Material loopMaterial = materials.get(j);
                Vehicle loopMaterialVehicle = null;
                Plan planx = null;
                Vehicle vehicle = null;
                List<Vehicle> vehicleList = new ArrayList<>();
                List<Driver> drivers = new ArrayList<>();
                List<Material> Matxx;

                if (vehAssignObj.getZuphrMblpo().equals(loopMaterial.getZuphrMblpo())) //matching vehassign material id with material material id
                {

                    //BREAKPOINT
                    if (loopMaterial.getVehicles() != null && loopMaterial.getVehicles().size() > 0) { // checking if material has list of vehicles
                        List<Vehicle> materialVehicleList = loopMaterial.getVehicles(); //material vehicle list

                        for (int k = 0; k < materialVehicleList.size(); k++) { //looping through material vehicle list
                            loopMaterialVehicle = materialVehicleList.get(k); // material vehicle

                            if (loopMaterialVehicle.getVehid().equals(vehAssignObj.getZuphrVehid())) {
                                           /*
                                       checking if material vehicle matches vehassign object (if first time seeing it)
                                       */
                                vehicle = loopMaterialVehicle;
                                drivers = loopMaterialVehicle.getLoaders();
                            }
                            vehicleList.add(vehicle);
                        }

                    } else if (loopMaterialVehicle == null) {

                        drivers.clear();
                        driver = new Driver(vehAssignObj.getZuphrDriverid(), vehAssignObj.getZuphrDriverName(),
                                "", "",
                                "", "", "");

                        vehicle = new Vehicle(vehAssignObj.getZuphrVehid(), "test", vehAssignObj.getZuphrVehType(), "",
                                "", "", "", "", "", drivers);
                        drivers.add(driver);
                        vehicleList.add(vehicle);


                    }
                    int count = 0;
                    for (int d = 0; d < drivers.size(); d++) {
                        if (drivers.get(d).getZuphrDriverid().equalsIgnoreCase(vehAssignObj.getZuphrDriverid())) {
                            count++;
                        }
                    }

                    if (count == 0) {
                        driver.setZuphrDriverid(vehAssignObj.getZuphrDriverid());
                        drivers.add(driver);
                    }

                    vehicle.setLoaders(drivers);

                    loopMaterial.setVehicles(vehicleList);

                    plan.getValue().getPlanToItems().set(j, loopMaterial);

                    planx = plan.getValue();
                    Matxx = planx.getPlanToItems();
                    Matxx.set(j, loopMaterial);
                    planx.setPlanToItems(Matxx);
                    List<Plan> plans = Plans.getValue();
                    //plans.set(pos,planx);
                    Plans.postValue(plans);

                    plan.postValue(planx);

                }

                plan.getValue().getPlanToItems().set(j, loopMaterial);

            }


        }
    }

    public void getLoaderMtr(String Lpid, LifecycleOwner owner) {

        if (Mode.equals("offline")) {

            db.Load().GetItemtoPost(Lpid, Loginsession.getInstance().getUser().UserId, true).observe(owner, itemlist -> {

                MatchingVechAssginLoader(itemlist, false);

            });

        } else {
            retrofitInterface.getDispatch("LpHdrSet('" + Lpid + "')?$expand=NavLpToVehAssign").enqueue(new Callback<AssignmentUnpack>() {
                @Override
                public void onResponse(Call<AssignmentUnpack> call, Response<AssignmentUnpack> response) {

                    if (response.errorBody() != null) {


                    }
                    if (response.isSuccessful()) {
                        List<VechAssignLoader> loaders = new ArrayList<>();

                        for (int i = 0; i < response.body().getAssignment().getVehassign().size(); i++) {

                            if(response.body().getAssignment().getVehassign().get(i).getZuphrDriverid().equalsIgnoreCase(Loginsession.getInstance().getUser().UserId)) {
                                VechAssignLoader vechAssignLoader = new VechAssignLoader(
                                        response.body().getAssignment().getVehassign().get(i).getZuphrLpid(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrMjahr(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrMblpo(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrDriverid(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrVehid(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrLoad(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrUload(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrNfound(),
                                        response.body().getAssignment().getVehassign().get(i).getZuphrProc(), true);

                                loaders.add(vechAssignLoader);
                            }



                        }

                        MatchingVechAssginLoader(loaders, true);
                    }


                }


                @Override
                public void onFailure(Call<AssignmentUnpack> call, Throwable t) {

                }
            });

        }


    }

    private void MatchingVechAssginLoader(List<VechAssignLoader> vehAssigns, Boolean flag) {

        List<Material> materials = plan.getValue().getPlanToItems();

        Boolean flaglooded = true;

        if (flag) {
            AppExecutors.getInstance().diskIO().execute(() -> {

                if (vehAssigns != null) {
                    if (vehAssigns.size() > 0) {
                        db.Load().DeleteByPlanID(vehAssigns.get(vehAssigns.size() - 1).getZuphrLpid());
                    }
                }
                for (int i = 0; i < vehAssigns.size(); i++) {
                    VechAssignLoader loader = vehAssigns.get(i);
                    loader.AddtoDB(true);
                    String id = String.valueOf(db.Load().insertAssginment(loader));

                }
            });


        }
        for (int i = 0; i < vehAssigns.size(); i++) { // for vehassigns

            VechAssignLoader vehAssignObj = vehAssigns.get(i);

            for (int j = 0; j < materials.size(); j++) { // for materials
                Material loopMaterial = materials.get(j);
                Plan planx = null;
                List<Material> Matxx;

                if (vehAssignObj.getZuphrMblpo().equals(loopMaterial.getZuphrMblpo())) //matching vehassign material id with material material id
                {


                    if (!vehAssignObj.getZuphrLoad().toLowerCase().contains("x") && flaglooded) {
                        flaglooded = false;
                    }
                    loopMaterial.setComplete(flaglooded);

                    loopMaterial.getVehAssignList().add(vehAssignObj);


                    plan.getValue().getPlanToItems().set(j, loopMaterial);

                    planx = plan.getValue();
                    Matxx = planx.getPlanToItems();
                    planx.setPlanToItems(Matxx);
                    plan.setValue(planx);


                    List<Plan> planslist = Plans.getValue();
                    for (int x = 0; x < planslist.size(); x++) {
                        if (plan.getValue().getZuphrLpid().equalsIgnoreCase(planslist.get(x).getZuphrLpid())) {
                            planslist.set(x, plan.getValue());
                            Plans.setValue(planslist);
                            break;
                        }


                    }


                }

            }


        }


    }

    public void postDriver() {
        Driver driver = new Driver("T_CBAD_PPLN", "Planner testuser ",
                "Heavy Truck", "74352",
                "Saudi", "05895798", "PPLN.Mohammed@aramco.com");


        driver = new Driver("", "T_CBAD_PPLN",
                "Heavy Truck", "546456",
                "Saudi", "054380958", "T_CBAD_PPLN@aramco.com");

        retrofitInterface.SaveDriver(driver, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.errorBody() != null) {


                }
                if (response.isSuccessful()) {

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void postVehicle() {

        Vehicle vehicle = new Vehicle("54353", "Meduim",
                "Truck", "654654", "10000", "Red", "2012",
                "2022-01-12T00:00:00", "54354",
                new ArrayList<>());

        retrofitInterface.SaveVechile(vehicle, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.errorBody() != null) {

                }
                if (response.isSuccessful()) {

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void postDevice() {

        Device device = new Device("1", "Androidtablet",
                "54644564", "654654");


        retrofitInterface.SaveDevice(device, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.errorBody() != null) {


                }
                if (response.isSuccessful()) {

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

    }

    public void AssignValue(MatrialDispatching matrialDispatching, LifecycleOwner owner) {

        if (Mode.equals("offline")) {

            AppExecutors.getInstance().diskIO().execute(() -> {

                db.Assignment().DeleteByPlanID(matrialDispatching.getZuphrLpid());

                for (int i = 0; i < matrialDispatching.getVehassign().size(); i++) {
                    VehAssign assign = matrialDispatching.getVehassign().get(i);
                    assign.AddtoDB(false);
                    db.Assignment().insertAssginment(assign);
                }


            });


        } else {
            retrofitInterface.Dispatch(matrialDispatching, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                    if (response.errorBody() != null) {

                    }
                    if (response.isSuccessful()) {

                        AppExecutors.getInstance().diskIO().execute(() -> {
                            db.Assignment().DeleteByPlanID(matrialDispatching.getZuphrLpid());

                            for (int i = 0; i < matrialDispatching.getVehassign().size(); i++) {
                                VehAssign assign = matrialDispatching.getVehassign().get(i);
                                assign.AddtoDB(true);
                                matrialDispatching.getVehassign().set(i, assign);
                            }
                            MatchingVechAssgin(matrialDispatching.getVehassign(), true);

                        });


                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                }
            });


        }
    }

    public void AssignValueLoader(VechAssignLoader vechAssignLoader, LifecycleOwner owner) {

        if (Mode.equals("offline")) {

            AppExecutors.getInstance().diskIO().execute(() -> {

                db.Load().DeleteByID(vechAssignLoader.getZuphrLpid(),
                        vechAssignLoader.getZuphrDriverid(), vechAssignLoader.getZuphrMblpo(), vechAssignLoader.getZuphrMjahr());

                db.Load().insertAssginment(vechAssignLoader);


            });


        } else {
            //   if(!vechAssignLoader.isAddedtoDB())
            retrofitInterface.LoaderStatus("ZuphrLpid='" + vechAssignLoader.getZuphrLpid() + "',ZuphrMjahr='" + vechAssignLoader.getZuphrMjahr() + "',ZuphrMblpo='" + vechAssignLoader.getZuphrMblpo() + "'"
                    , vechAssignLoader, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                    if (response.errorBody() != null) {

                    }
                    if (response.isSuccessful()) {

                        AppExecutors.getInstance().diskIO().execute(() -> {
                            db.Load().DeleteByPlanID(vechAssignLoader.getZuphrLpid());
                        });

                        //  getLoaderMtr(vechAssignLoader.getZuphrLpid(),owner);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }


    }

    public void GetImages(Application application, String MaterialId) {

        retrofitInterface.getImageList("ImageHandlingSet?$filter=ZuphrId eq'" + MaterialId + "'and AppPrefix eq 'STEV 'and Item eq '1' and ThumbNail eq ''")
                .enqueue(new Callback<ImageList>() {
                    @Override
                    public void onResponse(Call<ImageList> call, retrofit2.Response<ImageList> response) {
                        if (response.isSuccessful()) {
                            List<imagenode> temp = response.body().getItems();
                            MatrialImageList.postValue(temp);
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageList> call, Throwable t) {
                    }
                });
    }

    public void StroingImages(Application application, List<imagenode> imagenode, String MatrialId) {

        SharedPreferences preferences = application.getSharedPreferences(application.getResources()
                .getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        String token2 = preferences.getString("x-csrf-token", "");

        for (int i = 0; i < imagenode.size(); i++) {

            imagenode imagenode1 = imagenode.get(i);
            imagenode1.setZuphrId(MatrialId);
            retrofitInterface.SaveImages(token2, imagenode1).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        ResponseBody temp = response.body();


                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                }
            });


        }
    }

    public void offlineDispatchItems(LifecycleOwner owner, String lpid) {
        if (!Mode.equals("offline")) {
            db.Assignment().GetItemtoPost(lpid, false).observe(owner, items -> {


                if (items != null) {
                    if (items.size() > 0 && Loginsession.getInstance().isOfflineflag()) {
                        Loginsession.getInstance().setOfflineflag(false);
                        VechAssignDispatchList.postValue(items);
                    } else if (Loginsession.getInstance().isOfflineflag()) {
                        Loginsession.getInstance().setOfflineflag(false);
                        getDispatchMtr(lpid, owner);
                    }
                } else if (Loginsession.getInstance().isOfflineflag()) {
                    Loginsession.getInstance().setOfflineflag(false);
                    getDispatchMtr(lpid, owner);
                }
            });


        } else {
            Loginsession.getInstance().setOfflineflag(false);
            getDispatchMtr(lpid, owner);
        }

    }

    public void offlineLoaderItems(LifecycleOwner owner, String lpid) {

        if (!Mode.equals("offline")) {
            db.Load().GetItemtoPost(lpid, Loginsession.getInstance().getUser().UserId, false).observe(owner, items -> {

                if (items != null) {
                    if (items.size() > 0 && Loginsession.getInstance().isOfflineflag()) {
                        Loginsession.getInstance().setOfflineflag(false);
                        VechAssignLoaderList.postValue(items);
                    } else if (Loginsession.getInstance().isOfflineflag()) {
                        Loginsession.getInstance().setOfflineflag(false);
                        getLoaderMtr(lpid, owner);
                    }
                } else if (Loginsession.getInstance().isOfflineflag()) {
                    Loginsession.getInstance().setOfflineflag(false);
                    getDispatchMtr(lpid, owner);
                }
            });

        } else {
            Loginsession.getInstance().setOfflineflag(false);
            getLoaderMtr(lpid, owner);
        }

    }


    public void PostOfflineDispatchContent(List<VehAssign> vehAssigns, LifecycleOwner owner) {

        String id = plan.getValue().ZuphrLpid;

        MatrialDispatching matrialDispatching = new MatrialDispatching();
        matrialDispatching.setZuphrLpid(id);
        matrialDispatching.setVehassign(vehAssigns);

        AppExecutors.getInstance().diskIO().execute(() -> {
            db.Assignment().DeleteByPlanID(id);
        });
        Loginsession.getInstance().setOfflineflag(false);
        if (vehAssigns.size() > 0)
            AssignValue(matrialDispatching, owner);


    }

    public void PostOfflineLoaderContent(List<VechAssignLoader> vechAssignLoader, LifecycleOwner owner) {

        String id = plan.getValue().ZuphrLpid;

        AppExecutors.getInstance().diskIO().execute(() -> {

            for (int i = 0; i < vechAssignLoader.size(); i++) {
                AssignValueLoader(vechAssignLoader.get(i), owner);
            }

        });


        getLoaderMtr(id, owner);

    }


    public void deleteDispatchObject(VehAssign vehAssign) {

        AppExecutors.getInstance().diskIO().execute(() -> {
            db.Assignment().DeleteByObj(vehAssign);
        });

    }

    public void deleteLoaderObject(@Nullable VechAssignLoader vechAssignLoader, List<VechAssignLoader> loader) {


        AppExecutors.getInstance().diskIO().execute(() -> {

            if (vechAssignLoader != null)
                db.Load().DeleteByID(vechAssignLoader.getZuphrLpid(),
                        vechAssignLoader.getZuphrDriverid(), vechAssignLoader.getZuphrMblpo(), vechAssignLoader.getZuphrMjahr());
            if (loader != null) {

                for (int i = 0; i < loader.size(); i++) {
                    db.Load().DeleteByID(loader.get(i).getZuphrLpid(),
                            loader.get(i).getZuphrDriverid(), loader.get(i).getZuphrMblpo(), loader.get(i).getZuphrMjahr());
                }
            }

        });
    }


}


