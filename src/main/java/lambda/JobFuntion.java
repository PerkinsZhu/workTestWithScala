package lambda;

/**
 * Created by PerkinsZhu on 2018/8/2 15:45
 **/
@FunctionalInterface
public interface JobFuntion {
    void doJob(String name);

    default void doJob2(String name) {

    }
}