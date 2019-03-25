package com.weiller.auth.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiller.utils.model.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问/connect会获取所有绑定社交的信息(显示是否绑定第三方社交信息)
 * Created on 2018/1/11 0011.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
@Component("connect/status")
public class SocialConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>();
        for (String key : connections.keySet()) {
            List<Connection<?>> connections1 = connections.get(key);
            if(connections1!=null && connections1.size()>0){
                result.put(key,true );
            }else{
                result.put(key, false);
            }
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Msg.success(result)));
    }
}