package util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class Util {
    public static String generateNewId(){
        int randomInt = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
        int currentTimeNano = LocalDateTime.now().getNano();
        return randomInt + "" + currentTimeNano;
    }
}