package loan;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanController implements Initializable {

    public Button btnCompute;
    @FXML
    private TextArea taMessage;

    @FXML
    private TextField txtAnnualRate;

    @FXML
    private TextField txtLoanAmount;

    @FXML
    private TextField txtNoYears;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCompute.setOnAction(e -> {
            Task<Void> task = new Task<Void> () {

                @Override
                protected Void call() throws Exception {
                    double monthlyInterestRate = Double.parseDouble(txtAnnualRate.getText()) / 1200;

                    double monthlyPayment = Double.parseDouble(txtLoanAmount.getText()) * monthlyInterestRate / (
                            1 - 1 / Math.pow(1 + monthlyInterestRate, Integer.parseInt(txtNoYears.getText()) * 12)
                    );
                    double totalPayment = monthlyPayment * Integer.parseInt(txtNoYears.getText()) * 12;

                    Platform.runLater(() -> {
                        taMessage.appendText("The monthly payment is " + "Kshs." + (int)(monthlyPayment * 100.0) / 100.0 + "\n");
                    });

                    Platform.runLater(() -> {
                        taMessage.appendText("The total payment is " + "Kshs." +(int)(totalPayment * 100) / 100.0);
                    });
                    return null;
                }
            };
            new Thread(task).start();

        });

    }
}
