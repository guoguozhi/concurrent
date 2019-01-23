package com.guoguozhi.aqs.template;

/**
 *  模板设计模式
 *  1.在父类(抽象类)中提供模板方法
 *  2.在父类中提供的模板方法可以是抽象方法(不实现)，也可以是非抽象的方法(父类实现)
 *  3.然后在父类中定义框架方法，在框架方法中按一定的逻辑组织并调用模板方法
 *  4.在子类中实现模板方法，然后在合适的位置调用父类的模板方法
 */
public class SendSMS extends SendMessage {

    @Override
    public void from() {
        System.out.println("消息来自六小龄童");
    }

    @Override
    public void to() {
        System.out.println("发送给小小六龄童");
    }

    @Override
    public void content() {
        System.out.println("不要在网上传播黑我");
    }

    @Override
    public void send() {
        System.out.println("调用短信网关即将发送");
    }

    public static void main(String[] args) {
       SendSMS sendSMS = new SendSMS();
       //  在合适的位置调用框架方法
       sendSMS.sendMessage();
    }
}
