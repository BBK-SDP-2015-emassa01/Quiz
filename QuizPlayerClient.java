/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
public class QuizPlayerClient implements Serializable {

    QuizService serverQuiz;
    Remote service;
    boolean running = true;

    GetInput input = new GetInput();

    public QuizPlayerClient() throws NotBoundException, MalformedURLException, RemoteException {
        serverQuiz = new QuizServer();
//        clientQuiz = new QuizServer();
        Remote service = this.service = Naming.lookup("//127.0.0.1:1099/quiz");
//        if (System.getSecurityManager() == null) {
//        System.setSecurityManager(new RMISecurityManager());
//        }
        System.out.println("WELCOME! PLAY A QUIZ HERE!");
    }

    public void launch() throws RemoteException {

        try {
            serverQuiz = (QuizService) service;

            keepLooping();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void keepLooping() throws RemoteException {
        if (running) {
            int selectedQuizID = menu();
            playSelectedQuiz(selectedQuizID);
            keepLooping();
        } else {
            System.exit(0);
        }
    }

    public void terminateQuiz() {
        running = false;
    }

    public static void main(String args[]) {
        try {
            QuizPlayerClient playerClient = new QuizPlayerClient();
            playerClient.launch();
        } catch (Exception ex) {

        }
    }

    public int menu() throws RemoteException {

        System.out.println("\n\nBelow is the current quiz list. ");
        printOutQuizList();
        System.out.println("Please select a quiz you would like to play by entering it's ID NUMBER. \n"
                + "Type 'end' to save and exit.\n"
                + "Your score will be returned at the end of the quiz.");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equals("end")) {
            running = false;
        }
        int switchValue = Integer.parseInt(input);

        return switchValue;
    }

    public int selectQuizToPlay() {
        //return quiz ID that the player wants to play
        System.out.println("Please enter the ID number of the Quiz you want to play.");
        Scanner input = new Scanner(System.in);
        int result = input.nextInt();
        return result;
    }

    public void printOutQuizList() throws RemoteException {
        try {
            Object[] quizArray = serverQuiz.getCurrentQuizList();
            for (Object a : quizArray) {
                Quiz b = (Quiz) a;
                System.out.println("Quiz Name: " + b.getQuizName() + ", Quiz ID: " + b.getQuizID());
            }
            System.out.println("This is the complete list. If nothing has appeared, then the list is empty.\n");
        } catch (NullPointerException e) {
            System.out.println("That ID does not exist.");
        }
    }

    public int getScoreForPlayer() throws RemoteException {
        return serverQuiz.getScore();

    }

    /**
     *
     * @param selectedQuizID
     * @throws RemoteException
     */
    public void playSelectedQuiz(int selectedQuizID) throws RemoteException {

        Map<Integer, ArrayList<String>> quizMap = serverQuiz.getQuizMap();
        ArrayList<String> questions = quizMap.get(selectedQuizID);
        int score = serverQuiz.getScore();

        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i));

            Map<String, String[]> thisSet = serverQuiz.getQuestionsAndAnswers();
            System.out.println(thisSet.toString());
            //if (thisSet.containsKey(questions.get(i))) {
            try {
                String[] QAs = thisSet.get(questions.get(i));

                System.out.println("Question: " + QAs[0] + "\n");

                System.out.println("Option 1: " + QAs[1]);

                System.out.println("Option 2: " + QAs[2]);

                System.out.println("Option 3: " + QAs[3]);

                System.out.println("Option 4: " + QAs[4]);
                String answer = input.getStringInput();

                if (answer.equals(QAs[5])) {
                    score++;
                    System.out.println("CORRECT! \n 1 POINT AWARDED!");
                } else {
                    System.out.println("WRONG!");
                //}

                    //the answer needs to be checked and accumulated if correct.
                }
            } catch (Exception e) {
                System.out.println("Questions for this Quiz were not found.");
                e.printStackTrace();
            }
        }
    }
}
