package main.ui.views.customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.ui.components.common.NavigationBar;
import main.ui.components.common.Footer;

public class AboutUsPage extends BorderPane implements NavigationBar.NavigationListener {
    
    private NavigationBar navigationBar;
    private Footer footer;
    private ScrollPane scrollPane;
    private VBox mainContent;
    
    // Add external navigation listener
    private NavigationBar.NavigationListener externalNavigationListener;
    
    /**
     * Constructor for AboutUsPage
     */
    public AboutUsPage() {
        initComponents();
        setupLayout();
    }
    
    /**
     * Constructor with navigation listener
     * @param listener The navigation listener for handling external navigation
     */
    public AboutUsPage(NavigationBar.NavigationListener listener) {
        this.externalNavigationListener = listener;
        initComponents();
        setupLayout();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Create navigation bar
        navigationBar = new NavigationBar(this);
        navigationBar.setActiveDestination("ABOUT");
        
        // Create footer
        footer = new Footer();
        
        // Create main content container
        mainContent = new VBox();
        mainContent.setSpacing(40);
        mainContent.setStyle("-fx-background-color: #f5f0e6;");
        mainContent.setPadding(new Insets(40, 60, 40, 60));
        
        // Create scroll pane for main content
        scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f0e6;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    
    /**
     * Set up layout
     */
    private void setupLayout() {
        // Add all sections to main content
        mainContent.getChildren().addAll(
            createWhatIsMuseumSection(),
            createOurStorySection(),
            createOurValuesSection(),
            footer
        );
        
        // Set layout
        setCenter(scrollPane);
    }
    
    private HBox createWhatIsMuseumSection() {
        HBox section = new HBox();
        section.setSpacing(100);
        section.setAlignment(Pos.CENTER_LEFT);
        
        // Left side - Title
        VBox leftPanel = new VBox();
        leftPanel.setPrefWidth(400);
        
        Label titleLabel = new Label("What is Museum\nNusantara?");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titleLabel.setTextFill(Color.web("#2c2c2c"));
        
        // Add hover effect to title
        titleLabel.setStyle("-fx-cursor: hand;");
        titleLabel.setOnMouseEntered(e -> 
            titleLabel.setStyle("-fx-cursor: hand; -fx-opacity: 0.8;")
        );
        titleLabel.setOnMouseExited(e -> 
            titleLabel.setStyle("-fx-cursor: hand; -fx-opacity: 1.0;")
        );
        
        leftPanel.getChildren().add(titleLabel);
        
        // Right side - Description
        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(400);
        
        Text descText = new Text("Museum Nusantara adalah salah satu museum terbesar\n" +
            "di Indonesia yang memiliki koleksi artefak bersejarah\n" +
            "yang sangat beragam, mencakup berbagai daerah dan\n" +
            "periode sejarah bangsa.");
        descText.setFont(Font.font("Arial", 16));
        descText.setFill(Color.web("#666666"));
        
        TextFlow textFlow = new TextFlow(descText);
        rightPanel.getChildren().add(textFlow);
        
        section.getChildren().addAll(leftPanel, rightPanel);
        return section;
    }
    
    private StackPane createOurStorySection() {
        StackPane section = new StackPane();
        section.setPrefHeight(400);
        section.setMaxHeight(400);
        
        // Background (replace with your image path)
        Region background = new Region();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #4682b4, #1e3a5f);");
        
        // Content overlay
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);
        content.setPadding(new Insets(50));
        
        Label storyTitle = new Label("Our Story");
        storyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        storyTitle.setTextFill(Color.WHITE);
        
        // Add hover effect to story title
        storyTitle.setStyle("-fx-cursor: hand;");
        storyTitle.setOnMouseEntered(e -> 
            storyTitle.setStyle("-fx-cursor: hand; -fx-opacity: 0.8;")
        );
        storyTitle.setOnMouseExited(e -> 
            storyTitle.setStyle("-fx-cursor: hand; -fx-opacity: 1.0;")
        );
        
        Text storyText = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer commodo odio eu\n" +
            "eleifend congue. Nunc vulputate facilisis urna nec bibendum. In euismod est nec purus\n" +
            "posuere varius. Vestibulum tortor sapien, tincidunt in tempor in, fringilla in ipsum. Integer\n" +
            "dignissim eget sapien in posuere. Nulla facilisi. Vivamus dapibus et nibh accumsan\n" +
            "fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse\n" +
            "rutrum, risus quis.");
        storyText.setFont(Font.font("Arial", 14));
        storyText.setFill(Color.WHITE);
        
