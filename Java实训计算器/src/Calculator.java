import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends JFrame implements ActionListener {

    private JTextField displayField;
    private double num1, num2;
    private char operator;
    private List<String> history;

    public Calculator() {
        setTitle("计算器");
        setSize(320, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // 设置天空蓝色背景
        Color skyBlue = new Color(135, 206, 250);
        getContentPane().setBackground(skyBlue);

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // 创建"查看历史记录"菜单
        JMenu historyMenu = new JMenu("历史记录");
        menuBar.add(historyMenu);

        // 创建历史记录菜单项
        JMenuItem viewHistoryMenuItem = new JMenuItem("查看历史记录");
        viewHistoryMenuItem.addActionListener(e -> {
            String message = "";
            for (String entry : history) {
                message += entry + "\n";
            }
            JOptionPane.showMessageDialog(this, message, "历史记录", JOptionPane.INFORMATION_MESSAGE);
        });
        historyMenu.add(viewHistoryMenuItem);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(skyBlue);
        add(contentPane, BorderLayout.CENTER);

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setPreferredSize(new Dimension(280, 50));
        displayField.setFont(new Font("黑体", Font.PLAIN, 20));
        contentPane.add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBackground(skyBlue);

        String[] buttons = {
                "%", "CE", "C", "删除",
                "1/x", "平方", "√", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "±", "0", ".", "="
        };

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.addActionListener(this);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("黑体", Font.PLAIN, 18));
            buttonPanel.add(btn);
        }

        contentPane.add(buttonPanel, BorderLayout.CENTER);

        history = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "C":
                displayField.setText("");
                break;
            case "CE":
                displayField.setText("");
                num1 = 0;
                num2 = 0;
                operator = '\u0000';
                break;
            case "删除":
                String text = displayField.getText();
                if (!text.isEmpty()) {
                    displayField.setText(text.substring(0, text.length() - 1));
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                num1 = Double.parseDouble(displayField.getText());
                operator = command.charAt(0);
                displayField.setText("");
                break;
            case "=":
                num2 = Double.parseDouble(displayField.getText());
                double result = calculate(num1, num2, operator);
                displayField.setText(Double.toString(result));
                history.add(num1 + " " + operator + " " + num2 + " = " + result);
                break;
            case "±":
                double value = Double.parseDouble(displayField.getText());
                value = -value;
                displayField.setText(Double.toString(value));
                break;
            case "1/x":
                double reciprocal = 1 / Double.parseDouble(displayField.getText());
                displayField.setText(Double.toString(reciprocal));
                break;
            case "平方":
                double square = Math.pow(Double.parseDouble(displayField.getText()), 2);
                displayField.setText(Double.toString(square));
                break;
            case "√":
                double squareRoot = Math.sqrt(Double.parseDouble(displayField.getText()));
                displayField.setText(Double.toString(squareRoot));
                break;
            default:
                displayField.setText(displayField.getText() + command);
        }
    }

    private double calculate(double num1, double num2, char operator) {
        double result = 0;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;

        }
        return result;
    }

    public static void main(String[] args) {
        try {
            // 设置Look and Feel为系统默认
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}