/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.execution;

/**
 *
 * @author turna
 */
public class TinkerforgeModulStatusCheckExecutor {
    
    public static void main(String[] args) {
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        connectionCheckerThread.start();
        System.out.println("Dies ist ein test");
    }
    
}
