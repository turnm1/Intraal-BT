/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.eingang;

import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Turna
 */
public class eingangTemperatur implements MqttCallback {

    BrickletTemperature tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "t73";
    private final String ROOM = "Eingang";
    private final String MODUL = "Temperatur";
    /////////////////////////////////////////////////////

    public eingangTemperatur() {

    }

    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletTemperature(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgEingangIP(), con.getTgPort());
        /////////////////////////////////////////////////////
    }

    private void connectMQTT() throws Exception {
        c = new MQTTCommunication();
        p = new MQTTParameters();
        p.setClientID(con.getClientIDTopic(UID));
        p.setUserName(con.getUserName());
        p.setPassword(con.getPassword());
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getBrokerConnection()));
        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }
    
    public void getTemp() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        // Add temperature reached listener (parameter has unit °C/100)
        tinkerforg.addTemperatureListener((short temperature) -> {
            int toHigh = 25 * 100;
            int toLow = 21 * 100;
            
            if (temperature > toHigh) {
                message.setPayload((temperature / 100.0 + " Grad => Hoch").getBytes());
                message.setRetained(true);
                message.setQos(0);
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
            } else if (temperature <= toLow) {
                message.setPayload((temperature / 100.0 + " Grad => Tief").getBytes());
                message.setRetained(true);
                message.setQos(0);
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
            }
        });

        // Set period for temperature callback to 1s (1000ms)
        // Note: The temperature callback is only called every second
        //       if the temperature has changed since the last call!
        tinkerforg.setTemperatureCallbackPeriod(10000);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ",  OK! =========== ");
    }

}