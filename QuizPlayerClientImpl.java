/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizProject;

import java.rmi.RemoteException;

/**
 *
 * @author Esha
 */
public interface QuizPlayerClientImpl {

    int getHighestScoreForPlayer(int quizID) throws RemoteException;

    void keepLooping() throws RemoteException;

    void launch() throws RemoteException;

    int menu() throws RemoteException;

    /**
     *
     * @param selectedQuizID
     * @throws RemoteException
     */
    void playSelectedQuiz(int selectedQuizID) throws RemoteException;

    void printOutQuizList() throws RemoteException;

    int selectQuizToPlay();

    void terminateQuiz();
    
}
