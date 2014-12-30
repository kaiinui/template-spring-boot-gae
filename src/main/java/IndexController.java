import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kaiinui on 2014/12/30.
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }

    @RequestMapping("/books/{id}")
    public Book getBook(@PathVariable String id) {
        return new Book("Alice in Wonderland", id);
    }
}
