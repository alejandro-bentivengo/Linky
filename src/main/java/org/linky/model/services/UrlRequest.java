package org.linky.model.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Using this object as DTO for external API
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlRequest {
    private String url;
    private Boolean secure;
}
