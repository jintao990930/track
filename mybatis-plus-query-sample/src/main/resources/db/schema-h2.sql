CREATE TABLE IF NOT EXISTS `t_user`
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(30) COMMENT '姓名',
    age INT COMMENT '年龄',
    email VARCHAR(50) COMMENT '邮箱',
    birthday DATE COMMENT '生日'
);