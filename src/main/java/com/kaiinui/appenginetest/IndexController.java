package com.kaiinui.appenginetest;

import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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

    protected Book findBook(String id) {
        String key = "books" + id;
        String json = (String) memcacheService.get(key);
        if (json != null) {
            System.out.println("Cache hit!");
            return new Gson().fromJson(json, Book.class);
        }
        Book book = new Book("アリスの冒険", id);
        asyncMemcacheService.put(key, new Gson().toJson(book));
        return book;
    }
}
