package ru.itmo.ipm.repository.impl;

import org.apache.jena.jdbc.statements.DatasetStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.repository.IPlaceRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
@Repository
public class PlaceRepository implements IPlaceRepository {
    @Autowired
    Connection connection;

    @Override
    public List<Place> getAll(double latitude, double longitude, double radius) throws SQLException {
        List<Place> places = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT ?resource WHERE { ?resource a ?type } LIMIT 100");
//        ResultSet resultSet = statement.executeQuery("SELECT ?resource WHERE { ?resource rdf:type <http://dbpedia.org/ontology/ArchitecturalStructure> }");
        while (resultSet.next()) {
            Place place = new Place();
            place.setResource(resultSet.getString("resource"));
            places.add(place);
        }
        return places;
    }
}
