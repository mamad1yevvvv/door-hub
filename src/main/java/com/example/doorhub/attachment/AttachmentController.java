package com.example.doorhub.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.doorhub.attachment.dto.AttachmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    @PostMapping(value = "/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> uploadFileUser(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId) {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
                AttachmentResponseDto attachmentResponseDto = service.processImageUploadUser(file, userId);
                yield ResponseEntity.ok(attachmentResponseDto);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @PutMapping(value = "/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> updateFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId) {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {

                AttachmentResponseDto attachmentResponseDto = service.processImageUpdateUser(file, userId);
                yield ResponseEntity.ok(attachmentResponseDto);

            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        service.deleteAttachmentUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/upload/category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> uploadFileCategory(
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") Integer categoryId) {

        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
                AttachmentResponseDto attachmentResponseDto = service.processImageUploadCategory(file, categoryId);
                yield ResponseEntity.ok(attachmentResponseDto);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @PutMapping(value = "/upload/category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> updateFileCategory(@RequestParam("file") MultipartFile file, @RequestParam("categoryId") Integer categoryId) {

        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {

                AttachmentResponseDto attachmentResponseDto = service.processImageUpdateCategory(file, categoryId);
                yield ResponseEntity.ok(attachmentResponseDto);

            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        service.deleteAttachmentCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
