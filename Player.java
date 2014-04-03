/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizProject;

/**
 *
 * @author Esha
 */
public class Player {
    
    String playerName;
    
    int playerScore;
    
    public void setPlayerName(String name){
        this.playerName = name;
    }
    
    public void setPlayerScore(int score){
        this.playerScore = score;
    }
    
    public String getPlayerName(){
        return this.playerName;
    }
    
    public int getPlayerScore(){
        return this.playerScore;
    }


}
