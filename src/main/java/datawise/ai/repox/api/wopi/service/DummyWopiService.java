package datawise.ai.repox.api.wopi.service;

import datawise.ai.repox.api.wopi.application.ports.in.WopiUseCase;
import datawise.ai.repox.api.wopi.dto.CheckFileInfo;
import datawise.ai.repox.api.wopi.dto.WopiStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
class DummyWopiService implements WopiUseCase {
    private final String text = "Hello World";
    @Override
    public void getFile(String id, HttpServletResponse response) {
        log.info("Requested file " + id);
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
            response.reset();
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + id);
            response.addHeader("Content-Length", String.valueOf(text.length()));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            byte[] buffer = text.getBytes();
            toClient.write(buffer);
            toClient.flush();
        }
        catch (IOException e) {
            log.error("GetFile failure : " + e.getMessage());
            response.setStatus(WopiStatus.INTERNAL_SERVER_ERROR.getCode());
        }
    }

    @Override
    public ResponseEntity postFile(String id, byte[] content, HttpServletRequest request) {
        return null;
    }

    @Override
    public CheckFileInfo getFileInfo(String id) {
        return CheckFileInfo.builder()
                    .baseFileName("dummy.txt")
                    .ownerId("fyts")
                    .size(11L)
                    .userId("fyts")
                    .build();
    }
}
