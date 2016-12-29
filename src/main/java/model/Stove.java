package model;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by saipkri on 28/12/16.
 */
public class Stove {
    private static ExecutorService executors = Executors.newFixedThreadPool(4);  // 4 stoves available to use.

    public static void use(final Task task, final Optional<Long> simulationDelay) {
        executors.submit(() -> {
            System.out.println("Using stove for task: " + task);
            if (simulationDelay.isPresent()) {
                try {
                    Thread.sleep(simulationDelay.get());
                } catch (InterruptedException ignore) {
                }
            }
        });
    }
}
