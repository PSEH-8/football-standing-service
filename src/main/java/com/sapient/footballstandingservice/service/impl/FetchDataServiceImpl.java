package com.sapient.footballstandingservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.footballstandingservice.pojo.Leagues;
import com.sapient.footballstandingservice.pojo.StandingsList;
import com.sapient.footballstandingservice.service.FetchDataService;
import com.sapient.footballstandingservice.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchDataServiceImpl implements FetchDataService {
    static final String FETCH_URL = "https://apifootball.com/api";

    static final String FETCH_LEAGUE_ACTION = "get_leagues";

    static final String FETCH_STANDING_ACTION = "get_standings";

    static final String API_KEY = "9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";

    @Autowired
    RestClientUtil restClientUtil;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public List<Leagues> fetchLeagues() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FETCH_URL)
                .queryParam("action", FETCH_LEAGUE_ACTION)
                .queryParam("APIkey", API_KEY);

        List<Leagues> leaguesList = restClientUtil.fetchData(builder.toUriString(), Leagues.class, entity);
        return leaguesList;

    }

    @Override
    public List<StandingsList> fetchStandingsList() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FETCH_URL)
                .queryParam("action", FETCH_LEAGUE_ACTION)
                .queryParam("APIkey", API_KEY);

        List<Leagues> leaguesList = restClientUtil.fetchData(builder.toUriString(), Leagues.class, entity);
        List<StandingsList> standingsLists = new ArrayList<>();
        List<Leagues> pojos = objectMapper.convertValue(leaguesList, new TypeReference<List<Leagues>>() {
        });
        pojos.forEach(league -> {
            UriComponentsBuilder builder1 = UriComponentsBuilder.fromHttpUrl(FETCH_URL)
                    .queryParam("action", FETCH_STANDING_ACTION)
                    .queryParam("league_id", league.getLeague_id())
                    .queryParam("APIkey", API_KEY);
            try {
                List<StandingsList> standingsLists1 =
                        objectMapper.convertValue(restClientUtil.fetchData(builder1.toUriString(), StandingsList.class, entity), new TypeReference<List<StandingsList>>() {
                });

                standingsLists1.forEach(standingsList -> standingsList.setCountry_id(league.getCountry_id()));
                standingsLists.addAll(standingsLists1);

            } catch (Exception exc) {
            }
        });
        return standingsLists;
    }
}
