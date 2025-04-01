package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.EmailDomain;
import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.EmailDomainResponse;
import org.example.backend.mapper.EmailDomainMapper;
import org.example.backend.repository.IEmailDomainRepository;
import org.example.backend.service.IEmailDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailDomainService implements IEmailDomainService {
    private final IEmailDomainRepository emailDomainRepository;

    @Override
    public List<EmailDomainResponse> getAllDomains() {
        List<EmailDomain> emailDomains = emailDomainRepository.findAll();

        log.info("Retrieved all email domains from database");

        return emailDomains.stream()
                .map(EmailDomainMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmailDomainResponse getDomainById(Integer id) {
        EmailDomain emailDomain = emailDomainRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Email domain not found");
                    return new RuntimeException("Email domain not found");
                });

        log.info("Retrieved email domain from database");

        return EmailDomainMapper.mapToResponse(emailDomain);
    }

    @Override
    public EmailDomainResponse createDomain(EmailDomainRequest emailDomainRequest) {
        if (emailDomainRepository.findByDomain(emailDomainRequest.getDomain()).isPresent()) {
            log.error("Email domain already exists");
            throw new RuntimeException("Email domain already exists");
        }

        EmailDomain emailDomain = EmailDomainMapper.mapToDomain(emailDomainRequest);
        emailDomain = emailDomainRepository.save(emailDomain);

        log.info("Email domain saved to database successfully");

        return EmailDomainMapper.mapToResponse(emailDomain);
    }

    @Override
    public EmailDomainResponse updateDomain(Integer id, EmailDomainRequest emailDomainRequest) {
        EmailDomain emailDomain = emailDomainRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Email domain not found");
                    return new RuntimeException("Email domain not found");
                });

        emailDomain.setDomain(emailDomainRequest.getDomain());
        emailDomain = emailDomainRepository.save(emailDomain);

        log.info("Email domain updated successfully");

        return EmailDomainMapper.mapToResponse(emailDomain);
    }

    @Override
    public void deleteDomain(Integer id) {
        EmailDomain emailDomain = emailDomainRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Email domain not found");
                    return new RuntimeException("Email domain not found");
                });

        emailDomainRepository.delete(emailDomain);

        log.info("Email domain deleted successfully");
    }
}
