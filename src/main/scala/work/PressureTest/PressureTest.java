package work.PressureTest;

/**
 * Created by PerkinsZhu on 2018/3/1 14:51
 **/
public class PressureTest {
    public static void main(String[] args) {
        start();
    }
    private static void start(){
        System.out.println("==========================");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name ="hello";
        System.out.println(name);


    }
}
