package wood.mike.reactive.mapping;

import reactor.core.publisher.Mono;

/**
 * map just returns a value whereas flatmap returns another Mono or Flux
 */
public class ReactorMapFlatMap {

    public static void main(String[] args) {
        new ReactorMapFlatMap();
    }

    public ReactorMapFlatMap() {
        var person = new PersonDto(null, "Mike");
        logPerson(person);
        person = create(person).block();
        logPerson(person);
    }

    private void logPerson(PersonDto dto) {
        System.out.printf("Person id %s name %s%n", dto.id, dto.name);
    }

    private Mono<PersonDto> create(PersonDto person) {
        return Mono.just(person)
                .flatMap(this::saveToDb)
                //.map(entity -> new PersonDto(entity.id, entity.name))     can do this
                .map(this::toDto)                                        // or this
                ;
    }

    private PersonDto toDto(PersonEntity entity) {
        return new PersonDto(entity.id, entity.name);
    }

    private Mono<PersonEntity> saveToDb(PersonDto dto) {
        return Mono.just(dto)
                .map(this::toEntity);
    }

    private PersonEntity toEntity(PersonDto dto) {
        return new PersonEntity(1L, dto.name);
    }


    record PersonDto (Long id, String name ){}
    record PersonEntity(Long id, String name ){}
}
