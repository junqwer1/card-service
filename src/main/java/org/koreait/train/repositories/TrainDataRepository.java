package org.koreait.train.repositories;

import org.koreait.train.entities.TrainData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainDataRepository extends JpaRepository<TrainData, Long>, QuerydslPredicateExecutor<TrainData> {
}
