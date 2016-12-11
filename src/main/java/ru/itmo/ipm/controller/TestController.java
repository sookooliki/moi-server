package ru.itmo.ipm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.ipm.model.Ping;

import java.util.Date;

/**
 * Created by alexander on 10.12.16.
 */
@RestController
@RequestMapping("api/test")
public class TestController {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Ping ping() {
        Ping ping = new Ping();
        ping.setDate(new Date());
        return ping;
    }
}
