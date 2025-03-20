CREATE TABLE khoa (
                      id SERIAL PRIMARY KEY,
                      ten_khoa VARCHAR(100) UNIQUE NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      deleted BOOLEAN DEFAULT FALSE,
                      created_by VARCHAR(100) DEFAULT 'admin',
                      updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE tinh_trang_sinh_vien (
                                      id SERIAL PRIMARY KEY,
                                      ten_tinh_trang VARCHAR(50) UNIQUE NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      deleted BOOLEAN DEFAULT FALSE,
                                      created_by VARCHAR(100) DEFAULT 'admin',
                                      updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE chuong_trinh (
                              id SERIAL PRIMARY KEY,
                              ten_chuong_trinh VARCHAR(100) UNIQUE NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              deleted BOOLEAN DEFAULT FALSE,
                              created_by VARCHAR(100) DEFAULT 'admin',
                              updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE sinh_vien (
                           mssv VARCHAR(20) PRIMARY KEY,
                           ho_ten VARCHAR(100) NOT NULL,
                           ngay_sinh DATE NOT NULL,
                           gioi_tinh VARCHAR(10) CHECK (gioi_tinh IN ('Nam', 'Nữ', 'Khác')),
                           khoa_id INT REFERENCES khoa(id) ON DELETE SET NULL,
                           khoa_hoc VARCHAR(10) NOT NULL,
                           chuong_trinh_id INT REFERENCES chuong_trinh(id) ON DELETE SET NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           so_dien_thoai VARCHAR(15) UNIQUE NOT NULL,
                           tinh_trang_id INT REFERENCES tinh_trang_sinh_vien(id) ON DELETE SET NULL,
                           quoc_tich VARCHAR(50) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           deleted BOOLEAN DEFAULT FALSE,
                           created_by VARCHAR(100) DEFAULT 'admin',
                           updated_by VARCHAR(100) DEFAULT 'admin'
                       );

CREATE TABLE dia_chi (
                         id SERIAL PRIMARY KEY,
                         mssv VARCHAR(20) REFERENCES sinh_vien(mssv) ON DELETE CASCADE,
                         loai_dia_chi VARCHAR(20) CHECK (loai_dia_chi IN ('Thường Trú', 'Tạm Trú', 'Nhận Thư')),
                         so_nha_ten_duong VARCHAR(255),
                         phuong_xa VARCHAR(100),
                         quan_huyen VARCHAR(100),
                         tinh_thanh_pho VARCHAR(100),
                         quoc_gia VARCHAR(100) DEFAULT 'Việt Nam',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         created_by VARCHAR(100) DEFAULT 'admin',
                         updated_by VARCHAR(100) DEFAULT 'admin'
);

ALTER TABLE dia_chi
    DROP CONSTRAINT dia_chi_loai_dia_chi_check;

ALTER TABLE dia_chi
    ADD CONSTRAINT dia_chi_loai_dia_chi_check
        CHECK (loai_dia_chi IN ('Thường Trú', 'Tạm Trú', 'Nhận Thư'));


CREATE TABLE giay_to (
                         id SERIAL PRIMARY KEY,
                         mssv VARCHAR(20) REFERENCES sinh_vien(mssv) ON DELETE CASCADE,
                         loai_giay_to VARCHAR(20) CHECK (loai_giay_to IN ('CMND', 'CCCD', 'Hộ chiếu')),
                         so_giay_to VARCHAR(50) UNIQUE NOT NULL,
                         ngay_cap DATE NOT NULL,
                         ngay_het_han DATE,
                         noi_cap VARCHAR(100) NOT NULL,
                         quoc_gia_cap VARCHAR(50) NOT NULL,
                         ghi_chu TEXT,
                         co_gan_chip BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         created_by VARCHAR(100) DEFAULT 'admin',
                         updated_by VARCHAR(100) DEFAULT 'admin'
);
