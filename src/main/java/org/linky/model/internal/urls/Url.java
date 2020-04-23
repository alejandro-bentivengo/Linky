package org.linky.model.internal.urls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.linky.model.internal.acl.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "url")
public class Url implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "url_pk")
    @JsonIgnore
    private Long urlPk;
    private String code;
    @ManyToOne
    @JoinColumn(name = "user_pk")
    @JsonIgnore
    private User user;
    private String url;
    private Boolean secure;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(urlPk, url.urlPk) &&
                Objects.equals(code, url.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPk, code);
    }

    @JsonIgnore
    public String getStandardUrl() {
        String baseUrl = url.replaceAll("http.*//", "");
        return secure ? "https://".concat(baseUrl) : "http://".concat(baseUrl);
    }

}
