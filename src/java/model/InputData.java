package model;

public class InputData {
    public int Clients;
    public int Queues;
    public int SimulationInterval;
    public int MinimumArrivalTime;
    public int MaximumArrivalTime;
    public int MinimumServiceTime;
    public int MaximumServiceTime;


    public InputData(int clients, int queues, int simulationInterval,
                     int minimumArrivalTime, int maximumArrivalTime,
                     int minimumServiceTime, int maximumServiceTime) {
        Clients = clients;
        Queues = queues;
        SimulationInterval = simulationInterval;
        MinimumArrivalTime = minimumArrivalTime;
        MaximumArrivalTime = maximumArrivalTime;
        MinimumServiceTime = minimumServiceTime;
        MaximumServiceTime = maximumServiceTime;
    }
}
