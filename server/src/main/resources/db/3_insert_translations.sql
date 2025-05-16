-- Dịch tên các khoa (faculties)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Faculty', 1, 'facultyName', 'en', 'Faculty of Law'),
    ('Faculty', 2, 'facultyName', 'en', 'Faculty of Business English'),
    ('Faculty', 3, 'facultyName', 'en', 'Faculty of Japanese'),
    ('Faculty', 4, 'facultyName', 'en', 'Faculty of French');

-- Dịch trạng thái sinh viên (student_statuses)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('StudentStatus', 1, 'studentStatusName', 'en', 'Enrolled'),
    ('StudentStatus', 2, 'studentStatusName', 'en', 'Graduated'),
    ('StudentStatus', 3, 'studentStatusName', 'en', 'Withdrawn'),
    ('StudentStatus', 4, 'studentStatusName', 'en', 'Leave of Absence');

-- Dịch tên chương trình học (programs)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Program', 1, 'programName', 'en', 'Full-time'),
    ('Program', 2, 'programName', 'en', 'Articulation Program'),
    ('Program', 3, 'programName', 'en', 'Work-Study Program');

-- Dịch tên các môn học (courses)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Course', 1, 'courseName', 'en', 'Introduction to Programming'),
    ('Course', 1, 'description', 'en', 'Basic programming concepts'),
    ('Course', 2, 'courseName', 'en', 'Data Structures'),
    ('Course', 2, 'description', 'en', 'Study of data structures'),
    ('Course', 3, 'courseName', 'en', 'Algorithms'),
    ('Course', 3, 'description', 'en', 'Fundamental knowledge of algorithms'),
    ('Course', 4, 'courseName', 'en', 'Database Management Systems'),
    ('Course', 4, 'description', 'en', 'Database design and SQL'),
    ('Course', 5, 'courseName', 'en', 'Computer Networks'),
    ('Course', 5, 'description', 'en', 'Network protocols and security'),
    ('Course', 6, 'courseName', 'en', 'Operating Systems'),
    ('Course', 6, 'description', 'en', 'Processes, threads and memory management'),
    ('Course', 7, 'courseName', 'en', 'Software Engineering'),
    ('Course', 7, 'description', 'en', 'Software development lifecycle'),
    ('Course', 8, 'courseName', 'en', 'Artificial Intelligence'),
    ('Course', 8, 'description', 'en', 'Machine learning and AI concepts'),
    ('Course', 9, 'courseName', 'en', 'Network Security'),
    ('Course', 9, 'description', 'en', 'Security threats and measures'),
    ('Course', 10, 'courseName', 'en', 'Cloud Computing'),
    ('Course', 10, 'description', 'en', 'Introduction to cloud services');

-- Dịch lịch học của các lớp (classes.schedule)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Class', 1, 'schedule', 'en', 'Mon-Wed 10:00-12:00'),
    ('Class', 2, 'schedule', 'en', 'Tue-Thu 14:00-16:00'),
    ('Class', 3, 'schedule', 'en', 'Mon-Wed 08:00-10:00'),
    ('Class', 4, 'schedule', 'en', 'Fri 09:00-12:00'),
    ('Class', 5, 'schedule', 'en', 'Tue-Thu 10:00-12:00'),
    ('Class', 6, 'schedule', 'en', 'Wed 14:00-17:00'),
    ('Class', 7, 'schedule', 'en', 'Thu 09:00-12:00'),
    ('Class', 8, 'schedule', 'en', 'Mon 14:00-17:00'),
    ('Class', 9, 'schedule', 'en', 'Fri 08:00-11:00'),
    ('Class', 10, 'schedule', 'en', 'Wed 09:00-12:00');

-- Dịch lý do trong lịch sử đăng ký (class_registration_history.reason)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('ClassRegistrationHistory', 1, 'reason', 'en', 'Course registration'),
    ('ClassRegistrationHistory', 2, 'reason', 'en', 'Course registration'),
    ('ClassRegistrationHistory', 3, 'reason', 'en', 'Course registration'),
    ('ClassRegistrationHistory', 4, 'reason', 'en', 'Course registration cancellation'),
    ('ClassRegistrationHistory', 5, 'reason', 'en', 'Course registration cancellation'),
    ('ClassRegistrationHistory', 6, 'reason', 'en', 'Course registration cancellation'),
    ('ClassRegistrationHistory', 7, 'reason', 'en', 'Course completion'),
    ('ClassRegistrationHistory', 8, 'reason', 'en', 'Course completion'),
    ('ClassRegistrationHistory', 9, 'reason', 'en', 'Course completion'),
    ('ClassRegistrationHistory', 10, 'reason', 'en', 'Course completion');

