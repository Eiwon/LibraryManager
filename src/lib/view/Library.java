package lib.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog;

import lib.Interface.OracleUserQuery;
import lib.controller.BookDAOImple;
import lib.controller.UserManager;
import lib.model.BookVO;
import lib.module.board.BoardDialog;
import lib.module.room.ReadingRoom;

import javax.swing.JPanel;

public class Library{
	private JFrame frame;
	private JButton btnLogin;
	private JButton btnLogout;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JPanel panelForAdmin;
	private JPanel panelForMember;
	private JButton btnBorrow;
	private JButton btnMyInfo;
	private JButton btnReadingRoom;
	private Dialog libDialog;
	
	public Library() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 100, 1300, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("도서 관리 프로그램 ver0.1");
		lblTitle.setBounds(30, 28, 168, 35);
		frame.getContentPane().add(lblTitle);
		
		BookManagerComp bookManager = new BookManagerComp();
		bookManager.setBounds(100, 100, 1000, 630);
		bookManager.setVisible(true);
		frame.getContentPane().add(bookManager);
		
		btnLogin = new JButton();
		btnLogin.setBounds(1150, 750, 100, 50);
		
		btnLogin.setText("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame lf = new LoginFrame();
				lf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				lf.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {}
					
					@Override
					public void windowIconified(WindowEvent e) {}
					
					@Override
					public void windowDeiconified(WindowEvent e) {}
					
					@Override
					public void windowDeactivated(WindowEvent e) {}
					
					@Override
					public void windowClosing(WindowEvent e) {}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnRefresh();
					}
					
					@Override
					public void windowActivated(WindowEvent e) {}
				});
					lf.setVisible(true);
				
			}
		});
		frame.getContentPane().add(btnLogin);
		
		btnLogout = new JButton();
		btnLogout.setBounds(1150, 750, 100, 50);
		btnLogout.setText("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManager.initUser();
				if(libDialog != null) {
					libDialog.dispose();	
				}
				btnRefresh();
			}
		});
		frame.getContentPane().add(btnLogout);
		
		panelForAdmin = new JPanel();
		panelForAdmin.setBounds(1101, 522, 149, 186);
		frame.getContentPane().add(panelForAdmin);
		panelForAdmin.setLayout(null);
		
		btnInsert = new JButton();
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
		
		
		btnUpdate = new JButton();
		btnUpdate.setText("수정");
		btnUpdate.setBounds(15, 66, 119, 46);
		panelForAdmin.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO vo = bookManager.getSelectedBook();
				if(vo != null) {
					if(libDialog != null) {
						libDialog.dispose();	
					}
					libDialog = new BookUpdateDialog(vo);
				}
			}
		});
		
		
		btnDelete = new JButton();
		btnDelete.setText("삭제");
		btnDelete.setBounds(15, 122, 119, 46);
		panelForAdmin.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(BookDAOImple.getInstance().deleteBook(bookManager.getSelectedBook().getBookId()) == 1) {
					new AlertDialog("삭제에 성공했습니다.");
				}
			}
		});
		
		
		panelForMember = new JPanel();
		panelForMember.setBounds(1101, 177, 149, 242);
		frame.getContentPane().add(panelForMember);
		panelForMember.setLayout(null);
		
		btnBorrow = new JButton();
		btnBorrow.setText("도서 대출");
		btnBorrow.setBounds(12, 10, 119, 46);
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO vo = bookManager.getSelectedBook();
				if(vo != null) {
					if(libDialog != null) {
						libDialog.dispose();	
					}
					libDialog = new CheckoutDialog(vo);
				}
			}
		});
		
		panelForMember.add(btnBorrow);
		
		btnMyInfo = new JButton();
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
		
		btnReadingRoom = new JButton();
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
		
		
	}
	
	private void btnRefresh() {
		if(UserManager.getUserAuth().equals("GUEST")) {
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
		
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
}
