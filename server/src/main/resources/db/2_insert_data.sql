INSERT INTO faculties (faculty_name) VALUES
    ('Khoa Luật'),
    ('Khoa Tiếng Anh thương mại'),
    ('Khoa Tiếng Nhật'),
    ('Khoa Tiếng Pháp');

INSERT INTO student_statuses (student_status_name) VALUES
    ('Đang học'),
    ('Đã tốt nghiệp'),
    ('Đã thôi học'),
    ('Tạm dừng học');

INSERT INTO programs (program_name) VALUES
    ('Đại học chính quy'),
    ('Liên thông'),
    ('Vừa học vừa làm');

-- Insert data for students table
INSERT INTO students (student_id, full_name, dob, gender, faculty_id, intake, program_id, email, phone_country, phone, student_status_id, nationality)
VALUES
    ('SV001', 'Nguyễn Văn A', '2002-05-10', 'Nam', 1, 'K20', 1, 'nguyenvana@example.com', 'VN', '+84987654321', 1, 'Việt Nam'),
    ('SV002', 'Trần Thị B', '2001-08-15', 'Nữ', 2, 'K19', 2, 'tranthib@example.com', 'VN', '+84976543210', 2, 'Việt Nam'),
    ('SV003', 'Lê Hoàng C', '2003-03-22', 'Nam', 3, 'K21', 1, 'lehoangc@example.com', 'VN', '+84965432109', 1, 'Việt Nam'),
    ('SV004', 'Phạm Minh D', '2000-12-30', 'Nam', 1, 'K18', 3, 'phamminhd@example.com', 'VN', '+84954321098', 3, 'Việt Nam'),
    ('SV005', 'Hoàng Thị E', '2002-07-11', 'Nữ', 4, 'K20', 2, 'hoangthie@example.com', 'VN', '+84943210987', 1, 'Việt Nam'),
    ('SV006', 'Mike Johnson', '2001-05-25', 'Nam', 2, 'K19', 1, 'mike.johnson@example.com', 'US', '+12025550179', 2, 'Hoa Kỳ'),
    ('SV007', 'Emma Wilson', '2003-09-14', 'Nữ', 3, 'K21', 3, 'emma.wilson@example.com', 'UK', '+447700900123', 1, 'Anh'),
    ('SV008', 'Võ Thị H', '2002-01-07', 'Nữ', 1, 'K20', 2, 'vothih@example.com', 'VN', '+84910987654', 1, 'Việt Nam'),
    ('SV009', 'Liu Wei', '2000-06-19', 'Nam', 4, 'K18', 1, 'liu.wei@example.com', 'CN', '+8613812345678', 3, 'Trung Quốc'),
    ('SV010', 'Dương Minh K', '2003-10-23', 'Nam', 2, 'K21', 2, 'duongminhk@example.com', 'VN', '+84898765432', 1, 'Việt Nam'),
    ('SV011', 'Sophie Martin', '2001-02-08', 'Nữ', 3, 'K19', 3, 'sophie.martin@example.com', 'FR', '+33612345678', 2, 'Pháp'),
    ('SV012', 'Lý Văn M', '2002-04-17', 'Nam', 1, 'K20', 1, 'lyvanm@example.com', 'VN', '+84876543210', 1, 'Việt Nam'),
    ('SV013', 'Tanaka Yuki', '2000-11-29', 'Nam', 4, 'K18', 2, 'tanaka.yuki@example.com', 'JP', '+8190123456789', 3, 'Nhật Bản'),
    ('SV014', 'Kim Ji-woo', '2003-07-21', 'Nữ', 2, 'K21', 3, 'kim.jiwoo@example.com', 'KR', '+821012345678', 1, 'Hàn Quốc'),
    ('SV015', 'Phan Mỹ P', '2002-09-12', 'Nữ', 3, 'K20', 1, 'phanmyp@example.com', 'VN', '+84843210987', 1, 'Việt Nam'),
    ('SV016', 'Marco Rossi', '2001-03-06', 'Nam', 1, 'K19', 2, 'marco.rossi@example.com', 'IT', '+393123456789', 2, 'Ý'),
    ('SV017', 'Sarah Miller', '2002-05-31', 'Nữ', 4, 'K20', 3, 'sarah.miller@example.com', 'AU', '+61412345678', 1, 'Úc'),
    ('SV018', 'Carlos Rodriguez', '2000-08-27', 'Nam', 2, 'K18', 1, 'carlos.rodriguez@example.com', 'ES', '+34612345678', 3, 'Tây Ban Nha'),
    ('SV019', 'Maria Garcia', '2003-01-19', 'Nữ', 3, 'K21', 2, 'maria.garcia@example.com', 'MX', '+5215512345678', 1, 'Mexico'),
    ('SV020', 'Singh Raj', '2001-06-13', 'Nam', 1, 'K19', 3, 'singh.raj@example.com', 'IN', '+917012345678', 2, 'Ấn Độ');

