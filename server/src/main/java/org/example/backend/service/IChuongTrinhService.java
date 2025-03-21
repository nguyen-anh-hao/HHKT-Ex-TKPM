package org.example.backend.service;

import org.example.backend.dto.request.ChuongTrinhRequest;
import org.example.backend.dto.response.ChuongTrinhResponse;

public interface IChuongTrinhService {
    ChuongTrinhResponse addChuongTrinh(ChuongTrinhRequest request);
}
