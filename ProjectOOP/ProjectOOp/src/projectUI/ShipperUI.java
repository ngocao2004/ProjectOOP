package projectUI;

import bo.ShipperBO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import bean.Shipper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.text.ParseException;



public class ShipperUI extends Application {
    private ShipperBO shipperBO = new ShipperBO();
    private TableView<Shipper> shipperTable;
    private TextField tfEmployeeId, tfFirstName, tfMiddleName, tfLastName, tfGender, tfPhone, tfHomeTown, tfSearch;
    private TextField tfBirthday; // For simplicity, using a TextField for birthday input
    private Button btnAdd, btnUpdate, btnDelete, btnRefresh, btnSearch, btnSortById, btnSortByName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shipper Management");

        // Create form for inputs (Information section)
        GridPane formPanel = new GridPane();
        formPanel.setHgap(10);  // Horizontal gap between columns
        formPanel.setVgap(10);  // Vertical gap between rows
        formPanel.setStyle("-fx-padding: 10;");
        formPanel.setAlignment(Pos.TOP_RIGHT);  // Align the form to the right

        // Create the form elements (Labels and TextFields)
        tfEmployeeId = new TextField();
        tfFirstName = new TextField();
        tfMiddleName = new TextField();
        tfLastName = new TextField();
        tfGender = new TextField();
        tfBirthday = new TextField();
        tfPhone = new TextField();
        tfHomeTown = new TextField();
        tfSearch = new TextField();

      
        // Create buttons for actions
        HBox toolBar = new HBox(10);
        btnAdd = new Button("Add Shipper");
        btnUpdate = new Button("Update Shipper");
        btnDelete = new Button("Delete Shipper");
        btnRefresh = new Button("Refresh List");
        btnSearch = new Button("Search");
        btnSortById = new Button("Sort by ID");
        btnSortByName = new Button("Sort by Name");

        btnAdd.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnRefresh.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnSearch.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnSortById.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        btnSortByName.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");

