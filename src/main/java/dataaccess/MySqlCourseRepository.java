    package dataaccess;

    import domain.Course;
    import domain.CourseType;
    import domain.InvalidValueException;
    import util.Assert;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    public class MySqlCourseRepository implements MyCourseRepository {

        private Connection con;

        public MySqlCourseRepository(Connection con) throws SQLException, ClassNotFoundException {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
        }

        @Override
        public List<Course> findAllCoursesByName(String name) {
            // Implement the logic to find all courses by name
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByCourseType(CourseType courseType) {
            // Implement the logic to find all courses by course type
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByBeginDate(Date beginDate) {
            String sql = "SELECT * FROM `courses`";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Course> courseList = new ArrayList<>();
                while (resultSet.next()) {
                    courseList.add(new Course(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("description"),
                                    resultSet.getInt("hours"),
                                    resultSet.getDate("begin_date"),
                                    resultSet.getDate("end_date"),
                                    CourseType.valueOf(resultSet.getString("course_type"))
                            )
                    );
                }
                return courseList;
            } catch (SQLException e) {
                throw new DatabaseException("Database error occurred");
            } catch (InvalidValueException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<Course> findAllCoursesByEndDate(Date endDate) {
            // Implement the logic to find all courses by end date
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByDescription(String description) {
            // Implement the logic to find all courses by description
            return List.of();
        }

        @Override
        public List<Course> findAllRunningCourses() {
            // Implement the logic to find all running courses
            return List.of();
        }

        @Override
        public Optional<Course> insert(Course entity) {
            // Implement the logic to insert a new course
            return Optional.empty();
        }

        @Override
        public Optional<Course> update(Course entity) {
            // Implement the logic to update an existing course
            return Optional.empty();
        }

        @Override
        public Optional<Course> getById(Long id) {
            Assert.notNull(id);

            if (countCoursesInDbWithId(id) == 0) {
                return Optional.empty();
            } else {
                try {
                    String sql = "SELECT * FROM courses WHERE id = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setLong(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    resultSet.next();
                    Course course = new Course(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("hours"),
                            resultSet.getDate("begin_date"),
                            resultSet.getDate("end_date"),
                            CourseType.valueOf(resultSet.getString("course_type"))
                    );
                    return Optional.of(course);

                } catch (SQLException sqlException) {
                    throw new DatabaseException(sqlException.getMessage());
                } catch (InvalidValueException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private int countCoursesInDbWithId(Long id) {
            String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id` = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(countSql)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSetCount = preparedStatement.executeQuery()) {
                    resultSetCount.next();
                    return resultSetCount.getInt(1);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Database error occurred");
            }
        }

        @Override
        public List<Course> getAll() {
            // Implement the logic to retrieve all courses
            return List.of();
        }

        @Override
        public void deleteById(Long id) {
            // Implement the logic to delete a course by ID
        }
    }