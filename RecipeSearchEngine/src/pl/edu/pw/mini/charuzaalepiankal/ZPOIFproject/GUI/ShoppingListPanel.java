package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.GUI.FridgePanel.LeftPanel;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.Fridge;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.IngredientHolder;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.ShoppingList;

public class ShoppingListPanel extends JPanel {
	private ShoppingList list = new ShoppingList();

	private JTable costTable;
	
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new TopPanel();
	private JPanel leftPanel = new LeftPanel();
	private JPanel rightPanel = new RightPanel();
	
	public ShoppingListPanel() {
		
		setLayout(new BorderLayout());
		mainPanel.setLayout(new GridLayout(1, 2));
		add(topPanel, BorderLayout.PAGE_START);
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public class TopPanel extends JPanel {
		private JTextArea titleText = new JTextArea();
		private JTextArea description = new JTextArea();
		
		public TopPanel() {
			setLayout(new BorderLayout());
			
			// panel title
			Font titleFont = new Font("Verdana", Font.BOLD, 25);
			titleText.setText("\n Your Shopping List");
			titleText.setFont(titleFont);
			titleText.setBackground(getBackground());
			Color titleColor = new Color(255, 85, 85);
			titleText.setForeground(titleColor);
			titleText.setEditable(false);
			add(titleText, BorderLayout.PAGE_START);
			
			// panel description
			Font desFont = new Font("Verdana", Font.PLAIN, 12);
			description.setFont(desFont);
			description.setText("\n Here you can manage food products in your shopping list, which includes:\n\n"
					+ "     ➤ Scrolling through your products;\n"
					+ "     ➤ Adding new ones or removing already added ones;\n"
					+ "     ➤ Saving your shopping list to XML file;\n"
					+ "     ➤ Loading your shopping list from XML file.\n\n");
			description.setBackground(getBackground());
			description.setEditable(false);
			add(description, BorderLayout.CENTER);
			
			// image of grocieries
			ImageIcon fridgeImage = new ImageIcon(new ImageIcon("icons" + File.separator + "groceries.png").getImage().getScaledInstance(157, 190 , Image.SCALE_SMOOTH));
			JLabel imgLabel = new JLabel(fridgeImage);
			add(imgLabel, BorderLayout.EAST);
		}
	}
	
	public class LeftPanel extends JPanel {
		public LeftPanel() {
			setBorder(BorderFactory.createTitledBorder("Products on your shopping list"));
			String[] colNames = {"Product name", "Cost info"};
			Object[][] data = new Object[list.getCosts().size()][2];
			int i = 0;
			
			for(String key : list.getCosts().keySet()) {
				data[i][0] = key;
				if(list.getCosts().get(key) == null) {
					data[i][1] = "No data";
				} else {
					data[i][1] = list.getCosts().get(key);
				}
				i++;
			}
			
			costTable = new JTable(data, colNames);
			costTable.setDefaultEditor(Object.class, null);
			
			add(new JScrollPane(costTable));
		}
	}
	
	public class RightPanel extends JPanel {
		public RightPanel() {
			setBorder(BorderFactory.createTitledBorder("Manage your shopping list"));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JTextArea title1 = new JTextArea();
			Font titleFont = new Font("Verdana", Font.BOLD, 12);
			title1.setFont(titleFont);
			Color titleColor = new Color(255, 85, 85);
			title1.setForeground(titleColor);
			title1.setText("Add product to your shopping list:");
			title1.setEditable(false);
			title1.setBackground(getBackground());
			add(title1);
			
			JTextArea subtitle1 = new JTextArea();
			subtitle1.setText("Insert product name:");
			subtitle1.setEditable(false);
			subtitle1.setBackground(getBackground());
			add(subtitle1);
			
			
			JTextField productToAdd = new JTextField();
			add(productToAdd);
			
			JButton addProductButton = new JButton();
			addProductButton.setText("Add product");
			addProductButton.addActionListener(e -> {
				String name = productToAdd.getText();
				if(name != null && !name.equals("") && !name.matches("[ ]{1,}")) {
					list.addIngredient(name);
				}
				productToAdd.setText("");
				mainPanel.removeAll();
				leftPanel = new LeftPanel();
				mainPanel.add(leftPanel);
				mainPanel.add(rightPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
			});
			add(addProductButton);
			
			
			add(new JSeparator());
			
			JTextArea title2 = new JTextArea();
			title2.setText("Remove selected products:");
			title2.setFont(titleFont);
			title2.setEditable(false);
			title2.setForeground(titleColor);
			title2.setBackground(getBackground());
			add(title2);
			
			JTextArea subtitle2 = new JTextArea();
			subtitle2.setText("Select products you want to remove in the table on the left.");
			subtitle2.setEditable(false);
			subtitle2.setBackground(getBackground());
			add(subtitle2);
			
			JButton removeButton = new JButton();
			removeButton.setText("Remove products");
			
			removeButton.addActionListener(e -> {
				int[] selectedRows = costTable.getSelectedRows();
				for(int i : selectedRows) {
					String name = costTable.getModel().getValueAt(i, 0).toString();
					list.removeIngredient(name);
				}
				mainPanel.removeAll();
				leftPanel = new LeftPanel();
				mainPanel.add(leftPanel);
				mainPanel.add(rightPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
			});
			
			add(removeButton);
			add(new JSeparator());
			
			JPanel savingAndLoadingPanel = new JPanel();
			savingAndLoadingPanel.setLayout(new BoxLayout(savingAndLoadingPanel, BoxLayout.X_AXIS));
			
			JPanel savingPanel = new JPanel();
			savingPanel.setLayout(new BoxLayout(savingPanel, BoxLayout.Y_AXIS));
			
			JTextArea title4 = new JTextArea();
			title4.setText("Save your shopping list:");
			title4.setEditable(false);
			title4.setFont(titleFont);
			title4.setBackground(getBackground());
			title4.setForeground(titleColor);
			savingPanel.add(title4);
			
			JButton saveButton = new JButton();
			saveButton.setText("Save");
			
			saveButton.addActionListener(e -> {
				new SaveFrame();
			});
			
			savingPanel.add(saveButton);
			
			savingAndLoadingPanel.add(savingPanel);
			
			JPanel loadingPanel = new JPanel();
			loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
			
			JTextArea title5 = new JTextArea();
			title5.setText("Load your shopping list from a file:");
			title5.setEditable(false);
			title5.setFont(titleFont);
			title5.setBackground(getBackground());
			title5.setForeground(titleColor);
			loadingPanel.add(title5);
			
			JButton loadButton = new JButton();
			loadButton.setText("Load");
			
			loadButton.addActionListener(e -> {
				new LoadFrame();
			});
			
			loadingPanel.add(loadButton);
			
			
			savingAndLoadingPanel.add(loadingPanel);
			add(savingAndLoadingPanel);
			
		}
	}
		
	public class LoadFrame extends JFrame {
		
		public LoadFrame() {
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ImageIcon mainIcon = new ImageIcon("icons" + File.separator + "main_icon.png");
	        setIconImage(mainIcon.getImage());
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load");
			FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
				     "xml files (*.xml)", "xml");
			fileChooser.setFileFilter(xmlfilter);

			
			fileChooser.setCurrentDirectory(new File("." + File.separator + "saves"));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    if (selectedFile.getAbsolutePath().endsWith(".xml")) {
			    	IngredientHolder holder = IngredientHolder.XMLSaver.readFromFile(selectedFile.getAbsolutePath());
				    
				    if(holder.getClass().equals(ShoppingList.class)) {
				    	list.setIngredients(holder.getIngredients());
				    	list.setCosts(((ShoppingList) holder).getCosts());
				    	mainPanel.removeAll();
						leftPanel = new LeftPanel();
						mainPanel.add(leftPanel);
						mainPanel.add(rightPanel);
						mainPanel.revalidate();
						mainPanel.repaint();
				    }
			    }
			    dispose();
			    
			}
			
			add(fileChooser);
		}
		
	}
	
	public class SaveFrame extends JFrame {
		
		public SaveFrame() {
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ImageIcon mainIcon = new ImageIcon("icons" + File.separator + "main_icon.png");
	        setIconImage(mainIcon.getImage());
	        
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Save");
	        fileChooser.setCurrentDirectory(new File("." + File.separator + "saves"));
	        
	        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
				     "xml files (*.xml)", "xml");
			fileChooser.setFileFilter(xmlfilter);
	        
	        int result = fileChooser.showSaveDialog(this);
	        if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    try {
			    	String filePath = selectedFile.getAbsolutePath();
			    	if(!filePath.contains(".xml")) {
			    		filePath = String.join(".", filePath, "xml");
			    	}
			    	IngredientHolder.XMLSaver.saveToFile(list, filePath);
					dispose();
				} catch (IOException e) {
				}
			    }
	        
	        add(fileChooser);
		}
		
	}
	
	public void refresh() {
		mainPanel.removeAll();
		leftPanel = new LeftPanel();
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public ShoppingList getList() {
		return list;
	}

	public JTable getCostTable() {
		return costTable;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JPanel getTopPanel() {
		return topPanel;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public JPanel getRightPanel() {
		return rightPanel;
	}
	
	

	

}
