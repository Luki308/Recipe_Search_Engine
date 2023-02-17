package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.APIDataRetriever;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.Fridge;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.ShoppingList;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.Recipe;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.Result;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.ResultByIngredients;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Nutrient;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction.Instructions;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction.Step;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchPanel extends JPanel {
    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;
    // Parameters from searching
    private int recipesNumber;
    private Recipe[] recipesFound;
    private ResultByIngredients[] idsFound;
    //Components
    private JTextArea recipeText = new JTextArea("Here will be recipe");
    private JRadioButton r1=new JRadioButton("Text");
    private JRadioButton r2=new JRadioButton("Ingredients");
    private JCheckBox nutrition = new JCheckBox("Show nutrition?");
    private JButton ingredientsInFridge = new JButton("Ingredients from fridge?");
    //Fridge
    private Fridge fridge;
    private ShoppingList list;
    private ResultByIngredients currentResultByIngredient;

    public SearchPanel(ShoppingList list, Fridge fridge) {
    	this.list = list;
    	this.fridge = fridge;

        JPanel result = new JPanel(new GridLayout(1,2));

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // recipe text
        recipeText.setLineWrap(true);
        recipeText.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(recipeText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
        scroll.setPreferredSize(new Dimension(550,600));
        rightPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel leftUpPanel = new JPanel(new GridLayout(2,1));
        JPanel leftUpUpperPanel = new JPanel(new FlowLayout());
        JPanel leftUpUpperMiddlePanel = new JPanel(new GridLayout(2,1));
        JPanel leftUpDownPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new FlowLayout());

        // Text to search
        JTextArea textToSearch = new JTextArea("What do you want to eat? :)");
        textToSearch.setPreferredSize(new Dimension(400,100));
        textToSearch.setEditable(true);
        textToSearch.setLineWrap(true);

        // Ingredients or Text
        r1.setBounds(75,50,100,30);
        r2.setBounds(75,100,100,30);
        ButtonGroup bg=new ButtonGroup();
        bg.add(r1);bg.add(r2);
        bg.setSelected(r1.getModel(),true);

        // missed ingredients button
        JButton addMissedButton = new JButton();
        addMissedButton.setText("Add missed ingredients to shopping list");
        addMissedButton.setEnabled(false);

        r1.addActionListener(e -> {
            textToSearch.setText("What do you want to eat? :)");
            addMissedButton.setEnabled(false);
            textToSearch.setEditable(true);
        });

        r2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textToSearch.setText("Write ingredients separated with semicolons. For example:\ntomato;pasta");
                addMissedButton.setEnabled(true);
                textToSearch.setEditable(true);
            }
        });
        // Ingredients in Fridge
        ingredientsInFridge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(r2.isSelected()){
                    List<String> ingredientsFridge = fridge.getIngredients();
                    String ingredientsToSearch = "";
                    for(String ingredient:ingredientsFridge){
                        ingredientsToSearch += ingredient+";";
                    }
                    textToSearch.setText(ingredientsToSearch);
                    textToSearch.setEditable(false);
                }
            }
        });

        // Number of recipes
        JLabel numberLabel = new JLabel("Number of recipes:");
        SpinnerModel model = new SpinnerNumberModel(1, 1, 20, 1);
        JSpinner numberSpinner = new JSpinner(model);

        // Images panel
        JPanel imagesPanel = new JPanel(new GridLayout(recipesNumber,2));
        imagesPanel.setPreferredSize(new Dimension(550,400));
        JScrollPane scrollImages = new JScrollPane(imagesPanel);
        scrollImages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollImages.setPreferredSize(new Dimension(550,400));

        //Button to find recipe
        JButton findButton = new JButton("Find it!");
        findButton.setPreferredSize(new Dimension(70, 20));
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Pattern p = Pattern.compile("[^a-z0-9; ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(textToSearch.getText());
                boolean b = m.find();

                if(b){
                    recipeText.setText("There is a special character in query. You should only write letter, spaces and \";\"");
                    return;
                }
                if(textToSearch.getText().isEmpty()){
                    recipeText.setText("Query shouldn't be empty.");
                    return;
                }
                //Reading from API
                recipesNumber = (int) numberSpinner.getValue();
                boolean withNutrition = nutrition.isSelected();
                String json = null;

                //If searching by text
                if (r1.isSelected()) {

                    json = APIDataRetriever.searchByKeyWord(textToSearch.getText(), recipesNumber, withNutrition);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Result result = gson.fromJson(json, Result.class);
                    recipesFound = result.getRecipe();
                    Recipe recipe = result.getRecipe().length > 0 ? result.getRecipe()[0] : null;

                    if(recipe != null) {
                        printRecipe(recipe, recipeText, nutrition);

                        imagesPanel.removeAll();
                        int i = 0;
                        for (Recipe recipeImage : recipesFound) {
                            JLabel imageLabel = null;
                            if(recipeImage.getImage()!=null){
                                URL imageURL = null;
                                try {
                                    imageURL = new URL(recipeImage.getImage());
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                                BufferedImage image = null;
                                try {
                                    image = ImageIO.read(imageURL);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                if (image != null) {
                                    imageLabel = new JLabel(new ImageIcon(imageURL));
                                }
                                if (imageLabel == null) {
                                    imageLabel = new JLabel(new ImageIcon("icons" + File.separator + "search.png"));
                                }
                                imageLabel.setPreferredSize(new Dimension(250, 200));
                                imagesPanel.add(imageLabel);
                            }else{
                                imageLabel = new JLabel(new ImageIcon("icons" + File.separator + "search.png"));
                                imageLabel.setPreferredSize(new Dimension(250, 200));
                                imagesPanel.add(imageLabel);
                            }
                            imageLabel.addMouseListener(new ImageListener(i));
                            i++;
                        }

                    }
                    else{
                        recipeText.setText("No recipes found :(");
                    }
                }
                //If searching by ingredients
                else if (r2.isSelected()) {
                	addMissedButton.setEnabled(true);

                    String text = textToSearch.getText();
                    String[] array = text.split(";");
                    idsFound = APIDataRetriever.searchByIngredients(Arrays.stream(array).toList(), recipesNumber, withNutrition);
                    int resultId = idsFound.length > 0 ? idsFound[0].getId() : -1;
                    if (resultId != -1) {
                        json = APIDataRetriever.getInformation(resultId, withNutrition);
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        Recipe recipe = gson.fromJson(json, Recipe.class);

                        printRecipe(recipe,recipeText,nutrition);

                        currentResultByIngredient = idsFound[0];

                        // Show images
                        imagesPanel.removeAll();
                        int i = 0;
                        for (ResultByIngredients recipeImage : idsFound) {
                            JLabel imageLabel = null;
                            if(recipeImage.getImage()!= null) {
                                URL imageURL = null;
                                try {
                                    imageURL = new URL(recipeImage.getImage());
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                                BufferedImage image = null;
                                try {
                                    image = ImageIO.read(imageURL);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                if (image != null) {
                                    imageLabel = new JLabel(new ImageIcon(imageURL));
                                }
                                else if (imageLabel == null) {
                                    imageLabel = new JLabel(new ImageIcon("icons" + File.separator + "search.png"));
                                }
                                imageLabel.setPreferredSize(new Dimension(250, 200));
                                imagesPanel.add(imageLabel);
                            }
                            else{
                            imageLabel = new JLabel(new ImageIcon("icons" + File.separator + "search.png"));
                            imageLabel.setPreferredSize(new Dimension(250, 200));
                            imagesPanel.add(imageLabel);
                            }
                            imageLabel.addMouseListener(new ImageListener(i));
                            i++;
                        }
                    } else {
                        recipeText.setText("No recipes found :(");
                    }
                    
                }
            }
        });
        
        addMissedButton.addActionListener(t -> {
        	
        	List<Ingredient> missed = currentResultByIngredient.getIngredients();
            list.addMissedIngredients(missed);

        });

        // Prepare components for left panel
        leftUpUpperMiddlePanel.add(r1);
        leftUpUpperMiddlePanel.add(r2);
        leftUpUpperPanel.add(textToSearch);
        leftUpUpperPanel.add(leftUpUpperMiddlePanel);
        leftUpUpperPanel.add(findButton);
        leftUpDownPanel.add(nutrition);
        leftUpDownPanel.add(ingredientsInFridge);
        leftUpDownPanel.add(numberLabel);
        leftUpDownPanel.add(numberSpinner);
        leftUpPanel.add(leftUpUpperPanel);
        leftUpPanel.add(leftUpDownPanel);
        centerPanel.add(imagesPanel);

        // Add to left and right panel
        leftPanel.add(leftUpPanel, BorderLayout.NORTH);
        leftPanel.add(centerPanel, BorderLayout.CENTER);
        rightPanel.add(scroll);
        rightPanel.add(addMissedButton);

        //  Merge left and right
        result.add(leftPanel);
        result.add(rightPanel);
        add(result);
    }
    private void printRecipe(Recipe recipe, JTextArea recipeText, JCheckBox nutrition){
        recipeText.setText("Here is your recipe:\n");

        String recipeSteps = "How to make:\n";
        String ingredients = "Ingredients:\n";
        for (Instructions analyzedInstruction : recipe.getAnalyzedInstructions()) {
            for (Step step : analyzedInstruction.getSteps()) {
                recipeSteps += "\n" + step.toString();
                for (Ingredient i : step.getIngredients()) {
                    ingredients += i.getName() + ", ";
                    if (i.getAmount() != null && i.getUnit() != null) {
                        ingredients += ' ' + i.getAmount();
                        ingredients += ' ' + i.getUnit();
                    }
                    ingredients += '\n';
                }
            }
        }
        recipeText.append(recipe.getTitle() + '\n' + '\n');
        recipeText.append(ingredients + '\n');
        recipeText.append(recipeSteps + "\n\n");
        if (nutrition.isSelected()) {
            recipeText.append("Nutrition breakdown:\n" + recipe.getNutrition().getBreakdown().toString() + '\n');
            for (Nutrient n : recipe.getNutrition().getNutrients()) {
                recipeText.append(n.toString() + '\n');
            }
        }
    }

    private class ImageListener implements MouseListener {
        private final int recipeNumber;
        public ImageListener(int recipeNumber) {
            this.recipeNumber = recipeNumber;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Recipe recipe = null;
            if(r1.isSelected()){
                recipe = recipesFound[recipeNumber];
                printRecipe(recipe, recipeText, nutrition);
            }
            else if(r2.isSelected()){
                boolean withNutrition = nutrition.isSelected();
                ResultByIngredients resultId = idsFound[recipeNumber];
                currentResultByIngredient = resultId;
                
                String json = APIDataRetriever.getInformation(resultId.getId(), withNutrition);
                Gson gson = new GsonBuilder().serializeNulls().create();
                recipe = gson.fromJson(json, Recipe.class);
                printRecipe(recipe, recipeText, nutrition);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
