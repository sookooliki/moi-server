package ru.itmo.ipm.service;

import ru.itmo.ipm.model.Location;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.model.PlaceType;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
public interface IPlaceService {
    List<Place> getAll(Location location, double radius) throws SQLException;

    PlaceType getPlaceTypeTree();
}
