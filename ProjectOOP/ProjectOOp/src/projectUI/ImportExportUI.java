package projectUI;

import bo.ImportExportBO;
import bo.ProductBO;
import bo.WarehouseBO;
import bean.ImportExport;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class ImportExportUI extends Application {
    private ImportExportBO importExportBO = new ImportExportBO();
    private TableView<ImportExport> tableView;
    private ObservableList<ImportExport> importExportList;

    // Khai báo các trường nhập liệu ở cấp lớp
    private ComboBox<String> orderIDComboBox;
    private ComboBox<String> warehouseIDComboBox;
    private TextField inboundDateField;
    private TextField outboundDateField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quản Lý Nhập/Xuất Kho");

        // TableView để hiển thị danh sách bản ghi
        tableView = new TableView<>();
        setupTableView();

        // Form nhập liệu
        VBox form = createForm();

        // Layout chính
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(tableView);
        mainLayout.setRight(form);
        mainLayout.setPadding(new Insets(10));

        // Tải dữ liệu từ cơ sở dữ liệu
        loadTableData();

        // Tạo scene và hiển thị
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTableView() {
        TableColumn<ImportExport, String> orderIDCol = new TableColumn<>("OrderID");
        orderIDCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrderID()));

        TableColumn<ImportExport, String> warehouseIDCol = new TableColumn<>("WarehouseID");
        warehouseIDCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouseID()));

        TableColumn<ImportExport, String> inboundDateCol = new TableColumn<>("Inbound Date");
        inboundDateCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getInboundDate() != null ? data.getValue().getInboundDate().toString() : ""));

        TableColumn<ImportExport, String> outboundDateCol = new TableColumn<>("Outbound Date");
        outboundDateCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getOutboundDate() != null ? data.getValue().getOutboundDate().toString() : ""));

        tableView.getColumns().addAll(orderIDCol, warehouseIDCol, inboundDateCol, outboundDateCol);

        // Bắt sự kiện khi nhấn vào một hàng trong bảng
        tableView.setOnMouseClicked(event -> {
            ImportExport selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Điền dữ liệu vào các ô nhập liệu
                orderIDComboBox.setValue(selected.getOrderID());
                warehouseIDComboBox.setValue(selected.getWarehouseID());
                if (selected.getInboundDate() != null) {
                    inboundDateField.setText(selected.getInboundDate().toString());
                }
                if (selected.getOutboundDate() != null) {
                    outboundDateField.setText(selected.getOutboundDate().toString());
                }
            }
        });
    }

    ProductBO p = new ProductBO();
    WarehouseBO w = new WarehouseBO();

    private VBox createForm() {
        Label orderIDLabel = new Label("OrderID:");
        orderIDComboBox = new ComboBox<>();
        orderIDComboBox.setItems(FXCollections.observableArrayList(p.getAllOrderIDs()));

        Label warehouseIDLabel = new Label("WarehouseID:");
        warehouseIDComboBox = new ComboBox<>();
        warehouseIDComboBox.setItems(FXCollections.observableArrayList(w.getAllWarehouseIDs()));

        Label inboundDateLabel = new Label("Inbound Date (yyyy-MM-dd):");
        inboundDateField = new TextField();

        Label outboundDateLabel = new Label("Outbound Date (yyyy-MM-dd):");
        outboundDateField = new TextField();

    
        Button addButton = new Button("Thêm");
        addButton.setOnAction(e -> {
        	// Kiểm tra sự tồn tại của bản ghi trùng lặp
        	String orderID = orderIDComboBox.getValue();
        	String warehouseID = warehouseIDComboBox.getValue();

        	if (orderID == null || warehouseID == null) {
        	    showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng chọn OrderID và WarehouseID.");
        	    return;
        	}
        	
        	ImportExportBO i = new ImportExportBO();

        	// Kiểm tra xem bản ghi đã tồn tại trong cơ sở dữ liệu hay chưa
        	if (i.recordExists(orderID, warehouseID)) {
        	    showAlert(Alert.AlertType.WARNING, "Lỗi", "Bản ghi với OrderID và WarehouseID này đã tồn tại.");
        	    return;
        	}

        	try {
        	    // Kiểm tra ngày nhập hợp lệ
        	    Date inboundDate = Date.valueOf(inboundDateField.getText());
        	    ImportExport newRecord = new ImportExport(orderID, warehouseID, inboundDate, null);
        	    boolean success = importExportBO.addImport(newRecord);

        	    if (success) {
        	        importExportList.add(newRecord);
        	        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm bản ghi nhập kho!");
        	        
        	        // Reset lại các ô nhập liệu
        	        orderIDComboBox.getSelectionModel().clearSelection();
        	        warehouseIDComboBox.getSelectionModel().clearSelection();
        	        inboundDateField.clear();
        	        outboundDateField.clear();
        	    } else {
        	        showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể thêm bản ghi.");
        	    }
        	} catch (IllegalArgumentException ex) {
        	    showAlert(Alert.AlertType.ERROR, "Lỗi", "Ngày nhập không hợp lệ.");
        	}
        });

        Button updateButton = new Button("Cập nhật xuất kho");
        updateButton.setOnAction(e -> {
            String orderID = orderIDComboBox.getValue();
            String warehouseID = warehouseIDComboBox.getValue();
            String outboundDateStr = outboundDateField.getText();

            if (orderID == null || warehouseID == null) {
                showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng chọn OrderID và WarehouseID.");
                return;
            }

            try {
                Date outboundDate = Date.valueOf(outboundDateStr);
                ImportExport updatedRecord = new ImportExport(orderID, warehouseID, null, outboundDate);
                boolean success = importExportBO.updateExport(updatedRecord);

                if (success) {
                    loadTableData(); // Tải lại dữ liệu
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật thông tin xuất kho!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể cập nhật bản ghi.");
                }
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Ngày xuất không hợp lệ.");
            }
        });

        Button deleteButton = new Button("Xóa");
        deleteButton.setOnAction(e -> {
            ImportExport selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean success = importExportBO.deleteImportExport(selected.getOrderID(), selected.getWarehouseID());
                if (success) {
                	loadTableData(); // Tải lại dữ liệu
                    importExportList.remove(selected);
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã xóa bản ghi!");
                    // Reset lại các ô nhập liệu
        	        orderIDComboBox.getSelectionModel().clearSelection();
        	        warehouseIDComboBox.getSelectionModel().clearSelection();
        	        inboundDateField.clear();
        	        outboundDateField.clear();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể xóa bản ghi.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Chú ý", "Vui lòng chọn bản ghi cần xóa.");
            }
        });

        VBox form = new VBox(10, orderIDLabel, orderIDComboBox, warehouseIDLabel, warehouseIDComboBox,
                inboundDateLabel, inboundDateField, outboundDateLabel, outboundDateField, addButton, updateButton, deleteButton);
        form.setPadding(new Insets(10));
        return form;
    }

    private void loadTableData() {
        ArrayList<ImportExport> records = importExportBO.getAllImportExports();
        if (records != null) {
            importExportList = FXCollections.observableArrayList(records);
            tableView.setItems(importExportList);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
