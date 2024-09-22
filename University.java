import java.util.*;


/**
 * Represents a University with students and courses.
 * The university class will maintain an index of students and courses using Maps.
 *   - The keys for the students' map is the Student's ID
 *   - The keys for the courses' map is the Course's Code
 */
public class University {
    private Map<Integer, Student> studentBody;
    private Map<String, Course> availableCourses;
    private String universityName;
    private String universityMotto;


    public University(String universityName, String universityMotto) {
        this.universityName = universityName;
        this.universityMotto = universityMotto;
        studentBody = new HashMap<>();
        availableCourses = new HashMap<>();
    }

    /**
     * Adds a student to the university roster. Student's cannot be added twice.
     * @param student the student to be added
     * @return true if the student was added, false if the student was already on the map
     */
    public boolean addStudent(Student student){
        //The if is checking if the student was already in the registered course.
        //The if is also checking the id number and then false is returned if the student's id number is already registered.
        //The else is activated if the if condition fails.
        //Then in the else, the student's id number is registered and then true is returned.
        if(studentBody.containsKey(student.getIdNumber())) {
            return false;
        } else {
            studentBody.put(student.getIdNumber(), student);
            return true;
        }
    }

    /**
     * Gets a student from the university
     * @param idNumber the student's ID.
     * @return the student object or null if not found
     */
    public Student getStudent(int idNumber){
        //Here the student's id number is returned.
        //Here it is checking the student and also null is returned if the student is not found.
        return studentBody.get(idNumber);
    }

    /**
     * Returns a list containing all students from the university
     * You will have to work with maps and lists on this method.
     * Referer to the document on how to ITERATE over maps. You CANNOT use foreach directly with maps
     * What is the best List type to use? ArrayList or LinkedList? Why?
     *
     * @return A list of all students (the list will be empty if the university is empty)
     */
    public List<Student> getStudents(){

        //Here the whole list of students are returned.
        Set<Integer> set = studentBody.keySet();

        //ArrayList is used here and the variable is 'students'.
        ArrayList<Student> students = new ArrayList<>();

        Iterator iterator = set.iterator();

        for(int i = 0; i < studentBody.size(); i++) {
            Object id = iterator.next();
            students.add(studentBody.get(id));
        }
        return students;
    }

    /**
     * Add a course to the courses offered by the university
     * @param course the course object to be added
     * @return true if the course was added, false if the course was already on the university
     */
    public boolean addCourse(Course course){

        //The if is checking if the courses are available or not.
        //If the course was present in the university before, then false is returned.
        //Or else true is returned after adding the required course.
        if(availableCourses.containsKey(course.getCourseCode())) {
            return false;
        }
        availableCourses.put(course.getCourseCode(), course);
        return true;
    }


    /**
     * Adds a course as a pre-requisite to another course. Both courses must already exist
     * in the university's list of offered courses.
     * @param courseID the id of the course you want to add the pre-requisite to
     * @param preReqCourseID the id of the pre-requisite course
     * @return false if either the courseID or preReqCourseID are invalid, true after adding the pre-requisite
     */
    public boolean addRequisiteToCourse(String courseID, String preReqCourseID){

        //The courses are firstly added if the courses or pre-requisite is not valid.
        //Then true is returned.
        //Otherwise false is returned.
        if(availableCourses.containsKey(courseID) && availableCourses.containsKey(preReqCourseID)) {
            Course course = availableCourses.get(courseID);
            Course preReqCourse = availableCourses.get(preReqCourseID);
            List<Course> courseList = course.getPreRequisites();
            courseList.add(preReqCourse);
            return true;
        }
        return false;
    }

    /**
     * Gets a course from the university
     * @param courseCode the course code.
     * @return the course object associated with the code or null if not found
     */
    public Course getCourse(String courseCode){
        //The course is being checked.
        //The course is returned and null if the course is not present.
        return availableCourses.get(courseCode);
    }

    /**
     * Get a list containing all courses offered by the university.
     * You will have to work with maps and lists on this method.
     * Referer to the document on how to ITERATE over maps. You CANNOT use foreach directly with maps
     * What is the best List type to use? ArrayList or LinkedList? Why?
     *
     * @return the list of courses offered by the university
     */
    public List<Course> getCourses(){

        //Here the whole list of courses are returned.
        Set<String> set = availableCourses.keySet();

        //ArrayList is used here and the variable is 'courses'.
        ArrayList<Course> courses = new ArrayList<>();

        Iterator iterator = set.iterator();

        for(int i = 0; i < availableCourses.size(); i++) {
            Object id = iterator.next();
            courses.add(availableCourses.get(id));
        }
        return courses;
    }

