package ru.netology.netologydiplombackendfvd.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.netologydiplombackendfvd.model.FileStorage;
import ru.netology.netologydiplombackendfvd.model.User;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<FileStorage, Long> {
    List<FileStorage> findAllByUser_Login(String login, Sort sort);

    void removeFileStorageByFilename(String fileName);

    FileStorage findByFilenameAndUser(String filename, User user);

}
