package ru.itmo.ipm.repository;

import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.model.PlaceType;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
public interface IPlaceRepository {
    List<Place> getAll(double lat1, double lon1, double lat2, double lon2) throws SQLException;

    List<PlaceType> getAllTypes();
}
