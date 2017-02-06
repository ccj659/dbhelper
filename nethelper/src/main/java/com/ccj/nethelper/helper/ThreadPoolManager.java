package com.ccj.nethelper.helper;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ccj on 2017/1/18.
 */

public class ThreadPoolManager  {

    private  static final String TAG="ThreadPoolManager";
    private static ThreadPoolManager instance =new ThreadPoolManager();

    private LinkedBlockingDeque<Future<?>> tasksQueue=new LinkedBlockingDeque<>();



    ExecutorService executorService;

    ScheduledExecutorService scheduledExecutorService;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private ThreadPoolExecutor threadPoolExecutor;

    public static ThreadPoolManager getInstance(){


        return instance;
    }

    private  ThreadPoolManager(){

        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4), handler);

        /**
         * 线程池 执行线程
         */
        threadPoolExecutor.execute(runable);

    }



    private Runnable runable =new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                FutureTask futrueTask=null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG,"等待队列     "+tasksQueue.size());
                    futrueTask= (FutureTask) tasksQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(futrueTask!=null)
                {
                    threadPoolExecutor.execute(futrueTask);
                }
                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());
            }
        }
    };

    public <T> void execte(FutureTask<T> futureTask) throws InterruptedException {
        Log.i("execte","execte     "+futureTask.toString());

        tasksQueue.put(futureTask);
        String ss;
    }

    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                tasksQueue.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };




}
