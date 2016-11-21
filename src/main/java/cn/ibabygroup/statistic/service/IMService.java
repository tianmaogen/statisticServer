package cn.ibabygroup.statistic.service;

import cn.ibabygroup.statistic.model.IMResponse;
import cn.ibabygroup.statistic.util.RESTUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/11/7.
 */
@Service
public class IMService {

    @Value("${ibabygroup.imService.host}")
    private String host;

    /**
     * 根据讲坛id获取该讲坛现在的在线用户id集合
     * @param id
     * @return
     */
    public List<String> getOnlineIds(String id) {
        String url = host + "/cloud/im/v1/user/getGroupOnlineUserList/" + id;
        IMResponse response = RESTUtil.getForObject(url, null, IMResponse.class, true);
        if(response == null)
            return new ArrayList<>();
        List<String> list = JSONObject.parseArray(response.getData().toString(), String.class);
        return list;
    }

}
