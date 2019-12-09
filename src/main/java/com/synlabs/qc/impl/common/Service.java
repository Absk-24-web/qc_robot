package com.synlabs.qc.impl.common;

import java.io.IOException;
import java.net.Socket;

public class Service {


    public Socket client;

    public Service() {

    }

    public void socket() throws IOException {
                //For robot
        //setClient(new Socket("100.100.100.2", 3001));

                //For localhost
        setClient(new Socket("localhost", 3001));

        System.out.println("Just connected to " + getClient().getRemoteSocketAddress());

    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
}
