/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Esha
 */
public class QuizServiceImpl extends UnicastRemoteObject implements QuizService, Serializable {

    private static final String FILE_NAME = "quizData";

    private final Set<Quiz> quizzes = new HashSet<>(); //set of all current quizzes

    private final Map<Integer, ArrayList<String>> quizMap = new HashMap<>();//map Quiz ID to List of Questions

    private final Map<String, String[]> questionAnswers = new HashMap<>(); //holds an array, where pos[0] is the Question and pos[1-4] are the answers.

    private final Map<Integer, Player> highestScorePlayerIDMap = new HashMap<>();// maps Quiz ID to Player (holds player name, quiz ID and score for Quizzes)
//    private Serialize serializer;
//    
//    private QuizService quizData;
//    
    private final String fileName = "quizData.txt";

    public QuizServiceImpl() throws RemoteException {
        QuizService serverQuiz;
        if (new File(fileName).exists()){

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(fileName)));) {
                    serverQuiz = null;
                    serverQuiz = (QuizServiceImpl) ois.readObject();

                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println("On write error " + ex);
                    
                }
    }}

//    public QuizServer(QuizService quizServer, Serialize serialize) throws RemoteException {
//        this.serializer = serialize;
//        this.serializer.setFileName(FILE_NAME);
//        if (serialize.quizDataExists()){
//            Object qData = serialize.deserialize();
//            quizData = (QuizServer) qData;
//        } else {
//            quizData = quizServer;
//        }
//    }
    /**
     *
     * @throws RemoteException
     */
    @Override
    public void serialize() throws RemoteException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName)));

            oos.writeObject(this);
            oos.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public QuizService deserialize() throws RemoteException {
        QuizService quizServer = new QuizServiceImpl();
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(fileName)));

            quizServer = (QuizServiceImpl) ois.readObject();

            ois.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
         return quizServer;
    }
    

    /**
     *
     * @param quizID
     * @throws RemoteException
     */
    @Override
    public void getWinnerForQuiz(int quizID) throws RemoteException {
        System.out.println("got to servers");
        System.out.println(highestScorePlayerIDMap);

        if (highestScorePlayerIDMap.containsKey(quizID)) {
            Player winner = highestScorePlayerIDMap.get(quizID);
            System.out.println(winner);

            if (winner == null) {
                System.out.println("NO HIGHEST SCORER YET.");
            } else {

                System.out.println("THE WINNER FOR QUIZ " + quizID + " IS" + winner.getPlayerName());
                System.out.println("HIGHEST SCORE:" + winner.getPlayerScore());
            }
        } else {
            System.out.println("NO ID.");
        }
    }

    public Map<Integer, Player> getHighestScorePlayerIDMap() throws RemoteException {
        return this.highestScorePlayerIDMap;
    }

    public void setHighestScorePlayerIDMap(int id, Player player) throws RemoteException {
        this.highestScorePlayerIDMap.put(id, player);
    }

    public Set<Quiz> getQuizzes() throws RemoteException {
        return this.quizzes;
    }

    public Map<Integer, ArrayList<String>> getQuizMap() throws RemoteException {
        return this.quizMap;
    }

    public Map<String, String[]> getQuestionsAndAnswers() throws RemoteException {
        return this.questionAnswers;
    }

    public int getRandomID() throws RemoteException {
        Random random = new Random();
        int generatedID = random.nextInt(Integer.MAX_VALUE);
        return generatedID;
    }

    public int getHighestScoreForQuiz(int QuizID) {
        int highestScoreForQuiz = 0;
        for (Quiz a : quizzes) {
            if (a.getQuizID() == QuizID) {
                highestScoreForQuiz = a.getHighestScore();
            }
        }
        return highestScoreForQuiz;
    }

    public void setHighestScoreForQuiz(int QuizID, int score) {
        for (Quiz a : quizzes) {
            if (a.getQuizID() == QuizID) {
                if (a.getHighestScore() < score) {
                    a.setHighestScore(score);
                }
            }
        }
    }

    public int addQuiz(String s) throws RemoteException {
        Quiz newQuiz = new Quiz();
        int ID = getRandomID();
        newQuiz.setQuizID(ID);
        newQuiz.setQuizName(s);
        quizzes.add(newQuiz);
        quizMap.put(ID, null);
        System.out.println("ADDED QUIZ: " + s);
        return ID;
    }

    public Object[] getCurrentQuizList() {
        Object[] quizArray = quizzes.toArray();

        for (Object a : quizArray) {
            Quiz b = (Quiz) a;
            System.out.println("QUIZ: " + b.getQuizName());
        }
        return quizArray;
    }

    public Object[] getListOfQuestionsInQuiz(int id) {
        if (quizMap.containsKey(id)) {
            ArrayList<String> thisListOfQuestions = quizMap.get(id);
            System.out.println(thisListOfQuestions.toString());
            Object[] thisArrayOfQuestions = thisListOfQuestions.toArray();
            return thisArrayOfQuestions;
        } else {
            Object[] message = new String[1];
            message[0] = "ID DOES NOT EXIST";
            return message;
        }
    }

    public String checkIfQuizIDExists(int ID) throws RemoteException {
        String result;
        if (quizMap.containsKey(ID)) {
            result = "ADDING TO QUIZ: " + ID;
        } else {
            result = "CREATING QUIZ: " + ID;
        }
        return result;
    }

    public synchronized void serverAddsSetOfQuestions(int ID, ArrayList<String> newListOfQuestions) throws RemoteException {
//        if (quizMap.containsKey(ID)) {
//            System.out.println("id:" + ID);
//
//            quizMap.put(ID, newListOfQuestions);
//        } else {
//            Quiz aQuiz = new Quiz();
//            quizzes.add(aQuiz);
        quizMap.put(ID, newListOfQuestions);
        System.out.println(quizMap.get(ID).toString());

        System.out.println("ADDED QUESTION TO QUIZ:" + ID);
    }

    public void serverAddsAnswers(String question, String[] answers) throws RemoteException {
//        if (quizMap.containsKey(ID)) {
//            String[] temp = QuestionAnswers.get(ID);
//            temp = answers;
//
//        } else {
        questionAnswers.put(question, answers);
        System.out.println("Quiz: " + question + " has been added/amended in the Question and Answers Map. ");
        System.out.println("QA" + questionAnswers.toString());

    }

    public void serverAddstoQuizMap(String question, String[] answers) throws RemoteException {

        questionAnswers.put(question, answers);
        System.out.println("Quiz: " + question + " has been added/amended in the Question and Answers Map. ");
        System.out.println("QA" + questionAnswers.toString());
    }

    public void addAnswersToQuestions(int ID) throws RemoteException {
        try {
            if (quizMap.containsKey(ID)) {
                System.out.println("Adding answers to Quiz: " + ID);
            } else {
                System.out.println("That Quiz doesnt exist. You must create a Quiz first");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void printQuestions(int id) throws RemoteException {
        Object[] quesForId = quizMap.get(id).toArray();
        System.out.println("The list of Questions added so far are:\n");
        for (Object a : quesForId) {
            System.out.println(a.toString());
        }
    }

    public void writeQuizServer() throws RemoteException {

    }
}
