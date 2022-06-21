create table brands
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null,
	constraint UK_oce3937d2f4mpfqrycbr0l93m
		unique (name)
);

create table ingredients
(
	id bigint auto_increment
		primary key,
	title varchar(255) not null
);

create table products
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null,
	price int null,
	brand_id bigint not null,
	constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv
		foreign key (brand_id) references brands (id)
);

create table products_ingredients
(
	product_id bigint not null,
	ingredients_id bigint not null,
	primary key (product_id, ingredients_id),
	constraint FK3l6bgu3uwq54upl24vh0ttsun
		foreign key (product_id) references products (id),
	constraint FKkwfbafwlsixq5ms24dqnjk0eu
		foreign key (ingredients_id) references ingredients (id)
);

create table role
(
	id bigint auto_increment
		primary key,
	name varchar(20) null
);

create table skin_color
(
	id bigint auto_increment
		primary key,
	name varchar(20) null
);

create table treatment_category
(
	id bigint auto_increment
		primary key,
	name varchar(20) null
);

create table treatments
(
	id bigint auto_increment
		primary key,
	points int not null,
	title varchar(255) not null,
	category_id bigint null,
	constraint FK7baponemnng06v3hepk270h80
		foreign key (category_id) references treatment_category (id)
);

create table users
(
	id bigint auto_increment
		primary key,
	password varchar(120) not null,
	points int null,
	username varchar(20) not null,
	skin_color_id bigint null,
	constraint UKr43af9ap4edm43mmtq01oddj6
		unique (username),
	constraint FKqw8aflidw5wehry35l6smahyb
		foreign key (skin_color_id) references skin_color (id)
);

create table appointments
(
	id bigint auto_increment
		primary key,
	date date not null,
	customer_id bigint null,
	dermatologist_id bigint null,
	treatment_id bigint null,
	constraint FK4epecm6h3kayc2qg4t3675ksu
		foreign key (treatment_id) references treatments (id),
	constraint FK4q5rt20vvnkv7eohwq22l3ayy
		foreign key (customer_id) references users (id),
	constraint FKs1i2drg84cuu230r6cpih7yy6
		foreign key (dermatologist_id) references users (id)
);

create table user_roles
(
	user_id bigint not null,
	role_id bigint not null,
	primary key (user_id, role_id),
	constraint FKhfh9dx7w3ubf1co1vdev94g3f
		foreign key (user_id) references users (id),
	constraint FKrhfovtciq1l558cw6udg0h0d3
		foreign key (role_id) references role (id)
);

create table users_treatments
(
	user_id bigint not null,
	treatments_id bigint not null,
	primary key (user_id, treatments_id),
	constraint FK30y9u5kcb8eggfglpd64b8bke
		foreign key (user_id) references users (id),
	constraint FK4jm3tpllu12fljrj2ujdb6tn2
		foreign key (treatments_id) references treatments (id)
);

