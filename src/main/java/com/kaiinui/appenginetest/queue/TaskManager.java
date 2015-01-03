package com.kaiinui.appenginetest.queue;

import com.google.appengine.api.taskqueue.Queue;

/**
 * Created by kaiinui on 2015/01/04.
 */
public class TaskManager {
    private static final Queue defaultQueue = Queues.defaultQueue();
    private static final Queue defaultBackendQueue = Queues.defaultBackendQueue();
    private static final Queue defaultPullQueue = Queues.defaultPullQueue();
    // Queues are Threadsafe @see https://cloud.google.com/appengine/docs/java/javadoc/com/google/appengine/api/taskqueue/Queue

    // Implement Task Queueing Methods
}
