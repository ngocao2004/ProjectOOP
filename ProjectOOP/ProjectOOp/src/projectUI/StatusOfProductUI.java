package projectUI;

import bo.StatusofproductBO;
import bean.Statusofproduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.table.TableModel;



public class StatusOfProductUI extends JFrame {
    private StatusofproductBO bo = new StatusofproductBO();

    private JTable table;
    private DefaultTableModel tableModel;

  

    public StatusOfProductUI() {
        // Thiết lập JFrame
        setTitle("Quản lý trạng thái đơn hàng");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo layout chính
        setLayout(new BorderLayout());

        // Bảng hiển thị dữ liệu
        tableModel = new DefaultTableModel(new String[]{"OrderID", "WarehouseID", "OrderStatus"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Chỉ cho phép chọn 1 dòng
        table.setRowHeight(30); // Đặt chiều cao dòng
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Đặt font cho tiêu đề
        table.getTableHeader().setBackground(new Color(220, 220, 220));  // Màu nền tiêu đề bảng
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Đặt font cho các ô dữ liệu
        table.setGridColor(Color.GRAY); // Màu của lưới
        table.setShowGrid(true); // Hiển thị lưới giữa các ô
        table.setSelectionBackground(new Color(173, 216, 230)); // Màu nền khi chọn dòng
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nhập liệu
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


    
        add(searchPanel, BorderLayout.NORTH);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Tải lại");

        // Đặt style cho các nút
        customizeButton(btnAdd);
        customizeButton(btnUpdate);
        customizeButton(btnDelete);
        customizeButton(btnRefresh);

        // Thêm nút vào panel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho các nút
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStatus();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStatus();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        // Tìm kiếm
       ;

        // Tải dữ liệu ban đầu
        loadData();
    }

    // Hàm lọc dữ liệu
    private void filterData(String query) {
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(query));
    }

    // Cập nhật dữ liệu vào bảng
    private void loadData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        ArrayList<Statusofproduct> list = bo.getAllStatusofproduct();
        if (list != null && !list.isEmpty()) {
            for (Statusofproduct status : list) {
                tableModel.addRow(new Object[]{status.getOrderID(), status.getCurrentWarehouseID(), status.getOrderStatus()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Hàm tinh chỉnh các nút
    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Thay đổi font chữ
        button.setBackground(new Color(128, 128, 0));   // Màu nền nút
        button.setForeground(Color.WHITE);               // Màu chữ
        button.setFocusPainted(false);                    // Tắt viền focus
        button.setPreferredSize(new Dimension(100, 40));  // Đặt kích thước nút
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(150, 150, 0)); // Thay đổi màu khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(128, 128, 0)); // Trả lại màu ban đầu
            }
        });
    }

    private void addStatus() {
        // Yêu cầu người dùng nhập OrderID, WarehouseID, OrderStatus
        String orderID = JOptionPane.showInputDialog(this, "Nhập OrderID:", "Nhập OrderID", JOptionPane.PLAIN_MESSAGE);
        String warehouseID = JOptionPane.showInputDialog(this, "Nhập WarehouseID:", "Nhập WarehouseID", JOptionPane.PLAIN_MESSAGE);
        String orderStatus = JOptionPane.showInputDialog(this, "Nhập OrderStatus:", "Nhập OrderStatus", JOptionPane.PLAIN_MESSAGE);

        // Kiểm tra nếu có ô nhập liệu trống
        if (orderID == null || warehouseID == null || orderStatus == null || orderID.isEmpty() || warehouseID.isEmpty() || orderStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng Statusofproduct mới từ dữ liệu đã nhập
        Statusofproduct status = new Statusofproduct(orderID, warehouseID, orderStatus);

        // Thêm trạng thái đơn hàng mới vào cơ sở dữ liệu thông qua BO
        if (bo.addStatus(status)) {
            // Hiển thị thông báo thành công
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Làm mới bảng dữ liệu để hiển thị các bản ghi mới
            loadData();
        } else {
            // Hiển thị thông báo thất bại nếu không thể thêm dữ liệu
            JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateStatus() {
        // Yêu cầu người dùng nhập OrderID cần cập nhật
        String orderID = JOptionPane.showInputDialog(this, "Nhập OrderID cần cập nhật:", "Nhập OrderID", JOptionPane.PLAIN_MESSAGE);

        // Kiểm tra nếu người dùng không nhập OrderID
        if (orderID == null || orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập OrderID!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra xem OrderID có tồn tại trong cơ sở dữ liệu không
        Statusofproduct statusToUpdate = null;
        ArrayList<Statusofproduct> statusList = bo.getAllStatusofproduct();
        for (Statusofproduct status : statusList) {
            if (status.getOrderID().equals(orderID)) {
                statusToUpdate = status;
                break;
            }
        }

        // Nếu không tìm thấy OrderID trong cơ sở dữ liệu
        if (statusToUpdate == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng với OrderID này!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hiển thị thông tin hiện tại để người dùng sửa trong một hộp thoại
        String currentWarehouseID = statusToUpdate.getCurrentWarehouseID();
        String currentOrderStatus = statusToUpdate.getOrderStatus();

        // Tạo panel để chứa các ô nhập liệu cho cả 2 thông tin
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        // Các thành phần của panel
        JTextField txtWarehouseID = new JTextField(currentWarehouseID);
        JTextField txtOrderStatus = new JTextField(currentOrderStatus);

        panel.add(new JLabel("WarehouseID:"));
        panel.add(txtWarehouseID);
        panel.add(new JLabel("OrderStatus:"));
        panel.add(txtOrderStatus);

        // Hiển thị hộp thoại tùy chỉnh với các ô nhập liệu
        int option = JOptionPane.showConfirmDialog(this, panel, "Cập nhật thông tin đơn hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Lấy dữ liệu từ các ô nhập liệu
            String newWarehouseID = txtWarehouseID.getText();
            String newOrderStatus = txtOrderStatus.getText();

            // Kiểm tra nếu các thông tin không bị trống
            if (newWarehouseID.isEmpty() || newOrderStatus.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cập nhật lại thông tin trạng thái
            Statusofproduct updatedStatus = new Statusofproduct(orderID, newWarehouseID, newOrderStatus);

            // Thực hiện cập nhật vào cơ sở dữ liệu
            if (bo.updateStatus(updatedStatus)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Làm mới bảng dữ liệu
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteStatus() {
        // Yêu cầu người dùng nhập OrderID cần xóa
        String orderID = JOptionPane.showInputDialog(this, "Nhập OrderID cần xóa:", "Nhập OrderID", JOptionPane.PLAIN_MESSAGE);

        // Kiểm tra nếu người dùng không nhập OrderID
        if (orderID == null || orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập OrderID!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra xem OrderID có tồn tại trong cơ sở dữ liệu không
        boolean exists = false;
        ArrayList<Statusofproduct> statusList = bo.getAllStatusofproduct();
        for (Statusofproduct status : statusList) {
            if (status.getOrderID().equals(orderID)) {
                exists = true;
                break;
            }
        }

        // Nếu không tìm thấy OrderID trong cơ sở dữ liệu
        if (!exists) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng với OrderID này!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xóa bản ghi trong cơ sở dữ liệu
        if (bo.deleteStatus(orderID)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData(); // Làm mới bảng dữ liệu
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StatusOfProductUI().setVisible(true);
        });
    }
}
