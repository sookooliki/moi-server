package ru.itmo.ipm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.repository.IPlaceRepository;
import ru.itmo.ipm.service.IPlaceService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
@Service
public class PlaceService implements IPlaceService {
    @Autowired
    IPlaceRepository placeRepository;


    @Override
    public List<Place> getAll(double latitude, double longitude, double radius) throws SQLException {
        double longitude1 = longitude - radius / Math.abs(Math.cos(Math.toRadians(latitude)) * 111.0);
        double longitude2 = longitude + radius / Math.abs(Math.cos(Math.toRadians(latitude)) * 111.0);
        double latitude1 = latitude - (radius / 111.0);
        double latitude2 = latitude + (radius / 111.0);
        //        return placeRepository.getAll(latitude1, longitude1, latitude2, longitude2);
        return placeRepository.getAll(longitude1, latitude1, longitude2, latitude2); //проблема
    }
}
