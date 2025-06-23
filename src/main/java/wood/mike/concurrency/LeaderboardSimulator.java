package wood.mike.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardSimulator {

    private static final int NUM_USERS = 10; // Number of unique users
    private static final int NUM_THREADS = 5; // Number of concurrent threads
    private static final int UPDATES_PER_USER = 10; // How many times each user updates their score
    private static final int MAX_SCORE_INCREASE = 50; // Max points a user can get in one update
    private static final int TOP_N_PLAYERS_TO_SHOW = 5; // How many top players to display

    public static void main(String[] args) {
        System.out.println("--- Starting Leaderboard Simulation ---");

        Leaderboard leaderboard = new Leaderboard();
        Random random = new Random();

        // Generate initial users and add them to the leaderboard with a starting score
        List<String> userIds = new ArrayList<>();
        for (int i = 0; i < NUM_USERS; i++) {
            String userId = STR."User_\{i + 1}";
            userIds.add(userId);
            // Give initial random scores to make the leaderboard non-empty
            leaderboard.updatePlayerScore(userId, random.nextInt(100)); // Initial score up to 99
        }

        System.out.println("\n--- Initial Leaderboard State ---");
        displayLeaderboard(leaderboard, TOP_N_PLAYERS_TO_SHOW);
        sleep();

        // Create a thread pool for concurrent score updates
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        System.out.println("\n--- Performing Concurrent Score Updates ---");

        // Submit tasks for each user to update their score multiple times
        for (String userId : userIds) {
            for (int i = 0; i < UPDATES_PER_USER; i++) {
                final String currentUser = userId;
                executor.submit(() -> {
                    try {
                        // Simulate some work before updating
                        Thread.sleep(random.nextInt(500)); // Small random delay

                        int currentScore = leaderboard.getPlayerScore(currentUser);
                        int scoreIncrease = random.nextInt(MAX_SCORE_INCREASE) + 1; // Score increases by at least 1
                        int newScore = currentScore + scoreIncrease;

                        leaderboard.updatePlayerScore(currentUser, newScore);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println(STR."Thread interrupted: \{e.getMessage()}");
                    }
                });
            }
        }

        // Periodically display the leaderboard while updates are happening
        for (int i = 0; i < 5; i++) {
            sleep();
            System.out.println("\n--- Leaderboard Snapshot after " + (i + 1) + " seconds ---");
            displayLeaderboard(leaderboard, TOP_N_PLAYERS_TO_SHOW);
        }

        // Shut down the executor and wait for all tasks to complete
        executor.shutdown();
        try {
            System.out.println("\n--- Waiting for all updates to complete ---");
            // Wait for all tasks to finish, or for a timeout of 30 seconds
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.err.println("Some tasks did not complete within the timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting for executor termination: " + e.getMessage());
        }

        System.out.println("\n--- Final Leaderboard State ---");
        displayLeaderboard(leaderboard, TOP_N_PLAYERS_TO_SHOW);

        System.out.println("\n--- Simulation Finished ---");
    }

    /**
     * Helper method to display the current state of the leaderboard.
     * @param leaderboard The Leaderboard instance.
     * @param n The number of top players to display.
     */
    private static void displayLeaderboard(Leaderboard leaderboard, int n) {
        List<String> topPlayers = leaderboard.getTopNPlayers(n);
        if (topPlayers.isEmpty()) {
            System.out.println("Leaderboard is empty.");
            return;
        }
        System.out.println(STR."Top \{topPlayers.size()} Players:");
        for (int i = 0; i < topPlayers.size(); i++) {
            String playerId = topPlayers.get(i);
            System.out.printf("%d. %s (Score: %d)%n", (i + 1), playerId, leaderboard.getPlayerScore(playerId));
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(STR."Sleep interrupted: \{e.getMessage()}");
        }
    }
}
