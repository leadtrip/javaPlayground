package wood.mike.reactive.micronautlike;

public record GenreDto (
    Long id,
    String name
){
    public GenreDto(Long id) {
        this(id, null );
    }
}
