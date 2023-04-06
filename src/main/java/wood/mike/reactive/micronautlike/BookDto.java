package wood.mike.reactive.micronautlike;

import java.util.Set;

public record BookDto (
    Long id,
    String name,
    Set<GenreDto> genres
){}
