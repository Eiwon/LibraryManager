package lib.view;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import lib.Interface.OracleBookQuery;
import lib.controller.BookDAOImple;
import lib.model.BookVO;

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JPanel;


public class BookManagerComp extends JComponent {
	private static final String SEARCHALL = "전체";

	private JTextField txtSearch;
	private DefaultTableModel tableModel;
	private JTable tabSearch;
	private String[] tableCol = {"도서 코드", "도서 명", "저자", "카테고리", "출판사", "출판일", "상태"};
	private Object[] books = new Object[tableCol.length];
	private static BookDAOImple dao;
	private JComboBox cbxTag;
	private int selectedBookId = -1;
	private ArrayList<BookVO> printedList;
	private int currentPage = 1;
	private String searchTarget = "";
	private String searchMode = SEARCHALL;
	private JPanel panelMove;
	private JButton btnToPrev;
	private JButton btnToNext;
	
	public BookManagerComp() {
		dao = BookDAOImple.getInstance();
		
		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtSearch.getText().length() == 0) {
					searchMode = SEARCHALL;
				}else {
					searchMode = cbxTag.getSelectedItem().toString();
				}
				searchTarget = txtSearch.getText();
				currentPage = 1;
				search();
			}

		});
		btnSearch.setBounds(816, 23, 158, 44);
		add(btnSearch);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(145, 24, 659, 44);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		cbxTag = new JComboBox();
		cbxTag.setBounds(38, 23, 95, 44);
		cbxTag.addItem(OracleBookQuery.NAME);
		cbxTag.addItem(OracleBookQuery.WRITER);
		cbxTag.addItem(OracleBookQuery.CATEGORY);
		add(cbxTag);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 78, 939, 452);
		add(scrollPane);
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabSearch = new JTable(tableModel);
		
		scrollPane.setViewportView(tabSearch);
		
		panelMove = new JPanel();
		panelMove.setBounds(367, 540, 230, 44);
		add(panelMove);
		panelMove.setLayout(null);
		
		btnToPrev = new JButton("이전");
		btnToPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage = Math.max(1, currentPage -1);
				search();
			}
		});
		btnToPrev.setBounds(12, 10, 97, 23);
		panelMove.add(btnToPrev);
		
		btnToNext = new JButton("다음");
		btnToNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage++;
				search();
			}
		});
		btnToNext.setBounds(121, 10, 97, 23);
		panelMove.add(btnToNext);

	}
	
	private void search() {
		if(searchMode.equals(SEARCHALL)) {
			printedList = dao.selectAll(currentPage);
		}else {
			printedList = dao.selectByValue(searchMode, searchTarget, currentPage);
		}
		printTable(printedList);
	}
	
	
	private void printTable(ArrayList<BookVO> list) {
		// 입력받은 list를 table 컴포넌트에 출력
		tableModel.setRowCount(0);
		BookVO vo;
		for(int i = 0; i < list.size(); i++) {
			vo = list.get(i);
			books[0] = String.valueOf(vo.getBookId());
			books[1] = vo.getName();
			books[2] = vo.getWriter();
			books[3] = vo.getCategory();
			books[4] = vo.getPublisher();
			books[5] = vo.getPubDate().toLocalDate().toString();
			books[6] = vo.getState();
			
			tableModel.addRow(books);
		}
	} // end printTable 
	
	public BookVO getSelectedBook() {
		// 테이블의 원소가 선택된 상태면, 선택된 BookVO 반환
		// 선택되지 않은 상태면, null 반환
		int row = tabSearch.getSelectedRow();
		if( row >= 0) {
			return printedList.get(row);
		}else return null;
	} // end getSelectedBook
	
	public ArrayList<BookVO> getPrintedList(){
		// 테이블의 출력 리스트 반환
		return printedList;
	} // end getPrintedList
	
}
