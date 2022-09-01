package com.encore.smartcity.datalifecyclescheduler.baserules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * BaseRule 테이블의 데이터를 전달하기 위한 클래스
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRuleDto {

    private String baseRuleId;
    private String baseRuleName;
    private Long defaultMoveCycle;
    private Long defaultDeleteCycle;
    private String description;
    private String memo;
    private LocalDateTime createdDate;
    private String createdUser;
    private LocalDateTime modifiedDate;
    private String modifiedUser;
}