package com.guoguozhi.aqs.template;

//  发送消息模板类
public abstract class SendMessage {
    // 这些都是模板方法
    //  来自谁
    public abstract void from();
    //  发给谁
    public abstract void to();
    //  发送内容
    public abstract void content();
    //  什么时间
    public  void date() {
        System.out.println("父类实现 " + System.currentTimeMillis());
    }
    // 发送
    public abstract void send();

    //  框架方法
    public void sendMessage() {
        //  调用模板方法
        from();
        to();
        content();
        date();
        send();
    }

}
