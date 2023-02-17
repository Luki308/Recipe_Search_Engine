package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.GUI;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.Fridge;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.IngredientHolder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;

public class FridgePanel extends JPanel {
	
	private Fridge fridge = new Fridge();

	private JTable foodTable;

	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new TopPanel();
	private JPanel leftPanel = new LeftPanel();
	private JPanel rightPanel = new RightPanel();
	
	public FridgePanel(Fridge fridge) {
		this.fridge = fridge;
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
			titleText.setText("\n Your Fridge");
			titleText.setFont(titleFont);
			titleText.setBackground(getBackground());
			Color titleColor = new Color(255, 85, 85);
			titleText.setForeground(titleColor);
			titleText.setEditable(false);
			add(titleText, BorderLayout.PAGE_START);
			
			// panel description
			Font desFont = new Font("Verdana", Font.PLAIN, 12);
			description.setFont(desFont);
			description.setText("\n Here you can manage food products in your fridge, which includes:\n\n"
					+ "     ➤ Scrolling through your ingredients;\n"
					+ "     ➤ Adding new ones or removing already added ones;\n"
					+ "     ➤ Automatically removing gone off products (only those with added expiration date);\n"
					+ "     ➤ Saving your fridge to XML file;\n"
					+ "     ➤ Loading your fridge from XML file.\n\n"
					+ " Food items in your fridge will taken into account in searching for recipes by products if you tick\n the corresponding check box in search options.\n\n");
			description.setBackground(getBackground());
			description.setEditable(false);
			add(description, BorderLayout.CENTER);
			
			// image of fridge
			ImageIcon fridgeImage = new ImageIcon(new ImageIcon("icons" + File.separator + "fridge_image.png").getImage().getScaledInstance(157, 190 , Image.SCALE_SMOOTH));
			JLabel imgLabel = new JLabel(fridgeImage);
			add(imgLabel, BorderLayout.EAST);
		}
		
	}
	
	public class LeftPanel extends JPanel {
		
		public LeftPanel() {
			setBorder(BorderFactory.createTitledBorder("Products in your fridge"));
			String[] colNames = {"Product name", "Expiration Date"};
			Object[][] data = new Object[fridge.getExpirationDates().size()][2];
			int i = 0;
			
			for(String key : fridge.getExpirationDates().keySet()) {
				data[i][0] = key;
				if(fridge.getExpirationDates().get(key) == null) {
					data[i][1] = "No data";
				} else {
					data[i][1] = fridge.getExpirationDates().get(key);
				}
				i++;
			}
			
			foodTable = new JTable(data, colNames);
			foodTable.setDefaultEditor(Object.class, null);
			
			add(new JScrollPane(foodTable));
		}

		
	}
	
	public class RightPanel extends JPanel {
		
		private JPanel datePanel = new JPanel();
		
		public RightPanel() {
			setBorder(BorderFactory.createTitledBorder("Manage your fridge"));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JTextArea title1 = new JTextArea();
			Font titleFont = new Font("Verdana", Font.BOLD, 12);
			title1.setFont(titleFont);
			Color titleColor = new Color(255, 85, 85);
			title1.setForeground(titleColor);
			title1.setText("Add ingredient to your fridge:");
			title1.setEditable(false);
			title1.setBackground(getBackground());
			add(title1);
			
			JTextArea subtitle1 = new JTextArea();
			subtitle1.setText("Insert ingredient name:");
			subtitle1.setEditable(false);
			subtitle1.setBackground(getBackground());
			add(subtitle1);
			
			
			JTextField ingredientToAdd = new JTextField();
			add(ingredientToAdd);
			
			datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
			
			JCheckBox includeDateCheckBox = new JCheckBox();
			includeDateCheckBox.setText("Would you like to insert expiration date?");
			
			
			SpinnerModel yearModel = new SpinnerNumberModel(Year.now().getValue(), 1900, 2100, 1);
			JSpinner year = new JSpinner(yearModel);
			year.setEnabled(false);
			
			
			SpinnerModel monthModel = new SpinnerNumberModel(LocalDate.now().getMonthValue(), 1, 12, 1);
			JSpinner month = new JSpinner(monthModel);
			month.setEnabled(false);
			
			SpinnerModel dayModel = new SpinnerNumberModel(LocalDate.now().getDayOfMonth(), 1, 31, 1);
			JSpinner day = new JSpinner(dayModel);
			day.setEnabled(false);
			
			includeDateCheckBox.addItemListener(e -> {
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					year.setEnabled(false);
					month.setEnabled(false);
					day.setEnabled(false);
				} else {
					year.setEnabled(true);
					month.setEnabled(true);
					day.setEnabled(true);
				}
			});
			
			datePanel.add(includeDateCheckBox);
			datePanel.add(year);
			datePanel.add(month);
			datePanel.add(day);
			
			add(datePanel);
			
			JButton addIngredientButton = new JButton();
			addIngredientButton.addActionListener(e -> {
				String name = ingredientToAdd.getText();
				name.replace(" ", "+");
				
				boolean isNumericYear = year.getValue().toString().chars().allMatch( Character::isDigit );
				boolean isNumericMonth = month.getValue().toString().chars().allMatch( Character::isDigit );
				boolean isNumericDay = day.getValue().toString().chars().allMatch(Character::isDigit);
				
				if(isNumericYear && isNumericMonth && isNumericDay && includeDateCheckBox.isSelected()) {
					String monthS;
					String dayS;
					if(Integer.parseInt(month.getValue().toString()) < 10) {
						monthS = String.join("", "0", month.getValue().toString());
					} else {
						monthS = month.getValue().toString();
					}
					
					if(Integer.parseInt(day.getValue().toString()) < 10) {
						dayS = String.join("", "0", day.getValue().toString());
					} else {
						dayS = day.getValue().toString();
					}
					
					String date = String.join("-", year.getValue().toString(), monthS, dayS);
					
					if(name != null && !name.equals("") && !name.matches("[ ]{1,}")) {
						fridge.addIngredientWithExpirationDate(name, date);
					}
					
				} else {
					if(name != null && !name.equals("") && !name.matches("[ ]{1,}")) {
						fridge.addIngredient(name);
					}
					
				}
				
				
				ingredientToAdd.setText("");
				mainPanel.removeAll();
				leftPanel = new LeftPanel();
				mainPanel.add(leftPanel);
				mainPanel.add(rightPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
			});
			addIngredientButton.setText("Add ingredient");
			
			add(addIngredientButton);
			add(new JSeparator());
			
			JTextArea title2 = new JTextArea();
			title2.setText("Remove selected ingredients:");
			title2.setFont(titleFont);
			title2.setEditable(false);
			title2.setForeground(titleColor);
			title2.setBackground(getBackground());
			add(title2);
			
			JTextArea subtitle2 = new JTextArea();
			subtitle2.setText("Select ingredients you want to remove in the table on the left.");
			subtitle2.setEditable(false);
			subtitle2.setBackground(getBackground());
			add(subtitle2);
			
			JButton removeButton = new JButton();
			removeButton.setText("Remove ingredients");
			removeButton.addActionListener(e -> {
				int[] selectedRows = foodTable.getSelectedRows();
				for(int i : selectedRows) {
					String name = foodTable.getModel().getValueAt(i, 0).toString();
					fridge.removeIngredient(name);
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
			
			JTextArea title3 = new JTextArea();
			title3.setText("Remove expired ingredients:");
			title3.setEditable(false);
			title3.setFont(titleFont);
			title3.setBackground(getBackground());
			title3.setForeground(titleColor);
			add(title3);
			
			JTextArea subtitle3 = new JTextArea();
			subtitle3.setText("Just press the button below.");
			subtitle3.setEditable(false);
			subtitle3.setBackground(getBackground());
			add(subtitle3);
			
			JButton removeExpiredButton = new JButton();
			removeExpiredButton.setText("Remove expired ingredients");
			removeExpiredButton.addActionListener(e -> {
				fridge.removeExpiredItems();
				mainPanel.removeAll();
				leftPanel = new LeftPanel();
				mainPanel.add(leftPanel);
				mainPanel.add(rightPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
			});
			
			add(removeExpiredButton);
			
			add(new JSeparator());
			
			JPanel savingAndLoadingPanel = new JPanel();
			savingAndLoadingPanel.setLayout(new BoxLayout(savingAndLoadingPanel, BoxLayout.X_AXIS));
			
			JPanel savingPanel = new JPanel();
			savingPanel.setLayout(new BoxLayout(savingPanel, BoxLayout.Y_AXIS));
			
			JTextArea title4 = new JTextArea();
			title4.setText("Save your fridge:");
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
			title5.setText("Load your fridge from a file:");
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
			    	if(holder.getClass().equals(Fridge.class)) {
				    	fridge.setIngredients(holder.getIngredients());
				    	fridge.setExpirationDates(((Fridge) holder).getExpirationDates());
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
			    	IngredientHolder.XMLSaver.saveToFile(fridge, filePath);
					dispose();
				} catch (IOException e) {
				}
			    }
	        
	        add(fileChooser);
		}
		
	}

}
