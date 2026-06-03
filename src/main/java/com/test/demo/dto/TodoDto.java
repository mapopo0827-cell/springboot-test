package com.test.demo.dto;

/**
 * APIでやり取りするTODOのDTOです。
 * Entityと分離することで、APIの変更が内部実装に影響しにくくなります。
 */
public class TodoDto {
    private Long id;
    private String title;
    private boolean completed;

    public TodoDto() {
    }

    public TodoDto(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
