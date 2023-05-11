import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.InputData;
import model.QueuesSimulator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class DesignUI {
    public Button citireinput;
    public Button output;
    @FXML
    private TextField Clients;
    @FXML
    private TextField Queues;
    @FXML
    private TextField SimulationInterval;
    @FXML
    private TextField MinimumArrivalTime;
    @FXML
    private TextField MaximumArrivalTime;
    @FXML
    private TextField MinimumServiceTime;
    @FXML
    private TextField MaximumServiceTime;
    @FXML
    private TextArea Output;

    private String path;

    public InputData parseData() {
        String ClientsString = Clients.getText();
        String QueuesString = Queues.getText();
        String SimulationIntervalString = SimulationInterval.getText();
        String MinimumArrivalTimeString = MinimumArrivalTime.getText();
        String MaximumArrivalTimeString = MaximumArrivalTime.getText();
        String MinimumServiceTimeString = MinimumServiceTime.getText();
        String MaximumServiceTimeString = MaximumServiceTime.getText();

        return new InputData(Integer.parseInt(ClientsString), Integer.parseInt(QueuesString), Integer.parseInt(SimulationIntervalString), Integer.parseInt(MinimumArrivalTimeString), Integer.parseInt(MaximumArrivalTimeString), Integer.parseInt(MinimumServiceTimeString), Integer.parseInt(MaximumServiceTimeString));
    }

    public void citireInput() throws IOException {

        try {
            File f = new File("output.txt");
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else {
                System.out.println("File already exists.");
            }
            this.path = f.getAbsolutePath();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }

        this.Output.setText("");

        if (path == null || Clients == null || Queues == null || SimulationInterval == null || MinimumArrivalTime == null || MaximumArrivalTime == null || MinimumServiceTime == null || MaximumServiceTime == null) {

            this.Output.setText("No input data should be null!");

            return;
        }

        InputData inputData = this.parseData();

        updateFromFile();

        new QueuesSimulator(path, inputData.Clients, inputData.Queues, inputData.SimulationInterval, inputData.MinimumArrivalTime, inputData.MaximumArrivalTime, inputData.MinimumServiceTime, inputData.MaximumServiceTime);

    }

    private void updateFromFile() {

        Runnable thread1 = () -> {
            try {

                String content;
                FileTime lastModifiedTime = Files.readAttributes(Path.of(path), BasicFileAttributes.class).lastModifiedTime();
                while (true) {
                    FileTime lastModifiedAsOfNow = Files.readAttributes(Path.of(path), BasicFileAttributes.class).lastModifiedTime();

                    if (lastModifiedAsOfNow.compareTo(lastModifiedTime) > 0) {

                        content = Files.readString(Paths.get(path));
                        Output.setText(content);
                        Output.setScrollTop(Double.MAX_VALUE);
                        if (content.contains("Average waiting time:")) {
                            break;
                        }
                        lastModifiedTime = lastModifiedAsOfNow;

                    }
                    Output.setScrollTop(Double.MAX_VALUE);

                }
                Output.setScrollTop(Double.MAX_VALUE);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };

        new Thread(thread1).start();

    }
}
