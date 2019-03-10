package zpj.baseTest.singleInstance;

/**
 * Created by PerkinsZhu on 2019/3/9 9:24
 * <p>
 * 用枚举实现单例
 **/
public enum Singleton {
    INSTANCE;

    private Singleton() {
        System.out.println("--------create instance---");
    }

    public void doTask() {
        System.out.println("-----do task----");
    }
}