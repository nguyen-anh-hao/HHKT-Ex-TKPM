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
INSERT INTO students (student_id, full_name, dob, gender, faculty_id, intake, program_id, email, phone, student_status_id, nationality)
VALUES
    ('SV001', 'Nguyễn Văn A', '2002-05-10', 'Nam', 1, 'K20', 1, 'nguyenvana@example.com', '0987654321', 1, 'Việt Nam'),
    ('SV002', 'Trần Thị B', '2001-08-15', 'Nữ', 2, 'K19', 2, 'tranthib@example.com', '0976543210', 2, 'Việt Nam'),
    ('SV003', 'Lê Hoàng C', '2003-03-22', 'Nam', 3, 'K21', 1, 'lehoangc@example.com', '0965432109', 1, 'Việt Nam'),
    ('SV004', 'Phạm Minh D', '2000-12-30', 'Nam', 1, 'K18', 3, 'phamminhd@example.com', '0954321098', 3, 'Việt Nam'),
    ('SV005', 'Hoàng Thị E', '2002-07-11', 'Nữ', 4, 'K20', 2, 'hoangthie@example.com', '0943210987', 1, 'Việt Nam'),
    ('SV006', 'Bùi Văn F', '2001-05-25', 'Nam', 2, 'K19', 1, 'buivanf@example.com', '0932109876', 2, 'Việt Nam'),
    ('SV007', 'Đặng Hải G', '2003-09-14', 'Nam', 3, 'K21', 3, 'danghaig@example.com', '0921098765', 1, 'Việt Nam'),
    ('SV008', 'Võ Thị H', '2002-01-07', 'Nữ', 1, 'K20', 2, 'vothih@example.com', '0910987654', 1, 'Việt Nam'),
    ('SV009', 'Ngô Bảo I', '2000-06-19', 'Nam', 4, 'K18', 1, 'ngobaoi@example.com', '0909876543', 3, 'Việt Nam'),
    ('SV010', 'Dương Minh K', '2003-10-23', 'Nam', 2, 'K21', 2, 'duongminhk@example.com', '0898765432', 1, 'Việt Nam'),
    ('SV011', 'Cao Thanh L', '2001-02-08', 'Nữ', 3, 'K19', 3, 'caothanhl@example.com', '0887654321', 2, 'Việt Nam'),
    ('SV012', 'Lý Văn M', '2002-04-17', 'Nam', 1, 'K20', 1, 'lyvanm@example.com', '0876543210', 1, 'Việt Nam'),
    ('SV013', 'Hồ Quang N', '2000-11-29', 'Nam', 4, 'K18', 2, 'hoquangn@example.com', '0865432109', 3, 'Việt Nam'),
    ('SV014', 'Nguyễn Hải O', '2003-07-21', 'Nam', 2, 'K21', 3, 'nguyenhaio@example.com', '0854321098', 1, 'Việt Nam'),
    ('SV015', 'Phan Mỹ P', '2002-09-12', 'Nữ', 3, 'K20', 1, 'phanmyp@example.com', '0843210987', 1, 'Việt Nam'),
    ('SV016', 'Trịnh Hoàng Q', '2001-03-06', 'Nam', 1, 'K19', 2, 'trinhhoangq@example.com', '0832109876', 2, 'Việt Nam'),
    ('SV017', 'Đỗ Bảo R', '2002-05-31', 'Nữ', 4, 'K20', 3, 'dobaor@example.com', '0821098765', 1, 'Việt Nam'),
    ('SV018', 'Tạ Văn S', '2000-08-27', 'Nam', 2, 'K18', 1, 'tavans@example.com', '0810987654', 3, 'Việt Nam'),
    ('SV019', 'Vũ Thanh T', '2003-01-19', 'Nam', 3, 'K21', 2, 'vuthanht@example.com', '0809876543', 1, 'Việt Nam'),
    ('SV020', 'Lâm Thị U', '2001-06-13', 'Nữ', 1, 'K19', 3, 'lamthiu@example.com', '0798765432', 2, 'Việt Nam');

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
