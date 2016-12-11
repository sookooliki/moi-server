package ru.itmo.ipm.model;

/**
 * Created by alexander on 10.12.16.
 */
public class Place {
    private String resource;
    private String latitude;
    private String longitude;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
