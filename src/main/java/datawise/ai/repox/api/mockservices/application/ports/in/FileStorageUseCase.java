package datawise.ai.repox.api.mockservices.application.ports.in;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileStorageUseCase {
    List<String> listFilesInDirectory(String directory);
    String getFileId(String filename);
    Optional<String> getBaseFileNameWithId(String id);
    Long getSizeOfFileWithId(String id) throws IOException;
    File getFileWithId(String id) throws IOException;
    boolean fileExists(String filename);
}
