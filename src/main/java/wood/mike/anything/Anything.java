package wood.mike.anything;

import java.util.*;

public class Anything {
    public static void main(String[] args) {
        FilterCategory filterCategory = new FilterCategory();
        filterCategory.tags.add(new Tag("zz_auto_213"));
        filterCategory.tags.add(new Tag("zz_b"));
        filterCategory.tags.add(new Tag("zz_a"));

        List<String> tags = filterCategory.toList();

        var res = tags.isEmpty() ? "" : tags
                .stream()
                .sorted()
                .filter(tag -> !tag.startsWith("zz_auto_"))
                .findFirst()
                .orElse(tags.getFirst());

        System.out.println(res);
    }
}

record Tag(String name){}


class FilterCategory {
    List<Tag> tags = new ArrayList<>();

    public List<String> toList() {
        return tags.stream().map(Tag::name).toList();
    }
}
