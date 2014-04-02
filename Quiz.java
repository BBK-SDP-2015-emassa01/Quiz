/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Esha
 */
public class Quiz implements Serializable {

    private int quizID; //check permissions on all classes for fields and methods before submission
    private String quizName;
    private String[] quesAnsStringArray = new String[6];
    private int highestScore = 0;

    public void setQuizName(String nameOfQuiz) {
        this.quizName = nameOfQuiz;
    }

    public String getQuizName() {
        return this.quizName;
    }

    public void setQuizID(int idOfQuiz) {
        this.quizID = idOfQuiz;
    }

    public int getQuizID() {
        return this.quizID;
    }
    
    public void setHighestScore(int score) {
        this.highestScore = score;
    }

    public int getHighestScore() {
        return this.highestScore;
    }

    public String[] getQuesAns() {
        return this.quesAnsStringArray;
    }

    public void setQuesAns(String[] QA) {
        this.quesAnsStringArray = QA;
    }
}
