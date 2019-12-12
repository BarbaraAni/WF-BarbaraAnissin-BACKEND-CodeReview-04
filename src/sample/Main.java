package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        InputStream input = this.getClass().getResourceAsStream("/images/price.png" );
        Image image = new Image(input);
        primaryStage.getIcons().add(image);
        // INITIALIZING //
        BorderPane main = new BorderPane();
        primaryStage.setTitle("Set action prices");
        primaryStage.setScene(new Scene(main, 700, 600));
        primaryStage.show();
        // Structure //
        VBox left = new VBox();
        HBox prodName = new HBox();
        Label lblProduct = new Label("Prod. Name");
        TextField txtProduct = new TextField(); //NON EDITABLE
        prodName.getChildren().addAll(lblProduct,txtProduct); ////

        HBox quant = new HBox();
        Label lblQuantity = new Label("Quantity");
        TextField txtQuantity = new TextField(); //NON EDITABLE
        quant.getChildren().addAll(lblQuantity,txtQuantity); ////

        HBox oldPri = new HBox();
        Label oldPrice = new Label("old price");
        TextField txtOldPrice = new TextField();
        Label lblEurOld = new Label("EUR");
        oldPri.getChildren().addAll(oldPrice,txtOldPrice,lblEurOld);

        HBox newPri = new HBox();
        Label newPrice = new Label("new price");
        TextField txtNewPrice = new TextField();
        Label lblEurNew = new Label("EUR");
        newPri.getChildren().addAll(newPrice, txtNewPrice, lblEurNew);

        //IMAGES
        InputStream picCheese = this.getClass().getResourceAsStream("/images/cheese_salakis__600x600.jpg" );
        Image cheese = new Image(picCheese);
        InputStream picPfef = this.getClass().getResourceAsStream("/images/pfeffer__600x600.jpg" );
        Image pfeffer = new Image(picPfef);
        InputStream picVos = this.getClass().getResourceAsStream("/images/voslauer__600x600.jpg" );
        Image voslauer = new Image(picVos);
        InputStream picZuck = this.getClass().getResourceAsStream("/images/zucker__600x600.jpg" );
        Image zucker = new Image(picZuck);

        VBox descr = new VBox();
        Label description = new Label("Description: ");
        Label lblDesc = new Label(""); //NON EDITABLE
        descr.getChildren().addAll(description,lblDesc); //Wrap

        Button update = new Button("Update");

        left.getChildren().addAll(prodName,quant,oldPri,newPri,descr, update);

        ObservableList<Product> items = FXCollections.observableArrayList(
                new Product("Schafmilchkäse","200 Gramm Packung", "Hier gibt es keine Beschreibung",2.59,1.99,cheese),
                new Product("Vöslauer", "1.5 Liter Flasche","Spritziges Vöslauer Mineralwasser",0.75,0.49,voslauer),
                new Product("Pfeffer","1 Stück","Gewürz, gemahlen",2.49,2.79,pfeffer),
                new Product("Zucker","1 Packung","zum Kochen und Backen",3.49,2.79,zucker)
        );
        ListView<Product> right = new ListView<>();
        right.getItems().addAll(items);
        right.setMinWidth(300);
        main.setLeft(left);
        main.setRight(right);
        main.setStyle("-fx-padding:10");

        //ACTION
        update.setOnAction(actionEvent ->  {
            System.out.println("Updated");
            int selectedIdx = right.getSelectionModel().getSelectedIndex();
            if(selectedIdx != -1) {
                // Get values
                String product = txtProduct.getText();
                String quanti = txtQuantity.getText();
                String oPrice = txtOldPrice.getText();
                String nPrice = txtNewPrice.getText();
                // Set values
                if (oPrice.isEmpty()||nPrice.isBlank()||product.isBlank()||quanti.isEmpty()){
                    System.out.println("one or more fields are empty");
                } else {
                    right.getItems().get(selectedIdx).setNewPrice(Double.parseDouble(nPrice));
                    right.getItems().get(selectedIdx).setOldPrice(Double.parseDouble(oPrice));
                }
                right.refresh();
            }
        });



        //CONTROLS
        right.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            txtProduct.setText(newValue.getTitle());
            txtQuantity.setText(newValue.getQuantity());
            txtOldPrice.setText(String.valueOf(newValue.getOldPrice()));
            txtNewPrice.setText(String.valueOf(newValue.getNewPrice()));
            txtProduct.setDisable(true);
            txtQuantity.setDisable(true);
            //img make visible
            lblDesc.setText(newValue.getDescription());
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//(20) Create a Java class Product that holds all the
// properties (name, quantity, old & new price, image
// path and description). Create a single object
// (like the “Salakis Schafmilchkäse” above), and
// use that object’s to populate controls on the screen.

//(10) Create a print method (using formatting) that
// saves all products to a file report.txt . Output
// format should be similar to the section "raw data"
// in Product the description.

//optik
