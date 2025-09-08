-- ================================
-- 1. ROLE & USER
-- ================================
INSERT INTO role (name) VALUES
                            ('USER'),
                            ('DOCTOR'),
                            ('ADMIN');

-- User (id = 1..5)
INSERT INTO [user] (email, password, username) VALUES
    ('user1@example.com',   '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'user1'),
    ('user2@example.com',   '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'user2'),
    ('admin1@example.com',  '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'admin1');

INSERT INTO [user] (username, email, password, doctor_status)
VALUES
    ('dr.john', 'john@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.susan', 'susan@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.david', 'david@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED');

-- Gán role
INSERT INTO user_roles (user_id, role_id) VALUES
                                           (1, 1), -- user1 -> USER
                                           (2, 1), -- user2 -> USER
                                           (3, 3);-- admin1 -> ADMIN

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM [User] u, Role r
WHERE r.name = 'DOCTOR' AND u.username IN ('dr.john','dr.susan','dr.david');
-- ================================
-- 2. PROFILE (User / Doctor)
-- ================================
INSERT INTO profile (user_id, avatar, first_name, last_name, dob, gender, phone, address, bio) VALUES
                                                                                (1, 'https://randomuser.me/api/portraits/men/11.jpg', 'Nguyen','Van A', '1995-05-20', 'Male','0901234580','Hanoi', 'Người dùng bình thường'),
                                                                                (2, 'https://randomuser.me/api/portraits/women/12.jpg', 'Le','Thi Hoa', '1998-07-10', 'Female','0901294597','Hanoi', 'Người dùng bình thường'),
                                                                                (3, 'https://randomuser.me/api/portraits/men/31.jpg', 'Admin','Account', '1990-01-01', 'Other','0909284567','Hanoi', 'Quản trị viên hệ thống');


INSERT INTO profile (user_id, avatar, first_name, last_name, dob, gender, phone, address, bio,year_of_experience)
VALUES
    ((SELECT id FROM [User] WHERE username='dr.john'), 'avatar1.png','John','Smith','1980-05-12','MALE','0901234567','Hanoi','Experienced psychiatrist with 15 years.',15),
((SELECT id FROM [User] WHERE username='dr.susan'), 'avatar2.png','Susan','Lee','1985-08-20','FEMALE','0902345678','HCM','Clinical psychologist with focus on anxiety.',8),
((SELECT id FROM [User] WHERE username='dr.david'), 'avatar3.png','David','Nguyen','1978-02-15','MALE','0903456789','Danang','Therapist specialized in stress management.',9);

-- Specialty
INSERT INTO specialty (name, description)
VALUES
    ('Psychiatry','Treatment of mental health disorders.'),
    ('Clinical Psychology','Therapy and counseling.'),
    ('Child Psychology','Focus on children and adolescents.'),
    ('Cognitive Behavioral Therapy','CBT techniques.'),
    ('Stress Management','Helping patients manage stress.');

-- DoctorSpecialty
-- ===== DoctorSpecialty (random 1–2 cho mỗi bác sĩ) =====
-- Dr John: Psychiatry + CBT
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='John'), (SELECT id FROM Specialty WHERE name='Psychiatry')),
    ((SELECT id FROM Profile WHERE first_name='John'), (SELECT id FROM Specialty WHERE name='Cognitive Behavioral Therapy'));

-- Dr Susan: Clinical Psychology + Child Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Susan'), (SELECT id FROM Specialty WHERE name='Clinical Psychology')),
    ((SELECT id FROM Profile WHERE first_name='Susan'), (SELECT id FROM Specialty WHERE name='Child Psychology'));

-- Dr David: Stress Management
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='David'), (SELECT id FROM Specialty WHERE name='Stress Management'));

-- ===== Certificate (2 mỗi bác sĩ) =====
-- Dr John
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='John'),'Board Certified Psychiatry','Medical Board','2010-01-01','2030-01-01','cert_john1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='John'),'Advanced CBT Training','Harvard','2015-06-01','2030-06-01','cert_john2.pdf');

-- Dr Susan
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Susan'),'PhD Clinical Psychology','Oxford','2012-01-01','2032-01-01','cert_susan1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Susan'),'Child Therapy Specialist','APA','2018-03-01','2028-03-01','cert_susan2.pdf');

-- Dr David
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='David'),'Stress Management Coach','WHO','2009-01-01','2029-01-01','cert_david1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='David'),'Mindfulness Therapy','Stanford','2016-05-01','2031-05-01','cert_david2.pdf');


