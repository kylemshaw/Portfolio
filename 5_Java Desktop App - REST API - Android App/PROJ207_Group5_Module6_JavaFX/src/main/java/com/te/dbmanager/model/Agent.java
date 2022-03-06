package com.te.dbmanager.model;

public class Agent {
    private int agentId;
    private String agtFirstName;
    private String agtMiddleInitial;
    private String agtLastName;
    private String agtBusPhone;
    private String agtEmail;
    private String agtPosition;
    private int agencyId;
    private String agtPhoto;
    private String agtUsername;
    private String agtPassword;

    //Constructor
    public Agent(int agentId, String agtFirstName, String agtMiddleInitial,
                 String agtLastName, String agtBusPhone, String agtEmail,
                 String agtPosition, int agencyId, String agtPhoto,
                 String agtUsername, String agtPassword) {
        this.agentId = agentId;
        this.agtFirstName = agtFirstName;
        this.agtMiddleInitial = agtMiddleInitial;
        this.agtLastName = agtLastName;
        this.agtBusPhone = agtBusPhone;
        this.agtEmail = agtEmail;
        this.agtPosition = agtPosition;
        this.agencyId = agencyId;
        this.agtPhoto = agtPhoto;
        this.agtUsername = agtUsername;
        this.agtPassword = agtPassword;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgtFirstName() {
        return agtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.agtFirstName = agtFirstName;
    }

    public String getAgtMiddleInitial() {
        return agtMiddleInitial;
    }

    public void setAgtMiddleInitial(String agtMiddleInitial) {
        this.agtMiddleInitial = agtMiddleInitial;
    }

    public String getAgtLastName() {
        return agtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.agtLastName = agtLastName;
    }

    public String getAgtBusPhone() {
        return agtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        this.agtBusPhone = agtBusPhone;
    }

    public String getAgtEmail() {
        return agtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        this.agtEmail = agtEmail;
    }

    public String getAgtPosition() {
        return agtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        this.agtPosition = agtPosition;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgtPhoto() {
        return agtPhoto;
    }

    public void setAgtPhoto(String agtPhoto) {
        this.agtPhoto = agtPhoto;
    }

    public String getAgtUsername() {
        return agtUsername;
    }

    public void setAgtUsername(String agtUsername) {
        this.agtUsername = agtUsername;
    }

    public String getAgtPassword() {
        return agtPassword;
    }

    public void setAgtPassword(String agtPassword) {
        this.agtPassword = agtPassword;
    }

    @Override
    public String toString() {
        return agtFirstName + " " + agtLastName;
    }
}
