import model.BlockingTaskChef;
import model.GroupOfChefs;
import model.MultitaskingChef;
import model.Task;

/**
 * Created by saipkri on 28/12/16.
 */
public class Main {

    public static void main(String[] args) {
        Task boilPasta = new Task("Boil Pasta");
        Task boilCream = new Task("Boil Cream");
        Task meltButter = new Task("Melt Butter");
        Task boilSauce = new Task("Boil Sauce");
        inefficientChef(boilPasta, boilCream, meltButter, boilSauce);
        System.out.println(" ---------------------- ");
        multipleChefs(boilPasta, boilCream, meltButter, boilSauce);
        System.out.println(" ---------------------- ");
        multiTaskingChef(boilPasta, boilCream, meltButter, boilSauce);
    }

    private static void multiTaskingChef(Task boilPasta, Task boilCream, Task meltButter, Task boilSauce) {
        MultitaskingChef multitaskingChef = new MultitaskingChef();
        multitaskingChef.executeTask(boilPasta);
        multitaskingChef.executeTask(boilCream);
        multitaskingChef.executeTask(meltButter);
        multitaskingChef.executeTask(boilSauce);
    }

    private static void multipleChefs(Task boilPasta, Task boilCream, Task meltButter, Task boilSauce) {
        GroupOfChefs groupOfChefs = new GroupOfChefs(new Task[]{boilPasta, boilSauce, boilCream, meltButter});
        groupOfChefs.executeAll();
    }

    private static void inefficientChef(Task boilPasta, Task boilWater, Task meltButter, Task boilSauce) {
        // Inefficient Chef implementation.
        BlockingTaskChef blockingTaskChef = new BlockingTaskChef();
        blockingTaskChef.executeTask(boilPasta);
        blockingTaskChef.executeTask(boilWater);
        blockingTaskChef.executeTask(meltButter);
        blockingTaskChef.executeTask(boilSauce);
    }
}
