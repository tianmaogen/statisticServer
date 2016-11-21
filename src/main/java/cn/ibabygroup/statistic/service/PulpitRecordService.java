package cn.ibabygroup.statistic.service;

import cn.ibabygroup.statistic.dao.PulpitInfoDao;
import cn.ibabygroup.statistic.dao.PulpitRecordDao;
import cn.ibabygroup.statistic.dto.PulpitRecordDto;
import cn.ibabygroup.statistic.model.PageDataModel;
import cn.ibabygroup.statistic.model.PulpitInfo;
import cn.ibabygroup.statistic.model.PulpitRecord;
import cn.ibabygroup.statistic.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Crowhyc on 2016/7/25.
 */
@Service
@Slf4j
public class PulpitRecordService {
    @Value("${ibabygroup.rest.detail}")
    private String detail;
    @Value("${ibabygroup.rest.token}")
    private String token;
    @Value("${ibabygroup.rest.count}")
    private String countUrl;
    @Value("${ibabygroup.rest.isOpen}")
    private boolean isOpen;
    @Autowired
    private RESTService restService;
    @Autowired
    private IMService imService;
    @Autowired
    private PulpitInfoDao infoDao;
    @Autowired
    private PulpitRecordDao recordDao;
    private final List<String> runPulpits = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void recordPulpitInfo() {
        if (!isOpen) {
            return;
        }
        JSONObject infoObject;
        ArrayList<String> nowRunPulpit = new ArrayList<>();
        try {
            infoObject = new JSONObject(new HttpUtil().urlRequest("get", detail, token, ""));
            JSONArray infoArray = infoObject.getJSONObject("Data").getJSONArray("data");
            for (int i = 0; i < infoArray.length(); i++) {
                JSONObject result = infoArray.getJSONObject(i);
                String retId = result.getString("id");
                PulpitInfo pulpitInfo = infoDao.findOne(retId);
                if (pulpitInfo == null) {
                    pulpitInfo = PulpitInfo.createPulpitInfo(result);
                    infoDao.save(pulpitInfo);
                } else {
                    pulpitInfo.updatePulpitInfo(result);
                    infoDao.save(pulpitInfo);
                }
                DateTime startDate = new DateTime(sdf.parse(result.getString("planStartTime")));
                DateTime endDate = new DateTime(sdf.parse(result.getString("planEndTime")));
                //提前一小时
                if (startDate.minusHours(1).getMillis() > System.currentTimeMillis()) {
                    continue;
                }
                //延后一小时
                if (endDate.plusHours(1).getMillis() < System.currentTimeMillis()) {
                    continue;
                }

                nowRunPulpit.add(retId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nowRunPulpit.size() == 0)
            log.error("no pulpitRecord at this time");
        synchronized (runPulpits) {
            runPulpits.clear();
            runPulpits.addAll(nowRunPulpit);
        }
    }

    public void updatePulpitCount() {
        if (!isOpen) {
            return;
        }
        if (runPulpits.size() == 0) {
            return;
        }
        synchronized (runPulpits) {
            for(String id : runPulpits) {
                try {
                    log.info("updatePulpitCount:{}",id);
                    List<String> oldOnlineIds = restService.getOnlineIds(id);
                    List<String> newOnlineIds = imService.getOnlineIds(id);
                    PulpitRecord record = recordDao.findOne(id);
                    if (record == null) {
                        recordDao.save(PulpitRecord.createPulpitRecord(id, oldOnlineIds, newOnlineIds));
                    } else {
                        recordDao.save(record.addRecord(oldOnlineIds, newOnlineIds));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public PageDataModel<PulpitInfo> getPulpitInfoByTime(Date startDate, Date endDate, int pageIndex, int pageSize) {
        Criteria criteria = Criteria.where("planStartTime").gte(startDate).lte(endDate);
        return PageDataService.getPageData(criteria, pageIndex, pageSize, PulpitInfo.class);
    }

    public int getMaxCountById(String id) {
        PulpitRecord record = recordDao.findOne(id);
        if (record == null) {
            return 0;
        }
        Map<Long, Integer> oldClientOnlineCountMap = record.getOldClientOnlineCountMap();
        for(Map.Entry<Long, Integer> entry : record.getNewClientOnlineCountMap().entrySet()) {
            Long key = entry.getKey();
            Integer newVal = entry.getValue() + oldClientOnlineCountMap.get(key);
            oldClientOnlineCountMap.put(key, newVal);
        }
        int maxCount = 0;
        for (int count : record.getOldClientOnlineCountMap().values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    public int getAttendanceMaxCountById(String id) {
        PulpitRecord record = recordDao.findOne(id);
        if (record == null) {
            return 0;
        }
        return record.getAttendanceUsers().size();
    }

    public PulpitRecord getPulpitRecord(String id) {
        return recordDao.findOne(id);
    }

    /**
     * 获取最高在线人数dto
     * @param pulpitId 讲坛id
     * @return
     */
    public PulpitRecordDto getPulpitMaxOnlineCountDto(String pulpitId) {
        PulpitRecordDto dto = new PulpitRecordDto();
        PulpitRecord record = getPulpitRecord(pulpitId);
        if (record == null) {
            return dto;
        }
        //老客户端
        setDtoMaxOnline(record.getOldClientOnlineCountMap(), dto, true);
        //新客户端
        setDtoMaxOnline(record.getNewClientOnlineCountMap(), dto, false);
        return dto;
    }

    private void setDtoMaxOnline(Map<Long, Integer> clientOnlineCountMap, PulpitRecordDto dto, boolean oldFlag) {
        List<Map.Entry<Long, Integer>> entries = new ArrayList<>(clientOnlineCountMap.entrySet());
        Collections.sort(entries, (o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));
        for (Map.Entry<Long, Integer> ret : entries) {
            if(oldFlag) {
                dto.getTimes().add(sdf.format(new Date(ret.getKey())));
                dto.getOldClientCounts().add(ret.getValue());
            }
            else
                dto.getNewClientCounts().add(ret.getValue());
        }
    }

    /**
     * 获取对应讲坛到课人数纪录
     * @param pulpitId 讲坛id
     * @return
     */
    public PulpitRecordDto getPulpitAttendanceDto(String pulpitId) {
        PulpitRecord record = getPulpitRecord(pulpitId);
        PulpitRecordDto dto = new PulpitRecordDto();
        if (record == null) {
            return dto;
        }
        //老客户端
        setDtoPulpitAttendance(record.getOldClientNewEnterUserIdsMap(), dto, true);
        //新客户端
        setDtoPulpitAttendance(record.getNewClientNewEnterUserIdsMap(), dto, false);
        return dto;
    }

    private void setDtoPulpitAttendance(Map<Long, List<String>> clientNewEnterUserIdsMap,
                                        PulpitRecordDto dto, boolean oldFlag) {
        List<Map.Entry<Long, List<String>>> entries =
                new ArrayList<>(clientNewEnterUserIdsMap.entrySet());
        Collections.sort(entries, (o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));
        int count = 0;
        for (Map.Entry<Long, List<String>> ret : entries) {
            count += ret.getValue().size();
            if(oldFlag) {
                dto.getTimes().add(sdf.format(new Date(ret.getKey())));
                dto.getOldClientCounts().add(count);
            }
            else
                dto.getNewClientCounts().add(count);
        }
    }

}
