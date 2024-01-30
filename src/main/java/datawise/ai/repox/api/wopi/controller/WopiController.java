package datawise.ai.repox.api.wopi.controller;

import datawise.ai.repox.api.wopi.application.ports.in.WopiUseCase;
import datawise.ai.repox.api.wopi.mapper.CheckFileInfoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/wopi")
public class WopiController {

    private WopiUseCase wopiUseCase;
    private CheckFileInfoMapper checkFileInfoMapper;

    @Autowired
    public WopiController(@Qualifier("wopiService") WopiUseCase wopiUseCase,
                          CheckFileInfoMapper checkFileInfoMapper) {
        this.wopiUseCase = wopiUseCase;
        this.checkFileInfoMapper = checkFileInfoMapper;
    }

    @GetMapping("/files/{id}/contents")
    public void getFile(@PathVariable String id,
                        @RequestParam (required = false) String access_token,
                        @RequestParam (required = false) String access_token_ttl,
                        HttpServletResponse response) {
        wopiUseCase.getFile(id, response);
    }

    @PostMapping("/files/{id}/contents")
    public void putFile(@PathVariable String id,
                        @RequestParam (required = false) String access_token,
                        @RequestParam (required = false) String access_token_ttl,
                        @RequestBody byte[] content, HttpServletRequest request) {
        wopiUseCase.postFile(id, content, request);
    }

    @PostMapping("/files/{new_id}/contents")
    public void putRelativeFile(@PathVariable String new_id,
                                @RequestParam (required = false) String access_token,
                                @RequestParam (required = false) String access_token_ttl,
                                @RequestBody byte[] content, HttpServletRequest request){
        wopiUseCase.postFile(new_id, content, request);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Map> checkFileInfo(
            @PathVariable String id,
            @RequestParam (required = false) String access_token,
            @RequestParam (required = false) String access_token_ttl) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(checkFileInfoMapper.mapCheckFileInfoProperties(wopiUseCase.getFileInfo(id)));
    }


}
