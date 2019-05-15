public class AQPair {
    private int pointValue;
    private String answer;
    private String question;
    private String image;

    public AQPair( )
    {
    }
    //constructs aqpqairs with points answer question and image
    public AQPair(int pts, String ans, String qst, String img)
    {
        pointValue = pts;
        answer = ans;
        question = qst;
        image = img;
    }
    //gets the point value
    public int getPointValue( )
    {
        return pointValue;
    }
    //gets the answer
    public String getAnswer( )
    {
        return answer;
    }
    //gets the question
    public String getQuestion( )
    {
        return question;
    }
    //gets the image filename
    public String getImage( )
    {
        return image;
    }
    //everything to string
    public String toString()
    {
        return ("\n Point Value: " + pointValue +
                "\n      Answer: " + answer +
                "\n    Question: " + question +
                "\n       Image: " + image);
    }
}
