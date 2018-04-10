package thread;

import api.AsyncTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskDemo2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(100);
        File file = new File("./test.txt");
//        FileInputStream fin = new FileInputStream(file);
        FileWriter writer = new FileWriter(file);
        AsyncTask<String> asyncTask = new AsyncTask<String>() {
            @Override
            protected void onResult(String result) {
                try {
                    synchronized (writer) {
                        writer.append(Thread.currentThread().getName())
                                .append("\n")
                                .append(result)
                                .append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public String call() throws Exception {
                return "null";
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int i = 0;
        while (i < 100) {
            executorService.execute(asyncTask);
            i++;
        }
        latch.await();
        writer.flush();
        executorService.shutdownNow();
        writer.close();
        System.out.println("mission component");
    }
}
