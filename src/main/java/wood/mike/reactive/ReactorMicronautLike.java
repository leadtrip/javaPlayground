package wood.mike.reactive;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReactorMicronautLike {

    public static void main(String[] args) {
        new ReactorMicronautLike();
    }

    public ReactorMicronautLike() {
        buildDbEntityFromDto();
    }

    /**
     * Recreating the situation where a DTO is fed into the app via the API and we have to convert it to an entity.
     * There's a many to many relationship between the Book and Genre and we're only supplying the genre IDs through the
     * API e.g.
     *
     * {
     *     "id": 1
     *     "name": "The shining",
     *     "genres": [
     *          1,
     *          2
     *     ]
     * }
     */
    private void buildDbEntityFromDto() {
        BookDto bookDto = new BookDto(1L, "The shining", Set.of(new GenreDto(1L ), new GenreDto(2L )));

        transformDtoToEntity(bookDto)
                .flatMap(bookEntity -> setGenres(bookEntity, bookDto))
                .log()
                .block();
    }

    private Mono<BookEntity> transformDtoToEntity( BookDto bookDto ) {
        return Mono.just(new BookEntity(bookDto.id(), bookDto.name()));
    }

    private Mono<BookEntity> setGenres(BookEntity bookEntity, BookDto bookDto) {
        return entitiesFromDtoIds(bookDto.genres())
                .map(genreEntities -> {
                    bookEntity.setGenres(Set.copyOf(genreEntities));
                    return bookEntity;
                });
    }

    private Mono<List<GenreEntity>> entitiesFromDtoIds(Set<GenreDto> genreDtos) {
        return allRepositoryGenres()
                .filter(genreEntity -> genreDtos.stream().map(GenreDto::id).collect(Collectors.toSet()).contains(genreEntity.getId()))
                .collectList();
    }

    private Flux<GenreEntity> allRepositoryGenres() {
        return Flux.fromIterable(
            Set.of(
                new GenreEntity(1L, "Fiction"),
                new GenreEntity(2L, "Horror"),
                new GenreEntity(3L, "Sci-fi"),
                new GenreEntity(4L, "Comedy"),
                new GenreEntity(5L, "Mystery"))
        );
    }
}
