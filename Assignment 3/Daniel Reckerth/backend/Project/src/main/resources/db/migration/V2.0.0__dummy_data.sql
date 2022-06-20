insert into public.role(id, name) values (1, 'ROLE_ADMIN');
insert into public.role(id, name) values (2, 'ROLE_EMPLOYEE');
insert into public.role(id, name) values (3, 'ROLE_CUSTOMER');

insert into public.app_user(id, email, password, username)
VALUES (-1, 'admin@email.com', '$2a$10$NvYrTVTj/yQQmAMBZj.UI..XTHTpywSDVTnEueo1s0Zftw1VvTRRa', 'admin');

insert into public.app_user(id, email, password, username)
VALUES (-2, 'employee@email.com', '$2a$10$WJUxp1YaCfAQEMJkhiQrPOoQa9UEsoM038f6B18Rui3QzZMoSAa.u', 'employee');

insert into public.app_user(id, email, password, username)
VALUES (-3, 'customer@email.com', '$2a$10$PrhI0g2/wia7qlZMLPKCe.xFZ.pnQ45QN5cFQxy468W9jxT/zcBy2', 'customer');

INSERT INTO public.user_roles(user_id, role_id) VALUES (-1, 1);
INSERT INTO public.user_roles(user_id, role_id) VALUES (-2, 2);
INSERT INTO public.user_roles(user_id, role_id) VALUES (-3, 3);

INSERT INTO public.customer(address, birth_date, full_name, phone_number, id)
VALUES ('Address Customer 1', '1999-07-04', 'Customer Number 1', '+40723526325', -3);

INSERT INTO public.device(id, brand, name, fk_user)
VALUES (-1, 'Samsung', 'Galaxy S21', -3);

INSERT INTO public.device(id, brand, name, fk_user)
VALUES (-2, 'Samsung', 'Galaxy Earbuds', -3);

INSERT INTO public.ticket(id, created_at, description, status, fk_device)
VALUES (-1, '2022-06-14 16:00', 'Left earpice does not work', 'NEW', -2);

