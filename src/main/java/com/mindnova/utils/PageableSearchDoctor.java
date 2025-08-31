package com.mindnova.utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;
public class PageableSearchDoctor {

    public static Pageable mapSort(Pageable pageable) {
        Sort sort = pageable.getSort().stream()
                .map(order -> {
                    String property = order.getProperty();

                    switch (property) {
                        case "firstName":
                            return new Sort.Order(order.getDirection(), "profile.firstName");
                        case "lastName":
                            return new Sort.Order(order.getDirection(), "profile.lastName");
                        case "yearOfExperience":
                            return new Sort.Order(order.getDirection(), "profile.yearOfExperience");
                        default:
                            return order;
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Sort::by));

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
