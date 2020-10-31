package cn.estudy.jdk.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @description ����ʱ���Զ����������
 */
public class GeekCustomClassLoader extends ClassLoader {
    
    public static void main(String[] args)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> helloClass = new GeekCustomClassLoader().findClass("Hello");
        Object o = helloClass.newInstance();
        Method hello = helloClass.getDeclaredMethod("hello");
        hello.invoke(o);
        
    }
    
    @Override
    protected Class<?> findClass(String name) {
        
        byte[] bytes = getBytes();
        byte[] bytecode = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytecode[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytecode, 0, bytecode.length);
    }
    
    /**
     * ��ȡ�Ѽ��ܵ��ֽ�������
     *
     * 
     * @date 2020/10/21 ����10:40
     */
    private byte[] getBytes() {
        InputStream inputStream = GeekCustomClassLoader.class.getClassLoader().getResourceAsStream("Hello.xlass");
        if (inputStream == null) {
            return new byte[0];
        }
        try {
            //Ĭ���ܹ����������ֽ���
            byte[] temp = new byte[2048];
            int read = inputStream.read(temp, 0, 2048);
            byte[] bytes = new byte[read];
            System.arraycopy(temp, 0, bytes, 0, read);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}