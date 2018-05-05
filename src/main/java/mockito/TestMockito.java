package mockito;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import scala.Int;

/**
 * Created by PerkinsZhu on 2018/4/27 19:18
 **/
public class TestMockito {
    /**
     * Mockito 不是用来进行测试的，而是为了能够让单元测试成功的进行下去，用Mockito来模拟单元测试中那些不需要测试的对象。
     *  比如说：我要测试a.run()是否正确。可在run方法中又依赖于C的方法。这样就可以通过Mockito来构造一个C的模拟对象，当模拟对象被调用时返回一个
     *  正确可以用来测试的结果。
     */

    @Test()
    public void testReturnDirect() {
        String mocked = "mocked Return";
        Demo demo = Mockito.mock(Demo.class);
        Mockito.when(demo.methodNoParameters()).thenReturn(mocked);
        Assert.assertEquals(demo.methodNoParameters(), mocked);
    }

    /**
     * 测试任意基本类型参数函数mock
     */
    @Test()
    public void testMethodWithParameter() {
        String word = "mocked Return";
        Demo demo = Mockito.mock(Demo.class);
        Mockito.when(demo.speak(Mockito.anyString())).thenReturn(word);
        Assert.assertEquals(demo.speak("dd"), word);
    }

    @Test
    public void testMethodHaveChildObj(){
        Demo demo = new Demo("jack",23);
        ParameterClass  mock = Mockito.mock(ParameterClass.class);
        int size = 10;
        Mockito.when(mock.getNum("hello")).thenReturn(size);
        Assert.assertEquals(size,demo.methodHaveChildObj(mock,"hellooo"));
    }

}

class Demo {

    private String name = "laowang";
    private int age;

    public Demo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String speak(String str) {
        System.out.println(str);
        return str;
    }

    public String talk(String str) {
        return str;
    }

    public String methodNoParameters() {
        return name;
    }

    public String methodCustomParameters(ParameterClass parameter, String str) {
        return str;
    }

    public int methodHaveChildObj(ParameterClass parameter, String str) {
        int data = parameter.getNum(str);
        ParameterClass  mock = Mockito.mock(ParameterClass.class);
        int size = 10;
        Mockito.when(mock.getNum("hello")).thenReturn(size);

        System.out.println(data+"====="+str);
        return data;
    }
}

class ParameterClass {

    public ParameterClass() {

    }

    public String childTalk(String str) {
        System.out.println("=========");
        return str;
    }

    public int getNum(String str) {
        return str.length();
    }
}