-- Dịch loại địa chỉ (addresses.address_type)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Address', 1, 'addressType', 'en', 'Permanent Address'),
    ('Address', 2, 'addressType', 'en', 'Temporary Address'),
    ('Address', 3, 'addressType', 'en', 'Permanent Address'),
    ('Address', 4, 'addressType', 'en', 'Permanent Address'),
    ('Address', 5, 'addressType', 'en', 'Temporary Address'),
    ('Address', 6, 'addressType', 'en', 'Permanent Address'),
    ('Address', 7, 'addressType', 'en', 'Temporary Address'),
    ('Address', 8, 'addressType', 'en', 'Permanent Address'),
    ('Address', 9, 'addressType', 'en', 'Temporary Address'),
    ('Address', 10, 'addressType', 'en', 'Permanent Address'),
    ('Address', 11, 'addressType', 'en', 'Temporary Address'),
    ('Address', 12, 'addressType', 'en', 'Permanent Address'),
    ('Address', 13, 'addressType', 'en', 'Temporary Address'),
    ('Address', 14, 'addressType', 'en', 'Permanent Address'),
    ('Address', 15, 'addressType', 'en', 'Temporary Address');

-- Dịch loại giấy tờ (documents.document_type)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Document', 1, 'documentType', 'en', 'Citizen ID Card'),
    ('Document', 2, 'documentType', 'en', 'Passport');

-- Dịch nơi cấp giấy tờ (documents.issued_by)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Document', 1, 'issuedBy', 'en', 'Ho Chi Minh City'),
    ('Document', 2, 'issuedBy', 'en', 'Hanoi');

-- Dịch ghi chú giấy tờ (documents.note)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Document', 2, 'note', 'en', 'Used for studying abroad');

-- Dịch quốc tịch sinh viên (students.nationality)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Student', 1, 'nationality', 'en', 'Vietnam'),
    ('Student', 2, 'nationality', 'en', 'Vietnam'),
    ('Student', 3, 'nationality', 'en', 'Vietnam'),
    ('Student', 4, 'nationality', 'en', 'Vietnam'),
    ('Student', 5, 'nationality', 'en', 'Vietnam'),
    ('Student', 6, 'nationality', 'en', 'United States'),
    ('Student', 7, 'nationality', 'en', 'United Kingdom'),
    ('Student', 8, 'nationality', 'en', 'Vietnam'),
    ('Student', 9, 'nationality', 'en', 'China'),
    ('Student', 10, 'nationality', 'en', 'Vietnam'),
    ('Student', 11, 'nationality', 'en', 'France'),
    ('Student', 12, 'nationality', 'en', 'Vietnam'),
    ('Student', 13, 'nationality', 'en', 'Japan'),
    ('Student', 14, 'nationality', 'en', 'South Korea'),
    ('Student', 15, 'nationality', 'en', 'Vietnam'),
    ('Student', 16, 'nationality', 'en', 'Italy'),
    ('Student', 17, 'nationality', 'en', 'Australia'),
    ('Student', 18, 'nationality', 'en', 'Spain'),
    ('Student', 19, 'nationality', 'en', 'Mexico'),
    ('Student', 20, 'nationality', 'en', 'India');

