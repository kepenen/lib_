package gui.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextFieldListener {

    public static void addTextFieldListener(JTextField[] fields) {
        // 循环绑定回车跳转事件
        for(int i = 0; i < fields.length; i++){
            int index = i;
            fields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    // 判断按下Enter，DOWN， UP
                    if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
                        // 不是最后一个：跳到下一个
                        if(index != fields.length -1){
                            fields[index +1].requestFocus();
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        if(index != 0){
                            fields[index -1].requestFocus();
                        }
                    }

                }
            });
        }
    }
}
