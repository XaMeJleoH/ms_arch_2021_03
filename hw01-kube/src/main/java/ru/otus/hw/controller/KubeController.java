package ru.otus.hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hw")
public class KubeController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping(name = "/", produces = "application/json")
    public String getVersion() {
        return String.format("Hello, nice to meet you. Image from dockerHub. applicationId=%s", this.applicationContext.getId());
    }

/*
    $ wget -qO- http://172.17.0.3:80/actuator/health
    wget: can't connect to remote host (172.17.0.3): Connection refused
    $ wget -qO- http://172.17.0.3:80/hw/
    wget: can't connect to remote host (172.17.0.3): Connection refused
    $ wget -qO- http://172.17.0.3:80/hw/hello

    wget -qO- http://172.17.0.4:80/actuator/health
    wget -qO- http://172.17.0.4:8000/actuator/health
    wget -qO- http://172.17.0.4:8000/hw/

    wget -qO- http://172.17.0.6:8000/hw/
    wget -qO- http://10.105.14.23:9000/actuator/health
    wget -qO- http://10.105.14.23:9000/hw/

    wget -qO- http://10.103.136.13:9000/hw/

// 21:24 - манифесты
    //30:28
*/

}
