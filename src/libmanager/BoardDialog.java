package libManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.PostVO;
import libManager.Controller.BoardDAOImple;
import libManager.Controller.UserManagementService;
import libManager.Interface.OracleUserQuery;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class BoardDialog extends JDialog {

	private JPanel panelList;
	private JPanel panelWrite;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] tableCol = {"글 번호", "분류", "제목", "작성자", "작성일", "조회수"};
	private Object[] post = new Object[tableCol.length];
	private static final int LISTMODE = 1;
	private static final int WRITEMODE = 2;
	private static final int READMODE = 3;
	private JTextField txtTitle;
	private JTextField txtContent;
	private BoardDAOImple dao;
	private JComboBox cbxWrite;
	private JPanel writeBtnSet;
	
	private ArrayList<PostVO> printedList;
	private PostVO selectedPost;
	private JPanel panelRead;
	private JTextField txtReadTitle;
	private JTextField txtReadContent;
	private JComboBox cbxRead;
	private JPanel readBtnSet;
	private JButton btnBack;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JPanel panelSearch;
	private JComboBox cbxTarget;
	private JTextField txtTarget;
	private JButton btnSearch;
	
	public BoardDialog() {
		dao = BoardDAOImple.getInstance();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 952, 646);
		getContentPane().setLayout(null);
		setWritePanel();
		setListPanel();
		setReadPanel();
		setMode(LISTMODE);
	}
	
	private void setWritePanel() {
		
		panelWrite = new JPanel();
		panelWrite.setBounds(0, 0, 936, 597);
		getContentPane().add(panelWrite);
		panelWrite.setLayout(null);
		
		txtContent = new JTextField();
		txtContent.setBounds(44, 109, 848, 345);
		panelWrite.add(txtContent);
		txtContent.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(156, 32, 736, 43);
		panelWrite.add(txtTitle);
		txtTitle.setColumns(10);
		
		writeBtnSet = new JPanel();
		writeBtnSet.setBounds(587, 503, 307, 51);
		panelWrite.add(writeBtnSet);
		writeBtnSet.setLayout(null);
		
		JButton btnSubmit = new JButton("글 등록");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPost();
				setMode(LISTMODE);
			}
		});
		btnSubmit.setBounds(12, 10, 125, 31);
		writeBtnSet.add(btnSubmit);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTitle.setText("");
				txtContent.setText("");
				setMode(LISTMODE);
			}
		});
		btnCancel.setBounds(175, 14, 120, 27);
		writeBtnSet.add(btnCancel);
		
		cbxWrite = new JComboBox();
		cbxWrite.addItem("도서 문의");
		cbxWrite.addItem("시설 문의");
		cbxWrite.addItem("공지 사항");
		cbxWrite.addItem("기타");
		cbxWrite.setBounds(44, 32, 100, 43);
		panelWrite.add(cbxWrite);
		
		JPanel panelPageList = new JPanel();
		panelPageList.setBounds(44, 464, 872, 38);
		panelWrite.add(panelPageList);
	}
	
	private void setReadPanel() {
		panelRead = new JPanel();
		panelRead.setBounds(0, 0, 924, 597);
		getContentPane().add(panelRead);
		panelRead.setLayout(null);
		
		txtReadTitle = new JTextField();
		txtReadTitle.setBounds(152, 23, 719, 54);
		panelRead.add(txtReadTitle);
		txtReadTitle.setColumns(10);
		
		txtReadContent = new JTextField();
		txtReadContent.setBounds(24, 81, 847, 425);
		panelRead.add(txtReadContent);
		txtReadContent.setColumns(10);
		
		cbxRead = new JComboBox();
		cbxRead.addItem("도서 문의");
		cbxRead.addItem("시설 문의");
		cbxRead.addItem("공지 사항");
		cbxRead.addItem("기타");
		cbxRead.setBounds(24, 23, 111, 54);
		panelRead.add(cbxRead);
		
		
		readBtnSet = new JPanel();
		readBtnSet.setBounds(507, 521, 367, 42);
		panelRead.add(readBtnSet);
		readBtnSet.setLayout(null);
		
		btnBack = new JButton("목록으로");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(LISTMODE);
			}
		});
		btnBack.setBounds(258, 10, 97, 23);
		readBtnSet.add(btnBack);
		
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePost();
				setMode(LISTMODE);
			}
		});
		btnDelete.setBounds(149, 10, 97, 23);
		readBtnSet.add(btnDelete);
		
		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePost();
				setMode(LISTMODE);
			}
		});
		btnUpdate.setBounds(44, 10, 97, 23);
		readBtnSet.add(btnUpdate);
		
	}
	
	private void setListPanel() {
		panelList = new JPanel();
		panelList.setBounds(0, 0, 938, 597);
		panelList.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelList);
		panelList.setLayout(null);
		
		JButton btnWrite = new JButton("글 작성");
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(WRITEMODE);
			}
		});
		btnWrite.setBounds(827, 564, 97, 23);
		panelList.add(btnWrite);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 25, 872, 471);
		panelList.add(scrollPane);
		
		tableModel = new DefaultTableModel(tableCol, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setBounds(12, 10, 912, 484);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				readPost();
			}
		});
		scrollPane.setViewportView(table);
		
		panelSearch = new JPanel();
		panelSearch.setBounds(44, 514, 504, 61);
		panelList.add(panelSearch);
		panelSearch.setLayout(null);
		
		cbxTarget = new JComboBox();
		cbxTarget.setBounds(12, 10, 85, 41);
		cbxTarget.addItem("제목");
		cbxTarget.addItem("분류");
		cbxTarget.addItem("작성자");
		panelSearch.add(cbxTarget);
		
		txtTarget = new JTextField();
		txtTarget.setBounds(108, 10, 271, 41);
		panelSearch.add(txtTarget);
		txtTarget.setColumns(10);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		btnSearch.setBounds(395, 10, 85, 41);
		panelSearch.add(btnSearch);
		
		printTable();
		
	}
	//"글 번호", "분류", "제목", "작성자", "작성일", "조회수"
	private void printTable() {
		printedList = dao.selectPage(1);
		tableModel.setRowCount(0);
		for(PostVO vo : printedList) {
			post[0] = String.valueOf(vo.getId());
			post[1] = vo.getTag();
			post[2] = vo.getTitle();
			post[3] = vo.getUserId();
			post[4] = vo.getWriteDate().toString().substring(0, 10);
			post[5] = vo.getViews();
			
			tableModel.addRow(post);
		}
	} // end printTable
	
	private void setMode(int mode) {
		switch(mode) {
		case LISTMODE :
			printTable();
			panelList.setVisible(true);
			panelWrite.setVisible(false);
			panelRead.setVisible(false);
			break;
		case WRITEMODE : 
			panelList.setVisible(false);
			panelWrite.setVisible(true);
			panelRead.setVisible(false);
			break;
		case READMODE :
			panelList.setVisible(false);
			panelWrite.setVisible(false);
			panelRead.setVisible(true);
			break;
		default : System.out.println("잘못된 모드");
		}
		
	} // setMode
	
	private void insertPost() {
		PostVO vo = new PostVO(0, txtTitle.getText(), txtContent.getText(), UserManagementService.getUserId(), 
				cbxWrite.getSelectedItem().toString(), 0, "");
		if(dao.insertPost(vo) == 1) {
			System.out.println("boardDialog : 글 등록 성공");
			txtTitle.setText("");
			txtContent.setText("");
		}
	}
	
	private void readPost() {
		int selectedPostId = printedList.get(table.getSelectedRow()).getId();
		setMode(READMODE);
		selectedPost = dao.selectPostById(selectedPostId);
		cbxRead.setSelectedItem(selectedPost.getTag());
		txtReadTitle.setText(selectedPost.getTitle());
		txtReadContent.setText(selectedPost.getContent());
		if(!UserManagementService.getUserAuth().equals(OracleUserQuery.AUTH_ADMIN)
				|| !UserManagementService.getUserId().equals(selectedPost.getUserId())) {
			// 관리자 또는 글의 작성자가 아닐 경우, 수정 삭제 불가
			cbxRead.setEditable(false);
			txtReadTitle.setEditable(false);
			txtReadContent.setEditable(false);
			btnUpdate.setVisible(false);
			btnDelete.setVisible(false);
		}
	}
	
	private void updatePost() {
		PostVO vo = new PostVO(selectedPost.getId(), txtReadTitle.getText(), txtReadContent.getText(), "", 
				cbxRead.getSelectedItem().toString(), 0, "");
		dao.updatePost(vo);
	}
	
	private void deletePost() {
		dao.deletePost(selectedPost.getId());
	}
	
	private void search() {
		String category = cbx
	}
}
