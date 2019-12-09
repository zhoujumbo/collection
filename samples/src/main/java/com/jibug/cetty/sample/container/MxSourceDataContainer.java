package com.jibug.cetty.sample.container;

import com.jibug.cetty.sample.entity.domain.MlGoodsMx;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MxSourceDataContainer {

    private ConcurrentLinkedQueue<MlGoodsMx> queueBr;
    public MxSourceDataContainer() {
        queueBr =  new ConcurrentLinkedQueue<>();
    }
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock r = rwLock.readLock();
    private final Lock w = rwLock.writeLock();

    public MlGoodsMx poll() {
        return queueBr.poll();
    }

    /**
     * add
     */
    public void offer(MlGoodsMx mlGoodsBr) {
       this.queueBr.offer(mlGoodsBr);
    }

    /**
     * 删除
     */
    public void remove(MlGoodsMx mlGoodsBr) {
       this.queueBr.remove(mlGoodsBr);
    }

    /**
     * size
     */
    public int size() {
        return queueBr.size();
    }

    public boolean isEmpty(){
        return this.queueBr.isEmpty();
    }

    public boolean contains(MlGoodsMx mlGoodsBr){
        return this.queueBr.contains(mlGoodsBr);
    }

    public Iterator<MlGoodsMx> iterator(){
        return this.queueBr.iterator();
    }

}
