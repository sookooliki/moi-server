package ru.itmo.ipm.repository.arq;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.PrefixMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itmo.ipm.model.Location;
import ru.itmo.ipm.model.Place;
import ru.itmo.ipm.model.PlaceType;
import ru.itmo.ipm.repository.IPlaceRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander.sokolov on 2016-12-17.
 */
@Repository()
public class PlaceRepository implements IPlaceRepository {
    private static final String SERVICE_ENDPOINT = "http://dbpedia.org/sparql";

    @Autowired
    public PrefixMapping prefixMapping;

    @Override
    public List<Place> getAll(double lat1, double lon1, double lat2, double lon2, List<PlaceType> types) throws SQLException {
        List<Place> places = new ArrayList<>();
        String queryString =
                "SELECT ?resourceUrl ?lat ?long ?title ?description ?thumbnail ?type WHERE{\n" +
                        "?resourceUrl rdf:type  ?type.\n" +
                        "FILTER(?type IN (" + types.stream().map(placeType -> "<" + placeType.getResourceUrl() + ">").reduce((s, s2) -> s + ", " + s2).get() + ")) \n" +
                        "?resourceUrl geo:lat ?lat .\n" +
                        "?resourceUrl geo:long ?long .\n" +
                        "FILTER(\n" +
                        "(?lat > \"" + lat1 + "\"^^xsd:float) && \n" +
                        "(?lat < \"" + lat2 + "\"^^xsd:float) && \n" +
                        "(?long > \"" + lon1 + "\"^^xsd:float) && \n" +
                        "(?long < \"" + lon2 + "\"^^xsd:float)) \n" +
                        "?resourceUrl rdfs:label ?title .\n" +
                        "FILTER(LANG(?title) = \"\" || LANG(?title) = \"ru\")\n" +
                        "?resourceUrl rdfs:comment ?description .\n" +
                        "FILTER(LANG(?description) = \"\" || LANG(?description) = \"ru\")\n" +
                        "?resourceUrl dbo:thumbnail ?thumbnail .\n" +
                        "}";
        Query query = QueryFactory.create();
        query.setPrefixMapping(prefixMapping);
        query = QueryFactory.parse(query, queryString, null, null);
        try (QueryExecution execution = QueryExecutionFactory.sparqlService(SERVICE_ENDPOINT, query)) {
            ResultSet resultSet = execution.execSelect();
            for (; resultSet.hasNext(); ) {
                QuerySolution solution = resultSet.nextSolution();

                Place place = new Place();
                place.setResourceUrl(solution.getResource("resourceUrl").getURI());
                place.setTitle(solution.getLiteral("title").getString());
                place.setDescription(solution.getLiteral("description").getString());
                place.setThumbnail(solution.getResource("thumbnail").getURI());

                place.setLocation(new Location());
                place.getLocation().setLatitude(solution.getLiteral("lat").getDouble());
                place.getLocation().setLongitude(solution.getLiteral("long").getDouble());

                place.setPlaceType(new PlaceType());
                place.getPlaceType().setResourceUrl(solution.getResource("type").getURI());

                places.add(place);
            }
        }
        return places;
    }

    @Override
    public List<PlaceType> getAllTypes() {
        List<PlaceType> placeTypes = new ArrayList<>();
        String queryString = "SELECT DISTINCT ?parentType ?type ?title WHERE {\n" +
                "?type rdfs:subClassOf* <http://dbpedia.org/ontology/ArchitecturalStructure> .\n" +
                "?type rdfs:subClassOf ?parentType .\n" +
                "?type rdfs:label ?title .\n" +
                "FILTER(LANG(?title)=\"\" || LANG(?title)=\"en\" || LANG(?title)=\"ru\")\n" +
                "}";
        Query query = QueryFactory.create();
        query.setPrefixMapping(prefixMapping);
        query = QueryFactory.parse(query, queryString, null, null);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SERVICE_ENDPOINT, query)) {
            ResultSet resultSet = queryExecution.execSelect();
            Model resourceModel = resultSet.getResourceModel();
            for (; resultSet.hasNext(); ) {
                QuerySolution solution = resultSet.nextSolution();
                PlaceType placeType = new PlaceType();
                placeType.setParentTypeUrl(solution.getResource("parentType").getURI());
                placeType.setResourceUrl(solution.getResource("type").getURI());
                placeType.setTitle(solution.getLiteral("title").getString());
                placeTypes.add(placeType);
            }
        }
        return placeTypes;
    }
}
