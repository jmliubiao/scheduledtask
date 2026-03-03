package schedulev1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class ScheduleService {
    Trigger trigger = new Trigger();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    void schedule(Runnable task,long delay){
        Job job = new Job();
        job.setTask(task);
        job.setStartTime(System.currentTimeMillis()+delay);
        job.setDelay(delay);
        trigger.queue.offer(job);
        trigger.wakeUp();
    }

    class Trigger{

        PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();

        Thread thread = new  Thread(()->{
            while (true) {
                while (queue.isEmpty()) {
                    // 没有任务则park住线程
                    LockSupport.park();
                }

                Job lateyJob = queue.peek();
                if(lateyJob.getStartTime() < System.currentTimeMillis()){
                    lateyJob = queue.poll();
                    executorService.execute(lateyJob.getTask());

                    Job nextJob = new Job();
                    nextJob.setStartTime(lateyJob.getDelay()+System.currentTimeMillis());
                    nextJob.setTask(lateyJob.getTask());
                    nextJob.setDelay(lateyJob.getDelay());
                    queue.offer(nextJob);
                }else {
                    // parkUntil park到指定时间唤醒
                    LockSupport.parkUntil(lateyJob.getStartTime());
                }
            }
        });

        {
            thread.start();
            System.out.println("触发器启动了！");
        }

        void wakeUp(){
            LockSupport.unpark(thread);
        }
    }
}
