delete from conference_manager_app.public.lectures_has_users;
delete from conference_manager_app.public.lectures;
insert into conference_manager_app.public.lectures
    (conference_path, lecture_date, lecture_name)
     values
            (0, timestamp '2019-06-01 10:00', 'Spring podstawy'),
            (0, timestamp '2019-06-01 12:00', 'Spring MVC'),
            (0, timestamp '2019-06-02 10:00', 'Spring boot'),
            (0, timestamp '2019-06-02 12:00', 'Spring podstawy'),
            (1, timestamp '2019-06-01 10:00', 'Testy jednostkowe'),
            (1, timestamp '2019-06-01 12:00', 'Testy wydajnościowe'),
            (1, timestamp '2019-06-02 10:00', 'Testy akceptacyjne'),
            (1, timestamp '2019-06-02 12:00', 'Testy integracyjne'),
            (2, timestamp '2019-06-01 10:00', 'Android podstawy'),
            (2, timestamp '2019-06-01 12:00', 'Android zaawansowane widoki'),
            (2, timestamp '2019-06-02 10:00', 'Android praca sieciowa'),
            (2, timestamp '2019-06-02 12:00', 'Kotlin');