import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kaiinui on 2014/12/30.
 */
@RestController
public class IndexController {
    @RequestMapping("/app")
    public String index() {
        return "Hello World!";
    }
}
