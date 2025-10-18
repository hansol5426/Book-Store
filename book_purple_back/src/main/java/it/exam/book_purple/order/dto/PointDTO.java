package it.exam.book_purple.order.dto;

import it.exam.book_purple.order.entity.PointEntity;
import it.exam.book_purple.order.enums.BookPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointDTO {

    private int pointId;
    private String userId;
    private int amount;
    private BookPoint type;

    public static PointDTO of(PointEntity entity){
        return PointDTO.builder()
                       .pointId(entity.getPointId())
                       .userId(entity.getUser().getUserId())
                       .amount(entity.getAmount())
                       .type(entity.getType())
                       .build();
    }

}
