package main.ui.views.customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.ui.components.common.NavigationBar;
import main.ui.components.common.Footer;

/**
 * Event page for ME-SEUM application
 * Shows available museum events
 */
public class EventPage extends VBox implements NavigationBar.NavigationListener {
    
    // Navigation listener interface
    public interface EventNavigationListener {
        void onEventDetails(String eventId);
        void onYourOrdersClicked();
    }
    
    private Footer footer;
    private ScrollPane scrollPane;
    private VBox mainContent;
    private VBox eventsContainer;
    private NavigationBar.NavigationListener externalNavigationListener;
    private EventNavigationListener eventNavigationListener;
    
    /**
     * Constructor with navigation listeners
     */
    public EventPage(NavigationBar.NavigationListener externalListener, 
                    EventNavigationListener eventListener) {
        this.externalNavigationListener = externalListener;
        this.eventNavigationListener = eventListener;
        initComponents();
        setupLayout();
        loadSampleEvents();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Create footer
        footer = new Footer();
        
        // Create main content container
        mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: #f5f3e8;");
        
        // Create events container
        eventsContainer = new VBox(15);
        eventsContainer.setPadding(new Insets(20, 50, 20, 50));
        eventsContainer.setAlignment(Pos.CENTER);
        
        // Create scroll pane for main content
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f3e8;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    
    /**
     * Set up layout
     */
    private void setupLayout() {
        // Create page header
        VBox headerSection = createHeaderSection();
        
        // Create tabs section
        HBox tabsSection = createTabsSection();
        
        // Add all sections to main content
        mainContent.getChildren().addAll(
            headerSection,
            tabsSection,
            eventsContainer,
            footer
        );
        
        // Set up scroll pane
        scrollPane.setContent(mainContent);
        getChildren().add(scrollPane);
        setStyle("-fx-background-color: #f5f3e8;");
    }
    
    /**
     * Create header section
     */
    private VBox createHeaderSection() {
        VBox headerSection = new VBox(10);
        headerSection.setPadding(new Insets(40, 50, 20, 50));
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setStyle("-fx-background-color: #f5f3e8;");
        
        // Page title
        Label titleLabel = new Label("Event");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.BLACK);
        
        headerSection.getChildren().add(titleLabel);
        return headerSection;
    }
    
    /**
     * Create tabs section
     */
    private HBox createTabsSection() {
        HBox tabsSection = new HBox(0);
        tabsSection.setPadding(new Insets(0, 50, 20, 50));
        tabsSection.setAlignment(Pos.CENTER);
        
        // List Event tab (active - golden)
        Button listEventTab = createTabButton("List Event", true);
        
        // Your Orders tab (inactive)
        Button yourOrdersTab = createTabButton("Your Orders", false);
        yourOrdersTab.setOnAction(e -> {
            if (eventNavigationListener != null) {
                eventNavigationListener.onYourOrdersClicked();
            }
        });
        
        tabsSection.getChildren().addAll(listEventTab, yourOrdersTab);
        return tabsSection;
    }
    
    /**
     * Create tab button
     */
    private Button createTabButton(String text, boolean isActive) {
        Button tab = new Button(text);
        tab.setPrefHeight(45);
        tab.setPrefWidth(150);
        
        if (isActive) {
            // Active tab style (golden background)
            tab.setStyle(
                "-fx-background-color: #d4a574;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: transparent;" +
                "-fx-background-radius: 8 8 0 0;" +
                "-fx-cursor: hand;"
            );
        } else {
            // Inactive tab style
            tab.setStyle(
                "-fx-background-color: #e8e2d5;" +
                "-fx-text-fill: #666666;" +
                "-fx-font-size: 14px;" +
                "-fx-border-color: transparent;" +
                "-fx-background-radius: 8 8 0 0;" +
                "-fx-cursor: hand;"
            );
            
            tab.setOnMouseEntered(e -> 
                tab.setStyle(
                    "-fx-background-color: #ddd6c9;" +
                    "-fx-text-fill: black;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-radius: 8 8 0 0;" +
                    "-fx-cursor: hand;"
                )
            );
            
            tab.setOnMouseExited(e -> 
                tab.setStyle(
                    "-fx-background-color: #e8e2d5;" +
                    "-fx-text-fill: #666666;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-radius: 8 8 0 0;" +
                    "-fx-cursor: hand;"
                )
            );
        }
        
        return tab;
    }
    
