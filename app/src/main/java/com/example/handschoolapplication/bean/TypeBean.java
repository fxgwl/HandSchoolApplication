package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2018/3/20.
 */

public class TypeBean {

    private String typeOne;
    private String typeTwo;
    private String typeThree;

//    "1//2:3,1//2:3"

    public TypeBean(String typeOne, String typeTwo, String typeThree) {
        this.typeOne = typeOne;
        this.typeTwo = typeTwo;
        this.typeThree = typeThree;
    }

    public String getTypeOne() {
        return typeOne;
    }

    public void setTypeOne(String typeOne) {
        this.typeOne = typeOne;
    }

    public String getTypeTwo() {
        return typeTwo;
    }

    public void setTypeTwo(String typeTwo) {
        this.typeTwo = typeTwo;
    }

    public String getTypeThree() {
        return typeThree;
    }

    public void setTypeThree(String typeThree) {
        this.typeThree = typeThree;
    }


    @Override
    public String toString() {
        return "TypeBean{" +
                "typeOne='" + typeOne + '\'' +
                ", typeTwo='" + typeTwo + '\'' +
                ", typeThree='" + typeThree + '\'' +
                '}';
    }
}
