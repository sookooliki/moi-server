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
import java.util.*;
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
    public Map<String, List<Place>> getAll(Location location, double radius, List<PlaceType> types) throws SQLException {
        double lat1 = location.getLatitude() - radius / L;
        double lat2 = location.getLatitude() + radius / L;
        double lon1 = location.getLongitude() - radius / abs(cos(toRadians(location.getLatitude())) * L);
        double lon2 = location.getLongitude() + radius / abs(cos(toRadians(location.getLatitude())) * L);
        List<Place> places = placeRepository.getAll(lat1, lon1, lat2, lon2, types).stream()
                .filter(place -> LocationHelper.calcDistance(location, place.getLocation()) <= radius)
                .collect(Collectors.toList());
        return places.stream().collect(() -> new HashMap<String, List<Place>>(),
                (map, place) -> {
                    Optional<String> keyOptional = map.keySet().stream()
                            .filter(s -> s.equals(place.getPlaceType().getResourceUrl()))
                            .findAny();

                    if (keyOptional.isPresent()) {
                        List<Place> placeList = map.get(keyOptional.get());
                        placeList.add(place);
                    } else {
                        List<Place> placeList = new ArrayList<Place>();
                        placeList.add(place);
                        map.put(place.getPlaceType().getResourceUrl(), placeList);
                    }
                }, (map, map2) -> {
                });
    }

    @Override
    public PlaceType getPlaceTypeTree() {
        List<PlaceType> placeTypes = placeRepository.getAllTypes();

        return null;
    }
}
