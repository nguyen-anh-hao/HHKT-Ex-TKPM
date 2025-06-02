package org.example.backend.service;

import org.example.backend.domain.EmailDomain;
import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.EmailDomainResponse;
import org.example.backend.repository.IEmailDomainRepository;
import org.example.backend.service.impl.EmailDomainServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailDomainServiceTest {
    @Mock
    private IEmailDomainRepository emailDomainRepository;

    @InjectMocks
    private EmailDomainServiceImpl emailDomainService;

    @Test
    public void shouldGetAllDomains() {
        List<EmailDomain> emailDomains = List.of(
                new EmailDomain(1, "example.com"),
                new EmailDomain(2, "example.org")
        );

        when(emailDomainRepository.findAll()).thenReturn(emailDomains);

        List<EmailDomainResponse> emailDomainResponses = emailDomainService.getAllDomains();

        assertThat(emailDomainResponses).hasSize(2);
        assertThat(emailDomainResponses.get(0).getId()).isEqualTo(1);
        assertThat(emailDomainResponses.get(0).getDomain()).isEqualTo("example.com");
        assertThat(emailDomainResponses.get(1).getId()).isEqualTo(2);
        assertThat(emailDomainResponses.get(1).getDomain()).isEqualTo("example.org");
    }

    @Test
    public void shouldGetDomainById() {
        EmailDomain emailDomain = new EmailDomain(1, "example.com");

        when(emailDomainRepository.findById(1)).thenReturn(Optional.of(emailDomain));

        EmailDomainResponse emailDomainResponse = emailDomainService.getDomainById(1);

        assertThat(emailDomainResponse.getId()).isEqualTo(1);
        assertThat(emailDomainResponse.getDomain()).isEqualTo("example.com");
    }

    @Test
    public void givenDomainIdNotFound_whenGetDomainById_shouldThrowException() {
        when(emailDomainRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emailDomainService.getDomainById(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Email domain not found");
    }

    @Test
    public void shouldCreateDomain() {
        EmailDomain emailDomain = new EmailDomain(1, "example.com");
        EmailDomainRequest emailDomainRequest = EmailDomainRequest.builder()
                .domain("example.com")
                .build();

        when(emailDomainRepository.findByDomain("example.com")).thenReturn(Optional.empty());
        when(emailDomainRepository.save(any(EmailDomain.class))).thenReturn(emailDomain);

        EmailDomainResponse emailDomainResponse = emailDomainService.createDomain(emailDomainRequest);

        assertThat(emailDomainResponse.getId()).isEqualTo(1);
        assertThat(emailDomainResponse.getDomain()).isEqualTo("example.com");
    }

    @Test
    public void givenDomainAlreadyExists_whenCreateDomain_shouldThrowException() {
        EmailDomainRequest emailDomainRequest = EmailDomainRequest.builder()
                .domain("example.com")
                .build();

        when(emailDomainRepository.findByDomain("example.com")).thenReturn(Optional.of(new EmailDomain()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emailDomainService.createDomain(emailDomainRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Email domain already exists");
    }

    @Test
    public void shouldUpdateDomain() {
        EmailDomain emailDomain = new EmailDomain(1, "example.com");
        EmailDomainRequest emailDomainRequest = EmailDomainRequest.builder()
                .domain("example.org")
                .build();

        when(emailDomainRepository.findById(1)).thenReturn(Optional.of(emailDomain));
        when(emailDomainRepository.save(any(EmailDomain.class))).thenReturn(emailDomain);

        EmailDomainResponse emailDomainResponse = emailDomainService.updateDomain(1, emailDomainRequest);

        assertThat(emailDomainResponse.getId()).isEqualTo(1);
        assertThat(emailDomainResponse.getDomain()).isEqualTo("example.org");
    }

    @Test
    public void givenDomainIdNotFound_whenUpdateDomain_shouldThrowException() {
        EmailDomainRequest emailDomainRequest = EmailDomainRequest.builder()
                .domain("example.com")
                .build();

        when(emailDomainRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emailDomainService.updateDomain(1, emailDomainRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Email domain not found");
    }

    @Test
    public void shouldDeleteDomain() {
        EmailDomain emailDomain = new EmailDomain(1, "example.com");

        when(emailDomainRepository.findById(1)).thenReturn(Optional.of(emailDomain));

        emailDomainService.deleteDomain(1);
    }

    @Test
    public void givenDomainIdNotFound_whenDeleteDomain_shouldThrowException() {
        when(emailDomainRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emailDomainService.deleteDomain(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Email domain not found");
    }
}