-- ================================
-- 3. EMOTION JOURNAL
-- ================================
INSERT INTO mood_journal (user_id, mood, note)
VALUES
    (1, 'GREAT', N'Feeling very productive today'),
    (1, 'OKAY', N'Just an average day, nothing special'),
    (2, 'BAD', N'Stressed because of work deadline'),
    (2, 'GOOD', N'Went for a run, feeling refreshed'),
    (1, 'AWFUL', N'Argument with a friend, very upset');

-- Journal 1 (id = 1, user 1)
INSERT INTO mood_journal_emotions (journal_id, emotion)
VALUES (1, 'HAPPY'), (1, 'EXCITED'), (1, 'GRATEFUL');

-- Journal 2 (id = 2, user 1)
INSERT INTO mood_journal_emotions (journal_id, emotion)
VALUES (2, 'TIRED');

-- Journal 3 (id = 3, user 2)
INSERT INTO mood_journal_emotions (journal_id, emotion)
VALUES (3, 'STRESSED'), (3, 'ANXIOUS');

-- Journal 4 (id = 4, user 2)
INSERT INTO mood_journal_emotions (journal_id, emotion)
VALUES (4, 'RELAX'), (4, 'HAPPY');

-- Journal 5 (id = 5, user 1)
INSERT INTO mood_journal_emotions (journal_id, emotion)
VALUES (5, 'SAD'), (5, 'ANGRY'), (5, 'DEPRESSED');
-- ================================
-- 4. QUIZ
-- ================================
INSERT INTO quiz (title, description) VALUES
    ('Đánh giá mức độ căng thẳng', 'Bài test gồm 5 câu để đánh giá stress');

INSERT INTO quiz_question (quiz_id, question_text) VALUES
                                                    (1, 'Bạn có thường xuyên thấy lo lắng không?'),
                                                    (1, 'Bạn có khó ngủ không?'),
                                                    (1, 'Bạn có thường xuyên mất tập trung không?'),
                                                    (1, 'Bạn có cảm thấy kiệt sức không?'),
                                                    (1, 'Bạn có hay cáu gắt không?');

INSERT INTO quiz_answer_option (question_id, text, score) VALUES
                                                           (1, 'Không bao giờ', 0), (1, 'Thỉnh thoảng', 1), (1, 'Thường xuyên', 2),
                                                           (2, 'Không bao giờ', 0), (2, 'Thỉnh thoảng', 1), (2, 'Thường xuyên', 2),
                                                           (3, 'Không bao giờ', 0), (3, 'Thỉnh thoảng', 1), (3, 'Thường xuyên', 2),
                                                           (4, 'Không bao giờ', 0), (4, 'Thỉnh thoảng', 1), (4, 'Thường xuyên', 2),
                                                           (5, 'Không bao giờ', 0), (5, 'Thỉnh thoảng', 1), (5, 'Thường xuyên', 2);

INSERT INTO quiz_result (user_id, quiz_id, total_score) VALUES
                                                        (1, 1, 4),
                                                        (2, 1, 2);


-- ================================
-- 5. POST + COMMENT + LIKE + REPORT
-- ================================
INSERT INTO post (author_id, title, content, type) VALUES
                                                      (1, 'Làm sao để giảm căng thẳng khi làm việc?', 'Mình hay bị stress khi deadline gần kề.', 'QNA'),
                                                      (3, 'Kỹ thuật thở 4-7-8 để thư giãn', 'Bác sĩ hướng dẫn cách thở để giảm lo âu.', 'KNOWLEDGE');

