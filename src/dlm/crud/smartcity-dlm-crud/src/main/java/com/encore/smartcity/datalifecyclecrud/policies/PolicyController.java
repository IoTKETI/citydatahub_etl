package com.encore.smartcity.datalifecyclecrud.policies;

import com.encore.smartcity.datalifecyclecrud.common.CommonController;
import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Policy 테이블의 CRUD 및 관련 메소드를 정의한 클래스
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController {

    private final PolicyService policyService;

    private final PolicyRepository policyRepository;

    /**
     * Policy 테이블의 레코드를 생성하는 메소드
     * 정책명, 소스경로명, 소스파일명, 목적경로명, 목적파일명, 사용여부 필드 및 관련된 규칙을 요청 받음
     * 정책명, 소스경로명, 목적경로명, 사용여부 필드와 관련된 규칙은 빈 값을 받을 수 없음
     * 정책명은 중복된 값을 받을 수 없음
     * 결과는 json 형태로 반환
     *
     * @param request
     * @param errors
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity createPolicy(@RequestBody CreatePolicyRequest request,
                                       Errors errors) throws URISyntaxException {
        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(!policyService.checkDuplicatePolicyName(request.getPolicyName())) {
            return CommonController.badRequest(errors);
        }

        Policy policy = new Policy();
        policy.setPolicyName(request.getPolicyName());
        policy.setSrcPathName(request.getSrcPathName());
        policy.setSrcFileName(request.getSrcFileName());
        policy.setDestPathName(request.getDestPathName());
        policy.setDestFileName(request.getDestFileName());
        policy.setStatus(request.getStatus());
        policy.setRuleList(request.getRuleList());

        Policy savedPolicy = policyService.policy(policy);


        URI location = new URI("/api/policy" + savedPolicy.getPolicyId());
        return ResponseEntity.created(location).body(savedPolicy);
    }

    /**
     * Policy 테이블의 레코드 생성 시 요청을 받아오는 내부 클래스
     * 정책명, 소스경로명, 소스파일명, 목적경로명, 목적파일명, 사용여부 필드 및 관련된 규칙을 요청 받음
     * 정책명, 소스경로명, 목적경로명, 사용여부 필드와 관련된 규칙은 빈 값을 받을 수 없음
     *
     */
    @Data
    static class CreatePolicyRequest {
        @NotEmpty
        private String policyName;

        @NotEmpty
        private String srcPathName;

        private String srcFileName;

        @NotEmpty
        private String destPathName;

        private String destFileName;

        @NotEmpty
        private PolicyStatus status;

        @NotEmpty
        private List<String> ruleList;
    }

    /**
     * Policy 테이블의 전체목록을 조회하는 메소드
     * json 형태로 반환
     *
     * @return
     */
    @GetMapping
    public ResponseEntity findPolicies() {

        List<Policy> policies = policyRepository.findAll();
        List<ResponsePolicyDto> result = policies.stream()
                .map(p -> new ResponsePolicyDto(p))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Policy 테이블의 상세내용을 조회하는 메소드
     * 레코드가 존재하지 않을 경우 404 에러 반환
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity findPolicyById(@PathVariable("id") String id) {
        Policy policy = policyRepository.findOneById(id);
        if(policy == null) {
            return ResponseEntity.notFound().build();
        }

        ResponsePolicyDto result = new ResponsePolicyDto(policy);

        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Policy 테이블의 조회시 관계 테이블까지 조회한 결과를 반환하는 처리를 위한 내부 클래스
     */
    @Data
    @AllArgsConstructor
    static class ResponsePolicyDto {
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
        private List<PolicyRuleDto> policyRuleRelations;

        public ResponsePolicyDto(Policy policy) {
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
            policyRuleRelations = policy.getPolicyRuleRelations().stream()
                    .map(relation -> new PolicyRuleDto(relation))
                    .collect(Collectors.toList());

        }
    }

    /**
     * Policy 테이블의 조회 메소드 반환 시 json 형태로 반환하기 위한 내부 클래스
     *
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * Policy 테이블의 레코드를 수정하는 메소드
     * 정책명, 소스경로명, 소스파일명, 목적경로명, 목적파일명, 사용여부 필드 및 관련된 규칙을 요청 받음
     * 정책명, 소스경로명, 목적경로명, 사용여부 필드와 관련된 규칙은 빈 값을 받을 수 없음
     * 정책명은 수정 대상 레코드의 정책명을 제외한 타 레코드와 중복된 값을 받을 수 없음
     * 소스파일명은 수정 대상 레코드의 소스경로명을 제외한 타 레코드와 중복된 값을 받을 수 없음
     * 목적파일명은 수정 대상 레코드의 목적파일명을 제외한 타 레코드와 중복된 값을 받을 수 없음
     *
     * 레코드가 존재하지 않을 경우 404 에러 반환
     * 결과는 json 형태로 반환
     *
     * @param id
     * @param request
     * @param errors
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updatePolicy(@PathVariable("id") String id,
                                       @RequestBody UpdatePolicyRequest request,
                                       Errors errors) {

        Policy oneById = policyService.findOneById(id);

        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        // 정책명이 중복된 경우
        if(!oneById.getPolicyName().equals(request.getPolicyName()) &&
        !policyService.checkDuplicatePolicyName(request.getPolicyName())) {
            return CommonController.badRequest(errors);
        }

        // 소스파일명이 중복된 경우
        if(!oneById.getSrcFileName().equals(request.getSrcFileName()) &&
        !policyService.checkDuplicateSrcFileName(request.getSrcFileName())) {
            return CommonController.badRequest(errors);
        }

        // 목적파일명이 중복된 경우
        if(!oneById.getDestFileName().equals(request.getDestFileName()) &&
                !policyService.checkDuplicateDestFileName(request.getDestFileName())) {
            return CommonController.badRequest(errors);
        }

        PolicyDto policyDto = new PolicyDto();
        policyDto.setPolicyId(id);
        policyDto.setPolicyName(request.getPolicyName());
        policyDto.setSrcPathName(request.getSrcPathName());
        policyDto.setSrcFileName(request.getSrcFileName());
        policyDto.setDestPathName(request.getDestPathName());
        policyDto.setDestFileName(request.getDestFileName());
        policyDto.setStatus(request.getStatus());
        policyDto.setSetupDate(request.getSetupDate());
        policyDto.setRuleList(request.getRuleList());

        Policy updatedPolicy = policyService.update(oneById, policyDto);

        UpdatePolicyResponse response = new UpdatePolicyResponse();
        response.setPolicyId(updatedPolicy.getPolicyId());
        response.setPolicyName(updatedPolicy.getPolicyName());
        response.setSrcPathName(updatedPolicy.getSrcPathName());
        response.setSrcFileName(updatedPolicy.getSrcFileName());
        response.setDestPathName(updatedPolicy.getDestPathName());
        response.setDestFileName(updatedPolicy.getDestFileName());
        response.setStatus(updatedPolicy.getStatus());
        response.setSetupDate(updatedPolicy.getSetupDate());
        response.setRuleList(updatedPolicy.getRuleList());
        response.setModifiedDate(updatedPolicy.getModifiedDate());
        response.setModifiedUser(updatedPolicy.getModifiedUser());

        return ResponseEntity.ok(new Result(response));
    }

    /**
     * Policy 테이블의 레코드 수정 시 요청을 받아오는 내부 클래스
     * 정책명, 소스경로명, 소스파일명, 목적경로명, 목적파일명, 사용여부 필드 및 관련된 규칙을 요청 받음
     * 정책명, 소스경로명, 목적경로명, 사용여부 필드와 관련된 규칙은 빈 값을 받을 수 없음
     *
     */
    @Data
    static class UpdatePolicyRequest {
        @NotEmpty
        private String policyName;
        @NotEmpty
        private String srcPathName;
        private String srcFileName;
        @NotEmpty
        private String destPathName;
        private String destFileName;
        @NotEmpty
        private PolicyStatus status;
        private LocalDateTime setupDate;
        @NotEmpty
        private List<String> ruleList;
    }

    /**
     * Policy 테이블의 레코드 수정 시 수정된 내용을 다시 반환하는 내부 클래스
     */
    @Data
    static class UpdatePolicyResponse {
        private String policyId;
        private String policyName;
        private String srcPathName;
        private String srcFileName;
        private String destPathName;
        private String destFileName;
        private PolicyStatus status;
        private LocalDateTime setupDate;
        private List<String> ruleList;
        private LocalDateTime modifiedDate;
        private String modifiedUser;
    }

    /**
     * Policy 테이블의 레코드를 삭제하는 메소드
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deletePolicy(@PathVariable("id") String id) {
        Policy oneById = policyService.findOneById(id);

        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        policyService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
