package atmpackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {
    String pin;
    String cardno;
    JButton button;
    mini(String cardno ,String pin){
        this.pin = pin;
        this.cardno=cardno;
        getContentPane().setBackground(new Color(255,204,204));
        setSize(400,600);
        setLocation(20,20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20,140,400,200);
        add(label1);

        JLabel label2 = new JLabel("MINI STATEMENT");
        label2.setFont(new Font("System", Font.BOLD,15));
        label2.setBounds(150,20,200,20);
        add(label2);
        JLabel label5 = new JLabel("==========Last Six Transaction Details============");
        label5.setFont(new Font("System", Font.BOLD,13));
        label5.setBounds(20,110,350,20);
        add(label5);

        JLabel label3 = new JLabel();
        label3.setBounds(20,80,300,20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20,400,300,20);
        add(label4);

        try{
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("select * from login where card_number = '"+cardno+"'");
            while (resultSet.next()){
                label3.setText("Card Number:  "+ resultSet.getString("card_number").substring(0,4) + "XXXXXXXX"+ resultSet.getString("card_number").substring(12));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        try{
            int balance =0;
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("select * from bank where cardno = '"+cardno+"' and  pin = '"+pin+"' order by date desc limit 6");
            while (resultSet.next()){

                label1.setText(label1.getText() + "<html>"+resultSet.getString("date")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+resultSet.getString("type")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+resultSet.getString("amount")+ "<br><br><html>");

//                if (resultSet.getString("type").equals("Deposit")){
//                    balance += Integer.parseInt(resultSet.getString("amount"));
//                }else {
//                    balance -= Integer.parseInt(resultSet.getString("amount"));
//                }
            }
            ResultSet r1 = c.statement.executeQuery("select * from bank where cardno = '"+cardno+"' and  pin = '"+pin+"'");
            while(r1.next())
            {
                if (r1.getString("type").equals("Deposit")){
                    balance += Integer.parseInt(r1.getString("amount"));
                }else {
                    balance -= Integer.parseInt(r1.getString("amount"));
                }
            }
            label4.setText("Your Total Balance is Rs "+balance);
        }catch (Exception e){
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(20,500,100,25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("","");
    }
}