        toolBar.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRefresh, btnSearch, btnSortById, btnSortByName);

        // Create the table for displaying shipper data (left side)
        shipperTable = new TableView<>();

     // Shipper ID Column
     TableColumn<Shipper, String> idColumn = new TableColumn<>("Shipper ID");
     idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));

     // First Name Column
     TableColumn<Shipper, String> firstNameColumn = new TableColumn<>("First Name");
     firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));

     // Last Name Column
     TableColumn<Shipper, String> lastNameColumn = new TableColumn<>("Last Name");
     lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

     // Middle Name Column
     TableColumn<Shipper, String> middleNameColumn = new TableColumn<>("Middle Name");
     middleNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMiddleName())); // Đã sửa từ getLastName() thành getMiddleName()

     // Gender Column
     TableColumn<Shipper, String> genderColumn = new TableColumn<>("Gender");
     genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

     // Phone Column
     TableColumn<Shipper, String> phoneColumn = new TableColumn<>("Phone");
     phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));

     // Home Town Column
     TableColumn<Shipper, String> homeTownColumn = new TableColumn<>("Home Town");
     homeTownColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeTown()));

     // Birthday Column
     TableColumn<Shipper, Date> birthdayColumn = new TableColumn<>("Birthday");
     birthdayColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthday()));

     // Thêm tất cả các cột vào bảng
     shipperTable.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, middleNameColumn, genderColumn, phoneColumn, homeTownColumn, birthdayColumn);

     // Load dữ liệu ban đầu
     loadShippers();


        // Create layout and scene
        HBox mainLayout = new HBox(10);  // Horizontal layout to place form and table side by side
        mainLayout.setStyle("-fx-padding: 10;");
        mainLayout.getChildren().addAll(shipperTable, formPanel); // Table on the left, form on the right

        VBox vBoxLayout = new VBox(10);
        vBoxLayout.getChildren().addAll(toolBar, mainLayout); // Add toolbar on top

        Scene scene = new Scene(vBoxLayout, 700, 470);  // Adjust width for proper display
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button actions
        btnAdd.setOnAction(e -> addShipper());
        btnUpdate.setOnAction(e -> updateShipper(primaryStage));
        btnDelete.setOnAction(e -> deleteShipper(primaryStage));
        btnRefresh.setOnAction(e -> loadShippers());
        btnSearch.setOnAction(e -> searchShipper(primaryStage));
        btnSortById.setOnAction(e -> sortShippersById());
        btnSortByName.setOnAction(e -> sortShippersByName());
    }



    private void loadShippers() {
        try {
            ArrayList<Shipper> shippers = shipperBO.getAllShippers();
            ObservableList<Shipper> data = FXCollections.observableArrayList(shippers);
            shipperTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error loading shipper data");
        }
    }



    private void addShipper() {
        // Tạo cửa sổ (Popup) cho nhập thông tin mới
        Stage addShipperStage = new Stage();
        addShipperStage.setTitle("Add New Shipper");

        // Layout cho form nhập liệu
        GridPane formPanel = new GridPane();
        formPanel.setHgap(10);
        formPanel.setVgap(10);
        formPanel.setStyle("-fx-padding: 10;");
        formPanel.setAlignment(Pos.CENTER);

        // Tạo các trường nhập liệu
        TextField tfEmployeeId = new TextField();
        TextField tfFirstName = new TextField();
        TextField tfMiddleName = new TextField();
        TextField tfLastName = new TextField();
        TextField tfGender = new TextField();
        TextField tfBirthday = new TextField();
        TextField tfPhone = new TextField();
        TextField tfHomeTown = new TextField();

        // Các label cho các trường
        formPanel.add(new Label("Shipper ID:"), 0, 0);
        formPanel.add(tfEmployeeId, 1, 0);
        formPanel.add(new Label("First Name:"), 0, 1);
        formPanel.add(tfFirstName, 1, 1);
        formPanel.add(new Label("Middle Name:"), 0, 2);
        formPanel.add(tfMiddleName, 1, 2);
        formPanel.add(new Label("Last Name:"), 0, 3);
        formPanel.add(tfLastName, 1, 3);
        formPanel.add(new Label("Gender:"), 0, 4);
        formPanel.add(tfGender, 1, 4);
        formPanel.add(new Label("Birthday (yyyy-mm-dd):"), 0, 5);
        formPanel.add(tfBirthday, 1, 5);
        formPanel.add(new Label("Phone:"), 0, 6);
        formPanel.add(tfPhone, 1, 6);
        formPanel.add(new Label("Home Town:"), 0, 7);
        formPanel.add(tfHomeTown, 1, 7);

        // Nút Confirm (Add) và Cancel
        Button btnConfirm = new Button("Add Shipper");
        Button btnCancel = new Button("Cancel");

        // Xử lý sự kiện nút "Add Shipper"
        btnConfirm.setOnAction(e -> {
            String shipperId = tfEmployeeId.getText();
            String firstName = tfFirstName.getText();
            String middleName = tfMiddleName.getText();
            String lastName = tfLastName.getText();
            String gender = tfGender.getText();
            String birthdayStr = tfBirthday.getText();
            String phone = tfPhone.getText();
            String homeTown = tfHomeTown.getText();

            if (shipperId.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
                showErrorDialog("Please fill in all required fields.");
            } else {
                try {
                    Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
                    Shipper newShipper = new Shipper(shipperId, firstName, middleName, lastName, gender, birthday, phone, homeTown);
                    boolean isAdded = shipperBO.addShipper(newShipper);
                    if (isAdded) {
                        showSuccessDialog("Shipper added successfully!");
                        loadShippers();  // Reload the data after adding
                        addShipperStage.close();  // Close the add form
                    } else {
                        showErrorDialog("Failed to add shipper.");
                    }
                } catch (ParseException ex) {
                    showErrorDialog("Invalid date format. Please use yyyy-MM-dd.");
                }
            }
        });

        // Xử lý sự kiện nút "Cancel"
        btnCancel.setOnAction(e -> addShipperStage.close());

        // Tạo layout cho nút và thêm vào form
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(btnConfirm, btnCancel);
        formPanel.add(buttonLayout, 1, 8);

        // Tạo Scene và hiển thị cửa sổ
        Scene scene = new Scene(formPanel, 400, 400);
        addShipperStage.setScene(scene);
        addShipperStage.show();
    }


    private void updateShipper(Stage primaryStage) {
        // Bước 1: Hiển thị hộp thoại yêu cầu người dùng nhập ID shipper cần update
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Shipper");
        dialog.setHeaderText("Enter the Shipper ID to update");
        dialog.setContentText("Shipper ID:");

        // Hiển thị hộp thoại và nhận ID shipper từ người dùng
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String shipperId = result.get().trim();

            // Kiểm tra nếu ID shipper rỗng hoặc không hợp lệ
            if (shipperId.isEmpty()) {
                showErrorDialog("Shipper ID cannot be empty.");
                return;
            }

            // Bước 2: Lấy thông tin shipper từ ID nhập vào
            Shipper existingShipper = shipperBO.getShipperById(shipperId);
            if (existingShipper == null) {
                showErrorDialog("No shipper found with ID: " + shipperId);
                return;
            }

            // Bước 3: Tạo hộp thoại (Dialog) để hiển thị thông tin shipper và cho phép sửa
            Dialog<Shipper> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Shipper");
            updateDialog.setHeaderText("Edit Shipper Information");

            // Các trường nhập liệu cho các thông tin shipper
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField tfFirstName = new TextField(existingShipper.getFirstName());
            TextField tfMiddleName = new TextField(existingShipper.getMiddleName());
            TextField tfLastName = new TextField(existingShipper.getLastName());
            TextField tfGender = new TextField(existingShipper.getGender());
            TextField tfPhone = new TextField(existingShipper.getPhone());
            TextField tfHomeTown = new TextField(existingShipper.getHomeTown());
            TextField tfBirthday = new TextField(new SimpleDateFormat("yyyy-MM-dd").format(existingShipper.getBirthday()));

            // Thêm các trường nhập liệu vào grid
            grid.add(new Label("First Name:"), 0, 0);
            grid.add(tfFirstName, 1, 0);
            grid.add(new Label("Middle Name:"), 0, 1);
            grid.add(tfMiddleName, 1, 1);
            grid.add(new Label("Last Name:"), 0, 2);
            grid.add(tfLastName, 1, 2);
            grid.add(new Label("Gender:"), 0, 3);
            grid.add(tfGender, 1, 3);
            grid.add(new Label("Phone:"), 0, 4);
            grid.add(tfPhone, 1, 4);
            grid.add(new Label("Home Town:"), 0, 5);
            grid.add(tfHomeTown, 1, 5);
            grid.add(new Label("Birthday:"), 0, 6);
            grid.add(tfBirthday, 1, 6);

            updateDialog.getDialogPane().setContent(grid);

            // Thêm các nút "Update" và "Cancel"
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType);

            // Khi người dùng nhấn "Update"
            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    // Lấy giá trị từ các trường nhập liệu
                    String firstName = tfFirstName.getText();
                    String middleName = tfMiddleName.getText();
                    String lastName = tfLastName.getText();
                    String gender = tfGender.getText();
                    String phone = tfPhone.getText();
                    String address = tfHomeTown.getText();
                    String birthdayStr = tfBirthday.getText();

                    // Kiểm tra nếu bất kỳ trường nào trống
                    if (firstName.trim().isEmpty() || middleName.trim().isEmpty() || lastName.trim().isEmpty() ||
                            gender.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty() || birthdayStr.trim().isEmpty()) {
                        showErrorDialog("All fields are required. Please fill in all fields.");
                        return null;
                    }

                    // Kiểm tra định dạng ngày sinh
                    Date birthday = null;
                    try {
                        birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
                    } catch (ParseException ex) {
                        showErrorDialog("Invalid date format. Please use yyyy-MM-dd.");
                        return null;
                    }

                    // Cập nhật thông tin shipper
                    Shipper updatedShipper = new Shipper(shipperId, lastName, middleName, firstName, gender, birthday, phone, address);
                    boolean isUpdated = shipperBO.updateShipper(updatedShipper);
                    if (isUpdated) {
                        showSuccessDialog("Shipper updated successfully!");
                    } else {
                        showErrorDialog("Failed to update shipper.");
                    }

                    return updatedShipper;
                }
                return null;
            });

            updateDialog.showAndWait();
        }
    }


    private void deleteShipper(Stage primaryStage) {
        // Bước 1: Hiển thị hộp thoại yêu cầu người dùng nhập ID shipper cần xóa
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Shipper");
        dialog.setHeaderText("Enter the Shipper ID to delete");
        dialog.setContentText("Shipper ID:");

        // Hiển thị hộp thoại và nhận ID shipper từ người dùng
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String shipperId = result.get().trim();

            // Kiểm tra nếu ID shipper rỗng hoặc không hợp lệ
            if (shipperId.isEmpty()) {
                showErrorDialog("Shipper ID cannot be empty.");
                return;
            }

            // Bước 2: Lấy thông tin shipper từ ID nhập vào
            Shipper existingShipper = shipperBO.getShipperById(shipperId);
            if (existingShipper == null) {
                showErrorDialog("No shipper found with ID: " + shipperId);
                return;
            }

            // Bước 3: Hiển thị hộp thoại xác nhận xóa
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Delete");
            confirmDialog.setHeaderText("Are you sure you want to delete this shipper?");
            confirmDialog.setContentText("Shipper ID: " + existingShipper.getEmployeeID() + "\nName: " + existingShipper.getFirstName() + " " + existingShipper.getLastName());

            Optional<ButtonType> confirmation = confirmDialog.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                // Nếu người dùng xác nhận, xóa shipper
                boolean isDeleted = shipperBO.deleteShipper(shipperId);
                if (isDeleted) {
                    showSuccessDialog("Shipper deleted successfully!");
                } else {
                    showErrorDialog("Failed to delete shipper.");
                }
            }
        }
    }

    private void searchShipper(Stage primaryStage) {
        // Bước 1: Tạo hộp thoại yêu cầu người dùng chọn tìm kiếm theo ID hoặc Tên
        ChoiceDialog<String> dialog = new ChoiceDialog<>("ID", "ID", "Name");
        dialog.setTitle("Search Shipper");
        dialog.setHeaderText("Choose the search criteria");
        dialog.setContentText("Search by:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String searchCriteria = result.get();

            if ("ID".equals(searchCriteria)) {
                // Tìm kiếm theo ID
                TextInputDialog searchDialog = new TextInputDialog();
                searchDialog.setTitle("Search Shipper by ID");
                searchDialog.setHeaderText("Enter the Shipper ID to search");
                searchDialog.setContentText("Shipper ID:");

                Optional<String> searchResult = searchDialog.showAndWait();
                if (searchResult.isPresent()) {
                    String searchTerm = searchResult.get().trim();

                    if (searchTerm.isEmpty()) {
                        showErrorDialog("Shipper ID cannot be empty.");
                        return;
                    }

                    // Tìm kiếm theo ID
                    List<Shipper> searchResults = shipperBO.searchShippersByIdOrName(searchTerm);
                    showSearchResults(searchResults);  // Hiển thị kết quả tìm kiếm
                }
            } else if ("Name".equals(searchCriteria)) {
                // Tìm kiếm theo Tên (First Name, Middle Name, Last Name)
                // Bước 2: Tạo hộp thoại yêu cầu người dùng nhập First Name, Middle Name và Last Name
                TextInputDialog firstNameDialog = new TextInputDialog();
                firstNameDialog.setTitle("Search Shipper by Name");
                firstNameDialog.setHeaderText("Enter the First Name");
                firstNameDialog.setContentText("First Name:");

                Optional<String> firstNameResult = firstNameDialog.showAndWait();
                if (firstNameResult.isPresent()) {
                    String firstName = firstNameResult.get().trim();

                    // Tạo hộp thoại nhập Middle Name
                    TextInputDialog middleNameDialog = new TextInputDialog();
                    middleNameDialog.setTitle("Search Shipper by Name");
                    middleNameDialog.setHeaderText("Enter the Middle Name");
                    middleNameDialog.setContentText("Middle Name:");

                    Optional<String> middleNameResult = middleNameDialog.showAndWait();
                    if (middleNameResult.isPresent()) {
                        String middleName = middleNameResult.get().trim();

                        // Tạo hộp thoại nhập Last Name
                        TextInputDialog lastNameDialog = new TextInputDialog();
                        lastNameDialog.setTitle("Search Shipper by Name");
                        lastNameDialog.setHeaderText("Enter the Last Name");
                        lastNameDialog.setContentText("Last Name:");

                        Optional<String> lastNameResult = lastNameDialog.showAndWait();
                        if (lastNameResult.isPresent()) {
                            String lastName = lastNameResult.get().trim();

                            // Kiểm tra nếu tất cả các trường tên đều rỗng
                            if (firstName.isEmpty() && middleName.isEmpty() && lastName.isEmpty()) {
                                showErrorDialog("At least one name field must be provided.");
                                return;
                            }

                            // Tìm kiếm theo các trường tên
                            List<Shipper> searchResults = shipperBO.searchShippersByName(firstName, middleName, lastName);
                            showSearchResults(searchResults);  // Hiển thị kết quả tìm kiếm
                        }
                    }
                }
            }
        }
    }

    private void showSearchResults(List<Shipper> searchResults) {
        if (searchResults.isEmpty()) {
            showErrorDialog("No results found.");
        } else {
            // Tạo một thông báo hiển thị danh sách kết quả tìm kiếm
            StringBuilder resultMessage = new StringBuilder("Search Results:\n\n");
            for (Shipper shipper : searchResults) {
                resultMessage.append("ID: ").append(shipper.getEmployeeID())
                             .append(", Name: ").append(shipper.getFirstName()).append(" ")
                             .append(shipper.getMiddleName()).append(" ")
                             .append(shipper.getLastName()).append("\n");
            }

            // Hiển thị kết quả trong hộp thoại thông báo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText(null);  // Không có header
            alert.setContentText(resultMessage.toString());  // Nội dung là danh sách kết quả
            alert.showAndWait();
        }
    }


    private void sortShippersById() {
        try {
            ArrayList<Shipper> sortedShippers = shipperBO.getShippersSortedById(true);
            ObservableList<Shipper> data = FXCollections.observableArrayList(sortedShippers);
            shipperTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error sorting by ID");
        }
    }

    private void sortShippersByName() {
        try {
            ArrayList<Shipper> sortedShippers = shipperBO.getShippersSortedByName(true);
            ObservableList<Shipper> data = FXCollections.observableArrayList(sortedShippers);
            shipperTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error sorting by Name");
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
