package com.encore.smartcity.datalifecyclescheduler.hadoop.scheduler;

import com.encore.smartcity.datalifecyclescheduler.hadoop.services.HadoopService;
import com.encore.smartcity.datalifecyclescheduler.policies.Policy;
import com.encore.smartcity.datalifecyclescheduler.policies.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;

/**
 * 매일 0시 0분 1초에 HDFS의 파일의 이관 및 삭제를 실행하기 위한 스케줄러 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HadoopScheduler {

    private final HadoopService hadoopService;
    private final PolicyRepository policyRepository;
    private static Long count;

    /**
     * 파일 이관 실행 메소드
     * @throws URISyntaxException
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void runMove() throws URISyntaxException {
        count = 1L;
        count = runMoveScheduling(count);
    }

    /**
     * 파일 삭제 실행 메소드
     * @throws URISyntaxException
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void runDelete() throws URISyntaxException {
        policyRepository.clear();
        runDeleteScheduling(count);
    }

    /**
     * 파일 이관 메소드
     * @param count
     * @return
     * @throws URISyntaxException
     */
    public Long runMoveScheduling(Long count) throws URISyntaxException {
        List<Policy> policies = policyRepository.findStatusActive();

        for (Policy policy : policies) {
            Long newCount = hadoopService.moveFile(policy, count);
            count = newCount;
        }
        return count;
    }

    /**
     * 파일 삭제 메소드
     * @param count
     * @throws URISyntaxException
     */
    public void runDeleteScheduling(Long count) throws URISyntaxException {
        policyRepository.clear();
        List<Policy> policies = policyRepository.findStatusActive();

        for (Policy policy : policies) {
            Long newCount = hadoopService.deleteFile(policy, count);
            count = newCount;
        }
    }
}
