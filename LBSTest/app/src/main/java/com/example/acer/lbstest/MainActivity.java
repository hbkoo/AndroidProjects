package com.example.acer.lbstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.overlayutil.*;

//import com.baidu.mapapi.overlayutil.BusLineOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private EditText station_end, station_start,city_et,passby_et;
    private Button search_button,bus_search_btn;
    private Button busline_btn;
    private Button last_line,next_line;
    private Button addStation_btn;

    private String addStation;

    private int Lines = 0,line = 0;

    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;


    private DrivingRouteResult result;

    private List<TransitRouteLine.TransitStep> stepList = new ArrayList<>();

    private String CITY = "武汉";
 //   private MKSearch mkSearch = null;


    PoiSearch busPoiSearch;//公交地铁的Poi搜索对象
    BusLineSearch busLineSearch;//公交检索对象

    private List<BusLineResult.BusStation> busStations = new ArrayList<>();
    private List<BusLineResult.BusStep> busSteps = new ArrayList<>();
    private BusLineResult busLine = new BusLineResult();


    RoutePlanSearch routePlanSearch = null;

    List<PlanNode> list = new ArrayList<>();

    PlanNode Node = null;

    private static LatLng latLng;

    private Marker marker;
    StringBuilder builder = new StringBuilder();

    RouteNode node = new RouteNode();
    PoiSearch poiSearch = null;

    static public List<String> stringList = new ArrayList<>();

    private TransitRoutePlanOption transoption;
    static public int tag;//标记
    static public int allNum;

    static public Context context;

    private IntentFilter intentFilter = new IntentFilter();
    private DrawLineReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        context = this;

        passby_et = (EditText) findViewById(R.id.passby_et);
        addStation_btn = (Button) findViewById(R.id.add_button);
        last_line = (Button) findViewById(R.id.last_line);
        next_line = (Button) findViewById(R.id.next_line);
        city_et = (EditText) findViewById(R.id.city_et);
        station_end = (EditText) findViewById(R.id.location_end);
        station_start = (EditText) findViewById(R.id.location_start);
        search_button = (Button) findViewById(R.id.search_button);
        bus_search_btn = (Button) findViewById(R.id.bus_search);
        busline_btn = (Button) findViewById(R.id.buslin_search);
        positionText = (TextView) findViewById(R.id.position_TextView);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);

        this.setTitle("路线规划");
        //poi搜索对象的创建
//        poiSearch =  PoiSearch.newInstance();
        //公交检索对象的创建
//        busLineSearch = BusLineSearch.newInstance();

        //路线规划对象
        routePlanSearch = RoutePlanSearch.newInstance();
        busPoiSearch = PoiSearch.newInstance();


        //公交检索对象
        busLineSearch = BusLineSearch.newInstance();


