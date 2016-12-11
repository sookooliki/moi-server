package ru.itmo.ipm.repository;

import ru.itmo.ipm.model.Place;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
public interface IPlaceRepository {
    List<Place> getAll(double latitude1, double longitude1, double latitude2, double longitude2) throws SQLException;
}
