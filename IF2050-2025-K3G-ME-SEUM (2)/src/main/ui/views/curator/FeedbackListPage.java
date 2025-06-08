package main.ui.views.curator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Feedback List Page for curator - matches the UI design from the image
 */
public class FeedbackListPage extends BorderPane {
    
    // UI Components
    private TableView<FeedbackData> feedbackTable;
    private TextField searchField;
    private Label titleLabel;
    private Label subtitleLabel;
    private Pagination pagination;
    
    // Pagination variables
    private static final int ITEMS_PER_PAGE = 10;
    private ObservableList<FeedbackData> allFeedback;
    private int currentPageIndex = 0;
    
    /**
     * Constructor for FeedbackListPage
     */
    public FeedbackListPage() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        loadFeedbackData();
    }
    
    /**
     * Initialize components
     */
    private void initComponents() {
        // Title and subtitle
        titleLabel = new Label("Feedback List");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        subtitleLabel = new Label("Feedback");
        subtitleLabel.setFont(Font.font("System", 14));
        subtitleLabel.setTextFill(Color.GRAY);
        
        // Search field with rounded corner style
        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setPrefWidth(250);
        searchField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 12px;" +
            "-fx-font-size: 14px;"
        );
        
        // Create table with columns matching the image
        feedbackTable = new TableView<>();
        feedbackTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        feedbackTable.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        feedbackTable.setPrefHeight(500);
        
        // ID Column
        TableColumn<FeedbackData, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(60);
        idColumn.setStyle("-fx-alignment: CENTER;");
        
        // Name Customer Column
        TableColumn<FeedbackData, String> nameColumn = new TableColumn<>("Name Customer");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameCustomer"));
        nameColumn.setPrefWidth(200);
        
        // Event Column
        TableColumn<FeedbackData, String> eventColumn = new TableColumn<>("Event");
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        eventColumn.setPrefWidth(250);
        
        // Feedback Column
        TableColumn<FeedbackData, String> feedbackColumn = new TableColumn<>("Feedback");
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        feedbackColumn.setPrefWidth(400);
        
        // Style header
        feedbackTable.getStylesheets().add("data:text/css," +
            ".table-view .column-header-background { " +
            "    -fx-background-color: #f8f9fa; " +
            "} " +
            ".table-view .column-header { " +
            "    -fx-background-color: transparent; " +
            "    -fx-border-color: #dee2e6; " +
            "    -fx-border-width: 0 1 1 0; " +
            "    -fx-font-weight: bold; " +
            "    -fx-text-fill: #495057; " +
            "} " +
            ".table-view .table-cell { " +
            "    -fx-border-color: #dee2e6; " +
            "    -fx-border-width: 0 1 1 0; " +
            "    -fx-padding: 12px 8px; " +
            "}"
        );
        
        // Add columns to table
        feedbackTable.getColumns().addAll(idColumn, nameColumn, eventColumn, feedbackColumn);
        
        // Pagination
        pagination = new Pagination();
        pagination.setPageCount(1);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setStyle("-fx-border-color: transparent;");
        
        // Set page factory for pagination
        pagination.setPageFactory(this::createPage);
    }
    
    /**
     * Create page content for pagination
     */
    private TableView<FeedbackData> createPage(int pageIndex) {
        currentPageIndex = pageIndex;
        loadFeedbackByPage(pageIndex);
        return feedbackTable;
    }
    
    /**
     * Setup layout
     */
    private void setupLayout() {
        // Set white background for the entire page
        setStyle("-fx-background-color: white;");
        setPadding(new Insets(30));
        
        // Top section with title and subtitle
        VBox headerBox = new VBox(5);
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Search section
        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(20, 0, 20, 0));
        searchBox.getChildren().add(searchField);
        
        // Container for header and search
        VBox topContainer = new VBox(10);
        topContainer.getChildren().addAll(headerBox, searchBox);
        
        // Table container with padding
        VBox tableContainer = new VBox(10);
        tableContainer.setPadding(new Insets(0, 0, 0, 0));
        
        // Add table to container
        tableContainer.getChildren().add(feedbackTable);
        
        // Create custom pagination controls
        createCustomPaginationControls(tableContainer);
        
        // Add all components to the main layout
        setTop(topContainer);
        setCenter(tableContainer);
    }
    
    /**
     * Create custom pagination controls that match the design
     */
    private void createCustomPaginationControls(VBox container) {
        // Custom pagination controls
        HBox paginationBox = new HBox(5);
        paginationBox.setAlignment(Pos.CENTER_LEFT);
        paginationBox.setPadding(new Insets(20, 0, 0, 0));
        
        // Previous page button
        Button prevButton = new Button("‹");
        prevButton.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-padding: 8 12;");
        prevButton.setOnAction(e -> {
            if (currentPageIndex > 0) {
                pagination.setCurrentPageIndex(currentPageIndex - 1);
            }
        });
        
        // Page number buttons
        HBox pageButtons = new HBox(2);
        
        // Listen to pagination changes
        pagination.currentPageIndexProperty().addListener((obs, oldVal, newVal) -> {
            updatePageButtons(pageButtons, newVal.intValue());
        });
        
        // Next page button
        Button nextButton = new Button("›");
        nextButton.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-padding: 8 12;");
        nextButton.setOnAction(e -> {
            if (currentPageIndex < pagination.getPageCount() - 1) {
                pagination.setCurrentPageIndex(currentPageIndex + 1);
            }
        });
        
        // Page size selector
        ComboBox<Integer> pageSizeSelector = new ComboBox<>();
        pageSizeSelector.getItems().addAll(10, 25, 50, 100);
        pageSizeSelector.setValue(ITEMS_PER_PAGE);
        pageSizeSelector.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6;");
        
        // Page label
        Label pageLabel = new Label("/Page");
        pageLabel.setFont(Font.font("System", 12));
        pageLabel.setTextFill(Color.GRAY);
        
        // Add components to pagination box
        paginationBox.getChildren().addAll(prevButton, pageButtons, nextButton, pageSizeSelector, pageLabel);
        
        // Initialize page buttons
        updatePageButtons(pageButtons, 0);
        
        // Add pagination to container
        container.getChildren().add(paginationBox);
    }
    
    /**
     * Update page buttons based on current page
     */
    private void updatePageButtons(HBox pageButtons, int currentPage) {
        pageButtons.getChildren().clear();
        
        int totalPages = pagination.getPageCount();
        int start = Math.max(0, currentPage - 2);
        int end = Math.min(totalPages, start + 5);
        
        for (int i = start; i < end; i++) {
            Button pageButton = new Button(String.valueOf(i + 1));
            int pageIndex = i;
            
            if (i == currentPage) {
                pageButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-border-color: #007bff; -fx-padding: 8 12;");
            } else {
                pageButton.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-padding: 8 12;");
            }
            
            pageButton.setOnAction(e -> pagination.setCurrentPageIndex(pageIndex));
            pageButtons.getChildren().add(pageButton);
        }
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Search field
        searchField.setOnAction(e -> performSearch());
        
        // Add text change listener for real-time search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                performSearch();
            }
        });
    }
    
    /**
     * Load feedback by page
     */
    private void loadFeedbackByPage(int page) {
        try {
            if (allFeedback == null) {
                return;
            }
            
            // Calculate pagination
            int fromIndex = page * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allFeedback.size());
            
            // Clear table first
            feedbackTable.getItems().clear();
            
            // Add items for this page if there are items
            if (fromIndex < allFeedback.size()) {
                ObservableList<FeedbackData> pageItems = FXCollections.observableArrayList(
                    allFeedback.subList(fromIndex, toIndex)
                );
                feedbackTable.getItems().addAll(pageItems);
            }
            
            // Force table refresh
            feedbackTable.refresh();
            
        } catch (Exception e) {
            System.err.println("Failed to load page: " + e.getMessage());
        }
    }
    
    /**
     * Load all feedback data
     */
    private void loadFeedbackData() {
        // Sample data matching the image
        allFeedback = FXCollections.observableArrayList(
            new FeedbackData(1, "Ali Budi", "Matahari Terbit dari Barat", "Artefak 1 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(2, "Budi Asep", "Matahari Terbit dari Barat", "Artefak 2 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(3, "Asep Raharjo", "Matahari Terbit dari Barat", "Artefak 3 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(4, "Gunawan Silalahi", "Matahari Terbit dari Barat", "Artefak 4 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(5, "Kevin Handoyo", "Matahari Terbit dari Barat", "Artefak 5 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(6, "Handoko Sudarajat", "Matahari Terbit dari Barat", "Artefak 6 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(7, "Surya Aryo", "Matahari Terbit dari Barat", "Artefak 7 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(8, "Iwan Iwin", "Matahari Terbit dari Barat", "Artefak 8 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(9, "Lionel Messi", "Matahari Terbit dari Barat", "Artefak 9 ditemukan pada tahun 2010 dengan..."),
            new FeedbackData(10, "Ronaldo.wati", "Matahari Terbit dari Barat", "Artefak 10 ditemukan pada tahun 2010 dengan...")
        );
        
        // Update pagination
        int totalPages = (int) Math.ceil((double) allFeedback.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(Math.max(1, totalPages));
        
        // Load first page
        pagination.setCurrentPageIndex(0);
        currentPageIndex = 0;
        
        // Force reload the page content
        loadFeedbackByPage(0);
    }
    
    /**
     * Perform search operation
     */
    private void performSearch() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            // Show all feedback
            loadFeedbackData();
        } else {
            // Filter feedback based on search term
            ObservableList<FeedbackData> filteredFeedback = FXCollections.observableArrayList();
            
            for (FeedbackData feedback : allFeedback) {
                if (feedback.getNameCustomer().toLowerCase().contains(searchTerm) ||
                    feedback.getEvent().toLowerCase().contains(searchTerm) ||
                    feedback.getFeedback().toLowerCase().contains(searchTerm)) {
                    filteredFeedback.add(feedback);
                }
            }
            
            // Update pagination with filtered results
            allFeedback = filteredFeedback;
            int totalPages = (int) Math.ceil((double) allFeedback.size() / ITEMS_PER_PAGE);
            pagination.setPageCount(Math.max(1, totalPages));
            
            // Reset to first page
            pagination.setCurrentPageIndex(0);
            currentPageIndex = 0;
            
            // Force reload the first page
            loadFeedbackByPage(0);
        }
    }
    
    /**
     * Inner class representing feedback data
     */
    public static class FeedbackData {
        private final int id;
        private final String nameCustomer;
        private final String event;
        private final String feedback;
        
        public FeedbackData(int id, String nameCustomer, String event, String feedback) {
            this.id = id;
            this.nameCustomer = nameCustomer;
            this.event = event;
            this.feedback = feedback;
        }
        
        public int getId() { return id; }
        public String getNameCustomer() { return nameCustomer; }
        public String getEvent() { return event; }
        public String getFeedback() { return feedback; }
    }
}