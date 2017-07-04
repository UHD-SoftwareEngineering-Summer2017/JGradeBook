/*
    FileInputOutput handles file I/O.
    Student files are stored in the format:
    Name of file: StudentID.txt where StudentID has digits 0-9 and starts with a digit 1-9.
    Student Password
    Student Name
    NameOfCourse$##$##$##
    NameOfCourse$##$##$##
    Administrator files are stored in the format:
    Name of file: AdministratorID.txt where AdministratorID has digits 0-9 and starts with a digit 1-9.
    Administrator Password
*/

package database;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class FileInputOutput
{
    /**
     studentIDExists checks if the passed student ID exists in the Students folder.
     */
    public static boolean studentIDExists(int studentID)
    {
        try
        {
            FileInputStream check = new FileInputStream("Students/" + studentID + ".txt");
            return true;
        }
        catch(FileNotFoundException e)
        {
            return false;
        }
    }

    /**
     adminIDExists checks if the passed administrator ID exists in the Administrators folder.
     */
    public static boolean adminIDExists(int administratorID)
    {
        try
        {
            FileInputStream check = new FileInputStream("Administrators/" + administratorID + ".txt");
            return true;
        }
        catch(FileNotFoundException e)
        {
            return false;
        }
    }

    /**
     rightStudentPassword checks if the password matches the studentID.
     */
    public static boolean rightStudentPassword(int studentID, String password)
    {
        try
        {
            Scanner fInput = new Scanner(new FileInputStream("Students/" + studentID + ".txt"));
            return fInput.nextLine().equals(password);
        }
        catch(FileNotFoundException e)
        {
            return false;
        }
    }

    /**
     rightAdminPassword checks if the password matches the administratorID.
     */
    public static boolean rightAdminPassword(int administratorID, String password)
    {
        try
        {
            Scanner fInput = new Scanner(new FileInputStream("Administrators/" + administratorID + ".txt"));
            return fInput.nextLine().equals(password);
        }
        catch(FileNotFoundException e)
        {
            return false;
        }
    }

    /**
     getStudentFromFile returns a Student object using the passed ID.
     */
    public static Student getStudentFromFile(int username) throws FileNotFoundException
    {
        Scanner fInput = new Scanner(new FileInputStream("Students/" + username + ".txt"));
        Student student = new Student(username, fInput.nextLine(), fInput.nextLine());
        
        StringTokenizer st;
        String courseName;
        while (fInput.hasNextLine())
        {
            st = new StringTokenizer(fInput.nextLine(), "$");
            courseName = st.nextToken();
            student.enrollCourse(courseName);
            while (st.hasMoreTokens())
                student.addGrade(courseName, Double.parseDouble(st.nextToken()));
        }
        
        fInput.close();
        return student;
    }

    /**
     getStudentsFromFolder returns all the Student objects in the Students folder.
     */
    public static Student[] getStudentsFromFolder()
    {
        File[] studentFilesTemp = new File("Students").listFiles();
        ArrayList<File> studentFiles = new ArrayList();
        for(int index = 0; index < studentFilesTemp.length; index++)
        {
            studentFiles.add(studentFilesTemp[index]);
        }
        
        Student[] students = new Student[studentFiles.size()];
        StringTokenizer st;
        try
        {
            for(int index = 0; index < students.length; index++)
            {
                st = new StringTokenizer(studentFiles.get(index).getName(), ".");
                students[index] = getStudentFromFile(Integer.parseInt(st.nextToken()));
            }
        }
        catch (FileNotFoundException e) {}
        
        return students;
    }

    /**
     createNewStudentFile creates a new Student file in the Students folder.
     */
    public static void createNewStudentFile(Student student) throws Exception
    {
        if(studentIDExists(student.getStudentID())) {
            throw new Exception("ID " + student.getStudentID() + " already exists.");
        }
        if(student.getStudentID() < 10000 || student.getStudentID() > 19999) {
            throw new Exception("ID " + student.getStudentID() + " already exists.");
        }
        
        File studentFile = new File("Students/" + student.getStudentID() + ".txt");
        Course[] courses = student.getCourses();
        double[] grades;
        try
        {
            PrintWriter fOutput = new PrintWriter(studentFile);
            fOutput.println(student.getPassword());
            fOutput.print(student.getName());
            for(int courseIndex = 0; courseIndex < courses.length; courseIndex++)
            {
                fOutput.println();
                fOutput.print(courses[courseIndex].getName());
                grades = courses[courseIndex].getGrades();
                for(int gradeIndex = 0; gradeIndex < grades.length; gradeIndex++)
                    fOutput.print("$" + grades[gradeIndex]);
            }
            fOutput.close();
        }
        catch(FileNotFoundException e) {}
    }

    /**
     replaceStudentFile replaces a Student file in the Students folder.
     */
    public static void replaceStudentFile(Student student) throws FileNotFoundException
    {
        if(!studentIDExists(student.getStudentID()))
            throw new FileNotFoundException("ID " + student.getStudentID() + " does not exist.");
        
        File studentFile = new File("Students/" + student.getStudentID() + ".txt");
        Course[] courses = student.getCourses();
        double[] grades;
        try
        {
            PrintWriter fOutput = new PrintWriter(studentFile);
            fOutput.println(student.getPassword());
            fOutput.print(student.getName());
            for(int courseIndex = 0; courseIndex < courses.length; courseIndex++)
            {
                fOutput.println();
                fOutput.print(courses[courseIndex].getName());
                grades = courses[courseIndex].getGrades();
                for(int gradeIndex = 0; gradeIndex < grades.length; gradeIndex++)
                    fOutput.print("$" + grades[gradeIndex]);
            }
            fOutput.close();
        }
        catch(FileNotFoundException e) {}
    }

    /**
     deleteStudentFile deletes a Student file from the Students folder.
     */
    public static void deleteStudentFile(Student student) throws FileNotFoundException
    {
        if(!studentIDExists(student.getStudentID()))
            throw new FileNotFoundException("ID " + student.getStudentID() + " already does not exist.");
        
        new File("Students/" + student.getStudentID() + ".txt").delete();
    }
}
