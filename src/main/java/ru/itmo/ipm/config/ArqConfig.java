package ru.itmo.ipm.config;

import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alexander.sokolov on 2016-12-18.
 */
@Configuration
public class ArqConfig {
    @Bean
    PrefixMapping prefixMapping() {
        PrefixMapping prefixMapping = new PrefixMappingImpl();
        prefixMapping.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixMapping.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
        prefixMapping.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixMapping.setNsPrefix("dbp", "http://dbpedia.org/property/");
        prefixMapping.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        prefixMapping.setNsPrefix("dbo", "http://dbpedia.org/ontology/");
        prefixMapping.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
        return prefixMapping;
    }
}
