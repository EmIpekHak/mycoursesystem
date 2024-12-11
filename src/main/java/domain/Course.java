package domain;

import java.sql.Date;

public class Course extends BaseEntity{

    private String name;
    private String description;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseType courseType;

    public Course(Long id, String name, CourseType courseType, String description, int hours, Date beginDate, Date endDate) throws InvalidValueException{
        super(id);
        this.setName(name);
        this.setCourseType(courseType);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
    }

    public Course(String name, CourseType courseType, String description, int hours, Date beginDate, Date endDate) throws InvalidValueException{
        super(null);
        this.setName(name);
        this.setCourseType(courseType);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) throws  InvalidValueException{
        if(name != null && name.length() > 1) {
            this.name = name;
        }else{
            throw new InvalidValueException("Kursanme muss minedstens 2 zeichen lang sein");

        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidValueException{
        if(description != null && description.length() > 10) {
            this.description = description;
        }else{
            throw new InvalidValueException("Kursbeschreibung muss mindestens 10 zeichen lang sein");

        }
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) throws InvalidValueException{
        if(hours > 0 && hours < 10) {
            this.hours = hours;
        }else{
            throw new InvalidValueException("Anzahl der Kurssatunden pro Kurs darf nur zwischen 1 und 10 liegen!");

        }
        this.hours = hours;
    }

    public java.sql.Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) throws InvalidValueException{
        if(beginDate != null){
            if(endDate!=null) {
                if (beginDate.before(endDate)) {
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueException("Beginn des Kurses muss vor dem Ende liegen");
                }
            }else{
                this.beginDate = beginDate;
            }
        }else {
            throw new InvalidValueException("Startdatum darf nicht null/leer sein!");
        }
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws InvalidValueException{
        if(endDate != null){
            if(this.beginDate!=null) {
                if (endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueException("Kursende muss nach Kursbeginn sein!");
                }
            }else{
                this.endDate = endDate;
            }
        }else {
            throw new InvalidValueException("Enddatum darf nicht leer sein!");
        }
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) throws InvalidValueException{
        if(courseType != null){
            this.courseType = courseType;
        }else {
            throw new InvalidValueException("Kurstyp darf nicht null/leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + this.getId()+ '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseType=" + courseType +
                '}';
    }
}