package kaskavalci.springRest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kaskavalci.publisher.Publisher;

@RestController
public class ApplicationController {

    @RequestMapping("/tweaker")
    public String index() {
        return Publisher.getTweaker();
    }

}