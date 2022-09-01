package com.encore.smartcity.datalifecyclescheduler.hadoop.services;

import com.encore.smartcity.datalifecyclescheduler.common.TableName;
import com.encore.smartcity.datalifecyclescheduler.policies.Policy;
import com.encore.smartcity.datalifecyclescheduler.policies.history.ExecutKnd;
import com.encore.smartcity.datalifecyclescheduler.policies.history.PolicyExecutHist;
import com.encore.smartcity.datalifecyclescheduler.policies.history.PolicyExecutRepository;
import com.encore.smartcity.datalifecyclescheduler.policyrulerelations.PolicyRuleRelation;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 파일 이관 및 삭제 로직을 처리하는 클래스
 */
@Service
@RequiredArgsConstructor
public class HadoopService {

    private final PolicyExecutRepository policyExecutRepository;

    private static String HADOOP_HOME = "/etc/hadoop";

    private static String HADOOP_URL = "hdfs://10.6.0.102";

    private static String HADOOP_PORT = "8020";

    /**
     * 파일 이관을 로직이 정의된 메소드
     * @param policy
     * @param count
     * @return
     * @throws URISyntaxException
     */
    @Transactional
    public Long moveFile(Policy policy, Long count) throws URISyntaxException {
        List<PolicyRuleRelation> policyRuleRelations = policy.getPolicyRuleRelations();

        for (PolicyRuleRelation policyRuleRelation : policyRuleRelations) {
            String srcPath = policy.getSrcPathName();
            String destPath = policy.getDestPathName();
            String srcFile;
            String destFile;

            if(policy.getSrcFileName() == null) {
                srcFile = "/";
            } else {
                srcFile = "/" + policy.getSrcFileName();
            }

            if(policy.getDestFileName() == null) {
                destFile = "/";
            } else {
                destFile = "/" + policy.getDestFileName();
            }

            Long moveCycle = policyRuleRelation.getRule().getMoveCycle();
//            Long moveCycle = 3L;
            moveCycle -= 1; // 이관 당일에 이관해야하기 때문에 설정 날짜 -1이 필요

            // 시간 계산
            LocalDate today = LocalDate.now();
//            LocalDateTime today = LocalDateTime.now();
//            LocalDateTime moveStandard = today.minusMinutes(moveCycle);
            LocalDate moveStandard = today.minusDays(moveCycle);

            // 하둡 path 설정
            String src = srcPath + srcFile;
            String dest = destPath + destFile;

            String BASE_URI = HADOOP_URL + ":" + HADOOP_PORT;

            System.setProperty("hadoop.home.dir", HADOOP_HOME);
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", BASE_URI);

            URI uri = new URI(BASE_URI);

            // 이관 실행
            try(FileSystem hdfs = FileSystem.get(uri, configuration)) {
                String srcFileURI = BASE_URI + "/" + src;
                String destFileURI = BASE_URI + "/" + dest;

                Path srcURIPath = new Path(new URI(srcFileURI));
                Path destURIPath = new Path(new URI(destFileURI));

                if(hdfs.isFile(srcURIPath)) {
                    long modificationTime = hdfs.getFileStatus(srcURIPath).getModificationTime();
//                    LocalDateTime lastUpdateTime = LocalDateTime
//                            .ofInstant(Instant.ofEpochMilli(modificationTime), TimeZone.getDefault().toZoneId());
                    LocalDate lastUpdateTime = Instant.ofEpochMilli(modificationTime).atZone(ZoneId.systemDefault()).toLocalDate();

                    if(lastUpdateTime.isBefore(moveStandard)) {
                        String replacedURI = srcFileURI.replaceFirst(srcPath, destPath);
                        int splitIndex = replacedURI.lastIndexOf("/");
                        String changedURI = replacedURI.substring(0, splitIndex);
                        Path changedPath = new Path(new URI(changedURI));
                        if(!hdfs.isDirectory(changedPath)) {
                            hdfs.mkdirs(changedPath);
                        }

                        hdfs.rename(srcURIPath, destURIPath);
                        String id = makeId(count);
                        count++;
                        PolicyExecutHist policyExecutHist = new PolicyExecutHist(id, policy, ExecutKnd.MOVE);
                        policyExecutRepository.save(policyExecutHist);
                    }

                } else if(hdfs.isDirectory(srcURIPath)) {
                    RemoteIterator<LocatedFileStatus> i = hdfs.listFiles(srcURIPath, true);

                    while(i.hasNext()) {
                        Path originalPath = i.next().getPath();
                        String originalURI = originalPath.toString();

                        long modificationTime = hdfs.getFileStatus(originalPath).getModificationTime();
//                        LocalDateTime lastUpdateTime = LocalDateTime
//                                .ofInstant(Instant.ofEpochMilli(modificationTime), TimeZone.getDefault().toZoneId());
                        LocalDate lastUpdateTime = Instant.ofEpochMilli(modificationTime).atZone(ZoneId.systemDefault()).toLocalDate();

                        if(lastUpdateTime.isBefore(moveStandard)) {
                            String replacedURI = originalURI.replaceFirst(srcPath, destPath);
                            int splitIndex = replacedURI.lastIndexOf("/");
                            String changedURI = replacedURI.substring(0, splitIndex);

                            Path changedPath = new Path(new URI(changedURI));
                            if(!hdfs.isDirectory(changedPath)) {
                                hdfs.mkdirs(changedPath);
                            }
                            hdfs.rename(originalPath, changedPath);
                            String id = makeId(count);
                            count++;
                            PolicyExecutHist policyExecutHist = new PolicyExecutHist(id, policy, ExecutKnd.MOVE);
                            policyExecutRepository.save(policyExecutHist);
                        }
                    }
                } else {
                    System.out.println("파일 없음!!!");
                }

            } catch (IOException e) {
                System.out.println("ERROR!!!");
                e.printStackTrace();
            }
        }

        return count;
    }

