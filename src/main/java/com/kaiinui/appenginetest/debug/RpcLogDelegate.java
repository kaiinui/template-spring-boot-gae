package com.kaiinui.appenginetest.debug;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.LogRecord;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.ApiConfig;

import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A ApiProxy Delegate class that simply logs App Engine's RPC Call like as "memcache#Set".
 */
public class RpcLogDelegate implements Delegate<Environment> {
    static final Logger logger = Logger.getLogger(RpcLogDelegate.class.getSimpleName());
    final private Delegate<Environment> original = ApiProxy.getDelegate();

    public byte[] makeSyncCall(Environment environment, String s, String s1, byte[] bytes) throws ApiProxyException {
        hookRpcCall(environment, s, s1, bytes);

        return original.makeSyncCall(environment, s, s1, bytes);
    }

    public Future<byte[]> makeAsyncCall(Environment environment, String s, String s1, byte[] bytes, ApiConfig apiConfig) {
        hookRpcCall(environment, s, s1, bytes);

        return original.makeAsyncCall(environment, s, s1, bytes, apiConfig);
    }

    public void log(Environment environment, LogRecord logRecord) {
        original.log(environment, logRecord);
    }

    public void flushLogs(Environment environment) {
        original.flushLogs(environment);
    }

    public List<Thread> getRequestThreads(Environment environment) {
        return original.getRequestThreads(environment);
    }

    // Helpers
    // ===

    private void hookRpcCall(Environment environment, String packageName, String methodName, byte[] bytes) {
        logger.log(Level.INFO, buildMethodInvocationLog(packageName, methodName));
    }

    private String buildMethodInvocationLog(String packageName, String methodName) {
        return new StringBuilder().append("RPC Invoked: ")
                .append(packageName)
                .append("#")
                .append(methodName)
                .toString();
    }
}