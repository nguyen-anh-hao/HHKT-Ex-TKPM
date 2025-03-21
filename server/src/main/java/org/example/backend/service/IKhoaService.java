package org.example.backend.service;

import org.example.backend.dto.request.KhoaRequest;
import org.example.backend.dto.response.KhoaResponse;

public interface IKhoaService {
    KhoaResponse addKhoa(KhoaRequest request);
}
