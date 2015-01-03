import com.google.appengine.api.datastore.Key;
import com.kaiinui.appenginetest.Book;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;

import static org.junit.Assert.*;

/**
 * Created by kaiinui on 2015/01/04.
 */
public class BookTests extends AppEngineTestCase {
    @Test
    public void testItHasKeyAsStringIdMethod() {
        Book book = new Book();
        book.setKey(Datastore.createKey(Book.class, "id"));

        assertEquals(book.getId(), "id");
    }

    @Test
    public void testItSetsUpdatedAtOnPutting() {
        Book book = new Book();
        book.setId("id");
        Datastore.put(book);

        assertNotNull(book.getCreatedAt());
    }
}
