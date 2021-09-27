

update course c set `time` = (select sum(`time`) from `section` where course_id = #{courseId})
where c.id = #{courseId}