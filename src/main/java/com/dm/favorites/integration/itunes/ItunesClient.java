package com.dm.favorites.integration.itunes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "Itunes", url = "${itunes.url}")
public interface ItunesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/search", produces = APPLICATION_JSON_VALUE)
    String search(@RequestParam("entity") String entity, @RequestParam("term") String term);


    @RequestMapping(method = RequestMethod.GET, value = "/lookup", produces = APPLICATION_JSON_VALUE)
    String lookup(@RequestParam("entity") String entity,
                  @RequestParam("id") String artistId,
                  @RequestParam("limit") int limit);
}