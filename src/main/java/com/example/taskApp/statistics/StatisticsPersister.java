package com.example.taskApp.statistics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatisticsPersister {

    @Autowired
    StatisticsRepository statisticsRepository;


    public void saveUserData(String givenName, String familyName, String email) {//wyciąganie statystyk do klasy
        Statistics statistics = new Statistics();
/*        List<Statistics> all = statisticsRepoository.findAll();//ładuje wszystkie nie wydajne bo wszystkie naraz przy kazdym
        for (Statistics st :
                all) {
            if (st.email.equals(email)){
                st.incrementAmountOFLog();
                statisticsRepoository.save(st);
                return;
            }

        }*/

        Optional<Statistics> optionalStatistics = statisticsRepository.findByEmail(email);
        if (optionalStatistics.isPresent()) {
            Statistics st = optionalStatistics.get();
            st.incrementAmountOFLog();
            statisticsRepository.save(st);
            return;

        }


        statistics.setName(givenName);
        statistics.setLast_name(familyName);
        statistics.setEmail(email);
        if (email.equals("pedziszm8@gmail.com")) {
            statistics.setRole(Role.ADMIN);
        } else {
            statistics.setRole(Role.USER);
        }

        statisticsRepository.save(statistics);

    }
}
