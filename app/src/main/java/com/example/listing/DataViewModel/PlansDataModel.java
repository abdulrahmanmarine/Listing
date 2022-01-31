package com.example.listing.DataViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listing.Login;
import com.example.listing.Manual_Assigment.Manual_AssignMultiDialogFragment;
import com.example.listing.Manual_Assigment.Manual_Assignment_Adapter;
import com.example.listing.R;
import com.example.listing.Utils.AppExecutors;

import com.example.listing.Utils.DataClass;
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
import com.example.listing.models.SAPNote;
import com.example.listing.models.User;
import com.example.listing.models.Userunpack;
import com.example.listing.models.VehAssign;
import com.example.listing.models.Vehicle;
import com.example.listing.models.VehicleUnpack;
import com.example.listing.models.imagenode;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlansDataModel extends ViewModel {

    private final SharedPreferences preferences;
    private final String Mode;
    private final OfflineDatabaseClient db;
    public MutableLiveData<List<Plan>> Plans = new MutableLiveData<>();
    public  MutableLiveData<Plan> plan = new MutableLiveData<>();
    public MutableLiveData<List<com.example.listing.models.Material>>MatrialsList = new MutableLiveData<>();
    public  MutableLiveData<Boolean> UserRule =new MutableLiveData<>();

    public  MutableLiveData<List<Driver>> MasterdriversList = new MutableLiveData<>();
    public MutableLiveData<List<Vehicle>> MastervehiclesList = new MutableLiveData<>();
    public MutableLiveData<List<imagenode>> MatrialImageList = new MutableLiveData<>();
//    public MutableLiveData<LoadAction> LoadAction = new MutableLiveData<>();
    public MutableLiveData<Boolean> flag = new MutableLiveData<>(true);
    private Manual_AssignMultiDialogFragment manualAssignMultiDialogFragment;
    Application application;


    private static RetrofitInterface retrofitInterface;


    public PlansDataModel(Application application,String id) {
        super();
        this.application = application;

        retrofitInterface = RestApiClient.getInstance(application).getRetrofitInterface();

        preferences = application.getSharedPreferences(application.getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        Mode = preferences.getString(application.getResources().getString(R.string.UserMode), "");
        db = OfflineDatabaseClient.getInstance(application.getApplicationContext());


    }



    public void getplansLoader(Application application,LifecycleOwner owner ,User user) throws IOException {
        //OFFLINE DATA RETRIEVAL

        String userid=Loginsession.getInstance().getUser().getUserId().toUpperCase();

        if (Mode.equals("offline")) {


            db.planitem().GetItemAll(userid).observe(owner,itemlist->{
                Log.i("test plans:",itemlist.size()+"");

                for(int i=0 ;i<itemlist.size();i++){
                    Plan plan=itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist->{
                        Log.i("test matrial:",matriallist.size()+"");

                        for(int j=0 ;j<matriallist.size();j++) {
                            Material material=matriallist.get(j);
                            int x=j;
//                            db.MatrialLoadAction().GetItemAll(String.valueOf(material.MatrialId)).observe(owner, loadaction->{
//                                material.setZuphrLoada(loadaction);
//                                matriallist.set(x,material);
//                            });

//                            List<Vehicle> vehicles = db.MatrialVehicles().GetItemAll(String.valueOf(material.MatrialId));
//                            material.setVehicles(vehicles);
//                            matriallist.set(x, material);
//
//                            for(int y = 0 ; y <vehicles.size() ; y++){
//                                Vehicle vehicle = vehicles.get(y);
//                                List<Driver> drivers  = db.MatrialDrivers().GetItemAll(String.valueOf(vehicle.VehicleId));
//                                material.setDrivers(drivers);
//                                matriallist.set(x, material);
//                            }
                        }



                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i,plan);
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
                        List<Plan> temp =response.body().getItems();
                        Plans.postValue(temp);

                         AppExecutors.getInstance().diskIO().execute(() -> {
//                             db.MatrialLoadAction().nukeTable();
                             db.planitem().nukeTable();
                             db.Matrial().nukeTable();
                             db.MatrialDrivers().nukeTable();
                             db.MatrialImage().nukeTable();
                             db.Notes().nukeTable();
                            for(int i=0 ;i<temp.size();i++){
                                Plan plan= temp.get(i);
                                plan.setZuphrFpName(userid.toUpperCase());
                                String id = String.valueOf(db.planitem().insertplan(plan));

                                for(int j=0 ;j<temp.get(i).getPlanToItems().size();j++) {
                                    Material material=temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    String Mid= String.valueOf(db.Matrial().insertMatrial(material));
//                                    LoadAction loadAction=material.getZuphrLoada();
//                                    loadAction.setMatiralOfflineID(Mid);
//                                    db.MatrialLoadAction().insertLoadAction(loadAction);

//                                    for(int x=0 ; x<material.getVehicles().size();x++){
//                                        Vehicle vehicle = material.getVehicles().get(j);
//                                        String idV = String.valueOf(db.MatrialVehicles().insertV(vehicle));
//
//                                        for (int y = 0 ; y <vehicle.getLoaders().size(); y++){
//                                            Driver driver = vehicle.getLoaders().get(y);
//                                            driver.setVechileId(idV);
//                                            db.MatrialDrivers().insertImage(driver);
//                                        }
//                                    }
                                }



                            }
                        });

                    }

                }
                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {
                    Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

                }
            });
        }





   }
    public void getplansDispatcher(Application application, LifecycleOwner owner) throws IOException {
        //OFFLINE DATA RETRIEVAL
        if (Mode.equals("offline")) {
            db.planitem().GetItemAll(Loginsession.getInstance().getUser().getUserId().toUpperCase()).observe(owner,itemlist->{
                Log.i("test plans:",itemlist.size()+"");

                for(int i=0 ;i<itemlist.size();i++){
                    Plan plan=itemlist.get(i);
                    db.Matrial().GetItemAll(String.valueOf(plan.getPlanId())).observe(owner, matriallist->{
                        Log.i("test matrial:",matriallist.size()+"");

                        for(int j=0 ;j<matriallist.size();j++) {
                            Material material=matriallist.get(j);
                            int x=j;
//                            db.MatrialLoadAction().GetItemAll(String.valueOf(material.MatrialId)).observe(owner, loadaction->{
//                                Log.i("test loadaction:",loadaction.MatiralOfflineID+"");
//
//                                material.setZuphrLoada(loadaction);
//                                matriallist.set(x,material);
//                            });

//                            List<Vehicle> vehicles = db.MatrialVehicles().GetItemAll(String.valueOf(material.MatrialId));
//                            material.setVehicles(vehicles);
//                            matriallist.set(x, material);
//
//                            for(int y = 0 ; y <vehicles.size() ; y++){
//                                Vehicle vehicle = vehicles.get(y);
//                                List<Driver> drivers  = db.MatrialDrivers().GetItemAll(String.valueOf(vehicle.VehicleId));
//                                material.setDrivers(drivers);
//                                matriallist.set(x, material);
//                            }


                        }
                        plan.setPlanToItems(matriallist);
                    });

                    itemlist.set(i,plan);
                }
                Plans.postValue(itemlist);
            });

        }

        //ONLINE DATA RETRIEVAL
        else {

     //     Plans.setValue(DataClass.getInstance().getPlans());


            retrofitInterface.getPlansDispatcher("Fetch").enqueue(new Callback<PlanUnpack>() {
                @Override
                public void onResponse(Call<PlanUnpack> call, retrofit2.Response<PlanUnpack> response) {
                    if (response.isSuccessful()) {
                        List<Plan> temp =response.body().getItems();
                        Plans.postValue(temp);

                        AppExecutors.getInstance().diskIO().execute(() -> {
                            for(int i=0 ;i<temp.size();i++){
                                Plan plan= temp.get(i);
//                                plan.setZuphrFpName(Loginsession.getInstance().getUser().getUserId().toUpperCase());
                                String id = String.valueOf(db.planitem().insertplan(plan));

                                for(int j=0 ;j<temp.get(i).getPlanToItems().size();j++) {
                                    Material material=temp.get(i).getPlanToItems().get(j);
                                    material.setPlanOfflineID(id);
                                    String Mid= String.valueOf(db.Matrial().insertMatrial(material));
//                                    LoadAction loadAction=material.getZuphrLoada();
//                                    loadAction.setMatiralOfflineID(Mid);
//                                    db.MatrialLoadAction().insertLoadAction(loadAction);

//                                    for(int x=0 ; x<material.getVehicles().size();x++){
//                                        Vehicle vehicle = material.getVehicles().get(j);
//                                        String idV = String.valueOf(db.MatrialVehicles().insertV(vehicle));
//
//                                        for (int y = 0 ; y <vehicle.getLoaders().size(); y++){
//                                            Driver driver = vehicle.getLoaders().get(y);
//                                            driver.setVechileId(idV);
//                                            db.MatrialDrivers().insertImage(driver);
//                                        }
//                                    }
                                }



                            }
                        });

                    }

                }
                @Override
                public void onFailure(Call<PlanUnpack> call, Throwable t) {
                    Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());

                }
        });



        }
    }
    public  void getVechiles(){

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


        Vehicle vehicle1 = new Vehicle("1","Medium", "Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle2 = new Vehicle("2","Medium", "Crane", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle3 = new Vehicle("3","Medium", "Two Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle4 = new Vehicle("4","Medium", "Four Wheel", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle5 = new Vehicle("5","Medium", "Small Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);
        Vehicle vehicle6 = new Vehicle("6","Medium", "Huge Truck", "456234", "1000",
                "Red", "2012", "DDMMYYYY", "123456",null);

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



        retrofitInterface.GetVehicle().enqueue(new Callback<VehicleUnpack>() {
            @Override
            public void onResponse(Call<VehicleUnpack> call, Response<VehicleUnpack> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("get vehicle error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                        MastervehiclesList.setValue(response.body().getvehiclelist());

                    Log.i("get vehi error response",response.body().getvehiclelist().size() + " ");


                }


            }

            @Override
            public void onFailure(Call<VehicleUnpack> call, Throwable t) {

                Log.i("get driver no response",t.getMessage());

            }
        });



    }
    public  void getDevice(){
        retrofitInterface.GetDevice().enqueue(new Callback<Deviceunpack>() {
            @Override
            public void onResponse(Call<Deviceunpack> call, Response<Deviceunpack> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("get device error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                        Log.i("get device response",response.body().getdevice().getSno()+"");

                }


            }

            @Override
            public void onFailure(Call<Deviceunpack> call, Throwable t) {

                Log.i("get driver no response",t.getMessage());

            }
        });



    }
    public  void getdrivers(){

        retrofitInterface.GetLoader().enqueue(new Callback<DriverUnpack>() {
            @Override
            public void onResponse(Call<DriverUnpack> call, Response<DriverUnpack> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("get driver error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                    Log.i("get driver error response",response.body().getriverlist().size()+"");
                    MasterdriversList.setValue(response.body().getriverlist());
                }


            }

            @Override
            public void onFailure(Call<DriverUnpack> call, Throwable t) {

                Log.i("get driver no response",t.getMessage());

            }
        });
    }
    public  void getDispatchMtr(String Lpid ,int pos){
        retrofitInterface.getDispatch(Loginsession.getInstance().getToken(),"LpHdrSet('"+Lpid+"')?$expand=NavLpToVehAssign").enqueue(new Callback<AssignmentUnpack>() {
            @Override
            public void onResponse(Call<AssignmentUnpack> call, Response<AssignmentUnpack> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("get plan error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){

                    List<VehAssign> vehAssigns =response.body().getAssignment().getVehassign();
                    Log.i("list of objects",response.body().getAssignment().getVehassign().size()+"");
                    List<Material> materials=plan.getValue().getPlanToItems();


    //                    List<Driver> drivers=new ArrayList<>();
                    Driver driver=new Driver();
                    List<Plan> planxx = new ArrayList<>();

                    for(int i=0 ; i<vehAssigns.size();i++){ // for vehassigns
                        VehAssign vehAssignObj = vehAssigns.get(i);
                        Log.i("get plan vehAssigns :",vehAssignObj.getZuphrMblpo()+"");
                        for(int j=0; j<materials.size();j++){ // for materials
                            Material loopMaterial = materials.get(j);
                            Vehicle loopMaterialVehicle = null;
                            Plan planx = null;
                            Vehicle vehicle = null;
                            List<Vehicle> vehicleList= new ArrayList<>();
                            List<Driver> drivers = new ArrayList<>();
                            List<Material> Matxx;

                           if(vehAssignObj.getZuphrMblpo().equals(loopMaterial.getZuphrMblpo())) //matching vehassign material id with material material id
                           {

                               //BREAKPOINT
                               if(loopMaterial.getVehicles()!= null && loopMaterial.getVehicles().size() > 0){ // checking if material has list of vehicles
                                   List<Vehicle> materialVehicleList = loopMaterial.getVehicles(); //material vehicle list
//                                   vehicleList.clear();
                                   for (int k = 0 ; k < materialVehicleList.size() ; k++){ //looping through material vehicle list
                                       loopMaterialVehicle = materialVehicleList.get(k); // material vehicle

                                       if(loopMaterialVehicle.getVehid().equals(vehAssignObj.getZuphrVehid())){
                                           /*
                                       checking if material vehicle matches vehassign object (if first time seeing it)
                                       */
                                           vehicle = loopMaterialVehicle;
                                           drivers = loopMaterialVehicle.getLoaders();
                                       }
                                       vehicleList.add(vehicle);
                                   }
                                   Log.i("vehicle list obj 1",vehicle.getVehid());

                               } else if(loopMaterialVehicle == null){
//                                   vehicleList.clear();
                                   drivers.clear();
                                   driver=new Driver(vehAssignObj.getZuphrDriverid(),"  ",
                                           "","",
                                           "","","");
                                   driver.setZuphrdrvrName("Ahmad");
//                                   driver.setZuphrDriverid(vehAssignObj.getZuphrDriverid());
                                   vehicle = new Vehicle(vehAssignObj.getZuphrVehid(),"test","","",
                                           "","","","","",drivers);
                                   drivers.add(driver);
                                   vehicleList.add(vehicle);

                                   Log.i("vehicle list obj 2",vehAssignObj.getZuphrVehid());
                               }
                               int count = 0;
                               for(int d = 0 ; d < drivers.size() ; d++){
                                   if(drivers.get(d).getZuphrDriverid().equalsIgnoreCase(vehAssignObj.getZuphrDriverid())){
                                       count++;
                                   }
                               }

                               if(count == 0){
                                   driver.setZuphrDriverid(vehAssignObj.getZuphrDriverid());
                                   drivers.add(driver);
                               }

                               vehicle.setLoaders(drivers);
//                               vehicleList.add(vehicle);


                            /*
                            ABEER OR OLD SEGMENT
                               if(loopMaterial.getVehicles()!=null && loopMaterial.getVehicles().size()>0) //CURLY BRACKETS?
                               vehicleList= loopMaterial.getVehicles();
                               int posM=j;
                               int posV=0;
                               for(int k=0 ;k<vehicleList.size();k++){
                                   if(vehAssignObj.getZuphrVehid().equals(vehicleList.get(k).getVehid())){
                                       vehicle=vehicleList.get(k);
                                       drivers = vehicle.getLoaders();
                                       posV=k;
                                   }
                               }

                               if(vehicle==null){
                                   vehicle=new Vehicle(vehAssignObj.getZuphrVehid(),"test","","",
                                           "","","","","",new ArrayList<>());
                               }

                               driver.setZuphrDriverid(vehAssignObj.getZuphrDriverid());
                               vehicle.getLoaders().add(driver);
                               Log.i("get plan materials :",materials.get(j).getZuphrMblpo()+","+vehAssigns.get(i).getZuphrMblpo());
                               vehicleList.add(vehicle);
                            */

//                               loopMaterial.setDrivers(drivers);
                               loopMaterial.setVehicles(vehicleList);

                               plan.getValue().getPlanToItems().set(j, loopMaterial);

                               planx=plan.getValue();
                               Matxx=planx.getPlanToItems();
                               Matxx.set(j,loopMaterial);
                               planx.setPlanToItems(Matxx);
                               List<Plan> plans=Plans.getValue();
                               Log.i("Vech list",loopMaterial.getVehicles().size()+","+loopMaterial.getVehicles().get(0).getLoaders().size());
                               //plans.set(pos,planx);
                               Plans.postValue(plans);

                               plan.setValue(planx);
                               planxx = plans;


//                               manualAssignMultiDialogFragment.dataChanged();
                           }

                            plan.getValue(  ).getPlanToItems().set(j, loopMaterial);
//                            drivers.clear();
                            int breakpoint = 0;
//                           plan.setValue(planx);
//                            vehicleList.clear();

                        }
//                        drivers.clear();

//                        vehicleList.clear();
                    }
//                    Plans.postValue(planxx);


                }


                }




            @Override
            public void onFailure(Call<AssignmentUnpack> call, Throwable t) {

                Log.i("get plan no response",t.getMessage());

            }
        });
    }
    public  void postDriver(){
        Driver driver=new Driver("OS20MA","Osamah Mohammed",
                "Heavy Truck","74352",
                "Saudi","05895798","Osamah.Mohammed@aramco.com");
        Log.i("posting started","start");

        retrofitInterface.SaveDriver(driver, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("post driver error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                    try {
                        Log.i("post driver response",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.i("post driver no response",t.getMessage());

            }
        });

    }
    public  void postVehicle(){

        Vehicle vehicle=new Vehicle("54353","Meduim",
                "Truck","654654", "10000","Red","2012",
                "2022-01-12T00:00:00","54354",
                new ArrayList<>());
        Log.i("posting Vechile started","start");

        retrofitInterface.SaveVechile(vehicle, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("post Vechile error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                    try {
                        Log.i("post Vechile response",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.i("post Vechile no response",t.getMessage());

            }
        });

    }
    public  void postDevice(){

        Device device=new Device("1","Androidtablet",
                "54644564","654654");

        Log.i("posting Vechile started","start");

        retrofitInterface.SaveDevice(device, Loginsession.getInstance().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.errorBody()!=null){
                    try {
                        Log.i("post deivce error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){
                    try {
                        Log.i("post device response",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.i("post Vechile no response",t.getMessage());

            }
        });

    }
    public void AssignValue(MatrialDispatching matrialDispatch) {
        retrofitInterface.Dispatch(matrialDispatch,Loginsession.getInstance().getToken()).enqueue(new Callback<AssignmentUnpack>() {
            @Override
            public void onResponse(Call<AssignmentUnpack> call, retrofit2.Response<AssignmentUnpack> response) {


                if(response.errorBody()!=null){
                    try {
                        Log.i("post dispatch error response",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(response.isSuccessful()){

                     //   Log.i("post dispatch response",response.body());

                }


            }

            @Override
            public void onFailure(Call<AssignmentUnpack> call, Throwable t) {
                Log.i("response-error", t.getMessage());


            }
        });
    }
    public void AssignValueLoader(VehAssign vehAssign) {
        retrofitInterface.LoaderStatus(vehAssign,Loginsession.getInstance().getToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                        if(response.errorBody()!=null){
                            try {
                                Log.i("post loader error response",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        if(response.isSuccessful()){

                            try {
                                Log.i("post loader sucess response",response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("post loader response-error", t.getMessage());


                    }
                });
    }

    public void GetImages(Application application,  String MaterialId){

        retrofitInterface.getImageList("ImageHandlingSet?$filter=ZuphrId eq'"+MaterialId+
                "'and AppPrefix eq 'STEV 'and Item eq '1' and ThumbNail eq ''")
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
                Log.i("response-http" ,t.getMessage()+t.getLocalizedMessage());
            }
        });
    }
    public void StroingImages(Application application, List<imagenode> imagenode,String MatrialId)
            throws JSONException {

        SharedPreferences preferences=application.getSharedPreferences(application.getResources()
                .getString(R.string.SharedPrefName), Activity.MODE_PRIVATE);
        String token2 =preferences.getString("x-csrf-token","");

        Log.i("Imagelist",imagenode.size()+"");
        for(int i=0;i<imagenode.size();i++) {
            imagenode imagenode1 = imagenode.get(i);
            imagenode1.setZuphrId(MatrialId);
            retrofitInterface.SaveImages(token2, imagenode1).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            Log.d("response-post", response.code() + "");
                            if (response.code() == 201) {
                                ResponseBody temp = response.body();

                                try {
                                    Log.i("response-post", temp.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("response-error", t.getMessage());


                        }
                    });
        }

    }

    }

