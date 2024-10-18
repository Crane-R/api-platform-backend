create table `api-platform`.interface_info
(
    ii_id              bigint auto_increment comment '表id'
        primary key,
    ii_description     varchar(256)                       null comment '接口描述',
    ii_url             varchar(256)                       not null comment '接口地址',
    ii_method          int      default 0                 not null comment '请求方式，0post，1get',
    ii_request_header  text                               not null comment '请求头',
    ii_response_header text                               not null comment '响应头',
    ii_request_params  text                               null comment '请求参数',
    status             int      default 0                 not null comment '接口状态，0关闭，1开启',
    is_delete          int      default 0                 not null,
    create_time        datetime default CURRENT_TIMESTAMP not null
)
    comment '接口信息表';

create table `api-platform`.user
(
    u_id        bigint auto_increment
        primary key,
    username    varchar(50)                        not null comment '用户名，有唯一索引',
    nickname    varchar(50)                        null comment '昵称',
    avatar_url  varchar(512)                       null comment '用户头像',
    gender      int      default 0                 not null comment '性别，0男1女',
    u_password  varchar(256)                       not null comment '用户密码',
    user_status int      default 0                 not null comment '用户状态，0正常',
    user_role   int      default 0                 not null comment '用户角色，0用户，1管理员',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    is_delete   int      default 0                 not null,
    access_key  varchar(256)                       null comment '接口访问密钥',
    secret_key  varchar(256)                       null,
    constraint user_username_uindex
        unique (username)
)
    comment '用户表';

