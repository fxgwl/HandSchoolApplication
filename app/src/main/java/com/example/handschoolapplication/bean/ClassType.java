package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/11/11.
 */

public class ClassType {

    private String typeOne;
    private String typetwo;

    public ClassType(String typeOne, String typetwo) {
        this.typeOne = typeOne;
        this.typetwo = typetwo;
    }

    public String getTypeOne() {
        return typeOne;
    }

    public void setTypeOne(String typeOne) {
        this.typeOne = typeOne;
    }

    public String getTypetwo() {
        return typetwo;
    }

    public void setTypetwo(String typetwo) {
        this.typetwo = typetwo;
    }

    @Override
    public String toString() {
        return "ClassType{" +
                "typeOne='" + typeOne + '\'' +
                ", typetwo='" + typetwo + '\'' +
                '}';
    }
}
