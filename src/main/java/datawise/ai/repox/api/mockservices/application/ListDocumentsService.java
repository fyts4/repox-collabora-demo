package datawise.ai.repox.api.mockservices.application;

import datawise.ai.repox.api.mockservices.application.ports.in.FileStorageUseCase;
import datawise.ai.repox.api.mockservices.application.ports.in.ListDocumentsUseCase;
import datawise.ai.repox.api.mockservices.application.ports.in.TokenGeneratorUseCase;
import datawise.ai.repox.api.mockservices.domain.DocumentAcccessInfo;
import datawise.ai.repox.api.wopi.application.ports.in.WopiDiscoveryUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
class ListDocumentsService implements ListDocumentsUseCase {

    private FileStorageUseCase fileStorageUseCase;
    private WopiDiscoveryUseCase wopiDiscoveryUseCase;
    private TokenGeneratorUseCase tokenGeneratorUseCase;

    @Autowired
    public ListDocumentsService(
            FileStorageUseCase fileStorageUseCase,
            WopiDiscoveryUseCase wopiDiscoveryUseCase,
            TokenGeneratorUseCase tokenGeneratorUseCase) {
        this.fileStorageUseCase = fileStorageUseCase;
        this.wopiDiscoveryUseCase = wopiDiscoveryUseCase;
        this.tokenGeneratorUseCase = tokenGeneratorUseCase;
    }

    @Override
    public List<DocumentAcccessInfo> listFiles(String directory) {
        return fileStorageUseCase.listFilesInDirectory(directory).stream()
                .map(f -> fillDocumentAccessInfo(f))
                .collect(Collectors.toList());
    }

    private DocumentAcccessInfo fillDocumentAccessInfo(String filename) {
        String fileId = fileStorageUseCase.getFileId(filename);
        if (!fileId.isEmpty()) {
            Optional<String> wopiSrc = wopiDiscoveryUseCase.createWopiSrc(fileId, filename, Optional.of(tokenGeneratorUseCase.generateToken()));
            String url = wopiSrc
                    .orElse(fileStorageUseCase.getBaseFileNameWithId(fileId).get());
            String uuid = fileStorageUseCase.getFileId(filename);
            Long size = 0L;
            try {
                size = fileStorageUseCase.getSizeOfFileWithId(fileId);
            } catch (IOException e) {
                log.info("Could not get size of file " + filename + " with id = " + fileId);
            }
            return DocumentAcccessInfo.builder()
                    .uuid(uuid)
                    .filename(filename)
                    .url(url)
                    .size(size)
                    .build();
        }
        return DocumentAcccessInfo.builder()
                .filename(filename).build();
    }

}
