package org.basic.feimi.domain.specification;

import org.basic.feimi.infrastructure.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> byEmail(String email) {
        Specification<User> userSpecification = Specification.where(CommonSpecifications.equal("email", email));
        userSpecification = userSpecification.and(CommonSpecifications.notDeleted());
        return userSpecification;
    }

    public static Specification<User> byGeneratedId(String generatedId) {
        Specification<User> userSpecification = Specification.where(CommonSpecifications.equal("generatedId", generatedId));
        userSpecification = userSpecification.and(CommonSpecifications.notDeleted());
        return userSpecification;
    }
}
