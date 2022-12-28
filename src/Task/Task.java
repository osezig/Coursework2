package Task;

import Util.Periodicity;
import Util.ValidateUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
    private final int iD;
    private boolean isWorkTask;
    private String title;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime nextDateTask;
    static int counter;
    private Periodicity periodicity;
    private boolean deleted;

    public Task(String title, String description, Periodicity periodicity, boolean isWorkTask) {
        setTitle(title);
        setDescription(description);
        this.createdTime = LocalDateTime.now();
        this.isWorkTask = isWorkTask;
        Task.counter++;
        this.iD = counter;
        this.periodicity = periodicity;
    }

    public void setTitle(String title) {
        this.title = ValidateUtil.validateString(title);
    }

    public void setDescription(String description) {
        this.description = ValidateUtil.validateString(description);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getiD() {
        return iD;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getNextDateTask() {
        return nextDateTask;
    }

    public void setNextDateTask(LocalDateTime nextDateTask) {
        this.nextDateTask = nextDateTask;
    }

    public void setWorkTask(boolean workTask) {
        this.isWorkTask = workTask;
    }

    @Override
    public String toString() {
        String isWork = isWorkTask ? "Рабочая задача" : "Личная задача";
        return "Task.Task{" +
                "name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", Type=" + isWork + '\'' +
                ", Created Date=" + createdTime.format(TaskUtil.FORMAT_DATE) +
                ", id=" + iD +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return iD == task.iD && isWorkTask == task.isWorkTask && deleted == task.deleted && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(createdTime, task.createdTime) && Objects.equals(nextDateTask, task.nextDateTask) && periodicity == task.periodicity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD, isWorkTask, title, description, createdTime, nextDateTask, periodicity, deleted);
    }

    @Override
    public int compareTo(Task o) {
        if (this.getCreatedTime().isAfter(o.getCreatedTime())) {
            return 1;
        } else if (this.getCreatedTime().isBefore(o.getCreatedTime())) {
            return -1;
        } else {
            return 0;
        }
    }
}
