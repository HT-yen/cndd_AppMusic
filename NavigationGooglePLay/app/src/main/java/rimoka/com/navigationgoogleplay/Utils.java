package rimoka.com.navigationgoogleplay;

/**
 * Created by HOARIMOKA on 4/1/2017.
 */

public class Utils {
    public String showTime(long milliseconds ){
       String timer="";
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if(hours>0){
            timer+=String.valueOf(hours)+":";
        }
        else timer+="0:";
        if(minutes>0){
            if(minutes<=9) timer+="0";
            timer+=String.valueOf(minutes)+":";
        }
        else timer+="00:";
        if(seconds>0){
            if(seconds<=9) timer+="0";
            timer+=String.valueOf(seconds);
        }
        else timer+="00";

        return timer;
    }
    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     * @param progress -
     * @param totalDuration
     * returns current duration in milliseconds
     * */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000); //chuyển về seconds
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);
        // return current duration in milliseconds
        return currentDuration * 1000;
    }
}
