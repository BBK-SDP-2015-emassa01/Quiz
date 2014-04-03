/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Esha
 */
public class QuizServerLauncher {
    
    QuizService serverQuiz;
    QuizSetupClient client;
    QuizPlayerClient player;
    //private Serialize serializers;
    
    String serviceName = "quiz";
    
    public QuizServerLauncher() throws RemoteException{
        // ERROR serverQuiz.deserialize();
    }

    public static void main(String[] args) throws RemoteException {
        QuizServerLauncher test = new QuizServerLauncher();
        test.launch();
    }

    private void launch() {
// 1. If there is no security manager, start one
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new RMISecurityManager());
//        }
        try {
            // 2. Create the registry if there is not one
            LocateRegistry.createRegistry(1099);
            // 3. Create the server object
            QuizServiceImpl server = new QuizServiceImpl();
            // 4. Register (bind) the server object on the registy.
            // The registry may be on a different machine
            String registryHost = "//localhost/";
            Naming.rebind(registryHost + serviceName, server);
        } catch (UnmarshalException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {

            ex.printStackTrace();
        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }
    
    public void close() throws RemoteException{
        serverQuiz.serialize();
        Registry registry = LocateRegistry.getRegistry(1099);
        try{
            registry.unbind(serviceName);
            UnicastRemoteObject.unexportObject(client.service, true);
            UnicastRemoteObject.unexportObject(player.service, true);
        } catch(NotBoundException ex){
            throw new RemoteException("Could not un-register, quitting anyway...", ex);
        }
    }

}
