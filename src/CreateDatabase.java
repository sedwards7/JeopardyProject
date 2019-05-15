///*
//   CSC225DatabaseApp -- demonstration of an "embedded" JDBC database using Apache
//   Derby. Another Derby database version is client/server based. When Derby runs in
//   an embedded framework, the JDBC application and Derby run in the same
//   Java Virtual Machine (JVM). The application starts up the Derby engine.
//   Derby uses industry standard SQL.
//*/
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;
//
//public class CreateDatabase
//{
//    private String framework = "embedded";    // mode of operating (not client/server)
//    private String protocol = "jdbc:derby:";  // protocol for the translation between systems
//    private ResultSet resultSetA;
//    private ResultSet resultSetB;
//    private ResultSet resultSetC;
//    private ResultSet resultSetD;
//    private ResultSet resultSetE;
//
//    /*
//      When running this demo, you must include the correct driver in the
//      classpath of the JVM. This can result in some consternation for users.
//
//      Here's how to set this in IntelliJ
//      1) Go to File | Project Structure, click on Modules, and click on your Module
//      2) Choose the "Dependencies" tab
//      3) Click the "+" button on the right-hand side and select "Jars and directories..."
//      4) Add the directory(ies) you want (note, you can multi-select) and click OK
//      5) Make sure you're using that Module in your run target
//    */
//
//    public static void main(String[] args) throws FileNotFoundException
//    {
//
//        new CreateDatabase( ).go( );
//        System.out.println("CSC225DatabaseApp is finished");
//    }
//
//    public static ArrayList<String> fileStrings () throws FileNotFoundException{
//        String fileNames[] = {"columnA.txt", "columnB.txt", "columnC.txt", "columnD.txt", "columnE.txt", "columnF.txt",
//                "columnG.txt", "columnH.txt", "columnI.txt", "columnJ.txt", "columnL.txt", "columnM.txt"};
//
//        ArrayList<String> fileStrings = new ArrayList<>();
//
//        for(String fileName : fileNames) {
//            File file = new File(fileName);
//            Scanner fileScanner = new Scanner(file);
//
//            fileScanner.nextLine();
//            fileScanner.nextLine();
//            fileScanner.nextLine();
//            while (fileScanner.hasNextLine()) {
//                    fileStrings.add(fileScanner.nextLine());
//            }
//        }
//        fileStrings.trimToSize();
//        return fileStrings;
//    }
//
//    private void go( ) throws FileNotFoundException
//    {
//        Random myRandom = new Random(12345678L);
//        System.out.println("CSC225DatabaseApp starting in " + framework + " mode");
//
//      /*
//       Program is using Statement and PreparedStatement objects for
//       executing SQL. It is storing the Statement and PreparedStatement
//       object references in an ArrayList object for convenience.
//      */
//
//        // A connection with a specific database.  SQL statements are executed in the context of a connection.
//        Connection conn = null;
//
//        // ArrayList of Statements, PreparedStatements
//        ArrayList<Statement> statements = new ArrayList<Statement>();
//
//        // PreparedStatements for inserting and updating data.  These are "precompiled" statements.
//        PreparedStatement psInsert;
//        PreparedStatement psUpdate;
//
//        // Statements are for executin SQL
//        Statement s;
//
//
//
//        try
//        {
//            String dbName = "CSC225DataBase"; // the name of the database
//         /*
//          This connection specifies create=true in the connection object to
//          cause the database to be created when connecting for the first
//          time. To remove the database, remove the directory derbyDB (the
//          same as the database name) and its contents. The directory CSC225DataBase
//          will be recreated.
//         */
//            conn = DriverManager.getConnection(protocol + dbName + ";create=true");
//            System.out.println("Connected to and created database " + dbName);
//
//            // To control transactions manually, connection autocommit is false.
//            conn.setAutoCommit(false);
//
//         /*
//            Creating a statement object that we can use for running various
//            SQL statements commands against the database.
//         */
//            s = conn.createStatement();
//            statements.add(s);
//
//            // Create a table...
//            s.execute("create table jeopardy_data(category varchar(40), score int, answer varchar(300), question varchar(300), image varchar(100))");
//
//            // and add a few rows...
//
//         /*
//          It is recommended to use PreparedStatements when you are
//          repeating execution of an SQL statement. PreparedStatements also
//          allows you to parameterize variables. By using PreparedStatements
//          you may increase performance (because the Derby engine does not
//          have to recompile the SQL statement each time it is executed).
//         */
//            // add records to the database.  The question marks represent parameters
//            // parameter 1 is weekday (varchar), parameter 2 is temperature (int)
//            psInsert = conn.prepareStatement("insert into jeopardy_data values (?, ?, ?, ?, ?)");
//            statements.add(psInsert);
//
//            //Creates ArrayList out of the data from the CSV
//            ArrayList<String> fileData = fileStrings();
//
//            // insert a bunch more,  in a loop
//            int categoryPosition = 0;
//                for (int i = 0; i < fileData.size()-4; i+=4) {
//
//                    if ((i+1) % 21 == 0 && i != 0){
//                        categoryPosition += 21;
//                        i++;
//                    }
//                    psInsert.setString(1, fileData.get(categoryPosition));
//                    psInsert.setInt(2, Integer.parseInt(fileData.get(i + 1)));
//                    psInsert.setString(3, fileData.get(i + 2));
//                    psInsert.setString(4, fileData.get(i + 3));
//                    psInsert.setString(5, fileData.get(i + 4));
//                    psInsert.executeUpdate();
//                }
//
//
//            // Update a row....
//            // The question marks represent the parameter indicies 1, 2, and 3
//            psUpdate = conn.prepareStatement("update jeopardy_data set category=?, score=?, answer=?, question=?, image=?");
//            statements.add(psUpdate);
//
//            // A ResultSet is table of data which is usually generated by executing a statement that queries the database.
//            // The SQL is like natural language -- easy to understand for the most part
//            resultSetA = s.executeQuery("SELECT category FROM jeopardy_data");
//            printResultSet(resultSetA);
//
//            resultSetB = s.executeQuery("SELECT score FROM jeopardy_data");
//            printResultSet(resultSetB);
//
//            resultSetC = s.executeQuery("SELECT answer FROM jeopardy_data");
//            printResultSet(resultSetC);
//
//            resultSetD = s.executeQuery("SELECT question FROM jeopardy_data");
//            printResultSet(resultSetD);
//
//            resultSetE = s.executeQuery("SELECT image FROM jeopardy_data");
//            printResultSet(resultSetE);
//
//            // delete the table
//            s.execute("drop table jeopardy_data");
//            System.out.println("Dropped table jeopardy_data");
//
//            // Manually commit the transaction. Any changes are now in the database.
//            conn.commit();
//            System.out.println("Committed the transaction");
//
//            // In embedded mode, the application should shut down the database.
//            DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            System.out.println("Database has shut down...");
//        }
//        catch (SQLException e)
//        {
//            System.out.println(e.getMessage( ));
//        }
//    }
//
//    //getters for getting the reult sets
//    public ResultSet getResultSetA() {
//        return resultSetA;
//    }
//
//    public ResultSet getResultSetB() {
//        return resultSetB;
//    }
//
//    public ResultSet getResultSetC() {
//        return resultSetC;
//    }
//
//    public ResultSet getResultSetD() {
//        return resultSetD;
//    }
//
//    public ResultSet getResultSetE() {
//        return resultSetE;
//    }
//
//    private void printResultSet(ResultSet resultSet) throws SQLException
//    {
//        ResultSetMetaData resultSetMetaData = resultSet.getMetaData( );
//        System.out.println("************************************");
//
//        System.out.println("Results of query (a result set)...");
//
//        // process the data in the result set
//        while(resultSet.next())
//        {
//            for(int i = 1; i <= resultSetMetaData.getColumnCount( ); i++)
//                System.out.print(resultSet.getObject(i) + " ");
//            System.out.println( );
//        }
//        System.out.println("...end of result set");
//        System.out.println("************************************");
//    }
//}