//        poiSearch.searchInCity((new PoiCitySearchOption()).city("武汉")
//                .keyword("540"));

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.clear();
                list.clear();
                drive();
            }
        });
        bus_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransLine();
            }
        });
        busline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBusLine();
            }
        });
        last_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLast_line();
            }
        });
        next_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNext_line();
            }
        });
        addStation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStation();
            }
        });

        permission();

        InitListener();//初始化驾车、公交路线规划、步行监听
        InitBusLineLiatener();//初始化公交路线规划监听
        InitPoiBusListener();//初始化Poi公交规划监听


    }

    /**公交路线搜索,步骤：
    1.先发起一个poi检索，(新建一个Poi检索对象)
     2.在poi的检索结果中发起公交搜索
    3.在公交检索搜索结果中获取数据*/

    public void selectBusLine() {
        String key = passby_et.getText().toString();
        String start = station_start.getText().toString();
        String end = station_end.getText().toString();

//        BusLineSearchOption busLineOptions = new BusLineSearchOption();
//        busLineOptions.city(CITY).uid(start).uid(end);
//        busLineSearch.searchBusLine(busLineOptions);


        //城市内的公交、地铁路线检索
        //首先发起POI检索，查找是否为公交路线，如果是则再发起公交路线检索
        PoiCitySearchOption poiCitySearchOptions = new PoiCitySearchOption();
        poiCitySearchOptions.city(CITY).keyword(key);//城市检索的数据设置
        busPoiSearch.searchInCity(poiCitySearchOptions);

    }

    //公交路线规划监听
    public void InitBusLineLiatener() {
        busLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                Toast.makeText(MainActivity.this,"搜索成功",Toast.LENGTH_SHORT).show();
                BusLineOverlay busLineOverlay = new BusLineOverlay(baiduMap);

                busLineResult.setStations(busStations);
                //设置数据,这里只需要一步，
                busLineOverlay.setData(busLineResult);
                //添加到地图
                busLineOverlay.addToMap();
                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                busLineOverlay.zoomToSpan();//计算工具
                // 设置标记物的点击监听事件
                baiduMap.setOnMarkerClickListener(busLineOverlay);
            }
        });
    }

    //Poi公交规划监听者
    public void InitPoiBusListener() {
        busPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //baiduMap.clear();//清除标志
                // 如果没有错误
                if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
                    //遍历所有数据
                    for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                        //获取里面的数据对象
                        PoiInfo poiInfo = poiResult.getAllPoi().get(i);

                        if (poiInfo.type == PoiInfo.POITYPE.BUS_STATION){

                            latLng = poiInfo.location;
                            BusLineResult.BusStation busStation = new BusLineResult.BusStation();
                            busStation.setLocation(latLng);

                            busStations.add(busStation);

                            Node = PlanNode.withLocation(latLng);
                            list.add(Node);

                            DrawLocation(Node);

                            Toast.makeText(MainActivity.this,String.valueOf(Node.getLocation().latitude),Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //判断检索到的点的类型是不是公交路线或地铁路线
                        if (poiInfo.type == PoiInfo.POITYPE.BUS_LINE ||
                                poiInfo.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                            //发起公交检索
                            Toast.makeText(MainActivity.this,poiInfo.uid,Toast.LENGTH_SHORT).show();
                            BusLineSearchOption busLineOptions = new BusLineSearchOption();
                            busLineOptions.city(CITY).uid(poiInfo.uid);
                            busLineSearch.searchBusLine(busLineOptions);
                        }
                    }

                    return;
                } else {
                    Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getApplication(), "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {// 正常返回结果的时候，此处可以获得很多相关信息
                    Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                    + poiDetailResult.getAddress(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
        });
    }

    //标出当前位置
    public void DrawLocation(PlanNode Node) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(Node.getLocation())
                .icon(bitmap);
        //在地图上添加Marker，并显示
        marker = (Marker) (baiduMap.addOverlay(option));
        //1-20级 20级室内地图
        MapStatusUpdate mapStatusUpdate =
                MapStatusUpdateFactory.newLatLngZoom(Node.getLocation(), 19);
        baiduMap.setMapStatus(mapStatusUpdate);
    }


    //地理编码
    public void GetLocation(String name) {
        //新建编码查询对象
        final String Name = name;
        GeoCoder geoCoder = GeoCoder.newInstance();
        //新建查询对象要查询的条件
        GeoCodeOption GeoOtption = new GeoCodeOption().city(CITY).address(name);


        //发起地理编码请求
        geoCoder.geocode(GeoOtption);
        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null
                        || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(MainActivity.this,Name + "\n没有找到!",Toast.LENGTH_SHORT).show();
                }
                latLng = geoCodeResult.getLocation();
                Node = PlanNode.withLocation(latLng);
                list.add(Node);

