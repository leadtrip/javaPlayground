package wood.mike.functional;

import lombok.extern.slf4j.Slf4j;
import wood.mike.helper.ChargePort;
import wood.mike.helper.Manufacturer;
import wood.mike.helper.OperatingSystem;
import wood.mike.helper.Phone;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GroupingBy {

    Phone p1 = new Phone(Manufacturer.APPLE, "iPhone 1", OperatingSystem.IOS, ChargePort.LIGHTNING);
    Phone p2 = new Phone(Manufacturer.APPLE, "iPhone 3", OperatingSystem.IOS, ChargePort.LIGHTNING);
    Phone p3 = new Phone(Manufacturer.SAMSUNG, "Galaxy S1", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p4 = new Phone(Manufacturer.SAMSUNG, "Galaxy S3", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p5 = new Phone(Manufacturer.SAMSUNG, "Galaxy S8", OperatingSystem.ANDROID, ChargePort.USB_C);
    Phone p6 = new Phone(Manufacturer.GOOGLE, "Pixel 2", OperatingSystem.ANDROID, ChargePort.MICRO_USB);
    Phone p7 = new Phone(Manufacturer.GOOGLE, "Pixel 7", OperatingSystem.ANDROID, ChargePort.USB_C);

    Phone[] phones = new Phone[]{p1, p2, p3, p4, p5, p6, p7};

    record PhoneMfrTotal(Manufacturer manufacturer, Long total) {}

    public static void main(String[] args) {
        GroupingBy groupingBy = new GroupingBy();
        groupingBy.listPhonesByMfr();
        groupingBy.listPhoneModelsByMfr();
        groupingBy.countPhonesByMfr();
        groupingBy.listPhonesByMfrAndChargePort();
        groupingBy.countPhonesByMfrReturningRecord();
        groupingBy.enumMap();
    }

    private void listPhonesByMfr() {
        Map<String, List<Phone>> phonesByMfr = Arrays.stream(phones).collect(Collectors.groupingBy(
                phone -> phone.manufacturer().name()
        ));

      phonesByMfr.forEach((k, v) -> log.info("{} {}", k, v));
    }

    private void listPhoneModelsByMfr() {
        Map<String, List<String>> phoneModelsByMfr =
                Arrays.stream(phones).collect(
                        Collectors.groupingBy(
                    phone -> phone.manufacturer().name(),
                            Collectors.mapping(
                                Phone::model,
                                Collectors.toList()
                            )
        ));

        phoneModelsByMfr.forEach((k, v) -> log.info("{} {}", k, v));
    }

    /**
     * Perform a secondary grouping for multiple groupings, manufacturer and charge port
     */
    private void listPhonesByMfrAndChargePort() {
        Map<String, Map<String, List<Phone>>> phonesByMfrAndChargePort = Arrays.stream(phones)
                .collect(Collectors.groupingBy(
                        phone -> phone.manufacturer().name(), // Outer grouping: by manufacturer
                        Collectors.groupingBy(
                                phone -> phone.chargePort().name() // Inner grouping: by charge port
                        )
                ));

        for(Map.Entry<String, Map<String, List<Phone>>> entry : phonesByMfrAndChargePort.entrySet()) {
            log.info("{}", entry.getKey());
            entry.getValue().forEach((k, v) -> log.info("\t{} {}", k, v));
        }
    }

    private void countPhonesByMfr() {
        Map<String, Long> phoneCountsByMfr = Arrays.stream(phones).collect(Collectors.groupingBy(
                phone -> phone.manufacturer().name(),
                Collectors.counting()
        ));

        phoneCountsByMfr.forEach((k, v) -> log.info("{} {}", k, v));
    }

    private void countPhonesByMfrReturningRecord() {
        Map<Manufacturer, PhoneMfrTotal> phoneMfrTotalsMap = Arrays.stream(phones)
                .collect(Collectors.groupingBy(
                        Phone::manufacturer,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                total -> new PhoneMfrTotal(null, total)
                        )
                ));

        phoneMfrTotalsMap.forEach((mfr, totalRecord) ->
                phoneMfrTotalsMap.put(mfr, new PhoneMfrTotal(mfr, totalRecord.total()))
        );

        List<PhoneMfrTotal> phoneMfrTotalsList = new ArrayList<>(phoneMfrTotalsMap.values());

        log.info("{}", phoneMfrTotalsList);
    }

    private void enumMap() {
        EnumMap<Manufacturer, List<Phone>> phonesByEnumMfr =
                Arrays.stream(phones)
                .collect(Collectors.groupingBy(
                    Phone::manufacturer, () -> new EnumMap<>(Manufacturer.class), Collectors.toList()
                ));
        phonesByEnumMfr.forEach((k, v) -> log.info("{} {}", k, v));
    }
}
