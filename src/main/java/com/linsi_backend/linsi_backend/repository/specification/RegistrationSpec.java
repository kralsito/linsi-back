package com.linsi_backend.linsi_backend.repository.specification;

import com.linsi_backend.linsi_backend.model.Registration;
import com.linsi_backend.linsi_backend.model.RegistrationStatusType;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;

public class RegistrationSpec {
    public static Specification<Registration> getSpec(RegistrationFilterDTO filter){
        return (root, query, cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null){
                predicates.add(cb.equal(root.get("firstName"), filter.getFirstName()));
            }

            if (filter.getLastName() != null){
                predicates.add(cb.equal(root.get("lastName"), filter.getLastName()));
            }

            if (filter.getDni() > 0){
                predicates.add(cb.equal(root.get("dni"), filter.getDni()));
            }

            if (filter.getFile() > 0){
                predicates.add(cb.equal(root.get("file"), filter.getFile()));
            }

            if (filter.getEmail() != null){
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
            }

            if (filter.getRegistrationStatusType() != null){
                predicates.add(cb.equal(root.get("registrationStatusType"), filter.getRegistrationStatusType()));
            }

            if (filter.getArea_id() != null){
                predicates.add(cb.equal(root.get("area").get("id"), filter.getArea_id()));
            }

            if(filter.getConfirmed()){
                predicates.add(cb.or(
                        cb.equal(root.get("registrationStatusType"), RegistrationStatusType.CONFIRMED),
                        cb.equal(root.get("registrationStatusType"), RegistrationStatusType.PENDING)
                ));
            }else{
                predicates.add(cb.equal(root.get("registrationStatusType"), RegistrationStatusType.PENDING));
            }

            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
