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
(2, 4)  -- Đã thôi học

-- "Đã tốt nghiệp" has no transitions

-- "Đã thôi học" has no transitions;

-- Thêm dữ liệu mẫu số điện thoại đơn giản chỉ với format quốc tế
INSERT INTO phone_patterns (country_code, regex_pattern, description) VALUES
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