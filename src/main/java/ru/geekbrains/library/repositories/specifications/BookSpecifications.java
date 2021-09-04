package ru.geekbrains.library.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.library.model.Book;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookSpecifications {
    private static Specification<Book> titleLike(String titlePart) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title"))
                        , String.format("%%%s%%", titlePart).toLowerCase());
    }

    private static Specification<Book> genreEquals(Long genreId) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isMember(genreId, root.get("genres"));
    }

    private static Specification<Book> discountExist(Long discount) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("discount"), 0);
    }

    private static Specification<Book> discountNotExist(Long discount) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("discount"), 0);
    }

    private static Specification<Book> advices(Boolean advice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("editorsAdvice"), advice);
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
        if (params.containsKey("editorsAdvice") && !params.getFirst("editorsAdvice").isBlank()) {
            spec = spec.and(BookSpecifications.advices(Boolean.parseBoolean(params.getFirst("editorsAdvice"))));
        }
        if (params.containsKey("year_of_publish") && !params.getFirst("year_of_publish").isBlank()) {
            spec = spec.and(BookSpecifications.yearEquals(Integer.parseInt(params.getFirst("year_of_publish"))));
        }
        if (params.containsKey("discount") && !params.getFirst("discount").isBlank()) {
            Long discount = Long.parseLong(Objects.requireNonNull(params.getFirst("discount")));
            if (discount <= 0) {
                spec = spec.and(BookSpecifications.discountNotExist(discount));
            } else {
                spec = spec.and(BookSpecifications.discountExist(discount));
            }
        }
        if (params.containsKey("genre") && !params.getFirst("genre").isBlank()) {
            List<Long> genres = params.get("genre")
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            for (Long genreId : genres) {
                spec = spec.and(BookSpecifications.genreEquals(genreId));
            }
        }
        return spec;
    }

}