INSERT INTO Post (author_id, title, content, type) VALUES
                                                       (1, 'Làm sao để ngủ ngon hơn?', 'Mình thường bị mất ngủ vào ban đêm, có cách nào khắc phục không?', 'QNA'),
                                                       (2, 'Có nên uống cà phê khi đang căng thẳng?', 'Uống cà phê giúp tỉnh táo nhưng có làm tăng stress không?', 'QNA'),
                                                       (1, 'Cách giữ bình tĩnh khi bị người khác chỉ trích?', 'Mình hay phản ứng nóng nảy khi bị góp ý, cần cải thiện như thế nào?', 'QNA'),
                                                       (2, 'Nên tập thể dục lúc nào để giảm stress?', 'Buổi sáng hay buổi tối thì hiệu quả hơn?', 'QNA'),
                                                       (1, 'Có ứng dụng nào giúp thiền không?', 'Mọi người có app nào hay để hướng dẫn thiền không?', 'QNA'),
                                                       (2, 'Cách cân bằng giữa học tập và giải trí?', 'Mình thường học nhiều nên dễ căng thẳng, nên phân bổ thế nào?', 'QNA'),
                                                       (2, 'Ăn uống ảnh hưởng thế nào đến tâm trạng?', 'Có loại thực phẩm nào giúp giảm lo âu không?', 'QNA'),
                                                       (1, 'Cách quản lý cảm xúc khi thi cử?', 'Kỳ thi tới gần, mình rất lo lắng, mọi người có tips gì không?', 'QNA'),
                                                       (2, 'Làm sao để tăng sự tự tin khi giao tiếp?', 'Mình thường ngại nói trước đám đông, cần cải thiện thế nào?', 'QNA'),
                                                       (1, 'Có nên viết nhật ký cảm xúc mỗi ngày?', 'Viết nhật ký có giúp giảm căng thẳng thật không?', 'QNA'),
                                                       (1, 'Làm sao để ngủ ngon hơn?', 'Mình thường bị mất ngủ vào ban đêm, có cách nào khắc phục không?', 'QNA'),
                                                       (2, 'Có nên uống cà phê khi đang căng thẳng?', 'Uống cà phê giúp tỉnh táo nhưng có làm tăng stress không?', 'QNA'),
                                                       (1, 'Cách giữ bình tĩnh khi bị người khác chỉ trích?', 'Mình hay phản ứng nóng nảy khi bị góp ý, cần cải thiện như thế nào?', 'QNA'),
                                                       (2, 'Nên tập thể dục lúc nào để giảm stress?', 'Buổi sáng hay buổi tối thì hiệu quả hơn?', 'QNA'),
                                                       (1, 'Có ứng dụng nào giúp thiền không?', 'Mọi người có app nào hay để hướng dẫn thiền không?', 'QNA'),
                                                       (2, 'Cách cân bằng giữa học tập và giải trí?', 'Mình thường học nhiều nên dễ căng thẳng, nên phân bổ thế nào?', 'QNA'),
                                                       (2, 'Ăn uống ảnh hưởng thế nào đến tâm trạng?', 'Có loại thực phẩm nào giúp giảm lo âu không?', 'QNA'),
                                                       (1, 'Cách quản lý cảm xúc khi thi cử?', 'Kỳ thi tới gần, mình rất lo lắng, mọi người có tips gì không?', 'QNA'),
                                                       (2, 'Làm sao để tăng sự tự tin khi giao tiếp?', 'Mình thường ngại nói trước đám đông, cần cải thiện thế nào?', 'QNA'),
                                                       (1, 'Có nên viết nhật ký cảm xúc mỗi ngày?', 'Viết nhật ký có giúp giảm căng thẳng thật không?', 'QNA');

INSERT INTO comment (post_id, author_id, content) VALUES
                                                    (1, 3, 'Bạn có thể thử thiền hoặc tập thở nhé.'),
                                                    (2, 1, 'Cảm ơn bác sĩ, rất hữu ích!');

INSERT INTO [like] (post_id, user_id) VALUES
    (1, 3),
    (2, 1);

INSERT INTO report (post_id, reporter_id, reason) VALUES
    (1, 5, 'Nội dung trùng lặp');


-- ================================
-- 6. FOLLOW
-- ================================
INSERT INTO follow (follower_id, following_id) VALUES
                                                 (1, 3), -- User1 follow Doctor1
                                                 (2, 4), -- User2 follow Doctor2
                                                 (5, 3); -- Admin follow Doctor1



-- ================================
-- 8. CONTENT
-- ================================
INSERT INTO content (title, type, url, description) VALUES
                                                        ('Video hướng dẫn thở', 'VIDEO', 'https://example.com/video1.mp4', 'Bài tập thở thư giãn'),
                                                        ('Nhạc thiền 15 phút', 'AUDIO', 'https://example.com/audio1.mp3', 'Nhạc nền giúp ngủ ngon'),
                                                        ('Bài viết: Cách quản lý stress', 'ARTICLE', 'https://example.com/article1', 'Thông tin hữu ích về stress');


-- ================================
-- 9. APPOINTMENT
-- ================================
-- ===== DoctorSchedule mặc định (7 ngày / tuần, is_available=0) =====
-- 7 ngày/tuần cho 3 bác sĩ, giờ mặc định 09:00–17:00 UTC
INSERT INTO doctor_schedule (doctor_id, day_of_week, start_time, end_time, is_available)
SELECT u.id, d.day,
       CAST('2000-01-01 09:00:00' AS DATETIME2),
       CAST('2000-01-01 17:00:00' AS DATETIME2),
       0
