package wood.mike.reactive;

public record GenreDto (
    Long id,
    String name
){
    public GenreDto(Long id) {
        this(id, null );
    }
}
