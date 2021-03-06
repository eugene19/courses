INSERT INTO user_roles (id, role)
VALUES (1, 'STUDENT');
INSERT INTO user_roles (id, role)
VALUES (2, 'LECTURER');

INSERT INTO course_statuses (id, status)
VALUES (1, 'NOT_STARTED');
INSERT INTO course_statuses (id, status)
VALUES (2, 'IN_PROGRESS');
INSERT INTO course_statuses (id, status)
VALUES (3, 'FINISHED');

INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (1, 'user1', '698d51a19d8a121ce581499d7b701668', 'Дегтярёв', 'Евгений',
        'email1@test.ru', '2018-08-15',
        1, '1.jpg');
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (2, 'user2', '698d51a19d8a121ce581499d7b701668', 'Иванов', 'Иван',
        'email2@test.ru', '2018-08-15',
        1, '2.jpg');
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (3, 'user3', '698d51a19d8a121ce581499d7b701668', 'Петров', 'Петр',
        'email3@test.ru', '2018-08-15',
        1, '3.jpg');
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (4, 'user4', '698d51a19d8a121ce581499d7b701668', 'Сидоров', 'Лев',
        'email4@test.ru', '2018-08-15',
        1, null);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (5, 'user5', '698d51a19d8a121ce581499d7b701668', 'Шоушенко', 'Александр',
        'email5@test.ru', '2018-08-15',
        1, null);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (6, 'user6', '698d51a19d8a121ce581499d7b701668', 'Груздев', 'Олег',
        'email6@test.ru', '2018-08-15',
        1, null);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (7, 'user7', '698d51a19d8a121ce581499d7b701668', 'Лев', 'Лёва',
        'email7@test.ru', '2018-08-15',
        1, null);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (8, 'user8', '698d51a19d8a121ce581499d7b701668', 'Максимчук', 'Максим',
        'email8@test.ru', '2018-08-15',
        1, null);
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (9, 'user9', '698d51a19d8a121ce581499d7b701668', 'Василенко', 'Василий',
        'email9@test.ru', '2018-08-15',
        2, '9.jpeg');
INSERT INTO users (id, login, password, surname, name, email, birthday, role_id,
                   photo_path)
VALUES (10, 'user10', '698d51a19d8a121ce581499d7b701668', 'Степаненко',
        'Степан',
        'email10@test.ru', '2018-08-15',
        2, '10.jpg');

INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (1, 'Промышленная разработка программного обеспечения на Java',
        'Язык программирования Java находится в числе лидеров во многих рейтингах: TIOBE – на основе подсчёта результатов поисковых запросов, PYPL – по анализу популярности в поисковике Google, IEEE – по комплексу показателей, таких как упоминание в проектах, статьях, вакансиях и других. Такая популярность обусловлена практически безграничными его возможностями и областями применения. Java не зависит от определённой платформы, его называют безопасным, портативным, высокопроизводительным и динамичным языком.  Специалист, который знает этот язык, точно не останется без работы – уже более 7 миллиардов устройств по всему миру работают на Java. При этом те, кто освоит основы программирования на Java, могут развиваться в совершенно разных направлениях: заниматься enterprise-разработкой, промышленным программированием, разработкой мобильных приложений под Android, автоматизированным тестированием или программной роботизацией бизнес-процессов (RPA). ',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (2, 'Основы веб-технологий',
        'Если у вас есть общие знания в области информационных технологий и вы хотите понять, как ведётся web-разработка, и получить практический опыт в этой сфере, то курс «Основы веб-технологий» для вас.  Современные web-технологии предоставляют разработчикам неограниченные возможности для воплощения своих идей. Но чтобы использовать весь их потенциал, стоит разобраться, как с ними работать и как «с нуля» создавать web-приложения. И для начала необходимо постигнуть азы данного направления разработки: понять разницу и взаимосвязь front-end и back-end, освоить основы HTML, научиться работать с фреймворками (среди наиболее популярных Bootstrap и JQuery) и так далее.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (3, 'Технологии разработки энтерпрайз-решений на Java',
        'Enterprise-разработка направлена на решение проблем бизнеса путём создания программных продуктов. Лучше всего для этих целей подходит язык программирования Java. Его характеристики – кроссплатформенность, надёжность, безопасность, объекто-ориентированность – позволяют максимально эффективно удовлетворять требования бизнеса.  Если вы изучили основы программирования на Java и решили строить карьеру в IT в качестве Java-разработчика, то курс «Технологии разработки энтерпрайз-решений на Java» точно для вас. Он поможет изучить стек технологий и фреймворки современной промышленной разработки на Java, научиться применять основные инструменты, необходимые для создания веб-проектов, и подготовиться к работе в IT-компаниях.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (4, 'Курсы Front-end: обучение на web-разработчика',
        'С расширением выбора девайсов и браузеров возрастает потребность в гибких, адаптивных пользовательских интерфейсах (никому ведь не хочется пользоваться сайтом, который отображается лишь наполовину). Именно над этим и трудится Front-end-разработчик: от адаптации и верстки дизайн-макетов до реализации сложной логики взаимодействия с пользователями при разработке сайта.  Помимо основных веб-технологий Front-end-разработки (HTML, CSS, JavaScript), этот специалист хорошо владеет фреймворками и библиотеками (React, Angular), знает, что скрыто «под капотом» — в серверной части сайта. Также он умеет работать с репозиториями и системами контроля версий (Git, CVS), может применять графические редакторы и шаблоны различных CMS.  Курсы по созданию сайтов помогут погрузиться в мир веб-разработки. Front-end разработчики входят в число самых востребованных специалистов IT-сферы со средней зарплатой в $1500. Это легко объяснить: количество ресурсов в сети постоянно растет, всем им нужны «живые», понятные и работающие без сбоев во всех популярных браузерах интерфейсы и страницы. Высший пилотаж — создание браузерных игр.',
        'servlet.pdf', 5);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (5, 'Разработка мобильных приложений на Android',
        'Разработка мобильных приложений для Android – перспективное направление в IT-индустрии. По данным аналитиков Gartner, на долю Android приходится в общей сложности 72 процента мирового мобильного рынка.  Основной язык для Android-разработки – Java. Несколько лет подряд он занимает лидирующие позиции в различных рейтингах, вошёл в пятёрку самых популярных языков программирования по мнению участников опроса Stack Overflow.  Java позволяет реализовывать самые разнообразные проекты и использовать возможности Android в полном объёме.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (6, 'Разработка на Node.js',
        'JavaScript на стороне бэкенда У веб-разработки есть весьма интересная особенность – часть программной логики повторяется и на фронтенде, и на бэкенде. Например, логика валидации заполнения форм должна быть реализована в первом случае для удобства пользователя, во втором – для безопасности.  Ранее фронтенд и бэкенд сайта разрабатывался на разных языках (и сейчас зачастую тоже) и технологических стеках. Как следствие, повторяющуюся часть программы реализовывали разные специалисты, решая при этом одни и те же вопросы двумя способами.  Но когда платформа Node.js достигла зрелости, появилась возможность создавать бэкенд сайта на языке JavaScript, который традиционно ассоциируется с фронтендом. Это привело к разработке нового технологического стека для бэкенда, открывающего перед веб-разработчиками дополнительные возможности и перспективы.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (7, 'Курсы PHP (разработка веб-приложений)',
        'Язык PHP используется для разработки серверной части веб-приложений — бэкенда. Он позволяет за короткое время создавать сайты, отвечающие последним требованиям современности. Facebook, поисковая система Yahoo, системы управления контентом сайта WordPress, Drupal и многие другие созданы на PHP. PHP сочетает в себе достоинства C и Perl. Но имеет перед ними преимущества: команды PHP имплементируются в HTML-документ, а его скрипты выполняются на веб-сервере. Это позволяет PHP-программистам создавать быстродействующие сайты с высокой производительностью.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (8, 'Реляционные базы данных и SQL',
        'Редкое приложение сегодня обходится без баз данных, наиболее распространенным типом среди которых считаются реляционные. Для работы с ними применяется язык SQL. Знание баз данных и умение писать эффективные запросы понадобятся, если вы хотите построить успешную карьеру в IT в качестве back-end-разработчика или двигаться в направлении Business Intelligence и аналитики данных. Получить необходимые навыки поможет курс по реляционным базам данных и SQL от IT-компании Syberry, которая известна своим сильным упором на инженерный подход к программированию. На теоретических лекциях и практических занятиях вы получите необходимый набор инструментов для взаимодействия с базами данных и СУБД. Курс рассчитан на серьезную самостоятельную работу слушателей: вы будете выполнять домашние задания, проводить мастер-классы, работать в команде и в итоге создадите собственный веб-проект. Обучение завершится его презентацией и финальным экзаменом.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (9, 'Разработка облачных приложений',
        '«Облака» считаются одной из передовых технологий наравне с искусственным интеллектом и IoT. Потому умение разрабатывать приложения на основе облачных технологий – тот навык, который позволит строить успешную IT-карьеру. У многих крупных IT-компаний есть cloud-экспертиза. Речь идёт про EPAM, SCAND, Altoros, CompatibL, ISsoft, Godel Technologies, где всегда есть потребность в грамотных специалистах. Следовательно, пройдя обучение разработке cloud-приложений, вы повысите свои шансы на трудоустройство в IT.',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (10, 'Разработка мобильных приложений под iOS',
        'Операционная система iOS, среди основных преимуществ которой высокая скорость работы и понятный интерфейс, предназначена исключительно для устройств компании Apple. Речь идёт о более 1,3 миллиардов активных гаджетов. Для того, чтобы упростить процесс разработки приложений под iOS, в 2014 году компания представила язык программирования с открытым исходным кодом Swift. Его оценили и теперь активно применяют программисты во всём мире. Всего за четыре года Swift удалось войти в топ-10 самых популярных языков программирования (рейтинг в 2018 году составила аналитическая компания RedMonk по данным GitHub и Stack Overflow).',
        'servlet.pdf', 10);
INSERT INTO courses (id, summary, description, materials_path, students_limit)
VALUES (11, 'Ручное тестирование ПО',
        'Ручное тестирование (manual testing) – это один из этапов контроля и оценки качества программного продукта и его свойств. Оно проводится без использования программных средств – путём моделирования возможных действий пользователей. Считается, что именно при ручном тестировании удаётся быстрее получать необходимую информацию о состоянии продукта и качестве разработки.',
        'servlet.pdf', 10);

INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (1, 1, '2021-11-20', '2021-12-25', 9, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (2, 2, '2021-02-10', '2021-03-19', 9, 2);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (3, 3, '2021-01-20', '2021-03-20', 9, 3);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (4, 4, '2021-01-02', '2021-02-20', 10, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (5, 5, '2020-12-25', '2020-12-30', 10, 2);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (6, 6, '2020-11-20', '2020-12-20', 10, 3);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (7, 7, '2020-07-01', '2020-08-10', 10, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (8, 8, '2020-05-01', '2020-06-10', 9, 2);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (9, 9, '2020-03-01', '2020-04-10', 9, 3);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (10, 10, '2020-02-01', '2020-03-10', 10, 1);
INSERT INTO course_runs (id, course_id, start_date, end_date, lecturer_id,
                         status_id)
VALUES (11, 11, '2020-01-01', '2020-02-10', 10, 2);

INSERT INTO course_results (id, mark, comment)
VALUES (1, 10, 'Отличная работа! Все задания выполнял на отлично.');
INSERT INTO course_results (id, mark, comment)
VALUES (2, 2, 'Не очень хороший студент, много пропусков.');
INSERT INTO course_results (id, mark, comment)
VALUES (3, 4, 'Сдал, но с натяжкой.');
INSERT INTO course_results (id, mark, comment)
VALUES (4, 8, 'Хороший студент, усердно работал.');

INSERT INTO user_course_statuses (id, status)
VALUES (1, 'APPLIED');
INSERT INTO user_course_statuses (id, status)
VALUES (2, 'ENTERED');
INSERT INTO user_course_statuses (id, status)
VALUES (3, 'NOT_ENTERED');

INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (1, 1, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (2, 2, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (3, 3, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (4, 4, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (5, 5, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (6, 6, 4, null, 1);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (7, 7, 4, null, 2);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (8, 8, 4, null, 3);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (9, 1, 5, null, 2);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (10, 2, 5, 1, 2);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (11, 3, 5, 2, 2);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (12, 1, 6, 3, 2);
INSERT INTO user_courses (id, user_id, course_id, course_result_id,
                          user_course_status_id)
VALUES (13, 2, 6, 4, 2);
