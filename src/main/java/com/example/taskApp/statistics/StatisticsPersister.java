package com.example.taskApp.statistics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticsPersister {

    @Autowired
    StatisticsRepoository statisticsRepoository;


    public void saveUserData(String givenName, String familyName, String email) {
        Statistics statistics = new Statistics();
        List<Statistics> all = statisticsRepoository.findAll();
        for (Statistics st :
                all) {
            if (st.email.equals(email)){
                st.incrementAmountOFLog();
                statisticsRepoository.save(st);
                return;
            }

        }

        statistics.setName(givenName);
        statistics.setLast_name(familyName);
        statistics.setEmail(email);

        statisticsRepoository.save(statistics);

    }
}
