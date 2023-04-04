package wood.mike.reactive;

import java.util.Set;

public record BookDto (
    Long id,
    String name,
    Set<GenreDto> genres
){}
