package atmpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    String pin;
    String cardno;
    TextField textField;

    JButton b1, b2;
    Withdrawl(String cardno,String pin){
        this.pin=pin;
        this.cardno=cardno;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460,180,700,35);
        l3.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460,220,400,35);
        l3.add(label2);


        textField = new TextField();
        textField.setBackground(new Color(65,125,128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460,260,320,25);
        textField.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(textField);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(700,362,150,35);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700,406,150,35);
        b2.setBackground(new Color(65,125,128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        setLayout(null);
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {
            try {
                String amount = textField.getText();
                Date date = new Date();

                String dt=date.toString().substring(0,10);
                Connn c = new Connn();
                ResultSet r1 = c.statement.executeQuery("select * from bank where cardno = '" + cardno + "' ");
                int bal=0;
                while(r1.next()) {
                    if (r1.getString("date").substring(0, 10).equals(dt))
                    {
                        if (r1.getString("type").equals("withdrawl") || r1.getString("type").equals("Withdrawl")) {
                            bal += Integer.parseInt(r1.getString("amount"));

                        }
                    }
                };
                if (textField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to withdraw");
                }
                else if(textField.getText().length()>4 && !(textField.getText().toString().equals("10000")))
                {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount less Than 10000");
                }
                else if(bal+Integer.parseInt(textField.getText().toString())>20000)
                {
                    JOptionPane.showMessageDialog(this, "Limit Exceeded!!!","Daily WithDrawl Limit is Rs 20,000",JOptionPane.ERROR_MESSAGE);
                }

                else {
                    Connn con = new Connn();
                    ResultSet resultSet = con.statement.executeQuery("select * from bank where cardno = '" + cardno + "'");
                    int balance = 0;
                    while (resultSet.next()) {
                        if (resultSet.getString("type").equals("Deposit")) {
                            balance += Integer.parseInt(resultSet.getString("amount"));
                        } else {
                            balance -= Integer.parseInt(resultSet.getString("amount"));
                        }
                    }
                    if (balance < Integer.parseInt(amount)) {
                        JOptionPane.showMessageDialog(null, "Insuffient Balance");
                        return;
                    }

                    con.statement.executeUpdate("insert into bank values('"+cardno+"','" + pin + "', '" + date + "', 'withdrawl', '" + amount + "' )");
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");
                    setVisible(false);
                    new main_Class(cardno,pin);

                }
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid Amount:"+E.getMessage());
            }
        } else if (e.getSource()==b2) {
            setVisible(false);
            new main_Class(cardno,pin);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("","");
    }
}
