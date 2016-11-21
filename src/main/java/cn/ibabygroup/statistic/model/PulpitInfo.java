package cn.ibabygroup.statistic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Crowhyc on 2016/7/22.
 */
@Data
@AllArgsConstructor
@Document
public class PulpitInfo implements Serializable {
    @Id
    private String id;
    private String pulpitName;
    private Date createTime;
    private Date planStartTime;
    private Date planEndTime;
    private Date startTime;
    private Date endTime;
    private int registerLimit;
    private int registerCount;
    private int price;
    private int pulpitType;//1孕妈2医生
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private PulpitInfo() {

    }

    public static PulpitInfo createPulpitInfo(JSONObject jsonObject) throws ParseException {
        PulpitInfo info = new PulpitInfo();
        info.setId(jsonObject.getString("id"));
        updatePulpitInfo(jsonObject, info);
        return info;
    }

    private static void updatePulpitInfo(JSONObject jsonObject, PulpitInfo info) throws ParseException{
        info.pulpitName = jsonObject.getString("title");
        if (!jsonObject.isNull("createTime")) {
            String createTimeStr = jsonObject.getString("createTime");
            info.createTime = sdf.parse(createTimeStr);
        }
        if (!jsonObject.isNull("planStartTime")) {
            String planStartTimeStr = jsonObject.getString("planStartTime");
            info.planStartTime = sdf.parse(planStartTimeStr);
        }
        if (!jsonObject.isNull("planEndTime")) {
            String planEndTimeStr = jsonObject.getString("planEndTime");
            info.planEndTime = sdf.parse(planEndTimeStr);
        }
        if (!jsonObject.isNull("startTime")) {
            String startTimeStr = jsonObject.getString("startTime");
            info.startTime = sdf.parse(startTimeStr);
        }
        if (!jsonObject.isNull("endTime")) {
            String endTimeStr = jsonObject.getString("startTime");
            info.endTime = sdf.parse(endTimeStr);
        }
        info.registerLimit = jsonObject.getInt("memberLimit");
        //加上主讲医生
        info.registerCount = jsonObject.getInt("memberCount") + 1;
        info.price = jsonObject.getInt("price");
        info.pulpitType = jsonObject.getInt("userType");
    }

    public PulpitInfo updatePulpitInfo(JSONObject jsonObject) throws ParseException {
        updatePulpitInfo(jsonObject, this);
        return this;
    }


}
