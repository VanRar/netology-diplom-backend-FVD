package ru.netology.netologydiplombackendfvd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiplombackendfvd.model.FileStorage;
import ru.netology.netologydiplombackendfvd.model.User;
import ru.netology.netologydiplombackendfvd.properties.StorageProperties;
import ru.netology.netologydiplombackendfvd.repository.StorageRepository;
import ru.netology.netologydiplombackendfvd.repository.UserRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private final StorageService storageService;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;
    private final Path rootLocation;

    @Autowired
    public FileService(StorageService storageService, UserRepository userRepository,
                       StorageRepository storageRepository, StorageProperties storageProperties) {
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.storageRepository = storageRepository;
        this.rootLocation = Paths.get(storageProperties.getRootLocation());
    }

    @Transactional
    public void postFile(String filename, MultipartFile file){
        FileStorage fileStorage = getFileStorage(filename, file);
        storageRepository.saveAndFlush(fileStorage);
        storageService.store(file, filename, getFilePath(filename));
    }
    public void deleteFile(String filename) {
        storageRepository.removeFileStorageByFilename(filename);
        storageService.deleteFile(getFilePath(filename));
    }

    public Resource getFile(String filename) {
        return storageService.loadAsResource(filename, getFilePath(filename));
    }

    @Transactional
    public List<FileStorage> getList() {
        return storageRepository.findAllByUser_Login(getUser().getLogin(), Sort.by("filename"));
    }

    @Transactional
    public void putFile(String filename, String newFilename) {
        FileStorage fileStorage = storageRepository.findByFilenameAndUser(filename, getUser());

        fileStorage.setFilename(newFilename);

        storageRepository.saveAndFlush(fileStorage);

        storageService.renameFile(getFilePath(filename), newFilename);
    }

    private FileStorage getFileStorage(String filename, MultipartFile file) {
        return new FileStorage(filename, getFilePath(filename).toAbsolutePath().toString(),
                file.getSize(), getUser());
    }

    private User getUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }

    private Path getFilePath(String filename) {
        return rootLocation.resolve(getUser().getLogin()).resolve(filename);
    }

    private Path getUserPath() {
        return rootLocation.resolve(getUser().getLogin());
    }



}
