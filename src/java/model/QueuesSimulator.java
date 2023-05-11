package model;

import service.SimulationManager;

import java.io.FileWriter;
import java.io.IOException;

//Working with files class

public class QueuesSimulator {

    private static int nrOfClients;
    private static int nrOfQueues;

    private static int tMaxSimulation;

    private static int maxArrivalTime;
    private static int minArrivalTime;

    private static int maxFrontWaitTime;
    private static int minFrontWaitTime;

//    public QueuesSimulator(String string1, String string2) throws IOException {
//
//        FileReader fr = null;
//        FileWriter wr = null;
//
//        try {
//
//            fr = new FileReader(string1);
//            wr = new FileWriter(string2);
//
//            citireFisier(fr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        service.SimulationManager mySimulation = new service.SimulationManager(wr, nrOfClients, nrOfQueues, tMaxSimulation,
//                maxArrivalTime, minArrivalTime, maxFrontWaitTime, minFrontWaitTime);
//        Thread sim = new Thread(mySimulation);
//        sim.start();
//
//        Objects.requireNonNull(fr).close();
//
//    }

    public QueuesSimulator(String string1, int Clients, int Queues, int SimulationInterval, int MinimumArrivalTime, int MaximumArrivalTime, int MinimumServiceTime, int MaximumServiceTime) throws IOException {

        FileWriter wr = null;

        try {

            wr = new FileWriter(string1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        SimulationManager mySimulation = new SimulationManager(wr, Clients, Queues, SimulationInterval,
                MaximumArrivalTime, MinimumArrivalTime, MaximumServiceTime, MinimumServiceTime);
        Thread sim = new Thread(mySimulation);
        sim.start();

    }

    public QueuesSimulator() {
    }

//    private static void citireFisier(FileReader fr) throws IOException {
//
//        int c;
//        c = fr.read();
//        while (!(c >= '0' && c <= '9')) {
//            c = fr.read();
//        }
//
//        int i = 1;
//        int cop;
//        nrOfClients = (c - 48);
//        nrOfQueues = 0;
//
//        cop = c;
//        while ((c = fr.read()) != -1) {
//
//            if (c >= '0' && c <= '9') {
//                if (i == 1) {
//                    nrOfClients = nrOfClients * 10 + (c - 48);
//                } else if (i == 2) {
//                    nrOfQueues = nrOfQueues * 10 + (c - 48);
//                } else if (i == 3) {
//                    tMaxSimulation = tMaxSimulation * 10 + (c - 48);
//                } else if (i == 4) {
//                    minArrivalTime = minArrivalTime * 10 + (c - 48);
//                } else if (i == 5) {
//                    maxArrivalTime = maxArrivalTime * 10 + (c - 48);
//                } else if (i == 6) {
//                    minFrontWaitTime = minFrontWaitTime * 10 + (c - 48);
//                } else if (i == 7) {
//                    maxFrontWaitTime = maxFrontWaitTime * 10 + (c - 48);
//                }
//            }
//
//            if (!(c >= '0' && c <= '9') && (cop >= '0' && cop <= '9')) {
//                i++;
//            }
//            cop = c;
//
//        }
//
//    }

}
