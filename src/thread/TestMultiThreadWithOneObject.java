package thread;

public class TestMultiThreadWithOneObject {
    public static void main(String[] args) {
        Demo demo = new Demo(0);
        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    System.out.println(demo.getId());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                try {
                    synchronized (demo) {
                        demo.setId(2);
//                        System.out.println("A");
                        Thread.sleep(1000);
                        demo.notify();
                        demo.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            while (true) {
                try {
                    synchronized (demo) {
                        demo.setId(3);
//                        System.out.println("B");
                        Thread.sleep(1000);
                        demo.notify();
                        demo.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class Demo {
    private int id;

    public Demo(int id) {
        this.id = id;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}