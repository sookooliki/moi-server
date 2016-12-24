package ru.itmo.ipm.model;

/**
 * Created by alexander on 10.12.16.
 */
public class Place {
    private String resourceUrl;
    private String title;
    private Location location;
    private String description;
    private String thumbnailSmall;
    private String thumbnailMiddle;
    private String thumbnailLarge;
    private PlaceType placeType;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailSmall() {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(String thumbnailSmall) {
        this.thumbnailSmall = thumbnailSmall;
    }

    public String getThumbnailMiddle() {
        return thumbnailMiddle;
    }

    public void setThumbnailMiddle(String thumbnailMiddle) {
        this.thumbnailMiddle = thumbnailMiddle;
    }

    public String getThumbnailLarge() {
        return thumbnailLarge;
    }

    public void setThumbnailLarge(String thumbnailLarge) {
        this.thumbnailLarge = thumbnailLarge;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
}
