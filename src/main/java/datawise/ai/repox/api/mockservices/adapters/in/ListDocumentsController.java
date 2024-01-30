package datawise.ai.repox.api.mockservices.adapters.in;

import datawise.ai.repox.api.mockservices.application.ports.in.ListDocumentsUseCase;
import datawise.ai.repox.api.mockservices.domain.DocumentAcccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListDocumentsController {

    private ListDocumentsUseCase listDocumentsUseCase;

    @Autowired
    public ListDocumentsController(
            ListDocumentsUseCase listDocumentsUseCase) {
        this.listDocumentsUseCase = listDocumentsUseCase;
    }

    @GetMapping("/files/list")
    public List<DocumentAcccessInfo> listFiles() {
        return listDocumentsUseCase.listFiles("/file_storage");
    }

}
