package com.queue;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ConcurrentLinkedQueueTest
 * @Description TODO
 * @Author jb.zhou
 * @Date 2019/11/27
 * @Version 1.0
 */
public class ConcurrentLinkedQueueTest {



    @Test
    public void test01() throws InterruptedException {
        int peopleNum = 1000;//吃饭人数
        int tableNum = 3;//饭桌数量

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        CountDownLatch count = new CountDownLatch(tableNum);//计数器

        //将吃饭人数放入队列（吃饭的人进行排队）
        for(int i=1;i<=peopleNum;i++){
            queue.offer("消费者_" + i);
        }
        //执行10个线程从队列取出元素（10个桌子开始供饭）
        System.out.println("-----------------------------------开饭了-----------------------------------");
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(tableNum);
        for(int i=0;i<tableNum;i++) {
            executorService.submit(new Dinner("00" + (i+1), queue, count));
        }
        for(int i=1001;i<=10000;i++){
            queue.offer("消费者_" + i);
        }

        //计数器等待，知道队列为空（所有人吃完）
        count.await();
        long time = System.currentTimeMillis() - start;
        System.out.println("-----------------------------------所有人已经吃完-----------------------------------");
        System.out.println("共耗时：" + time);
        System.out.println("还剩多少人"+queue.size());
        //停止线程池
        executorService.shutdown();



    }



    private static class Dinner implements Runnable{
        private String name;
        private ConcurrentLinkedQueue<String> queue;
        private CountDownLatch count;

        public Dinner(String name, ConcurrentLinkedQueue<String> queue, CountDownLatch count) {
            this.name = name;
            this.queue = queue;
            this.count = count;
        }

        @Override
        public void run() {
            while (!queue.isEmpty()){
                //从队列取出一个元素 排队的人少一个
                System.out.println("【" +queue.poll() + "】----已吃完...， 饭桌编号：" + name);
            }
            count.countDown();  //计数器-1
//            while (true){
//                if(!queue.isEmpty()){
//                    //从队列取出一个元素 排队的人少一个
//                    System.out.println("【" +queue.poll() + "】----已吃完...， 饭桌编号：" + name);
//                }
//            }
        }
    }


}
