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
        return placeRepository.getAll(latitude, longitude, radius);
    }
}
