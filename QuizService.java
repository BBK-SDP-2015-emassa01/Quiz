/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizProject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Esha
 */
public interface QuizService extends Remote {

    void addAnswersToQuestions(int ID) throws RemoteException;

    int addQuiz(String s) throws RemoteException;

    String checkIfQuizIDExists(int ID) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    QuizService deserialize() throws RemoteException;

    Object[] getCurrentQuizList() throws RemoteException;

    int getHighestScoreForQuiz(int QuizID) throws RemoteException;

    Map<Integer, Player> getHighestScorePlayerIDMap() throws RemoteException;

    Object[] getListOfQuestionsInQuiz(int id) throws RemoteException;

    Map<String, String[]> getQuestionsAndAnswers() throws RemoteException;

    Map<Integer, ArrayList<String>> getQuizMap() throws RemoteException;

    Set<Quiz> getQuizzes() throws RemoteException;

    int getRandomID() throws RemoteException;

    /**
     *
     * @param quizID
     * @throws RemoteException
     */
    void getWinnerForQuiz(int quizID) throws RemoteException;

    void printQuestions(int id) throws RemoteException;

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
    void serialize() throws RemoteException;

    void serverAddsAnswers(String question, String[] answers) throws RemoteException;

    void serverAddsSetOfQuestions(int ID, ArrayList<String> newListOfQuestions) throws RemoteException;

    void serverAddstoQuizMap(String question, String[] answers) throws RemoteException;

    void setHighestScoreForQuiz(int QuizID, int score) throws RemoteException;

    void setHighestScorePlayerIDMap(int id, Player player) throws RemoteException;

    void writeQuizServer() throws RemoteException;
    
}
