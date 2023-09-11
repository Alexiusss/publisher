package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private GsonView() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        labelController = new LabelController(new GsonLabelRepositoryImpl(gson, "src/main/resources/store/labels.json"));
        postController = new PostController(new GsonPostRepositoryImpl(gson, "src/main/resources/store/posts.json"));
        writerController = new WriterController(new GsonWriterRepositoryImpl(gson, "src/main/resources/store/writers.json"));
    }

    private void start() throws IOException {
        mainMenu();
    }

    private void mainMenu() throws IOException {
        clearConsole();
        printMainMenu();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (((input = reader.readLine()) != null)) {
                switch (input) {
                    case "1":
                        writersMenu();
                        break;
                    case "2":
                        postsMenu();
                        break;
                    case "3":
                        labelsMenu();
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

    private void writersMenu() throws IOException {
        printMenuOptions(" Писатели ");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = reader.readLine()) != null) {
                switch (input) {
                    case "1":
                        //1. Просмотреть всех
                        printList(writerController.getAll(), List.of("posts"));
                        break;
                    case "2":
                        // 2. Найти по ID
                        break;
                    case "3":
                        //3. Добавить
                        break;
                    case "4":
                        //4. Отредактировать
                        break;
                    case "5":
                        mainMenu();
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Введены не корректные данные");
                        break;
                }
            }
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void postsMenu() throws IOException {
        printMenuOptions("Публикации");
    }

    private void labelsMenu() throws IOException {
        printMenuOptions("  Лэйблы  ");
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
        System.out.println("||  5. Вернуться в главное меню ||");
        System.out.println("||                              ||");
        System.out.println("||   Наберите exit для выхода   ||");
        System.out.println("==================================\n\n\n");

    }

    private <T extends BaseEntity> void printList(List<T> list, List<String> ignoredFields) throws NoSuchFieldException, IllegalAccessException {
        if (list.size() > 0) {
            Class<?> clazz = list.get(0).getClass();
            List<Field> fields = new ArrayList<>(List.of(clazz.getSuperclass().getDeclaredFields()));
            fields.addAll(List.of(clazz.getDeclaredFields()));

            List<Field> filteredFields = fields.stream()
                    .filter(field -> !ignoredFields.contains(field.getName()))
                    .collect(Collectors.toList());

            List<String> fieldsNames = fields.stream()
                    .map(Field::getName)
                    .filter(field -> !ignoredFields.contains(field))
                    .collect(Collectors.toList());

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println(createTableHeader(fieldsNames, ignoredFields));
            System.out.println("-------------------------------------------------------------------------------------");

            list.forEach(entity -> {
                System.out.println(createTableRow(filteredFields, entity));
            });

            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    private String createTableHeader(List<String> fieldNames, List<String> ignoredFields) {
        StringBuilder headerBuilder = new StringBuilder();

        fieldNames.forEach(fieldName -> {
            if (!ignoredFields.contains(fieldName)) {
                headerBuilder.append(fieldName);
                headerBuilder.append(" ".repeat(25 - fieldName.length()));
            }
        });

        return headerBuilder.toString();
    }

    private <T extends BaseEntity> String createTableRow(List<Field> fields, T entity) {
        StringBuilder rowBuilder = new StringBuilder();

        Class<?> clazz = entity.getClass();

        fields.forEach(field -> {
            try {
                String fieldValue = (String) field.get(clazz);
                rowBuilder.append(fieldValue);
                rowBuilder.append(" ".repeat(25 - fieldValue.length()));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return rowBuilder.toString();
    }

    private void clearConsole() {
        for (int i = 0; i < 25; ++i) System.out.println();
    }

    public static void main(String[] args) throws IOException {
        GsonView view = new GsonView();
        view.start();
    }
}