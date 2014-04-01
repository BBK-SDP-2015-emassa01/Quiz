/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.util.Scanner;

/**
 *
 * @author Esha
 */
public class GetInput {

    public int getIntInput() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public String getStringInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

}
