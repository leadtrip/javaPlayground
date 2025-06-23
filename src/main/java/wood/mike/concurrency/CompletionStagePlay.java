package wood.mike.concurrency;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class CompletionStagePlay {

    static final HashMap<Integer, User> users = new HashMap<Integer, User>();
    static {
        for (int i = 1; i <= 10; i++) {
            users.put(i, new User(i, STR."user_\{i}", ZonedDateTime.now().minusDays(i)));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CompletionStagePlay play = new CompletionStagePlay();
        play.go();
        Thread.sleep(1000);
    }

    private void go() {
        CompletableFuture
            .supplyAsync(this::getRandomUserId)
            .thenCompose(userId -> getUser(userId))
            .thenApply( user -> user.created)
                .exceptionally(ex -> {
                    System.out.println(STR."Error fetching user \{ex.getMessage()}");
                    return null;
                })
                .whenComplete((res, ex) -> {
                    if(ex != null) {
                        System.out.println("Failure");
                    } else {
                        System.out.println("Success");
                    }
                })
                .thenAccept(created -> System.out.println(STR."User created at \{created}"));
    }

    private Integer getRandomUserId() {
        return new Random().nextInt(10);
    }

    private CompletableFuture<User> getUser(final Integer userId) {
        return CompletableFuture.supplyAsync( () -> users.get(userId));
    }

    record User(Integer id, String username, ZonedDateTime created) {}
}
