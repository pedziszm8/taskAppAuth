package com.example.taskApp.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class StatisticGuiController {


    @Autowired
    private StatisticsRepository statisticsRepository;


    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        // if (user != null) {
        List<Statistics> statisticsList = statisticsRepository.findAll();
        model.addAttribute("statistics", statisticsList);
        //  model.addAttribute("name", user.getAttribute("name"));
        //}
        System.out.println("MAP: " + statisticsList);  //do sprawdzania
        for (Statistics statistic :
                statisticsList) {
            System.out.println(statistic);
        }
        return "usersEmail";

    }
}
