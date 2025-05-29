package dto;

import jakarta.validation.constraints.Size;

/**
 * Used for full updates (POST)
 */
public class UpdatePostDto {
    @Size(max = 255)
    public String title;
    public String content;
}