//                Node = PlanNode.withCityNameAndPlaceName(CITY,Name);
//                list.add(Node);

                DrawLocation(Node);

                builder.delete(0,builder.length());
                builder.append(geoCodeResult.getAddress()).append(latLng.latitude).append(" ").
                        append(latLng.longitude).append("\n");
                Toast.makeText(MainActivity.this,builder.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
    }

    //添加经过的站点
    public void AddStation() {
        addStation = passby_et.getText().toString();
        if("".equals(addStation)) {
            Toast.makeText(MainActivity.this,"请输入要添加的站点！",Toast.LENGTH_SHORT).show();
            return;
        }
        stringList.add(addStation);
        builder.delete(0,builder.length());
        Node = PlanNode.withCityNameAndPlaceName(CITY,addStation + "公交站点");
        list.add(Node);
        //DrawLocation(Node);
        GetLocation(addStation + "公交站点");
        passby_et.setText("");

//        LatLng latLng1 = Node.getLocation();
//        builder.append("经度：").append(latLng1.latitude).append("\n")
//                .append("纬度：").append(latLng1.longitude);
//        Toast.makeText(MainActivity.this,builder.toString(),Toast.LENGTH_SHORT).show();
//        GeoCoder geoCoder = GeoCoder.newInstance();
//        ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(latLng1);
//        geoCoder.reverseGeoCode(options);
//        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//            }
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result1) {
//                if (result1 == null || result1.error != SearchResult.ERRORNO.NO_ERROR) {
//                    return;
//                }
//                if (result1 != null && result1.error == SearchResult.ERRORNO.NO_ERROR) {
//
//                    //得到位置
//
//                    builder.append(result1.getAddress());
//                    builder.append("区：").append(result1.getAddressDetail().district).append("\n")
//                            .append("街道：").append(result1.getAddressDetail().street).append("\n");
//                    Toast.makeText(MainActivity.this,builder.toString(),Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//        });


    }

    //驾车、步行路线规划监听者
    public void InitListener() {
        transoption = new TransitRoutePlanOption();
        intentFilter.addAction("com.example.acer.lbstest");
        receiver = new DrawLineReceiver();
        registerReceiver(receiver,intentFilter);

        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            //步行路线结果回调
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                baiduMap.clear();
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(baiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(walkingOverlay);
                    Log.e("TAG", walkingOverlay.getOverlayOptions() + "");

                } else {
                    Toast.makeText(getBaseContext(), "搜不到！", Toast.LENGTH_SHORT).show();
                }
            }

            //换乘线结果回调
            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                tag++;
                if(transitRouteResult == null
                        || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MainActivity.this, list.get(tag-1) + "站到" + list.get(tag) +
                                    "站的路线未找到\n司机可以在地图上查看自行规划路线",
                            Toast.LENGTH_LONG).show();
                    if(tag < list.size()-1) {
                        Intent intent = new Intent("com.example.acer.bus1.Class.drawLine");
                        sendBroadcast(intent);
                    } else {
                        //progressDialog.dismiss();
                        unregisterReceiver(receiver);
                    }
                    return ;
                }
                if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteOverlay transitRouteOverlay = new TransitRouteOverlay(baiduMap);
                    transitRouteOverlay.setData(transitRouteResult.getRouteLines().get(0),stepList);
                    //if(tag +1 == list.size()) {

                        int totalLine = transitRouteResult.getRouteLines().size();
                        Lines = totalLine;
                        Toast.makeText(getBaseContext(),
                                "Hello！共查询出" + totalLine + "条符合条件的线路\n步数为:"+
                                        stepList.size() + "\ntag=" + tag,
                                Toast.LENGTH_LONG).show();


                        baiduMap.setOnMarkerClickListener(transitRouteOverlay);

                        transitRouteOverlay.addToMap();
                        //transitRouteOverlay.drawToMap(stepList);
                        transitRouteOverlay.zoomToSpan();


                    if (tag < list.size()-1) {
                        Intent intent = new Intent("com.example.acer.lbstest");
                        sendBroadcast(intent);
                    } else {
                        //progressDialog.dismiss();
                        unregisterReceiver(receiver);
                    }

                        //list.clear();
                    //}

                }
            }

            //跨城公共交通路线结果回调
            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {}

            //驾车路线结果回调
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                baiduMap.clear();//清除图标或路线
                if (drivingRouteResult == null
                        || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getBaseContext(), "sorry，抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                    line = Lines = 0;
                    list.clear();
                    last_line.setEnabled(false);
                    next_line.setEnabled(false);
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    line = Lines = 0;
                    result = drivingRouteResult;

                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                    baiduMap);
                    drivingRouteOverlay.setData(drivingRouteResult.getRouteLines().get(0));// 设置一条驾车路线方案
                    baiduMap.setOnMarkerClickListener(drivingRouteOverlay);
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int totalLine = drivingRouteResult.getRouteLines().size();
                    Lines = totalLine;
                    Toast.makeText(getBaseContext(),
                            "Hello！共查询出" + totalLine + "条符合条件的线路", Toast.LENGTH_LONG).show();

                    if(Lines != 0){
                        last_line.setEnabled(true);
                        next_line.setEnabled(true);
                        list.clear();
                    }
                    // 通过getTaxiInfo()可以得到很多关于打车的信息
