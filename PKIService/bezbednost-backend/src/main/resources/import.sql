insert into privileges(name) values ('GET_USER');
insert into privileges(name) values ('UPDATE_USER');
insert into privileges(name) values ('REGISTER_USER');
insert into privileges(name) values ('LOGIN');
insert into privileges(name) values ('SEND_LOGIN_LINK');
insert into privileges(name) values ('LOGIN_WITH_LINK');
insert into privileges(name) values ('UPDATE_ENGINEER_PROJECT_WORK');
insert into privileges(name) values ('UPLOAD_CV');
insert into privileges(name) values ('GET_PROJECTS_ENGINEER');
insert into privileges(name) values ('GET_PROJECT_WORK_ENGINEER');
insert into privileges(name) values ('GET_EMPLOYEE');
insert into privileges(name) values ('UPDATE_PROJECT');
insert into privileges(name) values ('GET_PROJECTS_MANAGER');
insert into privileges(name) values ('GET_PROJECT');
insert into privileges(name) values ('GET_EMPLOYEES_PROJECT');
insert into privileges(name) values ('GET_MANAGERS_PROJECT');
insert into privileges(name) values ('GET_EMPLOYEES');
insert into privileges(name) values ('CREATE_EMPLOYEE');
insert into privileges(name) values ('GET_PROJECTS');
insert into privileges(name) values ('UPDATE_PROJECT');
insert into privileges(name) values ('APPROVE_REGISTRATION');
insert into privileges(name) values ('CANCEL_REGISTRATION');
insert into privileges(name) values ('DELETE_USER');
insert into privileges(name) values ('ACTIVATE_ACCOUNT');
insert into privileges(name) values ('ADD_EMPLOYEE_PROJECT');
insert into privileges(name) values ('ADD_PERMISSION');
insert into privileges(name) values ('DELETE_PERMISSION');
insert into privileges(name) values ('ADD_PERMISSION_ROLE');
insert into privileges(name) values ('GET_SKILLS_EMPLOYEE');
insert into privileges(name) values ('GET_PROJECTS_EMPLOYEE');
insert into privileges(name) values ('GET_USER');

insert into roles(name) values ('ADMIN');
insert into roles(name) values ('ENGINEER');
insert into roles(name) values ('PROJECT_MANAGER');
insert into roles(name) values ('HR_MANAGER');

insert into roles_privileges(role_id, privilege_id) values (1,1);
insert into roles_privileges(role_id, privilege_id) values (1,2);
insert into roles_privileges(role_id, privilege_id) values (1,3);
insert into roles_privileges(role_id, privilege_id) values (1,4);
insert into roles_privileges(role_id, privilege_id) values (1,5);
insert into roles_privileges(role_id, privilege_id) values (1,6);
insert into roles_privileges(role_id, privilege_id) values (1,7);
insert into roles_privileges(role_id, privilege_id) values (1,8);
insert into roles_privileges(role_id, privilege_id) values (1,9);
insert into roles_privileges(role_id, privilege_id) values (1,10);
insert into roles_privileges(role_id, privilege_id) values (1,11);
insert into roles_privileges(role_id, privilege_id) values (1,12);
insert into roles_privileges(role_id, privilege_id) values (1,13);
insert into roles_privileges(role_id, privilege_id) values (1,14);
insert into roles_privileges(role_id, privilege_id) values (1,15);
insert into roles_privileges(role_id, privilege_id) values (1,16);
insert into roles_privileges(role_id, privilege_id) values (1,17);
insert into roles_privileges(role_id, privilege_id) values (1,18);
insert into roles_privileges(role_id, privilege_id) values (1,19);
insert into roles_privileges(role_id, privilege_id) values (1,20);
insert into roles_privileges(role_id, privilege_id) values (1,21);
insert into roles_privileges(role_id, privilege_id) values (1,22);
insert into roles_privileges(role_id, privilege_id) values (1,23);
insert into roles_privileges(role_id, privilege_id) values (1,24);
insert into roles_privileges(role_id, privilege_id) values (1,25);
insert into roles_privileges(role_id, privilege_id) values (1,26);
insert into roles_privileges(role_id, privilege_id) values (1,27);
insert into roles_privileges(role_id, privilege_id) values (1,28);
insert into roles_privileges(role_id, privilege_id) values (1,29);
insert into roles_privileges(role_id, privilege_id) values (1,30);
insert into roles_privileges(role_id, privilege_id) values (1,31);


insert into roles_privileges(role_id, privilege_id) values (2,1);
insert into roles_privileges(role_id, privilege_id) values (2,2);
insert into roles_privileges(role_id, privilege_id) values (2,3);
insert into roles_privileges(role_id, privilege_id) values (2,4);
insert into roles_privileges(role_id, privilege_id) values (2,5);
insert into roles_privileges(role_id, privilege_id) values (2,6);
insert into roles_privileges(role_id, privilege_id) values (2,7);
insert into roles_privileges(role_id, privilege_id) values (2,8);
insert into roles_privileges(role_id, privilege_id) values (2,9);
insert into roles_privileges(role_id, privilege_id) values (2,10);
insert into roles_privileges(role_id, privilege_id) values (2,11);


insert into roles_privileges(role_id, privilege_id) values (3,1);
insert into roles_privileges(role_id, privilege_id) values (3,2);
insert into roles_privileges(role_id, privilege_id) values (3,3);
insert into roles_privileges(role_id, privilege_id) values (3,4);
insert into roles_privileges(role_id, privilege_id) values (3,5);
insert into roles_privileges(role_id, privilege_id) values (3,6);
insert into roles_privileges(role_id, privilege_id) values (3,12);
insert into roles_privileges(role_id, privilege_id) values (3,13);
insert into roles_privileges(role_id, privilege_id) values (3,14);
insert into roles_privileges(role_id, privilege_id) values (3,15);

insert into roles_privileges(role_id, privilege_id) values (4,1);
insert into roles_privileges(role_id, privilege_id) values (4,2);
insert into roles_privileges(role_id, privilege_id) values (4,3);
insert into roles_privileges(role_id, privilege_id) values (4,4);
insert into roles_privileges(role_id, privilege_id) values (4,5);
insert into roles_privileges(role_id, privilege_id) values (4,6);
insert into roles_privileges(role_id, privilege_id) values (4,7);
insert into roles_privileges(role_id, privilege_id) values (4,16);
insert into roles_privileges(role_id, privilege_id) values (4,17);









insert into addresses (country, city, street_address) values ('Srbija', 'Novi Sad', 'Turgenjeva 1');
insert into users(is_active, name, password, phone_number, role, surname, username, addressid) values (false, 'Anja', '$2a$10$s7s88elNF/TkJ9D5OyOAUejgZ/t/NsHF4/QP4JFAzzLOLjpDBLGpS', 0691442001,'ADMIN', 'Dmitrovic', 'tibbers',1);