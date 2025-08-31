
package com.mindnova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CertificateDto {
    private String title;
    private String fileUrl;
    private LocalDate issuedDate;
    private LocalDate expiredAt;
}
