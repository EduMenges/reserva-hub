/* USUÁRIOS */

-- Senha: teste
INSERT INTO users (
	username,
	password, 
	role
)
VALUES (
	'student', 
	'$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu',
	 'STUDENT'
);

-- Senha: teste
INSERT INTO users (
	username,
	password,
	role
)
VALUES (
	'teacher',
	'$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu',
	 'TEACHER'
);

-- Senha: teste
INSERT INTO users (
	username,
	password,
	role
)
VALUES (
	'admin',
	'$2a$10$cRZ6dFxZTWOlgK68jm/HaOHz726q9L5Il9tS3/fkHxK.bzcROPOfu',
	'ADMIN'
);


/* SALAS */

INSERT INTO public.rooms (
	building_number,
	capacity,
	room_number,
	type
)
VALUES (
	'43412',
	50,
	'0',
	'AUDITORIUM'
);
	
INSERT INTO public.rooms (
	building_number,
	capacity,
	room_number,
	type
)
VALUES (
	'43425',
	50,
	'112',
	'CLASSROOM'
);
	
INSERT INTO public.rooms (
	building_number,
	capacity,
	room_number,
	type
)
VALUES (
	'43413',
	40,
	'104',
	'LAB'
);
	
INSERT INTO public.rooms (
	building_number, 
	capacity, 
	room_number,
	type
)
VALUES (
	'43425',
	40,
	'202',
	'MEETING_ROOM'
);

/* RESERVAS */

INSERT INTO public.reservations (
    date,
	end_time,
	event_description,
	event_name,
	start_time,
	status,
	user_id,
	room_id
) 
VALUES (
    '2024-02-08',
	'13:00:00',
	NULL,
	'Sessão de estudos',
	'12:00:00',
	'ACTIVE',
	1,
	4
);

INSERT INTO public.reservations (
    date,
	end_time,
	event_description,
	event_name,
	start_time,
	status,
	user_id,
	room_id
) 
VALUES (
    '2023-09-02',
	'19:00:00',
	'Primeira fase da Maratona SBC de Programação',
	'Maratona de programação',
	'14:00:00',
	'EXPIRED',
	1,
	1
);

INSERT INTO public.reservations (
    date,
	end_time,
	event_description,
	event_name,
	start_time,
	status,
	user_id,
	room_id
) 
VALUES (
    '2023-08-19',
	'13:15:00',
	'Palestra de incentivo aos alunos participarem da Maratona de Programação',
	'Palestra',
	'13:00:00',
	'CANCELED',
	1,
	3
);


/* PEDIDOS DE RESERVA */

INSERT INTO public.reservation_requests (
    date,
	end_time,
	event_description,
	event_name,
	start_time,
	status,
	user_id,
	room_id
) 
VALUES (
    '2024-02-15',
	'13:00:00',
	NULL,
	'Sessão de estudos',
	'12:00:00',
	'AWAITING_APPROVAL',
	1,
	4
);


/* PEDIDOS DE EDIÇÃO */

INSERT INTO public.edition_requests (
    date,
	end_time,
	event_description,
	event_name,
	start_time,
	status,
	reservation_id,
	room_id
) 
VALUES (
    '2024-02-09',
	'13:00:00',
	NULL,
	'Sessão de estudos',
	'12:00:00',
	'AWAITING_APPROVAL',
	1,
	4
);