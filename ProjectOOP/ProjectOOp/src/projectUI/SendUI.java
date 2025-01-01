package projectUI;




import bo.SendBO;
import bean.Send;
import bo.ProductBO;
import bo.ShipperBO;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.Instant;

import java.util.Date;

public class SendUI extends Application {

    private SendBO sendBO = new SendBO(); // Giả sử đây là lớp BO đã được định nghĩa trước
    ProductBO p = new ProductBO();
    ShipperBO s = new ShipperBO();

    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo TableView
        TableView<Send> sendTableView = new TableView<>();

        // Các cột trong bảng
        TableColumn<Send, String> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));
        employeeIDColumn.setMinWidth(150);
        employeeIDColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Send, String> orderIDColumn = new TableColumn<>("Order ID");
        orderIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderID()));
        orderIDColumn.setMinWidth(150);
        orderIDColumn.setStyle("-fx-alignment: CENTER;");

        // Cột ReceiptDate
        TableColumn<Send, String> receiptDateColumn = new TableColumn<>("Receipt Date");
        receiptDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getReceiptDate() != null ? cellData.getValue().getReceiptDate().toString() : "");
        });
        receiptDateColumn.setMinWidth(150);
        receiptDateColumn.setStyle("-fx-alignment: CENTER;");

        // Cột EstimatedDate
        TableColumn<Send, String> estimatedDateColumn = new TableColumn<>("Estimated Date");
        estimatedDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getEstimatedDate() != null ? cellData.getValue().getEstimatedDate().toString() : "");
        });
        estimatedDateColumn.setMinWidth(150);
        estimatedDateColumn.setStyle("-fx-alignment: CENTER;");

        // Cột ActualDate
        TableColumn<Send, String> actualDateColumn = new TableColumn<>("Actual Date");
        actualDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getActualDate() != null ? cellData.getValue().getActualDate().toString() : "");
        });
        actualDateColumn.setMinWidth(150);
        actualDateColumn.setStyle("-fx-alignment: CENTER;");

        // Cột SendStatus
        TableColumn<Send, String> sendStatusColumn = new TableColumn<>("Send Status");
        sendStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSendStatus()));
        sendStatusColumn.setMinWidth(150);
        sendStatusColumn.setStyle("-fx-alignment: CENTER;");

        // Thêm các cột vào TableView
        sendTableView.getColumns().addAll(employeeIDColumn, orderIDColumn, receiptDateColumn, estimatedDateColumn, actualDateColumn, sendStatusColumn);

        // Tạo danh sách dữ liệu giả lập để hiển thị
        ArrayList<Send> sends = sendBO.getAllSends();  // Giả sử đây là cách lấy dữ liệu
        ObservableList<Send> sendList = FXCollections.observableArrayList(sends);
        sendTableView.setItems(sendList);

        // Lấy tất cả EmployeeID và OrderID từ cơ sở dữ liệu
        List<String> employeeIDs = s.getAllEmployeeIDs();
        List<String> orderIDs = p.getAllOrderIDs();

        // Tạo ComboBox cho EmployeeID và OrderID
        ComboBox<String> employeeIDComboBox = new ComboBox<>(FXCollections.observableArrayList(employeeIDs));
        ComboBox<String> orderIDComboBox = new ComboBox<>(FXCollections.observableArrayList(orderIDs));

        // Tạo các trường nhập liệu cho ReceiptDate, EstimatedDate, ActualDate, và SendStatus
        DatePicker receiptDatePicker = new DatePicker();
        DatePicker estimatedDatePicker = new DatePicker();
        DatePicker actualDatePicker = new DatePicker();
        TextField sendStatusField = new TextField();
        
        
     // Sự kiện khi người dùng chọn dòng trong bảng
        sendTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Cập nhật ComboBox và trường nhập liệu với thông tin của dòng được chọn
                employeeIDComboBox.setValue(newValue.getEmployeeID());
                orderIDComboBox.setValue(newValue.getOrderID());

                // Chuyển đổi java.sql.Date thành java.util.Date và sau đó thành LocalDate
                if (newValue.getReceiptDate() != null) {
                    Date receiptDate = new Date(newValue.getReceiptDate().getTime());  // Chuyển java.sql.Date thành java.util.Date
                    LocalDate localReceiptDate = receiptDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Chuyển java.util.Date thành LocalDate
                    receiptDatePicker.setValue(localReceiptDate);
                }
                if (newValue.getEstimatedDate() != null) {
                    Date estimatedDate = new Date(newValue.getEstimatedDate().getTime()); // Chuyển java.sql.Date thành java.util.Date
                    LocalDate localEstimatedDate = estimatedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Chuyển java.util.Date thành LocalDate
                    estimatedDatePicker.setValue(localEstimatedDate);
                }
                if (newValue.getActualDate() != null) {
                    Date actualDate = new Date(newValue.getActualDate().getTime()); // Chuyển java.sql.Date thành java.util.Date
                    LocalDate localActualDate = actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Chuyển java.util.Date thành LocalDate
                    actualDatePicker.setValue(localActualDate);
                }

                sendStatusField.setText(newValue.getSendStatus());
            }
        });

        // Nút chức năng
        Button addSendButton = new Button("Thêm giao hàng mới");
        addSendButton.setOnAction(e -> {
            // Kiểm tra nếu tất cả các trường nhập liệu đều hợp lệ
            if (employeeIDComboBox.getValue() == null || orderIDComboBox.getValue() == null || 
                receiptDatePicker.getValue() == null || estimatedDatePicker.getValue() == null || 
                actualDatePicker.getValue() == null || sendStatusField.getText().isEmpty()) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ thông tin trước khi thêm giao hàng!");
                alert.showAndWait();
                return;  // Dừng thực hiện nếu thiếu thông tin
            }

            // Tạo đối tượng Send mới từ dữ liệu người dùng nhập
            Send newSend = new Send(
                employeeIDComboBox.getValue(),  // Lấy giá trị từ ComboBox
                orderIDComboBox.getValue(),     // Lấy giá trị từ ComboBox
                java.sql.Date.valueOf(receiptDatePicker.getValue()),  // Lấy giá trị từ DatePicker
                java.sql.Date.valueOf(estimatedDatePicker.getValue()),  // Lấy giá trị từ DatePicker
                java.sql.Date.valueOf(actualDatePicker.getValue()),  // Lấy giá trị từ DatePicker
                sendStatusField.getText()  // Lấy giá trị từ TextField
            );

            // Gọi phương thức addSend của BO để thêm hoặc cập nhật giao hàng
           
            try {
				if(sendBO.addSend(newSend)==false) {
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           

            // Cập nhật lại bảng giao hàng (sendTableView)
            sendTableView.getItems().add(newSend);

            // Thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Đã thêm giao hàng thành công!");
            alert.showAndWait();
            
        });
      


        Button updateSendButton = new Button("Cập nhật giao hàng");
        updateSendButton.setOnAction(e -> {
            // Kiểm tra nếu có dòng được chọn trong bảng
            if (!sendTableView.getSelectionModel().isEmpty()) {
                // Lấy đối tượng Send đã chọn
                Send selectedSend = sendTableView.getSelectionModel().getSelectedItem();

                // Kiểm tra các giá trị nhập vào
                String newSendStatus = sendStatusField.getText();
                if (newSendStatus == null || newSendStatus.isEmpty()) {
                    // Nếu sendStatus trống, hiển thị thông báo lỗi
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cảnh báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Trạng thái giao hàng không được để trống!");
                    alert.showAndWait();
                    return;  // Không tiếp tục nếu dữ liệu không hợp lệ
                }

                // Cập nhật sendStatus từ TextField
                selectedSend.setSendStatus(newSendStatus);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Đã cập nhật giao hàng thành công!");
                alert.showAndWait();

                // Cập nhật các giá trị khác nếu cần
                // selectedSend.setReceiptDate(java.sql.Date.valueOf(receiptDatePicker.getValue()));
                // selectedSend.setEstimatedDate(java.sql.Date.valueOf(estimatedDatePicker.getValue()));
                // selectedSend.setActualDate(java.sql.Date.valueOf(actualDatePicker.getValue()));

                // Gọi phương thức cập nhật của BO
                sendBO.updateSend(selectedSend);
            
                // Làm mới bảng sau khi cập nhật
                sendTableView.refresh();
            } else {
                // Nếu không có dòng nào được chọn
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn giao hàng cần cập nhật!");
                alert.showAndWait();
            }
        });


        Button deleteSendButton = new Button("Xóa giao hàng");
        deleteSendButton.setOnAction(e -> {
            // Xóa giao hàng (Giả lập chức năng xóa)
            if (!sendTableView.getSelectionModel().isEmpty()) {
                Send selectedSend = sendTableView.getSelectionModel().getSelectedItem();
                sendBO.deleteSend(selectedSend.getEmployeeID(), selectedSend.getOrderID());
                sendTableView.getItems().remove(selectedSend); // Cập nhật bảng
            }
        });

        // Tạo GridPane để sắp xếp các thành phần
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(new Label("Employee ID:"), 0, 0);
        gridPane.add(employeeIDComboBox, 1, 0);  // Thêm ComboBox vào GridPane
        gridPane.add(new Label("Order ID:"), 0, 1);
        gridPane.add(orderIDComboBox, 1, 1);    // Thêm ComboBox vào GridPane
        gridPane.add(new Label("Receipt Date:"), 0, 2);
        gridPane.add(receiptDatePicker, 1, 2);  // Thêm DatePicker vào GridPane
        gridPane.add(new Label("Estimated Date:"), 0, 3);
        gridPane.add(estimatedDatePicker, 1, 3); // Thêm DatePicker vào GridPane
        gridPane.add(new Label("Actual Date:"), 0, 4);
        gridPane.add(actualDatePicker, 1, 4);   // Thêm DatePicker vào GridPane
        gridPane.add(new Label("Send Status:"), 0, 5);
        gridPane.add(sendStatusField, 1, 5);    // Thêm TextField vào GridPane

        // Thêm các nút vào GridPane
        gridPane.add(addSendButton, 0, 6);
        gridPane.add(updateSendButton, 1, 6);
        gridPane.add(deleteSendButton, 0, 7);

        // Sắp xếp giao diện
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(sendTableView, gridPane);

        // Khung cảnh
        Scene scene = new Scene(vbox, 900, 600);  // Đặt kích thước cửa sổ
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quản Lý Giao Hàng");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
