package com.kaiinui.appenginetest.queue;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

/**
 * Created by kaiinui on 2015/01/04.
 */
public class Queues {
    private static final String BACKEND_QUEUE_NAME = "backend";
    private static final String PULL_QUEUE_NAME = "pull";

    public static Queue defaultQueue() {
        return QueueFactory.getDefaultQueue();
    }

    public static Queue defaultBackendQueue() {
        return QueueFactory.getQueue(BACKEND_QUEUE_NAME);
    }

    public static Queue defaultPullQueue() {
        return QueueFactory.getQueue(PULL_QUEUE_NAME);
    }
}
