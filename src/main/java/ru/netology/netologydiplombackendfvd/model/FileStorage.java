package ru.netology.netologydiplombackendfvd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_storage")
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore //для игнорирования свойства
    private Long id;

    @JsonProperty("name")
    private String filename;
    @JsonIgnore
    private String filepath;
    @JsonProperty("size")
    private Long size;

    @JsonIgnore
    @OneToOne
    private User user;

    public FileStorage(String filename, String filepath, Long size, User user) {
        this.filename = filename;
        this.filepath = filepath;
        this.size = size;
        this.user = user;
    }
}