-- Insert data for addresses table
INSERT INTO addresses (student_id, address_type, house_number_street_name, ward_commune, district, city_province, country)
VALUES
    ('SV001', 'Thường Trú', '123 Lê Lợi', 'Phường 1', 'Quận 3', 'TP.HCM', 'Việt Nam'),
    ('SV001', 'Tạm Trú', '456 Trần Hưng Đạo', 'Phường 5', 'Quận 1', 'TP.HCM', 'Việt Nam'),
    ('SV002', 'Thường Trú', '789 Nguyễn Huệ', 'Phường 2', 'Quận 4', 'TP.HCM', 'Việt Nam'),
    ('SV003', 'Thường Trú', '234 Hai Bà Trưng', 'Phường 6', 'Quận 1', 'TP.HCM', 'Việt Nam'),
    ('SV004', 'Tạm Trú', '567 Pasteur', 'Phường 3', 'Quận 5', 'TP.HCM', 'Việt Nam'),
    ('SV005', 'Thường Trú', '678 Lý Tự Trọng', 'Phường 7', 'Quận 10', 'TP.HCM', 'Việt Nam'),
    ('SV006', 'Tạm Trú', '890 Võ Văn Kiệt', 'Phường 8', 'Quận 6', 'TP.HCM', 'Việt Nam'),
    ('SV007', 'Thường Trú', '101 Đinh Tiên Hoàng', 'Phường 9', 'Quận 2', 'TP.HCM', 'Việt Nam'),
    ('SV008', 'Tạm Trú', '202 Nguyễn Trãi', 'Phường 10', 'Quận 7', 'TP.HCM', 'Việt Nam'),
    ('SV009', 'Thường Trú', '303 Trần Quang Khải', 'Phường 11', 'Quận 8', 'TP.HCM', 'Việt Nam'),
    ('SV010', 'Tạm Trú', '404 Hoàng Văn Thụ', 'Phường 12', 'Quận Tân Bình', 'TP.HCM', 'Việt Nam'),
    ('SV011', 'Thường Trú', '505 Nguyễn Văn Cừ', 'Phường 13', 'Quận 9', 'TP.HCM', 'Việt Nam'),
    ('SV012', 'Tạm Trú', '606 Phan Xích Long', 'Phường 14', 'Quận 11', 'TP.HCM', 'Việt Nam'),
    ('SV013', 'Thường Trú', '707 Điện Biên Phủ', 'Phường 15', 'Quận Bình Thạnh', 'TP.HCM', 'Việt Nam'),
    ('SV014', 'Tạm Trú', '808 Xô Viết Nghệ Tĩnh', 'Phường 16', 'Quận Gò Vấp', 'TP.HCM', 'Việt Nam');

-- Insert data for documents table
INSERT INTO documents (student_id, document_type, document_number, issued_date, expired_date, issued_by, issued_country, note, has_chip)
VALUES
    ('SV001', 'CCCD', '123456789012', '2020-10-01', '2030-10-01', 'TP.HCM', 'Việt Nam', NULL, TRUE),
    ('SV002', 'Hộ chiếu', 'B1234567', '2019-06-15', '2029-06-15', 'Hà Nội', 'Việt Nam', 'Dùng để du học', FALSE);

INSERT INTO student_status_rules (current_status_id, allowed_transition_id) VALUES
-- "Đang học" can transition to:
(1, 2),  -- Tạm dừng học
(1, 3),  -- Đã tốt nghiệp
(1, 4),  -- Đã thôi học

-- "Tạm dừng học" can transition to:
(2, 1),  -- Đang học
(2, 4);  -- Đã thôi học

-- "Đã tốt nghiệp" has no transitions

-- "Đã thôi học" has no transitions;