    /**
     * Load sample events (List Event content)
     */
    private void loadSampleEvents() {
        eventsContainer.getChildren().clear();
        
        // Add 6 sample events as shown in your UI
        for (int i = 0; i < 6; i++) {
            createEventCard(
                "Matahari Terbit dari Barat",
                "Apr 21 - 24",
                "9 a.m. - 6 p.m.",
                "Museum Nusantara",
                "EVENT_00" + (i + 1)
            );
        }
    }
    
    /**
     * Create individual event card with "Details" button
     */
    private void createEventCard(String eventName, String dateRange, String timeRange, String location, String eventId) {
        HBox eventCard = new HBox(20);
        eventCard.setAlignment(Pos.CENTER_LEFT);
        eventCard.setPadding(new Insets(20));
        eventCard.setStyle(
            "-fx-background-color: #e8e2d5;" +
            "-fx-border-color: #d4c4a8;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);"
        );
        eventCard.setPrefHeight(120);
        eventCard.setMaxWidth(1000);
        eventCard.setPrefWidth(900);
        
        // Left section - Date and time
        VBox leftSection = new VBox(5);
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.setPrefWidth(120);
        leftSection.setMinWidth(120);
        
        Label dateLabel = new Label(dateRange);
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        dateLabel.setTextFill(Color.BLACK);
        
        Label timeLabel = new Label(timeRange);
        timeLabel.setFont(Font.font("Arial", 12));
        timeLabel.setTextFill(Color.web("#666666"));
        
        leftSection.getChildren().addAll(dateLabel, timeLabel);
        
        // Middle section - Event details
        VBox middleSection = new VBox(8);
        middleSection.setAlignment(Pos.CENTER_LEFT);
        middleSection.setPrefWidth(450);
        middleSection.setMinWidth(400);
        HBox.setHgrow(middleSection, Priority.ALWAYS);
        
        Label eventNameLabel = new Label(eventName);
        eventNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        eventNameLabel.setTextFill(Color.BLACK);
        eventNameLabel.setWrapText(true);
        
        HBox locationBox = new HBox(5);
        locationBox.setAlignment(Pos.CENTER_LEFT);
        
        Label bullet = new Label("•");
        bullet.setFont(Font.font("Arial", 14));
        bullet.setTextFill(Color.web("#666666"));
        
        Label locationLabel = new Label(location);
        locationLabel.setFont(Font.font("Arial", 14));
        locationLabel.setTextFill(Color.web("#666666"));
        
        locationBox.getChildren().addAll(bullet, locationLabel);
        middleSection.getChildren().addAll(eventNameLabel, locationBox);
        
        // Right section - Event image and Details button
        VBox rightSection = new VBox(10);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPrefWidth(150);
        rightSection.setMinWidth(150);
        
        // Event image placeholder
        Region eventImage = new Region();
        eventImage.setPrefWidth(120);
        eventImage.setPrefHeight(70);
        eventImage.setStyle("-fx-background-color: #cccccc; -fx-background-radius: 4;");
        
        // Details button (different from Open Ticket button)
        Button detailsButton = new Button("Details →");
        detailsButton.setPrefWidth(120);
        detailsButton.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #007bff;" +
            "-fx-padding: 8 16;" +
            "-fx-border-color: #007bff;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 4;" +
            "-fx-background-radius: 4;" +
            "-fx-font-size: 12;" +
            "-fx-cursor: hand;"
        );
        
        // Hover effect
        detailsButton.setOnMouseEntered(e -> 
            detailsButton.setStyle(
                "-fx-background-color: #007bff;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 8 16;" +
                "-fx-border-color: #007bff;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-font-size: 12;" +
                "-fx-cursor: hand;"
            )
        );
        
        detailsButton.setOnMouseExited(e -> 
            detailsButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #007bff;" +
                "-fx-padding: 8 16;" +
                "-fx-border-color: #007bff;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-font-size: 12;" +
                "-fx-cursor: hand;"
            )
        );
        
        // Details button action
        detailsButton.setOnAction(e -> {
            if (eventNavigationListener != null) {
                eventNavigationListener.onEventDetails(eventId);
            }
        });
        
        rightSection.getChildren().addAll(eventImage, detailsButton);
        eventCard.getChildren().addAll(leftSection, middleSection, rightSection);
        eventsContainer.getChildren().add(eventCard);
    }
    
    @Override
    public void onNavigate(String destination) {
        if (externalNavigationListener != null) {
            externalNavigationListener.onNavigate(destination);
        }
    }
}