package org.example.backend.service;

import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.EmailDomainResponse;

import java.util.List;

public interface IEmailDomainService {
    List<EmailDomainResponse> getAllDomains();
    EmailDomainResponse getDomainById(Integer id);
    EmailDomainResponse createDomain(EmailDomainRequest emailDomainRequest);
    EmailDomainResponse updateDomain(Integer id, EmailDomainRequest emailDomainRequest);
    void deleteDomain(Integer id);
}
