package wood.mike.concurrency;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CompletionStagePlay {

    static final Map<Integer, User> users = new ConcurrentHashMap<>();
    static {
        for (int i = 1; i <= 10; i++) {
            users.put(i, new User(i, STR."user_\{i}", ZonedDateTime.now().minusDays(i)));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CompletionStagePlay play = new CompletionStagePlay();
        play.goSingleFuture();
        play.goMultipleFuturesThenCombineAsync();
        play.goMultipleFuturesAllOfAsync();
        Thread.sleep(6000);
    }

    private void goMultipleFuturesAllOfAsync() {
        int seed = 3;
        Random random = new Random();

        CompletableFuture<User> userFuture = CompletableFuture
                .supplyAsync(() -> users.get(seed));

        CompletableFuture<UUID> uuidFuture = CompletableFuture.supplyAsync(UUID::randomUUID);

        CompletableFuture<String> stringFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(userFuture, uuidFuture, stringFuture);

        combinedFuture.thenRunAsync(() -> {
            User user = userFuture.join();
            UUID uuid = uuidFuture.join();
            String message = stringFuture.join();

            System.out.println(STR."\{message} \{user.username}, your token is: \{uuid}");
        })
        .exceptionally(ex -> {
            System.out.println(STR."Exception: \{ex.getMessage()}");
            return null;
        });
    }

    private void goMultipleFuturesThenCombineAsync() {
        int seed = 3;
        Random random = new Random();

        // 1. Start getUserSlowly chain
        CompletableFuture<User> userFuture = CompletableFuture
                .supplyAsync(this::getRandomUserId)
                .thenComposeAsync(userId -> getUserSlowly(userId, random.nextInt(seed)));

        // 2. Start getRandomUuidSlowly independently
        CompletableFuture<UUID> uuidFuture = CompletableFuture
                .supplyAsync(() -> getRandomUuidSlowly(random.nextInt(seed)));

        // 3. Combine the results when both are complete
        userFuture.thenCombineAsync(uuidFuture, (user, uuid) -> {
            System.out.println(STR."Both operations completed, user: \{user}, UUID: \{uuid}");
            return new UserToken(user.username, uuid);
        }).thenAccept(finalResult -> {
            System.out.println(STR."Final combined result processing: \{finalResult}");
        }).exceptionally(ex -> {
            System.err.println(STR."An error occurred: \{ex.getMessage()}");
            return null;
        });
    }

    private UUID getRandomUuidSlowly(Integer delay) {
        try {
            System.out.println(STR."Getting UUID with \{delay} second delay");
            TimeUnit.SECONDS.sleep(delay);
            return UUID.randomUUID();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<User> getUserSlowly(Integer userId, Integer delay) {
        try {
            System.out.println(STR."Getting user \{userId} with \{delay} second delay");
            TimeUnit.SECONDS.sleep(delay);
            return getUser(userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void goSingleFuture() {
        CompletableFuture
            .supplyAsync(this::getRandomUserId)
            .thenCompose(this::getUser)
            .thenApply(User::createdMedium)
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
                .thenAccept(created -> System.out.println(STR."User created at: \{created}"));
    }

    private Integer getRandomUserId() {
        return new Random().nextInt(10);
    }

    private CompletableFuture<User> getUser(final Integer userId) {
        return CompletableFuture.supplyAsync( () -> users.get(userId));
    }

    record User(Integer id, String username, ZonedDateTime created) {
        @Override
        public String toString() {
            return STR."User{id=\{id}, username='\{username}', created=\{createdMedium()}}";
        }

        public String createdMedium() {
            return created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        }
    }

    record UserToken(String username, UUID uuid) {}
}
