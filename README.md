#[Sights Web API](https://moi-server.herokuapp.com/)

## Описание

Веб сервис, который использует приложение [Sights](https://github.com/rAseri/Sights).

В качестве источника данных используется проект [DBpedia](http://wiki.dbpedia.org/).

Для выполнения SPARQL запросов используется [Jena ARQ](http://jena.apache.org/documentation/query/index.html).

## Содержание

1. [Web API](#web-api)
1. [SPARQL запросы](#sparql-запросы)

## Web API

### /api/place/getPlaceTypeTree [GET]

Возвращает список типов достопримечательностей.

#### Формат ответа

``` javascript
[
  {
    "parentTypeUrl": "string",
    "resourceUrl": "string",
    "subTypes": [
      {
        "parentTypeUrl": "string",
        "resourceUrl": "string",
        "subTypes": [
          {}
        ],
        "title": "string"
      }
    ],
    "title": "string"
  }
]
```
#### Пример

[/api/place/getPlaceTypeTree](https://moi-server.herokuapp.com/api/place/getPlaceTypeTree)
> Возвращает список типов достопримечательностей.

### /api/place/getAll [GET]

Возвращает и группирует список всех достопримечательностей, которые пренадлежат указанным типам и находятся в оределённом радиусе от указанной точки.

#### Формат ответа

``` javascript
{
  "typeUrl" : [
    {
      "resourceUrl": "string",
      "title": "string",
      "location": {
        "latitude": number,
        "longitude": number
      },
      "description": "string",
      "thumbnailSmall": "string",
      "thumbnailMiddle": "string",
      "thumbnailLarge": "string",
      "placeType": {
        "resourceUrl": "string"
      }
  ]
}
```

#### Параметры запроса

Параметр | Описание | Пример | Тип параметра
---------|----------|--------|--------------
latitude | Широта географической координаты, в градусах | 59.963846 | `double`
longitude | Долгота географической координаты, в градусах | 30.343734 | `double`
radius | Радиус воокруг точки, в пределах которого следует искать достопримечательности, в километрах | 5.0 | `double`
types | Сиписок типов достопримечательностей | http://dbpedia.org/ontology/Bridge, http://dbpedia.org/ontology/Museum | `Array[string]`

### Пример

[api/place/getAll](https://moi-server.herokuapp.com/api/place/getAll?latitude=59.963846&longitude=30.343734&radius=5&types=http%3A%2F%2Fdbpedia.org%2Fontology%2FMuseum&types=http%3A%2F%2Fdbpedia.org%2Fontology%2FBridge)
> Получает все мосты и музеи в радиусе 5 км от точки с координатой (59.963846,30.343734).

## SPARQL запросы

### Список типов достопримечательностей

Получает список типов достопримечательностей.

``` sql
PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT DISTINCT  ?type ?parentType ?title
WHERE
  { ?type (rdfs:subClassOf)* <http://dbpedia.org/ontology/ArchitecturalStructure> .
    ?type  rdfs:subClassOf  ?parentType ;
           rdfs:label       ?title
    FILTER ( ( lang(?title) = "" ) || ( lang(?title) = "en" ) )
  }
```

[Пример](http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=PREFIX++rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0A%0D%0ASELECT+DISTINCT++%3Ftype+%3FparentType+%3Ftitle%0D%0AWHERE%0D%0A++%7B+%3Ftype+%28rdfs%3AsubClassOf%29*+%3Chttp%3A%2F%2Fdbpedia.org%2Fontology%2FArchitecturalStructure%3E+.%0D%0A++++%3Ftype++rdfs%3AsubClassOf++%3FparentType+%3B%0D%0A+++++++++++rdfs%3Alabel+++++++%3Ftitle%0D%0A++++FILTER+%28+%28+lang%28%3Ftitle%29+%3D+%22%22+%29+%7C%7C+%28+lang%28%3Ftitle%29+%3D+%22en%22+%29+%29%0D%0A++%7D%0D%0A&format=text%2Fhtml&CXML_redir_for_subjs=121&CXML_redir_for_hrefs=&timeout=30000&debug=on)

### Список достопримечательностей

Получает список достопримечательностей.

```sql
PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX  dbo:  <http://dbpedia.org/ontology/>
PREFIX  dbp:  <http://dbpedia.org/property/>
PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX  xsd:  <http://www.w3.org/2001/XMLSchema#>
PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT  ?resourceUrl ?type (AVG(?lat) AS ?avgLat) (AVG(?long) AS ?avgLong) ?title ?description ?thumbnail
WHERE
  { ?resourceUrl  rdf:type  ?type ;
              geo:lat   ?lat ;
              geo:long  ?long
    FILTER ( ?type IN (@typeList@) )
    FILTER ( ( ( ( ?lat > "@latitude1@"^^xsd:float ) && ( ?lat < "@latitude2@"^^xsd:float ) ) && ( ?long > "@longitude1@"^^xsd:float ) ) && ( ?long < "@longitude2@"^^xsd:float ) )
    ?resourceUrl  rdfs:label  ?title
    FILTER ( ( lang(?title) = "" ) || ( lang(?title) = "en" ) )
    ?resourceUrl  rdfs:comment  ?description
    FILTER ( ( lang(?description) = "" ) || ( lang(?description) = "en" ) )
    ?resourceUrl  dbo:thumbnail  ?thumbnail
  }
GROUP BY ?resourceUrl ?type ?title ?description ?thumbnail
```

 Где `@typeList@` список типов достопримечательноситей, `@latitude1@` и `@latitude2@` диапазон широт, `@longitude1@` и `@longitude2@` дипазон долгот.

[Пример](http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=PREFIX++geo%3A++%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23%3E%0D%0APREFIX++dbo%3A++%3Chttp%3A%2F%2Fdbpedia.org%2Fontology%2F%3E%0D%0APREFIX++dbp%3A++%3Chttp%3A%2F%2Fdbpedia.org%2Fproperty%2F%3E%0D%0APREFIX++rdf%3A++%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX++xsd%3A++%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0D%0APREFIX++rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0A%0D%0ASELECT++%3FresourceUrl+%3Ftype+%28AVG%28%3Flat%29+AS+%3FavgLat%29+%28AVG%28%3Flong%29+AS+%3FavgLong%29+%3Ftitle+%3Fdescription+%3Fthumbnail%0D%0AWHERE%0D%0A++%7B+%3FresourceUrl++rdf%3Atype++%3Ftype+%3B%0D%0A++++++++++++++geo%3Alat+++%3Flat+%3B%0D%0A++++++++++++++geo%3Along++%3Flong%0D%0A++++FILTER+%28+%3Ftype+IN+%28dbo%3ABridge%2C+dbo%3AMuseum%29+%29%0D%0A++++FILTER+%28+%28+%28+%28+%3Flat+%3E+%2259.91880095495495%22%5E%5Exsd%3Afloat+%29+%26%26+%28+%3Flat+%3C+%2260.00889104504504%22%5E%5Exsd%3Afloat+%29+%29+%26%26+%28+%3Flong+%3E+%2230.25374224712717%22%5E%5Exsd%3Afloat+%29+%29+%26%26+%28+%3Flong+%3C+%2230.43372575287283%22%5E%5Exsd%3Afloat+%29+%29%0D%0A++++%3FresourceUrl++rdfs%3Alabel++%3Ftitle%0D%0A++++FILTER+%28+%28+lang%28%3Ftitle%29+%3D+%22%22+%29+%7C%7C+%28+lang%28%3Ftitle%29+%3D+%22en%22+%29+%29%0D%0A++++%3FresourceUrl++rdfs%3Acomment++%3Fdescription%0D%0A++++FILTER+%28+%28+lang%28%3Fdescription%29+%3D+%22%22+%29+%7C%7C+%28+lang%28%3Fdescription%29+%3D+%22en%22+%29+%29%0D%0A++++%3FresourceUrl++dbo%3Athumbnail++%3Fthumbnail%0D%0A++%7D%0D%0AGROUP+BY+%3FresourceUrl+%3Ftype+%3Ftitle+%3Fdescription+%3Fthumbnail&format=text%2Fhtml&CXML_redir_for_subjs=121&CXML_redir_for_hrefs=&timeout=30000&debug=on)
