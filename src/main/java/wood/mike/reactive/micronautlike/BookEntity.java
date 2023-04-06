package wood.mike.reactive.micronautlike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    private Long id;
    private String name;
    private Set<GenreEntity> genres;

    public BookEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
