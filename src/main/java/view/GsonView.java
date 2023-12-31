package view;

import controller.BaseController;
import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import lombok.AllArgsConstructor;
import model.*;
import repository.label.GsonLabelRepositoryImpl;
import repository.label.LabelRepository;
import repository.post.GsonPostRepositoryImpl;
import repository.post.PostRepository;
import repository.writer.GsonWriterRepositoryImpl;
import repository.writer.WriterRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The GsonView class provides a console-based interface for managing writers, posts, and labels.
 * It allows users to perform various CRUD operations on these entities.
 */
@AllArgsConstructor
public class GsonView {

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
                    printWriterWithPosts(reader, writerController);
                    break;
                case "3":
                    addNewWriter(reader, writerController);
                    break;
                case "4":
                    editWriter(reader, writerController);
                    break;
                case "5":
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

    private void addNewWriter(BufferedReader reader, WriterController writerController) throws IOException {
        printEditorHeader();
        System.out.print("Введите имя и фамилию: ");
        String[] data = reader.readLine().split(" ");
        Writer writer = new Writer(data[0], data[1], List.of(), Status.ACTIVE);
        writerController.add(writer);
        printEditorFooter();
    }

    private void editWriter(BufferedReader reader, WriterController writerController) throws IOException {
        printEditorHeader();
        System.out.print("Введите ID: ");
        String id = reader.readLine();
        Writer writer = writerController.getByIdWithPosts(id);
        System.out.println("Текущие данные :");
        System.out.println("Имя: " + writer.getFirstName() + " " + writer.getLastName());
        System.out.println("Статус: " + writer.getStatus());
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.print("Введите имя: ");
        String firstName = reader.readLine();
        System.out.print("Введите фамилию: ");
        String lastName = reader.readLine();
        System.out.print("Введите статус: ");
        Status status = Status.valueOf(reader.readLine());
        System.out.println();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setStatus(status);
        writerController.update(writer);
        printEditorFooter();
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
                    getAndPrintById(reader, postController, List.of("labels"));
                    break;
                case "3":
                    postsEditor(reader, postController, false);
                    break;
                case "4":
                    postsEditor(reader, postController, true);
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

    private void postsEditor(BufferedReader reader, PostController postController, boolean isExisted) throws IOException {
        printEditorHeader();
        Post post = new Post();

        if (isExisted) {
            post = getAndPrintById(reader, postController, List.of("labels"));
            if (post == null) {
                return;
            }
        }

        System.out.print("Введите содержание: ");
        String content = reader.readLine();
        System.out.print("Введите статус: ");
        String status = reader.readLine();
        System.out.print("Введите ID писателя: ");
        String writerId = reader.readLine();
        post.setContent(content);
        post.setPostStatus(PostStatus.valueOf(status));
        post.setWriterId(writerId);
        printEditorFooter();

        if (isExisted) {
            postController.update(post);
        } else {
            postController.add(post);
        }
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
                    getAndPrintById(reader, labelController, List.of());
                    break;
                case "3":
                    labelEditor(reader, labelController, false);
                    break;
                case "4":
                    labelEditor(reader, labelController, true);
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

    private void labelEditor(BufferedReader reader, LabelController labelController, boolean isExisted) throws IOException {
        printEditorHeader();
        Label label = new Label();

        if (isExisted) {
            label = getAndPrintById(reader, postController, List.of("labels"));
            if (label == null) {
                return;
            }
        }

        System.out.print("Введите название: ");
        String name = reader.readLine();
        System.out.print("Введите статус: ");
        String status = reader.readLine();
        label.setName(name);
        label.setStatus(Status.valueOf(status));
        printEditorFooter();

        if (isExisted) {
            labelController.update(label);
        } else {
            labelController.add(label);
        }
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

    private <T extends BaseEntity> T getAndPrintById(BufferedReader reader, BaseController<?, String> controller, List<String> ignoredFields) throws IOException {
        System.out.print("Введите ID: ");
        String id = reader.readLine();
        BaseEntity entity = controller.getById(id);
        if (entity != null) {
            printList(List.of(entity), ignoredFields);
            return (T) entity;
        } else {
            System.out.println("Записи с id " + id + " не существует");
            return null;
        }
    }

    private void printWriterWithPosts(BufferedReader reader, WriterController writerController) throws IOException {
        System.out.print("Введите ID " + "писателя" + ": ");
        String id = reader.readLine();
        Writer writer = writerController.getByIdWithPosts(id);
        if (writer == null) {
            System.out.println("Писателя" + " с ID " + id + " не существует.");
        } else {
            System.out.println("ID: " + writer.id);
            System.out.println("Имя: " + writer.getFirstName() + " " + writer.getLastName());
            System.out.println("Статус: " + writer.getStatus());
            System.out.print("Публикации : ");
            List<String> posts = writer.getPosts().stream()
                    .map(Post::getContent)
                    .collect(Collectors.toList());
            System.out.println(posts);
        }
    }

    private void printEditorHeader() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("Редактор");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
    }

    private void printEditorFooter() {
        System.out.println("Запись успешно обновлена");
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