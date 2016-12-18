package ru.itmo.ipm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.ipm.helper.LocationHelper;
import ru.itmo.ipm.model.Location;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.model.PlaceType;
import ru.itmo.ipm.repository.IPlaceRepository;
import ru.itmo.ipm.service.IPlaceService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;

/**
 * Created by alexander on 10.12.16.
 */
@Service
public class PlaceService implements IPlaceService {
    private static final double L = 111.0;

    @Autowired
    IPlaceRepository placeRepository;

    @Override
    public List<Place> getAll(Location location, double radius) throws SQLException {
        double lat1 = location.getLatitude() - radius / L;
        double lat2 = location.getLatitude() + radius / L;
        double lon1 = location.getLongitude() - radius / abs(cos(toRadians(location.getLatitude())) * L);
        double lon2 = location.getLongitude() + radius / abs(cos(toRadians(location.getLatitude())) * L);
        return placeRepository.getAll(lat1, lon1, lat2, lon2).stream()
                .filter(place -> LocationHelper.calcDistance(location, place.getLocation()) <= radius)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceType getPlaceTypeTree() {
        List<PlaceType> placeTypes = placeRepository.getAllTypes();

        return null;
    }
}