//                    Toast.makeText(getBaseContext(),"该路线打车总路程"+ drivingRouteResult.getTaxiInfo()
//                                    .getDistance(), Toast.LENGTH_LONG).show();
                }
            }


            //室内路线规划回调
            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            // 骑行路线结果回调
            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }

    //公交路线规划
    public void TransLine() {
        //transitRouteResult
        //list.clear();
        allNum = list.size();
        if(!"".equals(city_et.getText().toString()))
            CITY = city_et.getText().toString();

        String location_start = station_start.getText().toString();
        String location_end = station_end.getText().toString();
 //       final TransitRoutePlanOption transoption = new TransitRoutePlanOption();
        transoption.city(CITY);
        PlanNode fromeNode = PlanNode.withCityNameAndPlaceName(CITY, location_start + "公交站点");
        PlanNode toNode = PlanNode.withCityNameAndPlaceName(CITY,location_end + "公交站点");

        list.add(toNode);
        list.add(0,fromeNode);

        if (list.size() == 0) {
            return;
        }

        tag = 0;
        transoption.from(list.get(tag)).to(list.get(tag+1));//设置路线的节点
        transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
        routePlanSearch.transitSearch(transoption);//路线搜索

//        ReentrantLock lock = new ReentrantLock();
//        if(isFirstLocate) {
//            lock.lock();  // block until condition holds
//
//            try {
//
//            } finally {
//                lock.unlock();
//            }
//        }

        //final int a ;

//        int s;
//        for(int i = 0; i < list.size()-1;i++) {
//
//            final int a = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    transoption.from(list.get(a)).to(list.get(a+1));//设置路线的节点
//                    transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
//                    routePlanSearch.transitSearch(transoption);//路线搜索
//                }
//            }).start();
//
//
//            transoption.from(list.get(i)).to(list.get(i+1));//设置路线的节点
//            transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
//            routePlanSearch.transitSearch(transoption);//路线搜索
//
//        }
//        try {
//            final Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for(int i = 0; i < list.size()-1;i++) {
//                        transoption.from(list.get(i)).to(list.get(i+1));
//                        transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
//                        routePlanSearch.transitSearch(transoption);
//                        try {
//                            this.wait(2000);
//                        } catch (InterruptedException e1) {
//
//                        }
//                    }
//                }
//            });
//            thread.start();
//            thread.join();
//
//        } catch (InterruptedException e) {
//
//        }
//        finally {
//
//        }

//        for(int i = 0; i < list.size()-1;i++) {
//            s = 0;
//            lock.lock();
//
//            final int a = i;
//            try {
//
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        transoption.from(list.get(a)).to(list.get(a+1));
//                        transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
//                        routePlanSearch.transitSearch(transoption);
//                    }
//                });
//                thread.start();
//                thread.join();
//
//
//
//
//            } catch (Exception e) {
//            }
//            finally {
//
//                lock.unlock();
//            }
//            while (true) {
//                s++;
//                if (!lock.isLocked()) {
//                    Toast.makeText(MainActivity.this,"已经解锁!循环次数为：" + s,Toast.LENGTH_SHORT).show();
//                    break;
//                }
//            }
//        }

    }

    class DrawLineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            transoption.from(list.get(tag)).to(list.get(tag+1));//设置路线的节点
            transoption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
            routePlanSearch.transitSearch(transoption);//路线搜索
        }
    }

    //驾车路线规划
    public void drive() {

        CITY = city_et.getText().toString();
        String location_start = station_start.getText().toString();
        String location_end = station_end.getText().toString();
        final DrivingRoutePlanOption driveOption = new DrivingRoutePlanOption();
        PlanNode fromeNode = PlanNode.withCityNameAndPlaceName(CITY, location_start + "公交站点");
        PlanNode toNode = PlanNode.withCityNameAndPlaceName(CITY,location_end + "公交站点");


//        fromeNode = list.get(0);
//        toNode = list.get(list.size()-1);
        //设置路线经过的点，要放入一个PlanNode的集合对象

//        GetLocation("友谊大道铁机路公交站点");//中北路车家岭路公交站
//        //GetLocation("友谊大道才茂街公交站点");
//        GetLocation("友谊大道才华街公交站点");
//        GetLocation("徐东大街徐东一路公交站点");
//        GetLocation("徐东大街徐东村公交站点");
//        GetLocation("团结大道沙湖边公交站点");
//        GetLocation("秦园东路沙湖湾公交站点");
//        GetLocation("秦园东路沙湖公园公交站点");
//        GetLocation("秦园东路中北路口公交站点");
        //Node = PlanNode.withLocation(new LatLng(30.6065074730,114.344077666));
        //list.add(Node);
//        Node = PlanNode.withCityNameAndPlaceName("武汉","友谊大道铁机路公交站点");
//        list.add(Node);
//        Node = PlanNode.withCityNameAndPlaceName("武汉","友谊大道才华街公交站点");
//        list.add(Node);
//        GetLocation("杨园南路友谊大道");
//        GetLocation("杨园南路徐东二路");
//        GetLocation("杨园南路武铁佳苑");
//        GetLocation("团结大道公交停车场(公交站)");
//        GetLocation("徐东大街徐东村公交站");
//        GetLocation("徐东大街徐东一路公交站点");
//        GetLocation("和平大道四美塘");
        //GetLocation("才临街润园路");

        driveOption.from(fromeNode).to(toNode);
        if (list.size() != 0) {
            driveOption.passBy(list);
        }
        //设置驾车策略，避免拥堵.ECAR_AVOID_JAM
        driveOption.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);
        //发起驾车检索

        new Thread(new Runnable() {
            @Override
            public void run() {
                routePlanSearch.drivingSearch(driveOption);
            }
        }).start();


    }

    //上一条路线
    public void setLast_line() {
        if(line != 0) {
            baiduMap.clear();
            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                    baiduMap);
            drivingRouteOverlay.setData(result.getRouteLines().get(--line));// 设置一条驾车路线方案
            baiduMap.setOnMarkerClickListener(drivingRouteOverlay);
            drivingRouteOverlay.addToMap();
            drivingRouteOverlay.zoomToSpan();
        }
        else
            Toast.makeText(MainActivity.this,"已经是第一条路线了!",Toast.LENGTH_SHORT).show();
    }
    //下一条路线
    public void setNext_line() {
        if(line != Lines-1) {
            baiduMap.clear();
            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                    baiduMap);
            drivingRouteOverlay.setData(result.getRouteLines().get(++line));// 设置一条驾车路线方案
            baiduMap.setOnMarkerClickListener(drivingRouteOverlay);
            drivingRouteOverlay.addToMap();
            drivingRouteOverlay.zoomToSpan();
        }
        else
            Toast.makeText(MainActivity.this,"已经是最后一条路线了!",Toast.LENGTH_SHORT).show();
    }

    //开始定位
    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    //定位选择设置
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setNeedDeviceDirect(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
    }

    //定位监听者
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
            currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
            currentPosition.append("定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络");
            }else if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");
            }
            positionText.setText(currentPosition.toString());

            LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            RouteNode routeNode = BusLineResult.BusStation.location(ll);
            currentPosition.append("\n公交站点：").append(routeNode.getTitle());
