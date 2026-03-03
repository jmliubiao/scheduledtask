package schedulev1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScTest {
    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        ScheduleService scheduleService = new ScheduleService();
        scheduleService.schedule(()->{
            System.out.println(LocalDateTime.now().format(dtf) + "这是100ms一次的1");
        },100);

        scheduleService.schedule(()->{
            System.out.println(LocalDateTime.now().format(dtf) + "这是100ms一次的2");
        },100);

        Thread.sleep(1000);
        System.out.println("添加一个200ms的定时任务");

        scheduleService.schedule(()->{
            System.out.println(LocalDateTime.now().format(dtf) + "这是200ms一次的");
        },200);
    }
}
