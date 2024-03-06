package libManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SearchFrame extends JFrame{

	private JPanel contentPane;

	
	public SearchFrame() {
		initialize();
	}
	
	private void initialize() {
		setBounds(100, 100, 932, 792);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("hello");
		lblNewLabel.setBounds(191, 74, 366, 101);
		getContentPane().add(lblNewLabel);
	}


}
