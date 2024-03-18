package lib.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import lib.Interface.BookDAO;
import lib.Interface.OracleBookQuery;
import lib.controller.BookDAOImple;
import lib.controller.ImageManager;
import lib.model.BookVO;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class BookUpdateDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtWriter;
	private JTextField txtCategory;
	private JTextField txtPublisher;
	private JTextField txtYear;
	private JTextField txtMonth;
	private JTextField txtDay;
	private JButton btnSubmit;
	private BookDAO dao;
	private BookVO updateTarget;
	private ImageManager im;
	private File selectedFile = null;
	private JComboBox<String> cbxState;
	
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
		LocalDateTime pubDate = vo.getPubDate();
		txtYear.setText(String.valueOf(pubDate.getYear()));
		txtMonth.setText(String.valueOf(pubDate.getMonthValue()));
		txtDay.setText(String.valueOf(pubDate.getDayOfMonth()));
		cbxState.setSelectedItem(updateTarget.getState());
		
		btnSubmit.setText("수정");
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookVO vo = validCheck();
				if(vo != null) {
					vo.setBookId(updateTarget.getBookId());
					dao.updateBook(vo);
				}
				dispose();
			}

		});
	} // end constructor for update

	public void init() {
		
		dao = BookDAOImple.getInstance();
		im = ImageManager.getInstance();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		setBounds(750, 300, 635, 719);
		getContentPane().setLayout(null);
		
		JLabel lblMode = new JLabel("도서 등록");
		lblMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblMode.setBounds(150, 22, 277, 48);
		getContentPane().add(lblMode);
			
		btnSubmit = new JButton("등록");
		btnSubmit.setBounds(153, 519, 93, 66);
		getContentPane().add(btnSubmit);
			
		JButton btnExit = new JButton("돌아가기");
		btnExit.setBounds(327, 519, 114, 66);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});	
		getContentPane().add(btnExit);
			
		JLabel lblState = new JLabel("상태");
		lblState.setBounds(38, 404, 161, 40);
		getContentPane().add(lblState);
		
		String[] stateOpt = { 
				OracleBookQuery.BOOK_STATE_SET, 
				OracleBookQuery.BOOK_STATE_OUT,
				OracleBookQuery.BOOK_STATE_RSV,
				OracleBookQuery.BOOK_STATE_RSVSET,
				OracleBookQuery.BOOK_STATE_LOST
				};
		cbxState = new JComboBox<>(stateOpt);
		cbxState.setBounds(135, 402, 114, 40);
		getContentPane().add(cbxState);
			
		JPanel panelPubDate = new JPanel();
		panelPubDate.setBounds(38, 320, 541, 54);
		getContentPane().add(panelPubDate);
		panelPubDate.setLayout(null);

		JLabel lblYear = new JLabel("년");
		lblYear.setBounds(308, 16, 27, 29);
		panelPubDate.add(lblYear);
		
		JLabel lblMonth = new JLabel("월");
		lblMonth.setBounds(404, 13, 34, 29);
		panelPubDate.add(lblMonth);
		
		JLabel lblDay = new JLabel("일");
		lblDay.setBounds(507, 13, 34, 34);
		panelPubDate.add(lblDay);
			
		JLabel lblPubDate = new JLabel("출판일");
		lblPubDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblPubDate.setBounds(20, 10, 65, 40);
		panelPubDate.add(lblPubDate);
			
		txtYear = new JTextField();
		txtYear.setHorizontalAlignment(SwingConstants.CENTER);
		txtYear.setBounds(205, 17, 91, 27);
		panelPubDate.add(txtYear);
		txtYear.setColumns(10);
			
		txtMonth = new JTextField();
		txtMonth.setHorizontalAlignment(SwingConstants.CENTER);
		txtMonth.setBounds(347, 13, 45, 35);
		panelPubDate.add(txtMonth);
		txtMonth.setColumns(10);
			
		txtDay = new JTextField();
		txtDay.setHorizontalAlignment(SwingConstants.CENTER);
		txtDay.setBounds(450, 13, 45, 35);
		panelPubDate.add(txtDay);
		txtDay.setColumns(10);
			
			
		JPanel panelImg = new JPanel();
		panelImg.setBounds(341, 400, 238, 108);
		getContentPane().add(panelImg);
		panelImg.setLayout(null);
			
		JLabel lblImg = new JLabel();
		lblImg.setBounds(12, 10, 95, 95);
		panelImg.add(lblImg);
			
		JButton btnAddImg = new JButton("사진 등록");
		btnAddImg.setBounds(134, 29, 97, 48);
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
		panelImg.add(btnAddImg);
			
		JPanel panelName = new JPanel();
		panelName.setBounds(38, 66, 550, 60);
		getContentPane().add(panelName);
		panelName.setLayout(null);
			
		JLabel lblName = new JLabel("제목");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(20, 10, 65, 40);
		panelName.add(lblName);
			
		txtName = new JTextField();
		txtName.setBounds(120, 10, 400, 40);
		panelName.add(txtName);
		txtName.setColumns(10);
			
		JPanel panelWriter = new JPanel();
		panelWriter.setBounds(38, 130, 556, 66);
		getContentPane().add(panelWriter);
		panelWriter.setLayout(null);
			
		JLabel lblWriter = new JLabel("저자");
		lblWriter.setHorizontalAlignment(SwingConstants.CENTER);
		lblWriter.setBounds(20, 10, 65, 40);
		panelWriter.add(lblWriter);
			
		txtWriter = new JTextField();
		txtWriter.setBounds(120, 10, 400, 40);
		panelWriter.add(txtWriter);
		txtWriter.setColumns(10);
			
		JPanel panelCategory = new JPanel();
		panelCategory.setBounds(38, 195, 556, 62);
		getContentPane().add(panelCategory);
		panelCategory.setLayout(null);
			
		JLabel lblCategory = new JLabel("장르");
		lblCategory.setBounds(20, 10, 65, 40);
		panelCategory.add(lblCategory);
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
			
		txtCategory = new JTextField();
		txtCategory.setBounds(120, 10, 400, 40);
		panelCategory.add(txtCategory);
		txtCategory.setColumns(10);
			
		JPanel panelPublisher = new JPanel();
		panelPublisher.setBounds(38, 256, 550, 66);
		getContentPane().add(panelPublisher);
		panelPublisher.setLayout(null);
			
		JLabel lblPublisher = new JLabel("출판사");
		lblPublisher.setHorizontalAlignment(SwingConstants.CENTER);
		lblPublisher.setBounds(20, 10, 65, 40);
		panelPublisher.add(lblPublisher);
			
		txtPublisher = new JTextField();
		txtPublisher.setBounds(120, 10, 400, 40);
		panelPublisher.add(txtPublisher);
		txtPublisher.setColumns(10);
			
	} // end init
	
	private BookVO validCheck() {
		String name = txtName.getText();
		String writer = txtWriter.getText();
		String category = txtCategory.getText();
		String publisher = txtPublisher.getText();
		String state = cbxState.getSelectedItem().toString();
		String img = null;
		
		if(name.length() * writer.length() * category.length() * publisher.length() * txtYear.getText().length() 
				* txtMonth.getText().length() * txtDay.getText().length() * state.length() == 0) {
			AlertDialog.printMsg("모든 필드 입력 필요");
			return null;
		}
		if(selectedFile != null) {
			img = selectedFile.getName();
		}
		int year = 0, month = 0, day = 0;
		
		try {
			year = Integer.parseInt(txtYear.getText());
			month = Integer.parseInt(txtMonth.getText());
			day = Integer.parseInt(txtDay.getText());
		}catch(Exception e) {
			AlertDialog.printMsg("잘못된 입력");
			return null;
		}
		if(year < 0 || year > LocalDateTime.now().getYear() || month < 1 || month > 12 || day < 1 || day > 31) {
			AlertDialog.printMsg("잘못된 날짜 형식");
			return null;
		}
		
		LocalDateTime pubDate = LocalDateTime.of(year, month, day, 0, 0, 0);
		
		return new BookVO(0, name, writer, category, publisher, pubDate, state, img);
	} // end validCheck
}
