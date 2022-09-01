package com.encore.smartcity.nifiapi.responses.flow;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlowStatus {

        private Integer activeThreadCount;
        private Integer terminatedThreadCount;
        private String queued;
        private Integer flowFilesQueued;
        private Integer bytesQueued;
        private Integer runningCount;
        private Integer stoppedCount;
        private Integer invalidCount;
        private Integer disabledCount;
        private Integer activeRemotePortCount;
        private Integer inactiveRemotePortCount;
        private Integer upToDateCount;
        private Integer locallyModifiedCount;
        private Integer staleCount;
        private Integer locallyModifiedAndStaleCount;
        private Integer syncFailureCount;

}
