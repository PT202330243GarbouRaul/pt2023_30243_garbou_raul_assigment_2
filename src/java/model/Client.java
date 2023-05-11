package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

//Creating Client entity and generating list

public class Client extends TimerTask { // runnable

    private int id;
    private int timeArrival;
    private int timeService;
    private int timeRemaining;

    public Client(int id, int timeArrival, int timeService) {
        this.id = id;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
        timeRemaining = timeService;
    }

    @Override
    public void run() {

    }

    public static ArrayList<Client> randomClientListGenerator(int nrClients, int minArrival, int maxArrival,
                                                              int minService, int maxService)
    {
        ArrayList<Client> myList = new ArrayList<>();
        for (int i = 0; i < nrClients; i++) {
            Random rand = new Random();
            Client c = new Client(i,
                    minArrival + rand.nextInt(maxArrival - minArrival),
                    minService + rand.nextInt(maxService - minService));
            myList.add(c);
        }
        System.out.println("A list of clients has been generated!\n");
        return myList;
    }

    public int getId() {
        return id;
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public int getTimeService() {
        return timeService;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", timeArrival=" + timeArrival + ", timeService=" + timeService + '}';
    }

    public String toStringCustom() {
        return "(" + id + "," + timeArrival + "," + timeService + ");";
    }
}
