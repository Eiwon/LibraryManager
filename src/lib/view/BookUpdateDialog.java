package lib.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import lib.Interface.BookDAO;
import lib.Interface.OracleBookQuery;
import lib.controller.BookDAOImple;
import lib.controller.ImageManager;
import lib.model.BookVO;
import lib.module.board.OracleBoardQuery;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

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
	private BookVO updateTarget;
	private JButton btnAddImg;
	private JLabel lblImg;
	private ImageManager im;
	private File selectedFile = null;
	private JComboBox cbxState;
	
	public BookUpdateDialog() {
		System.out.println("insert mode");
		init();
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookVO vo = validCheck();
				if(vo != null) {
					dao.insertBook(vo);
					if(selectedFile != null)
						im.putImage(selectedFile);
				}
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
		txtPubDate.setText(updateTarget.getPubDate().toLocalDate().toString());
		cbxState.setSelectedItem(updateTarget.getState());
		
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
		im = ImageManager.getInstance();
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
		lblName.setBounds(38, 82, 155, 34);
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
			getRootPane().setDefaultButton(btnSubmit);
		}
		{
			btnExit = new JButton("돌아가기");
			btnExit.setBounds(341, 541, 114, 66);
			contentPanel.add(btnExit);
			
			lblState = new JLabel("상태");
			lblState.setBounds(38, 462, 161, 40);
			contentPanel.add(lblState);
			
			btnAddImg = new JButton("사진 등록");
			btnAddImg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					int result = chooser.showOpenDialog(null);
					if(result == JFileChooser.APPROVE_OPTION) {
						selectedFile = chooser.getSelectedFile();
						lblImg.setIcon(im.convToIcon(selectedFile));
					}
				}
			});
			btnAddImg.setBounds(469, 472, 97, 48);
			contentPanel.add(btnAddImg);
			
			lblImg = new JLabel();
			lblImg.setBounds(330, 436, 97, 95);
			contentPanel.add(lblImg);
			
			cbxState = new JComboBox();
			cbxState.addItem(OracleBookQuery.BOOK_STATE_SET);
			cbxState.addItem(OracleBookQuery.BOOK_STATE_OUT);
			cbxState.addItem(OracleBookQuery.BOOK_STATE_RSV);
			cbxState.addItem(OracleBookQuery.BOOK_STATE_RSVSET);
			cbxState.addItem(OracleBookQuery.BOOK_STATE_LOST);
			cbxState.setBounds(135, 462, 114, 40);
			contentPanel.add(cbxState);
			
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
		
		String state = cbxState.getSelectedItem().toString();
		String img = null;
		
		if(name.length() * writer.length() * category.length() * publisher.length() * txtPubDate.getText().length() * state.length() == 0) {
			new AlertDialog("모든 필드 입력 필요");
			return null;
		}
		if(selectedFile != null) {
			img = selectedFile.getName();
		}
		LocalDate date = LocalDate.parse(txtPubDate.getText(), DateTimeFormatter.ofPattern("yyyyMMdd"));
		LocalDateTime pubDate = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
		
		return new BookVO(0, name, writer, category, publisher, pubDate, state, img);
	} // end validCheck
}
