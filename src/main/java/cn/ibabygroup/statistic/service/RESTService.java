package cn.ibabygroup.statistic.service;

import cn.ibabygroup.statistic.model.RESTResponse;
import cn.ibabygroup.statistic.util.RESTUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/11/7.
 * 调用REST系统的service
 */
@Service
public class RESTService {

    @Value("${ibabygroup.rest.detail}")
    private String detail;
    @Value("${ibabygroup.rest.token}")
    private String token;
    @Value("${ibabygroup.rest.count}")
    private String countUrl;
    @Value("${ibabygroup.rest.isOpen}")
    private boolean isOpen;

    /**
     * 根据讲坛id获取该讲坛现在的在线用户id集合
     * @param id
     * @return
     */
    public List<String> getOnlineIds(String id) {
        String url = countUrl + "/" + id + "/" + token;
        RESTResponse restResponse = RESTUtil.getForObject(url, null, RESTResponse.class, true);
        List<String> list = JSONObject.parseArray(restResponse.getData().toString(), String.class);
        return list;
//        String ret = new HttpUtil().urlRequest("GET", countPath, null, "");
//        org.json.JSONObject jsonObject = new org.json.JSONObject(ret);
//        org.json.JSONArray arr = jsonObject.getJSONArray("data");
//        List<String> ids = new ArrayList<>();
//        for (int i = 0; i < arr.length(); i++) {
//            ids.add(arr.getString(i));
//        }
    }


}