INSERT INTO courses (course_code, course_name, credits, faculty_id, description, prerequisite_course_id, is_active, created_by, updated_by)
VALUES
    ('CS101', 'Introduction to Programming', 3, 1, 'Basic programming concepts', NULL, TRUE, 'admin', 'admin'),
    ('CS102', 'Data Structures', 3, 1, 'Study of data structures', 1, TRUE, 'admin', 'admin'),
    ('CS103', 'Algorithms', 3, 1, 'Fundamentals of algorithms', 2, TRUE, 'admin', 'admin'),
    ('CS104', 'Database Systems', 3, 2, 'Database design and SQL', NULL, TRUE, 'admin', 'admin'),
    ('CS105', 'Computer Networks', 3, 3, 'Network protocols and security', NULL, TRUE, 'admin', 'admin'),
    ('CS106', 'Operating Systems', 3, 1, 'Processes, threads, and memory management', NULL, TRUE, 'admin', 'admin'),
    ('CS107', 'Software Engineering', 3, 2, 'Software development lifecycle', NULL, TRUE, 'admin', 'admin'),
    ('CS108', 'Artificial Intelligence', 3, 3, 'Machine learning and AI concepts', 3, TRUE, 'admin', 'admin'),
    ('CS109', 'Cyber Security', 3, 3, 'Cyber threats and security measures', NULL, TRUE, 'admin', 'admin'),
    ('CS110', 'Cloud Computing', 3, 1, 'Introduction to cloud services', 4, TRUE, 'admin', 'admin');

INSERT INTO semesters (academic_year, semester, start_date, end_date, last_cancel_date)
VALUES
    ('2023-2024', 1, '2023-09-01', '2023-12-31', '2023-10-15'), -- Fall
    ('2023-2024', 2, '2024-01-10', '2024-04-30', '2024-02-28'), -- Spring
    ('2023-2024', 3, '2024-06-01', '2024-07-31', '2024-06-20'), -- Summer

    ('2024-2025', 1, '2024-09-05', '2024-12-25', '2024-10-20'), -- Fall
    ('2024-2025', 2, '2025-01-08', '2025-04-28', '2025-02-25'), -- Spring
    ('2024-2025', 3, '2025-06-05', '2025-07-30', '2025-06-18'), -- Summer

    ('2025-2026', 1, '2025-09-03', '2025-12-20', '2025-10-10'), -- Fall
    ('2025-2026', 2, '2026-01-12', '2026-04-25', '2026-02-22'), -- Spring
    ('2025-2026', 3, '2026-06-07', '2026-08-01', '2026-06-22'), -- Summer

    ('2026-2027', 1, '2026-09-04', '2026-12-22', '2026-10-12'); -- Fall


INSERT INTO lecturers (full_name, email, phone, faculty_id)
VALUES
    ('Dr. John Doe', 'johndoe@university.edu', '1234567890', 1),
    ('Dr. Jane Smith', 'janesmith@university.edu', '2345678901', 1),
    ('Prof. Alice Brown', 'alicebrown@university.edu', '3456789012', 2),
    ('Dr. Bob Johnson', 'bobjohnson@university.edu', '4567890123', 2),
    ('Dr. Carol White', 'carolwhite@university.edu', '5678901234', 3),
    ('Dr. David Green', 'davidgreen@university.edu', '6789012345', 3),
    ('Prof. Eva Black', 'evablack@university.edu', '7890123456', 1),
    ('Dr. Frank Wilson', 'frankwilson@university.edu', '8901234567', 2),
    ('Dr. Grace Adams', 'graceadams@university.edu', '9012345678', 3),
    ('Dr. Henry Clark', 'henryclark@university.edu', '0123456789', 1);


INSERT INTO classes (class_code, course_id, semester_id, lecturer_id, max_students, schedule, room)
VALUES
    ('CS101-01', 1, 1, 1, 50, 'Mon-Wed 10:00-12:00', 'Room A101'),
    ('CS102-01', 2, 1, 2, 40, 'Tue-Thu 14:00-16:00', 'Room B201'),
    ('CS103-01', 3, 2, 3, 45, 'Mon-Wed 08:00-10:00', 'Room C301'),
    ('CS104-01', 4, 2, 4, 35, 'Fri 09:00-12:00', 'Room D401'),
    ('CS105-01', 5, 3, 5, 60, 'Tue-Thu 10:00-12:00', 'Room E501'),
    ('CS106-01', 6, 3, 6, 30, 'Wed 14:00-17:00', 'Room F601'),
    ('CS107-01', 7, 1, 7, 50, 'Thu 09:00-12:00', 'Room G701'),
    ('CS108-01', 8, 2, 8, 40, 'Mon 14:00-17:00', 'Room H801'),
    ('CS109-01', 9, 3, 9, 55, 'Fri 08:00-11:00', 'Room I901'),
    ('CS110-01', 10, 1, 10, 50, 'Wed 09:00-12:00', 'Room J1001');


