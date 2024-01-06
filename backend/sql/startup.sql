-- Senha: teste
INSERT INTO users (username, password, role)
VALUES ('student', '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'STUDENT');

-- Senha: teste
INSERT INTO users (username, password, role)
VALUES ('teacher', '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'TEACHER');

-- Senha: teste
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'ADMIN');

INSERT INTO public.rooms(
	building_number, capacity, room_number, type)
	VALUES ('503', 31, '233', 'AUDITORIUM');

INSERT INTO public.reservations (
    date, end_time, event_description, event_name, start_time, status, user_id, room_id
) VALUES (
    '2024-01-05', '12:00:00', 'Sample event description', 'Sample event name', '10:00:00', 'ACTIVE', 1, 1
);