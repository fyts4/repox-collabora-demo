package datawise.ai.repox.api.mockservices.application.ports.in;

import datawise.ai.repox.api.mockservices.domain.DocumentAcccessInfo;

import java.util.List;

public interface ListDocumentsUseCase {
    List<DocumentAcccessInfo> listFiles(String directory);
}
