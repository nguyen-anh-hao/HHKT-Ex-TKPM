package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.ClassRegistrationHistory;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.example.backend.mapper.ClassRegistrationHistoryMapper;
import org.example.backend.repository.IClassRegistrationHistoryRepository;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.service.IClassRegistrationHistoryService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassRegistrationHistoryServiceImpl implements IClassRegistrationHistoryService {
    private final IClassRegistrationHistoryRepository classRegistrationHistoryRepository;
    private final IClassRegistrationRepository classRegistrationRepository;

    @Override
    @Transactional
    public ClassRegistrationHistoryResponse addClassRegistrationHistory(ClassRegistrationHistoryRequest classRegistrationRequest) {

        log.info("Add class registration history id: {}", classRegistrationRequest.getClassRegistrationId());

        log.info("Checking if class registration exists");
        ClassRegistration classRegistration = classRegistrationRepository.findById(classRegistrationRequest.getClassRegistrationId())
                .orElseThrow(() ->
                {
                    log.error("Class registration not found");
                    return new RuntimeException("Class registration not found");
                });


        log.info("Mapping class registration history request to domain");
        ClassRegistrationHistory classRegistrationHistory = ClassRegistrationHistoryMapper.mapFromClassRegistrationHistoryRequestToDomain(classRegistrationRequest);
        classRegistrationHistory.setClassRegistration(classRegistration);

        log.info("Saving class registration history");
        ClassRegistrationHistory savedClassRegistrationHistory = classRegistrationHistoryRepository.save(classRegistrationHistory);

        log.info("Mapping class registration history to response");
        return ClassRegistrationHistoryMapper.mapFromDomainToClassRegistrationHistoryResponse(savedClassRegistrationHistory);
    }
}
