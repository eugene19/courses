INSERT INTO user_roles (id, role)
VALUES (1, 'STUDENT');
INSERT INTO user_roles (id, role)
VALUES (2, 'LECTURE');

INSERT INTO course_statuses (id, status)
VALUES (1, 'NOT_STARTED');
INSERT INTO course_statuses (id, status)
VALUES (2, 'IN_PROGRESS');
INSERT INTO course_statuses (id, status)
VALUES (3, 'FINISHED');

INSERT INTO users (id, login, password, surname, name, email, birthday, role_id)
VALUES (1, 'user1', '698d51a19d8a121ce581499d7b701668', 'Surname1', 'Name1',
        'email1@test.ru', '2018-08-15',
        1);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id)
VALUES (2, 'user2', '698d51a19d8a121ce581499d7b701668', 'Surname2', 'Name2',
        'email2@test.ru', '2018-08-15',
        2);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id)
VALUES (3, 'user3', '698d51a19d8a121ce581499d7b701668', 'Surname3', 'Name3',
        'email3@test.ru', '2018-08-15',
        2);

INSERT INTO courses (id, summary, description, students_limit)
VALUES (1, 'Summary1', 'Description1', 10);
INSERT INTO courses (id, summary, description, students_limit)
VALUES (2, 'Summary2', 'Description2', 10);
INSERT INTO courses (id, summary, description, students_limit)
VALUES (3, 'Summary3', 'Description3', 10);
INSERT INTO courses (id, summary, description, students_limit)
VALUES (4, 'Summary4', 'Description4', 10);
INSERT INTO courses (id, summary, description, students_limit)
VALUES (5, 'Summary5', 'Description5', 10);
INSERT INTO courses (id, summary, description, students_limit)
VALUES (6, 'Summary6', 'Description6', 10);

INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (1, 1, '2020-12-20', '2021-12-20', 2, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (2, 2, '2020-12-20', '2021-12-20', 2, 2);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (3, 3, '2020-12-20', '2021-12-20', 2, 3);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (4, 4, '2020-12-20', '2021-12-20', 3, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (5, 5, '2020-12-20', '2021-12-20', 3, 2);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (6, 6, '2020-12-20', '2021-12-20', 3, 3);

INSERT INTO course_results (id, mark, comment)
VALUES (1, 5, 'Good');
INSERT INTO course_results (id, mark, comment)
VALUES (2, 2, 'Bad student');

INSERT INTO user_courses (id, user_id, course_id, course_result_id)
VALUES (1, 1, 3, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id)
VALUES (2, 1, 6, 2);