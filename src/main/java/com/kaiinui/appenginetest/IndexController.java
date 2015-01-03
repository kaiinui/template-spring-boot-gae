package com.kaiinui.appenginetest;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.gson.Gson;
import org.slim3.datastore.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by kaiinui on 2014/12/30.
 */
@RestController
public class IndexController {
    @Autowired
    MemcacheService memcacheService;
    @Autowired
    AsyncMemcacheService asyncMemcacheService;

    @RequestMapping("/")
    public String index() throws Exception {
        return "Hello World!";
    }

    @RequestMapping("/books/{id}")
    public Book getBook(@PathVariable String id) {
        return findBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public Book createBook(@RequestBody Book book, BindingResult error) {
        Datastore.put(book);

        return book;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> listBook() {
        return Datastore.query(Book.class).asList();
    }

    @Cacheable("findBook")
    protected Book findBook(String id) {
        return Datastore.get(Book.class, Datastore.createKey(Book.class, id));
    }
}
