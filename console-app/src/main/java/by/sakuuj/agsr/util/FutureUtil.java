package by.sakuuj.agsr.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@UtilityClass
public class FutureUtil {

    public static void waitForFuture(Future<?> f) {
        try {
            f.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
