package com.sapient.footballstandingservice.service;

import com.sapient.footballstandingservice.pojo.Leagues;
import com.sapient.footballstandingservice.pojo.StandingsList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FetchDataService {
    List<Leagues> fetchLeagues() throws Exception;
    List<StandingsList> fetchStandingsList() throws Exception;
}
