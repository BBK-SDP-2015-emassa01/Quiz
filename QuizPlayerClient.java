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

/**
 *
 * @author Esha
 */
public class QuizPlayerClient implements Serializable {

    QuizService serverQuiz;
    Remote service;
    boolean running = true;
    
    private Serialize serializers;

    GetInput input = new GetInput();
    String player;
    
    Player playerName;

    public QuizPlayerClient() throws NotBoundException, MalformedURLException, RemoteException {
        serverQuiz = new QuizServer();
//        clientQuiz = new QuizServer();
        Remote service = this.service = Naming.lookup("//127.0.0.1:1099/quiz");
//        if (System.getSecurityManager() == null) {
//        System.setSecurityManager(new RMISecurityManager());
//        }
        //serverQuiz.deserialize();
        System.out.println("\t\t\t\tWELCOME! PLAY A QUIZ HERE!");
        System.out.println("ENTER YOUR PLAYER NAME: ");
        player = input.getStringInput();
        playerName = new Player();
        playerName.setPlayerName(player);
    }

    public void launch() throws RemoteException {

        try {
            serverQuiz = (QuizService) service;

            keepLooping();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void keepLooping() throws RemoteException {
        if (running) {
            int selectedQuizID = menu();
            
            System.out.println("TO PLAY THIS QUIZ PRESS 1");
            System.out.println("TO CLOSE THIS QUIZ PRESS 2");
            
            int response = input.getIntInput();
            
            if (response == 1){
            playSelectedQuiz(selectedQuizID);
            }
            else if (response ==2){
                //getWinnerForQuiz NOT WORKING - IT IS IN THE INTERFACE!?
                serverQuiz.getWinnerForQuiz(selectedQuizID);
                serverQuiz.serialize();
                System.out.println("FINISHED SERIALIZATION.");
            }
            keepLooping();
        } else {
            System.exit(0);
        }
    }

    public void terminateQuiz() {
        running = false;
        //serialize?
        System.exit(0);
        
    }

    public static void main(String args[]) {
        try {
            QuizPlayerClient playerClient = new QuizPlayerClient();
            playerClient.launch();
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {

        }
    }

    public int menu() throws RemoteException {

        printOutQuizList();
        System.out.println("ENTER QUIZ ID TO ACCESS: ('END' TO EXIT)");
        
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equalsIgnoreCase("end")) {
            running = false;
            terminateQuiz();
        }
        int quizID = Integer.parseInt(input);

        return quizID;
    }

    public int selectQuizToPlay() {
        //return quiz ID that the player wants to play
        System.out.println("ENTER ID OF QUIZ TO ACCESS.");
        Scanner input = new Scanner(System.in);
        int result = input.nextInt();
        return result;
    }

    public void printOutQuizList() throws RemoteException {
        try {
            Object[] quizArray = serverQuiz.getCurrentQuizList();
            if (serverQuiz.getCurrentQuizList() == null){
                System.out.println("NO SAVED QUIZZES.");
            }
            for (Object a : quizArray) {
                Quiz b = (Quiz) a;
                System.out.println("ID: "+b.getQuizID()+"\t|| NAME: "+ b.getQuizName());
            }
            //System.out.println("THE COMPLETE LIST:");
        } catch (NullPointerException e) {
            System.out.println("ID DOES NOT EXIST");
        }
    }

    public int getHighestScoreForPlayer(int quizID) throws RemoteException {
        return serverQuiz.getHighestScoreForQuiz(quizID);
    }

    /**
     *
     * @param selectedQuizID
     * @throws RemoteException
     */
    public void playSelectedQuiz(int selectedQuizID) throws RemoteException {

        Map<Integer, ArrayList<String>> quizMap = serverQuiz.getQuizMap();
        ArrayList<String> questions = quizMap.get(selectedQuizID);
        int tempScore = 0;
        int highestScoreForQuiz = 0;

        for (int i = 0; i < questions.size(); i++) {

            Map<String, String[]> thisSet = serverQuiz.getQuestionsAndAnswers();
            highestScoreForQuiz = serverQuiz.getHighestScoreForQuiz(i);

            try {
                String[] QAs = thisSet.get(questions.get(i));

                System.out.println("Question: " + QAs[0] + "\n");

                System.out.println("Option 1: " + QAs[1]);

                System.out.println("Option 2: " + QAs[2]);

                System.out.println("Option 3: " + QAs[3]);

                System.out.println("Option 4: " + QAs[4]);
                String answer = input.getStringInput();

                if (answer.equals(QAs[5])) {
                    tempScore++;
                    System.out.println("CORRECT! 1 POINT AWARDED!\n");
                } else { 
                    System.out.println("WRONG!\n");
                }
                
                if (serverQuiz.getHighestScoreForQuiz(selectedQuizID)< tempScore){
                    serverQuiz.setHighestScoreForQuiz(selectedQuizID, tempScore);
                    
                }
                } catch (RemoteException e) {
                System.out.println("Questions for this Quiz were not found.");
                e.printStackTrace();
            }
            
        }
        System.out.println("QUIZ COMPLETE. YOUR SCORE: "+ tempScore);
        if (tempScore>highestScoreForQuiz){
        System.out.println("\n\nYOU HAVE THE HIGHEST SCORE SO FAR!");
        playerName.setPlayerScore(highestScoreForQuiz);
        serverQuiz.setHighestScorePlayerIDMap(selectedQuizID, playerName);
        }
    }
}
