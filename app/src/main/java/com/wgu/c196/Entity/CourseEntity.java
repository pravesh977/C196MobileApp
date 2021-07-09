package com.wgu.c196.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//FIXME add foreign key constraints
@Entity(tableName = "Course_Table"
//, foreignKeys = @ForeignKey(entity = TermEntity.class, parentColumns = "termId", childColumns = "termId", onDelete = ForeignKey.RESTRICT)
)
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termId;

    public CourseEntity(int courseId, String courseTitle, String startDate, String endDate, String courseStatus, String instructorName, String instructorPhone, String instructorEmail, int termId) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
