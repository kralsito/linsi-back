package com.linsi_backend.linsi_backend.repository.specification;

import com.linsi_backend.linsi_backend.model.Project;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class ProjectSpec {
    public static Specification<Project> getSpec(ProjectFilterDTO filter) {
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(cb.equal(root.get("name"), filter.getName()));
            }

            if (filter.getDescription() != null) {
                predicates.add(cb.equal(root.get("description"), filter.getDescription()));
            }

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), filter.getEndDate()));
            }

            if (filter.getUser_id() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUser_id()));
            }


            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
