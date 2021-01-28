package com.example.emitterapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Users {
    private int id;
    private String name;
    private String username;
    private String email;
    private AddressSubModel address;

    private String phone;
    private String website;
    private CompanySubModel company;

    protected Users(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
    }


    private class AddressSubModel {
        public String street;
        public String suite;
        public String city;
        public String zipcode;
        public Geo geo;

        public class Geo {
            public String lat;
            public String lng;
        }

    }

    private class CompanySubModel {
        public String name;
        public String catchPhrase;
        public String bs;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressSubModel getAddress() {
        return address;
    }

    public void setAddress(AddressSubModel address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public CompanySubModel getCompany() {
        return company;
    }

    public void setCompany(CompanySubModel company) {
        this.company = company;
    }
}
