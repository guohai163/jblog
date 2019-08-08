package jblog.guohai.org.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import jblog.guohai.org.model.Hotkey;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HotkeyDao {

    /**
     * 增加一个新的热词默认数量1
     * @param hot 热词
     * @return 是否成功
     */
    @Insert("INSERT INTO `jblog_hotkey`(hotkey,hotkey_count)VALUES(#{hot.hotkey},1);")
    Boolean addHotkey(@Param("hot") Hotkey hot);

    /**
     * 为一个指定的热词+1
     * @param hot 热词
     * @return 是否成功
     */
    @Update("UPDATE jblog_hotkey SET hotkey_count=hotkey_count+1 WHERE hotkey=#{hot.hotkey};")
    Boolean setHotkeyCountAdd1(@Param("hot") Hotkey hot);

    @Update("UPDATE jblog_hotkey SET hotkey_count=hotkey_count-1 WHERE hotkey=#{hot.hotkey} AND hotkey_count>0;")
    Boolean setHotkeyCountDec1(@Param("hot") Hotkey hot);
    /**
     * 通过KEY查找热词
     * @param key 热词
     * @return 展示数量
     */
    @Select("SELECT * FROM jblog_hotkey WHERE hotkey=#{key};")
    Hotkey getHotkeybyKey(@Param("key") String key);

    /**
     * 活的最热的100个词
     * @return 前100个热词的KEY
     */
    @Select("SELECT * FROM jblog_hotkey ORDER BY hotkey_count DESC LIMIT 100;")
    List<Hotkey> getHotkeyTop100();
}
