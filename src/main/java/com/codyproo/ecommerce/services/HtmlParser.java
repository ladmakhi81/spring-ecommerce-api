package com.codyproo.ecommerce.services;

import java.util.Map;

public interface HtmlParser {
    String parseWithArgs(String templateName, Map<String, Object> args) throws Exception;
}
