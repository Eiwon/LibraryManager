package lib.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.Dialog;

import lib.Interface.OracleUserQuery;
import lib.controller.BookDAOImple;
import lib.controller.UserManager;
import lib.model.BookVO;
import lib.module.board.BoardDialog;
import lib.module.room.ReadingRoom;

import javax.swing.JPanel;

public class Library extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panelForAdmin;
	private JPanel panelForMember;
	private JButton btnLogin;
	private JButton btnLogout;
	private Dialog libDialog = null;
	
	public Library() {
		initialize();
	} // end Library

	private void initialize() {
		setBounds(300, 100, 1300, 850);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("도서 관리 프로그램 ver0.1");
		lblTitle.setBounds(30, 28, 168, 35);
		getContentPane().add(lblTitle);
		
		BookSearchComp bookManager = new BookSearchComp();
		bookManager.setBounds(100, 100, 1000, 630);
		bookManager.setVisible(true);
		getContentPane().add(bookManager);
		
		btnLogin = new JButton();
		btnLogin.setBounds(0, 0, 100, 50);
		btnLogin.setText("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(libDialog != null) {
					libDialog.dispose();
				}
				libDialog = new LoginDialog();
				libDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						btnRefresh();
					} // lf가 닫힐때 실행
				}); // end lf.addWindowListener
			}
		});
		getContentPane().add(btnLogin);
		
		btnLogout = new JButton();
		btnLogout.setBounds(1150, 750, 100, 50);
		btnLogout.setText("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManager.initUser();
				if(libDialog != null) {
					libDialog.dispose();	
				} // 열려있는 창 종료
				btnRefresh();
			}
		});
		getContentPane().add(btnLogout);
		
		panelForAdmin = new JPanel();
		panelForAdmin.setBounds(1101, 466, 149, 242);
		getContentPane().add(panelForAdmin);
		panelForAdmin.setLayout(null);
		
		JButton btnInsert = new JButton();
		btnInsert.setText("등록");
		btnInsert.setBounds(15, 10, 119, 46);
		panelForAdmin.add(btnInsert);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(libDialog != null) {
					libDialog.dispose();	
				}
				libDialog = new BookUpdateDialog();
			}
		});
		
		JButton btnUpdate = new JButton();
		btnUpdate.setText("수정");
		btnUpdate.setBounds(15, 66, 119, 46);
		panelForAdmin.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO selectedBook = bookManager.getSelectedBook();
				if(selectedBook != null) {
					if(libDialog != null) {
						libDialog.dispose();	
					}
					libDialog = new BookUpdateDialog(selectedBook);
				}
			}
		});
		
		JButton btnDelete = new JButton();
		btnDelete.setText("삭제");
		btnDelete.setBounds(15, 122, 119, 46);
		panelForAdmin.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO selectedBook = bookManager.getSelectedBook();
				if(selectedBook == null) {
					return;
				}
				int res = BookDAOImple.getInstance().deleteBook(selectedBook.getBookId());
				if( res == 1) {
					AlertDialog.printMsg("삭제에 성공했습니다.");
				}
			}
		});
		
		JButton btnDashboard = new JButton();
		btnDashboard.setText("현황");
		btnDashboard.setBounds(15, 186, 119, 46);
		btnDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				libDialog = new DashboardDialog();
			}
		});
		
		panelForAdmin.add(btnDashboard);
		
		panelForMember = new JPanel();
		panelForMember.setBounds(1101, 177, 149, 242);
		getContentPane().add(panelForMember);
		panelForMember.setLayout(null);
		
		JButton btnBorrow = new JButton();
		btnBorrow.setText("도서 대출");
		btnBorrow.setBounds(12, 10, 119, 46);
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO selectedBook = bookManager.getSelectedBook();
				if(selectedBook != null) {
					if(libDialog != null) {
						libDialog.dispose();	
					}
					libDialog = new CheckoutDialog(selectedBook);
				}
			}
		});
		
		panelForMember.add(btnBorrow);
		
		JButton btnMyInfo = new JButton();
		btnMyInfo.setText("내 정보");
		btnMyInfo.setBounds(12, 66, 119, 46);
		btnMyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(libDialog != null) {
					libDialog.dispose();	
				}
				libDialog = new MyInfoDialog();
			}
		});
		
		panelForMember.add(btnMyInfo);
		
		JButton btnBoard = new JButton();
		btnBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(libDialog != null) {
					libDialog.dispose();	
				}
				libDialog =  new BoardDialog();
			}
		});
		btnBoard.setText("문의하기");
		btnBoard.setBounds(12, 122, 119, 46);
		panelForMember.add(btnBoard);
		
		JButton btnReadingRoom = new JButton();
		btnReadingRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(libDialog != null) {
					libDialog.dispose();	
				}
				libDialog = new ReadingRoom(UserManager.getUserId(), UserManager.getUserAuth());
			}
		});
		btnReadingRoom.setText("열람실");
		btnReadingRoom.setBounds(12, 178, 119, 46);
		panelForMember.add(btnReadingRoom);
		
		btnRefresh();
	} // end initialize
	
	private void btnRefresh() {
		if(UserManager.getUserAuth().equals(OracleUserQuery.AUTH_GUEST)) {
			btnLogin.setVisible(true);
			btnLogout.setVisible(false);
			panelForMember.setVisible(false);
		}else {
			btnLogin.setVisible(false);
			btnLogout.setVisible(true);
			panelForMember.setVisible(true);
		}
		if(UserManager.getUserAuth().equals(OracleUserQuery.AUTH_ADMIN)) {
			panelForAdmin.setVisible(true);
		}else {
			panelForAdmin.setVisible(false);
		}
		revalidate();
		repaint();
	} // end btnRefresh
}
