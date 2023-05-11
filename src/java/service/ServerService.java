package service;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//Behavior of each queue

public class ServerService implements Runnable {
    private final BlockingQueue<Client> tasks;
    private final AtomicInteger serviceTime;
    private final String name;
    private Thread backupThread;
    private boolean okk;
    private int timerOfQueue;

    public ServerService(String name) {

        tasks = new LinkedBlockingDeque<>(1000);
        serviceTime = new AtomicInteger(0);

        this.name = name;
        timerOfQueue = 0;
    }

    public synchronized void addTask(Client newTask) {
        if (newTask.getTimeArrival() >= timerOfQueue) {
            //System.out.println("\nTask added:" + newTask.toString());
            tasks.add(newTask);
            serviceTime.set(serviceTime.get() + newTask.getTimeService());
        }
    }

    @Override
    public void run() {

        okk = true;

        while (okk) {

            Client clientTask;

            clientTask = tasks.peek();

            if (clientTask == null) {

                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }


            if (!okk)
                break;
            clientTask = tasks.peek();

            //System.out.println("\nRemaining time:" + clientTask.getTimeRemaining());
            if (clientTask.getTimeRemaining() == 0) {
                try {
                    Client x = tasks.take();
                    //System.out.println("\nTask OUT:");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            clientTask.setTimeRemaining(clientTask.getTimeRemaining() - 1);
            //else {
            //service.SimulationManager.averageWaitingTime++;
            ///Work dammit
            //}//why wont you? :(((((
            serviceTime.set(serviceTime.get() - 1);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (timerOfQueue != 0) {
                okk = false;
                break;

            }

        }
        //System.out.println("\nAverage added service time:" + service.SimulationManager.averageWaitingTime);
    }


    public List<Client> getTasks() {
        return new ArrayList<>(tasks);
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public String getName() {
        return name;
    }

    public void setOkk(boolean okk) {
        this.okk = okk;
    }


    public void setTimerOfQueue(int timerOfQueue) {
        this.timerOfQueue = timerOfQueue;
    }

}
