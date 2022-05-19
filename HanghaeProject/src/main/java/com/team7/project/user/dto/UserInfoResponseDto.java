package com.team7.project.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private UserBody user;
    private String token;
    private LocalDateTime tokenExpires;

    @Builder
    public static class UserBody{
        @JsonProperty
        private Long id;
        @JsonProperty
        private String nickname;
        @JsonProperty
        private String githubLink;
        @JsonProperty
        private String profileImageUrl;
        @JsonProperty
        private String introduce;
    }

    @Builder
    public UserInfoResponseDto(UserBody user, String token, LocalDateTime tokenExpires){
        this.user = user ;
        this.token = token;
        this.tokenExpires = null;
    }

}
