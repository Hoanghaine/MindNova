-- 1. ROLE & USER
CREATE TABLE role (
                      id INT IDENTITY PRIMARY KEY,
                      name NVARCHAR(50) UNIQUE NOT NULL -- USER, DOCTOR, ADMIN
);

CREATE TABLE [user] (
                        id INT IDENTITY PRIMARY KEY,
                        username NVARCHAR(100) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255),
    doctor_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    auth_provider VARCHAR(50) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME()
    );

CREATE TABLE user_roles (
                          user_id INT NOT NULL,
                          role_id INT NOT NULL,
                          PRIMARY KEY (user_id, role_id),
                          FOREIGN KEY (user_id) REFERENCES [user](id) ON DELETE CASCADE,
                          FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- 2. PROFILE (user / Doctor)
CREATE TABLE profile (
                         id INT IDENTITY PRIMARY KEY,
                         user_id INT NOT NULL UNIQUE,
                         avatar VARCHAR(200),
                         first_name NVARCHAR(50),
                         last_name NVARCHAR(50),
                         dob DATE,
                         gender VARCHAR(20),
                         phone VARCHAR(20)  UNIQUE,
                         address NVARCHAR(255),
                         bio NVARCHAR(MAX),
                         year_of_experience INT,
                         created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
                         FOREIGN KEY (user_id) REFERENCES [user](id)
);


-- Specialty
CREATE TABLE specialty (
                           id INT IDENTITY PRIMARY KEY,
                           name NVARCHAR(100) NOT NULL UNIQUE,
                           description NVARCHAR(255)
);

CREATE TABLE doctor_specialty (
                                 profile_id INT NOT NULL,
                                 specialty_id INT NOT NULL,
                                 PRIMARY KEY (profile_id, specialty_id),
                                 FOREIGN KEY (profile_id) REFERENCES profile(id),
                                 FOREIGN KEY (specialty_id) REFERENCES specialty(id)
);

-- Certificate
CREATE TABLE certificate (
                             id INT IDENTITY PRIMARY KEY,
                             profile_id INT NOT NULL,
                             title NVARCHAR(255) NOT NULL,
                             organization NVARCHAR(255),
                             issued_date DATE,
                             expired_at DATE,
                             file_url NVARCHAR(500),
                             FOREIGN KEY (profile_id) REFERENCES profile(id)
);

-- 3. EMOTION JOURNAL
CREATE TABLE mood_journal (
                                 id INT IDENTITY PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 mood NVARCHAR(20) CHECK (mood IN ('GREAT','GOOD','OKAY','BAD','AWFUL')),
                                 note NVARCHAR(1000),
                                 created_at DATETIME2 DEFAULT SYSDATETIME(),
                                 FOREIGN KEY (user_id) REFERENCES [user](id) ON DELETE CASCADE
);

CREATE TABLE mood_journal_emotions (
                                          journal_id INT NOT NULL,
                                          emotion NVARCHAR(50) NOT NULL,
                                          FOREIGN KEY (journal_id) REFERENCES mood_journal(id) ON DELETE CASCADE
);
-- 4. QUIZ
CREATE TABLE quiz (
                      id INT IDENTITY PRIMARY KEY,
                      title NVARCHAR(255) NOT NULL,
                      description NVARCHAR(1000),
                      created_at DATETIME2 DEFAULT SYSDATETIME()
);

CREATE TABLE quiz_question (
                              id INT IDENTITY PRIMARY KEY,
                              quiz_id INT NOT NULL,
                              question_text NVARCHAR(500) NOT NULL,
                              FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
);

CREATE TABLE quiz_answer_option (
                                  id INT IDENTITY PRIMARY KEY,
                                  question_id INT NOT NULL,
                                  text NVARCHAR(255),
                                  score INT NOT NULL,
                                  FOREIGN KEY (question_id) REFERENCES quiz_question(id) ON DELETE CASCADE
);

CREATE TABLE quiz_result (
                            id INT IDENTITY PRIMARY KEY,
                            user_id INT NOT NULL,
                            quiz_id INT NOT NULL,
                            total_score INT,
                            created_at DATETIME2 DEFAULT SYSDATETIME(),
                            FOREIGN KEY (user_id) REFERENCES [user](id) ON DELETE CASCADE,
                            FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
);

-- 5. POST + COMMENT + LIKE + REPORT
CREATE TABLE post (
                      id INT IDENTITY PRIMARY KEY,
                      author_id INT NOT NULL,
                      title NVARCHAR(255),
                      content NVARCHAR(MAX),
                      type NVARCHAR(20) CHECK (type IN ('QNA','KNOWLEDGE')),
                      created_at DATETIME2 DEFAULT SYSDATETIME(),
                      is_deleted BIT NOT NULL DEFAULT 0,
                      FOREIGN KEY (author_id) REFERENCES [user](id) ON DELETE NO ACTION
);

CREATE TABLE comment
(
    id         INT IDENTITY PRIMARY KEY,
    post_id    INT NOT NULL,
    author_id  INT NULL,
    content    NVARCHAR(1000),
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE, -- có thể giữ cascade
    FOREIGN KEY (author_id) REFERENCES [user](id) ON DELETE NO ACTION
);

CREATE TABLE [like] (
                        id INT IDENTITY PRIMARY KEY,
                        post_id INT NOT NULL,
                        user_id INT NOT NULL,
                        created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT UQ_Like UNIQUE (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES [user](id) ON DELETE CASCADE
    );

CREATE TABLE report (
                        id INT IDENTITY PRIMARY KEY,
                        post_id INT NOT NULL,
                        reporter_id INT NOT NULL,
                        reason NVARCHAR(500),
                        created_at DATETIME2 DEFAULT SYSDATETIME(),
                        FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                        FOREIGN KEY (reporter_id) REFERENCES [user](id) ON DELETE CASCADE
);

-- 6. FOLLOW
CREATE TABLE follow (
                        id INT IDENTITY PRIMARY KEY,
                        follower_id INT NOT NULL,
                        following_id INT NOT NULL,
                        created_at DATETIME2 DEFAULT SYSDATETIME(),
                        CONSTRAINT UQ_Follow UNIQUE (follower_id, following_id),
                        FOREIGN KEY (follower_id) REFERENCES [user](id) ON DELETE NO ACTION,
                        FOREIGN KEY (following_id) REFERENCES [user](id) ON DELETE CASCADE
);




-- 8. CONTENT
CREATE TABLE content (
                         id INT IDENTITY PRIMARY KEY,
                         title NVARCHAR(255),
                         type NVARCHAR(20) CHECK (type IN ('VIDEO','AUDIO','ARTICLE')),
                         url NVARCHAR(500),
                         description NVARCHAR(1000),
                         created_at DATETIME2 DEFAULT SYSDATETIME()
);

-- Bảng Appointment
CREATE TABLE appointment (
                             id BIGINT IDENTITY PRIMARY KEY,
                             doctor_id INT NOT NULL,
                             patient_id INT NULL,
                             guest_phone NVARCHAR(20) NULL,
                             guest_name NVARCHAR(255) NULL,
                             guest_address NVARCHAR(255) NULL,
                             is_guest BIT NOT NULL DEFAULT 0,
                             start_at DATETIME2 NOT NULL,
                             end_at DATETIME2 NOT NULL,
                             status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                             FOREIGN KEY (doctor_id) REFERENCES [user](id),
                             FOREIGN KEY (patient_id) REFERENCES [user](id)
);

-- Bảng DoctorSchedule
CREATE TABLE doctor_schedule (
                                id BIGINT IDENTITY PRIMARY KEY,
                                doctor_id INT NOT NULL,
                                day_of_week VARCHAR(20) NOT NULL,
                                start_time DATETIME2 NOT NULL,
                                end_time DATETIME2 NOT NULL,
                                is_available BIT NOT NULL DEFAULT 0,
                                CONSTRAINT uq_doctor_schedule UNIQUE (doctor_id, day_of_week),
                                FOREIGN KEY (doctor_id) REFERENCES [user](id)
);

-- Vx__create_notification_tables.sql

-- Bảng notification (chỉ dùng cho IN_APP)
CREATE TABLE notification (
                              id INT IDENTITY(1,1) PRIMARY KEY,
                              user_id INT NOT NULL,
                              title NVARCHAR(255) NOT NULL,
                              content NVARCHAR(MAX) NULL,
                              is_read BIT NOT NULL DEFAULT 0,
                              created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
                              sent_at DATETIME2 NULL,
                              CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
                                  REFERENCES [user](id)
                                  ON DELETE CASCADE
);

-- Bảng notification_delivery (log việc gửi đi qua các kênh)
CREATE TABLE notification_delivery (
                                       id INT IDENTITY(1,1) PRIMARY KEY,
                                       notification_id INT NULL,                -- có thể null nếu chỉ là email/websocket
                                       user_id INT NOT NULL,
                                       channel VARCHAR(20) NOT NULL,            -- EMAIL, IN_APP, WEB_SOCKET...
                                       status VARCHAR(20) NOT NULL,             -- PENDING, SENT, FAILED
                                       sent_at DATETIME2 NULL,
                                       error_message NVARCHAR(500) NULL,

                                       CONSTRAINT fk_delivery_notification FOREIGN KEY (notification_id)
                                           REFERENCES notification(id)
                                           ON DELETE SET NULL,

                                       CONSTRAINT fk_delivery_user FOREIGN KEY (user_id)
                                           REFERENCES [user](id)
                                           ON DELETE NO ACTION                  -- tránh multiple cascade paths
);
