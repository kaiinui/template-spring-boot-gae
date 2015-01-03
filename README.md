What is included?
---

### App Engine Ready

- Using Spring Boot Legacy (for Servlet v2.5)
- And some coordination.

### RPC Call Logging

```
RPC Invoked: memcache#Set
```

See `com.kaiinui.appenginetest.debug.RpcLogDelegate`

### App Engine Service Beans

```java
@Autowired
MemcacheService memcache;
```

```java
@Autowired
Queue defaultQueue;
```

### JSON Response when Exception Thrown

```json
{
    "code": 500,
    "message": "Something happen!",
    "url": "http://example.com/documentation_url"
}
```

To change the code, annotate exceptions with `@ResponseStatus`.

To change the url, annotate exceptions with `@DocUrl` (`com.kaiinui.appenginetest.error.DocUrl`)

### Appstats ready

Appstats service enable developers to measure RPC performance.