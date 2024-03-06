package libManager;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Library implements UISize{
	//public UserVO curUser = null;
	private JFrame frame;
	private JButton btnLogin;
	private JButton btnLogout;
	
	public Library() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(mainFrameX, mainFrameY, mainFrameW, mainFrameH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblTitle = new JLabel("도서 관리 프로그램 ver0.1");
		lblTitle.setBounds(30, 28, 168, 35);
		frame.getContentPane().add(lblTitle);
		
		BookManagerComp bookManager = new BookManagerComp();
		bookManager.setBounds(mainFrameX -200, mainFrameY, mainFrameW, mainFrameH);
		bookManager.setVisible(true);
		frame.getContentPane().add(bookManager);
		
		btnLogin = new JButton();
		
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
		btnLogin.setBounds(1150, 750, btnWidth, btnHeight);
		frame.getContentPane().add(btnLogin);
		
		btnLogout = new JButton();
		
		btnLogout.setText("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDAOImple.setCurrentUser(null);
				btnRefresh();
			}
		});
		btnLogout.setBounds(1150, 750, btnWidth, btnHeight);
		frame.getContentPane().add(btnLogout);
		
		btnRefresh();
		
		
	}
	
	private void btnRefresh() {
		if(UserDAOImple.getCurrentUser() == null) {
			btnLogin.setVisible(true);
			btnLogout.setVisible(false);
		}else {
			btnLogin.setVisible(false);
			btnLogout.setVisible(true);
		}
	}
}
