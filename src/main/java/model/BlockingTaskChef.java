package model;

/**
 * Created by saipkri on 28/12/16.
 */
public class BlockingTaskChef extends Chef {

    @Override
    public void executeTask(Task task) {
        System.out.println("[BlockingTaskChef] Started task: " + System.currentTimeMillis());
        executeBlocking(task);
    }

    @Override
    public void taskFinished(Task task) {
        System.out.println("[BlockingTaskChef] Finished task: " + task + " in " + (System.currentTimeMillis() - task.getStartedTimestamp()) + " ms.\n");
    }
}
