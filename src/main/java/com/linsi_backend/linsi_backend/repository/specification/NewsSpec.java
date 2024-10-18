package com.linsi_backend.linsi_backend.repository.specification;

import com.linsi_backend.linsi_backend.model.News;
import com.linsi_backend.linsi_backend.service.dto.request.NewsFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class NewsSpec {

    public static Specification<News> getSpec(NewsFilterDTO filter) {
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            // Filtro por título
            if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
            }

            // Filtro por usuario
            if (filter.getUser_id() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUser_id()));
            }

            // Filtro por fecha de publicación (si deseas filtrar por fechas específicas o rangos)
            if (filter.getPublicationDate() != null) {
                predicates.add(cb.equal(root.get("publicationDate"), filter.getPublicationDate()));
            }


            // Ordenar por el más reciente
            query.orderBy(cb.desc(root.get("publicationDate")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
