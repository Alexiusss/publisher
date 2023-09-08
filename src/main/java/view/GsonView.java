package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import lombok.AllArgsConstructor;
import repository.label.GsonLabelRepositoryImpl;
import repository.post.GsonPostRepositoryImpl;
import repository.writer.GsonWriterRepositoryImpl;

import java.io.*;

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
        writerController = new WriterController(new GsonWriterRepositoryImpl(gson, "src/main/resources/store/posts.json"));
    }

    private void start() throws IOException {
        mainMenu();
    }

    private void mainMenu() throws IOException {
        clearConsole();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postsMenu() throws IOException {
        printMenuOptions("Публикации");
    }

    private void labelsMenu() throws IOException {
        printMenuOptions("  Лэйблы  ");
    }

    private void printMenuOptions(String menuName) throws IOException {
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

    private void clearConsole() {
        for (int i = 0; i < 25; ++i) System.out.println();
    }

    public static void main(String[] args) throws IOException {
        GsonView view = new GsonView();
        view.start();
    }
}