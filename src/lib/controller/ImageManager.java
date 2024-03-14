package lib.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {
	
	private static ImageManager instance = null;
	private static final String IMAGE_DIR = "imageServer";
	private File imgDir;
	
	private ImageManager() {
		init();
	}
	
	public static ImageManager getInstance() {
		if(instance == null) {
			instance = new ImageManager();
		}
		return instance;
	} // end getInstance
	
	private void init() {
		imgDir = new File(IMAGE_DIR);
		
		if(!imgDir.exists()) {
			try {
				if(imgDir.mkdir()) {
					System.out.println("폴더 생성 성공");
				}else System.out.println("폴더 생성 실패");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}else {
			System.out.println("폴더가 이미 존재합니다.");
		}
	} // end init
	
	public void putImage(File selectedFile) {
		System.out.println("ImageManager : putImage()");
		BufferedImage image = null;
		File outputImage = new File(IMAGE_DIR + File.separator + selectedFile.getName());
		try {
			image = ImageIO.read(selectedFile);
			System.out.println("end ImageIO.read()");
			ImageIO.write(image, "jpg", outputImage);
			System.out.println("이미지 저장 성공");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end putImage
	
	public BufferedImage getImage(String imageName) {
		System.out.println("ImageManager : getImage()");
		File image = new File(IMAGE_DIR + File.separator + imageName);
		try {
			return ImageIO.read(image);
		} catch (IOException e) {
			System.out.println("읽어오기 실패 : " + e.toString());
			return null;
		}
	} // end getImage
	
	public ImageIcon getImageToIcon(String imageName, int width, int height) {
		System.out.println("ImageManager : getImageToIcon()");
		File image = new File(IMAGE_DIR + File.separator + imageName);
		try {
		 	BufferedImage bi = ImageIO.read(image);
		 	return new ImageIcon(bi.getScaledInstance(width, height, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			System.out.println("아이콘 읽어오기 실패 : " + e.toString());
		}
		return null;
	} // end getImageToIcon
	
	public ImageIcon convToIcon(File imageFile) {
		System.out.println("ImageManager : convToIcon()");
		ImageIcon icon = new ImageIcon(imageFile.getPath());
		icon = new ImageIcon(icon.getImage().getScaledInstance(95, 95, Image.SCALE_DEFAULT));
		return icon;
	} // end convToIcon
}
