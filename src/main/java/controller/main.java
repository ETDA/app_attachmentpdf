package controller;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import pdfa3.EmbedType;
import pdfa3.FileAttachmentController;
import pdfa3.PDFA3Converter;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.Color;
public class main extends JFrame{
	
	private FileNameExtensionFilter f_pdf = new FileNameExtensionFilter("PDF File", "pdf");
	private FileNameExtensionFilter f_all = new FileNameExtensionFilter("All files.","*");
	private ArrayList<String> listFile;
	private ArrayList<String> susFile=null;
	public static void main(String[] args) {
		System.setProperty("user.language", "th");
		System.setProperty("user.country", "en");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("********Start App**********");
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JPanel panel = new JPanel();
	private JTextField txt_pdf;

	main() {
		super("Attachment PDF File Java");
		setTitle("Attachment PDF File");
		setSize(717, 228);
		setLocation(500, 280);
		panel.setLayout(null);

		getContentPane().add(panel);
		
		JLabel lblPdfFile = new JLabel("PDF File");
		lblPdfFile.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblPdfFile.setBounds(10, 17, 130, 16);
		panel.add(lblPdfFile);
		
		txt_pdf = new JTextField();
		txt_pdf.setText("Choose PDF File");
		txt_pdf.setForeground(Color.BLACK);
		txt_pdf.setFont(new Font("Kanit", Font.PLAIN, 13));
		txt_pdf.setEditable(false);
		txt_pdf.setColumns(10);
		txt_pdf.setBounds(147, 12, 460, 22);
		panel.add(txt_pdf);
		
		final JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
		final JButton btn_pdf = new JButton("Browse");
		btn_pdf.setFont(new Font("Kanit", Font.PLAIN, 13));
		btn_pdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jfc.setDialogTitle("Select file pdf");
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setMultiSelectionEnabled(true);
//				jfc.setFileSelectionMode(jfc.FILES_AND_DIRECTORIES);
				jfc.setFileSelectionMode(jfc.FILES_ONLY);

				jfc.addChoosableFileFilter(f_pdf);
				jfc.getSelectedFiles();
				System.out.println("**File PDF Select**");
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = jfc.getSelectedFile().getAbsolutePath() + "";
					String word = path;
					System.out.println("PDF file : "+word);
					txt_pdf.setText(word);
				}
			}
		});
		btn_pdf.setBounds(610, 11, 81, 24);
		panel.add(btn_pdf);
		
		JLabel lblFile = new JLabel("File Attachment");
		lblFile.setFont(new Font("Kanit", Font.PLAIN, 13));
		lblFile.setBounds(10, 44, 130, 16);
		panel.add(lblFile);
		
		final JScrollPane scrollPane_fileAttache = new JScrollPane();
		scrollPane_fileAttache.setBounds(147, 45, 460, 101);
		panel.add(scrollPane_fileAttache);
		
		final JFileChooser jfc2 = new JFileChooser(FileSystemView.getFileSystemView());
		final JButton btn_attach = new JButton("Browse");
		btn_attach.setFont(new Font("Kanit", Font.PLAIN, 13));
		btn_attach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jfc2.setDialogTitle("Select file attachement");
				jfc2.setAcceptAllFileFilterUsed(false);
				jfc2.setMultiSelectionEnabled(true);
//				jfc2.setFileSelectionMode(jfc2.FILES_AND_DIRECTORIES);
				jfc2.setFileSelectionMode(jfc2.FILES_ONLY);
				jfc2.getSelectedFiles();

				int returnValue = jfc2.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					listFile = new ArrayList<String>();
					susFile = new ArrayList<String>();
					File[] namefile = jfc2.getSelectedFiles();
					System.out.println("**Attach File Select**");
					for (int i = 0; i < jfc2.getSelectedFiles().length; i++) {
						listFile.add(namefile[i].getName());
						System.out.println("Attach file ["+i+"]: "+namefile[i].getAbsoluteFile().toString());
						susFile.add(namefile[i].getAbsoluteFile().toString());
					}
					String[] stringArray = listFile.toArray(new String[0]);
					JList listToDo = new JList(stringArray);
					scrollPane_fileAttache.setViewportView(listToDo);
				}
			}
		});
		btn_attach.setBounds(610, 45, 81, 24);
		panel.add(btn_attach);
		
		final JButton btn_ok = new JButton("Start Attachment PDF");
		btn_ok.setFont(new Font("Kanit", Font.PLAIN, 13));
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btn_attach.setEnabled(false);
					btn_pdf.setEnabled(false);
					btn_ok.setEnabled(false);
					if(susFile!=null&&(jfc.getSelectedFile()!=null)) {
						PDFA3Converter pdfConverter = new PDFA3Converter();
						String out_ = jfc.getSelectedFile().getParent() + "\\Attachment_"
								+ jfc.getSelectedFile().getName();
						pdfConverter.setInputPDFFile(jfc.getSelectedFile() + "");
						pdfConverter.setOutputPDFFile(out_);
						String[] embedfile = susFile.toArray(new String[0]);
						pdfConverter.setEmbedFile(embedfile, EmbedType.ADD);
						System.out.println("**Start Covert**");
						pdfConverter.convert();
						System.out.println("File output: " + out_);
						System.out.println("********Attachment Done**********");
						JOptionPane.showMessageDialog(null, "Attachment Done \n"+out_, "Success", 1);
					
					}else {
						JOptionPane.showMessageDialog(null, "Please Choose And Attach A File!!! ", "Warning ", 2);
						System.out.println("NO basefile or Attach file");
					}
					btn_attach.setEnabled(true);
					btn_pdf.setEnabled(true);
					btn_ok.setEnabled(true);
					
					
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Error: \n"+e.getLocalizedMessage(), "ERROR ", 0);
					System.out.println("ERROR FileNotFoundException");
					e.printStackTrace();
					btn_attach.setEnabled(true);
					btn_pdf.setEnabled(true);
					btn_ok.setEnabled(true);
				} catch (Exception e) {
					System.out.println("ERROR Exception");
					JOptionPane.showMessageDialog(null, "Error: \n"+e.getLocalizedMessage(), "ERROR ", 0);
					e.printStackTrace();
					btn_attach.setEnabled(true);
					btn_pdf.setEnabled(true);
					btn_ok.setEnabled(true);
				}
			}
		});
		btn_ok.setBounds(516, 154, 175, 24);
		panel.add(btn_ok);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
