/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kepegawaian;

import data.Users;
import view.admin.Login;
import view.admin.Regis;

/**
 *
 * @author adity
 */
public class Kepegawaian {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Users users = new Users(" ", " ", " ");
        new Login(users).setVisible(true);
    }
    
}
