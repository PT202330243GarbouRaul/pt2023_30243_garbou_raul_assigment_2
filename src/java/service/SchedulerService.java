package service;

import model.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Management Servers(queues)
//Create queues, dispach tasks, kill server

public class SchedulerService {

    private final ArrayList<ServerService> serverServices;
    private final FileWriter wr;
    private int maxNoServers;

    public SchedulerService(FileWriter wr, int maxNoServers) {

        ArrayList<Thread> qThreads = new ArrayList<>(maxNoServers); // initializarea
        serverServices = new ArrayList<>(maxNoServers);
        this.wr = wr;

        for (int i = 0; i < maxNoServers; i++) {

            ServerService s = new ServerService("q" + i);
            serverServices.add(s);

            Thread e = new Thread(s);
            qThreads.add(e);
            qThreads.get(i).start();
        }
    }

    public void show() {
        for (int i = 0; i < serverServices.size(); i++) {
            if (serverServices.get(i).getTasks().isEmpty()) {
                try {
                    System.out.println("Queue " + serverServices.get(i).getName() + ":" + " Closed");
                    wr.write("Queue " + serverServices.get(i).getName() + ":" + " Closed\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                List<Client> tasks = serverServices.get(i).getTasks();
                List<String> tasksAsString = tasks.stream()
                        .map(Client::toStringCustom)
                        .collect(Collectors.toList());


                try {

                    System.out.println(
                            "Queue " + serverServices.get(i).getName() + ":" + serverServices.get(i).getServiceTime() + tasksAsString);
                    wr.write("Queue " + serverServices.get(i).getName() + ":" + serverServices.get(i).getServiceTime() + tasksAsString
                            + "\n");
                    System.out.println(tasks.toString() + "\n");
                } catch (IOException e) {
                    System.out.println("Exception at i = " + i + ", servers.get(i).getTasks().peek() = "
                            + tasks.size());
                    e = new IOException("Exception at i = " + i + ", servers.get(i).getTasks().peek() = "
                            + tasks.size());
                    e.printStackTrace();

                }
            }

        }
    }

    public synchronized void kill() {
        for (ServerService serverService : serverServices) {

            serverService.setOkk(false);
            serverService.setTimerOfQueue(1);
            synchronized (serverService) {
                serverService.notify();
            }
        }
    }

    public synchronized void dispatchTask(Client t) {
        int minWaitingPeriod = 99999;

        for (ServerService serverService : serverServices) {
            if (serverService.getServiceTime().get() < minWaitingPeriod) {
                minWaitingPeriod = serverService.getServiceTime().get();

            }
        }
        for (ServerService serverService : serverServices) {
            if (serverService.getServiceTime().get() == minWaitingPeriod) {
                serverService.addTask(t);
                //System.out.println("\nTask actually added:" + t.toString());
                synchronized (serverService) {
                    serverService.notify();
                }
                break;

            }
        }

    }

}
