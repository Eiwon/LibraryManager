package lib.view;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import lib.Interface.BookDAO;
import lib.Interface.OracleBookQuery;
import lib.Interface.UserDAO;
import lib.controller.BookDAOImple;
import lib.controller.UserDAOImple;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DashboardDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable tabOverdue;
	private UserDAO userDao;
	private BookDAO bookDao;
	private DefaultTableModel tableModel;
	private String[] tableCol = {"code", "도서명", "유저ID", "반납일", "유저 이름", "전화번호", "이메일"};
	private ArrayList<Object[]> printedList;
	
	public DashboardDialog() {
		userDao = UserDAOImple.getInstance();
		bookDao = BookDAOImple.getInstance();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(750, 300, 1052, 552);
		getContentPane().setLayout(null);
		
		JPanel panelOverdueInfo = new JPanel();
		panelOverdueInfo.setBounds(12, 10, 622, 473);
		getContentPane().add(panelOverdueInfo);
		panelOverdueInfo.setLayout(null);
		
		JLabel lblOverdueList = new JLabel("연체 중인 도서 목록");
		lblOverdueList.setHorizontalAlignment(SwingConstants.CENTER);
		lblOverdueList.setBounds(38, 10, 487, 40);
		panelOverdueInfo.add(lblOverdueList);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 48, 598, 415);
		panelOverdueInfo.add(scrollPane);
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			private static final long serialVersionUID = 2L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabOverdue = new JTable(tableModel);
		scrollPane.setViewportView(tabOverdue);
		tabOverdue.setRowHeight(30);
		tabOverdue.getColumnModel().getColumn(0).setPreferredWidth(25);
		tabOverdue.getColumnModel().getColumn(1).setPreferredWidth(160);
		
		JLabel lblUserNum = new JLabel("회원 수 : ");
		lblUserNum.setFont(new Font("굴림", Font.PLAIN, 25));
		lblUserNum.setBounds(646, 10, 181, 48);
		getContentPane().add(lblUserNum);
		lblUserNum.setText("회원 수 : " + userDao.getUserNum());

		JLabel lblBookNum = new JLabel("도서 수 : ");
		lblBookNum.setFont(new Font("굴림", Font.PLAIN, 25));
		lblBookNum.setBounds(646, 68, 181, 48);
		getContentPane().add(lblBookNum);
		lblBookNum.setText("도서 수 : " + bookDao.getBookNum());
		
		JButton btnRelease = new JButton("차단 해제");
		btnRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedUId = printedList.get(tabOverdue.getSelectedRow())[2].toString();
				int selectedBId = Integer.parseInt(printedList.get(tabOverdue.getSelectedRow())[0].toString());
				releaseUser(selectedUId, selectedBId);
				printList();
			}
		});
		btnRelease.setBounds(646, 431, 113, 41);
		getContentPane().add(btnRelease);
		
		printList();
		
	} // end DashboardDialog
	
	public void printList() {
		System.out.println("DashboardDialog : printList()");
		printedList = userDao.selectOverdueBook();
		tableModel.setRowCount(0);
		
		for(Object[] obj : printedList) {
			tableModel.addRow(obj);
		}
	} // end printList
	
	private void releaseUser(String selectedUId, int selectedBId) {
		System.out.println("DashboardDialog : releaseUser()");
		int res1 = bookDao.updateByBookId(OracleBookQuery.BOOK_STATE_SET, selectedBId);
		if(res1 == 1) {
			System.out.println("책 반납 성공");
			int res2 = bookDao.deleteByBookId(selectedBId, OracleBookQuery.BOOK_STATE_OUT);
			if(res2 == 1) {
				System.out.println("체크아웃 테이블에서 삭제 성공");
			}
			int res3 = userDao.deleteFromBlackList(selectedUId);
			if(res3 == 1) {
				System.out.println("블랙리스트에서 삭제 성공");
			}
		}
	} // end releaseUser
}
