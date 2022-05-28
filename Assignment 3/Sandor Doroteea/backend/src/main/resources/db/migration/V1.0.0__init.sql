create table batch_job_execution_seq
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table batch_job_instance
(
    JOB_INSTANCE_ID bigint       not null
        primary key,
    VERSION         bigint       null,
    JOB_NAME        varchar(100) not null,
    JOB_KEY         varchar(32)  not null,
    constraint JOB_INST_UN
        unique (JOB_NAME, JOB_KEY)
);

create table batch_job_execution
(
    JOB_EXECUTION_ID           bigint        not null
        primary key,
    VERSION                    bigint        null,
    JOB_INSTANCE_ID            bigint        not null,
    CREATE_TIME                datetime(6)   not null,
    START_TIME                 datetime(6)   null,
    END_TIME                   datetime(6)   null,
    STATUS                     varchar(10)   null,
    EXIT_CODE                  varchar(2500) null,
    EXIT_MESSAGE               varchar(2500) null,
    LAST_UPDATED               datetime(6)   null,
    JOB_CONFIGURATION_LOCATION varchar(2500) null,
    constraint JOB_INST_EXEC_FK
        foreign key (JOB_INSTANCE_ID) references batch_job_instance (JOB_INSTANCE_ID)
);

create table batch_job_execution_context
(
    JOB_EXECUTION_ID   bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint JOB_EXEC_CTX_FK
        foreign key (JOB_EXECUTION_ID) references batch_job_execution (JOB_EXECUTION_ID)
);

create table batch_job_execution_params
(
    JOB_EXECUTION_ID bigint       not null,
    TYPE_CD          varchar(6)   not null,
    KEY_NAME         varchar(100) not null,
    STRING_VAL       varchar(250) null,
    DATE_VAL         datetime(6)  null,
    LONG_VAL         bigint       null,
    DOUBLE_VAL       double       null,
    IDENTIFYING      char         not null,
    constraint JOB_EXEC_PARAMS_FK
        foreign key (JOB_EXECUTION_ID) references batch_job_execution (JOB_EXECUTION_ID)
);

create table batch_job_seq
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table batch_step_execution
(
    STEP_EXECUTION_ID  bigint        not null
        primary key,
    VERSION            bigint        not null,
    STEP_NAME          varchar(100)  not null,
    JOB_EXECUTION_ID   bigint        not null,
    START_TIME         datetime(6)   not null,
    END_TIME           datetime(6)   null,
    STATUS             varchar(10)   null,
    COMMIT_COUNT       bigint        null,
    READ_COUNT         bigint        null,
    FILTER_COUNT       bigint        null,
    WRITE_COUNT        bigint        null,
    READ_SKIP_COUNT    bigint        null,
    WRITE_SKIP_COUNT   bigint        null,
    PROCESS_SKIP_COUNT bigint        null,
    ROLLBACK_COUNT     bigint        null,
    EXIT_CODE          varchar(2500) null,
    EXIT_MESSAGE       varchar(2500) null,
    LAST_UPDATED       datetime(6)   null,
    constraint JOB_EXEC_STEP_FK
        foreign key (JOB_EXECUTION_ID) references batch_job_execution (JOB_EXECUTION_ID)
);

create table batch_step_execution_context
(
    STEP_EXECUTION_ID  bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint STEP_EXEC_CTX_FK
        foreign key (STEP_EXECUTION_ID) references batch_step_execution (STEP_EXECUTION_ID)
);

create table batch_step_execution_seq
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table payment
(
    id       bigint auto_increment
        primary key,
    name     varchar(120) not null,
    track_id bigint       not null,
    user_id  bigint       not null
);

create table playlist
(
    id       bigint auto_increment
        primary key,
    duration int          not null,
    title    varchar(512) not null,
    constraint UK5x3qfexombuksyoe81cxxsl61
        unique (title)
);

create table role
(
    id   int auto_increment
        primary key,
    name varchar(20) null
);

create table track
(
    id              bigint auto_increment
        primary key,
    album           varchar(512) not null,
    artist          varchar(512) not null,
    duration        int          not null,
    explicit_lyrics bit          not null,
    link            varchar(512) not null,
    preview         varchar(512) not null,
    title           varchar(512) not null
);

create table playlist_tracks
(
    playlist_id bigint not null,
    track_id    bigint not null,
    constraint FKdmojw87kwq7bpf4h9fas2tkyj
        foreign key (playlist_id) references playlist (id),
    constraint FKgnlcepi5qffyln4jq4gyjd5f9
        foreign key (track_id) references track (id)
);

create table user
(
    id       bigint auto_increment
        primary key,
    email    varchar(50)  not null,
    password varchar(120) not null,
    username varchar(20)  not null,
    constraint UKob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint UKsb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

create table user_payments
(
    users_id   bigint not null,
    payment_id bigint not null,
    primary key (users_id, payment_id),
    constraint UK_7tigi2jg3gl4g50w54no6pg7x
        unique (payment_id),
    constraint FKaxpbv8pmjfml852vbo1ug3kei
        foreign key (payment_id) references payment (id),
    constraint FKjaep6rikk0boboki1rp344aym
        foreign key (users_id) references user (id)
);

create table user_playlist
(
    user_id     bigint not null,
    playlist_id bigint not null,
    constraint UK_pele1rbg0m5nf54ryv7du7o2k
        unique (playlist_id),
    constraint FK7k6xercebs1wg014xb49hw3h0
        foreign key (playlist_id) references playlist (id),
    constraint FKmofufj03yw1wqauec6vrdnfw1
        foreign key (user_id) references user (id)
);

create table user_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references user (id)
            on delete cascade,
    constraint FKrhfovtciq1l558cw6udg0h0d3
        foreign key (role_id) references role (id)
);

create table user_tracks
(
    users_id bigint not null,
    track_id bigint not null,
    primary key (users_id, track_id),
    constraint FKdn6dqj5bbn3i3uutsw1sf4nuf
        foreign key (users_id) references user (id),
    constraint FKov50xtb8m5m5v31qvivlrm1ob
        foreign key (track_id) references track (id)
);

