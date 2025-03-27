CREATE TABLE faculties (
    id SERIAL PRIMARY KEY,
    faculty_name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE student_statuses (
    id SERIAL PRIMARY KEY,
    student_status_name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE programs (
    id SERIAL PRIMARY KEY,
    program_name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE students (
    student_id VARCHAR(20) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('Nam', 'Nữ', 'Khác')),
    faculty_id INT REFERENCES faculties(id) ON DELETE SET NULL,
    intake VARCHAR(10) NOT NULL,
    program_id INT REFERENCES programs(id) ON DELETE SET NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    student_status_id INT REFERENCES student_statuses(id) ON DELETE SET NULL,
    nationality VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) REFERENCES students(student_id) ON DELETE CASCADE,
    address_type VARCHAR(20) CHECK (address_type IN ('Thường Trú', 'Tạm Trú', 'Nhận Thư')),
    house_number_street_name VARCHAR(255),
    ward_commune VARCHAR(100),
    district VARCHAR(100),
    city_province VARCHAR(100),
    country VARCHAR(100) DEFAULT 'Việt Nam',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

ALTER TABLE addresses
    DROP CONSTRAINT addresses_address_type_check;

ALTER TABLE addresses
    ADD CONSTRAINT addresses_address_type_check
    CHECK (address_type IN ('Thường Trú', 'Tạm Trú', 'Nhận Thư'));

CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) REFERENCES students(student_id) ON DELETE CASCADE,
    document_type VARCHAR(20) CHECK (document_type IN ('CMND', 'CCCD', 'Hộ chiếu')),
    document_number VARCHAR(50) UNIQUE NOT NULL,
    issued_date DATE NOT NULL,
    expired_date DATE,
    issued_by VARCHAR(100) NOT NULL,
    issued_country VARCHAR(50) NOT NULL,
    note TEXT,
    has_chip BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE student_status_rules (
                                      id SERIAL PRIMARY KEY,
                                      current_status_id INT NOT NULL,
                                      allowed_transition_id INT NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      deleted BOOLEAN DEFAULT FALSE,
                                      created_by VARCHAR(100) DEFAULT 'admin',
                                      updated_by VARCHAR(100) DEFAULT 'admin',
                                      CONSTRAINT fk_current_status FOREIGN KEY (current_status_id) REFERENCES student_statuses(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_allowed_transition FOREIGN KEY (allowed_transition_id) REFERENCES student_statuses(id) ON DELETE CASCADE,
                                      CONSTRAINT unique_transition UNIQUE (current_status_id, allowed_transition_id)
);