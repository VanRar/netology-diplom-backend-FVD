package ru.netology.netologydiplombackendfvd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiplombackendfvd.exceptions.ResponseError;
import ru.netology.netologydiplombackendfvd.exceptions.StorageException;
import ru.netology.netologydiplombackendfvd.model.FileStorage;
import ru.netology.netologydiplombackendfvd.service.FileService;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Validated
@RestController
public class StorageController {
    private final FileService fileService;

    @Autowired
    public StorageController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public ResponseEntity<?> postFile(@RequestParam("filename") String filename, @RequestPart("file") @NotNull MultipartFile file) {
        fileService.postFile(filename, file);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String filename) {
        fileService.deleteFile(filename);
        return ResponseEntity.ok().body(null);
    }


    @GetMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getFile(@RequestParam("filename") String filename) throws IOException {
        Resource resource = fileService.getFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentLength(resource.getFile().length())
                .body(resource);
    }

    @RequestMapping(value = "/file", method = RequestMethod.PUT)
    public ResponseEntity<?> putFile(@RequestParam("filename") String filename,
                                     @RequestBody FileStorage newFilename) {
        fileService.putFile(filename, newFilename.getFilename());
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok().body(fileService.getList());
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ResponseError> handleStorageExceptions(StorageException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(e.getMessage(), 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleInternalServerErrors(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError(e.getMessage(), 500));
    }
}
