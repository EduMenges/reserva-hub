package com.reservahub.backend.app.room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.room.dto.RoomFilterDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class RoomFilter {
    public static Specification<Room> dynamicRoomFilter(final RoomFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            addBasicFilters(predicates, filter, root, criteriaBuilder);
            addResourceFilters(predicates, filter, root, query, criteriaBuilder);
            addDateFilters(predicates, filter, root, query, criteriaBuilder);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addBasicFilters(List<Predicate> predicates, RoomFilterDto filter, Root<Room> root,
            CriteriaBuilder criteriaBuilder) {
        if (filter.getRoomType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), filter.getRoomType()));
        }
        if (filter.getCapacity() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), filter.getCapacity()));
        }
        if (filter.getRoomNumber() != null) {
            predicates.add(criteriaBuilder.equal(root.get("roomNumber"), filter.getRoomNumber()));
        }
        if (filter.getBuildingNumber() != null) {
            predicates.add(criteriaBuilder.equal(root.get("buildingNumber"), filter.getBuildingNumber()));
        }
    }

    private static void addResourceFilters(List<Predicate> predicates, RoomFilterDto filter, Root<Room> root,
            CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (filter.getResources() != null && !filter.getResources().isEmpty()) {
            for (String requiredResource : filter.getResources()) {
                Subquery<Room> resourceSubquery = query.subquery(Room.class);
                Root<Room> subqueryRoot = resourceSubquery.from(Room.class);
                Join<Room, Room.ResourceEnum> resourcesJoin = subqueryRoot.join("resources", JoinType.INNER);

                resourceSubquery.select(subqueryRoot)
                        .where(criteriaBuilder.equal(subqueryRoot.get("id"), root.get("id")),
                                criteriaBuilder.equal(resourcesJoin, Room.ResourceEnum.valueOf(requiredResource)));

                predicates.add(criteriaBuilder.exists(resourceSubquery));
            }
        }
    }

    private static void addDateFilters(List<Predicate> predicates, RoomFilterDto filter, Root<Room> root,
            CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (filter.getDate() != null && filter.getStartTime() != null && filter.getEndTime() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Reservation> reservationRoot = subquery.from(Reservation.class);

            Predicate activeStatusPredicate = criteriaBuilder.equal(reservationRoot.get("status"),
                    Reservation.ReservationStatus.ACTIVE);
            Predicate datePredicate = criteriaBuilder.equal(reservationRoot.get("date"), filter.getDate());
            Predicate startTimePredicate = criteriaBuilder.lessThanOrEqualTo(reservationRoot.get("startTime"),
                    filter.getEndTime());
            Predicate endTimePredicate = criteriaBuilder.greaterThanOrEqualTo(reservationRoot.get("endTime"),
                    filter.getStartTime());

            Predicate conflictPredicate = criteriaBuilder.and(activeStatusPredicate, datePredicate, startTimePredicate,
                    endTimePredicate);

            subquery.select(reservationRoot.get("room").get("id")).where(conflictPredicate);

            predicates.add(criteriaBuilder.not(root.get("id").in(subquery)));
        }
    }
}
