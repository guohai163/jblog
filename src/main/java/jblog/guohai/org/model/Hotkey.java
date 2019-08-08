package jblog.guohai.org.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 热词
 */
public class Hotkey {

    /**
     * 词
     */
    @Getter
    @Setter
    String hotkey;

    /**
     * 词出现次数
     */
    @Getter
    @Setter
    Integer hotkeyCount;

    public Hotkey(){

    }

    public Hotkey(String hotkey,Integer hotkeyCount){
        this.hotkey = hotkey;
        this.hotkeyCount = hotkeyCount;
    }
}
