package windows;

import windowRunners.StudentRunner;
import database.Student;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class StudentWindow extends JFrame implements ActionListener
{
    public static final int MINIMUM_WIDTH = 300;
    public static final int MINIMUM_HEIGHT = 100;
    public static final int ADD_WIDTH_FACTOR = 30;
    public static final int ADD_HEIGHT_FACTOR = 30;
    private StudentRunner sr;
    private Student student;
    
    private JPanel infoPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    
    private JPanel coursePanel;
    private JLabel[][] courseLabels;
    
    private JPanel buttonPanel;
    private JButton gpaButton;
    private JButton doneButton;

    private JTable  courseTable;
    private JTextArea courseTextArea;
    private String courseTemp;
    
    public StudentWindow(StudentRunner sr, Student student)
    {
        super(student.getName());
        this.sr = sr;
        this.student = student;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        infoPanel = new JPanel(new GridLayout(1, 2));
        usernameLabel = new JLabel("ID: " + student.getStudentID());
        infoPanel.add(usernameLabel);
        //passwordLabel = new JLabel("Password: " + student.getPassword());
        //infoPanel.add(passwordLabel);
        add(infoPanel, BorderLayout.NORTH);
        
        setupCourseData();
        
        buttonPanel = new JPanel(new FlowLayout());
        gpaButton = new JButton("GPA");
        gpaButton.addActionListener(this);
        buttonPanel.add(gpaButton);
        doneButton = new JButton("Done");
        doneButton.addActionListener(this);
        buttonPanel.add(doneButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupCourseData()
    {
        Object[][] courseData = student.getCourseData();
        if(courseData.length == 0)
        {
            setSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
            return;
        }
        
        coursePanel = new JPanel(new GridLayout(courseData.length, courseData[0].length, -1, -1));
        courseLabels = new JLabel[courseData.length][courseData[0].length];


        Object[] header = {"Courses", "Test1", "Test2", "Test3"};
        courseTable = new JTable(courseData,header);

        courseTextArea = new JTextArea(courseData.length, courseData[0].length);
        coursePanel.add(courseTable);


        add(coursePanel, BorderLayout.CENTER);
        setSize(MINIMUM_WIDTH + courseData[0].length*ADD_WIDTH_FACTOR, MINIMUM_HEIGHT + (courseData.length*ADD_HEIGHT_FACTOR));
        setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if(action.equals("Done"))
            sr.clickedDone();
        else if(action.equals("GPA"))
            sr.clickedGPA();
    }
}