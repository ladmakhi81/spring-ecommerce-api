package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.services.HtmlParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class HtmlParserImpl implements HtmlParser {
    @Value("${app.mail-template-directory}")
    private String mailTemplateDirectory;

    @Override
    public String parseWithArgs(String templateName, Map<String, Object> args) throws Exception {
        String htmlContent = Files.readString(Paths.get(mailTemplateDirectory + templateName + ".html"));
        for (var entry : args.entrySet()) {
            var key = String.format("{{%s}}", entry.getKey());
            htmlContent = htmlContent.replace(key, entry.getValue().toString());
        }
        return htmlContent;
    }
}
