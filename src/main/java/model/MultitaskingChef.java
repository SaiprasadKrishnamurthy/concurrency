package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by saipkri on 28/12/16.
 */
public class MultitaskingChef extends Chef {

    private Queue<Task> taskQueue = new LinkedList<>();

    public MultitaskingChef() {
        ScheduledExecutorService eventLoop = Executors.newScheduledThreadPool(1);
        eventLoop.scheduleWithFixedDelay(() -> {
            if (taskQueue.peek() != null) {
                executeNonBlocking(taskQueue.poll());
            }
        }, 0, 2, TimeUnit.MILLISECONDS); // Loop every 2 milliseconds.


    }

    @Override
    public void executeTask(Task task) {
        System.out.println("[MultitaskingChef] Started task: " + System.currentTimeMillis());
        // push it into a task queue and execute later.
        taskQueue.offer(task);
        try {
            Thread.sleep(1); // simulate a delay
        } catch (InterruptedException ignore) {

        }
    }

    @Override
    public void taskFinished(Task task) {
        System.out.println("[MultitaskingChef] Finished task: " + task + " in " + (System.currentTimeMillis() - task.getStartedTimestamp()) + " ms.\n");
    }
}
