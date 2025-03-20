package org.example.backend.repository;

import org.example.backend.domain.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISinhVienRepository extends JpaRepository<SinhVien, String> {

    Optional<SinhVien> findByMssv(String mssv);

    @Query("SELECT sv FROM SinhVien sv WHERE sv.mssv LIKE %:keyword% OR sv.hoTen LIKE %:keyword% OR sv.email LIKE %:keyword% OR sv.khoa.tenKhoa LIKE %:keyword%")
    Page<SinhVien> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
