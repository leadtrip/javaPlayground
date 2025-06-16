package wood.mike.functional;

import com.google.common.collect.ImmutableList;
import wood.mike.helper.*;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsAll {

    Phone p1 = new Phone(Manufacturer.APPLE, "iPhone 1", OperatingSystem.IOS, ChargePort.LIGHTNING);
    Phone p2 = new Phone(Manufacturer.APPLE, "iPhone 3", OperatingSystem.IOS, ChargePort.LIGHTNING);
    Phone p3 = new Phone(Manufacturer.SAMSUNG, "Galaxy S1", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p4 = new Phone(Manufacturer.SAMSUNG, "Galaxy S3", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p5 = new Phone(Manufacturer.SAMSUNG, "Galaxy S8", OperatingSystem.ANDROID, ChargePort.USB_C);
    Phone p6 = new Phone(Manufacturer.GOOGLE, "Pixel 2", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p7 = new Phone(Manufacturer.GOOGLE, "Pixel 7", OperatingSystem.ANDROID, ChargePort.USB_C);

    Phone[] phones = new Phone[]{p1, p2, p3, p4, p5, p6, p7};

    Sale s1 = new Sale(p1, 200.99);
    Sale s2 = new Sale(p1, 200.99);
    Sale s3 = new Sale(p2, 300.00);
    Sale s4 = new Sale(p3, 199.00);
    Sale s5 = new Sale(p3, 199.00);
    Sale s6 = new Sale(p4, 300.00);
    Sale s7 = new Sale(p6, 200.00);
    Sale s8 = new Sale(p7, 799.00);
    Sale s9 = new Sale(p7, 799.00);
    Sale s10 = new Sale(p7, 799.00);

    Sale[] sales = new Sale[]{s1, s2, s3, s4, s5, s6, s7, s8, s9, s10};

    public ArrayList<Phone> toCollection() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Phone> toList() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toList());
    }

    public List<Phone> toUnmodifiableList() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toUnmodifiableList());
    }

    public Set<Phone> toSet() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toSet());
    }

    public Set<Phone> toUnmodifiableSet() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toUnmodifiableSet());
    }

    public String joining() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .map(p -> p.manufacturer().toString())
                .collect(Collectors.joining());
    }

    public String joining2() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .map(p -> p.manufacturer().toString())
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public Map<Manufacturer, Set<ChargePort>> mapping() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.groupingBy(
                        Phone::manufacturer,
                        Collectors.mapping(
                                Phone::chargePort,  Collectors.toSet())
                ));
    }

    public void flatMapping() {

    }

    public List<Phone> filtering() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.filtering(
                        (p)->p.manufacturer().equals(Manufacturer.SAMSUNG), Collectors.toList()));
    }

    public ImmutableList<Phone> collectingAndThen() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    public Long counting(){
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.counting());
    }

    public Optional<String> minBy() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .filter(p -> p.manufacturer().equals(Manufacturer.APPLE))
                .map(Phone::model)
                .collect(Collectors.minBy(String::compareTo));
    }

    public Optional<String> maxBy() {
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .filter(p -> p.manufacturer().equals(Manufacturer.APPLE))
                .map(Phone::model)
                .collect(Collectors.maxBy(String::compareTo));
    }

    public Integer summingInt() {
        return Stream.of(1,2,3,4,5,6)
                .collect(Collectors.summingInt(Integer::intValue));
    }

    public Double averagingInt() {
        return Stream.of(1,2,3,4,5,6)
                .collect(Collectors.averagingInt(Integer::intValue));
    }

    public Optional<Double> reducing() {
        return Arrays.stream(sales)
                .map(Sale::price)
                .collect(
                        Collectors.reducing((a, b) -> a + b)
                );
    }

    public Double reducing2() {
        return Arrays.stream(sales)
                .map(Sale::price)
                .collect(
                        Collectors.reducing(
                                0.0,
                                (a, b) -> a + b)
                );
    }

    public Double reducing3() {
        return Arrays.stream(sales)
                .collect(
                        Collectors.reducing(
                                0.0,
                                Sale::price,
                                Double::max )
                );
    }

    public Map<Manufacturer, List<Phone>> groupingBy() {
        return Stream.of(phones)
                .collect(Collectors.groupingBy(Phone::manufacturer));
    }

    public Map<Manufacturer, List<ChargePort>> groupingBy2() {
        return Stream.of(phones)
                .collect(
                        Collectors.groupingBy(
                                Phone::manufacturer,
                                Collectors.mapping(Phone::chargePort, Collectors.toList()))
                );
    }

    public TreeMap<Manufacturer, List<ChargePort>> groupingBy3() {
        return Stream.of(phones)
                .collect(
                        Collectors.groupingBy(
                                Phone::manufacturer,
                                TreeMap::new,
                                Collectors.mapping(Phone::chargePort, Collectors.toList())
                        )
                );
    }

    public ConcurrentMap<Manufacturer, List<Phone>> groupingByConcurrent() {
        return Stream.of(phones)
                .collect(
                        Collectors.groupingByConcurrent(Phone::manufacturer)
                );
    }

    public Map<Manufacturer, List<ChargePort>> groupingByConcurrent2() {
        return Stream.of(phones)
                .collect(
                        Collectors.groupingByConcurrent(
                                Phone::manufacturer,
                                Collectors.mapping(Phone::chargePort, Collectors.toList()))
                );
    }
    
    public ConcurrentSkipListMap<Manufacturer, List<ChargePort>> groupingByConcurrent3() {
        return Stream.of(phones)
                .collect(
                        Collectors.groupingByConcurrent(
                                Phone::manufacturer,
                                ConcurrentSkipListMap::new,
                                Collectors.mapping(Phone::chargePort, Collectors.toList())
                        )
                );
    }

    List<Phone> allPhones() {
        return List.of(p1, p2, p3, p4, p5, p6, p7);
    }
}
