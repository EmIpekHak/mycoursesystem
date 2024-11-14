package dataaccess;

import domain.Course;
import domain.CourseType;

import java.sql.Date;
import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course, Long>{
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByCourseType(CourseType courseType);
    List<Course> findAllCoursesByBeginDate(Date beginDate);
    List<Course> findAllCoursesByEndDate(Date endDate);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllRunningCourses();

}
