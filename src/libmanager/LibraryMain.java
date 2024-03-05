package libManager;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LibraryMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryMain window = new LibraryMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 932, 792);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("도서 관리 프로그램 ver0.1");
		lblTitle.setBounds(30, 28, 168, 35);
		frame.getContentPane().add(lblTitle);
		
		JButton btnLogIn = new JButton("New button");
		btnLogIn.setBounds(741, 720, 163, 23);
		frame.getContentPane().add(btnLogIn);
		
		JButton btnSearch = new JButton("도서 검색");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchFrame sf = new SearchFrame();
				sf.setVisible(true);
				
			}
		});
		btnSearch.setBounds(132, 172, 605, 142);
		frame.getContentPane().add(btnSearch);
	}
}
