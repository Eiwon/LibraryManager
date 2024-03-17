package lib.module.board;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import lib.Interface.OracleUserQuery;
import lib.controller.UserManager;
import lib.view.AlertDialog;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class ReplyComp extends JComponent {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JPanel panelSbtnSet;
	private JTextArea txtReply;
	private BoardDAO dao;

	private DefaultTableModel tableModel;
	private String[] tableCol = {"ID", "내용", "작성일자"};
	private Object[] rowObj = new Object[3];
	private int currentPage = 1;
	private int postId;
	private ArrayList<ReplyVO> printedList = new ArrayList<>();
	
	public ReplyComp() {
		dao = BoardDAOImple.getInstance();
		JLabel lblReply = new JLabel("댓글");
		lblReply.setBounds(12, 10, 150, 30);
		add(lblReply);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 828, 175);
		add(scrollPane);
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			private static final long serialVersionUID = 2L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if(hasAuth(selectedRow)) {
					String content = printedList.get(selectedRow).getContent();
					txtReply.setText(content);
					panelSbtnSet.setVisible(true);
				}else {
					panelSbtnSet.setVisible(false);
				}
			}
		});
		
		scrollPane.setViewportView(table);
		
		txtReply = new JTextArea();
		txtReply.setBounds(12, 210, 495, 30);
		add(txtReply);
		
		JPanel panelBtnSet = new JPanel();
		panelBtnSet.setBounds(634, 200, 204, 43);
		add(panelBtnSet);
		panelBtnSet.setLayout(null);
		
		JButton btnInsert = new JButton("등록");
		btnInsert.setBounds(127, 0, 71, 43);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = txtReply.getText();
				if (content.length() > 0) {
					insertReply(content);
					printReply(postId);
					txtReply.setText("");
				}
			}
		});
		panelBtnSet.add(btnInsert);
		
		
		JButton btnToNext = new JButton("다음");
		btnToNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage++;
				printReply(postId);
			}
		});
		btnToNext.setBounds(62, 0, 65, 43);
		panelBtnSet.add(btnToNext);
		
		JButton btnToPrev = new JButton("이전");
		btnToPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage = Math.max(1, currentPage -1);
				printReply(postId);
			}
		});
		btnToPrev.setBounds(0, 0, 65, 43);
		panelBtnSet.add(btnToPrev);
		
		panelSbtnSet = new JPanel();
		panelSbtnSet.setBounds(509, 161, 123, 36);
		panelSbtnSet.setVisible(false);
		add(panelSbtnSet);
		panelSbtnSet.setLayout(null);
		
		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateReply();
				txtReply.setText("");
				printReply(postId);
			}
		});
		btnUpdate.setBounds(0, 0, 62, 36);
		panelSbtnSet.add(btnUpdate);
		
		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteReply();
				txtReply.setText("");
				printReply(postId);
			}
		});
		btnDelete.setBounds(61, 0, 62, 36);
		panelSbtnSet.add(btnDelete);
	} // end ReplyComp
	
	private void insertReply(String content) {
		ReplyVO vo = new ReplyVO(0, postId, UserManager.getUserId(), content,
				LocalDateTime.now());
		dao.insertReply(vo);
	} // end insertReply
	
	private void updateReply() {
		int selectedRow = table.getSelectedRow();
		int replyId = printedList.get(selectedRow).getId();
		String content = txtReply.getText();
		ReplyVO vo = new ReplyVO(replyId, 0, "", content, null);
		int res = dao.updateReply(vo);
		if(res == 1) {
			AlertDialog.printMsg("수정 성공");
		}
	} // end updateReply
	
	private void deleteReply() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow >= 0) {
			int replyId = printedList.get(selectedRow).getId();
			int res = dao.deleteReply(replyId);
			if(res == 1) {
				AlertDialog.printMsg("삭제 성공");
			}
		}
	} // end deleteReply
	
	public void printReply(int postId) {
		this.postId = postId;
		ArrayList<ReplyVO> list = dao.selectReplyById(postId, currentPage);
		
		if(list.size() == 0 && currentPage > 1) {
			currentPage = Math.max(1, currentPage -1);
		}else {
			printedList = list;
		}
		
		tableModel.setRowCount(0);
		
		for(ReplyVO vo : printedList) {
			rowObj[0] = vo.getUserId();
			rowObj[1] = vo.getContent();
			rowObj[2] = vo.getWriteDate().toString();
			tableModel.addRow(rowObj);
		}
	} // end printReply
	
	public boolean hasAuth(int selectedRow) {
		if(UserManager.getUserAuth().equals(OracleUserQuery.AUTH_ADMIN) 
				|| printedList.get(selectedRow).getUserId().equals(UserManager.getUserId())) {
			return true; // 현재 계정 권한이 관리자 권한이거나 userId가 선택된 댓글 작성자 userId와 같을 경우
		}else return false;
	} // end hasAuth
}
