package com.learn.roomservice.controller;

import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
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
                registration.getUri().toString()
        );
    }

    @Data
    @AllArgsConstructor
    static class InstanceInfo {
        private String serviceId;
        private String instanceId;
        private String host;
        private int port;
        private String uri;
    }
}
