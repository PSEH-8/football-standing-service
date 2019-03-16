package com.sapient.footballstandingservice.controller;

import com.sapient.footballstandingservice.pojo.Leagues;
import com.sapient.footballstandingservice.pojo.StandingsList;
import com.sapient.footballstandingservice.service.FetchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StandingController {
    @Autowired
    FetchDataService fetchDataService;

    @RequestMapping(path = "/leagues", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Leagues>> getLeagues() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Type","application/json");

        ResponseEntity<List<Leagues>>
                responseEntity = ResponseEntity.ok().headers(headers).body(fetchDataService.fetchLeagues());

        return responseEntity;

    }

    @RequestMapping(path = "/standings", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<StandingsList>> getStandings() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Type","application/json");

        ResponseEntity<List<StandingsList>>
                responseEntity = ResponseEntity.ok().headers(headers).body(fetchDataService.fetchStandingsList());

        return responseEntity;

    }
}
