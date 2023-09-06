package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class GsonView {

    private Gson gson;
    private String fileName;

    private GsonView() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        this.fileName = "src/main/resources/store/writers.json";
        mainMenu();
    }

    private void mainMenu() {
        System.out.println("================================");
        System.out.println("||  Выберите меню из списка:  ||");
        System.out.println("||  1. Писатели               ||");
        System.out.println("||  2. Публикации             ||");
        System.out.println("||  3. Лэйблы                 ||");
        System.out.println("================================");
    }

    public static void main(String[] args) throws IOException {
    }
}