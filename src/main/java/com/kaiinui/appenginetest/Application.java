package com.kaiinui.appenginetest;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.apphosting.api.ApiProxy;
import com.googlecode.spring.appengine.api.factory.AsyncMemcacheServiceFactoryBean;
import com.googlecode.spring.appengine.api.factory.ImagesServiceFactoryBean;
import com.googlecode.spring.appengine.api.factory.MemcacheServiceFactoryBean;
import com.googlecode.spring.appengine.api.factory.QueueFactoryBean;
import com.googlecode.spring.appengine.cache.memcache.MemcacheCache;
import com.googlecode.spring.appengine.cache.memcache.MemcacheCacheManager;
import com.kaiinui.appenginetest.debug.RpcLogDelegate;
import org.gmr.web.multipart.GMultipartResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Created by kaiinui on 2014/12/30.
 */

@Configuration
@ComponentScan(basePackages = "com.kaiinui.appenginetest")
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // To avoid java.lang.NoSuchMethodError: javax.servlet.http.HttpServletResponse.getStatus()
    // @see http://stackoverflow.com/questions/25797222/exception-from-running-spring-boot-legacy
    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setPublishEvents(false);

        return dispatcherServlet;
    }

    // https://code.google.com/p/gmultipart/
    @Bean(name = "multipartResolver")
    public GMultipartResolver multipartResolver() {
        GMultipartResolver resolver = new GMultipartResolver();
        resolver.setMaxUploadSize(100000);

        return resolver;
    }

    // Task Queue Service
    // ===

    @Bean(name = "defaultQueue")
    public Queue defaultQueue() throws Exception {
        return queueFactoryBean().getObject();
    }

    @Bean(name = "queueFactoryBean")
    public QueueFactoryBean queueFactoryBean() {
        return new QueueFactoryBean();
    }

    // Memcache Service
    // ===

    @Bean(name = "cache")
    public Cache cache() throws Exception {
        return new MemcacheCache(memcacheService());
    }

    @Bean(name = "memcacheService")
    public MemcacheService memcacheService() throws Exception {
        return memcacheServiceFactoryBean().getObject();
    }

    @Bean(name = "memcacheServiceFactory")
    public MemcacheServiceFactoryBean memcacheServiceFactoryBean() {
        return new MemcacheServiceFactoryBean();
    }

    @Bean(name = "asyncMemcacheService")
    public AsyncMemcacheService asyncMemcacheService() throws Exception {
        return asyncMemcacheServiceFactoryBean().getObject();
    }

    @Bean(name = "asyncMemcacheServiceFactoryBean")
    public AsyncMemcacheServiceFactoryBean asyncMemcacheServiceFactoryBean() {
        return new AsyncMemcacheServiceFactoryBean();
    }

    // Google Cloud Storage
    // ===
    @Bean(name = "gcsService")
    public GcsService gcsService() {
        return GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
    }

    // Images Service
    // ===
    @Bean(name = "imagesService")
    public ImagesService imagesService() throws Exception {
        return imagesServiceFactoryBean().getObject();
    }

    @Bean(name = "imagesServiceFactoryBean")
    public ImagesServiceFactoryBean imagesServiceFactoryBean() {
        return new ImagesServiceFactoryBean();
    }

    // Setting RPC Proxy For Debug
    // ===

    @Bean(name = "appengineProxy")
    public RpcLogDelegate rpcLogDelegate() {
        RpcLogDelegate delegate = new RpcLogDelegate();
        ApiProxy.setDelegate(delegate);
        return delegate;
    }
}
