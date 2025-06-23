package wood.mike.concurrency;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet; // Using TreeSet to keep players with the same score sorted (optional)
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * A thread-safe, real-time leaderboard implemented using ConcurrentSkipListMap.
 * It maintains player scores in sorted order and allows concurrent updates.
 * Players with the same score are stored in a TreeSet to maintain a consistent
 * ordering among them (e.g., by player ID alphabetically).
 */
public class Leaderboard {
    // Maps scores to a set of player IDs. Keys are sorted in reverse order (highest score first).
    // Integer is used for score, Set<String> for player IDs (since multiple players can have the same score).
    private final ConcurrentSkipListMap<Integer, Set<String>> scoreToPlayers;

    // Maps player IDs to their current scores for quick lookup and efficient updates.
    private final ConcurrentHashMap<String, Integer> playerCurrentScores;

    /**
     * Constructs a new Leaderboard. Scores will be ordered from highest to lowest.
     */
    public Leaderboard() {
        // Use Comparator.reverseOrder() to sort scores in descending order (highest score first)
        this.scoreToPlayers = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        this.playerCurrentScores = new ConcurrentHashMap<>();
    }

    /**
     * Updates a player's score on the leaderboard. This operation is thread-safe.
     * If the player is new, they are added. If their score changes, their position
     * on the leaderboard is updated.
     *
     * @param playerId The unique ID of the player.
     * @param newScore The new score for the player.
     */
    public void updatePlayerScore(String playerId, int newScore) {
        // Atomically update the player's score.
        // This ensures thread-safety when fetching and updating the old score.
        Integer oldScore = playerCurrentScores.put(playerId, newScore);

        // If the player had an old score, remove them from that score's set
        if (oldScore != null && !oldScore.equals(newScore)) {
            scoreToPlayers.computeIfPresent(oldScore, (score, players) -> {
                players.remove(playerId);
                // If the set for the old score becomes empty, remove the score entry itself
                return players.isEmpty() ? null : players;
            });
        }

        // Add the player to the set for their new score
        // computeIfAbsent creates a new TreeSet if the score key doesn't exist,
        // then adds the player ID to the set.
        scoreToPlayers.computeIfAbsent(newScore, k -> new TreeSet<>()).add(playerId);
        System.out.println(Thread.currentThread().getName() + " updated " + playerId + " to " + newScore);
    }

    /**
     * Retrieves the top N players from the leaderboard. This operation is thread-safe
     * and provides a consistent snapshot.
     *
     * @param n The number of top players to retrieve.
     * @return A list of player IDs representing the top N players, sorted by score (highest first).
     */
    public List<String> getTopNPlayers(int n) {
        List<String> topPlayers = new ArrayList<>();
        int count = 0;

        // Iterate through the entry set of ConcurrentSkipListMap, which is already sorted.
        // This iteration is weakly consistent, meaning it reflects elements as of the
        // construction of the iterator, and may or may not reflect subsequent updates.
        // For a leaderboard, this is generally acceptable for a "snapshot" view.
        for (Map.Entry<Integer, Set<String>> entry : scoreToPlayers.entrySet()) {
            for (String playerId : entry.getValue()) {
                if (count < n) {
                    topPlayers.add(playerId);
                    count++;
                } else {
                    break; // We have collected 'n' players, no need to continue
                }
            }
            if (count >= n) {
                break; // Stop iterating through scores once 'n' players are found
            }
        }
        return topPlayers;
    }

    /**
     * Gets the current score of a specific player.
     * @param playerId The ID of the player.
     * @return The player's current score, or 0 if the player is not found.
     */
    public int getPlayerScore(String playerId) {
        return playerCurrentScores.getOrDefault(playerId, 0);
    }

    /**
     * Returns the total number of unique players on the leaderboard.
     * @return The count of unique players.
     */
    public int getTotalPlayers() {
        return playerCurrentScores.size();
    }

    /**
     * Clears all entries from the leaderboard.
     */
    public void clear() {
        scoreToPlayers.clear();
        playerCurrentScores.clear();
    }
}
