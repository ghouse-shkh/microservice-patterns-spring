# Room Service – Eureka Client Setup

## 1. Error and Latency Simulation
### RoomService
```java
public void simulateDelay(Long delayMs) {
        if (delayMs != null && delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
```

### RoomController
 ```java
 @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        // Simulation of error for demonstrating fault tolerance
        if (id == 9999) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

        ...
    }

@GetMapping("/search")
    public ResponseEntity<List<Room>> search(@RequestParam String type,
            @RequestParam(required = false) Long delayMs,
            @RequestParam(required = false) Integer status) {

        // Simulation of latency,error for demonstrating fault tolerance
        if (delayMs != null) {
            log.info("Simulating latency of {} ms", delayMs);
            roomService.simulateDelay(delayMs);
        }
        if (status != null && status >= 400) {
            return ResponseEntity.status(status).build();
        }

        return ResponseEntity.ok(roomService.searchByType(type));
    }
  ```