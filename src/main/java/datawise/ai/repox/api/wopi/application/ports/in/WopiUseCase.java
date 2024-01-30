package datawise.ai.repox.api.wopi.application.ports.in;

import datawise.ai.repox.api.wopi.dto.CheckFileInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface WopiUseCase {
    void getFile(String id, HttpServletResponse response);

    ResponseEntity postFile(String id, byte[] content, HttpServletRequest request);

    CheckFileInfo getFileInfo(String id);
}
