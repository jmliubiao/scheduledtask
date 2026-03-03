package schedulev1;

public class Job implements Comparable<Job> {
    public Runnable task;
    public long startTime;

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long delay;

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public int compareTo(Job o) {
        return Long.compare(this.startTime,o.startTime);
    }
}
