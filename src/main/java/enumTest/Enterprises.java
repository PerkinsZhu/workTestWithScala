package enumTest;

/**
 * Created by PerkinsZhu on 2018/9/12 19:59
 **/
public enum Enterprises {
    GUANGFA("广发");
    private String desc;

    Enterprises(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return this.name() + "\t 描述:" + this.desc;
    }
}
