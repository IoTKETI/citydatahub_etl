package com.encore.smartcity.search;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroupList;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroups;
import com.encore.smartcity.nifiapi.services.ProcessGroupService;
import com.encore.smartcity.nifiapi.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PGSearchInit {

    private final InitService initService;

    @PostConstruct
    @Transactional
    public void init() throws IOException {
        initService.dbRootInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final ResourceService resourceService;
        private final JsonConfigProperties jsonConfigProperties;
        private final ProcessGroupService processGroupService;

        public void dbRootInit() throws IOException {

            // 나이파이 내 모든 프로세스 그룹의 uuid와 이름을 불러와 저장
            Map<String, String> allNifiProcessGroups = this.resourceService.getAllNifiProcessGroups();

            for(String flowId :allNifiProcessGroups.keySet()) {
                PGSearch pgSearch = new PGSearch();
                pgSearch.setFlowId(flowId);
                pgSearch.setName(allNifiProcessGroups.get(flowId));
                if(jsonConfigProperties.getNifiRootFlowId().equals(flowId)) {
                    pgSearch.setLevel(0L);
                }
                em.persist(pgSearch);
            }

            PGSearch root = findLevelZero().get(0);
            Long level = 1L;

            System.out.println("=========================================");
            System.out.println("=========================================");
            findSubPG(root.getFlowId(), level, root.getFlowId());

        }

        private void findSubPG(String id, Long level, String preId) throws IOException {
            ProcessGroupList subPG = processGroupService.getSubPG(id);

//            System.out.println("sub process-group 갯수 : " + subPG.getProcessGroups().size());
            List<ProcessGroups> processGroups = subPG.getProcessGroups();
            for(ProcessGroups pg : processGroups) {
                String parentGroupId = pg.getComponent().getParentGroupId();
                String name = pg.getComponent().getName();
                String uri = pg.getUri();
                String originId = pg.getId();
                PGSearch childPG = findByFlowId(originId).get(0);
                System.out.println("=========================================");

                childPG.setName(name);
                childPG.setParentId(parentGroupId);
                childPG.setUri(uri);
                childPG.setLevel(level);
                System.out.println(pg.toString());
                System.out.println("pgName : " + name);
                System.out.println("level : " + level);
                preId = parentGroupId;
                System.out.println();
                em.merge(childPG);
                level++;
                findSubPG(childPG.getFlowId(), level, preId);
                level--;
            }

        }


        private List<PGSearch> findLevelZero() {
            return em.createQuery("select p from PGSearch p where p.level = 0", PGSearch.class)
                    .getResultList();
        }

        private List<PGSearch> findByFlowId(String flowId) {
            return em.createQuery("select p from PGSearch p where p.flowId = :flowId", PGSearch.class)
                    .setParameter("flowId", flowId)
                    .getResultList();
        }

    }
}
