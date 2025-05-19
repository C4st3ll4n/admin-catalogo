package br.com.ph3nr1qu3.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    public static <T>Specification<T> like(final String prop, final String term){
       return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), "%" + term.toUpperCase() + "%");
    }
}
