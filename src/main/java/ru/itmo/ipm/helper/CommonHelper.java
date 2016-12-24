package ru.itmo.ipm.helper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by alexander.sokolov on 2016-12-24.
 */
public class CommonHelper {
    private CommonHelper() {
    }

    public static URI getThumbnailUrl(String url, Integer size) throws URISyntaxException {
        return getThumbnailUrl(new URI(url), size);
    }

    public static URI getThumbnailUrl(URI url, Integer size) throws URISyntaxException {
        Map<String, String> queryParams = getQueryParams(url.getQuery());
        queryParams.put("width", size.toString());
        return new URI(
                url.getScheme(),
                url.getUserInfo(),
                url.getHost(),
                url.getPort(),
                url.getPath(),
                queryParams.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .reduce((s, s2) -> s + "&" + s2).get(),
                url.getFragment());
    }

    public static Map<String, String> getQueryParams(String query) {
        return Stream.of(query.split("&")).collect(
                () -> new HashMap<String, String>(),
                (map, s) -> {
                    String[] split = s.split("=");
                    map.put(split[0], split[1]);
                }, (map1, map2) -> {
                    map1.putAll(map2);
                });
    }
}
