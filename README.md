# Spring Webflux - Demo
## Running
### Dependencies
```docker run -d -p 27017:27017 --name webflux_demo_mongo mongo:4.0.9 --smallfiles```

### Application
```mvn spring-boot:run```

## Request examples
All examples use [HTTPie](https://httpie.org/)

### Insert a CIO
```http POST :8080/employees id=1 name=John salary=10000```

### Insert a employees
```http POST :8080/employees id=2 name=Alex salary=1000 bossId=1```

```http POST :8080/employees id=3 name=Mary salary=2000 bossId=1```

```http POST :8080/employees id=4 name=Juliet salary=2500 bossId=1```

```http POST :8080/employees id=5 name=Mark salary=500 bossId=4```

```http POST :8080/employees id=6 name=Logan salary=600 bossId=4```

### List all employees
```http :8080/employees```

### List direct employees of a boss
```http :8080/employees/1/directs```

### Count direct employees of a boss
```http :8080/employees/1/directs/count```

### List all employees of a boss
```http :8080/employees/1/all```

### Count all employees of a boss
```http :8080/employees/1/all/count```

### Total cost of employees by boss
```http :8080/employees/1/all/cost```

### Statistics
```http :8080/employees/statistics```

### Export
```http POST :8080/employees/export```
