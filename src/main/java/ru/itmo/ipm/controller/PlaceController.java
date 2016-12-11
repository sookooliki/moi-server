package ru.itmo.ipm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.service.IPlaceService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
@RestController
@RequestMapping("/api/place")
public class PlaceController {
    @Autowired
    IPlaceService placeService;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Place> getAll(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius) throws SQLException {
        return placeService.getAll(latitude, longitude, radius);
    }
}
