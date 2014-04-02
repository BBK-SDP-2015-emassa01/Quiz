/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Esha
 */
public class QuizSetupClient {

    QuizService serverQuiz;
//    QuizService clientQuiz;

    boolean running = true;

    Remote service;
    private int quizID;

    public QuizSetupClient() throws NotBoundException, MalformedURLException, RemoteException {
        serverQuiz = new QuizServer();
//        clientQuiz = new QuizServer();
        Remote service = this.service = Naming.lookup("//127.0.0.1:1099/quiz");
//        if (System.getSecurityManager() == null) {
//        System.setSecurityManager(new RMISecurityManager());
//        }
        System.out.println("\t\t\t\tWELCOME TO THE QUIZ CREATOR!");
    }

    public void launch() throws RemoteException {

        try {
            serverQuiz = (QuizService) service;

            keepLooping();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            QuizSetupClient quizClient = new QuizSetupClient();
            quizClient.launch();
        } catch (Exception ex) {

        }
    }

    public int menu() {

        System.out.println("What would you like to do?");
        System.out.println("-->>PRESS 1 TO ADD QUIZ.");
        System.out.println("-->>Press 3 to CLOSE A QUIZ QUOTING THE GAME ID. TO RETURN THE WINNER, FULL PLAYER DETAILS (SAVED ON SERVER)");
        System.out.println("-->>Press 4 to save and exit.");
        System.out.println("-->>Press 5 to get the current Quiz List.");
        System.out.println("-->>Press 6 to get the list of questions for a specific Quiz.");
        System.out.println("-->>Press 7 to add the answers to an already specified set of quiz questions.");

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        int switchValue = Integer.parseInt(input);
        return switchValue;
    }

    public void keepLooping() throws RemoteException {
        if (running) {
            dealWithSwitchRequest(menu());
            keepLooping();
        } else {
            System.exit(0);
        }
    }

    public ArrayList<String> clientAddsSetOfQuestions(int id) throws RemoteException {
        ArrayList<String> newListOfQuestions = new ArrayList<>();
        String question = null;
        String[] answers = null;
        quizID = id;

        boolean collectingQ = true;
        while (collectingQ) {
            System.out.println("ENTER A QUESTION: ");
            GetInput input = new GetInput();
            question = input.getStringInput();

            if (question.equalsIgnoreCase("end")) {
//                serverQuiz.serverAddsAnswers(question, answers);
                serverQuiz.serverAddsSetOfQuestions(id, newListOfQuestions);
                collectingQ = false;
                System.out.println("SETUP COMPLETE.");
            } else {
                newListOfQuestions.add(question);
                answers = clientAddsAnswers(question);
                serverQuiz.serverAddsAnswers(question, answers);
                serverQuiz.getQuestionsAndAnswers().put(question, answers);

            }
//            if (!question.equals("end")) {
//                answers = clientAddsAnswers(question);
//                serverQuiz.serverAddsAnswers(id, answers);
//                serverQuiz.serverAddsSetOfQuestions(id, newListOfQuestions);

//                serverQuiz.getQuestionsAndAnswers().put(id, answers);
//                serverQuiz.getQuizMap().put(id, newListOfQuestions);
//            }
        }

        Object[] list = newListOfQuestions.toArray();
        for (Object a : list) {
            System.out.println("ADDED: " + a.toString());
        }
        return newListOfQuestions;
    }

    public String[] clientAddsAnswers(String question) {
        String[] answers = new String[6];
        System.out.println("ENTER YOUR MULTIPLE ANSWER CHOICES");

        GetInput input = new GetInput();

        answers[0] = question;
        System.out.println("CHOICE 1:");

        String ans1 = input.getStringInput();
        answers[1] = ans1;

        System.out.println("CHOICE 2:");
        String ans2 = input.getStringInput();
        answers[2] = ans2;

        System.out.println("CHOICE 3:");
        String ans3 = input.getStringInput();
        answers[3] = ans3;

        System.out.println("CHOICE 4:");
        String ans4 = input.getStringInput();
        answers[4] = ans4;

        System.out.println("CHOICE NUMBER OF CORRECT ANSWER:");
        String ans5 = input.getStringInput();
        answers[5] = ans5;
        System.out.println("SAVED SUCCESSFULLY.");
        return answers;

    }

    public void dealWithSwitchRequest(int choice) throws RemoteException {
        switch (choice) {
            case 1: //deal with add a new Quiz
                System.out.println("ENTER QUIZ NAME:");
                GetInput input = new GetInput();
                String name = input.getStringInput();
                int id = serverQuiz.addQuiz(name);
                System.out.println("QUIZ ID: \"" + id + "\"");
                System.out.println("ENTER THE QUESTIONS. TYPE 'END' TO FINISH. ");
                ArrayList<String> questionSet = clientAddsSetOfQuestions(id);
                serverQuiz.serverAddsSetOfQuestions(id, questionSet);
                break;
            case 3: //currently empty

                break;
            case 4: //exit given the Quiz ID
                running = false;
                //NEED TO SERIALIZE DATA HERE.
                System.out.println("The program has saved and closed. Thanks for playing the Quiz Game!");
                //notify who is the winner getScore()
                System.exit(0);
                break;
            case 6:
                System.out.println("What is the ID of the Quiz? (If you do not know the Quiz ID you can check it using the get Quiz List (Option 5)).");
                GetInput input2 = new GetInput();
                Object[] questions2 = serverQuiz.getListOfQuestionsInQuiz(input2.getIntInput());
                for (Object a : questions2) {
                    System.out.println("Question: " + a.toString());
                }
                break;
            case 7:
                //add the answers to an already specified set of quiz questions
                input2 = new GetInput();
                System.out.println("Enter the ID number of your Quiz.");
                int QuizId = input2.getIntInput();
                System.out.println(serverQuiz.checkIfQuizIDExists(QuizId));
                break;
            //
            default:
                System.out.println("Something went wrong, please try again.");
                break;
        }
    }
}
