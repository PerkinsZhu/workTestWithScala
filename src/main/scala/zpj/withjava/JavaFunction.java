package zpj.withjava;

/**
 * Created by PerkinsZhu on 2019/1/8 10:13
 **/
public class JavaFunction {
    public static void main(String[] args) {
        AbstractFunctionIntForJava temp = new AbstractFunctionIntForJava() {
            @Override
            public Integer apply(Integer v1) {
                return v1 * 10;
            }
        };
        System.out.println(FunctionUtil.testFunction(temp));
    }
}
