package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {

    ExecutorService service = Executors.newFixedThreadPool(4);

    public void exec() {

    }
}
