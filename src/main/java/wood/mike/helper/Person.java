package wood.mike.helper;

import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public Person(final String theForename, final String theSurname, final String theDob) {
        forename = theForename;
        surname = theSurname;
        dob = LocalDate.parse( theDob, DateTimeFormatter.ISO_LOCAL_DATE );
    }

    public Person( Map<String, Object> map ) {
        forename = (String) map.get("forename");
        surname = (String) map.get("surname");
        dob = (LocalDate) map.get("dob");
        dod = (LocalDate) map.get("dod");
    }

    public Person( PersonBuilder builder ) {
        forename = builder.forename;
        surname = builder.surname;
        dob = builder.dob;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(forename, person.forename)
                && Objects.equals(surname, person.surname)
                && Objects.equals(dob, person.dob)
                && Objects.equals(dod, person.dod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename, surname, dob, dod);
    }

    public static class PersonBuilder {
        private final String forename;
        private final String surname;
        private LocalDate dob;
        private LocalDate dod;

        public PersonBuilder( final String fn, final String sn ) {
            forename = fn;
            surname = sn;
        }

        public PersonBuilder( final String fn, final String sn, final String db ) {
            forename = fn;
            surname = sn;
            dob(db);
        }

        public PersonBuilder dob( final String db ) {
            dob = LocalDate.parse( db, DateTimeFormatter.ISO_LOCAL_DATE );
            return this;
        }

        public Person build() {
            Person p = new Person(this );
            return p;
        }
    }
}