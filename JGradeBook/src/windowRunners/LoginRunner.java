/**
 LoginRunner controls the login window and login system.
 */
package windowRunners;

import database.Student;
import database.FileInputOutput;
import windows.LoginWindow;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

public class LoginRunner
{
    private LoginWindow lw; // Associated LoginWindow

    /**
     Constructor.
     */
    public LoginRunner()
    {
        lw = new LoginWindow(this);
        lw.setVisible(true);
    }

    /**
     clickedLogin checks the entered username and password and logs in the user.
     */
    public void clickedLogin(String username, String password)
    {
        int usernameInt = 0;
        // Check if the username is a number.
        try
        {
            usernameInt = Integer.parseInt(username);
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Username in wrong format.", "Login", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // Try to find the username in the Students folder.
        if(FileInputOutput.studentIDExists(usernameInt))
        {
            // If found, check that the password is right.
            if(FileInputOutput.rightStudentPassword(usernameInt, password))
            {
                try
                {
                    // If right, log in as an administrator.
                    //Student student = FileInputOutput.getStudentFromFile(usernameInt);
                    lw.dispose();
                    StudentRunner sr = new StudentRunner(student);
                }
                catch(FileNotFoundException e) {}
            }
            else
                loginWrong();
        }

        // If not found, try to find the username in the Administrators folder.
        else if(FileInputOutput.adminIDExists(usernameInt))
        {
            if(FileInputOutput.rightAdminPassword(usernameInt, password))
            {
                lw.dispose();
                AdminRunner ar = new AdminRunner();
            }
            else
                loginWrong();
        }
        
        else
            loginWrong();
    }

    /**
     clickedCancel exits the program.
     */
    public void clickedCancel()
    {
        System.exit(0);
    }
    public void loginWrong()
    {
        JOptionPane.showMessageDialog(null, "Wrong username and/or password.", "Login", JOptionPane.WARNING_MESSAGE);
    }

    /**
     loginWrong displays a window if the login information is wrong.
     */
    public void fileNotFound()//Testing Function
    {
        JOptionPane.showMessageDialog(null, "File Not Found.", "Login", JOptionPane.WARNING_MESSAGE);
    }
}
