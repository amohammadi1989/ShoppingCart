package com.behsazan.baseclasses.command;

import com.behsazan.samyar.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@Component
@Order(1)
public class XSSCheckRequestFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XSSCheckRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        //this method is empty
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        boolean haveError;
        haveError = checkingXSSAttack(request);
        if (!haveError) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        } else {
            throw new SecurityException("occurred an security exception, xss attack detected");
        }
    }

    @Override
    public void destroy() {
        //this method is empty
    }

    private boolean checkingXSSAttack(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Map<String, String[]> params = httpServletRequest.getParameterMap();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String[] value = entry.getValue();
            if (value != null && checkEntry(entry, params)) {
                return true;
            }
        }
        String uri = httpServletRequest.getRequestURI();
        return xssAttackJavaScriptOnly(uri, null, params.size());
    }

    private static Boolean checkEntry(Map.Entry<String, String[]> entry, Map<String, String[]> params) {
        String key = entry.getKey();
        String[] value = entry.getValue();
        for (String s : value) {
            if (xssAttackJavaScriptOnly(s, key, params.size())) {
                return true;
            }
        }
        return false;
    }

    private static boolean xssAttackJavaScriptOnly(String value, String key, int paramsSize) {
        String value1 = StringUtils.replace(value, "%3C", "<");
        value1 = StringUtils.replace(value1, "%3c", "<");
        value1 = StringUtils.replace(value1, "%3E", ">");
        value1 = StringUtils.replace(value1, "%3e", ">");
        value1 = StringUtils.replace(value1, " ", "");
        value1 = StringUtils.lowerCase(value1);
        boolean hasXss = (StringUtils.contains(value1, "<script>") || StringUtils.contains(value1, "</script>"));
        if (!hasXss && (StringUtils.isNotBlank(key))) {
            boolean keyCondition = key.equals("endpointUrl") || key.equals("referenceId") || key.equals("encryptedPan");
            if (keyCondition) {
                String changeValue = null;
                try {
                    changeValue = java.net.URLDecoder.decode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    hasXss = true;
                }
                changeValue = StringEscapeUtils.escapeHtml4(changeValue);
                if (((changeValue).contains("&quot;") || paramsSize > Constants.NUMBER_3) || (key.equals("endpointUrl"))) {
                    hasXss = true;
                }
            }
        }
        return hasXss;
    }

}
