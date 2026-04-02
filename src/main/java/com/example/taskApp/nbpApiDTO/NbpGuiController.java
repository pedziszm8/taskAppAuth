package com.example.taskApp.nbpApiDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NbpGuiController {

    @Autowired
    private NbpService nbpService;

    @GetMapping("/nbp")
    public String showNbpRates(Model model) {
        List<NbpTableDto> tables = nbpService.getTableA();
        model.addAttribute("tables", tables);
        return "nbp";
    }
}
