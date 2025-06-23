package wood.mike.concurrency;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapPlay {

    private ZonedDateTime baseDateTime;

    public static void main(String[] args) {
        ConcurrentSkipListMapPlay play = new ConcurrentSkipListMapPlay();
        play.run();
    }

    private void run() {
        ConcurrentSkipListMap<ZonedDateTime, String> events = buildAndInit();

        System.out.println("Latest events:");
        printEvents(events.headMap(getBaseDateTime().minusMinutes(8)));

        System.out.println("Oldest events:");
        printEvents(events.tailMap(getBaseDateTime().minusMinutes(2)));

        System.out.println("Middle events:");
        printEvents(events.subMap(baseDateTime.minusMinutes(8), baseDateTime.minusMinutes(2)));
    }

    private ConcurrentSkipListMap<ZonedDateTime, String> buildAndInit() {
        ConcurrentSkipListMap<ZonedDateTime, String> events = new ConcurrentSkipListMap<>(
                Comparator.comparing(
                        v -> v.toInstant().toEpochMilli()
                )
        );

        for(Event event: initialEvents()) {
            events.put(event.time, event.message);
        }

        return events;
    }

    private void printEvents(Map<ZonedDateTime, String> events) {
        events.forEach((key, value) -> {
            System.out.println(STR."\{key} : \{value}");
        });
    }

    private ZonedDateTime getBaseDateTime() {
        if (baseDateTime == null) {
            baseDateTime = ZonedDateTime.now();
        }
        return baseDateTime;
    }

    private List<Event> initialEvents() {
        List<Event> events = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
                events.add(new Event(getBaseDateTime().minusMinutes(i), STR."Message \{i}"));
        }
        return events;
    }

    record Event(ZonedDateTime time, String message) {}
}
