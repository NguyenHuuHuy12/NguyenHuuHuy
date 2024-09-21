import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineShoppingApp extends JFrame {
    private JTextField textFieldName;
    private JTextField textFieldDiaChi;
    private JTextField textFieldPhone;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public OnlineShoppingApp() {
        init();
    }

    private void init() {
        // Thiết lập JFrame
        setTitle("Mua Hàng Online");
        setSize(900, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Thông tin khách hàng
        JPanel customerPanel = new JPanel(new GridLayout(3, 1));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));

        JLabel labelName = new JLabel("Họ Tên:");
        textFieldName = new JTextField();
        customerPanel.add(labelName);
        customerPanel.add(textFieldName);
        
        JLabel labelDiaChi = new JLabel("Địa Chỉ:");
        textFieldDiaChi = new JTextField();
        customerPanel.add(labelDiaChi);
        customerPanel.add(textFieldDiaChi);
        
        JLabel labelPhone = new JLabel("Số Điện Thoại:");
        textFieldPhone = new JTextField();
        customerPanel.add(labelPhone);
        customerPanel.add(textFieldPhone);

        // Panel Sản phẩm
        JPanel productPanel = new JPanel(new GridLayout(3, 1));
        productPanel.setBorder(BorderFactory.createTitledBorder("Sản Phẩm"));
        
        radioButton1 = new JRadioButton("Bánh Tráng Trộn - 15000");
        radioButton2 = new JRadioButton("Trà Sữa Trân Châu - 20000");
        radioButton3 = new JRadioButton("Trà Sữa Thạch Matcha - 25000");
        radioButton4 = new JRadioButton("Khoai Tây Chiên Bo - 5000");
        radioButton5 = new JRadioButton("Banh Kem - 100000");

        ButtonGroup productGroup = new ButtonGroup();
        productGroup.add(radioButton1);
        productGroup.add(radioButton2);
        productGroup.add(radioButton3);
        productGroup.add(radioButton4);
        productGroup.add(radioButton5);

        productPanel.add(radioButton1);
        productPanel.add(radioButton2);
        productPanel.add(radioButton3);
        productPanel.add(radioButton4);
        productPanel.add(radioButton5);

        // Panel Thêm sản phẩm và Bảng sản phẩm đã chọn
        JPanel centerPanel = new JPanel(new BorderLayout());

        JButton addButton = new JButton("Chọn SP");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        JButton removeButton = new JButton("Xóa SP");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeProduct();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        tableModel = new DefaultTableModel(new Object[]{"STT", "Tên Sản Phẩm", "Đơn Giá"}, 0);
        table = new JTable(tableModel);

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel Thanh toán và Thoát
        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel("Tổng số tiền cần thanh toán: 0");
        bottomPanel.add(totalLabel, BorderLayout.NORTH);

        JButton payButton = new JButton("Thanh Toán");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pay();
            }
        });

        JButton exitButton = new JButton("Thoát");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        JPanel actionPanel = new JPanel();
        actionPanel.add(payButton);
        actionPanel.add(exitButton);

        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        // Thêm các panel vào JFrame
        setLayout(new BorderLayout());
        add(customerPanel, BorderLayout.NORTH);
        add(productPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addProduct() {
        String productName = null;
        int price = 0;

        if (radioButton1.isSelected()) {
            productName = "Bánh Tráng Trộn";
            price = 15000;
        } else if (radioButton2.isSelected()) {
            productName = "Trà Sữa Trân Châu";
            price = 20000;
        } else if (radioButton3.isSelected()) {
            productName = "Trà Sữa Thạch Matcha";
            price = 25000;
        }
        else if (radioButton4.isSelected()) {
            productName = "Khoai Tây Chiên Bo";
            price = 5000;
        }
        else if(radioButton5.isSelected()) {
        	productName = "Banh Kem";
        	price= 100000;
        }
        if (productName != null) {
            int rowCount = tableModel.getRowCount();
            tableModel.addRow(new Object[]{rowCount + 1, productName, price});
            updateTotal();
        }
    }

    private void removeProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            updateTableIndices();
            updateTotal();
        }
    }

    private void updateTableIndices() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(i + 1, i, 0);
        }
    }

    private void updateTotal() {
        int total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (int) tableModel.getValueAt(i, 2);
        }
        totalLabel.setText("Tổng số tiền cần thanh toán: " + total);
    }

    private void pay() {
        int response = JOptionPane.showConfirmDialog(this, "Bạn muốn thanh toán?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            tableModel.setRowCount(0);
            updateTotal();
        }
    }

    private void exit() {
        int response = JOptionPane.showConfirmDialog(this, "Bạn muốn thoát?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineShoppingApp().setVisible(true);
            }
        });
    }
}
