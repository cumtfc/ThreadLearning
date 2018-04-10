package thread;

import java.util.concurrent.*;

public class AsyncTaskDemo {
    public static void main(String[] args) {
        Callable<String> runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            return "finish";
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask<String>(runnable) {
            @Override
            protected void done() {
                if (!isCancelled()) {
                    executorService.submit(() -> {
                        try {
                            System.out.println(Thread.currentThread().getName() + get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                }
                super.done();
            }
        };
        executorService.submit(futureTask);
    }
}
