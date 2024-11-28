package domain;

import java.sql.Date;

public class Course extends BaseEntity {
    private String name;
    private String description;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseType courseType;

    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType) throws InvalidValueException {
        super(null);
        setName(name);
        setDescription(description);
        setHours(hours);
        setBeginDate(beginDate);
        setEndDate(endDate);
        setCourseType(courseType);
    }

    public String getName() throws InvalidValueException {
        if (name != null && name.length() > 1) {
            return name;
        } else {
            throw new InvalidValueException("Kursname muss mindestens 2 Zeichen lang sein");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + getId() + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseType=" + courseType +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() throws InvalidValueException {
        if (description != null && description.length() > 5) {
            return description;
        } else {
            throw new InvalidValueException("Beschreibung muss mindestens 6 Zeichen lang sein");
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() throws InvalidValueException {
        if (hours > 0) {
            return hours;
        } else {
            throw new InvalidValueException("Stundenzahl muss größer als 0 sein");
        }
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Date getBeginDate() throws InvalidValueException {
        if (beginDate != null) {
            return beginDate;
        } else {
            throw new InvalidValueException("Beginndatum darf nicht null sein");
        }
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() throws InvalidValueException {
        if (endDate != null) {
            return endDate;
        } else {
            throw new InvalidValueException("Enddatum darf nicht null sein");
        }
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CourseType getCourseType() throws InvalidValueException {
        if (courseType != null) {
            return courseType;
        } else {
            throw new InvalidValueException("Kursetyp darf nicht null sein");
        }
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
