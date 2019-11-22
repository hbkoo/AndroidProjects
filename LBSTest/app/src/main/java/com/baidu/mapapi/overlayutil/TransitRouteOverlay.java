package com.baidu.mapapi.overlayutil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.example.acer.lbstest.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示换乘路线的Overlay，自3.4.0版本起可实例化多个添加在地图中显示
 */
public class TransitRouteOverlay extends OverlayManager {

    private TransitRouteLine mRouteLine = null;

    private List<TransitRouteLine.TransitStep> busSteps = new ArrayList<>();

    /**
     * 构造函数
     * 
     * @param baiduMap
     *            该TransitRouteOverlay引用的 BaiduMap 对象
     */
    public TransitRouteOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {

        if (mRouteLine == null) {
            return null;
        }

        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();
        // step node
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {

            for (TransitRouteLine.TransitStep step : mRouteLine.getAllStep()) {
                Bundle b = new Bundle();
                b.putInt("index", MainActivity.tag-1);
//                b.putInt("index", mRouteLine.getAllStep().indexOf(step));
                if (step.getEntrance() != null) {
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getEntrance().getLocation())
                            .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b)
                            .icon(getIconForStep(step)));
                }
                if (step.getExit() != null) {
                    Bundle b1 = new Bundle();
                    b1.putInt("index", MainActivity.tag);
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getExit().getLocation())
                            .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b)
                            .icon(getIconForStep(step)));
                }
                // 最后路段绘制出口点
                if (mRouteLine.getAllStep().indexOf(step) == (mRouteLine
                        .getAllStep().size() - 1) && step.getExit() != null) {
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getExit().getLocation())
                            .anchor(0.5f, 0.5f).zIndex(10)
                            .icon(getIconForStep(step)));
                }
            }
        }

        if (MainActivity.tag == 1 && mRouteLine.getStarting() != null) {
            overlayOptionses.add((new MarkerOptions())
                    .position(mRouteLine.getAllStep().get(0).getEntrance().getLocation())
                    //.position(mRouteLine.getStarting().getLocation())
                    .icon(getStartMarker() != null ? getStartMarker() :
                            BitmapDescriptorFactory
                                    .fromAssetWithDpi("Icon_start.png")).zIndex(10));
        }
        if (MainActivity.tag+1 == MainActivity.allNum && mRouteLine.getTerminal() != null) {
            overlayOptionses
                    .add((new MarkerOptions())
                            .position(mRouteLine.getAllStep()
                            .get(mRouteLine.getAllStep().size()-1).getExit().getLocation())
                            //.position(mRouteLine.getTerminal().getLocation())
                            .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                    BitmapDescriptorFactory
                                            .fromAssetWithDpi("Icon_end.png"))
                            .zIndex(10));
        }
        // polyline
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {

            for (TransitRouteLine.TransitStep step : mRouteLine.getAllStep()) {
                if (step.getWayPoints() == null) {
                    continue;
                }
                int color = 0;
                if (step.getStepType() != TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING) {
//                    color = Color.argb(178, 0, 78, 255);
                    color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 0, 78, 255);
                } else {
                    step.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE);
//                    color = Color.argb(178, 88, 208, 0);
                    color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 88, 208, 0);
                }
                overlayOptionses.add(new PolylineOptions()
                        .points(step.getWayPoints()).width(10).color(color)
                        .zIndex(0));
            }
        }
        return overlayOptionses;
    }

    public static List<OverlayOptions> DrawMap(List<TransitRouteLine.TransitStep> stepList) {
        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();

        for (TransitRouteLine.TransitStep step : stepList) {
            if (step.getWayPoints() == null) {
                continue;
            }
//                step.setEntrace(mRouteLine.getStarting());
//                step.setExit(mRouteLine.getTerminal());

            int color1 = Color.argb(180, 0, 78, 255);
//            int color = 0;
//            if (step.getStepType() != TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING) {
////                    color = Color.argb(178, 0, 78, 255);
//                color = getLineColor() != 0 ? getLineColor() : Color.argb(180, 0, 78, 255);
//            } else {
//                //step.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE);
////                    color = Color.argb(178, 88, 208, 0);
//                color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 88, 208, 0);
//            }

            overlayOptionses.add(new PolylineOptions()
                    .points(step.getWayPoints()).width(10).color(color1)
                    .zIndex(0));

        }


        return overlayOptionses;
    }

    public void updateSteps(List<TransitRouteLine.TransitStep> stepList) {

        if (mRouteLine == null) {
            return;
        }
        // step node
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {

            for (TransitRouteLine.TransitStep step : mRouteLine.getAllStep()) {

                if (stepList.contains(step))
                    continue;

//                if (stepList.size() == 0) {
//                    stepList.add(step);
//                    continue;
//                  } else if (step.getStepType() !=
//                          TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING){
//                      step.setEntrace(busSteps.get(busSteps.size()-1).getExit());
//                      busSteps.add(step);
//                  } else {
//                      if(mRouteLine.getAllStep().indexOf(step) != mRouteLine.getAllStep().size()-1) {
//                          step.setEntrace(busSteps.get(busSteps.size()-1).getExit());
//                          step.setExit(busSteps.get(busSteps.size()-1).getExit());
//                          continue;
//                      }
//                  }

                if (stepList.size() == 0) {
                    stepList.add(step);
                    continue;
                } else if (step.getWayPoints() != null) {
                    stepList.get(stepList.size()-1).setExit(step.getEntrance());
                    stepList.add(step);
                }


            }
        }
        mRouteLine.setSteps(stepList);
    }

    private BitmapDescriptor getIconForStep(TransitRouteLine.TransitStep step) {
        switch (step.getStepType()) {
            case BUSLINE:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_bus_station.png");
            case SUBWAY:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_subway_station.png");
            case WAKLING:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_walk_route.png");
            default:
                return null;
        }
    }

    /**
     * 设置路线数据
     * 
     * @param routeOverlay
     *            路线数据
     */
    public void setData(TransitRouteLine routeOverlay,List<TransitRouteLine.TransitStep> stepList) {
        this.mRouteLine = routeOverlay;
        //updateSteps(stepList);
    }

    /**
     * 覆写此方法以改变默认起点图标
     * 
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认终点图标
     * 
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    public int getLineColor() {
        return 0;
    }
    /**
     * 覆写此方法以改变起默认点击行为
     * 
     * @param i
     *            被点击的step在
     *            {@link com.baidu.mapapi.search.route.TransitRouteLine#getAllStep()}
     *            中的索引
     * @return 是否处理了该点击事件
     */
    public boolean onRouteNodeClick(int i) {
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().get(i) != null) {
            String station = MainActivity.stringList.get(i);
            Toast.makeText(MainActivity.context,station,Toast.LENGTH_SHORT).show();
            Log.i("baidumapsdk", "TransitRouteOverlay onRouteNodeClick");
        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        for (Overlay mMarker : mOverlayList) {
            if (mMarker instanceof Marker && mMarker.equals(marker)) {
                if (marker.getExtraInfo() != null) {
                    onRouteNodeClick(marker.getExtraInfo().getInt("index"));
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }

}
