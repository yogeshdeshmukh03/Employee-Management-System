package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField tusername;
    JPasswordField tpassword;
    JButton login, back;

    Login() {

        setLayout(null);

        // ✅ background first
        ImageIcon b1 = new ImageIcon(ClassLoader.getSystemResource("icon/LoginB.jpg"));
        Image b2 = b1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        JLabel bg = new JLabel(new ImageIcon(b2));
        bg.setBounds(0, 0, 600, 300);
        add(bg);

        JLabel username = new JLabel("Username");
        username.setBounds(40, 20, 100, 30);
        bg.add(username);

        tusername = new JTextField();
        tusername.setBounds(150, 20, 150, 30);
        bg.add(tusername);

        JLabel password = new JLabel("Password");
        password.setBounds(40, 70, 100, 30);
        bg.add(password);

        tpassword = new JPasswordField();
        tpassword.setBounds(150, 70, 150, 30);
        bg.add(tpassword);

        login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        bg.add(login);

        back = new JButton("BACK");
        back.setBounds(150, 180, 150, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        bg.add(back);

        // ✅ side image
        ImageIcon s1 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image s2 = s1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel side = new JLabel(new ImageIcon(s2));
        side.setBounds(360, 40, 200, 200);
        bg.add(side);

        setSize(600, 300);
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try {
                String username = tusername.getText();
                String password = new String(tpassword.getPassword()); // ✅ fix

                conn c = new conn();
                String query = "select * from login where username='" + username + "' and password='" + password + "'";
                ResultSet rs = c.statement.executeQuery(query);

                if (rs.next()) {
                    setVisible(false);
                    new Main_class();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}