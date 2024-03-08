package libManager;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Model.BookVO;
import libManager.Controller.BookDAOImple;
import libManager.Controller.UserDAOImple;
import libManager.Interface.OracleBookQuery;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MyInfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private BookDAOImple dao;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] tableCol = {"현재 상태", "도서 코드", "제목", "저자", "카테고리", "상태", "대출/예약일", "반납/예약만료일"};
	private Object[] info = new Object[8];
	ArrayList<ArrayList<String>> printedList;
	
	public MyInfoDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);
		dao = BookDAOImple.getInstance();
		setBounds(100, 100, 904, 547);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 475, 888, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			
			JButton btnReturn = new JButton("반납하기");
			btnReturn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					returnBook();
				}
			});
			buttonPane.add(btnReturn);
			buttonPane.add(contentPanel);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.setLayout(null);
			{
				JButton btnExtend = new JButton("대출 기간 연장");
				btnExtend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						extendBook();
					}
				});
				buttonPane.add(btnExtend);
				
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 81, 785, 246);
		getContentPane().add(scrollPane);
		
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		printTable();
		
	} // end MyInfoDialog
	
	private void printTable() {
		// 입력받은 list를 table 컴포넌트에 출력
		printedList = dao.selectAllInfoById(UserDAOImple.getCurrentUser().getUserId());
		tableModel.setRowCount(0);
		ArrayList<String> ls;
		for(int i = 0; i < printedList.size(); i++) {
			ls = printedList.get(i);
			info[0] = ls.get(0);
			info[1] = ls.get(1);
			info[2] = ls.get(2);
			info[3] = ls.get(3);
			info[4] = ls.get(4);
			info[5] = ls.get(5);
			info[6] = ls.get(6).substring(0, 10);
			info[7] = ls.get(7).substring(0, 10);
			
			tableModel.addRow(info);
		}
	} // end printTable 
	
	private void returnBook() {
		int row = table.getSelectedRow();
		if(row >= 0) {
			int bookId = Integer.parseInt(printedList.get(row).get(1));
			if(printedList.get(row).get(5).equals("OUT")) {
				dao.updateByBookId(OracleBookQuery.BOOK_STATE_SET, bookId);
			}else {
				dao.updateByBookId(OracleBookQuery.BOOK_STATE_RSVSET, bookId);
			}
			if(dao.deleteByBookId(bookId, printedList.get(row).get(0)) == 1) {
				System.out.println("반납 완료");
				printTable();
			}
			dispose();
		}
		
	} // end returnBook
	
	private void extendBook() {
		int row = table.getSelectedRow();
		if(row >= 0) {
			ArrayList<String> targetInfo = printedList.get(row);
			LocalDateTime checkoutTime = LocalDateTime.parse(targetInfo.get(6).replace(' ', 'T'));
			LocalDateTime checkinTime = LocalDateTime.parse(targetInfo.get(7).replace(' ', 'T'));
			long day = ChronoUnit.DAYS.between(checkoutTime, checkinTime);
		
			if(targetInfo.get(5).equals(OracleBookQuery.BOOK_STATE_OUT) && day < 15) {
				dao.updateCheckinDate(Integer.parseInt(targetInfo.get(1)), checkinTime.plusDays(7).toString().substring(0, 10));
			}
		}
	} // end extendBook
}
