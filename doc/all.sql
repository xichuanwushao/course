drop table id exists `chapter`;
create table `chapter`(
    `id` char(50) not null comment 'ID',
    `course_id` char(8)  comment '课程ID',
    `name` varchar(50)  comment '名称',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='大章';


drop table if exists `test`;
create table `test` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment '名称',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `test` (id, name) values (1, '测试');

insert into `chapter` (id, course_id, name) values ('2','2','《骆驼祥子》');
insert into `chapter` (id, course_id, name) values ('3','3','《西游记》');
insert into `chapter` (id, course_id, name) values ('4','4','《做最好的自己》');
insert into `chapter` (id, course_id, name) values ('5','5','《曾国藩家书》');
insert into `chapter` (id, course_id, name) values ('6','6','《人性的弱点》');
insert into `chapter` (id, course_id, name) values ('7','7','《钢铁是怎样炼成的》');
insert into `chapter` (id, course_id, name) values ('8','8','《情商：它为什么比智商更重要？》');
insert into `chapter` (id, course_id, name) values ('9','9','《高效能人士的七个习惯》');
insert into `chapter` (id, course_id, name) values ('10','10','《乞丐囡仔》');
insert into `chapter` (id, course_id, name) values ('11','11','《如何在大学里脱颖而出》');
insert into `chapter` (id, course_id, name) values ('12','12','《大学生与现代社会》');
insert into `chapter` (id, course_id, name) values ('13','13','《回归大学之道》');
insert into `chapter` (id, course_id, name) values ('14','14','《假如给我三天光明》');
insert into `chapter` (id, course_id, name) values ('15','15','《谁动了我的奶酪》');
insert into `chapter` (id, course_id, name) values ('16','16','《未来之路》');
insert into `chapter` (id, course_id, name) values ('17','17','《唤醒心中的巨人》');
insert into `chapter` (id, course_id, name) values ('18','18','《倾城之恋》');
insert into `chapter` (id, course_id, name) values ('19','19','《荆棘鸟》');
insert into `chapter` (id, course_id, name) values ('20','20','《挪威的森林》');
insert into `chapter` (id, course_id, name) values ('21','21','《飞鸟集》');
insert into `chapter` (id, course_id, name) values ('22','22','《麦田里的守望者》');
insert into `chapter` (id, course_id, name) values ('23','23','《生命中不能承受之轻》');
insert into `chapter` (id, course_id, name) values ('24','24','《舒婷诗集》');

Drop TABLE IF EXISTS `section`;
CREATE TABLE `section`(
    `id` CHAR(50) NOT NULL DEFAULT '' COMMENT 'ID',
    `title` VARCHAR (50) NOT NULL COMMENT '标题',
    `course_id` CHAR (8) COMMENT '课程|course.id',
    `chapter_id` CHAR(8) COMMENT '大章|chapter.id',
    `video` VARCHAR (200) COMMENT '视频',
    `time` INT COMMENT '时长|单位秒',
    `charge` CHAR (1) COMMENT '收费|C 收费;F 免费',
    `sort` INT COMMENT '顺序',
    `created_at` DATETIME COMMENT '创建时间',
    `updated_at` DATETIME COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小节';

INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('1','钢铁侠','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('2','变形金刚5','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('3','阿凡达3','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('4','大黄蜂','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('5','绿巨人','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('6','血战钢锯岭','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('7','战狼3','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('8','泰囧','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('9','我和我的祖国','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('10','反贪风暴','1','0','','500','F','1',null,null);
INSERT INTO `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('11','流浪地球','1','0','','500','F','1',null,null);

alter table `section` add column (
    `vod` char (32) comment 'vod|阿里云vod'
);

drop table if exists course;
create table course (
    id char(50) not null default '' comment 'id',
    name varchar(50) not null  comment '名称',
    summary varchar(2000)  comment '概述',
    time int default 0  comment '时长|单位秒',
    price decimal(8,2) default 0.00 comment '价格(元)',
    image varchar(100) comment '封面',
    level char(1) not null comment '级别|枚举[CourseLevelEnum]:ONE("1","初级"),TWO("2","中级"),THREE("3","高级")',
    charge char(1)  comment '收费|枚举[CourseChargeEnum]:CHARGE("C","收费"),FREE("F","免费")',
    status char(1)  comment '状态|枚举[CourseStatusEnum]:PUBLISH("P","发布"),DRAFT("D","草稿")',
    enroll integer default 0 comment '报名数',
    sort int comment '顺序',
    created_at datetime(3) comment '创建时间',
    updated_at datetime(3) comment '修改时间',
    primary key (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程';

insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('1','钢铁侠','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('2','变形金刚5','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('3','阿凡达3','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('4','大黄蜂','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('5','绿巨人','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('6','血战钢锯岭','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('7','战狼3','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('8','泰囧','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('9','我和我的祖国','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('10','反贪风暴','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('11','流浪地球','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());
insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at) VALUES ('12','刺客信条','电影获得奥斯卡奖项,年度最佳纸片人',7200,19.8,'',0,'C','D',100,0,now(),now());




# 分类
drop table if exists `category`;
create table `category` (
    `id` char(50) not null default '' comment 'id',
    `parent` char(50)  not null default '' comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';

insert into `category` (id, parent, name, sort) values (100, 000, '前端开发', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'HTML & CSS', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) values (201, 200, '基础应用', 201);
insert into `category` (id, parent, name, sort) values (202, 200, '框架应用', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) values (301, 300, '基础应用', 301);
insert into `category` (id, parent, name, sort) values (302, 300, '进阶方向应用', 302);
insert into `category` (id, parent, name, sort) values (400, 000, '数据库', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'MySQL', 401);
insert into `category` (id, parent, name, sort) values (500, 000, '其它', 500);
insert into `category` (id, parent, name, sort) values (501, 500, '服务器', 501);
insert into `category` (id, parent, name, sort) values (502, 500, '开发工具', 502);
insert into `category` (id, parent, name, sort) values (503, 500, '热门服务端语言', 503);


-- 课程分类
drop table if exists `course_category`;
create table `course_category` (
    `id` char(50) not null default '' comment 'id',
    `course_id` char(50) comment '课程|course.id',
    `category_id` char(50) comment '分类|course.id',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程分类';


-- 课程内容
drop table  if exists `course_content`;
create table `course_content` (
    `id` char(50) not null default '' comment '课程id',
    `content` mediumtext not null  comment '课程内容',
    primary key (`id`)
)  engine=innodb default charset=utf8mb4 comment='课程内容';

-- 讲师
drop table if exists `teacher`;
create table `teacher`(
  `id` char(50) not null default '' comment 'id',
  `name` varchar (50) not null  comment '姓名',
  `nickname` varchar(50)  comment '昵称',
  `image` varchar(100)  comment '头像',
  `position` varchar(50)  comment '职位',
  `motto` varchar(50)  comment '座右铭',
  `intro` varchar(500)  comment '简介',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='讲师';

alter table `course` add column (`teacher_id` char(50) comment '讲师|teacher.id');
-- 文件
drop table if exists `file`;
create table `file` (
    `id` char(50) not null default '' comment 'id',
    `path` varchar(100) not null comment '相对路径',
    `name` varchar(100)  comment '文件名',
    `suffix` varchar(10)  comment '后缀',
    `size` int  comment '大小|字节B',
    `use` char(1)  comment '用途|枚举[FileUseEnum]:COURSE("C","讲师"),TEACHER("T","课程")',
    `create_at` datetime(3)  comment '创建时间',
    `update_at` datetime(3)  comment '修改时间',
    primary key (`id`),
    unique key `path_unique` (`path`)
) engine=innodb default charset=utf8mb4 comment='文件';


alter table `file` add column (`shard_index` int comment '已上传分片');
alter table `file` add column (`shard_size` int comment '分片大小|B');
alter table `file` add column (`shard_total` int comment '分片总数');
alter table `file` add column (`key` varchar(32) comment '文件标识');
alter table `file` add unique key key_unique (`key`);

alter table `file` add column (
    `vod` char (32) comment 'vod|阿里云vod'
);

-- 课程内容文件
drop table if exists `course_content_file` ;
create table `course_content_file` (
    `id` char(50) not null default '' comment 'id',
    `course_id` char(50) not null  comment '课程id',
    `url` varchar (100)  comment '地址',
    `name` varchar(100)  comment '文件名',
    `size` int comment '大小|字节b',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程文件内容';