-- Dịch giới tính sinh viên (students.gender)
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Student', 1, 'gender', 'en', 'Male'),
    ('Student', 2, 'gender', 'en', 'Female'),
    ('Student', 3, 'gender', 'en', 'Male'),
    ('Student', 4, 'gender', 'en', 'Male'),
    ('Student', 5, 'gender', 'en', 'Female'),
    ('Student', 6, 'gender', 'en', 'Male'),
    ('Student', 7, 'gender', 'en', 'Female'),
    ('Student', 8, 'gender', 'en', 'Female'),
    ('Student', 9, 'gender', 'en', 'Male'),
    ('Student', 10, 'gender', 'en', 'Male'),
    ('Student', 11, 'gender', 'en', 'Female'),
    ('Student', 12, 'gender', 'en', 'Male'),
    ('Student', 13, 'gender', 'en', 'Male'),
    ('Student', 14, 'gender', 'en', 'Female'),
    ('Student', 15, 'gender', 'en', 'Female'),
    ('Student', 16, 'gender', 'en', 'Male'),
    ('Student', 17, 'gender', 'en', 'Female'),
    ('Student', 18, 'gender', 'en', 'Male'),
    ('Student', 19, 'gender', 'en', 'Female'),
    ('Student', 20, 'gender', 'en', 'Male');

-- Dịch địa chỉ (addresses) - phường, quận, thành phố
INSERT INTO translations (entity_type, entity_id, field_name, language_code, translated_value)
VALUES
    ('Address', 1, 'wardCommune', 'en', 'Ward 1'),
    ('Address', 1, 'district', 'en', 'District 3'),
    ('Address', 1, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 1, 'country', 'en', 'Vietnam'),

    ('Address', 2, 'wardCommune', 'en', 'Ward 5'),
    ('Address', 2, 'district', 'en', 'District 1'),
    ('Address', 2, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 2, 'country', 'en', 'Vietnam'),

    ('Address', 3, 'wardCommune', 'en', 'Ward 2'),
    ('Address', 3, 'district', 'en', 'District 4'),
    ('Address', 3, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 3, 'country', 'en', 'Vietnam'),

    ('Address', 4, 'wardCommune', 'en', 'Ward 6'),
    ('Address', 4, 'district', 'en', 'District 1'),
    ('Address', 4, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 4, 'country', 'en', 'Vietnam'),

    ('Address', 5, 'wardCommune', 'en', 'Ward 3'),
    ('Address', 5, 'district', 'en', 'District 5'),
    ('Address', 5, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 5, 'country', 'en', 'Vietnam'),

    ('Address', 6, 'wardCommune', 'en', 'Ward 7'),
    ('Address', 6, 'district', 'en', 'District 10'),
    ('Address', 6, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 6, 'country', 'en', 'Vietnam'),

    ('Address', 7, 'wardCommune', 'en', 'Ward 8'),
    ('Address', 7, 'district', 'en', 'District 6'),
    ('Address', 7, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 7, 'country', 'en', 'Vietnam'),

    ('Address', 8, 'wardCommune', 'en', 'Ward 9'),
    ('Address', 8, 'district', 'en', 'District 2'),
    ('Address', 8, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 8, 'country', 'en', 'Vietnam'),

    ('Address', 9, 'wardCommune', 'en', 'Ward 10'),
    ('Address', 9, 'district', 'en', 'District 7'),
    ('Address', 9, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 9, 'country', 'en', 'Vietnam'),

    ('Address', 10, 'wardCommune', 'en', 'Ward 11'),
    ('Address', 10, 'district', 'en', 'District 8'),
    ('Address', 10, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 10, 'country', 'en', 'Vietnam'),

    ('Address', 11, 'wardCommune', 'en', 'Ward 12'),
    ('Address', 11, 'district', 'en', 'Tan Binh District'),
    ('Address', 11, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 11, 'country', 'en', 'Vietnam'),

    ('Address', 12, 'wardCommune', 'en', 'Ward 13'),
    ('Address', 12, 'district', 'en', 'District 9'),
    ('Address', 12, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 12, 'country', 'en', 'Vietnam'),

    ('Address', 13, 'wardCommune', 'en', 'Ward 14'),
    ('Address', 13, 'district', 'en', 'District 11'),
    ('Address', 13, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 13, 'country', 'en', 'Vietnam'),

    ('Address', 14, 'wardCommune', 'en', 'Ward 15'),
    ('Address', 14, 'district', 'en', 'Binh Thanh District'),
    ('Address', 14, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 14, 'country', 'en', 'Vietnam'),

    ('Address', 15, 'wardCommune', 'en', 'Ward 16'),
    ('Address', 15, 'district', 'en', 'Go Vap District'),
    ('Address', 15, 'cityProvince', 'en', 'Ho Chi Minh City'),
    ('Address', 15, 'country', 'en', 'Vietnam');

