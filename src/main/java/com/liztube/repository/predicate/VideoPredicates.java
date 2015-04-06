package com.liztube.repository.predicate;

import com.liztube.entity.Video;
import com.mysema.query.types.Predicate;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 * Created by laurent on 02/04/15.
 */
public class VideoPredicates{
    /*String keyword;
    @Override
    public javax.persistence.criteria.Predicate toPredicate(Root<Video> root, javax.persistence.criteria.CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get(
                                                root.getModel().getDeclaredSingularAttribute("username", String.class)
                                        )
                                ), "%" + keyword.toLowerCase() + "%"
                ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get(
                                                root.getModel().getDeclaredSingularAttribute("username", String.class)
                                        )
                                ), "%" + keyword.toLowerCase() + "%"
                        )
        ));
    }*/
    /*public static Predicate matchWithKeywords(final String keywords) {
        QUserLiztube user = QUserLiztube.userLiztube;
        return user.email.eq(email);
    }*/

}
