package libManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.BookVO;
import libManager.Controller.BookDAOImple;
import libManager.Interface.BookDAO;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BookUpdateDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtWriter;
	private JTextField txtCategory;
	private JTextField txtPublisher;
	private JTextField txtPubDate;
	private JButton btnSubmit;
	private JButton btnExit;
	private BookDAO dao;
	private JLabel lblState;
	private JTextField txtState;
	private BookVO updateTarget;
	
	public BookUpdateDialog() {
		System.out.println("insert mode");
		init();
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookVO vo = validCheck();
				if(vo != null)
					dao.insertBook(vo);
				dispose();
			}
		});
		
	} // end constructor for insert

	public BookUpdateDialog(BookVO vo) {
		System.out.println("update mode");
		init();
		updateTarget = vo;
		txtName.setText(updateTarget.getName());
		txtWriter.setText(updateTarget.getWriter());
		txtCategory.setText(updateTarget.getCategory());
		txtPublisher.setText(updateTarget.getPublisher());
		txtPubDate.setText(updateTarget.getPubDate());
		txtState.setText(updateTarget.getState());
		
		btnSubmit.setText("수정");
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookVO vo = validCheck();
				vo.setBookId(updateTarget.getBookId());
				if(vo != null)
					dao.updateBook(vo);
				dispose();
			}

		});
		
		
	} // end constructor for update

	public void init() {
		
		dao = BookDAOImple.getInstance();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);
		
		setBounds(100, 100, 635, 719);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 619, 647);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("제목");
		lblName.setBounds(38, 80, 155, 34);
		contentPanel.add(lblName);
		
		JLabel lblWriter = new JLabel("저자");
		lblWriter.setBounds(38, 160, 155, 34);
		contentPanel.add(lblWriter);
		
		JLabel lblCategory = new JLabel("장르");
		lblCategory.setBounds(38, 237, 155, 34);
		contentPanel.add(lblCategory);
		
		JLabel lblPublisher = new JLabel("출판사");
		lblPublisher.setBounds(38, 316, 155, 34);
		contentPanel.add(lblPublisher);
		
		JLabel lblPubDate = new JLabel("출판일");
		lblPubDate.setBounds(38, 384, 155, 34);
		contentPanel.add(lblPubDate);
		
		JLabel lblMode = new JLabel("도서 등록");
		lblMode.setBounds(150, 22, 277, 48);
		contentPanel.add(lblMode);
		
		txtName = new JTextField();
		txtName.setBounds(202, 80, 364, 40);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		txtWriter = new JTextField();
		txtWriter.setColumns(10);
		txtWriter.setBounds(202, 157, 364, 41);
		contentPanel.add(txtWriter);
		
		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		txtCategory.setBounds(202, 231, 364, 48);
		contentPanel.add(txtCategory);
		
		txtPublisher = new JTextField();
		txtPublisher.setColumns(10);
		txtPublisher.setBounds(202, 310, 364, 48);
		contentPanel.add(txtPublisher);
		
		txtPubDate = new JTextField();
		txtPubDate.setColumns(10);
		txtPubDate.setBounds(202, 378, 364, 48);
		contentPanel.add(txtPubDate);
		{
			btnSubmit = new JButton("등록");
			btnSubmit.setBounds(150, 541, 93, 66);
			contentPanel.add(btnSubmit);
			btnSubmit.setActionCommand("OK");
			getRootPane().setDefaultButton(btnSubmit);
		}
		{
			btnExit = new JButton("돌아가기");
			btnExit.setBounds(341, 541, 114, 66);
			contentPanel.add(btnExit);
			
			lblState = new JLabel("상태");
			lblState.setBounds(38, 462, 161, 40);
			contentPanel.add(lblState);
			
			txtState = new JTextField();
			txtState.setBounds(211, 462, 340, 54);
			contentPanel.add(txtState);
			txtState.setColumns(10);
			btnExit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
		}
	} // end init
	
	private BookVO validCheck() {
		String name = txtName.getText();
		String writer = txtWriter.getText();
		String category = txtCategory.getText();
		String publisher = txtPublisher.getText();
		String pubDate = txtPubDate.getText();
		String state = txtState.getText();
		
		if(name.length() * writer.length() * category.length() * publisher.length() * pubDate.length() * state.length() == 0) {
			System.out.println("모든 필드 입력 필요");
			return null;
		}
		return new BookVO(0, name, writer, category, publisher, pubDate, state, null);
	} // end validCheck
}
