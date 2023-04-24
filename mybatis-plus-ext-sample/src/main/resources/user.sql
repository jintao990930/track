DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    birthday date NULL DEFAULT NULL COMMENT '生日',
    PRIMARY KEY (id)
);

INSERT INTO user (id, name, age, email) VALUES
                                            (1, 'Jone', 18, 'test1@baomidou.com', '2005-03-18'),
                                            (2, 'Jack', 20, 'test2@baomidou.com', '2003-07-21'),
                                            (3, 'Tom', 28, 'test3@baomidou.com', '1995-11-09'),
                                            (4, 'Sandy', 21, 'test4@baomidou.com', '2002-02-16'),
                                            (5, 'Billie', 24, 'test5@baomidou.com', '1999-06-20');