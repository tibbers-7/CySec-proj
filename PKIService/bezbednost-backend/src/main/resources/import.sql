insert into addresses (country, city, street_address) values ('Srbija', 'Novi Sad', 'Turgenjeva 1');
insert into addresses (country, city, street_address) values ('Srbija', 'Novi Sad', 'Futoska 62');
insert into addresses (country, city, street_address) values ('Srbija', 'Beograd', 'Petra Drapsina 3');
insert into addresses (country, city, street_address) values ('Srbija', 'Beograd', 'Knjeginje Ljubice 12');
insert into addresses (country, city, street_address) values ('Srbija', 'Beograd', 'Kralja Petra 2');
insert into addresses (country, city, street_address) values ('Srbija', 'Beograd', 'Mihajla Pupina 112');
insert into addresses (country, city, street_address) values ('Srbija', 'Nis', 'Novosadska 33');

insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Novi Sad", "country": "Srbija", "streetAddress": "Futoska 62"}', false, true, 'Elena', 'Milanovic', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS','0623403843','2023-05-20 17:03:15.433759', '2023-05-22 12:00:10.433759', 'ROLE_HR_MANAGER', 'enaa@gmail.com', 'Psychologist');
insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Beograd", "country": "Srbija", "streetAddress": "Petra Drapsina 3"}', false, true, 'Milan', 'Preradovic', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0645459035','2023-05-10 07:00:00.421759', '2023-05-22 12:01:10.433759', 'ROLE_PROJECT_MANAGER', 'mili@gmail.com', 'Java developer');
insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Beograd", "country": "Srbija", "streetAddress": "Knjeginje Ljubice 12"}', false, true,'Nemanja', 'Djordjevic', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0613985239','2023-05-17 14:20:00.423292', '2023-05-18 18:34:14.433733', 'ROLE_ENGINEER', 'ddgss@gmail.com', '.NET developer');
insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Beograd", "country": "Srbija", "streetAddress": "Kralja Petra 2"}', false, true, 'Ivana', 'Knezevic', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0690358205', '2023-05-22 15:54:10.234292', '2023-05-18 18:36:14.411732', 'ROLE_ENGINEER', 'maliknez@gmail.com', 'JS developer');
insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Beograd", "country": "Srbija", "streetAddress": "Mihajla Pupina 112"}', false, true, 'Milos', 'Ciric', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0603523525', '2023-05-23 17:11:12.314242', '2023-05-24 12:12:12.321342', 'ROLE_HR_MANAGER', 'tibbbii@gmail.com', 'Sociologist');
insert into registration_requests (address, is_cancelled, is_resolved, name, surname, password, phone_number, request_created, request_updated, role, username, work_title) values ('{"id": null, "city": "Nis", "country": "Srbija", "streetAddress": "Novosadska 33"}', false, true, 'Isidora', 'Zelenovic', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0662202029', '2023-05-19 16:43:08.111232', '2023-05-19 17:02:14.342732', 'ROLE_PROJECT_MANAGER', 'nestonesto@gmail.com', 'JS developer');

insert into roles(id, name) values (1, 'ROLE_ADMIN')
insert into roles(id, name) values (2, 'ROLE_PROJECT_MANAGER')
insert into roles(id, name) values (3, 'ROLE_HR_MANAGER')
insert into roles(id, name) values (4, 'ROLE_ENGINEER')

insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (true, 'Anja', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0691442001', 'Dmitrovic', 'tibbers@gmail.com',1, 'Full stack developer');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (false, 'Elena', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0623403843', 'Milanovic', 'mili@gmail.com',2, 'Psychologist');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (false, 'Milan', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0645459035', 'Preradovic', 'enaa@gmail.com',3, 'Java developer');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (false, 'Nemanja', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0613985239', 'Djordjevic', 'ddgss@gmail.com',4, '.NET developer');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (true, 'Ivana', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0690358205', 'Knezevic', 'maliknez@gmail.com',5, 'JS developer');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (false, 'Milos', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0603523525', 'Ciric', 'tibbbii@gmail.com',6, 'Sociologist');
insert into users (is_active, name, password, phone_number, surname, username, address_id, work_title) values (true, 'Isidora', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', '0662202029', 'Zelenovic', 'nestonesto@gmail.com',7, 'JS developer');

insert into users_roles (user_id, role_id) values(1,1)
insert into users_roles (user_id, role_id) values(2,3)
insert into users_roles (user_id, role_id) values(3,2)
insert into users_roles (user_id, role_id) values(4,4)
insert into users_roles (user_id, role_id) values(5,4)
insert into users_roles (user_id, role_id) values(6,3)
insert into users_roles (user_id, role_id) values(7,2)

insert into privileges(id, name) values (1, 'GET_ADDRESSES')
insert into privileges(id, name) values (2, 'GET_ADDRESS_BY_ID')
insert into privileges(id, name) values (3, 'UPDATE_ADDRESS')
insert into privileges(id, name) values (4, 'CREATE_ADDRESS')
insert into privileges(id, name) values (5, 'DELETE_ADDRESS')
insert into privileges(id, name) values (6, 'LOG_OUT')
insert into privileges(id, name) values (7, 'GET_ALL_CVS')
insert into privileges(id, name) values (8, 'UPLOAD_CV')
insert into privileges(id, name) values (9, 'DOWNLOAD_CV')
insert into privileges(id, name) values (10, 'GET_ALL_PROJECTS')
insert into privileges(id, name) values (11, 'GET_PROJECT_BY_ID')
insert into privileges(id, name) values (12, 'GET_PROJECT_BY_MANAGER_ID')
insert into privileges(id, name) values (13, 'CREATE_PROJECT')
insert into privileges(id, name) values (14, 'UPDATE_PROJECT')
insert into privileges(id, name) values (15, 'DELETE_PROJECT')
insert into privileges(id, name) values (16, 'GET_ALL_PROJECT_WORK_HISTORIES')
insert into privileges(id, name) values (17, 'GET_PROJECT_WORK_HISTORY_BY_ID')
insert into privileges(id, name) values (18, 'GET_ALL_ENGINEERS_ON_PROJECT')
insert into privileges(id, name) values (19, 'GET_ALL_PROJECTS_FOR_ENGINEER')
insert into privileges(id, name) values (20, 'CREATE_WORK_ON_PROJECT_FOR_ENGINEER')
insert into privileges(id, name) values (21, 'UPDATE_WORK_ON_PROJECT_FOR_ENGINNER')
insert into privileges(id, name) values (22, 'DELETE_WORK_ON_PROJECT_FOR_ENGINEER')
insert into privileges(id, name) values (23, 'GET_ALL_SKILLS')
insert into privileges(id, name) values (24, 'GET_SKILL_BY_ID')
insert into privileges(id, name) values (25, 'GET_ALL_SKILLS_FOR_ENGINEER')
insert into privileges(id, name) values (26, 'CREATE_NEW_SKILL_FOR_ENGINEER')
insert into privileges(id, name) values (27, 'UPDATE_SKILL_FOR_ENGINEER')
insert into privileges(id, name) values (28, 'DELETE_SKILL')
insert into privileges(id, name) values (29, 'UPDATE_SKILL_FOR_ENGINEER')
insert into privileges(id, name) values (30, 'GET_USER_BY_ID')
insert into privileges(id, name) values (31, 'CANCEL_REGISTRATION_REQUEST')
insert into privileges(id, name) values (32, 'GET_EMPLOYEE_BY_ID')
insert into privileges(id, name) values (33, 'GET_ALL_EMPLOYEES')
insert into privileges(id, name) values (34, 'GET_EMPLOYEE_BY_USERNAME')
insert into privileges(id, name) values (35, 'GET_ALL_ENGINEERS')
insert into privileges(id, name) values (36, 'GET_ALL_PROJECT_MANAGERS')
insert into privileges(id, name) values (37, 'CREATE_EMPLOYEE')
insert into privileges(id, name) values (38, 'UPDATE_EMPLOYEE')
insert into privileges(id, name) values (39, 'DELETE_USER')
insert into privileges(id, name) values (40, 'APPROVE_REGISTRATION_REQUEST')
insert into privileges(id, name) values (41, 'GET_ALL_REGISTRATION_REQUESTS')


insert into roles_privileges(role_id, privilege_id) values (1, 41)
insert into roles_privileges(role_id, privilege_id) values (1, 40)
insert into roles_privileges(role_id, privilege_id) values (1, 31)