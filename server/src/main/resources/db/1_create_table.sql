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
    phone_country VARCHAR(2) DEFAULT 'VN',
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

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         course_code VARCHAR(20) UNIQUE NOT NULL,
                         course_name VARCHAR(100) UNIQUE NOT NULL,
                         credits INT CHECK (credits >= 2) NOT NULL,
                         faculty_id INT REFERENCES faculties(id) ON DELETE SET NULL,
                         description TEXT,
                         prerequisite_course_id INT REFERENCES courses(id) ON DELETE SET NULL,
                         is_active BOOLEAN DEFAULT TRUE,  -- Deactivate instead of deleting
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         created_by VARCHAR(100) DEFAULT 'admin',
                         updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE semesters (
                           id SERIAL PRIMARY KEY,
                           academic_year VARCHAR(10) NOT NULL,
                           semester INT CHECK (semester IN (1, 2, 3)) NOT NULL,
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL,
                           last_cancel_date DATE NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           deleted BOOLEAN DEFAULT FALSE,
                           created_by VARCHAR(100) DEFAULT 'admin',
                           updated_by VARCHAR(100) DEFAULT 'admin',
                           UNIQUE (academic_year, semester),  -- Ensures no duplicate semesters
                           CHECK (start_date < last_cancel_date AND last_cancel_date < end_date),  -- Ensure logical order
                           CHECK (academic_year ~ '^\d{4}-\d{4}$'), -- Ensure academic_year format is 'YYYY-YYYY'
                           CHECK (CAST(SPLIT_PART(academic_year, '-', 1) AS INTEGER) < CAST(SPLIT_PART(academic_year, '-', 2) AS INTEGER)),  -- Ensure start year is before end year
                           -- Ensure start_date and end_date are within academic_year
                           CHECK (
                                   (
                                    EXTRACT(YEAR FROM start_date) = CAST(SPLIT_PART(academic_year, '-', 1) AS INTEGER)
                                        OR EXTRACT(YEAR FROM start_date) = CAST(SPLIT_PART(academic_year, '-', 2) AS INTEGER)
                                   )
                                   AND (
                                   EXTRACT(YEAR FROM end_date) = CAST(SPLIT_PART(academic_year, '-', 1) AS INTEGER)
                                       OR EXTRACT(YEAR FROM end_date) = CAST(SPLIT_PART(academic_year, '-', 2) AS INTEGER)
                                   )
                               )  -- Ensure all dates are within academic year
);


CREATE TABLE lecturers (
                           id SERIAL PRIMARY KEY,
                           full_name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           phone VARCHAR(15) UNIQUE,
                           faculty_id INT REFERENCES faculties(id) ON DELETE SET NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           deleted BOOLEAN DEFAULT FALSE,
                           created_by VARCHAR(100) DEFAULT 'admin',
                           updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE classes (
                         id SERIAL PRIMARY KEY,
                         class_code VARCHAR(20) UNIQUE NOT NULL,
                         course_id INT REFERENCES courses(id) ON DELETE CASCADE,
                         semester_id INT NOT NULL REFERENCES semesters(id) ON DELETE RESTRICT,
                         lecturer_id INT REFERENCES lecturers(id) ON DELETE SET NULL,
                         max_students INT NOT NULL CHECK (max_students > 0),
                         schedule TEXT NOT NULL,
                         room VARCHAR(50) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         created_by VARCHAR(100) DEFAULT 'admin',
                         updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE class_registrations (
                                     id SERIAL PRIMARY KEY,
                                     student_id VARCHAR(20) REFERENCES students(student_id) ON DELETE CASCADE,
                                     class_id INT REFERENCES classes(id) ON DELETE CASCADE,
                                     registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     status VARCHAR(20) CHECK (status IN ('REGISTERED', 'CANCELLED')) DEFAULT 'REGISTERED',
                                     cancellation_date TIMESTAMP,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     deleted BOOLEAN DEFAULT FALSE,
                                     created_by VARCHAR(100) DEFAULT 'admin',
                                     updated_by VARCHAR(100) DEFAULT 'admin',
                                     UNIQUE (student_id, class_id)  -- Prevent duplicate registrations
);

CREATE TABLE class_registration_history (
                                            id SERIAL PRIMARY KEY,
                                            class_registration_id INT NOT NULL REFERENCES class_registrations(id) ON DELETE CASCADE,
                                            action VARCHAR(20) CHECK (action IN ('REGISTERED', 'CANCELLED')) NOT NULL,
                                            reason TEXT,  -- Optional: Store reason for cancellation
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            deleted BOOLEAN DEFAULT FALSE,
                                            created_by VARCHAR(100) DEFAULT 'admin',
                                            updated_by VARCHAR(100) DEFAULT 'admin'
);


CREATE TABLE transcripts (
                             id SERIAL PRIMARY KEY,
                             class_registration_id INT UNIQUE REFERENCES class_registrations(id) ON DELETE CASCADE,
                             grade DECIMAL(3,2) CHECK (grade >= 0.0 AND grade <= 10.0),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             deleted BOOLEAN DEFAULT FALSE,
                             created_by VARCHAR(100) DEFAULT 'admin',
                             updated_by VARCHAR(100) DEFAULT 'admin'
);


CREATE TABLE phone_patterns (
    country_code VARCHAR(2) PRIMARY KEY,
    regex_pattern TEXT NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);

CREATE TABLE email_domains (
    id SERIAL PRIMARY KEY,
    domain VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(100) DEFAULT 'admin',
    updated_by VARCHAR(100) DEFAULT 'admin'
);