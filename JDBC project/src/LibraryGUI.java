import java.awt.*;
import javax.swing.*;

public class LibraryGUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Library - Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(420, 220);
            frame.setLocationRelativeTo(null);

            JPanel p = new JPanel();
            p.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JButton add = new JButton("Add Book (GUI)");
            JButton view = new JButton("View / Search Books");
            JButton console = new JButton("Open Console Menu");
            JButton exit = new JButton("Exit");

            gbc.gridx = 0; gbc.gridy = 0; p.add(add, gbc);
            gbc.gridx = 0; gbc.gridy = 1; p.add(view, gbc);
            gbc.gridx = 0; gbc.gridy = 2; p.add(console, gbc);
            gbc.gridx = 0; gbc.gridy = 3; p.add(exit, gbc);

            add.addActionListener(e -> new AddBookForm(frame));
            view.addActionListener(e -> new ViewBooksGUI(frame));
            console.addActionListener(e -> new Thread(() -> LibraryManagement.main(new String[]{})).start());
            exit.addActionListener(e -> System.exit(0));

            frame.getContentPane().add(p, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
