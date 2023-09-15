package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.BaseController;
import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import lombok.AllArgsConstructor;
import model.BaseEntity;
import repository.label.GsonLabelRepositoryImpl;
import repository.post.GsonPostRepositoryImpl;
import repository.writer.GsonWriterRepositoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GsonView {

    private Gson gson;

    private LabelController labelController;
    private PostController postController;
    private WriterController writerController;

    private GsonView(WriterRepository writerRepository, PostRepository postRepository, LabelRepository labelRepository) {
        labelController = new LabelController(labelRepository);
        postController = new PostController(postRepository);
        writerController = new WriterController(writerRepository, postRepository);
    }

    private void start() {
        mainMenu();
    }

    private void mainMenu() {
        clearConsole();
        printMainMenu();
        boolean shouldExitFromMainMenu = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!shouldExitFromMainMenu) {
                String input = reader.readLine();
                switch (input) {
                    case "1":
                        shouldExitFromMainMenu = true;
                        writersMenu(reader);
                        break;
                    case "2":
                        shouldExitFromMainMenu = true;
                        postsMenu(reader);
                        break;
                    case "3":
                        shouldExitFromMainMenu = true;
                        labelsMenu(reader);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Введены не корректные данные");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writersMenu(BufferedReader reader) throws IOException {
        printMenuOptions(" Писатели ");
        boolean shouldReturnToMainMenu = false;

        while (!shouldReturnToMainMenu) {
            String input = reader.readLine();
            switch (input) {
                case "1":
                    printList(writerController.getAll(), List.of("posts"));
                    break;
                case "2":
                    // 2. Найти по ID
                    break;
                case "3":
                    // 3. Добавить
                    break;
                case "4":
                    // 4. Отредактировать
                    break;
                case "5":
                    //deleteWriterById(reader);
                    deleteById(reader, writerController, "писателя");
                    break;
                case "6":
                    shouldReturnToMainMenu = true;
                    break;
                default:
                    System.out.println("Введены не корректные данные");
                    break;
            }
        }
        mainMenu();
    }

    private void postsMenu(BufferedReader reader) throws IOException {
        printMenuOptions("Публикации");

        boolean shouldReturnToMainMenu = false;

        while (!shouldReturnToMainMenu) {
            String input = reader.readLine();
            switch (input) {
                case "1":
                    printList(postController.getAll(), List.of("labels"));
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    deleteById(reader, postController, "публикации");
                    break;
                case "6":
                    shouldReturnToMainMenu = true;
                    break;
                default:
                    System.out.println("Введены не корректные данные");
                    break;
            }
        }
        mainMenu();
    }

    private void labelsMenu(BufferedReader reader) throws IOException {
        printMenuOptions("  Лэйблы  ");

        boolean shouldReturnToMainMenu = false;

        while (!shouldReturnToMainMenu) {
            String input = reader.readLine();
            switch (input) {
                case "1":
                    printList(labelController.getAll(), List.of());
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    deleteById(reader, labelController, "лэйбла");
                    break;
                case "6":
                    shouldReturnToMainMenu = true;
                    break;
                default:
                    System.out.println("Введены не корректные данные");
                    break;
            }
        }
        mainMenu();
    }

    private void printMainMenu() {
        System.out.println("==================================");
        System.out.println("||         Главное меню         ||");
        System.out.println("==================================");
        System.out.println("||Введите номер меню из списка: ||");
        System.out.println("||                              ||");
        System.out.println("||  1. Писатели                 ||");
        System.out.println("||  2. Публикации               ||");
        System.out.println("||  3. Лэйблы                   ||");
        System.out.println("||                              ||");
        System.out.println("||   Наберите exit для выхода   ||");
        System.out.println("==================================\n\n\n");
    }

    private void printMenuOptions(String menuName) {
        clearConsole();

        System.out.println("==================================");
        System.out.println("||         " + menuName + "           ||");
        System.out.println("==================================");
        System.out.println("||Введите номер меню из списка: ||");
        System.out.println("||                              ||");
        System.out.println("||  1. Просмотреть всех         ||");
        System.out.println("||  2. Найти по ID              ||");
        System.out.println("||  3. Добавить                 ||");
        System.out.println("||  4. Отредактировать          ||");
        System.out.println("||  5. Удалить  по ID           ||");
        System.out.println("||  6. Вернуться в главное меню ||");
        System.out.println("||                              ||");
        System.out.println("==================================\n\n\n");

    }

    private <T extends BaseEntity> void printList(List<T> list, List<String> ignoredFields) {
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        if (list.size() > 0) {
            Class<?> clazz = list.get(0).getClass();
            List<Field> fields = new ArrayList<>(List.of(clazz.getSuperclass().getDeclaredFields()));
            fields.addAll(List.of(clazz.getDeclaredFields()));

            List<Field> filteredFields = fields.stream()
                    .filter(field -> !ignoredFields.contains(field.getName()))
                    .collect(Collectors.toList());

            System.out.println(createTableHeader(filteredFields, ignoredFields));
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

            list.forEach(entity -> {
                System.out.println(createTableRow(filteredFields, entity));
            });
        } else {
            System.out.println("Записи отсутствуют");
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
    }

    private String createTableHeader(List<Field> fieldNames, List<String> ignoredFields) {
        StringBuilder headerBuilder = new StringBuilder();

        fieldNames.forEach(fieldName -> {
            if (!ignoredFields.contains(fieldName.getName())) {
                headerBuilder.append(fieldName.getName());
                headerBuilder.append(" ".repeat(25 - fieldName.getName().length()));
            }
        });

        return headerBuilder.toString();
    }

    private <T extends BaseEntity> String createTableRow(List<Field> fields, T entity) {
        StringBuilder rowBuilder = new StringBuilder();

        fields.forEach(field -> {
            try {
                String fieldValue = field.get(entity).toString();
                rowBuilder.append(fieldValue);
                rowBuilder.append(" ".repeat(25 - fieldValue.length()));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return rowBuilder.toString();
    }

    private void deleteById(BufferedReader reader, BaseController<?, String> controller, String entityName) throws IOException {
        System.out.print("Введите ID " + entityName + " для удаления: ");
        String id = reader.readLine();
        controller.deleteById(id);
        System.out.println("Запись с " + id + " успешно удалена");
    }

    private void clearConsole() {
        for (int i = 0; i < 25; ++i) System.out.println();
    }

    public static void main(String[] args) {
        GsonView view = new GsonView(
                new GsonWriterRepositoryImpl("src/main/resources/store/writers.json"),
                new GsonPostRepositoryImpl("src/main/resources/store/posts.json"),
                new GsonLabelRepositoryImpl("src/main/resources/store/labels.json")
        );
        view.start();
    }
}