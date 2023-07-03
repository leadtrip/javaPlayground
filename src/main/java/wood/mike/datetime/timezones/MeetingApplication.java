package wood.mike.datetime.timezones;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Zoned date and time conversion in action.
 * This app gives an idea about how to convert from one zoned date and time to another in a distributed system.
 *
 * Consider the following story: Mike wants to book a meeting with Fred in St Louis USA
 * Mike lives in the Europe/London time zone, and he created a calendar appointment by entering a date and time for when a phone call with Fred will occur.
 * A meeting invitation was sent to Fred. When Fred views the invitation, it should reflect his time zone: USA Central.
 * The vital point is that the meeting will be held at the same instant worldwide; therefore, the code uses the Instant object for persistence,
 * and without any time zone knowledge, the object’s value should be only in UTC. The rest of the story is shown in the code comments.
 */
public class MeetingApplication {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 1. Mike (in "Europe/London" time zone) creates a meeting slot with Fred in St Louis.

        // Mike's meeting date and time, UK summer so +1 hour UTC
        String mikeTime = "2022-07-10 10:30:00";

        // Mike's time zone
        ZoneId mikeZone = ZoneId.of("Europe/London");

        // Creating the meeting
        ZonedDateTime parsed = LocalDateTime.parse(mikeTime, formatter).atZone(mikeZone);

        System.out.println("1. Mike booked a meeting according to his time zone at: " + parsed);
        // will print: 2022-07-10T10:30+01:00[Europe/London]

        // 2. Send the calendar invite and save the event
        Instant instant = parsed.toInstant();

        // Invitation (instant) is stored in the database
        System.out.println("2. Mike meeting time saved in database as UTC equivalent: " + instant);
        // will print: 2022-07-10T08:30:00Z

        // Fred in the "America/Chicago" time zone (-6 hours UK) is viewing the meeting DateTime Mike has booked to determine when exactly the meeting is.

        // Initialize Fred time zone.
        ZoneId fredZone = ZoneId.of("America/Chicago");

        // The meeting time is retrieved from the database (instant) with Fred's time zone.
        ZonedDateTime fredTime = ZonedDateTime.ofInstant(instant, fredZone);

        System.out.println("3.1. Fred meeting will be at (formatted): " + fredTime.format(formatter));
        // will print: 2022-07-10 04:30:00

        System.out.println("3.2. Fred meeting will be at: " + fredTime);
        // will print: 2022-07-10T04:30-05:00[America/Chicago]

        // Mike would like to make sure of the meeting time
        System.out.println("4. Again, Mike is checking the meeting time: " + ZonedDateTime
                .ofInstant(instant, mikeZone)
                .format(formatter)); // will print: 2022-07-10 10:30:00
    }
}