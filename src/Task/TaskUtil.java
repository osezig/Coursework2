package Task;

import Util.Periodicity;
import Util.ValidateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Util.Periodicity.DAILY;

public class TaskUtil {

    final static DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    final static DateTimeFormatter FORMAT_DATE_TWO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static HashMap<Integer, Task> taskCollection = new HashMap<>();
    public static HashMap<Integer, Task> archive = new HashMap<>();

    public static void printSortedTask() {
        Set<Task> taskSet = new TreeSet<>(taskCollection.values());
        System.out.println(taskSet);
    }

    public static void addTask(Scanner scanner) {
        Task task = new Task(
                inputTitle(scanner),
                inputDescription(scanner),
                inputPeriodicity(scanner),
                inputTypeTask(scanner));
        taskCollection.put(task.getiD(), task);
    }

    public static void deleteTask(Scanner scanner) {
        System.out.println(TaskUtil.taskCollection);

        System.out.println("Введите ID задачи: ");
        int idForDelete = scanner.nextInt();

        archive.put(taskCollection.get(idForDelete).getiD(), taskCollection.get(idForDelete));

        archive.get(idForDelete).setDeleted(true);
        taskCollection.remove(idForDelete);
    }

    public static void printTaskForSpecifyDay(Scanner scanner) {

        String date = inputDate(scanner);
        LocalDateTime localDateTime = LocalDate.parse(date, TaskUtil.FORMAT_DATE_TWO).atStartOfDay();

        returnNextDate();

        for (Task task : TaskUtil.taskCollection.values()) {
            switch (task.getPeriodicity()) {
                case WEEKLY:
                    while (task.getNextDateTask().isBefore(localDateTime)) {

                        task.setNextDateTask
                                (task.getNextDateTask().plusWeeks(1L));
                    }
                    break;

                case MONTHLY:
                    while (task.getNextDateTask().isBefore(localDateTime)) {

                        task.setNextDateTask
                                (task.getNextDateTask().plusMonths(1L));
                    }
                    break;

                case YEARLY:
                    while (task.getNextDateTask().isBefore(localDateTime)) {

                        task.setNextDateTask
                                (task.getNextDateTask().plusYears(1L));
                    }
                    break;
            }
        }

        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (Task task : taskCollection.values()) {
            if (task.getNextDateTask()
                    .format(TaskUtil.FORMAT_DATE_TWO)
                    .equals(date)) {
                taskArrayList.add(task);
            }
            if (task.getPeriodicity() == DAILY) {
                taskArrayList.add(task);
            }
        }
        System.out.println(taskArrayList);
    }

    public static void editTask(Scanner scanner) {
        System.out.println("Введите ID задачи которую нужно отредактировать:");
        System.out.println(TaskUtil.taskCollection);
        int idForCorrection = scanner.nextInt();

        label:
        while (true) {
            System.out.print(
                    "1. Редактирвать название \n" +
                            "2. Редактирвать описание \n" +
                            "3. Редактирвать дату создания \n" +
                            "4. Редактировать тип повторяемости \n" +
                            "5. Редактировать тип задачи \n" +
                            "0. Выход \n"
            );
            int menu = scanner.nextInt();

            switch (menu) {
                case 1:
                    TaskUtil.taskCollection
                            .get(idForCorrection)
                            .setTitle(inputTitle(scanner));
                    break;

                case 2:
                    TaskUtil.taskCollection
                            .get(idForCorrection)
                            .setDescription(inputDescription(scanner));
                    break;

                case 3:
                    TaskUtil.taskCollection
                            .get(idForCorrection)
                            .setCreatedTime(LocalDate
                                    .parse(inputDate(scanner), TaskUtil.FORMAT_DATE_TWO)
                                    .atStartOfDay());
                    break;

                case 4:
                    TaskUtil.taskCollection
                            .get(idForCorrection)
                            .setPeriodicity(inputPeriodicity(scanner));
                    break;

                case 5:
                    TaskUtil.taskCollection
                            .get(idForCorrection)
                            .setWorkTask(inputTypeTask(scanner));
                    break;

                case 0:
                    break label;
            }
        }
    }

    public static String inputTitle(Scanner scanner) {
        try {
            System.out.println("Введите название задачи: ");
            String taskName = scanner.next();

            ValidateUtil.validateString(taskName);

            return taskName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String inputDescription(Scanner scanner) {
        try {
            System.out.println("Введите описание задачи: ");
            String taskDescription = scanner.next();

            ValidateUtil.validateString(taskDescription);

            return taskDescription;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Periodicity inputPeriodicity(Scanner scanner) {
        try {
            System.out.println("Введите признак повторяемости задачи: \n" +
                    "1 - однократная,\n" +
                    "2 - ежедневная,\n" +
                    "3 - еженедельная,\n" +
                    "4 - ежемесячная,\n" +
                    "5 - ежегодная.");
            int stringPeriodicity = scanner.nextInt();

            return ValidateUtil.validatePeriodicity(stringPeriodicity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean inputTypeTask(Scanner scanner) {
        try {
            System.out.print("Введите новый тип задачи: \n" +
                    "1: рабочая \n" +
                    "2: личная: \n");
            int intIsWork = scanner.nextInt();

            return ValidateUtil.validateIsWork(intIsWork);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String inputDate(Scanner scanner) {
        try {
            System.out.println("Введите новую дату в формате dd/mm/yyyy: ");
            String date = scanner.next();

            ValidateUtil.validateDate(date);

            return date;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void returnNextDate() {

        for (Task task : taskCollection.values()) {

            switch (task.getPeriodicity()) {
                case WEEKLY:
                    task.setNextDateTask
                            (task.getCreatedTime()
                                    .plusWeeks(1L));
                    break;
                case MONTHLY:
                    task.setNextDateTask
                            (task.getCreatedTime()
                                    .plusMonths(1L));
                    break;
                case YEARLY:
                    task.setNextDateTask
                            (task.getCreatedTime()
                                    .plusYears(1L));
                    break;
            }
        }
    }
}
