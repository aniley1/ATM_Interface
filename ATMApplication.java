/*code by Arnav Kumar
          BSC CS , MITWPU , KOTHRUD
		  TASK 5:  ATM APPLICATION INTERFACE
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 7, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Empty label for spacing
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // You can implement your own authentication logic here.
                // For simplicity, this example uses a hardcoded username and password.
                if (username.equals("arnavkumar") && password.equals("2001")) {
                    openATMInterface();
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the password field for security
                passwordField.setText("");
            }
        });
    }

    private void openATMInterface() {
        BankAccount account = new BankAccount(5000); // Initial balance
        ATMGUI atmGUI = new ATMGUI(account);
        atmGUI.setVisible(true);
        dispose(); // Close the login window
    }
}

class ATMGUI extends JFrame {
	 private BankAccount account;

    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea outputArea;

    public ATMGUI(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        add(balanceLabel, BorderLayout.NORTH);

        amountField = new JTextField(10);
        add(amountField, BorderLayout.CENTER);

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        add(buttonPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.EAST);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });
    }

    private void performWithdraw() {
        double amount = Double.parseDouble(amountField.getText());
        if (account.withdraw(amount)) {
            outputArea.append("Withdrawal successful: $" + amount + "\n");
        } else {
            outputArea.append("Insufficient balance for withdrawal: $" + amount + "\n");
        }
        updateBalance();
    }

    private void performDeposit() {
        double amount = Double.parseDouble(amountField.getText());
        account.deposit(amount);
        outputArea.append("Deposit successful: $" + amount + "\n");
        updateBalance();
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + account.getBalance());
        amountField.setText("");
    }
}

public class ATMApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            }
        });
    }
}
