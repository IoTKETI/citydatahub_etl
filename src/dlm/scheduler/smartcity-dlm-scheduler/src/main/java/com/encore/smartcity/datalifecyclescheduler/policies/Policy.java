package com.encore.smartcity.datalifecyclescheduler.policies;

import com.encore.smartcity.datalifecyclescheduler.policies.history.PolicyExecutHist;
import com.encore.smartcity.datalifecyclescheduler.policyrulerelations.PolicyRuleRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Policy 테이블의 필드가 정의된 클래스
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

    @OneToMany(mappedBy = "policy")
    @JsonIgnore
    @Transient
    private List<PolicyExecutHist> policyExecutHists = new ArrayList<>();

}