package com.encore.smartcity.datalifecyclecrud.policies;

import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Policy 테이블의 필드 정의 및 생성 메소드를 정의한 클래스
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "POLICY")
public class Policy implements Serializable {

    @Id
    @Column(name = "POLICY_ID")
    private String policyId;

    @Column(name = "POLICY_NM")
    private String policyName;

    @Column(name = "SRC_PATH_NM")
    private String srcPathName;

    @Column(name = "SRC_FILE_NM")
    private String srcFileName;

    @Column(name = "DST_PATH_NM")
    private String destPathName;

    @Column(name = "DST_FILE_NM")
    private String destFileName;

    @Column(name = "USE_YN")
    @Enumerated(EnumType.STRING)
    private PolicyStatus status;

    @Column(name = "REG_DTM")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDateTime createdDate;

    @Column(name = "REGR_ID")
    private String createdUser;

    @Column(name = "MOD_DTM")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDateTime modifiedDate;

    @Column(name = "MODR_ID")
    private String modifiedUser;

    @Column(name = "POLICY_SETUP_DTM")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDateTime setupDate;

    @Transient
    private List<String> ruleList;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PolicyRuleRelation> policyRuleRelations = new ArrayList<>();

    //==Relation Method==//

    /**
     * Policy_Rule_R 테이블 생성을 위한 메소드
     * @param policyRuleRelation
     */
    public void addPolicyRule(PolicyRuleRelation policyRuleRelation) {
        policyRuleRelations.add(policyRuleRelation);
        policyRuleRelation.setPolicy(this);
    }

    //==Create Method==//

    /**
     * Policy 테이블의 시스템 변수들(등록일시, 등록자ID, 수정일시, 수정자ID)과 관계 테이블을 생성하는 메소드
     * @param policy
     * @param policyRuleRelations
     * @return
     */
    public static Policy create(Policy policy, List<PolicyRuleRelation> policyRuleRelations) {
        for(PolicyRuleRelation policyRuleRelation : policyRuleRelations) {
            policy.addPolicyRule(policyRuleRelation);
        }
        policy.setCreatedDate(LocalDateTime.now());
        policy.setCreatedUser("user");
        policy.setModifiedDate(LocalDateTime.now());
        policy.setModifiedUser("user");
        policy.setSetupDate(LocalDateTime.now());
        return policy;
    }

    //==Modify Method==//

    /**
     * Policy 테이블 수정 시 관계 테이블을 다시 생성하는 메소드
     * @param policy
     * @param policyRuleRelations
     * @return
     */
    public static Policy updatePolicy(Policy policy, List<PolicyRuleRelation> policyRuleRelations) {
        for(PolicyRuleRelation policyRuleRelation : policyRuleRelations) {
            policy.addPolicyRule(policyRuleRelation);
        }
        return policy;
    }

}