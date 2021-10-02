

update course c set `time` = (select sum(`time`) from `section` where course_id = #{courseId})
where c.id = #{courseId}

alter table `course` add column (`teacher_id` char(8) comment `讲师|teacher.id`);
