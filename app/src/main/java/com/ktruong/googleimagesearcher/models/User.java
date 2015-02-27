package com.ktruong.googleimagesearcher.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ktruong on 2/24/15.
 * consider using custom Parcelable faster
 */
//public class User implements Parcelable {
public class User implements Serializable {
    private String name;
    private int age;
    private String favoriteDrink;

    
//    public User(Parcel parcel) {
//        this.name = parcel.readString();
//        this.age = parcel.readInt();
//        this.favoriteDrink = parcel.readString();
//    }


    public User() {
    }

    public User(String name, int age, String favoriteDrink) {
        this.name = name;
        this.age = age;
        this.favoriteDrink = favoriteDrink;
    }

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

    public String getFavoriteDrink() {
        return favoriteDrink;
    }

    public void setFavoriteDrink(String favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
    }

    
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeInt(age);
//        dest.writeString(favoriteDrink);
//    }
}
