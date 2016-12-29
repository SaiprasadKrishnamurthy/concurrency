package model;

import java.util.Optional;

/**
 * Created by saipkri on 28/12/16.
 */
public abstract class Chef {
    public abstract void executeTask(Task task);

    public abstract void taskFinished(Task task);

    public void executeBlocking(Task task) {
        try {
            Stove.use(task, Optional.empty());
            Thread.sleep(1000); // simulate a delay. Because the chef is blocked.
            taskFinished(task);
        } catch (Exception ignore) {
        }
    }

    public void executeNonBlocking(Task task) {
        try {
            // Pass a delay to the stove as the chef is not blocked here. Only the stove is blocked. Which is fair enough.
            Stove.use(task, Optional.of(1000L));
            taskFinished(task);
        } catch (Exception ignore) {
        }
    }
}
