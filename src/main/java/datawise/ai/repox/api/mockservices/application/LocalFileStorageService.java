package datawise.ai.repox.api.mockservices.application;

import datawise.ai.repox.api.mockservices.application.ports.in.FileStorageUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
class LocalFileStorageService implements FileStorageUseCase {
    @Value("classpath:file_storage/*")
    private Resource[] files;

    private Map<UUID, Resource> filesMap = new HashMap<>();

    @Override
    public List<String> listFilesInDirectory(String directory) {
        List<Resource> filesList = Arrays.asList(files);
        return filesList.stream().map(f -> f.getFilename()).collect(Collectors.toList());
    }

    @Override
    public String getFileId(String filename) {
        Optional<UUID> uuid = filesMap.keySet().stream()
                .filter(key -> filesMap.get(key).getFilename().compareToIgnoreCase(filename) == 0)
                .findFirst();
        if (uuid.isPresent()) return uuid.get().toString();
        else {
            UUID new_uuid = UUID.randomUUID();
            Resource file = Arrays.asList(files).stream()
                            .filter(f -> f.getFilename().compareToIgnoreCase(filename)==0)
                            .findFirst().orElseThrow();
            filesMap.put(new_uuid, file);
            return new_uuid.toString();
        }
    }

    @Override
    public Optional<String> getBaseFileNameWithId(String id) {
        UUID uuid = findFileWithId(id);
        return Optional.of(filesMap.get(uuid).getFilename());
    }

    @Override
    public Long getSizeOfFileWithId(String id) throws IOException {
        UUID uuid = findFileWithId(id);
        File f = filesMap.get(uuid).getFile();
        return f.length();
    }

    @Override
    public File getFileWithId(String id) throws IOException {
        UUID uuid = findFileWithId(id);
        return filesMap.get(uuid).getFile();
    }

    @Override
    public boolean fileExists(String filename) {
        return Arrays.asList(files).stream()
                .anyMatch(f -> f.getFilename().compareToIgnoreCase(filename)==0);
    }

    private UUID findFileWithId(String id) {
        UUID uuid = UUID.fromString(id);
        if (!filesMap.containsKey(uuid)) throw new RuntimeException("Could not find file with id " + id);
        return uuid;
    }
}
