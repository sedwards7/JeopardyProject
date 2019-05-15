//creates a method that will save the counter in a loop and allow it to be accessed in a lambda expression
public class CounterForLambda {
    private int counter;
    //passes in the value and svaes it with the on=bject
    public CounterForLambda(int i){
        this.counter = i;
    }
    //get the counter value for lambda
    public int getCounter() {
        return counter;
    }
}
