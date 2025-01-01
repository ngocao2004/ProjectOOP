package projectUI;

import bo.SurchargeBO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;




import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import bean.Surcharge;

public class SurchargeApp extends Application {

    private Label resultLabel;
    private TableView<Surcharge> tableView;
    private ObservableList<Surcharge> surchargeData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quản lý Phụ Phí");

        // Tạo layout chính
        VBox vbox = new VBox(10); 
        vbox.setPadding(new javafx.geometry.Insets(20));

    

        // Tạo Label kết quả tìm kiếm
        resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill: red;");

        // Tạo TableView
        tableView = new TableView<>();
        surchargeData = FXCollections.observableArrayList();

        // Thiết lập cột trong TableView
        TableColumn<Surcharge, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("SurchargeID"));
        
        
        TableColumn<Surcharge, String> nameColumn = new TableColumn<>("Tên phụ phí");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("SurchargeName"));
  
        
        TableColumn<Surcharge, Double> priceColumn = new TableColumn<>("Giá");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
 

        // Thêm các cột vào TableView
        tableView.getColumns().addAll(idColumn, nameColumn, priceColumn);
        tableView.setStyle("-fx-border-color: #808080; -fx-border-width: 1px;");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Nút tìm kiếm
        Button searchButton = new Button("tìm kiếm");

        searchButton.setOnAction(e -> handleSearchSurcharge());
        // Nút thêm phụ phí
        Button addButton = new Button("Thêm Phụ Phí");

        addButton.setOnAction(e -> handleAddSurcharge());

        // Nút xóa phụ phí
        Button deleteButton = new Button("Xóa Phụ Phí");

        deleteButton.setOnAction(e -> handleDeleteSurcharge());

        // Nút cập nhật phụ phí
        Button updateButton = new Button("Cập nhật Phụ Phí");

        updateButton.setOnAction(e -> handleUpdateSurcharge());

        // Thêm các thành phần vào layout
        HBox buttonBox = new HBox(10, addButton, deleteButton, updateButton, searchButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll( buttonBox, resultLabel, tableView);

        // Tạo scene và hiển thị
        Scene scene = new Scene(vbox, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Gọi phương thức hiển thị tất cả phụ phí khi ứng dụng khởi động
        try {
            handleShowAllSurcharges();
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Đã xảy ra lỗi khi tải phụ phí.");
        }
    }

    // Hiển thị tất cả phụ phí
    private void handleShowAllSurcharges() throws SQLException {
        SurchargeBO surchargeBO = new SurchargeBO();
        List<Surcharge> surcharges = surchargeBO.getAllSurcharges();
        surchargeData.clear(); // Xóa dữ liệu cũ

        if (surcharges.isEmpty()) {
            resultLabel.setText("Không có phụ phí nào.");
        } else {
            surchargeData.addAll(surcharges); // Thêm tất cả phụ phí vào ObservableList
            tableView.setItems(surchargeData); // Cập nhật TableView với dữ liệu mới
        }
    }

    // Tìm kiếm phụ phí
    
    SurchargeBO surchargeBO = new SurchargeBO();

    public void handleSearchSurcharge() {
        // Tạo giao diện đẹp hơn với JavaFX
        Label instructionLabel = new Label("Chọn phương thức tìm kiếm");
        
        ChoiceBox<String> searchOptions = new ChoiceBox<>();
        searchOptions.getItems().addAll("Tìm kiếm theo ID", "Tìm kiếm theo Name");
        searchOptions.setValue("Tìm kiếm theo ID");

        TextField searchField = new TextField();
        searchField.setPromptText("Nhập giá trị tìm kiếm...");

        Button searchButton = new Button("Tìm kiếm");

        // Xử lý sự kiện khi nhấn nút tìm kiếm
        searchButton.setOnAction(e -> {
            String searchQuery = searchField.getText().trim();
            String selectedOption = searchOptions.getValue();

            if (searchQuery.isEmpty()) {
                showAlert("Lỗi", "Vui lòng nhập giá trị tìm kiếm.");
                return;
            }

            if ("Tìm kiếm theo ID".equals(selectedOption)) {
                // Kiểm tra ID có phải là số không
                try {
                    Integer.parseInt(searchQuery);
                } catch (NumberFormatException ex) {
                    showAlert("Lỗi", "ID phải là một số hợp lệ.");
                    return;
                }
            }

            try {
                // Gọi phương thức tìm kiếm trong SurchargeBO
                List<Surcharge> surcharges = surchargeBO.searchSurcharge(searchQuery);

                // Nếu có kết quả tìm kiếm
                if (!surcharges.isEmpty()) {
                    StringBuilder result = new StringBuilder("Kết quả tìm kiếm:\n");
                    for (Surcharge surcharge : surcharges) {
                        result.append("ID: ").append(surcharge.getSurchargeID())
                              .append(", Name: ").append(surcharge.getSurchargeName())
                              .append(", Price: ").append(surcharge.getPrice())
                              .append("\n");
                    }
                    showAlert("Kết quả tìm kiếm", result.toString());
                } else {
                    showAlert("Kết quả tìm kiếm", "Không tìm thấy phụ phí nào.");
                }
            } catch (SQLException ex) {
                showAlert("Lỗi", "Lỗi khi tìm kiếm: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Cài đặt giao diện cửa sổ (JavaFX layout)
        VBox layout = new VBox(10);
        layout.getChildren().addAll(instructionLabel, searchOptions, searchField, searchButton);

        Scene scene = new Scene(layout, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Tìm kiếm phụ phí");
        stage.setScene(scene);
        stage.show();
    }

    // Phương thức hiển thị hộp thoại cảnh báo
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Thêm phụ phí
    public void handleAddSurcharge() {
        // Tạo một dialog để nhập thông tin phụ phí
        Dialog<Surcharge> dialog = new Dialog<>();
        dialog.setTitle("Thêm Phụ Phí");
        dialog.setHeaderText("Nhập thông tin phụ phí mới:");

        // Tạo các trường nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // ID phụ phí
        TextField idField = new TextField();
        idField.setPromptText("ID phụ phí");
        grid.add(new Label("ID phụ phí:"), 0, 0);
        grid.add(idField, 1, 0);

        // Tên phụ phí
        TextField nameField = new TextField();
        nameField.setPromptText("Tên phụ phí");
        grid.add(new Label("Tên phụ phí:"), 0, 1);
        grid.add(nameField, 1, 1);

        // Giá phụ phí
        TextField priceField = new TextField();
        priceField.setPromptText("Giá phụ phí");
        grid.add(new Label("Giá:"), 0, 2);
        grid.add(priceField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Tạo các nút cho dialog
        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Lắng nghe sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // Lấy giá trị người dùng nhập
                String surchargeID = idField.getText();
                String surchargeName = nameField.getText();
                String priceText = priceField.getText();

                // Kiểm tra xem các trường có rỗng không
                if (surchargeID.isEmpty() || surchargeName.isEmpty() || priceText.isEmpty()) {
                    // Hiển thị thông báo lỗi nếu có trường trống
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin phụ phí.");
                    return null; // Nếu thông tin không đầy đủ, không thực hiện thêm
                }

                try {
                    // Chuyển giá trị giá từ String sang double
                    double price = Double.parseDouble(priceText);

                    // Tạo đối tượng Surcharge và thêm vào cơ sở dữ liệu thông qua SurchargeBO
                    Surcharge surcharge = new Surcharge(surchargeID, surchargeName, price);
                    SurchargeBO surchargeBO = new SurchargeBO();

                    // Kiểm tra nếu phụ phí đã tồn tại trước khi thêm vào
                    if (surchargeBO.addSurcharge(surcharge)) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Phụ phí đã được thêm thành công.");
                        return surcharge;  // Trả về đối tượng phụ phí đã thêm
                    } else {
                        // Nếu thêm không thành công, hiển thị thông báo lỗi
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm phụ phí.");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp giá không hợp lệ
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Giá phải là một số hợp lệ.");
                    return null;
                }
            }
            return null;  // Trả về null nếu người dùng nhấn Cancel
        });

        // Hiển thị dialog
        dialog.showAndWait().ifPresent(surcharge -> {
            loadSurcharges(); // Làm mới danh sách phụ phí trong bảng
        });
    }

    // Hàm để hiển thị thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadSurcharges() {
        SurchargeBO surchargeBO = new SurchargeBO();
        try {
            // Lấy danh sách phụ phí từ database thông qua BO
            ArrayList<Surcharge> surcharges = surchargeBO.getAllSurcharges();
            
            // Cập nhật dữ liệu cho TableView
            surchargeData.clear(); // Xóa dữ liệu cũ trong table
            surchargeData.addAll(surcharges); // Thêm danh sách phụ phí mới vào table

            // Đảm bảo TableView hiển thị đúng thông tin
            tableView.setItems(surchargeData);
        } catch (SQLException e) {
            // Xử lý ngoại lệ nếu có lỗi trong quá trình tải dữ liệu
            e.printStackTrace();
            resultLabel.setText("Đã xảy ra lỗi khi tải phụ phí.");
        }
    }


    // Xóa phụ phí
    public void handleDeleteSurcharge() {
        // Tạo một dialog để nhập ID phụ phí cần xóa
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Xóa Phụ Phí");
        dialog.setHeaderText("Nhập ID phụ phí cần xóa:");

        // Tạo trường nhập liệu cho ID phụ phí
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // ID phụ phí
        TextField idField = new TextField();
        idField.setPromptText("ID phụ phí");
        grid.add(new Label("ID phụ phí:"), 0, 0);
        grid.add(idField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Tạo các nút cho dialog
        ButtonType deleteButtonType = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        // Lắng nghe sự kiện khi người dùng nhấn OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                // Lấy giá trị người dùng nhập
                String surchargeID = idField.getText();

                // Kiểm tra xem ID có trống không
                if (surchargeID.isEmpty()) {
                    // Hiển thị thông báo lỗi nếu ID trống
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập ID phụ phí.");
                    return null; // Nếu ID trống, không thực hiện xóa
                }

                try {
                    // Tạo đối tượng SurchargeBO để gọi phương thức xóa
                    SurchargeBO surchargeBO = new SurchargeBO();

                    // Kiểm tra xem phụ phí có tồn tại hay không
                    if (!surchargeBO.surchargeExists(surchargeID)) {
                        // Nếu không tồn tại, hiển thị thông báo lỗi
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Phụ phí không tồn tại.");
                        return null;
                    }

                    // Xóa phụ phí
                    if (surchargeBO.deleteSurcharge(surchargeID)) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Phụ phí đã được xóa thành công.");
                        return surchargeID;  // Trả về ID của phụ phí đã xóa
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa phụ phí.");
                        return null;
                    }
                } catch (SQLException e) {
                    // Xử lý lỗi khi kết nối CSDL
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi xóa phụ phí.");
                    e.printStackTrace();
                }
            }
            return null;  // Trả về null nếu người dùng nhấn Cancel
        });

        // Hiển thị dialog
        dialog.showAndWait().ifPresent(surchargeID -> {
            loadSurcharges(); // Làm mới danh sách phụ phí trong bảng
        });
    }


    


    // Cập nhật phụ phí
    public void handleUpdateSurcharge() {
        // Tạo một dialog để yêu cầu nhập ID phụ phí
        Dialog<String> idDialog = new Dialog<>();
        idDialog.setTitle("Cập Nhật Phụ Phí");
        idDialog.setHeaderText("Nhập ID phụ phí cần cập nhật:");

        // Tạo trường nhập liệu ID phụ phí
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        idField.setPromptText("ID phụ phí");
        grid.add(new Label("ID phụ phí:"), 0, 0);
        grid.add(idField, 1, 0);

        idDialog.getDialogPane().setContent(grid);

        // Thêm các nút OK và Hủy cho dialog
        ButtonType okButtonType = new ButtonType("Tìm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        idDialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Lắng nghe sự kiện khi người dùng nhấn OK
        idDialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return idField.getText();
            }
            return null;
        });

        // Hiển thị dialog yêu cầu ID phụ phí
        idDialog.showAndWait().ifPresent(surchargeID -> {
            if (surchargeID != null && !surchargeID.isEmpty()) {
                // Tìm phụ phí từ cơ sở dữ liệu
                SurchargeBO surchargeBO = new SurchargeBO();
                Surcharge surcharge = null;
                try {
                    surcharge = surchargeBO.getSurchargeByID(surchargeID);  // Tìm thông tin phụ phí theo ID
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (surcharge != null) {
                    // Nếu tìm thấy phụ phí, hiển thị thông tin trong một dialog mới để cập nhật
                    final Surcharge finalSurcharge = surcharge; // Đảm bảo surcharge là final hoặc effectively final

                    Dialog<Surcharge> updateDialog = new Dialog<>();
                    updateDialog.setTitle("Cập Nhật Phụ Phí");
                    updateDialog.setHeaderText("Chỉnh sửa thông tin phụ phí:");

                    // Tạo các trường nhập liệu cho tên và giá
                    GridPane updateGrid = new GridPane();
                    updateGrid.setHgap(10);
                    updateGrid.setVgap(10);

                    // Tên phụ phí
                    TextField nameField = new TextField(finalSurcharge.getSurchargeName());
                    nameField.setPromptText("Tên phụ phí");
                    updateGrid.add(new Label("Tên phụ phí:"), 0, 0);
                    updateGrid.add(nameField, 1, 0);

                    // Giá phụ phí
                    TextField priceField = new TextField(String.valueOf(finalSurcharge.getPrice()));
                    priceField.setPromptText("Giá phụ phí");
                    updateGrid.add(new Label("Giá:"), 0, 1);
                    updateGrid.add(priceField, 1, 1);

                    updateDialog.getDialogPane().setContent(updateGrid);

                    // Thêm nút OK và Hủy
                    ButtonType updateButtonType = new ButtonType("Cập nhật", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancelButtonType2 = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
                    updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType2);

                    // Lắng nghe sự kiện khi người dùng nhấn Cập nhật
                    updateDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == updateButtonType) {
                            // Lấy giá trị người dùng nhập
                            String newSurchargeName = nameField.getText();
                            String newPriceText = priceField.getText();

                            // Kiểm tra các trường nhập liệu
                            if (newSurchargeName.isEmpty() || newPriceText.isEmpty()) {
                                // Hiển thị thông báo lỗi nếu có trường trống
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Lỗi");
                                alert.setHeaderText(null);
                                alert.setContentText("Vui lòng điền đầy đủ thông tin phụ phí.");
                                alert.showAndWait();
                                return null;
                            }

                            try {
                                // Chuyển giá trị giá từ String sang double
                                double newPrice = Double.parseDouble(newPriceText);

                                // Cập nhật lại đối tượng Surcharge
                                finalSurcharge.setSurchargeName(newSurchargeName);
                                finalSurcharge.setPrice(newPrice);

                                // Cập nhật phụ phí trong cơ sở dữ liệu thông qua SurchargeBO
                                if (surchargeBO.updateSurcharge(finalSurcharge)) {
                                    // Hiển thị thông báo thành công
                                    Alert successAlert = new Alert(AlertType.INFORMATION);
                                    successAlert.setTitle("Cập nhật thành công");
                                    successAlert.setHeaderText(null);
                                    successAlert.setContentText("Phụ phí đã được cập nhật thành công.");
                                    successAlert.showAndWait();
                                    
                                    return finalSurcharge;  // Trả về phụ phí đã được cập nhật
                                } else {
                                    // Nếu cập nhật không thành công
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Lỗi");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Không thể cập nhật phụ phí.");
                                    alert.showAndWait();
                                }
                            } catch (NumberFormatException e) {
                                // Xử lý lỗi nếu giá không hợp lệ
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Lỗi");
                                alert.setHeaderText(null);
                                alert.setContentText("Giá phải là một số hợp lệ.");
                                alert.showAndWait();
                            }
                        }
                        return null;
                    });

                    // Hiển thị dialog cập nhật
                    updateDialog.showAndWait().ifPresent(updatedSurcharge -> {
                        loadSurcharges();  // Làm mới danh sách phụ phí trong bảng sau khi cập nhật
                    });
                } else {
                    // Nếu không tìm thấy phụ phí với ID đó
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy phụ phí với ID đã nhập.");
                    alert.showAndWait();
                }
            } else {
                // Thông báo khi người dùng không nhập ID
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập ID phụ phí.");
                alert.showAndWait();
            }
        });
    }

}
