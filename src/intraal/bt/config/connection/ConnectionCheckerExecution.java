/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection;

import intraal.bt.config.connection.ConnectionTest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class ConnectionCheckerExecution implements Runnable {

    @Override
    public void run() {
        while(true) {
            ConnectionTest test = new ConnectionTest();
            try {
                test.testTinkerforgeModuls(2500);               
            } catch (Exception ex) {
                Logger.getLogger(ConnectionCheckerExecution.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                 Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionCheckerExecution.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        ConnectionCheckerExecution c = new ConnectionCheckerExecution();
        c.run();
    }
}
