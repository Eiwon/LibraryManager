package libManager;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class BookManagerComp extends JComponent {
	private JTextField txtSearch;
	private DefaultTableModel tableModel;
	private JTable tabSearch;
	private String[] tableCol = {"도서 코드", "도서 명", "저자", "카테고리", "출판사", "출판일", "상태"};
	private Object[] books = new Object[tableCol.length];
	private static BookDAOImple dao;
	
	public BookManagerComp() {
		dao = BookDAOImple.getInstance();
		
		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable(dao.selectByCategory());
			}

		});
		btnSearch.setBounds(816, 23, 158, 44);
		add(btnSearch);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(145, 24, 659, 44);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JComboBox cbxCategory = new JComboBox();
		cbxCategory.setBounds(38, 23, 95, 44);
		add(cbxCategory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 78, 939, 529);
		add(scrollPane);
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabSearch = new JTable();
		
		scrollPane.setViewportView(tabSearch);

	}
	
	private void printTable(ArrayList<BookVO> list) {
		
		tableModel.setRowCount(0);
		
		for(int i = 0; i < list.size(); i++) {
			books[0] = String.valueOf(list.get(i).getBookId());
			books[1] = list.get(1).getName();
			books[2] = list.get(2).getWriter();
			books[3] = list.get(3).getCategory();
			books[4] = list.get(4).getPublisher();
			books[5] = list.get(5).getPubDate();
			books[6] = list.get(6).getState();
			
			tableModel.addRow(books);
		}
		
		
	}
}
