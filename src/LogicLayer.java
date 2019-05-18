/*
Author:Sean Edwards
Date:4/22/19
Version: 1
Description:A simple jeopardy game made with javafx
IMPORTANT: scaling for windows needs to be at 100%. Got to display settings and change the scaling to 100%
if things seem a little off. This was the setting my computer was on when program was made
 */
import com.sun.istack.internal.NotNull;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.sql.ResultSet.*;

public class LogicLayer extends Application {

    private String framework = "embedded";    // mode of operating (not client/server)
    private String protocol = "jdbc:derby:";  // protocol for the translation between systems

    public void start (Stage primaryStage) throws FileNotFoundException
    {
        double xPosition = 35;
        double yPosition = 80;
        int height = 175;
        int width = 300;
        Pane pane = new Pane();
        //-------------------------------------------------------------------------------------------------------------------
        String fileNames[] = {"columnA.txt", "columnB.txt", "columnC.txt", "columnD.txt", "columnE.txt", "columnF.txt"};
        ArrayList<Category> jeopardyData = null;

        try {
            jeopardyData = getCategories(go());
        } catch (SQLException e) {
            System.out.println("Error: Problem with the database!");
            System.exit(1); // terminates the program, code other than 0 indicates abend
        }
        System.out.println(jeopardyData); // prints all of the data for the game
       // ----------------------------------------------------------------------------
        //creates columns for each category
//        for(Category cat: jeopardyData) {
//            ArrayList<Rectangle> rectangles = new ArrayList<>();
//            ArrayList<Text> scores = new ArrayList<>();
//            //makes the text header for each category
//            Text header = new Text(cat.getCategoryName());
//            header.setFont(Font.font("Helvetica", 30));
//            int headerLength = header.getText().length();
//            header.setX(xPosition + (400.0 / headerLength));//attempts to keep the text x position relative to its length
//            header.setY(40);
//            //loop to take care of individual rectangles and aqpairs
//            for (int i = 0; i < 5; i++) {
//                CounterForLambda counter = new CounterForLambda(i);//quick counter class i made to be able to see the counter inside the lambda
//                //adds rectangles for each category
//                rectangles.add(new Rectangle(xPosition, yPosition, width, height));
//                rectangles.get(i).setStroke(Color.BLACK);
//                rectangles.get(i).setFill(Color.BLUE);
//                //adds the scores on top of each rectangle
//                scores.add(new Text(Integer.toString(cat.getAQPair(i).getPointValue())));//get point value from aqpair relative to the category
//                scores.get(i).setFill(Color.GOLDENROD);//similar color to jeopardy
//                scores.get(i).setFont(Font.font("Verdana", 50));
//                scores.get(i).setX(rectangles.get(i).getX() + (width / 3.0) + 10);//attempts to posiition score right in the middke of the rectangles
//                scores.get(i).setY(rectangles.get(i).getY() + (height / 1.7));
//                scores.get(i).toFront();//brings the scores in front of the rectangles
//                scores.get(i).setMouseTransparent(true);//makes scores invisible to the mouse
//                scores.trimToSize();
//                //creates a different rectangle that will go full screen when score rectangle is clicked
//                Rectangle fullScreenRectangle = new Rectangle(rectangles.get(i).getX(), rectangles.get(i).getY(), width, height);
//                fullScreenRectangle.setStroke(Color.BLACK);
//                fullScreenRectangle.setFill(Color.BLUE);
//                //creates action when the scores rectangles are clicked
//                rectangles.get(i).setOnMouseClicked(e -> {
//                    scores.get(counter.getCounter()).setFill(Color.WHITE);//makes the score white when clicked
//                    //creates the answer text
//                    Text answer = new Text(cat.getAQPair(counter.getCounter()).getAnswer().replaceAll("  +", "\n"));//replaces multiple spaces with next lines for cleanliness
//                    answer.setFont(Font.font("Helvetica", 20));
//                    answer.setFill(Color.WHITE);
//                    answer.setX(fullScreenRectangle.getX());//smaeposition of new rectangle
//                    answer.setY(fullScreenRectangle.getY());
//                    answer.setWrappingWidth(fullScreenRectangle.getWidth());
//                    answer.setMouseTransparent(true);//text invisible to mouse
//                    answer.toFront();
//                    //for adding an image if the is one
//                    ImageView selectedImage = new ImageView();
//                    //chekcs if there is an image
//                    if(cat.getAQPair(counter.getCounter()).getImage().equalsIgnoreCase("none")){
//                    }else{
//                        //if there is, gets image from file and add it to image view
//                        File imageName = new File(cat.getAQPair(counter.getCounter()).getImage());
//                        Image image = new Image(imageName.toURI().toString());
//                        selectedImage.setX(answer.getX() + 50);
//                        selectedImage.setY(answer.getY() + 50);
//                        selectedImage.setScaleX(3);
//                        selectedImage.setScaleY(3);
//                        selectedImage.toFront();
//                        selectedImage.setMouseTransparent(true);
//                        selectedImage.setImage(image);
//                    }
//                    //found a class online the allows the text to grow progressively with the rectangle
//                    //using key vlaue and frame does not work to increase font size trnasitionally
//                    TextSizeTransition textTrans = new TextSizeTransition(answer, 20, 100, Duration.millis(500));
//
//                    pane.getChildren().addAll(fullScreenRectangle, answer);//adds rectangle and answer at the same time
//
//                    textTrans.play();
//                    //adds the images after the other animations are done
//                    Timeline animation = new Timeline(
//                            new KeyFrame(Duration.millis(500), k ->{
//                                pane.getChildren().add(selectedImage);
//                            }));
//                    animation.play();
//                    //changes properties of the answer and the rectangle to grow to fullscreen over time
//                    final Timeline timeline = new Timeline();
//                    final KeyValue kvX = new KeyValue(fullScreenRectangle.xProperty(), 0);
//                    final KeyFrame kfX = new KeyFrame(Duration.millis(500), kvX);
//
//                    final KeyValue kvY = new KeyValue(fullScreenRectangle.yProperty(), 0);
//                    final KeyFrame kfY = new KeyFrame(Duration.millis(500), kvY);
//
//                    final KeyValue kvWidth = new KeyValue(fullScreenRectangle.widthProperty(), pane.getWidth());
//                    final KeyFrame kfwidth = new KeyFrame(Duration.millis(500), kvWidth);
//
//                    final KeyValue kvHeight = new KeyValue(fullScreenRectangle.heightProperty(), pane.getHeight());
//                    final KeyFrame kfHeight = new KeyFrame(Duration.millis(500), kvHeight);
//
//                    final KeyValue kvAnsX = new KeyValue(answer.xProperty(), 50);
//                    final KeyFrame kfAnsX = new KeyFrame(Duration.millis(500), kvAnsX);
//
//                    final KeyValue kvAnsY = new KeyValue(answer.yProperty(), 100);
//                    final KeyFrame kfAnsY = new KeyFrame(Duration.millis(500), kvAnsY);
//
//                    final KeyValue kvAnsWidth = new KeyValue(answer.wrappingWidthProperty(), pane.getWidth() );
//                    final KeyFrame kfAnsWidth = new KeyFrame(Duration.millis(500), kvAnsWidth);
//
//                    timeline.getKeyFrames().addAll(kfwidth, kfHeight, kfX, kfY, kfAnsX, kfAnsY, kfAnsWidth);
//                    timeline.play();
//                    //creates click event on the fullscreen rectangle
//                    fullScreenRectangle.setOnMouseClicked(g ->{
//                        pane.getChildren().removeAll(answer, selectedImage);//removes image and answer
//                        //creates the question just like the answer
//                        Text question = new Text(cat.getAQPair(counter.getCounter()).getQuestion());
//                        question.setFont(Font.font("Helvetica", 80));
//                        question.setFill(Color.WHITE);
//                        question.setX(50);
//                        question.setY(100);
//                        question.setWrappingWidth(fullScreenRectangle.getWidth());
//                        question.setMouseTransparent(true);
//                        question.toFront();
//
//                        pane.getChildren().add(question);//addds question to screen
//                        //addds second click event for fullscreen rectangle
//                        fullScreenRectangle.setOnMouseClicked(f ->{
//                            pane.getChildren().removeAll(question, fullScreenRectangle);//removes rectangle and question
//                        });
//                    });
//                });
//                yPosition += 190;//moves each new rectangle down evenly spaced
//            }
//            yPosition = 80;//returns y position of rectangle to original location
//            xPosition += 310;//moves the next column over
//           //addAll wont allow more than one list to be added
//            pane.getChildren().addAll(rectangles);
//            pane.getChildren().addAll(scores);
//            pane.getChildren().addAll(header);
//
//        }
//
//        //--------------------------------------------------------------------------------------------------
//        // Create a scene and place it in the stage
//        Scene scene = new Scene(pane, 1920, 1020);
//        primaryStage.setTitle("Jeopardy"); // Set the stage title
//        primaryStage.setScene(scene); // Place the scene in the stage
//        primaryStage.show(); // Display the stage
    }
//-----------------------------------------------------------------------------------------------------------------------
    //creates the category objects from the files
    private static ArrayList<Category> getCategories (ArrayList<String> results) throws SQLException
    {
        final int AQ_PAIR_COUNT = 5;
//        AQPair[] aQPs = new AQPair[AQ_PAIR_COUNT];

//        for(int i = 1; i < AQ_PAIR_COUNT * 6; i+=5)
//        {
//            aQPs[i] = new AQPair(Integer.parseInt(results.get(i)), // read the point value
//                    results.get(i+1),  // read the answer
//                    results.get(i+2),  // read the question
//                    results.get(i+3)); // read image file name
//        }

        ArrayList<Category> gameData = new ArrayList<>( );
        for (int j = 0; j < results.size(); j+=5)
        {
            AQPair[] aQPs = new AQPair[AQ_PAIR_COUNT];

            for(int i = 1; i < AQ_PAIR_COUNT * 5; i+=5)
            {
                aQPs[i] = new AQPair(Integer.parseInt(results.get(i)), // read the point value
                        results.get(i+1),  // read the answer
                        results.get(i+2),  // read the question
                        results.get(i+3)); // read image file name
            }
            String categoryName ;
            if (j % 20 == 0) {
                categoryName = results.get(j);
                Category aCategory = new Category(categoryName, aQPs);
                gameData.add(aCategory);
            }
//            AQPair[] aQPs = readAQs(results);

        }
        return gameData;
    }
//----------------------------------------------------------------------------------------------------------------------
    //not needed after adding database functionality
    //reads the header for each category
    public static String readHeader(Scanner fileScanner)
    {
        for(int i = 0; i < 3; i++)    // header consists of three blank lines and the category name
            fileScanner.nextLine();
        return fileScanner.nextLine(); // returns the Category name
    }
//-----------------------------------------------------------------------------------------------------------------------------
   //reads the answers and questions in string form
//    public static AQPair[] readAQs(ArrayList<String> aqs) throws SQLException
//    {
//        final int AQ_PAIR_COUNT = 5;
//        AQPair[] aQPs = new AQPair[AQ_PAIR_COUNT];
//
//        for(int i = 1; i < AQ_PAIR_COUNT * 6; i+=5)
//        {
//            aQPs[i] = new AQPair(Integer.parseInt(aqs.get(i)), // read the point value
//                                 aqs.get(i+1),  // read the answer
//                                 aqs.get(i+2),  // read the question
//                                 aqs.get(i+3)); // read image file name
//        }
//        return aQPs;
//    }
    //------------------------------------------------------------------------------------------------------
    private static ArrayList<String> fileStrings () throws FileNotFoundException {
        String fileNames[] = {"columnA.txt", "columnB.txt", "columnC.txt", "columnD.txt", "columnE.txt", "columnF.txt",
                "columnG.txt", "columnH.txt", "columnI.txt", "columnJ.txt", "columnL.txt", "columnM.txt"};

        ArrayList<String> fileStrings = new ArrayList<>();

        for(String fileName : fileNames) {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            fileScanner.nextLine();
            fileScanner.nextLine();
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                fileStrings.add(fileScanner.nextLine());
            }
        }
        fileStrings.trimToSize();
        return fileStrings;
    }
    //---------------------------------------------------------------------------------------------------------------------
    private ArrayList<String> go( ) throws FileNotFoundException
    {

        Random myRandom = new Random(12345678L);
        System.out.println("CSC225DatabaseApp starting in " + framework + " mode");

      /*
       Program is using Statement and PreparedStatement objects for
       executing SQL. It is storing the Statement and PreparedStatement
       object references in an ArrayList object for convenience.
      */

        // A connection with a specific database.  SQL statements are executed in the context of a connection.
        Connection conn = null;

        // ArrayList of Statements, PreparedStatements
        ArrayList<Statement> statements = new ArrayList<Statement>();

        // PreparedStatements for inserting and updating data.  These are "precompiled" statements.
        PreparedStatement psInsert;
        PreparedStatement psUpdate;

        // Statements are for executin SQL
        Statement s;

        ArrayList<String> results = new ArrayList<>();
        try
        {
            String dbName = "CSC225DataBase"; // the name of the database
         /*
          This connection specifies create=true in the connection object to
          cause the database to be created when connecting for the first
          time. To remove the database, remove the directory derbyDB (the
          same as the database name) and its contents. The directory CSC225DataBase
          will be recreated.
         */
            conn = DriverManager.getConnection(protocol + dbName + ";create=true");
            System.out.println("Connected to and created database " + dbName);

            // To control transactions manually, connection autocommit is false.
            conn.setAutoCommit(false);

         /*
            Creating a statement object that we can use for running various
            SQL statements commands against the database.
         */
            s = conn.createStatement();
            statements.add(s);

            // Create a table...
            s.execute("create table jeopardy_data(category varchar(40), score int, answer varchar(300), question varchar(300), image varchar(100))");

            // and add a few rows...

         /*
          It is recommended to use PreparedStatements when you are
          repeating execution of an SQL statement. PreparedStatements also
          allows you to parameterize variables. By using PreparedStatements
          you may increase performance (because the Derby engine does not
          have to recompile the SQL statement each time it is executed).
         */
            // add records to the database.  The question marks represent parameters
            // parameter 1 is weekday (varchar), parameter 2 is temperature (int)
            psInsert = conn.prepareStatement("insert into jeopardy_data values (?, ?, ?, ?, ?)");
            statements.add(psInsert);

            //Creates ArrayList out of the data from the CSV
            ArrayList<String> fileData = fileStrings();

            // insert a bunch more,  in a loop
            int categoryPosition = 0;
            for (int i = 0; i < fileData.size()-4; i+=4) {

                if ((i+1) % 21 == 0 && i != 0){
                    categoryPosition += 21;
                    i++;
                }
                psInsert.setString(1, fileData.get(categoryPosition));
                psInsert.setInt(2, Integer.parseInt(fileData.get(i + 1)));
                psInsert.setString(3, fileData.get(i + 2));
                psInsert.setString(4, fileData.get(i + 3));
                psInsert.setString(5, fileData.get(i + 4));
                psInsert.executeUpdate();
            }


            // Update a row....
            // The question marks represent the parameter indicies 1, 2, and 3
            psUpdate = conn.prepareStatement("update jeopardy_data set category=?, score=?, answer=?, question=?, image=?");
            statements.add(psUpdate);

            // A ResultSet is table of data which is usually generated by executing a statement that queries the database.
            // The SQL is like natural language -- easy to understand for the most part

            ResultSet resultSetA = s.executeQuery("SELECT * FROM jeopardy_data");
            printResultSet(resultSetA);
//            ArrayList<String> results = resultSetToString(resultSetA);
            ResultSetMetaData rsmd = resultSetA.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (resultSetA.next()) {
                int i = 1;
                while(i <= columnCount) {
                    results.add(resultSetA.getString(i++));
                }
            }

//            ResultSet resultSetB = s.executeQuery("SELECT score FROM jeopardy_data");
//            //printResultSet(resultSetB);
//            setOfResultStrings.addAll(resultSetToString(resultSetB));
//
//
//            ResultSet resultSetC = s.executeQuery("SELECT answer FROM jeopardy_data");
//            //printResultSet(resultSetC);
//            setOfResultStrings.addAll(resultSetToString(resultSetC));
//
//
//            ResultSet resultSetD = s.executeQuery("SELECT question FROM jeopardy_data");
//            //printResultSet(resultSetD);
//            setOfResultStrings.addAll(resultSetToString(resultSetD));
//
//
//            ResultSet resultSetE = s.executeQuery("SELECT image FROM jeopardy_data");
//            //printResultSet(resultSetE);
//            setOfResultStrings.addAll(resultSetToString(resultSetE));

//            setOfResultStrings.trimToSize();


            // delete the table
            s.execute("drop table jeopardy_data");
            System.out.println("Dropped table jeopardy_data");

            // Manually commit the transaction. Any changes are now in the database.
            conn.commit();
            System.out.println("Committed the transaction");

            // In embedded mode, the application should shut down the database.
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            System.out.println("Database has shut down...");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage( ));
        }
        return results;
    }
    //prints result set
    private void printResultSet(ResultSet resultSet) throws SQLException
    {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData( );
        System.out.println("************************************");

        System.out.println("Results of query (a result set)...");

        // process the data in the result set
        while(resultSet.next())
        {
            for(int i = 1; i <= resultSetMetaData.getColumnCount( ); i++)
                System.out.print(resultSet.getObject(i) + " ");
            System.out.println( );
        }
        System.out.println("...end of result set");
        System.out.println("************************************");
    }
    //turns result sets to arrays of strings
    private ArrayList<String> resultSetToString(ResultSet resultSet) throws SQLException
    {
//        ResultSetMetaData resultSetMetaData = resultSetA.getMetaData( );
        ArrayList<String> resultStrings = null;
        int counter = 0;
        // process the data in the result set
        while (resultSet.next()) {
            counter++;
            for (int i = 1; i <= counter; i++) {
                resultStrings.add(resultSet.getString("categories"));
            }
        }
        resultStrings.trimToSize();

        return resultStrings;
    }

}
