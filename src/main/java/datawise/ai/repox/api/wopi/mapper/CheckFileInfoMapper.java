package datawise.ai.repox.api.wopi.mapper;

import datawise.ai.repox.api.wopi.dto.CheckFileInfo;
import datawise.ai.repox.api.wopi.dto.WopiFileInfoProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CheckFileInfoMapper {
    public Map mapCheckFileInfoProperties(CheckFileInfo checkFileInfo) {
        Map<String, String> map = new HashMap();
        map.put(WopiFileInfoProperty.BASE_FILE_NAME.getName(), checkFileInfo.getBaseFileName());
        map.put(WopiFileInfoProperty.OWNER_ID.getName(), checkFileInfo.getOwnerId());
        map.put(WopiFileInfoProperty.SIZE.getName(), String.valueOf(checkFileInfo.getSize()));
        map.put(WopiFileInfoProperty.USER_ID.getName(), checkFileInfo.getUserId());
        // now put some additional to test
        map.put(WopiFileInfoProperty.USER_CAN_WRITE.getName(), checkFileInfo.getUserCanEdit().toString());
        return map;
    }
}
