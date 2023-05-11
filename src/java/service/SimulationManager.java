package service;

import model.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//Overall Simulation control
//Used for calling generator and getting statistics

public class SimulationManager implements Runnable {

    public static double averageWaitingTime;
    private static int tMaxSimulation;
    private final SchedulerService schedulerService;
    private final ArrayList<Client> waitingTasks;
    //int peak[];
    private final FileWriter wr;
    int count = 0;
    int aux_count;
    private double averageServiceTime;

    public SimulationManager(FileWriter wr, int nrOfClients, int nrOfQueues, int tMaxSimulation, int maxArrivalTime,
                             int minArrivalTime, int maxFrontWaitTime, int minFrontWaitTime) {
        //peak = new int[tMaxSimulation];
        SimulationManager.tMaxSimulation = tMaxSimulation;
        this.wr = wr;

        averageServiceTime = 0;
        int count = 0;

        waitingTasks = Client.randomClientListGenerator(nrOfClients, minArrivalTime, maxArrivalTime, minFrontWaitTime,
                maxFrontWaitTime); // clienti random
        //Sorting
        for (int i = 0; i < waitingTasks.size() - 1; i++) {
            for (int j = i + 1; j < waitingTasks.size(); j++) {

                if (waitingTasks.get(i).getTimeArrival() > waitingTasks.get(j).getTimeArrival()) {
                    Client aux;
                    aux = waitingTasks.get(i);
                    waitingTasks.set(i, waitingTasks.get(j));
                    waitingTasks.set(j, aux);
                }
            }
        }
        for (Client e : waitingTasks) {
            averageServiceTime += e.getTimeService();
            averageWaitingTime += (e.getTimeRemaining() - e.getTimeArrival());
            count++;
        }
        System.out.println("\nAverage service time:" + averageServiceTime);
        averageServiceTime = averageServiceTime / count;
        aux_count = count;

        schedulerService = new SchedulerService(wr, nrOfQueues);

    }

    @Override
    public void run() {
        int currentTime = 0;
        boolean ok = true;
        while (ok) {
            try {

                System.out.println();
                wr.write("\n");
                System.out.println("\nTime " + currentTime);
                wr.write("\nTime " + currentTime + "\n");
                System.out.print("Waiting clients: ");
                wr.write("Waiting clients: ");

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < waitingTasks.size(); i++) {
                if (currentTime == waitingTasks.get(i).getTimeArrival()) {
                    //peak[generatedTasks.get(count).getTimeArrival()]++;
                    schedulerService.dispatchTask(waitingTasks.get(i));
                    waitingTasks.remove(waitingTasks.get(i));
                    if (!(waitingTasks.isEmpty())) {
                        i--;
                    } else
                        break;
                }

            }
            try {
                for (Client generatedTask : waitingTasks) {
                    System.out.print("(" + generatedTask.getId() + "," + generatedTask.getTimeArrival()
                            + "," + generatedTask.getTimeService() + "); ");
                    wr.write("(" + generatedTask.getId() + "," + generatedTask.getTimeArrival() + ","
                            + generatedTask.getTimeService() + "); ");
                }
                System.out.println();
                wr.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            schedulerService.show();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentTime++;
            if (currentTime >= tMaxSimulation + 1) {
                ok = false;
                schedulerService.kill();
            }
        }
        /*int max = 0;
        for(int i=0;i<=peak.length;i++)
        {

        }
        for(int i=0;i<=peak.length;i++)
        {
            if(peak[i]>max)
                max = peak[i];
        }*/

        //System.out.println("\nPeak time:" + max);
        averageWaitingTime = averageWaitingTime / aux_count;
        System.out.println("\nAverage service time:" + averageServiceTime);
        System.out.println("\nAverage waiting time:" + averageWaitingTime);
        try {
            wr.write("\nAverage service time:" + averageServiceTime);
            wr.write("\nAverage waiting time:" + averageWaitingTime);
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


//Strategie de sincronizare in care verific timp asteptare la toate cozile si assignez unde waiting time minim
//Coada are dimensiune mare si sa le introduc pe toate