package ThreadPool;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 9, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20),new ThreadPoolExecutor.AbortPolicy());
        //默认线程池满时的策略是AbortPolicy,如果拒绝则丢弃任务并抛出运行时异常
        //不捕获会导致主线程后续代码不会执行

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                threadPool.submit(() -> System.out.println("测试抛出异常后会不会执行后续提交"));
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,5000);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            //submit方法可以有返回值，execute方法无返回值
            Future future = threadPool.submit(() -> {
                System.out.println("子线程" + finalI);
                return finalI;
            });
            System.out.println("线程返回结果"+future.get());
        }
        System.out.println("最大线程数量:"+threadPool.getLargestPoolSize());
    }
}
