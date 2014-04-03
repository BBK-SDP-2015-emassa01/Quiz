/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Esha
 */
public class Serialize implements Serializable{
    private String fileName;
    
    public void serialize(QuizServer quizData){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(quizData);
            oos.close();
            fos.close();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Object deserialize(){
        QuizServer quizServer = null;
        
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            quizServer= (QuizServer) ois.readObject();
            
            ois.close();
            fis.close();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
             ex.printStackTrace();
        } 
        return quizServer;
    }
    
    public boolean quizDataExists(){
        return new File(fileName).exists();
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
