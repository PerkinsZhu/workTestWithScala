//通过nashorn可以实现js和java的互相调用
var MyJavaClass = Java.type('lambda.TestCase.testStream');

var result = MyJavaClass.fun1('John Doe');
print(result);