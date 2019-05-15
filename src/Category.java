import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Category {
    private String categoryName;
    private AQPair[] aQPairs;
    private ArrayList<Rectangle> rectangles;

    public Category( )
    {
    }
    //constructs cat with catname and aqpairs
    public Category(String cat, AQPair[] aQPs )
    {
        categoryName = cat;
        aQPairs = aQPs;
    }
    //gets cat name
    public String getCategoryName()
    {
        return categoryName;
    }
    //gets aqpaur  soecificobject
    public AQPair getAQPair(int i)
    {
        return aQPairs[i];
    }
    //gets all aqpairs
    public AQPair[] getAQPairs()
    {
        return aQPairs;
    }
    //everything to a string
    public String toString()
    {
        String gameData = "GameData:\n" + "---- Category ---- " + categoryName;
        for(AQPair aqPair : aQPairs)
            gameData += aqPair;
        return gameData;
    }
}
