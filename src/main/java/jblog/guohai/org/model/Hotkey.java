package jblog.guohai.org.model;

import lombok.*;

/**
 * 热词
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotkey {

    /**
     * 词
     */
    String hotkey;

    /**
     * 词出现次数
     */
    Integer hotkeyCount;

}
