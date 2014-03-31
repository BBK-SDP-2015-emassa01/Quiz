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

    public void serverAddsAnswers(int ID, String[] answers) throws RemoteException;

    public Set<Quiz> getQuizzes() throws RemoteException;

    public Map<Integer, ArrayList<String>> getQuizMap() throws RemoteException;

    public Map<Integer, String[]> getQuestionsAndAnswers() throws RemoteException;

    public int getScore() throws RemoteException;
}