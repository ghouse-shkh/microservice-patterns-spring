package com.learn.booking.dto;

public record InstanceInfo(
    String serviceId,
    String instanceId,
    String host,
    int port,
    String uri
) {}
