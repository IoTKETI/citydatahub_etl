package com.encore.smartcity.search.controllers;

import com.encore.smartcity.configurations.ModelMapperConfig;
import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlow;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.services.FlowService;
import com.encore.smartcity.nifiapi.services.ProcessGroupService;
import com.encore.smartcity.search.PGSearch;
import com.encore.smartcity.search.QPGSearch;
import com.encore.smartcity.search.dto.SearchPGDto;
import com.encore.smartcity.search.repositories.SearchRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    private Logger logger = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    private SearchRepository searchRepository;

    @Autowired
    private ProcessGroupService processGroupService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private ModelMapper modelMapper;

    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> searchPGByLevelOrName(
            @RequestParam(required = false, name = "level") Integer level,
            @RequestParam(required = false, name = "name", defaultValue = "") String name,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page

    ) throws IOException {
        Sort sort = Sort.by(Sort.Direction.ASC, "level");
        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        Map<String, Object> response = new HashMap<>();
        Page<PGSearch> pgSearchList;
        List<SearchPGDto> dto = new ArrayList<>();

        if (level != null && !name.isEmpty()) {
            long l = (long) level;
            pgSearchList = this.searchRepository.findPGSearchByLevelAndNameContainingIgnoreCase(l, name, pageRequest);
        } else if (level == null && !name.isEmpty()) {
            pgSearchList = this.searchRepository.findByNameContainingIgnoreCase(name, pageRequest);
        } else if (level != null) {
            long l = (long) level;
            pgSearchList = this.searchRepository.findPGSearchByLevel(l, pageRequest);
        } else {
            pgSearchList = this.searchRepository.findAll(pageRequest);
        }

        int i = 0;
        for (PGSearch p :
                pgSearchList) {
            ProcessGroup flowInfo = this.processGroupService.getProcessGroupById(p.getFlowId()).body();

            if (flowInfo == null)
                continue;

            ProcessGroupFlow processGroupFlow = this.flowService.getProcessGroupFlowById(p.getFlowId()).body().getProcessGroupFlow();

            processGroupFlow.getFlow().setFlowInfo(flowInfo);

            System.out.println(processGroupFlow);

            dto.add(this.modelMapper.map(p, SearchPGDto.class));
            dto.get(i).setProcessGroupFlow(processGroupFlow);
            i++;
        }

//        dto = ModelMapperConfig.mapAll(pgSearchList.getContent(), SearchPGDto.class);

        response.put("data", dto);
        response.put("total_record", pgSearchList.getTotalElements());
        response.put("total_page", pgSearchList.getTotalPages());
        response.put("size", pgSearchList.getSize());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-level")
    @Transactional
    public ResponseEntity<Map<String, Object>> getAllAvailableLevel() {
        QPGSearch qpgSearch = QPGSearch.pGSearch;
//        JPAQuery<PGSearch> pgSearchJPAQuery = new JPAQuery<>(entityManager);
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        List<Long> allLevel = jpaQueryFactory
                .select(qpgSearch.level)
                .from(qpgSearch)
                .distinct()
                .orderBy(qpgSearch.level.asc())
                .fetch();

        logger.info("All Level: {}", allLevel);

        Map<String, Object> response = new HashMap<>();
        response.put("data", allLevel);
        response.put("total_level", allLevel.size());

        return ResponseEntity.ok(response);
    }
}
