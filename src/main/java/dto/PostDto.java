package dto;

import java.time.LocalDateTime;

/**
 * For API responses
 */
public class PostDto {
    public Long id;
    public String title;
    public String content;
    public String authorEmail;
    public LocalDateTime creationDate;
    public LocalDateTime lastModifiedDate;
}
