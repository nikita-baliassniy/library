package ru.geekbrains.library.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.library.model.Book;

public class BookSpecifications {
    private static Specification<Book> titleLike(String titlePart) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    private static Specification<Book> yearEquals(int year) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("year_of_publish"), year);
    }

    private static Specification<Book> priceGreaterThanOrEquals(double minPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Book> priceLesserThanOrEquals(double maxPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Book> build(MultiValueMap<String, String> params) {
        Specification<Book> spec = Specification.where(null);
        if (params.containsKey("min_price") && !params.getFirst("min_price").isBlank()) {
            spec = spec.and(BookSpecifications
                    .priceGreaterThanOrEquals(Double.parseDouble(params.getFirst("min_price"))));
        }
        if (params.containsKey("max_price") && !params.getFirst("max_price").isBlank()) {
            spec = spec.and(BookSpecifications
                    .priceLesserThanOrEquals(Double.parseDouble(params.getFirst("max_price"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(BookSpecifications.titleLike(params.getFirst("title")));
        }
        if (params.containsKey("year_of_publish") && !params.getFirst("year_of_publish").isBlank()) {
            spec = spec.and(BookSpecifications.yearEquals(Integer.parseInt(params.getFirst("year_of_publish"))));
        }
        return spec;
    }

}
