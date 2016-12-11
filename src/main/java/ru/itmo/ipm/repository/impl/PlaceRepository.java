package ru.itmo.ipm.repository.impl;

import org.apache.jena.jdbc.remote.connections.RemoteEndpointConnection;
import org.apache.jena.jdbc.remote.statements.RemoteEndpointPreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.repository.IPlaceRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 10.12.16.
 */
@Repository
public class PlaceRepository implements IPlaceRepository {
    @Autowired
    Connection connection;

    @Autowired
    RemoteEndpointConnection remoteEndpointConnection;

    @Override
    public List<Place> getAll(double latitude1, double longitude1, double latitude2, double longitude2) throws SQLException {
        List<Place> places = new ArrayList<>();

        String query = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>" +
                "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>" +
                "SELECT ?place ?latitude ?longitude WHERE { " +
                "?place rdf:type <http://dbpedia.org/ontology/ArchitecturalStructure>." +
                "?place geo:lat ?latitude ." +
                "?place geo:long ?longitude ." +
                "FILTER((?latitude > \"" + latitude1 + "\"^^xsd:float) && (?latitude < \"" + latitude2 + "\"^^xsd:float) && " +
                "(?longitude > \"" + longitude1 + "\"^^xsd:float) && (?longitude < \"" + longitude2 + "\"^^xsd:float))." +
                "}";

        RemoteEndpointPreparedStatement preparedStatement = new RemoteEndpointPreparedStatement(query, remoteEndpointConnection);
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Place place = new Place();
                place.setResource(resultSet.getString("place"));
                place.setLatitude(resultSet.getString("latitude"));
                place.setLongitude(resultSet.getString("longitude"));
                places.add(place);
            }
        }
        return places;
    }
}
