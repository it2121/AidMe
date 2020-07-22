package com.example.AidMe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.example.AidMe.Models.TestModel;
import com.example.AidMe.Retrofit.ApiClient;
import com.example.AidMe.Retrofit.ApiInterface;
import com.example.fuckboy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public Toolbar toolbar;


    public ListView listView;
    //    public List<Vertex> nodes;
//    public List<Edge> edges;
    public static int linescounter = 0;
    public static String oneStringLocationTemp = "";
    public MapView mMapView;
    public LocationDisplay mLocationDisplay;
    double realLon;
    double realLat;
    String xloc = "";
    String yloc = "";
   public static List list = new ArrayList();

    public static int frompoint, topoint;
    public static GraphicsOverlay mGraphicsOverlay;
    public static int newlinescounter = 0;
    public static int[][] linesbaby;
    public static int[][] geno;
    public static int[][] finalbitch;
    String[] u;
    double[] lines;
    double[] lined;
    double[] linec;
    public static double[][] nodesarray;
    public static double[][] linesarray;
    public LocationListener loclisn;
    public LocationManager locman;
    double nodelon = 0;
    double nodelat = 0;
    public static double[] fuckbox1 = new double[2];
    public static double[] fuckbox2 = new double[2];
    public static boolean lonfuckbox1isbigger = false;
    public static boolean latfuckbox1isbigger = false;
    public static boolean br = false;
    public static double biglon;
    public static double smalllon;
    public static double biglat;
    public static double smalllat;
    int max;
    int k9 = 0;
    int ss = 0;
    public static int[] pathtohell;
    public static int uu;
    public static int jj = 0;
    public static double[][] nodesbaby;
    public static int com1;
    public static int com2;
    public static int from = 0, into = 0;
    public static double[][] pointstemp = new double[3][18726];
    public static int[][] tempfmaplines = new int[5][10000];
    boolean workdone = false;
    //int[] finalnodes;
    public static int distance = 0;
    public static int k1 = 0, k2 = 0, k3 = 0, k4 = 0, k5 = 0;
    public static final String TAG = "MainActivity";
    public static TextView Hospitaltittal;
    public static TextView Hospitalinfo;
    public static TextView car;
    public static TextView walking;
    public static TextView testing;
