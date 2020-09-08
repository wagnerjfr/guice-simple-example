package repository;

import java.time.Duration;

interface Sleepable {

    default void sleep(long milliseconds) {
        try {
            Thread.sleep(Duration.ofMillis(milliseconds).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
