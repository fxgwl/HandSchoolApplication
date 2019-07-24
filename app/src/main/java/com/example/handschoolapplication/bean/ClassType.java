package com.example.handschoolapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */

public class ClassType {

    private String typeOne;//第一类学堂类型
    private List<String> typetwo;//第二类和第三类学堂类型的集合

    public ClassType() {
    }

    public ClassType(String typeOne, List<String> typetwo) {
        this.typeOne = typeOne;
        this.typetwo = typetwo;
    }

    public String getTypeOne() {
        return typeOne;
    }

    public void setTypeOne(String typeOne) {
        this.typeOne = typeOne;
    }

    public List<String> getTypetwo() {
        return typetwo;
    }

    public void setTypetwo(List<String> typetwo) {
        this.typetwo = typetwo;
    }

    @Override
    public String toString() {
        return "ClassType{" +
                "typeOne='" + typeOne + '\'' +
                ", typetwo='" + typetwo + '\'' +
                '}';
    }

    public static class TwoTypeData implements Serializable{

        private String twoType;

        public String getTwoType() {
            return twoType;
        }

        public void setTwoType(String twoType) {
            this.twoType = twoType;
        }
    }
}
