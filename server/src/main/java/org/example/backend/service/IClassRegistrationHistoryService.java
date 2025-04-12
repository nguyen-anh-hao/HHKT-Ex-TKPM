package org.example.backend.service;

import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;

public interface IClassRegistrationHistoryService {
    ClassRegistrationHistoryResponse addClassRegistrationHistory(ClassRegistrationHistoryRequest classRegistrationHistoryRequest);
}
