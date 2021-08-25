package ru.geekbrains.library.model.filter;

import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Data
public class ModelSorter {
    MultiValueMap<String, String> params;

    public ModelSorter(MultiValueMap<String, String> params) {
        this.params = params;
    }

    public Sort byFiltered() {
        if (!params.containsKey("sort")) {
            return Sort.by("id");
        } else {
            return SortParameter.generateParam(
                    Objects.requireNonNull(
                            params.getFirst("sort")
                    )
            );
        }
    }
}
