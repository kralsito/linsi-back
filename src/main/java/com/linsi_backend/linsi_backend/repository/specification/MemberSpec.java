package com.linsi_backend.linsi_backend.repository.specification;

import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.service.dto.request.MemberFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class MemberSpec {
    public static Specification<Member> getSpec(MemberFilterDTO filter){
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null){
                predicates.add(cb.equal(root.get("firstName"), filter.getFirstName()));
            }

            if (filter.getLastName() != null){
                predicates.add(cb.equal(root.get("lastName"), filter.getLastName()));
            }

            if (filter.getEmail() != null){
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
            }

            if (filter.getRole_id() != null){
                predicates.add(cb.equal(root.get("role").get("id"), filter.getRole_id()));
            }

            if (filter.getUser_id() != null){
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUser_id()));
            }


            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
