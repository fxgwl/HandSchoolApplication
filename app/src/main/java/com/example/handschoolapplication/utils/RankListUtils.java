package com.example.handschoolapplication.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class RankListUtils {
    //插入字段并排序
    public static List<CourseSortBean> rankList(List<CourseSortBean> mlist, LatLng currentPoint){
        List<CourseSortBean> courseSortBeen = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            String school_wei = mlist.get(i).getSchool_wei();
            String school_jing = mlist.get(i).getSchool_jing();
            double distance = DistanceUtil.getDistance(currentPoint, new LatLng(Double.parseDouble(school_wei), Double.parseDouble(school_jing)));
            mlist.get(i).setDistance(distance);
        }
        for (int i = 0; i < mlist.size()-1; i++) {
            int k = i;
            for (int j = k+1; j < mlist.size(); j++) {
                if (mlist.get(j).getDistance()<mlist.get(k).getDistance()){
                    k=j;
                }
            }

            if (i!=k){
                CourseSortBean temp = mlist.get(i);
                mlist.add(i,mlist.get(k));
                mlist.remove(i+1);
                mlist.add(k,temp);
                mlist.remove(k+1);
            }
        }
        courseSortBeen.addAll(mlist);

//        for (int i = 0; i < mlist.size(); i++) {
//            double distance = mlist.get(i).getDistance();
//            Log.e("aaa",
//                "(RankListUtils.java:42)==================="+distance);
//        }

        return courseSortBeen;
    }

    /**
     * 机构的距离升序
     * @param mlist
     * @param currentPoint
     * @return
     */
    public static List<ClassSortBean> rankListss(List<ClassSortBean> mlist, LatLng currentPoint){
        List<ClassSortBean> courseSortBeen = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            String school_wei = mlist.get(i).getUser_area();
            String school_jing = mlist.get(i).getUser_name();
            double distance = DistanceUtil.getDistance(currentPoint, new LatLng(Double.parseDouble(school_wei), Double.parseDouble(school_jing)));
            mlist.get(i).setDistance(distance);
        }
        for (int i = 0; i < mlist.size()-1; i++) {
            int k = i;
            for (int j = k+1; j < mlist.size(); j++) {
                if (mlist.get(j).getDistance()<mlist.get(k).getDistance()){
                    k=j;
                }
            }

            if (i!=k){
                ClassSortBean temp = mlist.get(i);
                mlist.add(i,mlist.get(k));
                mlist.remove(i+1);
                mlist.add(k,temp);
                mlist.remove(k+1);
            }
        }
        courseSortBeen.addAll(mlist);

//        for (int i = 0; i < mlist.size(); i++) {
//            double distance = mlist.get(i).getDistance();
//            Log.e("aaa",
//                "(RankListUtils.java:42)==================="+distance);
//        }

        return courseSortBeen;
    }
    public static List<CourseSortBean> rankListsss(List<CourseSortBean> mlist,LatLng currentPoint){
        List<CourseSortBean> courseSortBeen = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            String school_wei = mlist.get(i).getSchool_wei();
            String school_jing = mlist.get(i).getSchool_jing();
            double distance = DistanceUtil.getDistance(currentPoint, new LatLng(Double.parseDouble(school_wei), Double.parseDouble(school_jing)));
            mlist.get(i).setDistance(distance);
        }

        for (int i = 0; i < mlist.size()-1; i++) {
            int k = i;
            for (int j = k+1; j < mlist.size(); j++) {
                if (mlist.get(j).getDistance()>mlist.get(k).getDistance()){
                    k=j;
                }
            }

            if (i!=k){
                CourseSortBean temp = mlist.get(i);
                mlist.add(i,mlist.get(k));
                mlist.remove(i+1);
                mlist.add(k,temp);
                mlist.remove(k+1);
            }
        }

        courseSortBeen.addAll(mlist);


        return courseSortBeen;
    }

    /**
     * 机构的距离降序
     * @param mlist
     * @param currentPoint
     * @return
     */
    public static List<ClassSortBean> rankListssss(List<ClassSortBean> mlist, LatLng currentPoint){
        List<ClassSortBean> courseSortBeen = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            String school_wei = mlist.get(i).getUser_area();
            String school_jing = mlist.get(i).getUser_name();
            double distance = DistanceUtil.getDistance(currentPoint, new LatLng(Double.parseDouble(school_wei), Double.parseDouble(school_jing)));
            mlist.get(i).setDistance(distance);
        }

        for (int i = 0; i < mlist.size()-1; i++) {
            int k = i;
            for (int j = k+1; j < mlist.size(); j++) {
                if (mlist.get(j).getDistance()>mlist.get(k).getDistance()){
                    k=j;
                }
            }

            if (i!=k){
                ClassSortBean temp = mlist.get(i);
                mlist.add(i,mlist.get(k));
                mlist.remove(i+1);
                mlist.add(k,temp);
                mlist.remove(k+1);
            }
        }

        courseSortBeen.addAll(mlist);


        return courseSortBeen;
    }
}
