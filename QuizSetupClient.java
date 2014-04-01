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
        System.out.println("WELCOME TO THE QUIZ CREATOR!");
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
        System.out.println("-->>Press 1 to Add a New Quiz.");
        System.out.println("-->>Press 2 to add a question or, set of Questions and their multiple choice answers to a specified (or new) Quiz.");
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
            System.out.println("Please enter a question: ");
            GetInput input = new GetInput();
            question = input.getStringInput();

            if (question.equals("end")) {
                serverQuiz.serverAddsAnswers(id, answers);
                serverQuiz.serverAddsSetOfQuestions(id, newListOfQuestions);
                collectingQ = false;
                System.out.println("Set-Up Client completed entering Questions.");
            } else {
                newListOfQuestions.add(question);
                System.out.println("Set-Up Client added a Question");
                answers = clientAddsAnswers(question);
                serverQuiz.getQuestionsAndAnswers().put(id, answers);

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
            System.out.println("Added Question: " + a.toString());
        }
        return newListOfQuestions;
    }

    public String[] clientAddsAnswers(String question) {
        String[] answers = new String[6];

        GetInput input = new GetInput();

        answers[0] = question;
        System.out.println("Please enter in the 1 ST multiple choice answer for this question (as you would like it to appear in the list):\n");

        String ans1 = input.getStringInput();
        answers[1] = ans1;
        System.out.println("Added: " + ans1 + " to the multiple choice answers.");

        System.out.println("Please enter in the 2 ND multiple choice answer for this question (as you would like it to appear in the list):\n");
        String ans2 = input.getStringInput();
        answers[2] = ans2;
        System.out.println("Added: " + ans2 + " to the multiple choice answers.");

        System.out.println("Please enter in the 3 RD multiple choice answer for this question (as you would like it to appear in the list):\n");
        String ans3 = input.getStringInput();
        answers[3] = ans3;
        System.out.println("Added: " + ans3 + " to the multiple choice answers.");

        System.out.println("Please enter in the 4 TH multiple choice answer for this question (as you would like it to appear in the list):\n");
        String ans4 = input.getStringInput();
        answers[4] = ans4;
        System.out.println("Added: " + ans4 + " to the multiple choice answers.");

        System.out.println("Please enter the number for the multiple choice option for the CORRECT ANSWER followed by enter.");
        String ans5 = input.getStringInput();
        answers[5] = ans5;
        System.out.println("The correct answer has been saved.");
        return answers;

    }

    public void dealWithSwitchRequest(int choice) throws RemoteException {
        switch (choice) {
            case 1: //deal with add a new Quiz
                System.out.println("Enter the Name of your Quiz.");
                GetInput input = new GetInput();
                String name = input.getStringInput();
                int id = serverQuiz.addQuiz(name);
                System.out.println("The ID number for the Quiz is: \"" + id + "\"");

                break;
            case 2: //deal with add a set of Questions to a specified Quiz

                GetInput input1 = new GetInput();
                System.out.println("Enter the ID number of your Quiz.");
                int QuizId = input1.getIntInput();
                System.out.println(serverQuiz.checkIfQuizIDExists(QuizId));

                System.out.println("Enter your Question to add to the list followed by Enter. Type 'end' to finish.");
                ArrayList<String> questionSet = clientAddsSetOfQuestions(QuizId);
                serverQuiz.serverAddsSetOfQuestions(QuizId, questionSet);
                //System.out.println("Please enter your next question, or 'end' to complete entering questions.");

                break;
            case 3: //currently empty

                break;
            case 4: //exit
                running = false;
                //NEED TO SERIALIZE DATA HERE.
                System.out.println("The program has saved and closed. Thanks for playing the Quiz Game!");
                System.exit(0);
                break;
//            case 5:
//                try {
//                    Object[] quizArray = serverQuiz.getCurrentQuizList();
//                    for (Object a : quizArray) {
//                        Quiz b = (Quiz) a;
//                        System.out.println("Quiz Name: " + b.getQuizName());
//                    }
//                    System.out.println("This is the complete list. If nothing has appeared, then the list is empty.\n\n");
//                } catch (NullPointerException e) {
//                    System.out.println("That ID does not exist.");
//                }
//                break;
            case 6:
                System.out.println("What is the ID of the Quiz? (If you do not know the Quiz ID you can check it using the get Quiz List (Option 5)).");
                GetInput input2 = new GetInput();
                Object[] questions = serverQuiz.getListOfQuestionsInQuiz(input2.getIntInput());
                for (Object a : questions) {
                    System.out.println("Question: " + a.toString());
                }
                break;
            case 7:
                //add the answers to an already specified set of quiz questions
                input2 = new GetInput();
                System.out.println("Enter the ID number of your Quiz.");
                QuizId = input2.getIntInput();
                System.out.println(serverQuiz.checkIfQuizIDExists(QuizId));
                break;
            //
            default:
                System.out.println("Something went wrong, please try again.");
                break;
        }
    }
}
