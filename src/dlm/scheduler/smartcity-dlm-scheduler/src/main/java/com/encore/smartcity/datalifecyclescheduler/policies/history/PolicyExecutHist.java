package com.encore.smartcity.datalifecyclescheduler.policies.history;

import com.encore.smartcity.datalifecyclescheduler.policies.Policy;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

/**
 * Policy_Execut_Hist 테이블의 필드 및 생성자가 정의된 클래스
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "histId")
@Table(name = "POLICY_EXECUT_HIST")
public class PolicyExecutHist {

    @Id
    @Column(name = "HIST_NO")
    private String histId;

    @Column(name = "EXECUT_OC_DTM")
    private LocalDateTime dtm;

    @Column(name = "EXECUT_KND")
    private String kind;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POLICY_ID")
    private Policy policy;

    public PolicyExecutHist(String id, Policy policy, ExecutKnd kind) {
        histId = id;
        this.policy = policy;
        if(kind.equals(ExecutKnd.DELETE)) {
            this.kind = "2";
        } else if(kind.equals(ExecutKnd.MOVE)) {
            this.kind = "1";
        } else {
            this.kind = "0";
        }
        dtm = LocalDateTime.now();
    }
}
