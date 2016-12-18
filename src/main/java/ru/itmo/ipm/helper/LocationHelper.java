package ru.itmo.ipm.helper;

import ru.itmo.ipm.model.Location;

import static java.lang.Math.*;


/**
 * Created by alexander.sokolov on 2016-12-18.
 */
public final class LocationHelper {
    private static final double R = 6371.0;

    private LocationHelper() {
    }

    public static double calcDistance(Location location1, Location location2) {
        double lat1 = toRadians(location1.getLatitude());
        double lon1 = toRadians(location1.getLongitude());
        double lat2 = toRadians(location2.getLatitude());
        double lon2 = toRadians(location2.getLongitude());
        return 2 * R * asin(sqrt(pow(sin((lat2 - lat1) / 2.0), 2.0) + cos(lat1) * cos(lat2) * pow(sin((lon2 - lon1) / 2.0), 2.0)));
    }
}
