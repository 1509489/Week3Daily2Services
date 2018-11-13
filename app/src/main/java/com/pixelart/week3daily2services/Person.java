package com.pixelart.week3daily2services;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name, icon;
    private int age, weight;

    public Person() {
    }

    public Person(String name, int age, int weight, String icon) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.icon = icon;
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        weight = in.readInt();
        icon = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeInt(weight);
        dest.writeString(icon);
    }
}
