package org.example.backend.controller;

import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.EmailDomainResponse;
import org.example.backend.service.impl.EmailDomainServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailDomainController.class)
@AutoConfigureDataJpa
public class EmailDomainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailDomainServiceImpl service;

    @Test
    public void shouldGetAllEmailDomains() throws Exception {
        List< EmailDomainResponse> emailDomains = List.of(
                EmailDomainResponse.builder().id(1).domain("example.com").build(),
                EmailDomainResponse.builder().id(2).domain("example.org").build()
        );

        when(service.getAllDomains()).thenReturn(emailDomains);

        mockMvc.perform(get("/api/email-domains"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].domain").value("example.com"))
                .andExpect(jsonPath("$.data[1].domain").value("example.org"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    public void shouldGetEmailDomainById() throws Exception {
        EmailDomainResponse emailDomain = EmailDomainResponse.builder().id(1).domain("example.com").build();

        when(service.getDomainById(1)).thenReturn(emailDomain);

        mockMvc.perform(get("/api/email-domains/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.domain").value("example.com"));
    }

    @Test
    public void shouldAddEmailDomain() throws Exception {
        EmailDomainResponse emailDomain = EmailDomainResponse.builder().id(1).domain("example.com").build();

        when(service.createDomain(any(EmailDomainRequest.class))).thenReturn(emailDomain);

        mockMvc.perform(post("/api/email-domains")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"domain\": \"example.com\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.domain").value("example.com"));
    }

    @Test
    public void shouldUpdateEmailDomain() throws Exception {
        EmailDomainResponse emailDomain = EmailDomainResponse.builder().id(1).domain("example.com").build();

        when(service.updateDomain(eq(1), any(EmailDomainRequest.class))).thenReturn(emailDomain);

        mockMvc.perform(put("/api/email-domains/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"domain\": \"example.com\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.domain").value("example.com"));
    }

    @Test
    public void shouldDeleteEmailDomain() throws Exception {
        mockMvc.perform(delete("/api/email-domains/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    public void shouldRejectDomainWithoutTLD() throws Exception {
        mockMvc.perform(post("/api/email-domains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"domain\": \"example\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRejectDomainStartingWithHyphen() throws Exception {
        mockMvc.perform(post("/api/email-domains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"domain\": \"-example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRejectBlankDomain() throws Exception {
        mockMvc.perform(post("/api/email-domains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"domain\": \"\"}"))
                .andExpect(status().isBadRequest());
    }
}
