package com.learn.roomservice.controller;

import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.roomservice.model.InstanceInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomInstanceInfoController {

    private final Registration registration;

    @GetMapping("/api/rooms/instance-info")
    public InstanceInfo getInstanceInfo() {
        return new InstanceInfo(
                registration.getServiceId(),
                registration.getInstanceId(),
                registration.getHost(),
                registration.getPort(),
                registration.getUri().toString());
    }

}