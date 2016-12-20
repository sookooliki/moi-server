package ru.itmo.ipm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.ipm.model.Location;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.model.PlaceType;
import ru.itmo.ipm.service.IPlaceService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by alexander on 10.12.16.
 */
@RestController
@RequestMapping("/api/place")
public class PlaceController {
    @Autowired
    IPlaceService placeService;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public Map<String, List<Place>> getAll(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius, @RequestParam List<String> types) throws SQLException {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        List<PlaceType> typesList = types.stream().map(s -> {
            PlaceType placeType = new PlaceType();
            placeType.setResourceUrl(s);
            return placeType;
        }).collect(Collectors.toList());
        return placeService.getAll(location, radius, typesList);
    }

    @RequestMapping(value = "getPlaceTypeTree", method = RequestMethod.GET)
    public PlaceType getPlaceTypeTree() {
        return placeService.getPlaceTypeTree();
    }
}
