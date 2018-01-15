/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.execution;

import intraal.bt.config.connection.ConnectionChecker;
import intraal.bt.algo.uc1.ActivityCheck;
import intraal.bt.algo.uc1.BedActivity;
import intraal.bt.algo.uc1.NightLightUC1;
import intraal.bt.algo.uc1.OnePersonLokation;
import intraal.bt.sensor.room.execution.StartBadModul;
import intraal.bt.sensor.room.execution.StartEingangModul;
import intraal.bt.sensor.room.execution.StartKücheModul;
import intraal.bt.sensor.room.execution.StartBettModul;
import intraal.bt.sensor.room.execution.StartSchlafzimmerModul;
import intraal.bt.sensor.room.execution.StartWohnzimmerModul;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class IntraalExecutor implements Runnable{
    
    // java -jar "C:\Users\turna\Documents\NetBeansProjects\Intraal-BT\dist\Intraal-BT.jar"
    
   
    private void startSensors(){
        StartBettModul bett = new StartBettModul();
        StartBadModul bad = new StartBadModul();
        StartWohnzimmerModul wohnzimmer = new StartWohnzimmerModul();
        StartEingangModul eingang = new StartEingangModul();
        StartKücheModul küche = new StartKücheModul();
        StartSchlafzimmerModul schlafzimmer = new StartSchlafzimmerModul();
        
//        wohnzimmer.startWohnzimmerModul();
  //      eingang.startEingangModul();
    //    schlafzimmer.startSchlafzimmerModul();
      //  küche.startKücheModul();
  //      bad.startBadModul();
        bett.startBettModul();
    }
    
    private void startAlgo() throws Exception{
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        OnePersonLokation opl = new OnePersonLokation();
     //   NightLightUC1 nl = new NightLightUC1();
    //    BedActivity ba = new BedActivity();
    //    ActivityCheck ac = new ActivityCheck();
        
        connectionCheckerThread.start();
       opl.locationOfPerson();
    //   nl.runNightLight();
    //    ba.bedActivity();
        
        
    }
    
    @Override
    public void run() {
        startSensors();
        
    }
    
    public static void main(String[] args) {
        IntraalExecutor IntraalSoftware = new IntraalExecutor();
        IntraalSoftware.run();
        System.out.println("INTRAAL Software Läuft jetzt!");
    }
}
