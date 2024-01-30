package datawise.ai.repox.api.wopi.service;

import datawise.ai.repox.api.mockservices.application.ports.in.FileStorageUseCase;
import datawise.ai.repox.api.wopi.config.WopiSettings;
import datawise.ai.repox.api.wopi.dto.CheckFileInfo;
import datawise.ai.repox.api.wopi.application.ports.in.WopiUseCase;
import datawise.ai.repox.api.wopi.dto.WopiStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
@Slf4j
class WopiService implements WopiUseCase {
    private FileStorageUseCase fileStorageUseCase;
    private WopiSettings wopiSettings;

    @Autowired
    public WopiService(FileStorageUseCase fileStorageUseCase,
                       WopiSettings wopiSettings) {
        this.fileStorageUseCase = fileStorageUseCase;
        this.wopiSettings = wopiSettings;
    }

    @Override
    public void getFile(String id, HttpServletResponse response) {
        log.info("Requested file " + id);
        Optional<String> filenameOpt = fileStorageUseCase.getBaseFileNameWithId(id);
        if (filenameOpt.isEmpty()) {
            response.setStatus(WopiStatus.NOT_FOUND.getCode());
            return;
        }

        String filename = filenameOpt.get();
        log.info("Resolved id to file " + filename);

        try {
            File file = fileStorageUseCase.getFileWithId(id);
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            response.reset();
            response.addHeader("Content-Disposition","attachment;filename=" +
                    new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", String.valueOf(file.length()));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
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
        throw new RuntimeException("POST FILE not implemented yet.");
    }

    @Override
    public CheckFileInfo getFileInfo(String id) {
        log.info("Requested file info " + id);
        Optional<String> filenameOpt = fileStorageUseCase.getBaseFileNameWithId(id);
        String filename = filenameOpt.get();
        Boolean edit = wopiSettings.getEnableEdit();
        log.info("Resolved id to file " + filename);
        long size;
        try {
            size = fileStorageUseCase.getSizeOfFileWithId(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CheckFileInfo.builder()
                        .baseFileName(filename)
                        .ownerId("fyts")
                        .size(size)
                        .userId("fyts")
                        .userCanEdit(edit)
                        .build();
    }
}
