package ru.itmo.ipm.service;

import ru.itmo.ipm.model.Place;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
public interface IPlaceService {
    List<Place> getAll(double latitude, double longitude, double radius) throws SQLException;
}
