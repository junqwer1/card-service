package org.koreait.train.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainData {

    @Id @GeneratedValue
    private Long seq;

    private double item1;
    private double item2;
    private double item3;
    private double item4;
    private double item5;

    private int target; // 정답

    @Column(length = 40)
    private String targetDescription;
    private boolean done; // true - 훈련 완료, false - 훈련 예정
}
