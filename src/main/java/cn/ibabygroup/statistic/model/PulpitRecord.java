package cn.ibabygroup.statistic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Crowhyc on 2016/7/22.
 */
@Data
@AllArgsConstructor
@Document
public class PulpitRecord {
    @Id
    private String id;
    private Map<Long, Integer> oldClientOnlineCountMap;//时间戳,老客户端在该时间戳对应的在线人数
    private Map<Long, Integer> newClientOnlineCountMap;//时间戳,新客户端在该时间戳对应的在线人数
    private Map<Long, List<String>> oldClientNewEnterUserIdsMap;//时间戳，老客户端在该时间戳对应的新进入讲坛的在线用户ID集合
    private Map<Long, List<String>> newClientNewEnterUserIdsMap;//时间戳，新客户端在该时间戳对应的新进入讲坛的在线用户ID集合
    private Set<String> attendanceUsers;//到课用户Id

    private PulpitRecord() {
    }

    public static PulpitRecord createPulpitRecord(String id, List<String> oldClientUserIds, List<String> newClientUserIds) {
        PulpitRecord record = new PulpitRecord();
        record.setId(id);

        Long time = new Date().getTime();

        Map<Long, Integer> oldClientOnlineCountMap = new HashMap<>();
        oldClientOnlineCountMap.put(time, oldClientUserIds.size());
        record.setOldClientOnlineCountMap(oldClientOnlineCountMap);
        Map<Long, Integer> newClientOnlineCountMap = new HashMap<>();
        newClientOnlineCountMap.put(time, newClientUserIds.size());
        record.setNewClientOnlineCountMap(newClientOnlineCountMap);

        Map<Long, List<String>> oldClientNewEnterUserIdsMap = new HashMap<>();
        oldClientNewEnterUserIdsMap.put(time, oldClientUserIds);
        record.setOldClientNewEnterUserIdsMap(oldClientNewEnterUserIdsMap);
        Map<Long, List<String>> newClientNewEnterUserIdsMap = new HashMap<>();
        newClientNewEnterUserIdsMap.put(time, newClientUserIds);
        record.setNewClientNewEnterUserIdsMap(newClientNewEnterUserIdsMap);

        Set<String> attendanceUsers = new HashSet<>(oldClientUserIds);
        attendanceUsers.addAll(newClientUserIds);
        record.setAttendanceUsers(attendanceUsers);

        return record;
    }

    public PulpitRecord addRecord(List<String> oldClientUserIds, List<String> newClientUserIds) {
        Long time = new Date().getTime();
        oldClientOnlineCountMap.put(time, oldClientUserIds.size());
        newClientOnlineCountMap.put(time, newClientUserIds.size());
//        for(String user : userIds)
//            onlineUserCount.add(user);
        List<String> distinctOldUser = oldClientUserIds.stream().filter(user -> attendanceUsers.add(user)).collect(Collectors.toList());
        oldClientNewEnterUserIdsMap.put(time, distinctOldUser);
        List<String> distinctNewUser = newClientUserIds.stream().filter(user -> attendanceUsers.add(user)).collect(Collectors.toList());
        newClientNewEnterUserIdsMap.put(time, distinctNewUser);
        return this;
    }

}
