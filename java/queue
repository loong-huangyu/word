
    @Test
    public void testConsumer() {
        ReentrantLock lock = new ReentrantLock();
        Condition producerQueue = lock.newCondition();
        Condition consumerQueue = lock.newCondition();

        Queue<Integer> queue = new LinkedList<>();
        Producer producer = new Producer(lock, producerQueue, consumerQueue, queue);
        Consumer consumer = new Consumer(lock, producerQueue, consumerQueue, queue);
        ExecutorService pool = Executors.newCachedThreadPool();
        System.out.printf("Start");
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Integer number = consumer.take();
                    System.out.printf("消费 number " + number);
                }
            });

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    Integer number = random.nextInt(1000);
                    System.out.printf("生产 number " + number);
                    producer.add(number);
                }
            });
        }
    }

    public static class Producer {

        private ReentrantLock lock;

        private Condition producer;

        private Condition consumer;

        private Queue<Integer> queue;

        public Producer(ReentrantLock lock, Condition producer, Condition consumer,
            Queue<Integer> queue) {
            this.lock = lock;
            this.producer = producer;
            this.consumer = consumer;
            this.queue = queue;
        }

        public void add(Integer element) {
            lock.lock();
            try {
                while (queue.size() > 10) {
                    producer.await();
                }
                queue.add(element);
                System.out.printf("添加元素 element " + element);
                consumer.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    public static class Consumer {

        private ReentrantLock lock;

        private Condition producer;

        private Condition consumer;

        private Queue<Integer> queue;

        public Consumer(ReentrantLock lock, Condition producer, Condition consumer,
            Queue<Integer> queue) {
            this.lock = lock;
            this.producer = producer;
            this.consumer = consumer;
            this.queue = queue;
        }

        public Integer take() {
            Integer element = null;
            lock.lock();
            try {
                while (queue.size() == 0) {
                    consumer.await();
                }
                element = queue.poll();
                System.out.printf("获取 element " + element);
                producer.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return element;
        }

    }