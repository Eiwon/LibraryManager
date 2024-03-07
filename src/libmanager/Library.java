package libManager;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import Model.BookVO;
import libManager.Controller.BookDAOImple;
import libManager.Controller.UserDAOImple;
import libManager.Interface.OracleUserQuery;
import libManager.Interface.UISize;
import libManager.Interface.UserDAO;

import javax.swing.JPanel;

public class Library implements UISize{
	private JFrame frame;
	private JButton btnLogin;
	private JButton btnLogout;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JPanel panelForAdmin;
	private BookUpdateDialog budInsert;
	private JPanel panelForMember;
	private JButton btnBorrow;
	private JButton btnReserve;
	private JButton btnMyInfo;
	public Library() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(mainFrameX, mainFrameY, mainFrameW, mainFrameH);
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
				UserDAOImple.setCurrentUser(UserDAO.defaultUser);
				btnRefresh();
			}
		});
		frame.getContentPane().add(btnLogout);
		
		panelForAdmin = new JPanel();
		panelForAdmin.setBounds(1101, 522, 149, 186);
		frame.getContentPane().add(panelForAdmin);
		panelForAdmin.setLayout(null);
		
		btnInsert = new JButton();
		btnInsert.setBounds(15, 10, 119, 46);
		panelForAdmin.add(btnInsert);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				budInsert = new BookUpdateDialog();
			}
		});
		btnInsert.setText("등록");
		
		btnUpdate = new JButton();
		btnUpdate.setBounds(15, 66, 119, 46);
		panelForAdmin.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO vo = bookManager.getSelectedBook();
				if(vo != null)
					budInsert = new BookUpdateDialog(vo);
			}
		});
		btnUpdate.setText("수정");
		
		btnDelete = new JButton();
		btnDelete.setBounds(15, 122, 119, 46);
		panelForAdmin.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookDAOImple.getInstance().deleteBook(bookManager.getSelectedBook().getBookId());
			}
		});
		btnDelete.setText("삭제");
		
		panelForMember = new JPanel();
		panelForMember.setBounds(1101, 177, 149, 186);
		frame.getContentPane().add(panelForMember);
		panelForMember.setLayout(null);
		
		btnBorrow = new JButton();
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookVO vo = bookManager.getSelectedBook();
				if(vo != null) {
					new CheckoutDialog(vo);
				}
			}
		});
		btnBorrow.setText("도서 대출");
		btnBorrow.setBounds(12, 10, 119, 46);
		panelForMember.add(btnBorrow);
		
		btnReserve = new JButton();
		btnReserve.setText("예약");
		btnReserve.setBounds(12, 67, 119, 46);
		panelForMember.add(btnReserve);
		
		btnMyInfo = new JButton();
		btnMyInfo.setText("내 정보");
		btnMyInfo.setBounds(12, 123, 119, 46);
		panelForMember.add(btnMyInfo);
		
		btnRefresh();
		
		
	}
	
	private void btnRefresh() {
		if(UserDAOImple.getCurrentUser().equals(UserDAO.defaultUser)) {
			btnLogin.setVisible(true);
			btnLogout.setVisible(false);
		}else {
			btnLogin.setVisible(false);
			btnLogout.setVisible(true);
		}
		if(UserDAOImple.getCurrentUser().getAuth().equals(OracleUserQuery.AUTH_ADMIN)) {
			panelForAdmin.setVisible(true);
		}else {
			panelForAdmin.setVisible(false);
		}
		
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
}
