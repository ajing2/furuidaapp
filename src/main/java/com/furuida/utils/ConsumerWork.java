package com.furuida.utils;

/**
 * @program: furuidaapp
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-13 22:54
 **/
public class ConsumerWork implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        BlockQueueConsumer.getInstance().consumer();
    }
}
