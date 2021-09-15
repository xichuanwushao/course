drop table id exists `chapter`;
create table `chapter`(
    `id` char(8) not null comment 'ID',
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