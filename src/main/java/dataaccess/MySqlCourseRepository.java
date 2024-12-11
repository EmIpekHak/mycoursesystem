    package dataaccess;

    import domain.Course;
    import domain.CourseType;
    import util.Assert;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Optional;

    public class MySqlCourseRepository implements MyCourseRepository{

        private Connection con;

        public MySqlCourseRepository() throws SQLException, ClassNotFoundException {
            this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem","root","");

        }

        @Override
        public List<Course> findALllCourseByNyme(String name) {
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByDescription(String description) {
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByNameOrDescription(String searchText) {
            try{
                String sql = "SELECT * FROM `courses` WHERE LOWER(`description`) LIKE LOWER(?) OR LOWER(`name`) LIKE LOWER(?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, "%"+searchText+"%");
                preparedStatement.setString(2, "%"+searchText+"%");
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Course> courseList = new ArrayList<>();
                while(resultSet.next()){
                    courseList.add(new Course(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            CourseType.valueOf(resultSet.getString("coursetype")),
                            resultSet.getString("description"),
                            resultSet.getInt("hours"),
                            resultSet.getDate("begindate"),
                            resultSet.getDate("enddate")
                    ));
                }
                return courseList;
            }catch (SQLException sqlException){
                throw new DatabaseException(sqlException.getMessage());
            }
            //return List.of();
        }

        @Override
        public List<Course> findAllCoursesByCourseType(CourseType courseType) {
            return List.of();
        }

        @Override
        public List<Course> findAllCoursesByStartDate(Date startDate) {
            return List.of();
        }

        @Override
        public List<Course> findAllRunningCourses() {
            return List.of();
        }

        @Override
        public Optional<Course> insert(Course entity) {
            Assert.notNull(entity);

            try{
                String sql = "INSERT INTO `courses` (`name`, `description`, `hours`, `beginDate`, `endDate`, `coursetype`) VALUES (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3, entity.getHours());
                preparedStatement.setDate(4, entity.getBeginDate());
                preparedStatement.setDate(5, entity.getEndDate());
                preparedStatement.setString(6, entity.getCourseType().toString());

                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows==0){
                    return Optional.empty();

                }

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if(generatedKeys.next()){
                    return this.getByID(generatedKeys.getLong(1));
                }
                else {
                    return Optional.empty();
                }
            }

            catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }

        @Override
        public Optional<Course> getByID(Long id) {
            Assert.notNull(id);
            if(countCoursesInDbWithId(id)==0){
                return Optional.empty();
            }
            else{
                try {
                    String sql = "SELECT * FROM `courses` WHERE `id` =?";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setLong(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    resultSet.next();
                    Course coures = new Course(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            CourseType.valueOf(resultSet.getString("coursetype")),
                            resultSet.getString("description"),
                            resultSet.getInt("hours"),
                            resultSet.getDate("begindate"),
                            resultSet.getDate("enddate")

                    );
                    return Optional.of(coures);
                }
                catch (SQLException sqlException) {
                    throw new DatabaseException(sqlException.getMessage());
                }

            }

        }

        private int countCoursesInDbWithId(Long id) {
            try {
                String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id` =?";
                PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
                preparedStatementCount.setLong(1, id);
                ResultSet resultSetCount = preparedStatementCount.executeQuery();
                resultSetCount.next();
                int courseCount = resultSetCount.getInt(1);
                return courseCount;
            }
            catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }



        @Override
        public List<Course> getAll() {
            String sql = "SELECT * FROM `courses`";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Course> courseList = new ArrayList<>();
                while (resultSet.next()) {
                    courseList.add(new Course(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    CourseType.valueOf(resultSet.getString("coursetype")),
                                    resultSet.getString("description"),
                                    resultSet.getInt("hours"),
                                    resultSet.getDate("begindate"),
                                    resultSet.getDate("enddat")
                            )
                    );

                }
                return courseList;
            } catch (SQLException e) {
                throw new DatabaseException("database errror occured");
            }
            //return null;
        }

        @Override
        public Optional<Course> update(Course entity) {

            Assert.notNull(entity);

            String sql = "UPDATE `courses` SET `name` = ?, `description` = ?, `hours` = ?, `begindate` = ?, `enddate` = ?, `coursetype` = ? WHERE `courses`.`id` = ?";
            if(countCoursesInDbWithId(entity.getId())==0){
                return Optional.empty();
            }
            else {
                try{
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, entity.getName());
                    preparedStatement.setString(2, entity.getDescription());
                    preparedStatement.setInt(3, entity.getHours());
                    preparedStatement.setDate(4, entity.getBeginDate());
                    preparedStatement.setDate(5, entity.getEndDate());
                    preparedStatement.setString(6, entity.getCourseType().toString());
                    preparedStatement.setLong(7, entity.getId());

                    int affectedRows = preparedStatement.executeUpdate();

                    if(affectedRows==0){
                        return Optional.empty();
                    }
                    else{
                        return this.getByID(entity.getId());
                    }
                }
                catch (SQLException sqlException){
                    throw new DatabaseException(sqlException.getMessage());
                }
            }
            //return Optional.empty();
        }

        @Override
        public void deleteById(Long id) {
            Assert.notNull(id);
            String sql = "DELETE FROM `courses` WHERE `id`=?";
            if(countCoursesInDbWithId(id)==1){
                try {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setLong(1, id);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sqlException){
                    throw new DatabaseException(sqlException.getMessage());
                }
            }



            //throw new IllegalArgumentException("No course with this id exists");
        }
    }