package com.example.AidMe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import com.example.fuckboy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
public DrawerLayout drawer;
public NavigationView navigationView;
    public Toolbar toolbar;

   public static TextView textView;
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

    int frompoint, topoint;
    public static GraphicsOverlay mGraphicsOverlay;
    public static int newlinescounter = 0;
   public static int[][] linesbaby;
    int[][] finalbitch;
    String[] u;
    double[] lines;
    double[] lined;
    double[] linec;
  public static  double[][] nodesarray;
    double[][] linesarray;
    public LocationListener loclisn;
    public LocationManager locman;
    double nodelon = 0;
    double nodelat = 0;
    double[] fuckbox1 = new double[2];
    double[] fuckbox2 = new double[2];
    boolean lonfuckbox1isbigger = false;
    boolean latfuckbox1isbigger = false;
    boolean br = false;
    double biglon;
    double smalllon;
    double biglat;
    double smalllat;
    int max;
    int k9 = 0;
    int ss = 0;
    public static int jj = 0;
   public static double[][] nodesbaby;
    int com1;
    int com2;
   public static int from = 0, into = 0;
    double[][] pointstemp = new double[3][7313];
    int[][] tempfmaplines = new int[5][10000];
    boolean workdone = false;
    //int[] finalnodes;
    int k1 = 0, k2 = 0, k3 = 0, k4 = 0, k5 = 0;
public static final String TAG ="MainActivity";
    private FusedLocationProviderClient fusedLocationClient;

    protected void onCreate (Bundle savedInstanceState) {

     //   from = 2756;
     //   into = 3542;

        super.onCreate(savedInstanceState);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        setContentView(R.layout.activity_main);
        initViews();
setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle  = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawerOpen,R.string.drawerClose);
drawer.addDrawerListener(toggle);
toggle.syncState();

        this.textView = (TextView) findViewById(R.id.textView);
        textView.setText("");
        // *** ADD ***
        mMapView = findViewById(R.id.mapView);
        setupMap();


        setupLocationDisplay();

        databaseAccess.open();
        nodesarray = databaseAccess.getnodes();
        linesarray = databaseAccess.getLines();
        DijkstraAlgorithm d;
        databaseAccess.close();

//      createGraphics();

        createGraphicsOverlay();


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override

            public void onSuccess(Location location) {

                realLon = location.getLongitude();
                realLat = location.getLatitude();

                clashortdis();
                get90d();
                startThread();
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                }
            }
        });

        int cc = 0;


    }
   public void  initViews(){

       Log.d(TAG,"intViews: started");
       drawer = (DrawerLayout)findViewById(R.id.drawer);
       navigationView = (NavigationView)findViewById(R.id.navigation_view);
       toolbar= (Toolbar)findViewById(R.id.toolbar);
navigationView.setNavigationItemSelectedListener(this);

    }
