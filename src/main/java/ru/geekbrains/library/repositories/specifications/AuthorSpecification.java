package ru.geekbrains.library.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.library.model.Author;

public class AuthorSpecification {
    private static Specification<Author> nameLike(String namePart) {
        return (Specification<Author>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name"))
                        , String.format("%%%s%%", namePart).toLowerCase());
    }

    public static Specification<Author> build(MultiValueMap<String, String> params) {
        Specification<Author> spec = Specification.where(null);
        if (params.containsKey("name") && !params.getFirst("name").isBlank()) {
            spec = spec.and(AuthorSpecification.nameLike(params.getFirst("name")));
        }
        return spec;
    }
}
