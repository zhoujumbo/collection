package com.queue;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ConcurrentLinkedQueueTest
 * @Description TODO
 */
public class ConcurrentLinkedQueueTest {

    /**
     * http://www.itxm.cn/post/19151.html
     * https://www.jianshu.com/p/95ec319c79a5
     * https://www.jianshu.com/p/a4e0e3b872b0
     */


    /**
     *
     方法摘要
     boolean	add(E e)
     将指定元素插入此队列的尾部。
     boolean	contains(Object o)
     如果此队列包含指定元素，则返回 true。
     boolean	isEmpty()
     如果此队列不包含任何元素，则返回 true。
     Iterator<E>	iterator()
     返回在此队列元素上以恰当顺序进行迭代的迭代器。
     boolean	offer(E e)
     将指定元素插入此队列的尾部。
     E	peek()
     获取但不移除此队列的头；如果此队列为空，则返回 null。
     E	poll()
     获取并移除此队列的头，如果此队列为空，则返回 null。
     boolean	remove(Object o)
     从队列中移除指定元素的单个实例（如果存在）。
     int	size()
     返回此队列中的元素数量。
     Object[]	toArray()
     返回以恰当顺序包含此队列所有元素的数组。
     <T> T[]	toArray(T[] a)
     返回以恰当顺序包含此队列所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
     */




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
