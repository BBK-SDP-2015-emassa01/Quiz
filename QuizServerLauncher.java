/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuizProject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Esha
 */
public class QuizServerLauncher {

    public static void main(String[] args) {
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
            QuizServer server = new QuizServer();
            // 4. Register (bind) the server object on the registy.
            // The registry may be on a different machine
            String registryHost = "//localhost/";
            String serviceName = "quiz";
            Naming.rebind(registryHost + serviceName, server);
        } catch (UnmarshalException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {

            ex.printStackTrace();
        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }

}
