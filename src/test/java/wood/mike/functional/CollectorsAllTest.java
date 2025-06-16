package wood.mike.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wood.mike.helper.ChargePort;
import wood.mike.helper.Manufacturer;
import wood.mike.helper.Phone;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.junit.jupiter.api.Assertions.*;

public class CollectorsAllTest {

    private CollectorsAll collectorsAll;

    @BeforeEach
    public void setUp() {
        collectorsAll = new CollectorsAll();
    }

    @Test
    void testToCollection() {
        ArrayList<Phone> phones = collectorsAll.toCollection();
        assertEquals(collectorsAll.allPhones().size(), phones.size());
    }

    @Test
    void testToList() {
        List<Phone> phones = collectorsAll.toList();
        assertEquals(collectorsAll.allPhones().size(), phones.size());
    }

    @Test
    void testToSet() {
        Set<Phone> phones = collectorsAll.toSet();
        assertEquals(collectorsAll.allPhones().size(), phones.size());
    }

    @Test
    void testToUnmodifiableSet() {
        Set<Phone> phones = collectorsAll.toUnmodifiableSet();
        assertEquals(collectorsAll.allPhones().size(), phones.size());
    }

    @Test
    void testJoining() {
        String mfrString = collectorsAll.joining();
        assertEquals("APPLEAPPLESAMSUNGSAMSUNGSAMSUNGGOOGLEGOOGLE", mfrString);
    }

    @Test
    void testJoining2() {
        String mfrString = collectorsAll.joining2();
        assertEquals("[APPLE, APPLE, SAMSUNG, SAMSUNG, SAMSUNG, GOOGLE, GOOGLE]",  mfrString);
    }

    @Test
    void testMapping() {
        Map<Manufacturer, Set<ChargePort>> mapping = collectorsAll.mapping();
        assertEquals(mapping.get(Manufacturer.SAMSUNG), Set.of(ChargePort.MICRO_USB, ChargePort.USB_C));
        assertEquals(mapping.get(Manufacturer.APPLE),  Set.of(ChargePort.LIGHTNING));
        assertEquals(mapping.get(Manufacturer.GOOGLE), Set.of(ChargePort.MICRO_USB, ChargePort.USB_C));
    }

    @Test
    void testFiltering() {
        List<Phone> phones = collectorsAll.filtering();
        assertEquals(3, phones.size());
    }

    @Test
    void testCollectingAndThen() {
        List<Phone> phones = collectorsAll.collectingAndThen();
        assertEquals(collectorsAll.allPhones().size(), phones.size());
    }

    @Test
    void testCounting() {
        Long count = collectorsAll.counting();
        assertEquals(collectorsAll.allPhones().size(), count);
    }

    @Test
    void minBy() {
        Optional<String> phone = collectorsAll.minBy();
        assertEquals("iPhone 1", phone.get());
    }

    @Test
    void maxBy() {
        Optional<String> phone = collectorsAll.maxBy();
        assertEquals("iPhone 3", phone.get());
    }

    @Test
    void summingInt() {
        Integer i = collectorsAll.summingInt();
        assertEquals(21, i);
    }

    @Test
    void testSummingDouble() {
        Double v = collectorsAll.summingDouble();
        assertEquals(3996.98, v);
    }

    @Test
    void testAveragingInt() {
        Double d =  collectorsAll.averagingInt();
        assertEquals(3.5, d);
    }

    @Test
    void testReducing1() {
        Optional<Double> o = collectorsAll.reducing();
        assertEquals(3996.98, o.get());
    }

    @Test
    void testReducing2() {
        Double d = collectorsAll.reducing2();
        assertEquals(3996.98, d);
    }

    @Test
    void testReducing3() {
        Double d = collectorsAll.reducing3();
        assertEquals(799.00, d);
    }

    @Test
    void testGrouping() {
        Map<Manufacturer, List<Phone>> manufacturerListMap = collectorsAll.groupingBy();
        assertEquals(3, manufacturerListMap.get(Manufacturer.SAMSUNG).size());
    }

    @Test
    void testGroupingBy2() {
        Map<Manufacturer, List<ChargePort>> manufacturerListMap = collectorsAll.groupingBy2();
        assertEquals(2, manufacturerListMap.get(Manufacturer.GOOGLE).size());
    }

    @Test
    void testGroupingBy3() {
        TreeMap<Manufacturer, List<ChargePort>> manufacturerListTreeMap = collectorsAll.groupingBy3();
        assertEquals(2, manufacturerListTreeMap.get(Manufacturer.APPLE).size());
    }

    @Test
    void testGroupingByConcurrent() {
        ConcurrentMap<Manufacturer, List<Phone>> manufacturerListConcurrentMap = collectorsAll.groupingByConcurrent();
        assertEquals(3, manufacturerListConcurrentMap.get(Manufacturer.SAMSUNG).size());
    }

    @Test
    void testGroupingByConcurrent2() {
        Map<Manufacturer, List<ChargePort>> manufacturerListMap = collectorsAll.groupingByConcurrent2();
        assertEquals(2, manufacturerListMap.get(Manufacturer.GOOGLE).size());
    }

    @Test
    void testGroupingByConcurrent3() {
        ConcurrentSkipListMap<Manufacturer, List<ChargePort>> manufacturerListTreeMap = collectorsAll.groupingByConcurrent3();
        assertEquals(2, manufacturerListTreeMap.get(Manufacturer.APPLE).size());
    }

    @Test
    void testPartitioningBy() {
        Map<Boolean, List<Phone>> booleanListMap = collectorsAll.partitioningBy();
        assertEquals(3, booleanListMap.get(true).size());
        assertEquals(4, booleanListMap.get(false).size());
    }

    @Test
    void testPartitioningBy2() {
        Map<Boolean, Set<Phone>> booleanListMap = collectorsAll.partitioningBy2();
        assertEquals(3, booleanListMap.get(true).size());
        assertEquals(4, booleanListMap.get(false).size());
    }

    @Test
    void testToMap() {
        Map<String, Manufacturer> map = collectorsAll.toMap();
        assertEquals(Manufacturer.SAMSUNG, map.get("Galaxy S1"));
        assertEquals(Manufacturer.APPLE, map.get("iPhone 1"));
    }

    @Test
    void testToMap2() {
        Map<Manufacturer, Phone> map = collectorsAll.toMap2();
        assertEquals(map.get(Manufacturer.APPLE), collectorsAll.allPhones().get(0));
    }

    @Test
    void testToMap3() {
        Map<Manufacturer, Phone> map = collectorsAll.toMap3();
        assertEquals(map.get(Manufacturer.APPLE), collectorsAll.allPhones().get(1));
    }

    @Test
    void testSummarizingDouble() {
        DoubleSummaryStatistics doubleSummaryStatistics = collectorsAll.summarizingDouble();
        assertEquals(3996.98, doubleSummaryStatistics.getSum());
        assertEquals(799.00, doubleSummaryStatistics.getMax());
        assertEquals(199.00, doubleSummaryStatistics.getMin());
        assertEquals(400.00, Math.round(doubleSummaryStatistics.getAverage()));
        assertEquals(10,  doubleSummaryStatistics.getCount());
    }

    @Test
    void testTeeing() {
        String s = collectorsAll.teeing();
        assertEquals("Average: 399.70, Sum: 3996.98", s);
    }
}
