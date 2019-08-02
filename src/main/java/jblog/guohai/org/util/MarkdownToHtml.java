package jblog.guohai.org.util;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

/**
 * Markdown文档转html
 */
public class MarkdownToHtml {
    private static HtmlRenderer renderer;
    private static Parser parser;

    static {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    /**
     * 转换
     *
     * @param markdownContent markdown内容
     * @return
     */
    public static String convert(String markdownContent) {
        Node document = parser.parse(markdownContent);
        return renderer.render(document);
    }
}
