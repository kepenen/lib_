package gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * Swing多面板切换工具类
 * 基于CardLayout封装，一键注册面板、切换页面
 */
public class PageSwitcher {
    private final JPanel container;        // 存放所有页面的父容器
    private final CardLayout cardLayout;

    // 构造：传入承载页面的父面板
    public PageSwitcher(JPanel container) {
        this.container = container;
        this.cardLayout = new CardLayout();
        container.setLayout(cardLayout);
    }

    /**
     * 注册页面
     * @param pageKey 页面唯一标识key
     * @param panel   要添加的面板
     */
    public void registerPage(String pageKey, JPanel panel) {
        container.add(panel, pageKey);
    }

    /**
     * 根据key切换页面
     */
    public void switchTo(String pageKey) {
        cardLayout.show(container, pageKey);
    }

    /** 切换上一页 */
    public void prevPage() {
        cardLayout.previous(container);
    }

    /** 切换下一页 */
    public void nextPage() {
        cardLayout.next(container);
    }

    /** 首页 */
    public void firstPage() {
        cardLayout.first(container);
    }

    /** 尾页 */
    public void lastPage() {
        cardLayout.last(container);
    }
}