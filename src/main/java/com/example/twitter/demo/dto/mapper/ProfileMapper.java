package com.example.twitter.demo.dto.mapper;

import com.example.twitter.demo.dto.profileDto.ProfileDto;
import com.example.twitter.demo.entity.Register;

import java.util.stream.Collectors;

public class ProfileMapper {

    public static ProfileDto toDto(Register register) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(register.getId());
        profileDto.setFullName(register.getFirstName() + " " + register.getLastName());
        profileDto.setUsername(register.getUsername());
        profileDto.setSummary(register.getSummary());
        profileDto.setCreatedAt(register.getCreatedAt());
        profileDto.setUpdatedAt(register.getUpdatedAt());
        profileDto.setFollows(register.getFollows().stream().map(follow -> follow.getFollower().getId()).collect(Collectors.toList()));
        profileDto.setFollowers(register.getFollowers().stream().map(follow -> follow.getFollowed().getId()).collect(Collectors.toList()));
        return profileDto;
    }
}
