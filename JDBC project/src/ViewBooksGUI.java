import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewBooksGUI extends JDialog {
    private BookDAO dao = new BookDAO();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;

    public ViewBooksGUI(Frame owner) {
        super(owner, "View / Search Books", true);
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        top.add(searchField);

        JButton searchBtn = new JButton("Search");
        top.add(searchBtn);

        JButton refreshBtn = new JButton("Refresh");
        top.add(refreshBtn);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"BOOK ID", "Title", "Author", "Quantity"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadAll();

        searchBtn.addActionListener(e -> loadSearch());
        refreshBtn.addActionListener(e -> loadAll());

        setSize(700, 400);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void loadAll() {
        model.setRowCount(0);
        List<Book> list = dao.getAllBooks();

        for (Book b : list) {
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getQuantity()});
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadSearch() {
        String key = searchField.getText().trim();
        model.setRowCount(0);

        if (key.isEmpty()) {
            loadAll();
            return;
        }

        List<Book> list = dao.searchBooks(key);

        for (Book b : list) {
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getQuantity()});
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching books found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
