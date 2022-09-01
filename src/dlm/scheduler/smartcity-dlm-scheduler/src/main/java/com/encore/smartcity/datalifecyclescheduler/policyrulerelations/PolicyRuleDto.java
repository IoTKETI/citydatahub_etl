package com.encore.smartcity.datalifecyclescheduler.policyrulerelations;

import lombok.Getter;

/**
 * Policy_Rule_R 테이블의 데이터를 전달하기 위한 클래스
 */
@Getter
public class PolicyRuleDto {

    private String ruleId;
    private String ruleName;

    public PolicyRuleDto(PolicyRuleRelation relation) {
        ruleId = relation.getRule().getRuleId();
        ruleName = relation.getRule().getRuleName();
    }
}