INSERT INTO class_registrations (student_id, class_id, status, grade)
VALUES
    ('SV001', 1, 'REGISTERED', NULL),
    ('SV002', 2, 'REGISTERED', NULL),
    ('SV003', 3, 'REGISTERED', NULL),
    ('SV004', 4, 'CANCELLED', NULL),
    ('SV005', 5, 'CANCELLED', NULL),
    ('SV006', 6, 'CANCELLED', NULL),
    ('SV007', 7, 'COMPLETED', 9.5),
    ('SV008', 8, 'COMPLETED', 8.0),
    ('SV009', 9, 'COMPLETED', 7.5),
    ('SV010', 10, 'COMPLETED', 6.0);

INSERT INTO class_registration_history (class_registration_id, action, reason)
VALUES
    (1, 'REGISTERED', 'Đăng ký học phần'),
    (2, 'REGISTERED', 'Đăng ký học phần'),
    (3, 'REGISTERED', 'Đăng ký học phần'),
    (4, 'CANCELLED', 'Hủy đăng ký học phần'),
    (5, 'CANCELLED', 'Hủy đăng ký học phần'),
    (6, 'CANCELLED', 'Hủy đăng ký học phần'),
    (7, 'COMPLETED', 'Hoàn thành học phần'),
    (8, 'COMPLETED', 'Hoàn thành học phần'),
    (9, 'COMPLETED', 'Hoàn thành học phần'),
    (10, 'COMPLETED', 'Hoàn thành học phần');

-- Thêm dữ liệu mẫu số điện thoại đơn giản chỉ với format quốc tế
INSERT INTO phone_patterns (country_code, regex_pattern, description) VALUES
('VN', '^\+84[0-9]{9}$', 'Vietnam: +84xxxxxxxxx'),
('US', '^\+1[2-9][0-9]{9}$', 'USA: +1xxxxxxxxxx'),
('UK', '^\+44[0-9]{10}$', 'UK: +44xxxxxxxxxx'),
('FR', '^\+33[1-9][0-9]{8}$', 'France: +33xxxxxxxxx'),
('DE', '^\+49[1-9][0-9]{10}$', 'Germany: +49xxxxxxxxxxx'),
('IN', '^\+91[6789][0-9]{9}$', 'India: +91xxxxxxxxxx'),
('JP', '^\+81[1-9][0-9]{9}$', 'Japan: +81xxxxxxxxxx'),
('CA', '^\+1[2-9][0-9]{9}$', 'Canada: +1xxxxxxxxxx'),
('AU', '^\+61[2-478][0-9]{8}$', 'Australia: +61xxxxxxxxx'),
('BR', '^\+55[1-9][0-9]{10}$', 'Brazil: +55xxxxxxxxxxx'),
('RU', '^\+7[0-9]{10}$', 'Russia: +7xxxxxxxxxx'),
('IT', '^\+39[0-9]{10}$', 'Italy: +39xxxxxxxxxx'),
('ES', '^\+34[6-9][0-9]{8}$', 'Spain: +34xxxxxxxxx'),
('CN', '^\+861[3-9][0-9]{9}$', 'China: +86xxxxxxxxxx'),
('KR', '^\+82[1-9][0-9]{8}$', 'South Korea: +82xxxxxxxxx'),
('MX', '^\+52[1-9][0-9]{9}$', 'Mexico: +52xxxxxxxxxx'),
('PH', '^\+63[9][0-9]{9}$', 'Philippines: +63xxxxxxxxxx'),
('ID', '^\+62[1-9][0-9]{10}$', 'Indonesia: +62xxxxxxxxxxx'),
('MY', '^\+60[1-9][0-9]{8}$', 'Malaysia: +60xxxxxxxxx');

INSERT INTO email_domains (domain) VALUES
    ('example.com'),
    ('student.university.edu.vn')
ON CONFLICT (domain) DO NOTHING;