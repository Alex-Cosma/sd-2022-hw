package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemFilterRequestDto;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

public class ItemSpecifications {

    public static Specification<Item> similarNames(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), name);
    }

    public static Specification<Item> withAdministratorReviews() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            final Root<User> user = query.from(User.class);
            final Join<Object, Object> role = user.join("roles", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(role.get("name"), ERole.ADMIN)
            );
        };
    }

    public static Specification<Item> specificationsFromFilter(ItemFilterRequestDto filter) {
        final Specification<Item> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getName()).ifPresent(s -> spec.and(similarNames(s)));
        if (filter.getWithAdminReview()) spec.and(withAdministratorReviews());
        return spec;
    }
}
