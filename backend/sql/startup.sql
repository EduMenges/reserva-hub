/* USUÁRIOS */

-- Senha: teste
INSERT INTO users (id, username, password, role)
VALUES 
    (1, 'student', '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'STUDENT'),
    (2, 'teacher', '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'TEACHER'),
    (3, 'admin',   '$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu', 'ADMIN');

/* SALAS */

INSERT INTO public.rooms (id, building_number, capacity, room_number, type)
VALUES 
    (1, '43412', 50, '0',   'AUDITORIUM'),
    (2, '43425', 50, '112', 'CLASSROOM'),
    (3, '43413', 40, '104', 'LAB'),
    (4, '43425', 40, '202', 'MEETING_ROOM');
	
/* RESOURCES */

INSERT INTO public.room_resources (room_id, resource) 
VALUES 
    (1, 'AC'),
    (1, 'PROJECTOR'),
    (1, 'PC'),
    (2, 'AC'),
    (2, 'PROJECTOR'),
    (3, 'PC'),
    (4, 'AC');

/* RESERVATIONS */

INSERT INTO public.reservations
    (id, date, end_time, event_description, event_name, start_time, status, room_id, user_id)
VALUES
    (1, '2024-02-01', '15:00', 'Description 1', 'Event 1', '13:00', 'ACTIVE', 1, 1),
    (2, '2024-02-02', '16:00', 'Description 2', 'Event 2', '14:00', 'ACTIVE', 2, 2),
    (3, '2024-02-03', '17:00', 'Description 3', 'Event 3', '15:00', 'CANCELED', 3, 1),
    (4, '2024-02-04', '18:00', 'Description 4', 'Event 4', '16:00', 'AWAITING_APPROVAL', 4, 2),
    (5, '2024-02-05', '19:00', 'Description 5', 'Event 5', '17:00', 'DENIED', 1, 1),
    (6, '2024-02-06', '20:00', 'Description 6', 'Event 6', '18:00', 'EXPIRED', 2, 2),
    (7, '2024-02-07', '21:00', 'Description 7', 'Event 7', '19:00', 'ACTIVE', 3, 1),
    (8, '2024-02-08', '22:00', 'Description 8', 'Event 8', '20:00', 'ACTIVE', 4, 2),
    (9, '2024-02-09', '23:00', 'Description 9', 'Event 9', '21:00', 'CANCELED', 1, 1),
    (10, '2024-02-10', '24:00', 'Description 10', 'Event 10', '22:00', 'AWAITING_APPROVAL', 2, 2);

/* EDITIONS */
INSERT INTO public.edition_requests
    (id, date, end_time, event_description, event_name, start_time, status, reservation_id, room_id, user_id)
VALUES
    (1, '2024-03-01', '15:00', 'Edit Description 1', 'Edit Event 1', '13:00', 'APPROVED', 1, 1, 1),
    (2, '2024-03-02', '16:00', 'Edit Description 2', 'Edit Event 2', '14:00', 'DENIED', 2, 2, 2),
    (3, '2024-03-03', '17:00', 'Edit Description 3', 'Edit Event 3', '15:00', 'AWAITING_APPROVAL', 3, 3, 1),
    (4, '2024-03-04', '18:00', 'Edit Description 4', 'Edit Event 4', '16:00', 'CANCELED', 4, 4, 2),
    (5, '2024-03-05', '19:00', 'Edit Description 5', 'Edit Event 5', '17:00', 'APPROVED', 5, 1, 1),
    (6, '2024-03-06', '20:00', 'Edit Description 6', 'Edit Event 6', '18:00', 'DENIED', 6, 2, 2),
    (7, '2024-03-07', '21:00', 'Edit Description 7', 'Edit Event 7', '19:00', 'AWAITING_APPROVAL', 7, 3, 1),
    (8, '2024-03-08', '22:00', 'Edit Description 8', 'Edit Event 8', '20:00', 'CANCELED', 8, 4, 2),
    (9, '2024-03-09', '23:00', 'Edit Description 9', 'Edit Event 9', '21:00', 'APPROVED', 9, 1, 1),
    (10, '2024-03-10', '24:00', 'Edit Description 10', 'Edit Event 10', '22:00', 'DENIED', 10, 2, 2);