FROM [user] u
    JOIN (VALUES
    ('MONDAY'),('TUESDAY'),('WEDNESDAY'),
    ('THURSDAY'),('FRIDAY'),('SATURDAY'),('SUNDAY')
    ) AS d(day) ON 1=1
WHERE u.username IN ('dr.john','dr.susan','dr.david');

-- Appointment (patient = user1 id=1, user2 id=2; doctor = 3,4)
-- Ví dụ: 9:00–10:00 ICT (UTC+7) => 02:00–03:00 UTC
INSERT INTO appointment (doctor_id, patient_id, start_at, end_at, status)
VALUES
    (
        (SELECT id FROM [user] WHERE username='dr.john'),
    (SELECT id FROM [user] WHERE username='user1'),
    CAST('2025-08-25 02:00:00' AS DATETIME2),
    CAST('2025-08-25 03:00:00' AS DATETIME2),
    'CONFIRMED'
    ),
(
  (SELECT id FROM [user] WHERE username='dr.john'),
  (SELECT id FROM [user] WHERE username='user2'),
  CAST('2025-08-26 03:00:00' AS DATETIME2),  -- 10:00 ICT -> 03:00 UTC
  CAST('2025-08-26 04:00:00' AS DATETIME2),
  'PENDING'
),
(
  (SELECT id FROM [user] WHERE username='dr.susan'),
  (SELECT id FROM [user] WHERE username='user1'),
  CAST('2025-08-28 07:00:00' AS DATETIME2),  -- 14:00 ICT -> 07:00 UTC
  CAST('2025-08-28 08:00:00' AS DATETIME2),
  'CONFIRMED'
);

-- ===================== USER =====================
INSERT INTO [user] (username, email, password, doctor_status)
VALUES
    ('dr.linda', 'linda@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.michael', 'michael@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.emily', 'emily@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.james', 'james@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.olivia', 'olivia@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.william', 'william@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.sophia', 'sophia@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.robert', 'robert@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.anna', 'anna@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED'),
    ('dr.daniel', 'daniel@example.com', '$2a$10$dfrXz1UtS./mUzcT4Qs9fuGwKtcibisRoXoEHL3R8TJKos1bxRMaa', 'APPROVED');

-- ===================== ROLE DOCTOR =====================
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM [User] u, Role r
WHERE r.name = 'DOCTOR'
  AND u.username IN (
    'dr.linda','dr.michael','dr.emily','dr.james','dr.olivia',
    'dr.william','dr.sophia','dr.robert','dr.anna','dr.daniel'
    );

-- ===================== PROFILE =====================
INSERT INTO profile (user_id, avatar, first_name, last_name, dob, gender, phone, address, bio, year_of_experience)
VALUES
    ((SELECT id FROM [User] WHERE username='dr.linda'),'avatar4.png','Linda','Tran','1982-07-15','FEMALE','0901111222','Hue','Expert in family therapy.',12),
    ((SELECT id FROM [User] WHERE username='dr.michael'),'avatar5.png','Michael','Pham','1979-11-23','MALE','0901111333','Hanoi','Specialist in addiction counseling.',18),
    ((SELECT id FROM [User] WHERE username='dr.emily'),'avatar6.png','Emily','Hoang','1988-03-19','FEMALE','0901111444','HCM','Focus on adolescent mental health.',9),
    ((SELECT id FROM [User] WHERE username='dr.james'),'avatar7.png','James','Le','1975-09-30','MALE','0901111555','Danang','Experienced neurologist and counselor.',20),
    ((SELECT id FROM [User] WHERE username='dr.olivia'),'avatar8.png','Olivia','Nguyen','1983-04-22','FEMALE','0901111666','Cantho','Specialist in trauma recovery.',14),
    ((SELECT id FROM [User] WHERE username='dr.william'),'avatar9.png','William','Vo','1981-01-10','MALE','0901111777','Hanoi','Expert in stress reduction.',16),
    ((SELECT id FROM [User] WHERE username='dr.sophia'),'avatar10.png','Sophia','Bui','1990-06-11','FEMALE','0901111888','HCM','Therapist focusing on depression.',7),
    ((SELECT id FROM [User] WHERE username='dr.robert'),'avatar11.png','Robert','Dao','1977-08-25','MALE','0901111999','Haiphong','Psychiatrist with holistic approach.',19),
    ((SELECT id FROM [User] WHERE username='dr.anna'),'avatar12.png','Anna','Phan','1986-12-05','FEMALE','0901112000','Hanoi','Focus on workplace mental health.',10),
    ((SELECT id FROM [User] WHERE username='dr.daniel'),'avatar13.png','Daniel','Nguyen','1974-02-14','MALE','0901112111','HCM','Veteran psychiatrist with 22 years.',22);

