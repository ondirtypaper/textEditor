package textEditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

public final class TextEditor extends JFrame implements ActionListener{
	// this code base on article 
	// https://opensource.com/article/20/12/write-your-own-text-editor
	
	private static JTextArea area;
	private static JFrame frame;
	private static int returnValue = 0;
	
	public TextEditor() { run(); }
	
	public void run() {
		frame = new JFrame("Text Editor");
		
		//Set the look-and-feel (LNF) of the application
		//Try to default to whatever the host system prefers
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | 
				IllegalAccessException | UnsupportedLookAndFeelException ex){
			Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//Set attributes of the app window
		area = new JTextArea();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(area);
		frame.setSize(640, 480);
		frame.setVisible(true);
		
		//Build the menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		
		JMenuItem menuItemNew  = new JMenuItem("New");
		JMenuItem menuItemOpen  = new JMenuItem("Open");
		JMenuItem menuItemSave  = new JMenuItem("Save");
		JMenuItem menuItemQuit  = new JMenuItem("Quit");
		
		menuItemNew.addActionListener(this);
		menuItemOpen.addActionListener(this);
		menuItemSave.addActionListener(this);
		menuItemQuit.addActionListener(this);
		
		menuBar.add(menuFile);
		
		menuFile.add(menuItemNew);
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemQuit);
		
		frame.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ingest = null;
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose destination");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		String ae = e.getActionCommand();
		if(ae.equals("Open")) {
			returnValue = jfc.showOpenDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File f = new File(jfc.getSelectedFile().getAbsolutePath());
				try {
					FileReader read = new FileReader(f);
					Scanner scan = new Scanner(read);
					while(scan.hasNextLine()) {
						String line = scan.nextLine() + "\n";
						ingest = ingest + line;
					}
					area.setText(ingest);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		} else if (ae.equals("Save")) {
			returnValue = jfc.showOpenDialog(null);
			try {
				File f = new File(jfc.getSelectedFile().getAbsolutePath());
				FileWriter out = new FileWriter(f);
				out.write(area.getText());
				out.close();
			} catch (FileNotFoundException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "File not found");
			} catch (IOException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "Error");
			}
		} else if (ae.equals("New")) {
			area.setText("");
		} else if (ae.equals("Quit")) {
			System.exit(0);
		}
	}

}
