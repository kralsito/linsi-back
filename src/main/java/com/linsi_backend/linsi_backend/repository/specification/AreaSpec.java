package com.linsi_backend.linsi_backend.repository.specification;

import com.linsi_backend.linsi_backend.model.Area;
import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.service.dto.request.AreaFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.request.MemberFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class AreaSpec {
    public static Specification<Area> getSpec(AreaFilterDTO filter) {
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(cb.equal(root.get("name"), filter.getName()));
            }

            if (filter.getUser_id() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUser_id()));
            }


            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}