package com.learn.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstanceInfo {
    private String serviceId;
    private String instanceId;
    private String host;
    private int port;
    private String uri;
}