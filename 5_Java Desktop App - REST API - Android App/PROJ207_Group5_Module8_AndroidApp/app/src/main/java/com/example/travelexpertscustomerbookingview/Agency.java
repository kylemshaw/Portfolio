//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Data model class for any Agencies Table entries being handled by the application
//----------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

public class Agency {
    private int agencyId;
    private String agencyAddress;
    private String agencyCity;
    private String agencyProv;
    private String agencyPostal;
    private String agencyCountry;
    private String agencyPhone;
    private String agencyFax;

    public Agency(int agencyId, String agencyAddress, String agencyCity, String agencyProv, String agencyPostal, String agencyCountry, String agencyPhone, String agencyFax) {
        this.agencyId = agencyId;
        this.agencyAddress = agencyAddress;
        this.agencyCity = agencyCity;
        this.agencyProv = agencyProv;
        this.agencyPostal = agencyPostal;
        this.agencyCountry = agencyCountry;
        this.agencyPhone = agencyPhone;
        this.agencyFax = agencyFax;
    }

    public Agency() {

    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public String getAgencyCity() {
        return agencyCity;
    }

    public void setAgencyCity(String agencyCity) {
        this.agencyCity = agencyCity;
    }

    public String getAgencyProv() {
        return agencyProv;
    }

    public void setAgencyProv(String agencyProv) {
        this.agencyProv = agencyProv;
    }

    public String getAgencyPostal() {
        return agencyPostal;
    }

    public void setAgencyPostal(String agencyPostal) {
        this.agencyPostal = agencyPostal;
    }

    public String getAgencyCountry() {
        return agencyCountry;
    }

    public void setAgencyCountry(String agencyCountry) {
        this.agencyCountry = agencyCountry;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyFax() {
        return agencyFax;
    }

    public void setAgencyFax(String agencyFax) {
        this.agencyFax = agencyFax;
    }

    @Override
    public String toString() {
        return "Travel Experts " + agencyCity;
    }
}