public void startThread(){
        process p = new process();
        p.start();

}
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

    private void createPolylineGraphics(int[][] bitch) {


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

    public void get90d() {
//


        textView.append(frompoint + "\n");
        textView.append(topoint + "\n");
        fuckbox1[0] = nodesarray[1][from - 1];
        fuckbox1[1] = nodesarray[2][from - 1];

        fuckbox2[0] = nodesarray[1][into - 1];
        fuckbox2[1] = nodesarray[2][into - 1];


        if (fuckbox1[0] >= fuckbox2[0]) {

            lonfuckbox1isbigger = true;
        }
        if (fuckbox1[1] >= fuckbox2[1]) {

            latfuckbox1isbigger = true;
        }

        if (lonfuckbox1isbigger) {
            biglon = fuckbox1[0];
            smalllon = fuckbox2[0];
        } else {
            smalllon = fuckbox1[0];
            biglon = fuckbox2[0];
        }

        if (latfuckbox1isbigger) {
            biglat = fuckbox1[1];
            smalllat = fuckbox2[1];
        } else {
            smalllat = fuckbox1[1];
            biglat = fuckbox2[1];
        }
        double longaxis = 0;
        int oppo = 0;
        if ((biglat - smalllat) < 0.0037) {
            longaxis = biglon - smalllon;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            longaxis = longaxis / 2.5;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            biglat = biglat + (longaxis);
            smalllat = smalllat - (longaxis);

            oppo = (int) (biglat * 10000000);
            biglat = (oppo * 1.0) / 10000000;
            oppo = (int) (smalllat * 10000000);
            smalllat = (oppo * 1.0) / 10000000;

        }

        if ((biglon - smalllon) < 0.0037) {
            longaxis = biglat - smalllat;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;
            longaxis = longaxis / 2.5;
            oppo = (int) (longaxis * 10000000);
            longaxis = (oppo * 1.0) / 10000000;

            biglon = biglon + (longaxis);
            smalllon = smalllon - (longaxis);

            oppo = (int) (biglon * 10000000);
            biglon = (oppo * 1.0) / 10000000;

            oppo = (int) (smalllon * 10000000);
            smalllon = (oppo * 1.0) / 10000000;

        }
        biglat = biglat + 0.001;
        biglon = biglon + 0.001;
        smalllon = smalllon - 0.001;
        smalllat = smalllat - 0.001;


        for (int i1 = 0; i1 < 7313; i1++) {

            com1 = Double.compare(nodesarray[1][i1], biglon);
            com2 = Double.compare(nodesarray[1][i1], smalllon);

            if (com1 > 0 || com2 < 0) {
                br = true;
            } else {
                k2++;
                com1 = 0;
                com2 = 0;
                com1 = Double.compare(nodesarray[2][i1], biglat);
                com2 = Double.compare(nodesarray[2][i1], smalllat);
                if (com1 > 0 || com2 < 0) {
                    br = true;
                } else {
                    pointstemp[0][jj] = nodesarray[0][i1];
                    pointstemp[1][jj] = nodesarray[1][i1];
                    pointstemp[2][jj] = nodesarray[2][i1];


                    jj++;
                }

            }
        }


        boolean exiest = false;
        for (int i = 0; i < jj; i++) {
            for (int ii = 0; ii < 10869; ii++) {
                if (linesarray[0][i] <= 10868) {
                    if (linesarray[1][ii] == pointstemp[0][i] || linesarray[4][ii] == pointstemp[0][i]) {
                        if (linescounter > 0) {
                            for (int k = 0; k < linescounter; k++) {
                                if (tempfmaplines[0][k] == (int) linesarray[0][ii]) {
                                    exiest = true;
                                }


                            }
                            if (exiest == false) {
                                tempfmaplines[0][linescounter] = (int) linesarray[0][ii];
                                tempfmaplines[1][linescounter] = (int) linesarray[1][ii];
                                tempfmaplines[2][linescounter] = (int) linesarray[4][ii];
                                tempfmaplines[3][linescounter] = (int) linesarray[7][ii];
                                tempfmaplines[4][linescounter] = (int) linesarray[8][ii];

                                linescounter++;
                            }
                            exiest = false;

                        } else {

                            tempfmaplines[0][linescounter] = (int) linesarray[0][ii];
                            tempfmaplines[1][linescounter] = (int) linesarray[1][ii];
                            tempfmaplines[2][linescounter] = (int) linesarray[4][ii];
                            tempfmaplines[3][linescounter] = (int) linesarray[7][ii];
                            tempfmaplines[4][linescounter] = (int) linesarray[8][ii];

                            linescounter++;
                        }

                    }
                }


            }


        }

        int[][] tempfmaplines2 = new int[5][linescounter];

        boolean belong1 = false;
        boolean belong2 = false;
        boolean be1 = false;
        boolean be2 = false;


        for (int i = 0; i < linescounter; i++) {
            if (tempfmaplines[1][i] == from || tempfmaplines[2][i] == from || tempfmaplines[1][i] == into || tempfmaplines[2][i] == into) {

                tempfmaplines2[0][newlinescounter] = tempfmaplines[0][i];
                tempfmaplines2[1][newlinescounter] = tempfmaplines[1][i];
                tempfmaplines2[2][newlinescounter] = tempfmaplines[2][i];
                tempfmaplines2[3][newlinescounter] = tempfmaplines[3][i];
                tempfmaplines2[4][newlinescounter] = tempfmaplines[4][i];

                newlinescounter++;


            } else {
                for (int ii = 0; ii < jj; ii++) {
                    if (tempfmaplines[1][i] == pointstemp[0][ii]) {
                        belong1 = true;

                    }
                    if (tempfmaplines[2][i] == pointstemp[0][ii]) {
                        belong2 = true;

                    }

                }
                if (belong1 == true && belong2 == true) {
                    tempfmaplines2[0][newlinescounter] = tempfmaplines[0][i];
                    tempfmaplines2[1][newlinescounter] = tempfmaplines[1][i];
                    tempfmaplines2[2][newlinescounter] = tempfmaplines[2][i];
                    tempfmaplines2[3][newlinescounter] = tempfmaplines[3][i];
                    tempfmaplines2[4][newlinescounter] = tempfmaplines[4][i];

                    newlinescounter++;
                }
                belong1 = false;
                belong2 = false;
            }
        }


        int over = 0;
        boolean voerbool = false;
        finalbitch = new int[5][newlinescounter];
        for (int i = 0; i < newlinescounter; i++) {
            finalbitch[0][i] = tempfmaplines2[0][i];
            finalbitch[1][i] = tempfmaplines2[1][i];
            finalbitch[2][i] = tempfmaplines2[2][i];
            finalbitch[3][i] = tempfmaplines2[3][i];
            finalbitch[4][i] = tempfmaplines2[4][i];

            
//            if (tempfmaplines2[0][i] >= 10530) {
//
//                voerbool = true;
//                over++;
//
//
//            }
//        }
//        if (voerbool) {
//            textView.append("over shit\n");
//            textView.append(over + "\n");
//        }
        double x, xx, y, yy;


        createPolylineGraphics(finalbitch);


        int max;
        int k9 = 0;
        int ss = 0;

        max = (int) pointstemp[0][0];
        for (int i = 1; i <= jj; i++) {
            if (max < pointstemp[0][i]) {

                max = (int) pointstemp[0][i];

            }
        }


        boolean pointitis1 = false, pointitis2 = false;
        for (int ii = 0; ii < newlinescounter; ii++) {

            for (int iii = 0; iii < jj; iii++) {
                if (finalbitch[1][ii] == pointstemp[0][iii]) {
                    pointitis1 = true;
                }
                if (finalbitch[2][ii] == pointstemp[0][iii]) {
                    pointitis2 = true;
                }

            }

            if (pointitis1 == false) {

                pointstemp[0][jj] = nodesarray[0][finalbitch[1][ii] - 1];
                pointstemp[1][jj] = nodesarray[1][finalbitch[1][ii] - 1];
                pointstemp[2][jj] = nodesarray[2][finalbitch[1][ii] - 1];
                jj++;

            }
            if (pointitis2 == false) {

                pointstemp[0][jj] = nodesarray[0][finalbitch[2][ii] - 1];
                pointstemp[1][jj] = nodesarray[1][finalbitch[2][ii] - 1];
                pointstemp[2][jj] = nodesarray[2][finalbitch[2][ii] - 1];
                jj++;

            }
            pointitis1 = false;
            pointitis2 = false;

        }


        textView.append(jj + "\n");

        textView.append(newlinescounter + "\n");
        nodesbaby = new double[4][jj];
        for (int i = 0; i < jj; i++) {

            nodesbaby[0][i] = pointstemp[0][i];
            nodesbaby[1][i] = i + 1;
            nodesbaby[2][i] = pointstemp[1][i];
            nodesbaby[3][i] = pointstemp[2][i];


        }
        linesbaby = new int[8][newlinescounter];
        int pointersource = 0, pointertargit = 0;
// 0 = db id , 1 new baby id , 2 source id , 3 source baby id , 4 targit , 5 baby targit, the distance
        for (int i = 0; i < newlinescounter; i++) {


            linesbaby[0][i] = finalbitch[0][i];
            linesbaby[1][i] = i + 1;
            linesbaby[2][i] = finalbitch[1][i];
            for (int ii = 0; ii < jj; ii++) {
                if (finalbitch[1][i] == nodesbaby[0][ii]) {
                    pointersource = ii;
                }
                if (finalbitch[2][i] == nodesbaby[0][ii]) {
                    pointertargit = ii;
                }


            }
            linesbaby[3][i] = (int) nodesbaby[1][pointersource];
            linesbaby[4][i] = finalbitch[2][i];

            linesbaby[5][i] = (int) nodesbaby[1][pointertargit];

            linesbaby[6][i] = finalbitch[3][i];

            linesbaby[7][i] = finalbitch[4][i];


        }


//        nodes = new ArrayList<Vertex>();
//        edges = new ArrayList<Edge>();
//        for (int i = 0; i < jj; i++) {
//            Vertex locationn = new Vertex("" + i, "Node_" + i);
//            nodes.add(locationn);
//        }
//        textView.append("\n");
//        int o = 0;
//        for (int j = 0; j < newlinescounter; j++) {
//            addLane("Edge_" + o, linesbaby[3][j] - 1, linesbaby[5][j] - 1, linesbaby[6][j]);
//            o++;
//        }
//
//
//        for (int j = 0; j < newlinescounter; j++) {
//            // textView.append(linesbaby[7][j]+"");
//            if (linesbaby[7][j] == 0 || linesbaby[7][j] == 2) {
//                addLane("Edge_" + o, linesbaby[5][j] - 1, linesbaby[3][j] - 1, linesbaby[6][j]);
//                o++;
//            }
//        }
//
//        int s = 0, df = 0;
//        for (int i = 0; i < jj; i++) {
//
//            if (nodesbaby[0][i] == from) {
//
//                s = i;
//
//            }
//            if (nodesbaby[0][i] == into) {
//
//                df = i;
//
//            }
//
//
//        }
//
//
//        Graph graph = new Graph(nodes, edges);
//        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
//        dijkstra.execute(nodes.get(s));
//
//        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(df));
////+++import java.util.LinkedList;
//
//        int kk9 = 0;
//        for (Vertex v : path) {
//            kk9++;
//        }
//
//
//        int[] pathth = new int[kk9++];
//        int u = 0;
//        for (Vertex vertex : path) {
//
//
//            textView.append(vertex.getId() + " ");
//            pathth[u] = Integer.parseInt(vertex.getId());
//            u++;
//
//
//        }
//        int[] pathtohell = new int[u];
//
//        for (int i = 0; i < u; i++) {
//            pathtohell[i] = (int) nodesbaby[0][pathth[i]];
//
//
//        }
//
//
//        createPolylineGraphicspath(pathtohell);


    }


    public void clashortdis() {

        double shortdist = 10000;
        int idholder = 0;
        double tempholder = 0;

        for (int i = 0; i < 7313; i++) {
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

        double[][] hosp = new double[3][4];
        hosp[0][0] = nodesarray[0][5906 - 1];
        hosp[1][0] = nodesarray[1][5906 - 1];
        hosp[2][0] = nodesarray[2][5906 - 1];

        hosp[0][1] = nodesarray[0][2756 - 1];
        hosp[1][1] = nodesarray[1][2756 - 1];
        hosp[2][1] = nodesarray[2][2756 - 1];

        hosp[0][2] = nodesarray[0][1126 - 1];
        hosp[1][2] = nodesarray[1][1126 - 1];
        hosp[2][2] = nodesarray[2][1126 - 1];

        hosp[2][3] = nodesarray[0][2802 - 1];
        hosp[2][3] = nodesarray[1][2802 - 1];
        hosp[2][3] = nodesarray[2][2802 - 1];
        double tempholder2;
        double shortdist2 = 10000;
        int idholder2 = 0;
        for (int i = 0; i < 4; i++) {

            tempholder2 = Math.sqrt(Math.pow((nodesarray[1][frompoint] - hosp[1][i]), 2) + Math.pow((nodesarray[2][frompoint] - hosp[2][i]), 2));

            if (shortdist2 > tempholder2) {
                idholder2 = i;
                shortdist2 = tempholder2;
            }


        }

        frompoint = idholder + 1;
        topoint = idholder2 - 1;
from = frompoint;

        topoint = (int) hosp[0][topoint];
into = topoint;
        createPointGraphics();

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

        if(item.getItemId()==R.id.first){
            textView.append("first is selected\n");
            drawer.closeDrawer(Gravity.LEFT);
        }else if(item.getItemId()==R.id.second){
            textView.append("second is selected\n");
            drawer.closeDrawer(Gravity.LEFT);

        }else if(item.getItemId()==R.id.thired){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            drawer.closeDrawer(Gravity.LEFT);

            startActivity(callIntent);


        }else if(item.getItemId()==R.id.forth){
            textView.append("forth is selected\n");
            drawer.closeDrawer(Gravity.LEFT);

        }
//        switch (item.getItemId()){
//           case R.id.first:{
//             textView.append("first is selected");
//              Toast.makeText(getApplicationContext(),"First is selected",Toast.LENGTH_SHORT).show();}
//           case R.id.second:{
//               textView.append("second is selected");
//
//               Toast.makeText(getApplicationContext(),"second is selected",Toast.LENGTH_SHORT).show();}
//           case R.id.thired:{
//               textView.append("third is selected");
//
//               Toast.makeText(getApplicationContext(),"thired is selected",Toast.LENGTH_SHORT).show();}
//           case R.id.forth:{
//               textView.append("fourth is selected");
//
//               Toast.makeText(getApplicationContext(),"forth is selected",Toast.LENGTH_SHORT).show();}
//
//       }
        return true ;
    }
}

class process extends Thread {
    public List<Vertex> nodes;
    public List<Edge> edges;
   // int jj;
//    int[][] linesbaby;
//    int newlinescounter;
//    double[][] nodesbaby;
//    int from;
//    int into;
//    process( int newlinescounter, int[][] linesbaby, double[][] nodesbaby, int from, int into) {
//       // this.jj = jj;
//        this.newlinescounter = newlinescounter;
//        this.linesbaby = linesbaby;
//        this.nodesbaby = nodesbaby;
//        this.from = from;
//        this.into = into;
//    }

    public void addLane(String laneId, int sourceLocNo, int destLocNo,
                        int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }

    public void run() {

        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        for (int i = 0; i < MainActivity.jj; i++) {
            Vertex locationn = new Vertex("" + i, "Node_" + i);
            nodes.add(locationn);
        }
        // textView.append("\n");
        int o = 0;
        for (int j = 0; j < MainActivity.newlinescounter; j++) {
            addLane("Edge_" + o, MainActivity.linesbaby[3][j] - 1, MainActivity.linesbaby[5][j] - 1,MainActivity. linesbaby[6][j]);
            o++;
        }


        for (int j = 0; j < MainActivity.newlinescounter; j++) {
            // textView.append(linesbaby[7][j]+"");
            if (MainActivity.linesbaby[7][j] == 0 || MainActivity.linesbaby[7][j] == 2) {
                addLane("Edge_" + o, MainActivity.linesbaby[5][j] - 1, MainActivity.linesbaby[3][j] - 1, MainActivity.linesbaby[6][j]);
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


        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(s));

        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(df));
//+++import java.util.LinkedList;

        int kk9 = 0;
        for (Vertex v : path) {
            kk9++;
        }


        int[] pathth = new int[kk9++];
        int u = 0;
        for (Vertex vertex : path) {


           //  MainActivity.textView.append(vertex.getId() + " ");
            pathth[u] = Integer.parseInt(vertex.getId());
            u++;


        }
        int[] pathtohell = new int[u];

        for (int i = 0; i < u; i++) {
            pathtohell[i] = (int) MainActivity.nodesbaby[0][pathth[i]];


        }

        MainActivity.createPolylineGraphicspath(pathtohell);

    }


}