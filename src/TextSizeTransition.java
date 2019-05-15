//This code copied from  https://stackoverflow.com/questions/51013522/how-to-animate-font-size-of-text-node-in-javafx

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextSizeTransition extends Transition{

    private Text text; // a little generic -> subclasses: ButtonBase, Cell, Label, TitledPane
    private int start, end; // initial and final size of the text

    public TextSizeTransition(Text text, int start, int end, Duration duration) {
        this.text = text;
        this.start = start;
        this.end = end - start; // minus start because of (end * frac) + start in interpolate() 
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        //setCycleCount(100);
        // and a lot of other methods 
    }

    @Override
    protected void interpolate(double frac) {
        // frac value goes from 0 to 1
        // when frac is zero -> size is start
        // when frac is 1 -> size is end + start 
        //(that's why we this.end = end - start; above to back to original end value)
        int size = (int) ((end * frac) + start);
        if(size<=end) {
            text.setFont(Font.font(size));
        }else { // once the size reaches the destination (i.e. end value)
            // back to the start size if you want
            //text.setFont(Font.font(start));
        }
    }
}