    /**
     * Enroll a student in a course IF the student has already passed the pre-requisites of the course.
     * Note to 1110 students: remember that students keep two lists of courses.
     *
     * The method should add the student to the course's list of students and add the course to to the student's
     * list of current courses.
     *
     * @param studentID the id of the student
     * @param courseCode the course code for enrollment.
     * @return false if studentID, courseCode are incorrect, false if the student does not have the pre-requisites
     *         true if the student was enrolled in the course.
     *
     * NOTE2: See how false is representing three different issues? Here the "modern" approach would be to use
     *        exceptions (throw an exception) for the wrong id and code problems.
     */
    public boolean enrollStudentInCourse(int studentID, String courseCode){

        //Here the courses and studentID are added using HashSet and List.
        if(studentBody.containsKey(studentID) && availableCourses.containsKey(courseCode)) {
            HashSet<Course> courses = studentBody.get(studentID).getPreviousCourses();
            List<Course> courses1 = availableCourses.get(courseCode).getPreRequisites();

            //The if is used to check if the ID and courses code are correct or not.
            //False is returned based on the conditions.
            for(int i = 0; i < courses1.size(); i++) {
                if(!courses.contains(courses1.get(i))) {
                    return false;
                }
            }

            //The student and course are added.
            //True is returned if the ID and the course code is valid or matches.
            Student student = studentBody.get(studentID);
            Course course = availableCourses.get(courseCode);
            student.enrollInCourse(course);
            course.addStudentToCourse(student);
            return true;
        }
        return false;
    }

    /**
     * Removes a student from a course IF the student is already enrolled in it.
     * @param studentID the student ID
     * @param courseCode the course code
     * @return false if studentID, courseCode are incorrect, false if the student is not enrolled in the course
     *        true if the student was removed from the course.
     */
    public boolean removeStudentFromCourse(int studentID, String courseCode){

        //Here the courses are added using HashSet.
        if(studentBody.containsKey(studentID) && availableCourses.containsKey(courseCode)) {
            Student student = studentBody.get(studentID);
            Course course = availableCourses.get(courseCode);

            HashSet<Course> courses = student.getEnrolledCourses();

            //The if here checks if the ID and course code is not valid.
            //And also if the student is not registered for the course.
            if(!courses.contains(course)) {
               return false;
            }

            //Then the student gets removed.
            //True is returned based on validations.
            courses.remove(course);

            List<Student> students = course.getEnrolledStudents();
            students.remove(student);
            return true;
        }
        return false;
    }


    /**
     * Removes a student from the university.
     * The student will be removed from the university index AND from the list of students of every course that the
     * student was already enrolled.
     *
     * There are a couple of ways to solve this method. If you iterate over the student's own courses,
     *  you have to be careful not to change the collection while you are iterating (unless you remove with the iterator)
     *
     * @param studentID the id of the student to remove
     * @return false if the studentID is not in the index. True if the student was removed from the index and courses
     */
    public boolean removeStudentFromUniversity(int studentID){

        //Here the Student object is declared.
        Student student = studentBody.get(studentID);

        //The HashSet is used and the name of the variable is 'students'.
        HashSet<Course> students = student.getEnrolledCourses();

        Iterator iterator = students.iterator();

        //Course object is called.
        //Then the student is removed.
        for(int i = 0; i < students.size(); i++) {
            Object course = iterator.next();
            Course course1 = (Course) course;
            List<Student> studentsList = ((Course) course).getEnrolledStudents();
            studentsList.remove(student);
        }
        return studentBody.remove(studentID, student);
    }

    /**
     * Removes a course from the university.
     * This method should remove the course from the university offered courses
     *     AND from each student's current courses.
     *
     * The method must remove the course from any other courses that have it as a pre-requisite
     *
     * @param courseCode the course code
     * @return false if the course code does not match any offered course. True after the course was removed
     */
    public boolean removeCourseFromUniversity(String courseCode){

        //Here Course object is called.
        Course course = availableCourses.get(courseCode);

        //List is used here and the name of the variable is 'students'.
        List<Student> students = course.getEnrolledStudents();

        //HashSet is used and the variable is 'courseList'.
        //The course is then removed.
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            HashSet<Course> courseList = student.getEnrolledCourses();
            courseList.remove(course);
        }
        Set<String> courses = availableCourses.keySet();

        Iterator iterator = courses.iterator(); 

        //Course object is made.
        //List is created.
        //An if is used to check if the list of the courses contains the specified course or not.
        //Then the course is removed from the list of courses.
        for(int i = 0; i < courses.size(); i++) {
            Object id = iterator.next();
            Course course1 = availableCourses.get(id);
            List<Course> courseList = course1.getPreRequisites();
            if(courseList.contains(course)) {
                courseList.remove(course);
            }
        }
        return availableCourses.remove(courseCode, course);

    }


    public String getUniversityMotto() {
        return universityMotto;
    }

    public String getUniversityName() {
        return universityName;
    }

    /**
     * Add a new student using name and ID
     * @param name the student's name
     * @param studentID the student's ID between 0 and 999999
     * @return true if the student was added, false if the student was already on the university
     */
    public boolean addStudent(String name, int studentID){
        return addStudent(new Student(name,studentID));
    }

    public boolean addCourse(String name, String courseID){
        return addCourse(new Course(name,courseID));
    }



    @Override
    public String toString() {
        return String.format("%s (%s)\nNumber of Students: %d\nNumber of Courses %d",
                universityName,universityMotto,studentBody.size(),availableCourses.size());
    }

}
