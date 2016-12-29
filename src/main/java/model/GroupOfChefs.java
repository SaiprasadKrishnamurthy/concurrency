package model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Created by saipkri on 28/12/16.
 */
public class GroupOfChefs extends Chef {

    private final Task[] tasks;
    private int numberOfChefs = 4;
    private ExecutorService executors = Executors.newFixedThreadPool(numberOfChefs);

    public GroupOfChefs(final Task[] tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        Stream.of(tasks).forEach(this::executeTask);
    }

    @Override
    public void executeTask(Task task) {
        executors.submit(() -> {
            System.out.println("[GroupOfChefs] Started task: " + System.currentTimeMillis());
            executeBlocking(task);
        });
    }

    @Override
    public void taskFinished(Task task) {
        System.out.println("[GroupOfChefs] Finished task: " + task + " in " + (System.currentTimeMillis() - task.getStartedTimestamp()) + " ms.\n");
    }
}
