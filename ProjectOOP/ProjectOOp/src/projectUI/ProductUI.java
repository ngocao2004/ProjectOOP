package projectUI;

import bean.Product;
import bo.ProductBO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProductUI extends Application {
    private TableView<Product> tableView;
    private ObservableList<Product> productList;

    @Override
    public void start(Stage primaryStage) {
        // Tạo danh sách sản phẩm mẫu
        productList = FXCollections.observableArrayList(
            new Product("P001", "John Doe", "Hà Nội", "Ba Đình", "Phúc Xá",
                        "123 Phố X", "Jane Doe", "0987654321", "Hồ Chí Minh",
                        "Quận 1", "Phường Bến Nghé", "456 Đường Y", "S001"),
            new Product("P002", "Alice Smith", "Đà Nẵng", "Hải Châu", "Hòa Cường Bắc",
                        "789 Phố Z", "Bob Smith", "0912345678", "Cần Thơ",
                        "Ninh Kiều", "Tân An", "101 Đường W", "S002")
        );

        // Tạo bảng
        tableView = createTableView();

        // Tạo các nút điều khiển
        Button addButton = new Button("Thêm");
        Button editButton = new Button("Sửa");
        Button deleteButton = new Button("Xóa");
        Button searchButton = new Button("Tìm kiếm");

        // Thêm sự kiện cho các nút
        addButton.setOnAction(e -> addProduct());
        editButton.setOnAction(e -> editProduct());
        deleteButton.setOnAction(e -> deleteProduct());
        searchButton.setOnAction(e -> searchProduct());

        // Tạo thanh điều khiển
        HBox controlBox = new HBox(10, addButton, editButton, deleteButton, searchButton);
        controlBox.setPadding(new Insets(10));
        controlBox.setStyle("-fx-background-color: #dfe3e6;");

        // Thiết kế bố cục chính
        VBox mainLayout = new VBox(10, tableView, controlBox);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: #f7f9fc;");

        // Tạo scene
        Scene scene = new Scene(mainLayout, 800, 600);

        // Tùy chỉnh cửa sổ chính
        primaryStage.setTitle("Quản lý Sản phẩm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Tạo bảng hiển thị sản phẩm
    private TableView<Product> createTableView() {
        TableView<Product> table = new TableView<>();
        table.setItems(productList);

        TableColumn<Product, String> orderIDCol = new TableColumn<>("Mã Đơn");
        orderIDCol.setCellValueFactory(data -> data.getValue().orderIDProperty());
        TableColumn<Product, String> payerCol = new TableColumn<>("Người Thanh Toán");
        payerCol.setCellValueFactory(data -> data.getValue().payerProperty());
        TableColumn<Product, String> pickupCityCol = new TableColumn<>("Thành Phố Nhận");
        pickupCityCol.setCellValueFactory(data -> data.getValue().pickupCityProperty());
        TableColumn<Product, String> recipientNameCol = new TableColumn<>("Người Nhận");
        recipientNameCol.setCellValueFactory(data -> data.getValue().recipientNameProperty());
        TableColumn<Product, String> phoneRecipientCol = new TableColumn<>("SĐT Người Nhận");
        phoneRecipientCol.setCellValueFactory(data -> data.getValue().phoneRecipientProperty());
        TableColumn<Product, String> deliveryCityCol = new TableColumn<>("Thành Phố Giao");
        deliveryCityCol.setCellValueFactory(data -> data.getValue().deliveryCityProperty());

        table.getColumns().addAll(orderIDCol, payerCol, pickupCityCol, recipientNameCol, phoneRecipientCol, deliveryCityCol);
        table.setStyle("-fx-selection-bar: #4a90e2; -fx-selection-bar-non-focused: #a1c4fd;");

        return table;
    }

    // Xử lý thêm sản phẩm
    private void addProduct() {
        Dialog<Product> dialog = createProductDialog(null); // Truyền `null` để thêm sản phẩm mới
        dialog.showAndWait().ifPresent(newProduct -> {
            ProductBO productBO = new ProductBO();  // Tạo đối tượng BO
            boolean isAdded = productBO.addProduct(newProduct);  // Thêm sản phẩm vào CSDL qua BO
            
            if (isAdded) {
                productList.add(newProduct);  // Thêm vào danh sách nếu thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đã thêm sản phẩm mới vào CSDL!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Có lỗi khi thêm sản phẩm vào CSDL!");
                alert.showAndWait();
            }
        });
    }


    private void editProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Dialog<Product> dialog = createProductDialog(selectedProduct); // Truyền sản phẩm cần sửa
            dialog.showAndWait().ifPresent(updatedProduct -> {
                // Cập nhật thông tin sản phẩm đã chọn
                selectedProduct.setOrderID(updatedProduct.getOrderID());
                selectedProduct.setPayer(updatedProduct.getPayer());
                selectedProduct.setPickupCity(updatedProduct.getPickupCity());
                selectedProduct.setPickupDistrict(updatedProduct.getPickupDistrict());
                selectedProduct.setPickupWard(updatedProduct.getPickupWard());
                selectedProduct.setPickupAddress(updatedProduct.getPickupAddress());
                selectedProduct.setRecipientName(updatedProduct.getRecipientName());
                selectedProduct.setPhoneRecipient(updatedProduct.getPhoneRecipient());
                selectedProduct.setDeliveryCity(updatedProduct.getDeliveryCity());
                selectedProduct.setDeliveryDistrict(updatedProduct.getDeliveryDistrict());
                selectedProduct.setDeliveryWard(updatedProduct.getDeliveryWard());
                selectedProduct.setDeliveryAddress(updatedProduct.getDeliveryAddress());
                selectedProduct.setServiceID(updatedProduct.getServiceID());

                tableView.refresh(); // Làm mới bảng
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đã sửa thông tin sản phẩm!");
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy chọn sản phẩm để sửa!");
            alert.showAndWait();
        }
    }

    // Hộp thoại để thu thập thông tin sản phẩm
    private Dialog<Product> createProductDialog(Product existingProduct) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle(existingProduct == null ? "Thêm Sản phẩm" : "Sửa Sản phẩm");
        dialog.setHeaderText(existingProduct == null ? "Nhập thông tin sản phẩm mới:" : "Sửa thông tin sản phẩm:");

        // Các ô nhập liệu
        TextField orderIDField = new TextField(existingProduct == null ? "" : existingProduct.getOrderID());
        orderIDField.setPromptText("Mã Đơn");
        TextField payerField = new TextField(existingProduct == null ? "" : existingProduct.getPayer());
        payerField.setPromptText("Người Thanh Toán");
        TextField pickupCityField = new TextField(existingProduct == null ? "" : existingProduct.getPickupCity());
        pickupCityField.setPromptText("Thành Phố Nhận");
        TextField pickupDistrictField = new TextField(existingProduct == null ? "" : existingProduct.getPickupDistrict());
        pickupDistrictField.setPromptText("Quận/Huyện Nhận");
        TextField pickupWardField = new TextField(existingProduct == null ? "" : existingProduct.getPickupWard());
        pickupWardField.setPromptText("Phường/Xã Nhận");
        TextField pickupAddressField = new TextField(existingProduct == null ? "" : existingProduct.getPickupAddress());
        pickupAddressField.setPromptText("Địa Chỉ Nhận");
        TextField recipientNameField = new TextField(existingProduct == null ? "" : existingProduct.getRecipientName());
        recipientNameField.setPromptText("Người Nhận");
        TextField phoneRecipientField = new TextField(existingProduct == null ? "" : existingProduct.getPhoneRecipient());
        phoneRecipientField.setPromptText("SĐT Người Nhận");
        TextField deliveryCityField = new TextField(existingProduct == null ? "" : existingProduct.getDeliveryCity());
        deliveryCityField.setPromptText("Thành Phố Giao");
        TextField deliveryDistrictField = new TextField(existingProduct == null ? "" : existingProduct.getDeliveryDistrict());
        deliveryDistrictField.setPromptText("Quận/Huyện Giao");
        TextField deliveryWardField = new TextField(existingProduct == null ? "" : existingProduct.getDeliveryWard());
        deliveryWardField.setPromptText("Phường/Xã Giao");
        TextField deliveryAddressField = new TextField(existingProduct == null ? "" : existingProduct.getDeliveryAddress());
        deliveryAddressField.setPromptText("Địa Chỉ Giao");
        TextField serviceIDField = new TextField(existingProduct == null ? "" : existingProduct.getServiceID());
        serviceIDField.setPromptText("Mã Dịch Vụ");

        // Bố cục ô nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Mã Đơn:"), 0, 0);
        grid.add(orderIDField, 1, 0);
        grid.add(new Label("Người Thanh Toán:"), 0, 1);
        grid.add(payerField, 1, 1);
        grid.add(new Label("Thành Phố Nhận:"), 0, 2);
        grid.add(pickupCityField, 1, 2);
        grid.add(new Label("Quận/Huyện Nhận:"), 0, 3);
        grid.add(pickupDistrictField, 1, 3);
        grid.add(new Label("Phường/Xã Nhận:"), 0, 4);
        grid.add(pickupWardField, 1, 4);
        grid.add(new Label("Địa Chỉ Nhận:"), 0, 5);
        grid.add(pickupAddressField, 1, 5);
        grid.add(new Label("Người Nhận:"), 0, 6);
        grid.add(recipientNameField, 1, 6);
        grid.add(new Label("SĐT Người Nhận:"), 0, 7);
        grid.add(phoneRecipientField, 1, 7);
        grid.add(new Label("Thành Phố Giao:"), 0, 8);
        grid.add(deliveryCityField, 1, 8);
        grid.add(new Label("Quận/Huyện Giao:"), 0, 9);
        grid.add(deliveryDistrictField, 1, 9);
        grid.add(new Label("Phường/Xã Giao:"), 0, 10);
        grid.add(deliveryWardField, 1, 10);
        grid.add(new Label("Địa Chỉ Giao:"), 0, 11);
        grid.add(deliveryAddressField, 1, 11);
        grid.add(new Label("Mã Dịch Vụ:"), 0, 12);
        grid.add(serviceIDField, 1, 12);

        dialog.getDialogPane().setContent(grid);

        // Các nút xác nhận và hủy
        ButtonType okButtonType = new ButtonType("Xác nhận", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Product(
                    orderIDField.getText(),
                    payerField.getText(),
                    pickupCityField.getText(),
                    pickupDistrictField.getText(),
                    pickupWardField.getText(),
                    pickupAddressField.getText(),
                    recipientNameField.getText(),
                    phoneRecipientField.getText(),
                    deliveryCityField.getText(),
                    deliveryDistrictField.getText(),
                    deliveryWardField.getText(),
                    deliveryAddressField.getText(),
                    serviceIDField.getText()
                );
            }
            return null;
        });

        return dialog;
    }

    // Xử lý sửa sản phẩm
   
    // Xử lý xóa sản phẩm
    private void deleteProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đã xóa sản phẩm!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy chọn sản phẩm để xóa!");
            alert.showAndWait();
        }
    }

    // Xử lý tìm kiếm sản phẩm
    private void searchProduct() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm");
        dialog.setHeaderText("Nhập mã đơn cần tìm:");
        dialog.setContentText("Mã đơn:");

        dialog.showAndWait().ifPresent(searchKey -> {
            for (Product product : productList) {
                if (product.getOrderID().equals(searchKey)) {
                    tableView.getSelectionModel().select(product);
                    tableView.scrollTo(product);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tìm thấy sản phẩm!");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.WARNING, "Không tìm thấy sản phẩm!");
            alert.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
