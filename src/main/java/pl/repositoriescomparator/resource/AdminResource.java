package pl.repositoriescomparator.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminResource {

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }
}
