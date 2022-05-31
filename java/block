    public static class Producer implements Runnable {

        private final BlockingQueue<Integer> blockingQueue;

        private volatile boolean flag;

        private Random random;

        public Producer(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
            flag = false;
            random = new Random();
        }

        @Override
        public void run() {
            while (!flag) {
                int info = random.nextInt(100);
                try {
                    blockingQueue.put(info);
                    System.out.println(Thread.currentThread().getName() + " Producer " + info);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutDown() {
            flag = true;
        }

    }

    public static class Consumer implements Runnable {

        private final BlockingQueue<Integer> blockingQueue;

        private volatile boolean flag;

        public Consumer(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (!flag) {
                try {
                    int info = blockingQueue.take();
                    System.out.println(Thread.currentThread().getName() + " Consumer " + info);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutDown() {
            this.flag = true;
        }

    }

    @Test
    public void queue() {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<Integer>(10);
        Producer producer = new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                new Thread(producer, "producer" + i).start();
            } else {
                new Thread(consumer, "consumer" + i).start();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        producer.shutDown();
        consumer.shutDown();

    }
