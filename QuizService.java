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

    public Object[] getCurrentQuizList() throws RemoteException;

    public Object[] getListOfQuestionsInQuiz(int id) throws RemoteException;

    public int addQuiz(String s) throws RemoteException;

    public void serverAddsSetOfQuestions(int id, ArrayList<String> questionSet) throws RemoteException;

    public String checkIfQuizIDExists(int ID) throws RemoteException;

    public void serverAddsAnswers(String question, String[] answers) throws RemoteException;

    public Set<Quiz> getQuizzes() throws RemoteException;

    public Map<Integer, ArrayList<String>> getQuizMap() throws RemoteException;

    public Map<String, String[]> getQuestionsAndAnswers() throws RemoteException;

    public void serverAddstoQuizMap(String question, String[] answers) throws RemoteException;
            
    public int getHighestScoreForQuiz(int quizID) throws RemoteException;
    
    public Map<Integer, Player> getHighestScorePlayerIDMap() throws RemoteException;
    
    public int getRandomID() throws RemoteException;
            
    public void setHighestScoreForQuiz(int quizID, int score) throws RemoteException;
    
    public void writeQuizServer()throws RemoteException;
    
    public void addAnswersToQuestions(int ID) throws RemoteException;
            
    public void printQuestions(int id) throws RemoteException;
        
    public void serialize()throws RemoteException;
    
    public QuizService deserialize() throws RemoteException;
    
    public void setHighestScorePlayerIDMap(int id, Player player) throws RemoteException ;
    
    public void getWinnerForQuiz(int quizID) throws RemoteException;
    

}
