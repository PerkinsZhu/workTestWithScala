package datastracture;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by PerkinsZhu on 2018/7/18 9:33
 **/
public class QueueTestCase {

    @Test
    public void testQueue() {
        Queue arrayBlockingQueue = new ArrayBlockingQueue(10);
        arrayBlockingQueue.add(2);
        arrayBlockingQueue.add(3);
        arrayBlockingQueue.add(4);
        arrayBlockingQueue.forEach((Object a) -> System.out.println(a));

        Queue linkedBlockingQueue = new LinkedBlockingQueue(10);
        linkedBlockingQueue.add(5);
        linkedBlockingQueue.add(6);
        linkedBlockingQueue.add(7);
        linkedBlockingQueue.forEach((Object a) -> System.out.println(a));

        Queue priorityBlockingQueue  = new PriorityBlockingQueue(10);
        priorityBlockingQueue.add(8);
        priorityBlockingQueue.add(9);
        priorityBlockingQueue.add(10);
        priorityBlockingQueue.forEach((Object a) -> System.out.println(a));

    }
}
