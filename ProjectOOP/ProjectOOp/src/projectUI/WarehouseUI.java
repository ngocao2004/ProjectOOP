package projectUI;

import dao.WarehouseDAO;
import bo.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import bean.Warehouse;

import java.util.List;  // Import List class
import java.util.Optional;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;






public class WarehouseUI extends Application {

    private WarehouseDAO warehouseDAO = new WarehouseDAO();
    private TableView<Warehouse> tableView;
    private TextField warehouseIDField, wareNameField, cityField, districtField, wardField, addressField;
    private ObservableList<Warehouse> data;
    private Label statusLabel;

    public static void main(String[] args) {
        launch(args);
    }
  
    private TextArea warehouseDisplayArea;
    TextField searchField = new TextField();

    private TableView<Warehouse> warehouseTable;
    ComboBox<String> searchTypeComboBox = new ComboBox<>();



    @Override
    public void start(Stage primaryStage) {
        // Thiết lập cửa sổ chính
        primaryStage.setTitle("Quản lý kho hàng");
        primaryStage.setWidth(700);
        primaryStage.setHeight(600);

        // Tải hình nền
        Image backgroundImage = new Image("file:C:\\path\\to\\your\\image.jpg");

        // Tạo ImageView để hiển thị hình nền
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(400);
        backgroundImageView.setFitHeight(300);

        // Tạo TableView và các cột
        warehouseTable = new TableView<>();

        // Cột "ID Kho"
        TableColumn<Warehouse, String> idColumn = new TableColumn<>("ID Kho");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWarehouseID()));
        idColumn.setPrefWidth(100);  // Đặt chiều rộng cột cố định
        idColumn.setStyle("-fx-alignment: CENTER;");

        // Cột "Tên Kho"
        TableColumn<Warehouse, String> nameColumn = new TableColumn<>("Tên Kho");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWareName()));
        nameColumn.setPrefWidth(200);  // Đặt chiều rộng cột cố định
        nameColumn.setStyle("-fx-alignment: CENTER;");

        // Cột "Địa chỉ"
        TableColumn<Warehouse, String> addressColumn = new TableColumn<>("Địa chỉ");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        addressColumn.setPrefWidth(300);  // Đặt chiều rộng cột cố định
        addressColumn.setStyle("-fx-alignment: CENTER;");

        // Thêm các cột vào TableView
        warehouseTable.getColumns().addAll(idColumn, nameColumn, addressColumn);

        // Tạo các nút chức năng
        Button btnAdd = new Button("Thêm kho");
        Button btnDelete = new Button("Xóa kho");
        Button btnSearch = new Button("Tìm kiếm");
        Button btnShowAllWarehouses = new Button("Hiển thị tất cả kho");

        // Thêm kiểu chữ đậm cho các nút
        btnAdd.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnDelete.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnSearch.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnShowAllWarehouses.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Đặt kích thước cho các nút
        btnAdd.setPrefWidth(150);
        btnAdd.setPrefHeight(40);

        btnDelete.setPrefWidth(150);
        btnDelete.setPrefHeight(40);

        btnSearch.setPrefWidth(150);
        btnSearch.setPrefHeight(40);

        btnShowAllWarehouses.setPrefWidth(150);
        btnShowAllWarehouses.setPrefHeight(40);

        // Tạo VBox chứa các nút menu và bố trí
        VBox menuBox = new VBox(10, btnAdd, btnDelete, btnSearch, btnShowAllWarehouses);
        menuBox.setStyle("-fx-background-color: #808000; -fx-padding: 30px;");
        menuBox.setPadding(new Insets(0, 0, 0, 50)); 

        // TextArea để hiển thị tất cả kho hàng
        warehouseDisplayArea = new TextArea();
        warehouseDisplayArea.setPrefHeight(200); 
        warehouseDisplayArea.setEditable(false);

        // Panel tìm kiếm
        searchTypeComboBox = new ComboBox<>();
        searchTypeComboBox.getItems().addAll("ID", "Tên");
        searchField = new TextField();
        HBox searchBox = new HBox(10, searchTypeComboBox, searchField);
        searchBox.setStyle("-fx-padding: 10px;");

        // Gọi hàm để tải tất cả kho khi khởi tạo
        showAllWarehouses();

        // Sự kiện cho các nút chức năng
        btnAdd.setOnAction(e -> addWarehouse());
        btnDelete.setOnAction(e -> deleteWarehouse());
        btnSearch.setOnAction(e -> showSearchWarehouse());
        btnShowAllWarehouses.setOnAction(e -> showAllWarehouses());

        // Sử dụng HBox để bố trí ảnh bên trái và menu bên phải
        HBox layout = new HBox(10, backgroundImageView, menuBox);
        layout.setStyle("-fx-background-color: #ffffff; -fx-padding: 10px;");

        // Thêm TextArea vào layout
        VBox mainLayout = new VBox(10, layout, warehouseDisplayArea); 
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void searchWarehouse() {
        // Tạo hộp thoại để người dùng nhập giá trị tìm kiếm
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm kho");
        dialog.setHeaderText("Nhập giá trị tìm kiếm");
        dialog.setContentText("Nhập ID hoặc Tên kho:");

        // Hiển thị hộp thoại và lấy giá trị người dùng nhập vào
        Optional<String> result = dialog.showAndWait();

        // Kiểm tra nếu người dùng nhập dữ liệu
        if (result.isPresent()) {
            String searchValue = result.get().trim(); // Lấy giá trị người dùng nhập vào

            // Kiểm tra nếu ô tìm kiếm rỗng
            if (searchValue.isEmpty()) {
                showErrorDialog("Vui lòng nhập giá trị tìm kiếm!");
                return;
            }

            // Thực hiện tìm kiếm theo giá trị người dùng nhập
            try {
                ArrayList<Warehouse> results = new ArrayList<>();
                // Tìm kiếm theo ID trước
                results = warehouseBO.searchWarehousesByID(searchValue);
                if (results.isEmpty()) {
                    // Nếu không tìm thấy theo ID, thử tìm theo Tên
                    results = warehouseBO.searchWarehousesByName(searchValue);
                }

                // Cập nhật bảng với kết quả tìm kiếm
                if (results.isEmpty()) {
                    // Không tìm thấy kho theo từ khóa
                    showErrorDialog("Không tìm thấy kho nào!");
                } else {
                    // Hiển thị các kho tìm được vào bảng
                    try {
                        StringBuilder warehouseDetails = new StringBuilder();
                        for (Warehouse warehouse : results) {
                            warehouseDetails.append("ID: ").append(warehouse.getWarehouseID())
                                    .append(", Tên: ").append(warehouse.getWareName())
                                    .append(", Thành phố: ").append(warehouse.getCity())
                                    .append(", Quận/Huyện: ").append(warehouse.getDistrict())
                                    .append(", Phường/Xã: ").append(warehouse.getWard())
                                    .append(", Địa chỉ: ").append(warehouse.getAddress())
                                    .append("\n");
                        }
                        warehouseDisplayArea.setText(warehouseDetails.toString()); // Hiển thị kho tìm được vào TextArea

                        // Thông báo thành công
                        showSuccessDialog("Tìm kiếm thành công! Đã tìm thấy " + results.size() + " kho.");
                    } catch (Exception e) {
                        showErrorDialog("Lỗi khi hiển thị kho: " + e.getMessage());
                    }
                }

            } catch (SQLException e) {
                // Xử lý lỗi khi truy vấn cơ sở dữ liệu
                showErrorDialog("Lỗi khi tìm kiếm: " + e.getMessage());
            }
        } else {
            // Người dùng đã hủy bỏ nhập liệu
            showErrorDialog("Tìm kiếm bị hủy bỏ.");
        }
    }


    
    private WarehouseBO warehouseBO;  // Declare the warehouseBO object

    public WarehouseUI() {
        warehouseBO = new WarehouseBO(); // Initialize it with an instance of your business logic class
    }

    
    private void showAllWarehouses() {
        try {
            ArrayList<Warehouse> warehouses = warehouseBO.getAllWarehouses();
            StringBuilder warehouseDetails = new StringBuilder();
            for (Warehouse warehouse : warehouses) {
                warehouseDetails.append("ID Kho: ").append(warehouse.getWarehouseID())
                        .append(", Tên Kho: ").append(warehouse.getWareName())
                        .append(", Thành phố: ").append(warehouse.getCity())
                        .append(", Quận/Huyện: ").append(warehouse.getDistrict())
                        .append(", Phường: ").append(warehouse.getWard())
                        .append(", Địa chỉ: ").append(warehouse.getAddress())
                        .append("\n");
            }
            warehouseDisplayArea.setText(warehouseDetails.toString());  // Hiển thị tất cả kho vào TextArea
        } catch (SQLException e) {
            showErrorDialog("Lỗi khi hiển thị tất cả kho: " + e.getMessage());
        }
    }


    
    private void showSearchDialog() {
        // Example code to show a dialog asking for a search term
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm kho");
        dialog.setHeaderText("Nhập thông tin để tìm kiếm kho");
        dialog.setContentText("Tìm kiếm theo ID hoặc Tên:");

        // Lấy kết quả từ người dùng
        dialog.showAndWait().ifPresent(searchTerm -> {
            // Thực hiện tìm kiếm với searchTerm
            warehouseDisplayArea.setText("Kết quả tìm kiếm: " + searchTerm);
        });
    }


    // Tải dữ liệu kho
    private void loadAllWarehouses() {
        try {
            ArrayList<Warehouse> warehouses = warehouseBO.getAllWarehouses();
            warehouseTable.getItems().clear();
            warehouseTable.getItems().addAll(warehouses);
        } catch (SQLException e) {
            showErrorDialog("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    // Tạo một dòng label và text field nằm cạnh nhau
    private HBox createFormRow(String label, TextField textField) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-font-weight: bold;");
        textField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-font-weight: bold;");
        HBox row = new HBox(10, lbl, textField);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
    
    private void showErrorDialog(String message) {
        // Create a simple alert to display the error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);  // No header text
        alert.setContentText(message);  // The error message
        
        // Show the alert and wait for user interaction
        alert.showAndWait();
    }

    // Thêm kho hàng
   

    private void addWarehouse() {
        // Mở hộp thoại để nhập thông tin ID kho
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Thêm kho");
        idDialog.setHeaderText("Nhập thông tin kho:");
        idDialog.setContentText("ID kho:");
        idDialog.showAndWait().ifPresent(id -> {
            // Sau khi nhập ID, tiếp tục yêu cầu nhập tên kho
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Nhập tên kho");
            nameDialog.setHeaderText("Nhập tên kho:");
            nameDialog.setContentText("Tên kho:");
            nameDialog.showAndWait().ifPresent(name -> {
                // Sau khi nhập tên, tiếp tục yêu cầu nhập thành phố
                TextInputDialog cityDialog = new TextInputDialog();
                cityDialog.setTitle("Nhập thành phố");
                cityDialog.setHeaderText("Nhập thành phố:");
                cityDialog.setContentText("Thành phố:");
                cityDialog.showAndWait().ifPresent(city -> {
                    // Sau khi nhập thành phố, tiếp tục yêu cầu nhập quận/huyện
                    TextInputDialog districtDialog = new TextInputDialog();
                    districtDialog.setTitle("Nhập quận/huyện");
                    districtDialog.setHeaderText("Nhập quận/huyện:");
                    districtDialog.setContentText("Quận/Huyện:");
                    districtDialog.showAndWait().ifPresent(district -> {
                        // Sau khi nhập quận/huyện, tiếp tục yêu cầu nhập phường
                        TextInputDialog wardDialog = new TextInputDialog();
                        wardDialog.setTitle("Nhập phường");
                        wardDialog.setHeaderText("Nhập phường:");
                        wardDialog.setContentText("Phường:");
                        wardDialog.showAndWait().ifPresent(ward -> {
                            // Sau khi nhập phường, tiếp tục yêu cầu nhập địa chỉ
                            TextInputDialog addressDialog = new TextInputDialog();
                            addressDialog.setTitle("Nhập địa chỉ");
                            addressDialog.setHeaderText("Nhập địa chỉ:");
                            addressDialog.setContentText("Địa chỉ:");
                            addressDialog.showAndWait().ifPresent(address -> {
                                // Kiểm tra dữ liệu nhập vào
                                if (id.isEmpty() || name.isEmpty() || city.isEmpty() || district.isEmpty() || ward.isEmpty() || address.isEmpty()) {
                                    showErrorDialog("Tất cả các trường thông tin phải được điền đầy đủ.");
                                    return;
                                }

                                // Tạo đối tượng Warehouse
                                Warehouse warehouse = new Warehouse(id, name, city, district, ward, address);

                                try {
                                    // Thêm kho vào cơ sở dữ liệu thông qua WarehouseBO
                                    warehouseBO.addWarehouse(warehouse);

                                    // Thông báo thành công
                                    showInfoDialog("Thêm kho thành công!");
                                    // Cập nhật lại danh sách kho
                            
                                } catch (SQLException e) {
                                    // Xử lý lỗi
                                    showErrorDialog("Thêm kho thất bại: " + e.getMessage());
                                }
                            });
                        });
                    });
                });
            });
        });
    }


    
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);  // Không cần tiêu đề
        alert.setContentText(message);  // Nội dung thông báo
        alert.showAndWait();  // Hiển thị hộp thoại
    }


    // Cập nhật kho hàng
    private void updateWarehouse() {
        Warehouse selectedWarehouse = tableView.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            String warehouseID = warehouseIDField.getText();
            String wareName = wareNameField.getText();
            String city = cityField.getText();
            String district = districtField.getText();
            String ward = wardField.getText();
            String address = addressField.getText();

            selectedWarehouse.setWarehouseID(warehouseID);
            selectedWarehouse.setWareName(wareName);
            selectedWarehouse.setCity(city);
            selectedWarehouse.setDistrict(district);
            selectedWarehouse.setWard(ward);
            selectedWarehouse.setAddress(address);

            try {
                warehouseDAO.updateWarehouse(selectedWarehouse);
                loadWarehouses();  // Cập nhật danh sách kho hàng
                statusLabel.setText("Warehouse updated successfully.");
                statusLabel.setStyle("-fx-text-fill: green;");
            } catch (SQLException e) {
                e.printStackTrace();
                showError("Error updating warehouse");
            }
        }
    }

    // Xóa kho hàng
    private void deleteWarehouse() {
        // Tạo hộp thoại yêu cầu người dùng nhập ID kho
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Xóa kho");
        inputDialog.setHeaderText("Nhập ID kho để xóa");
        inputDialog.setContentText("Vui lòng nhập ID kho cần xóa:");

        inputDialog.showAndWait().ifPresent(inputID -> {
            if (inputID.isEmpty()) {
                showErrorDialog("Vui lòng nhập ID kho!");
                return;
            }

            Warehouse warehouseToDelete = warehouseBO.getWarehouseByID(inputID);
			if (warehouseToDelete != null) {
			    // Tạo hộp thoại xác nhận trước khi xóa
			    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
			    confirmationDialog.setTitle("Xác nhận xóa");
			    confirmationDialog.setHeaderText("Bạn có chắc chắn muốn xóa kho?");
			    confirmationDialog.setContentText("Kho: " + warehouseToDelete.getWareName() + "\nID kho: " + inputID);

			    confirmationDialog.showAndWait().ifPresent(response -> {
			        if (response == ButtonType.OK) {
			            // Tiến hành xóa kho
			            try {
							if (warehouseBO.deleteWarehouse(inputID)) {
							    showInfoDialog("Kho đã được xóa thành công!");

							} else {
							    showErrorDialog("Xóa kho thất bại!");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        } else {
			            showInfoDialog("Hành động xóa đã bị hủy.");
			        }
			    });
			} else {
			    showErrorDialog("Không tìm thấy kho với ID: " + inputID);
			}
        });
    }


    // Tìm kiếm kho hàng
    private void showSearchWarehouse() {
        // Tạo hộp thoại để người dùng nhập giá trị tìm kiếm
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm kho");
        dialog.setHeaderText("Nhập giá trị tìm kiếm");
        dialog.setContentText("Nhập ID hoặc Tên kho:");

        // Hiển thị hộp thoại và lấy giá trị người dùng nhập vào
        Optional<String> result = dialog.showAndWait();

        // Kiểm tra nếu người dùng nhập dữ liệu
        if (result.isPresent()) {
            String searchValue = result.get(); // Lấy giá trị người dùng nhập vào

            // Kiểm tra nếu ô tìm kiếm rỗng hoặc chỉ chứa khoảng trắng
            if (searchValue.trim().isEmpty()) {
                showErrorDialog("Vui lòng nhập giá trị tìm kiếm!");
                return;
            }

            // Thực hiện tìm kiếm theo giá trị người dùng nhập
            try {
                ArrayList<Warehouse> results = warehouseBO.searchWarehousesByID(searchValue.trim());
                if (results.isEmpty()) {
                    results = warehouseBO.searchWarehousesByName(searchValue.trim());
                }

                if (results.isEmpty()) {
                    showErrorDialog("Không tìm thấy kho nào!");
                } else {
                    StringBuilder warehouseDetails = new StringBuilder();
                    for (Warehouse warehouse : results) {
                        warehouseDetails.append("ID: ").append(warehouse.getWarehouseID())
                                .append(", Tên kho: ").append(warehouse.getWareName())
                                .append(", Địa chỉ: ").append(warehouse.getAddress())
                                .append("\n");
                    }
                    warehouseDisplayArea.setText(warehouseDetails.toString());
                    showSuccessDialog("Tìm kiếm thành công! Đã tìm thấy " + results.size() + " kho.");
                }
            } catch (SQLException e) {
                showErrorDialog("Lỗi khi tìm kiếm kho: " + e.getMessage());
            }
        } else {
            showErrorDialog("Tìm kiếm bị hủy bỏ.");
        }
    }

   

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);  // Không cần tiêu đề
        alert.setContentText(message);  // Nội dung thông báo

        alert.showAndWait();  // Hiển thị hộp thoại thông báo
    }



    // Tải dữ liệu kho hàng
    private void loadWarehouses() {
        // Đảm bảo rằng tableView và data đã được khởi tạo
        if (tableView == null) {
            showError("Table view is not initialized.");
            return;
        }

        try {
            // Lấy danh sách các kho từ DAO
            ArrayList<Warehouse> warehouses = warehouseDAO.getAllWarehouses();

            // Chuyển đổi thành ObservableList để có thể gán vào TableView
            ObservableList<Warehouse> data = FXCollections.observableArrayList(warehouses);

            // Cập nhật TableView với dữ liệu mới
            Platform.runLater(() -> tableView.setItems(data));

        } catch (SQLException e) {
            // Xử lý lỗi khi không thể tải dữ liệu
            e.printStackTrace();
            showError("Error loading warehouses: " + e.getMessage());
        }
    }

    // Hiển thị thông báo lỗi
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }
}
