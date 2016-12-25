package ru.itmo.ipm.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alexander.sokolov on 2016-12-25.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
}