public  static double time=0;
    ApiInterface apiInterface ;
    ApiInterface apiInterfacen ;

    public static List listm = new ArrayList();

    int timeforcar =0, timeforwalking=0,dis=0;
    //int timeforcart =0, timeforwalkingt=0,dist=0;
    private FusedLocationProviderClient fusedLocationClient;
    protected void onCreate(Bundle savedInstanceState) {

        //   from = 2756;
        //   into = 3542;

//   .printStackTrace();




        super.onCreate(savedInstanceState);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        databaseAccess.open();
        nodesarray = databaseAccess.getnodes();
        linesarray = databaseAccess.getLines();
        DijkstraAlgorithm d;
        databaseAccess.close();

        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.testing = (TextView) findViewById(R.id.textView);
        testing.setText("\n");
        testing.append("\n");
       // Message m = new Message();
        String text = "Take steps to care for yourself and help protect others in your home and community. \n - Wash your hands often\n -Avoid close contact\n - Cover your mouth and nose with a cloth face cover when around others \n - Cover coughs and sneezes \n - Clean and disinfect \n - Monitor Your Health\n - be safe.";
      //  m.show("caution",text);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Caution")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


//        Call<TestModel> ins = apiInterface.insert(name.toString(),
//                age.toString(),
//                number.toString());
//        ins.enqueue(new Callback<TestModel>() {
//            @Override
//            public void onResponse(Call<TestModel> call, Response<TestModel> response) {
//                testing.append("worked");
//            }
//
//            @Override
//            public void onFailure(Call<TestModel> call, Throwable t) {
//                testing.append("fuck");
//
//            }
//        });
        //Call<TestModel> testModelCall = apiInterface.info(age);
//        Intent intent = new Intent(MainActivity.this.RegisterActivity.class);
//        startActivity(intent);
        //final String[] name111 = new String[1];
//        testModelCall.enqueue(new Callback<TestModel>() {
//            @Override
//            public void onResponse(Call<TestModel> call, Response<TestModel> response) {
//                if(response.body()!=null){
//
//                    TestModel testModel = response.body();
//                  name111[0] =  testModel.getName();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TestModel> call, Throwable t) {
//
//            }
//        });







        this.Hospitaltittal = (TextView) findViewById(R.id.hospitaltittle);
        this.Hospitalinfo = (TextView) findViewById(R.id.hospitalinfo);
        this.car = (TextView) findViewById(R.id.car);
        this.walking = (TextView) findViewById(R.id.walking);



       //this.editText=(EditText)findViewById(R.id.editText);
       // editText.setText("123");
        // *** ADD ***
        mMapView = findViewById(R.id.mapView);
        setupMap();


        setupLocationDisplay();


//      createGraphics();

        createGraphicsOverlay();

//       fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                realLon = location.getLongitude();
//                realLat = location.getLatitude();



               // clashortdis();

//                process p = new process();
//                p.start();
//
//                while(p.isAlive()){
//                }


               // testing.append("\nTime = "+time);
//                car.append("The Distance is ="+getDistance(pathtohell));
//                dis=getDistance(pathtohell);
//                timeforcar = dis/420;
//                timeforwalking = dis/60;
//                testing.append("\n"+dis);
//                if(timeforcar==0){
//                    double x = dis/420;
//                    int xx = (int) (x * 100);
//                    timeforcar = xx / 100;
//
//                }
//                car.setText(timeforcar+" min");
//                walking.setText(timeforwalking+" min");
//                if (location != null) {
//                }
//            }
//        });

        int cc = 0;

    }

    public void initViews() {

        Log.d(TAG, "intViews: started");
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public static void startThread() {


    }
    public static int getDistance(int[] pathoo) {

        int w = 0;
        for (int i = 0; i < MainActivity.uu - 1; i++) {
            for (int ii = 0; ii < MainActivity.linesbaby[0].length; ii++) {
                if ((pathoo[i] == MainActivity.linesbaby[2][ii] && pathoo[i + 1] == MainActivity.linesbaby[4][ii])) {

                    w += MainActivity.linesbaby[6][ii];


                }
                if ((pathoo[i + 1] == MainActivity.linesbaby[2][ii] && pathoo[i] == MainActivity.linesbaby[4][ii])) {
                    w += MainActivity.linesbaby[6][ii];

                }
            }
        }
return w;

    }


        //Toast.makeText(getApplicationContext(),"forth is selected",Toast.LENGTH_SHORT).show();}
      // whatever(editText,w);



    private void createGraphicsOverlay() {
        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    private void createPointGraphics() {
        Point point = new Point(nodesarray[1][into - 1], nodesarray[2][into - 1], SpatialReferences.getWgs84());
        // Point point1 = new Point(nodesarray[1][from - 1], nodesarray[2][from - 1], SpatialReferences.getWgs84());

        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        //  Graphic pointGraphic1 = new Graphic(point1, pointSymbol);

        mGraphicsOverlay.getGraphics().add(pointGraphic);
        //mGraphicsOverlay.getGraphics().add(pointGraphic1);
    }

    public static void createPolylineGraphics(int[][] bitch) {


        for (int i = 0; i < bitch[0].length; i++) {
            PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());

            polylinePoints.add(new Point(nodesarray[1][bitch[1][i] - 1], nodesarray[2][bitch[1][i] - 1]));
            polylinePoints.add(new Point(nodesarray[1][bitch[2][i] - 1], nodesarray[2][bitch[2][i] - 1]));
            Polyline polyline = new Polyline(polylinePoints);

            SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3.0f);
            Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
            mGraphicsOverlay.getGraphics().add(polylineGraphic);
        }


    }

    public static void createPolylineGraphicspath(int[] bitch) {


        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());

        for (int i = 0; i < bitch.length; i++) {

            polylinePoints.add(new Point(nodesarray[1][bitch[i] - 1], nodesarray[2][bitch[i] - 1]));
            polylinePoints.add(new Point(nodesarray[1][bitch[i] - 1], nodesarray[2][bitch[i] - 1]));

        }
        Polyline polyline = new Polyline(polylinePoints);

        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        mGraphicsOverlay.getGraphics().add(polylineGraphic);

    }

    private void createGraphics() {

        // createPolygonGraphics()
    }


    public void clashortdis() {

        double shortdist = 10000;
        int idholder = 0;
        double tempholder = 0;

        for (int i = 0; i < 18726; i++) {
            if (Math.abs(nodesarray[1][i] - realLon) <= 0.005) {
                if (Math.abs(nodesarray[2][i] - realLat) <= 0.005) {
                    tempholder = Math.sqrt(Math.pow((realLon - nodesarray[1][i]), 2) + Math.pow((realLat - nodesarray[2][i]), 2));

                    if (shortdist > tempholder) {
                        idholder = i;
                        shortdist = tempholder;
                    }

                }

            }
        }

        String [][] hospitalinfo = new String [2][9];
        hospitalinfo[0][0] = "مستشفى الموانئ العام";
        hospitalinfo[1][0] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][1] = "مستشفى الفيحاء العام";
        hospitalinfo[1][1] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][2] = "مستشفى البصرة التخصصي للأطفال";
        hospitalinfo[1][2] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][3] = "مستشفى الشفاء العام";
        hospitalinfo[1][3] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][4] = "مستشفى البصرة العام";
        hospitalinfo[1][4] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][5] = "مستشفى الصدر التعليمي";
        hospitalinfo[1][5] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][6] = "مستشفى البصرة للنسائية والأطفال العام";
        hospitalinfo[1][6] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][7] = "مستشفى الموسوي اﻻهلي";
        hospitalinfo[1][7] = "تعمل على مدار 24 ساعة";

        hospitalinfo[0][8] = "مستشفى المواساة اﻻهلي";
        hospitalinfo[1][8] = "تعمل على مدار 24 ساعة";

        double[][] hosp = new double[3][9];
        hosp[0][0] = 18591;
        hosp[1][0] = nodesarray[1][18591 - 1];
        hosp[2][0] = nodesarray[2][18591 - 1];

        hosp[0][1] = 8426;
        hosp[1][1] = nodesarray[1][8426 - 1];
        hosp[2][1] = nodesarray[2][8426 - 1];

        hosp[0][2] = 18236;
        hosp[1][2] = nodesarray[1][18236 - 1];
        hosp[2][2] = nodesarray[2][18236 - 1];

        hosp[0][3] = 7373;
        hosp[1][3] = nodesarray[1][7373 - 1];
        hosp[2][3] = nodesarray[2][7373 - 1];

        hosp[0][4] = 10758;
        hosp[1][4] = nodesarray[1][10758 - 1];
        hosp[2][4] = nodesarray[2][10758 - 1];

        hosp[0][5] = 11280;
        hosp[1][5] = nodesarray[1][11280 - 1];
        hosp[2][5] = nodesarray[2][11280 - 1];

        hosp[0][6] = 10934 ;
        hosp[1][6] = nodesarray[1][10934 - 1];
        hosp[2][6] = nodesarray[2][10934 - 1];

        hosp[0][7] = 3615 ;
        hosp[1][7] = nodesarray[1][3615 - 1];
        hosp[2][7] = nodesarray[2][3615 - 1];

        hosp[0][8] = 2883 ;
        hosp[1][8] = nodesarray[1][2883 - 1];
        hosp[2][8] = nodesarray[2][2883 - 1];

        double tempholder2;
        double shortdist2 = 10000;
        int idholder2 = 0;
        for (int i = 0; i < 9; i++) {

            tempholder2 = Math.sqrt(Math.pow((nodesarray[1][idholder] - hosp[1][i]), 2) + Math.pow((nodesarray[2][idholder] - hosp[2][i]), 2));

            if (shortdist2 > tempholder2) {
                idholder2 = i;
                shortdist2 = tempholder2;
            }


        }

       // idholder2=7;
        frompoint = idholder + 1;
        topoint = idholder2;
        from = frompoint;

        topoint = (int) hosp[0][topoint];
        into = topoint;
        createPointGraphics();

        Hospitaltittal.setText(hospitalinfo[0][idholder2]);
        Hospitalinfo.setText(hospitalinfo[1][idholder2]);

        //startThread();
    }

    ;

    private void setupLocationDisplay() {


        mLocationDisplay = mMapView.getLocationDisplay();


        mLocationDisplay.addDataSourceStatusChangedListener(dataSourceStatusChangedEvent -> {
            if (dataSourceStatusChangedEvent.isStarted() || dataSourceStatusChangedEvent.getError() == null) {
                return;
            }

            int requestPermissionsCode = 2;
            String[] requestPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (!(ContextCompat.checkSelfPermission(MainActivity.this, requestPermissions[0]) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(MainActivity.this, requestPermissions[1]) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(MainActivity.this, requestPermissions, requestPermissionsCode);
            } else {
                String message = String.format("Error in DataSourceStatusChangedListener: %s",
                        dataSourceStatusChangedEvent.getSource().getLocationDataSource().getError().getMessage());
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
        mLocationDisplay.startAsync();


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationDisplay.startAsync();


        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }

    private void setupMap() {
        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.OPEN_STREET_MAP;
            double latitude = 34.0270;
            double longitude = -118.8050;
            int levelOfDetail = 10;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);


        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//        if (item.getItemId() == R.id.first) {
//           // textView.append("first is selected\n");
//            drawer.closeDrawer(Gravity.LEFT);
//        } else if (item.getItemId() == R.id.second) {
//            //textView.append("second is selected\n");
//            drawer.closeDrawer(Gravity.LEFT);
//
//        } else

            if (item.getItemId() == R.id.thired) {
       Intent callIntent = new Intent(Intent.ACTION_DIAL);
                drawer.closeDrawer(Gravity.LEFT);
               // Toast.makeText(getApplicationContext(),"forth is selected",Toast.LENGTH_SHORT).show();
               // openDoners();
               // startActivity(new Intent(MainActivity.this,doners.class));

           startActivity(callIntent);
        } else if (item.getItemId() == R.id.fifth) {

            Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "009647801872588"));
            drawer.closeDrawer(Gravity.LEFT);

            startActivity(callIntent);

        }
            else if (item.getItemId() == R.id.blast) {




                drawer.closeDrawer(Gravity.LEFT);
                openDoners();

            }
            else if (item.getItemId() == R.id.last) {

                drawer.closeDrawer(Gravity.LEFT);

               openNeeders();

            }
//            else if (item.getItemId() == R.id.forth) {
//           // textView.append("forth is selected\n");
//            drawer.closeDrawer(Gravity.LEFT);
//
//        }

//               Toast.makeText(getApplicationContext(),"forth is selected",Toast.LENGTH_SHORT).show();}

        return true;
    }
public void openDoners()
{
    listm.clear();

    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Call<TestModel> getid = apiInterface.getid("0");

getid.enqueue(new Callback<TestModel>() {
    @Override
    public void onResponse(Call<TestModel> call, Response<TestModel> response) {
        if(response.body()!=null){
            TestModel tt = response.body();

            int iidd= tt.getId();

            for (int i = 1; i <= iidd; i++){

                Call<TestModel> getdata = apiInterface.getdoners(i+"");
                int finalI = i;
                getdata.enqueue(new Callback<TestModel>() {
                    @Override
                    public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                        if(response.body()!=null){
                            TestModel testModel = response.body();
                            String temo = "Name : "+testModel.getName()+"\n   Phone Number : "+ testModel.getPhone()+"\n    Donating : "+testModel.getGives();
                            //adapter.notifyDataSetChanged();
                            listm.add(temo);
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<TestModel> call, Throwable t) {
                    }
                });
                if(i == iidd){



                    Intent intent = new Intent(MainActivity.this, doners.class);
                    startActivity(intent);

                }

            }

        }
    }

    @Override
    public void onFailure(Call<TestModel> call, Throwable t) {

    }
});



//    Intent intent = new Intent(MainActivity.this, doners.class);
//    startActivity(intent);

}
    public void openNeeders(){
       // final int[] u = {1};
        listm.clear();

        apiInterfacen = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TestModel> getidn = apiInterfacen.getidn("0");



        getidn.enqueue(new Callback<TestModel>() {
            @Override
            public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                if(response.body()!=null){
                    TestModel tt = response.body();
                    int iidd=tt.getId();
                    for (int i = 1; i <= iidd; i++){

                        Call<TestModel> getdata = apiInterfacen.getneedes(i+"");
                        int finalI = i;
                        getdata.enqueue(new Callback<TestModel>() {
                            @Override
                            public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                                if(response.body()!=null){
                                    TestModel testModel = response.body();
                                    String temo = "Name : "+testModel.getName()+" \n  Age : "+testModel.getAge()+" \n  Phone Number : "+ testModel.getPhone()+" \n   Donating : "+testModel.getNeedes();
                                    //adapter.notifyDataSetChanged();
                                    listm.add(temo);
                                }else{
                                }
                            }
                            @Override
                            public void onFailure(Call<TestModel> call, Throwable t) {
                            }
                        });
                        if(i == iidd){



                            Intent intent = new Intent(MainActivity.this, needers.class);
                            startActivity(intent);

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<TestModel> call, Throwable t) {

            }
        });



//    Intent intent = new Intent(MainActivity.this, doners.class);
//    startActivity(intent);

    }


}

class process extends Thread {


    public List<Vertex> nodes;
    public List<Edge> edges;
    public double adding = 0.001;

    public void addLane(String laneId, int sourceLocNo, int destLocNo,
                        int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }

    public void run() {


        //MainActivity.textView.append(MainActivity.frompoint + "\n");
        // MainActivity.textView.append(MainActivity.topoint + "\n");
        MainActivity.fuckbox1[0] = MainActivity.nodesarray[1][MainActivity.from - 1];
        MainActivity.fuckbox1[1] = MainActivity.nodesarray[2][MainActivity.from - 1];

        MainActivity.fuckbox2[0] = MainActivity.nodesarray[1][MainActivity.into - 1];
        MainActivity.fuckbox2[1] = MainActivity.nodesarray[2][MainActivity.into - 1];


        if (MainActivity.fuckbox1[0] >= MainActivity.fuckbox2[0]) {

            MainActivity.lonfuckbox1isbigger = true;
        }
        if (MainActivity.fuckbox1[1] >= MainActivity.fuckbox2[1]) {

            MainActivity.latfuckbox1isbigger = true;
        }

        if (MainActivity.lonfuckbox1isbigger) {
            MainActivity.biglon = MainActivity.fuckbox1[0];
            MainActivity.smalllon = MainActivity.fuckbox2[0];
        } else {
            MainActivity.smalllon = MainActivity.fuckbox1[0];
            MainActivity.biglon = MainActivity.fuckbox2[0];
        }

        if (MainActivity.latfuckbox1isbigger) {
            MainActivity.biglat = MainActivity.fuckbox1[1];
            MainActivity.smalllat = MainActivity.fuckbox2[1];
        } else {
            MainActivity.smalllat = MainActivity.fuckbox1[1];
            MainActivity.biglat = MainActivity.fuckbox2[1];
        }
        double longaxis = 0;
        int oppo = 0;
        if ((MainActivity.biglat - MainActivity.smalllat) < 0.0037) {
            longaxis = MainActivity.biglon - MainActivity.smalllon;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            longaxis = longaxis / 2.5;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            MainActivity.biglat = MainActivity.biglat + (longaxis);
            MainActivity.smalllat = MainActivity.smalllat - (longaxis);

            oppo = (int) (MainActivity.biglat * 10000000);
            MainActivity.biglat = (oppo * 1.0) / 10000000;
            oppo = (int) (MainActivity.smalllat * 10000000);
            MainActivity.smalllat = (oppo * 1.0) / 10000000;

        }

        if ((MainActivity.biglon - MainActivity.smalllon) < 0.0037) {
            longaxis = MainActivity.biglat - MainActivity.smalllat;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            longaxis = longaxis / 2.5;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;

            MainActivity.biglon = MainActivity.biglon + (longaxis);
            MainActivity.smalllon = MainActivity.smalllon - (longaxis);

            oppo = (int) (MainActivity.biglon * 10000000);
            MainActivity.biglon = (oppo * 1.0) / 10000000;

            oppo = (int) (MainActivity.smalllon * 10000000);
            MainActivity.smalllon = (oppo * 1.0) / 10000000;

        }

        MainActivity.biglat = MainActivity.biglat + adding;
        MainActivity.biglon = MainActivity.biglon + adding;
        MainActivity.smalllon = MainActivity.smalllon - adding;
        MainActivity.smalllat = MainActivity.smalllat - adding;


        for (int i1 = 0; i1 < 18726; i1++) {

            MainActivity.com1 = Double.compare(MainActivity.nodesarray[1][i1], MainActivity.biglon);
            MainActivity.com2 = Double.compare(MainActivity.nodesarray[1][i1], MainActivity.smalllon);

            if (MainActivity.com1 > 0 || MainActivity.com2 < 0) {
                MainActivity.br = true;
            } else {
                MainActivity.k2++;
                MainActivity.com1 = 0;
                MainActivity.com2 = 0;
                MainActivity.com1 = Double.compare(MainActivity.nodesarray[2][i1], MainActivity.biglat);
                MainActivity.com2 = Double.compare(MainActivity.nodesarray[2][i1], MainActivity.smalllat);
                if (MainActivity.com1 > 0 || MainActivity.com2 < 0) {
                    MainActivity.br = true;
                } else {
                    MainActivity.pointstemp[0][MainActivity.jj] = MainActivity.nodesarray[0][i1];
                    MainActivity.pointstemp[1][MainActivity.jj] = MainActivity.nodesarray[1][i1];
                    MainActivity.pointstemp[2][MainActivity.jj] = MainActivity.nodesarray[2][i1];


                    MainActivity.jj++;
                }

            }
        }


        boolean exiest = false;
        for (int i = 0; i < MainActivity.jj; i++) {
            for (int ii = 0; ii < 27250; ii++) {
                if (MainActivity.linesarray[0][i] <= 27250) {
                    if (MainActivity.linesarray[1][ii] == MainActivity.pointstemp[0][i] || MainActivity.linesarray[4][ii] == MainActivity.pointstemp[0][i]) {
                        if (MainActivity.linescounter > 0) {
                            for (int k = 0; k < MainActivity.linescounter; k++) {
                                if (MainActivity.tempfmaplines[0][k] == (int) MainActivity.linesarray[0][ii]) {
                                    exiest = true;
                                }


                            }
                            if (exiest == false) {
                                MainActivity.tempfmaplines[0][MainActivity.linescounter] = (int) MainActivity.linesarray[0][ii];
                                MainActivity.tempfmaplines[1][MainActivity.linescounter] = (int) MainActivity.linesarray[1][ii];
                                MainActivity.tempfmaplines[2][MainActivity.linescounter] = (int) MainActivity.linesarray[4][ii];
                                MainActivity.tempfmaplines[3][MainActivity.linescounter] = (int) MainActivity.linesarray[7][ii];
                                MainActivity.tempfmaplines[4][MainActivity.linescounter] = (int) MainActivity.linesarray[8][ii];

                                MainActivity.linescounter++;
                            }
                            exiest = false;

                        } else {

                            MainActivity.tempfmaplines[0][MainActivity.linescounter] = (int) MainActivity.linesarray[0][ii];
                            MainActivity.tempfmaplines[1][MainActivity.linescounter] = (int) MainActivity.linesarray[1][ii];
                            MainActivity.tempfmaplines[2][MainActivity.linescounter] = (int) MainActivity.linesarray[4][ii];
                            MainActivity.tempfmaplines[3][MainActivity.linescounter] = (int) MainActivity.linesarray[7][ii];
                            MainActivity.tempfmaplines[4][MainActivity.linescounter] = (int) MainActivity.linesarray[8][ii];

                            MainActivity.linescounter++;
                        }

                    }
                }


            }


        }

        int[][] tempfmaplines2 = new int[5][MainActivity.linescounter];

        boolean belong1 = false;
        boolean belong2 = false;
        boolean be1 = false;
        boolean be2 = false;


        for (int i = 0; i < MainActivity.linescounter; i++) {
            if (MainActivity.tempfmaplines[1][i] == MainActivity.from || MainActivity.tempfmaplines[2][i] == MainActivity.from || MainActivity.tempfmaplines[1][i] == MainActivity.into || MainActivity.tempfmaplines[2][i] == MainActivity.into) {

                tempfmaplines2[0][MainActivity.newlinescounter] = MainActivity.tempfmaplines[0][i];
                tempfmaplines2[1][MainActivity.newlinescounter] = MainActivity.tempfmaplines[1][i];
                tempfmaplines2[2][MainActivity.newlinescounter] = MainActivity.tempfmaplines[2][i];
                tempfmaplines2[3][MainActivity.newlinescounter] = MainActivity.tempfmaplines[3][i];
                tempfmaplines2[4][MainActivity.newlinescounter] = MainActivity.tempfmaplines[4][i];

                MainActivity.newlinescounter++;


            } else {
                for (int ii = 0; ii < MainActivity.jj; ii++) {
                    if (MainActivity.tempfmaplines[1][i] == MainActivity.pointstemp[0][ii]) {
                        belong1 = true;

                    }
                    if (MainActivity.tempfmaplines[2][i] == MainActivity.pointstemp[0][ii]) {
                        belong2 = true;

                    }

                }
                if (belong1 == true && belong2 == true) {
                    tempfmaplines2[0][MainActivity.newlinescounter] = MainActivity.tempfmaplines[0][i];
                    tempfmaplines2[1][MainActivity.newlinescounter] = MainActivity.tempfmaplines[1][i];
                    tempfmaplines2[2][MainActivity.newlinescounter] = MainActivity.tempfmaplines[2][i];
                    tempfmaplines2[3][MainActivity.newlinescounter] = MainActivity.tempfmaplines[3][i];
                    tempfmaplines2[4][MainActivity.newlinescounter] = MainActivity.tempfmaplines[4][i];

                    MainActivity.newlinescounter++;
                }
                belong1 = false;
                belong2 = false;
            }
        }


        int over = 0;
        boolean voerbool = false;
        MainActivity.finalbitch = new int[5][MainActivity.newlinescounter];
        for (int i = 0; i < MainActivity.newlinescounter; i++) {
            MainActivity.finalbitch[0][i] = tempfmaplines2[0][i];
            MainActivity.finalbitch[1][i] = tempfmaplines2[1][i];
            MainActivity.finalbitch[2][i] = tempfmaplines2[2][i];
            MainActivity.finalbitch[3][i] = tempfmaplines2[3][i];
            MainActivity.finalbitch[4][i] = tempfmaplines2[4][i];


        }


        double x, xx, y, yy;




        int max;
        int k9 = 0;
        int ss = 0;

        max = (int) MainActivity.pointstemp[0][0];
        for (int i = 1; i <= MainActivity.jj; i++) {
            if (max < MainActivity.pointstemp[0][i]) {

                max = (int) MainActivity.pointstemp[0][i];

            }
        }


        boolean pointitis1 = false, pointitis2 = false;
        for (int ii = 0; ii < MainActivity.newlinescounter; ii++) {

            for (int iii = 0; iii < MainActivity.jj; iii++) {
                if (MainActivity.finalbitch[1][ii] == MainActivity.pointstemp[0][iii]) {
                    pointitis1 = true;
                }
                if (MainActivity.finalbitch[2][ii] == MainActivity.pointstemp[0][iii]) {
                    pointitis2 = true;
                }

            }

            if (pointitis1 == false) {

                MainActivity.pointstemp[0][MainActivity.jj] = MainActivity.nodesarray[0][MainActivity.finalbitch[1][ii] - 1];
                MainActivity.pointstemp[1][MainActivity.jj] = MainActivity.nodesarray[1][MainActivity.finalbitch[1][ii] - 1];
                MainActivity.pointstemp[2][MainActivity.jj] = MainActivity.nodesarray[2][MainActivity.finalbitch[1][ii] - 1];
                MainActivity.jj++;

            }
            if (pointitis2 == false) {

                MainActivity.pointstemp[0][MainActivity.jj] = MainActivity.nodesarray[0][MainActivity.finalbitch[2][ii] - 1];
                MainActivity.pointstemp[1][MainActivity.jj] = MainActivity.nodesarray[1][MainActivity.finalbitch[2][ii] - 1];
                MainActivity.pointstemp[2][MainActivity.jj] = MainActivity.nodesarray[2][MainActivity.finalbitch[2][ii] - 1];
                MainActivity.jj++;

            }
            pointitis1 = false;
            pointitis2 = false;

        }


        // MainActivity.textView.append(MainActivity.jj + "\n");

        //  MainActivity.textView.append(MainActivity.newlinescounter + "\n");
        MainActivity.nodesbaby = new double[4][MainActivity.jj];
        for (int i = 0; i < MainActivity.jj; i++) {

            MainActivity.nodesbaby[0][i] = MainActivity.pointstemp[0][i];
            MainActivity.nodesbaby[1][i] = i + 1;
            MainActivity.nodesbaby[2][i] = MainActivity.pointstemp[1][i];
            MainActivity.nodesbaby[3][i] = MainActivity.pointstemp[2][i];


        }
        MainActivity.linesbaby = new int[8][MainActivity.newlinescounter];
        int pointersource = 0, pointertargit = 0;
// 0 = db id , 1 new baby id , 2 source id , 3 source baby id , 4 targit , 5 baby targit, the distance
        for (int i = 0; i < MainActivity.newlinescounter; i++) {


            MainActivity.linesbaby[0][i] = MainActivity.finalbitch[0][i];
            MainActivity.linesbaby[1][i] = i + 1;
            MainActivity.linesbaby[2][i] = MainActivity.finalbitch[1][i];
            for (int ii = 0; ii < MainActivity.jj; ii++) {
                if (MainActivity.finalbitch[1][i] == MainActivity.nodesbaby[0][ii]) {
                    pointersource = ii;
                }
                if (MainActivity.finalbitch[2][i] == MainActivity.nodesbaby[0][ii]) {
                    pointertargit = ii;
                }


            }
            MainActivity.linesbaby[3][i] = (int) MainActivity.nodesbaby[1][pointersource];
            MainActivity.linesbaby[4][i] = MainActivity.finalbitch[2][i];

            MainActivity.linesbaby[5][i] = (int) MainActivity.nodesbaby[1][pointertargit];

            MainActivity.linesbaby[6][i] = MainActivity.finalbitch[3][i];

            MainActivity.linesbaby[7][i] = MainActivity.finalbitch[4][i];


        }
       // MainActivity.geno = new int[8]

        //testing.append(linesarray[7][21]+"\n");
//        double pp=0;
//        double tt;
//        tt =  Math.sqrt(Math.pow((linesarray[2][314]- linesarray[5][314]), 2) + Math.pow((linesarray[3][314] -  linesarray[6][314]), 2));
//        testing.append(tt+"  tt without\n");
//        testing.append(linesarray[7][314]+"  real\n");
//        double oo =0;
//        //  longaxis = longaxis / 2.5;
//        int ttt;
//        ttt = (int) (tt * 100000);
//        //oppo = (int) (longaxis * 10000000);
//        // longaxis = (oppo * 1.0) / 10000000;
//
//        testing.append(ttt+" final real\n");
//
//
//
//
////                int tt = Math.sqrt(Math.pow(( linesarray[2][21]- nodesarray[1][i]), 2) + Math.pow((linesarray[3][21]; - nodesarray[2][i]), 2));
//////
//////                if (shortdist > tempholder) {
//////                    idholder = i;
//////                    shortdist = tempholder;
//////                }
//////
//////
//////
//////
//////                linesarray[5][21];
//////                linesarray[6][21];

//

        MainActivity.createPolylineGraphics(MainActivity.finalbitch);



        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        for (int i = 0; i < MainActivity.jj; i++) {
            Vertex locationn = new Vertex("" + i, "Node_" + i);
            nodes.add(locationn);
        }
        // textView.append("\n");
        int o = 0;
        for (int j = 0; j < MainActivity.newlinescounter; j++) {
            if (MainActivity.linesbaby[7][j] == 1) {
                double tt2 =  Math.sqrt(Math.pow((MainActivity.pointstemp[1][MainActivity.linesbaby[4][j]-1]- MainActivity.pointstemp[1][MainActivity.into]), 2) + Math.pow((MainActivity.pointstemp[2][MainActivity.linesbaby[4][j]-1] -  MainActivity.pointstemp[2][MainActivity.into]), 2));
                int ttt2 = (int) (tt2 * 100000);
                int des=1;
                des = MainActivity.linesbaby[6][j] + ttt2;
                des= (int) (des/2.3);
                addLane("Edge_" + o, MainActivity.linesbaby[3][j] - 1, MainActivity.linesbaby[5][j] - 1, des);
                o++;

            }else{
          //double tt1 =  Math.sqrt(Math.pow((MainActivity.pointstemp[1][MainActivity.linesbaby[2][j]-1]- MainActivity.pointstemp[1][MainActivity.into]), 2) + Math.pow((MainActivity.pointstemp[2][MainActivity.linesbaby[2][j]-1] -  MainActivity.pointstemp[2][MainActivity.into]), 2));
          double tt2 =  Math.sqrt(Math.pow((MainActivity.pointstemp[1][MainActivity.linesbaby[4][j]-1]- MainActivity.pointstemp[1][MainActivity.into]), 2) + Math.pow((MainActivity.pointstemp[2][MainActivity.linesbaby[4][j]-1] -  MainActivity.pointstemp[2][MainActivity.into]), 2));
           int ttt2 = (int) (tt2 * 100000);
                int des=1;
               des = MainActivity.linesbaby[6][j] + ttt2;

            addLane("Edge_" + o, MainActivity.linesbaby[3][j] - 1, MainActivity.linesbaby[5][j] - 1,  des);
            o++;
        }}


        for (int j = 0; j < MainActivity.newlinescounter; j++) {
            // textView.append(linesbaby[7][j]+"");
            if (MainActivity.linesbaby[7][j] == 0 || MainActivity.linesbaby[7][j] == 2) {
                double tt2 =  Math.sqrt(Math.pow((MainActivity.pointstemp[1][MainActivity.linesbaby[4][j]-1]- MainActivity.pointstemp[1][MainActivity.into]), 2) + Math.pow((MainActivity.pointstemp[2][MainActivity.linesbaby[4][j]-1] -  MainActivity.pointstemp[2][MainActivity.into]), 2));
                int ttt2 = (int) (tt2 * 100000);
                int des=1;
                des = MainActivity.linesbaby[6][j] + ttt2;
                addLane("Edge_" + o, MainActivity.linesbaby[5][j] - 1, MainActivity.linesbaby[3][j] - 1,des);
                o++;
            }
        }

        int s = 0, df = 0;
        for (int i = 0; i < MainActivity.jj; i++) {

            if (MainActivity.nodesbaby[0][i] == MainActivity.from) {

                s = i;

            }
            if (MainActivity.nodesbaby[0][i] == MainActivity.into) {

                df = i;

            }


        }
        long startTime = System.nanoTime();

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(s));

        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(df));

//+++import java.util.LinkedList;

        int kk9 = 0;
        boolean gg = false;
        for (Vertex v : path) {
            kk9++;
            if (v.getId() == "") {
                gg = true;
            }
        }


        int[] pathth = new int[kk9++];
        MainActivity.uu = 0;
        for (Vertex vertex : path) {


            //  MainActivity.textView.append(vertex.getId() + " ");

            pathth[MainActivity.uu] = Integer.parseInt(vertex.getId());
            MainActivity.uu++;


        }
        MainActivity.pathtohell = new int[MainActivity.uu];
        int distance = 0;
        for (int i = 0; i < MainActivity.uu; i++) {
            MainActivity.pathtohell[i] = (int) MainActivity.nodesbaby[0][pathth[i]];


        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        MainActivity.time = duration;
        MainActivity.createPolylineGraphicspath( MainActivity.pathtohell);


      // MainActivity.getDistance( MainActivity.pathtohell);
       // getDistance(MainActivity.pathtohell);

    }

}

