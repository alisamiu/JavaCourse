import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MyHelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new MyHelloClassLoader();
        Class<?> myClass = classLoader.loadClass("Hello");
        Object helloObject = myClass.getDeclaredConstructor().newInstance();
        Method helloMethod = myClass.getMethod("hello");
        helloMethod.invoke(helloObject);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(name + ".xlass");
        int count = 0;
        try {
            count = stream.available();
            byte[] b = new byte[count];
            stream.read(b);
            byte[] bytes = decode(b);
            return defineClass(name, bytes,0,bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decode(byte[] bytes) {
        byte[] results = new byte[bytes.length];
        for (int i = 0 ; i < bytes.length; i++) {
            results[i] = (byte) (255 - bytes[i]);
        }
        return results;
    }
}
