package wood.mike.helper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class Person {

    private final String forename;
    private final String surname;
    private final LocalDate dob;
    private LocalDate dod;

    public Person(final String theForename, final String theSurname, final LocalDate theDob) {
        forename = theForename;
        surname = theSurname;
        dob = theDob;
    }

    public Person( Map<String, Object> map ) {
        forename = (String) map.get("forename");
        surname = (String) map.get("surname");
        dob = (LocalDate) map.get("dob");
        dod = (LocalDate) map.get("dod");
    }

    public String getName() { return forename + " " + surname; }

    public long getAge() {
        return ChronoUnit.YEARS.between(dob, Objects.requireNonNullElseGet(dod, LocalDate::now));
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDod( final LocalDate theDod ) {
        dod = theDod;
    }

    public LocalDate getDod() {
        return dod;
    }

    public int ageDifference(final Person other) {
        return (int) (getAge() - other.getAge());
    }

    public String toString() {
        return String.format("%s - %d%s", getName(), getAge(), getDod() != null ? " (DOD " + dod.getYear() + ")" : "");
    }
}