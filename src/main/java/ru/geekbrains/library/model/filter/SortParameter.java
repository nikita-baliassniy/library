package ru.geekbrains.library.model.filter;

import org.springframework.data.domain.Sort;
import ru.geekbrains.library.exceptions.BadSortingRequestException;

import java.util.Arrays;
import java.util.List;

public class SortParameter {

    public static Sort generateParam(String s) {
        String field = null;
        boolean descSorting = false;
        String cleanStr = s.replace(" ", "");
        if (!cleanStr.contains(",")) {
            field = cleanStr;
        } else {
            List<String> list = Arrays.asList(cleanStr.split(","));
            if (!list.contains("desc") && !list.contains("asc") || list.size() != 2) {
                throw new BadSortingRequestException("Некорректный параметр сортировки");
            }
            for (String str : list) {
                switch (str) {
                    case "desc": {
                        descSorting = true;
                        break;
                    }
                    case "asc":
                        break;
                    default:
                        field = str;
                }
            }
        }
        if (descSorting) {
            return Sort.by(field).descending();
        } else {
            return (Sort.by(field)).ascending();
        }
    }
}
