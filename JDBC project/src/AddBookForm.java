import java.awt.*;
import javax.swing.*;

public class AddBookForm extends JDialog {
    private JTextField idField, titleField, authorField, qtyField;
    private BookDAO dao = new BookDAO();

    public AddBookForm(Frame owner) {
        super(owner, "Add Book", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(10);
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        qtyField = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Book ID:"), gbc);
        gbc.gridx = 1; add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; add(qtyField, gbc);

        JButton addBtn = new JButton("Add Book");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 4; add(addBtn, gbc);
        gbc.gridx = 1; add(cancelBtn, gbc);

        addBtn.addActionListener(e -> onAdd());
        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void onAdd() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int qty = Integer.parseInt(qtyField.getText().trim());

            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Title and Author cannot be empty.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (qty < 0) {
                JOptionPane.showMessageDialog(this,
                        "Quantity cannot be negative.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = new Book(id, title, author, qty);
            boolean ok = dao.addBook(book);

            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Book added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to add book. Book ID may already exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for Book ID and Quantity.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