-- ===================== SPECIALTY =====================
-- Linda → Clinical Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Linda'), (SELECT id FROM Specialty WHERE name='Clinical Psychology'));

-- Michael → Stress Management
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Michael'), (SELECT id FROM Specialty WHERE name='Stress Management'));

-- Emily → Child Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Emily'), (SELECT id FROM Specialty WHERE name='Child Psychology'));

-- James → Psychiatry + CBT
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='James'), (SELECT id FROM Specialty WHERE name='Psychiatry')),
    ((SELECT id FROM Profile WHERE first_name='James'), (SELECT id FROM Specialty WHERE name='Cognitive Behavioral Therapy'));

-- Olivia → Clinical Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Olivia'), (SELECT id FROM Specialty WHERE name='Clinical Psychology'));

-- William → Stress Management + CBT
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='William'), (SELECT id FROM Specialty WHERE name='Stress Management')),
    ((SELECT id FROM Profile WHERE first_name='William'), (SELECT id FROM Specialty WHERE name='Cognitive Behavioral Therapy'));

-- Sophia → Psychiatry
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Sophia'), (SELECT id FROM Specialty WHERE name='Psychiatry'));

-- Robert → Clinical Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Robert'), (SELECT id FROM Specialty WHERE name='Clinical Psychology'));

-- Anna → Child Psychology
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES ((SELECT id FROM Profile WHERE first_name='Anna'), (SELECT id FROM Specialty WHERE name='Child Psychology'));

-- Daniel → Psychiatry + CBT
INSERT INTO doctor_specialty (profile_id, specialty_id)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Daniel'), (SELECT id FROM Specialty WHERE name='Psychiatry')),
    ((SELECT id FROM Profile WHERE first_name='Daniel'), (SELECT id FROM Specialty WHERE name='Cognitive Behavioral Therapy'));
-- ===================== CERTIFICATE =====================
-- Linda
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Linda'),'Certified Family Therapist','APA','2012-01-01','2032-01-01','cert_linda1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Linda'),'Marriage Counseling Training','Yale','2016-05-01','2031-05-01','cert_linda2.pdf');

-- Michael
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Michael'),'Addiction Specialist','WHO','2005-01-01','2025-01-01','cert_michael1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Michael'),'Substance Abuse Therapy','Harvard','2010-06-01','2030-06-01','cert_michael2.pdf');

-- Emily
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Emily'),'Adolescent Therapy','APA','2014-01-01','2029-01-01','cert_emily1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Emily'),'Child Mental Health','Oxford','2017-03-01','2032-03-01','cert_emily2.pdf');

-- James
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='James'),'Neurology Board','Medical Council','2000-01-01','2030-01-01','cert_james1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='James'),'Counseling Therapy','APA','2008-09-01','2028-09-01','cert_james2.pdf');

-- Olivia
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Olivia'),'Trauma Specialist','WHO','2010-01-01','2030-01-01','cert_olivia1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Olivia'),'PTSD Therapy','Stanford','2015-07-01','2030-07-01','cert_olivia2.pdf');

-- William
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='William'),'Stress Management Certification','APA','2006-01-01','2026-01-01','cert_william1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='William'),'Mindfulness Instructor','Harvard','2012-04-01','2032-04-01','cert_william2.pdf');

-- Sophia
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Sophia'),'Depression Therapy Training','APA','2015-01-01','2030-01-01','cert_sophia1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Sophia'),'Cognitive Therapy','Oxford','2019-06-01','2029-06-01','cert_sophia2.pdf');

-- Robert
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Robert'),'Psychiatry License','Medical Board','2003-01-01','2028-01-01','cert_robert1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Robert'),'Holistic Psychiatry','Yale','2010-06-01','2030-06-01','cert_robert2.pdf');

-- Anna
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Anna'),'Workplace Mental Health','APA','2011-01-01','2031-01-01','cert_anna1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Anna'),'Organizational Psychology','Oxford','2017-08-01','2032-08-01','cert_anna2.pdf');

-- Daniel
INSERT INTO certificate (profile_id, title, organization, issued_date, expired_at, file_url)
VALUES
    ((SELECT id FROM Profile WHERE first_name='Daniel'),'Psychiatry Specialist','Medical Council','1999-01-01','2029-01-01','cert_daniel1.pdf'),
    ((SELECT id FROM Profile WHERE first_name='Daniel'),'CBT Advanced','Harvard','2008-05-01','2028-05-01','cert_daniel2.pdf');