    /**
     * 파일 삭제 로직이 정의된 메소드
     * @param policy
     * @param count
     * @return
     * @throws URISyntaxException
     */
    @Transactional
    public Long deleteFile(Policy policy, Long count) throws URISyntaxException {

        List<PolicyRuleRelation> policyRuleRelations = policy.getPolicyRuleRelations();

        for (PolicyRuleRelation policyRuleRelation : policyRuleRelations) {
            String destPath = policy.getDestPathName();
            String destFile;

            if(policy.getSrcFileName() == null) {
                destFile = "/";
            } else {
                destFile = "/" + policy.getDestFileName();
            }

            String BASE_URI = HADOOP_URL + ":" + HADOOP_PORT;

            String dest = destPath + destFile;

            Long deleteCycle = policyRuleRelation.getRule().getDeleteCycle();
//            Long deleteCycle = 3L;
            deleteCycle -= 1; // 삭제 당일에 삭제되야하기 때문에 설정 날짜 -1이 필요

            // 시간 계산
            LocalDate today = LocalDate.now();
//            LocalDateTime today = LocalDateTime.now();
            System.out.println("now : " + today);
//            LocalDateTime moveStandard = today.minusMinutes(deleteCycle);
            LocalDate moveStandard = today.minusDays(deleteCycle);

            System.setProperty("hadoop.home.dir", HADOOP_HOME);
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", BASE_URI);

            URI uri = new URI(BASE_URI);

            try(FileSystem hdfs = FileSystem.get(uri, configuration)) {
                String destFileURI = BASE_URI + "/" + dest;
                Path destURIPath = new Path(new URI(destFileURI));

                if(hdfs.isFile(destURIPath)) {
                    long modificationTime = hdfs.getFileStatus(destURIPath).getModificationTime();
//                    LocalDateTime lastUpdateTime = LocalDateTime
//                            .ofInstant(Instant.ofEpochMilli(modificationTime), TimeZone.getDefault().toZoneId());
                    LocalDate lastUpdateTime = Instant.ofEpochMilli(modificationTime).atZone(ZoneId.systemDefault()).toLocalDate();

                    if(lastUpdateTime.isBefore(moveStandard)) {
                        hdfs.delete(destURIPath, true);
                        String id = makeId(count);
                        count++;
                        PolicyExecutHist policyExecutHist = new PolicyExecutHist(id, policy, ExecutKnd.DELETE);
                        policyExecutRepository.save(policyExecutHist);
                    }

                } else if(hdfs.isDirectory(destURIPath)) {
                    RemoteIterator<LocatedFileStatus> i = hdfs.listFiles(destURIPath, true);

                    while(i.hasNext()) {
                        Path originalPath = i.next().getPath();
                        String originalURI = originalPath.toString();

                        long modificationTime = hdfs.getFileStatus(originalPath).getModificationTime();
//                        LocalDateTime lastUpdateTime = LocalDateTime
//                                .ofInstant(Instant.ofEpochMilli(modificationTime), TimeZone.getDefault().toZoneId());
                        LocalDate lastUpdateTime = Instant.ofEpochMilli(modificationTime).atZone(ZoneId.systemDefault()).toLocalDate();

                        if(lastUpdateTime.isBefore(moveStandard)) {
                            hdfs.delete(originalPath, true);
                            String id = makeId(count);
                            count++;
                            PolicyExecutHist policyExecutHist = new PolicyExecutHist(id, policy, ExecutKnd.DELETE);
                            policyExecutRepository.save(policyExecutHist);
                        }
                    }

                } else {
                    System.out.println("파일 없음!!!");
                }

            } catch (Exception e) {
                System.out.println("Error 발생!!!");
            }
        }

        return count;
    }

    /**
     * POLICY_EXECUT_HIST 테이블 ID를 생성하는 메소드
     * @param count
     * @return
     */
    private String makeId(Long count) {
        String now = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String stringCount;
        if(count < 10) {
            stringCount = "00" + count;
        } else if(count < 100) {
            stringCount = "0" + count;
        } else {
            stringCount = "" + count;
        }

        return TableName.POLICY_EXECUT_HIST  + "_" + now + "_" + stringCount;
    }
}
