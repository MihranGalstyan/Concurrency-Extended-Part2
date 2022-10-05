import java.util.SortedMap;
import java.util.concurrent.*;

/**
 * Created by Mihran Galstyan
 * All rights reserved
 */
public class Loading {
    public static void main(final String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        Runnable timer = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.print(".");
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        executorService.execute(timer);

        Future<String> nameThread = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "Frank";
            }
        });

        Future<Integer> ageThread = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 24;
            }
        });
        try {
            String name = nameThread.get();
            int age = ageThread.get();
            System.out.println();
            System.out.printf("Name: %s, Age: %s", name,age);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
