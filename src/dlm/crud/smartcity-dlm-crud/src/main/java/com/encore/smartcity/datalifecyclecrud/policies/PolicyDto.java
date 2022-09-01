package com.encore.smartcity.datalifecyclecrud.policies;

import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Policy 테이블의 데이터를 전달하기 위한 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyDto {

    private String policyId;
    private String policyName;
    private String srcPathName;
    private String srcFileName;
    private String destPathName;
    private String destFileName;
    private PolicyStatus status;
    private LocalDateTime createdDate;
    private String createdUser;
    private LocalDateTime modifiedDate;
    private String modifiedUser;
    private LocalDateTime setupDate;
    private List<String> ruleList;
    private List<PolicyRuleDto> policyRuleRelations;

    public PolicyDto(Policy policy) {
        policyId = policy.getPolicyId();
        policyName = policy.getPolicyName();
        srcPathName = policy.getSrcPathName();
        srcFileName = policy.getSrcFileName();
        destPathName = policy.getDestPathName();
        destFileName = policy.getDestFileName();
        status = policy.getStatus();
        createdDate = policy.getCreatedDate();
        createdUser = policy.getCreatedUser();
        modifiedDate = policy.getModifiedDate();
        modifiedUser = policy.getModifiedUser();
        setupDate = policy.getSetupDate();
        ruleList = policy.getRuleList();
        policyRuleRelations = policy.getPolicyRuleRelations().stream()
                .map(relation -> new PolicyRuleDto(relation))
                .collect(Collectors.toList());

    }
}
