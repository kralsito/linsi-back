package com.linsi_backend.linsi_backend.repository.specification;


import com.linsi_backend.linsi_backend.model.Role;
import com.linsi_backend.linsi_backend.service.dto.request.RoleFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class RoleSpec {
    public static Specification<Role> getSpec(RoleFilterDTO filter){
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null){
                predicates.add(cb.equal(root.get("name"), filter.getName()));
            }

            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
