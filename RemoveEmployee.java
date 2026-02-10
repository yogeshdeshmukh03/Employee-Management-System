package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.ResultSet;

public class RemoveEmployee extends JFrame implements ActionListener {

    Choice choiceEMPID;
    JButton delete, back;

    // Helper: resource icon load (NPE-proof)
    private ImageIcon loadIcon(String path) {
        URL url = RemoveEmployee.class.getResource(path); // ex: "/icon/delete.png"
        if (url == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Resource not found: " + path + "\n" +
                            "Fix:\n" +
                            "1) Keep images in: src/icon/\n" +
                            "2) Mark src/icon as Resources Root (NOT Excluded)\n" +
                            "3) Build -> Rebuild Project",
                    "Missing Resource",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
        return new ImageIcon(url);
    }

    public RemoveEmployee() {

        // ---------- Background FIRST (so it doesn't cover components) ----------
        ImageIcon bg = loadIcon("/icon/rback.png");
        JLabel background = new JLabel();
        background.setBounds(0, 0, 1120, 630);
        if (bg != null) {
            Image bgImg = bg.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
            background.setIcon(new ImageIcon(bgImg));
        }
        setContentPane(background);
        setLayout(null);

        // ---------- UI ----------
        JLabel label = new JLabel("Employee ID");
        label.setBounds(50, 50, 100, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(label);

        choiceEMPID = new Choice();
        choiceEMPID.setBounds(200, 50, 150, 30);
        add(choiceEMPID);

        JLabel labelName = new JLabel("Name");
        labelName.setBounds(50, 100, 100, 30);
        labelName.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelName);

        JLabel textName = new JLabel();
        textName.setBounds(200, 100, 250, 30);
        add(textName);

        JLabel labelPhone = new JLabel("Phone");
        labelPhone.setBounds(50, 150, 100, 30);
        labelPhone.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelPhone);

        JLabel textPhone = new JLabel();
        textPhone.setBounds(200, 150, 250, 30);
        add(textPhone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        labelemail.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelemail);

        JLabel textEmail = new JLabel();
        textEmail.setBounds(200, 200, 300, 30);
        add(textEmail);

        // ---------- Load Employee IDs ----------
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("select * from employee");
            while (rs.next()) {
                choiceEMPID.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------- Load details of selected employee ----------
        Runnable loadDetails = () -> {
            try {
                conn c = new conn();
                ResultSet rs = c.statement.executeQuery(
                        "select * from employee where empId = '" + choiceEMPID.getSelectedItem() + "'"
                );
                if (rs.next()) {
                    textName.setText(rs.getString("name"));
                    textPhone.setText(rs.getString("phone"));
                    textEmail.setText(rs.getString("email"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        // initial load
        loadDetails.run();

        // change listener
        choiceEMPID.addItemListener(e -> loadDetails.run());

        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        // ---------- Delete Icon ----------
        ImageIcon del = loadIcon("/icon/delete.png");
        if (del != null) {
            Image delImg = del.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            JLabel img = new JLabel(new ImageIcon(delImg));
            img.setBounds(700, 80, 200, 200);
            add(img);
        }

        // ---------- Frame ----------
        setSize(1000, 400);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == delete) {
            try {
                conn c = new conn();
                String query = "delete from employee where empId = '" + choiceEMPID.getSelectedItem() + "'";
                c.statement.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
                setVisible(false);
                new Main_class();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemoveEmployee::new);
    }
}