package thread;

public class ThreadMethodTest {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            int i = 1000;
            while (i > 0) {
                i--;
                System.out.println("running");
            }

            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
//        thread.setDaemon(true);
        thread.start();
        thread.join(6000);
//        thread.interrupt();//只是发送阻塞时可以中断的信号，不是直接中断
        System.out.println("main ending");
    }
}
