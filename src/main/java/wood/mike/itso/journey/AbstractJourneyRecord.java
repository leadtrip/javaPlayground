package wood.mike.itso.journey;

import wood.mike.itso.ItsoElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractJourneyRecord implements ItsoElement {

    protected final List<ItsoElement> fields = new ArrayList<>();

    @Override
    public String toTransportFormat() {
        return fields.stream()
                .map(ItsoElement::toTransportFormat)
                .collect(Collectors.joining(","));
    }
}