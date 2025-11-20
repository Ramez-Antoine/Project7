package project7;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private ArrayList<Course> createdCourses;
    private Databasef db = new Databasef();

    public Instructor(String id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash, "instructor");
        this.createdCourses = db.loadCoursesByInstructor(id); 
    }

    public ArrayList<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void createCourse(String courseId, String title, String description, String instructorId) {
        Course c = new Course(courseId, title, description, instructorId);

        // 1) Add to memory
        createdCourses.add(c);

        // 2) Save to database
        db.addCourse(c);
    }

    public Course getCourseById(String courseId) {
        for (Course c : createdCourses) {
            if (c.getCourseId().equals(courseId)) return c;
        }
        return null;
    }

    public void editCourse(String courseId, String newTitle, String newDescription, String newInstructorId) {
        Course c = getCourseById(courseId);
        if (c != null) {
            c.editCourse(newTitle, newDescription, newInstructorId);
            db.updateCourse(c);
        }
    }

    public void deleteCourse(String courseId) {
        createdCourses.removeIf(c -> c.getCourseId().equals(courseId));
        db.deleteCourse(courseId);
    }

    public void addLessonToCourse(String courseId, Lesson lesson) {
        Course c = getCourseById(courseId);
        if (c != null) {
            c.getLessons().add(lesson);
            db.updateCourse(c);
        }
    }

    public void editLessonInCourse(String courseId, String lessonId, String newTitle, String newContent) {
        Course c = getCourseById(courseId);
        if (c != null) {
            for (Lesson l : c.getLessons()) {
                if (l.getLessonId().equals(lessonId)) {
                    l.setTitle(newTitle);
                    l.setContent(newContent);
                }
            }
            db.updateCourse(c);
        }
    }

    public void deleteLessonFromCourse(String courseId, String lessonId) {
        Course c = getCourseById(courseId);
        if (c != null) {
            c.getLessons().removeIf(l -> l.getLessonId().equals(lessonId));
            db.updateCourse(c);
        }
    }
}
    