//            Toast.makeText(MainActivity.this,currentPosition.toString(),
//                    Toast.LENGTH_SHORT).show();
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }
    }

    //定位
    private void navigateTo(BDLocation location){
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
        if (isFirstLocate) {
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll,18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
//            GetLocation("友谊大道铁机路公交站点");
//            GetLocation("友谊大道才茂街公交站点");
//            GetLocation("友谊大道才华街公交站点");
//            GetLocation("徐东大街徐东一路公交站点");
//            GetLocation("徐东大街徐东村公交站点");
//            GetLocation("团结大道沙湖边公交站点");
//            GetLocation("秦园东路沙湖湾公交站点");
//            GetLocation("秦园东路沙湖公园公交站点");
//            GetLocation("秦园东路中北路口公交站点");
//            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                    .fromResource(R.mipmap.ic_launcher);
//            //构建MarkerOption，用于在地图上添加Marker
//            OverlayOptions option = new MarkerOptions()
//                    .position(ll)
//                    .icon(bitmap);
//            //在地图上添加Marker，并显示
//            marker = (Marker) (baiduMap.addOverlay(option));
            //1-20级 20级室内地图
//            MapStatusUpdate mapStatusUpdate =
//                    MapStatusUpdateFactory.newLatLngZoom(ll, 19);
//            baiduMap.setMapStatus(mapStatusUpdate);

        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);

        //0x80ff0000
        CircleOptions circle = new CircleOptions().center(ll).fillColor(0x00B2BF).radius(100);
        baiduMap.addOverlay(circle);

    }

    //权限设置
    public void permission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
//        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission
//                .READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
//            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
//        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        } else {
            requestLocation();
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须统一所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


}