        TextFlow storyTextFlow = new TextFlow(storyText);
        storyTextFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        content.getChildren().addAll(storyTitle, storyTextFlow);
        
        section.getChildren().addAll(background, content);
        return section;
    }
    
    private HBox createOurValuesSection() {
        HBox section = new HBox();
        section.setSpacing(100);
        section.setAlignment(Pos.CENTER_LEFT);
        
        // Left side - Title
        VBox leftPanel = new VBox();
        leftPanel.setPrefWidth(400);
        
        Label valuesTitle = new Label("Our Values is\nHuge. Hype. Art Vibe.");
        valuesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        valuesTitle.setTextFill(Color.web("#2c2c2c"));
        
        // Add hover effect to values title
        valuesTitle.setStyle("-fx-cursor: hand;");
        valuesTitle.setOnMouseEntered(e -> 
            valuesTitle.setStyle("-fx-cursor: hand; -fx-opacity: 0.8;")
        );
        valuesTitle.setOnMouseExited(e -> 
            valuesTitle.setStyle("-fx-cursor: hand; -fx-opacity: 1.0;")
        );
        
        leftPanel.getChildren().add(valuesTitle);
        
        // Right side - Values list
        VBox rightPanel = new VBox();
        rightPanel.setSpacing(20);
        rightPanel.setPrefWidth(400);
        
        rightPanel.getChildren().addAll(
            createValueItem("📊", "Huge", "Lorem ipsum dolor sit amet, consectetur elit. Nam faucibus."),
            createValueItem("🌀", "Hype", "Lorem ipsum dolor sit amet, consectetur elit. Nam faucibus."),
            createValueItem("🎨", "Art Vibe", "Lorem ipsum dolor sit amet, consectetur elit. Nam faucibus.")
        );
        
        section.getChildren().addAll(leftPanel, rightPanel);
        return section;
    }
    
    private HBox createValueItem(String icon, String title, String description) {
        HBox item = new HBox();
        item.setSpacing(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10, 0, 10, 0));
        
        // Add hover effect to the entire value item
        item.setStyle(
            "-fx-cursor: hand;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 15px;"
        );
        
        item.setOnMouseEntered(e -> 
            item.setStyle(
                "-fx-cursor: hand;" +
                "-fx-background-color: rgba(255, 255, 255, 0.3);" +
                "-fx-background-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
            )
        );
        
        item.setOnMouseExited(e -> 
            item.setStyle(
                "-fx-cursor: hand;" +
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 5px;" +
                "-fx-padding: 15px;"
            )
        );
        
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(24));
        iconLabel.setPrefWidth(40);
        
        VBox textPanel = new VBox();
        textPanel.setSpacing(5);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#323232"));
        
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 14));
        descLabel.setTextFill(Color.web("#646464"));
        
        textPanel.getChildren().addAll(titleLabel, descLabel);
        item.getChildren().addAll(iconLabel, textPanel);
        
        return item;
    }
    
    /**
     * Implementation of NavigationBar.NavigationListener
     */
    @Override
    public void onNavigate(String destination) {
        // Forward navigation to external listener (Main.java)
        if (externalNavigationListener != null) {
            externalNavigationListener.onNavigate(destination);
        }
    }
}