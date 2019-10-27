package br.com.squadjoaquina.errorlogger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity @Setter @Getter @NoArgsConstructor
public class Error {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull @Size(min = 1)
    private String origin;

    @NotNull @Size(min = 1)
    private String title;

    private String description;

    @NotNull
    private Long userID;

    @NotNull
    private Level level;

    @NotNull
    private Environment environment;

    private boolean archived = false;

    private Timestamp archivedDate;

    @CreatedDate
    private Timestamp createAt;
}
