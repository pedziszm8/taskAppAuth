package com.example.taskApp;

import jakarta.persistence.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "Desciption")
    String description;
    @Column(name = "status")
    boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   /* @Lob              //image//
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }*/

    /* @Lob             //json//
    @Column(name = "subtasks", columnDefinition = "TEXT")
    private String subTasksJson;

    @Transient
    private List<SubTask> subTasks;

    // Konwersja JSON <-> obiekt

    public List<SubTask> getSubTasks() {
        if (subTasks == null && subTasksJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                subTasks = mapper.readValue(subTasksJson, new TypeReference<List<SubTask>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.subTasksJson = mapper.writeValueAsString(subTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }           //json
     */
}
