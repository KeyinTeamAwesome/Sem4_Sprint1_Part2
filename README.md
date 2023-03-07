<h1>
  Semester 4 | Java | Sprint 1

  *Server (Spring Boot), HTTP Client, CLI*
</h1>


> __Note__ 
>
> This is a group project for our 4nd Semester at Keyin College's Software Development Program.
>
>⠀⠀❗ ⠀➝⠀This repository contains **Part 2** of the assignment.⠀⠀⠀ *(Spring Boot Server)*
>
>⠀⠀☞ ➝⠀**Part 1** can be found [**here**](https://github.com/KeyinTeamAwesome/Sem4_Sprint1_Part1).⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ *(HTTP Client/CLI/Testing)*


## Part 2: HTTP Client, Command Line Interface, Testing

### **Overview**
This repository contains a **Java (Maven)** project consisting of a command line interface and HTTP client which connects via http to the server, making use of the API in [**Part 1**](https://github.com/KeyinTeamAwesome/Sem4_Sprint1_Part1).

---

### **Entities**

The HTTP client has access to endpoints associated with these entites:

| Entity       | Fields                                      | 
| :----------: | :------------------------------------------ | 
|    Cities    | id, cityName, cityState, cityPopulation     |
|  Passengers  | id, firstName, lastName, phoneNumber        |
|   Airports   | id, name, code                              |
|   Aircraft   | id, type, airlineName, numberOfPassengers   |

---

### **Endpoints**

The HTTP client has access to these endpoints via the command line interface:

#### **Questions**

|   Method    | URL                                 | Question                                               |
| :---------: | :---------------------------------- | :----------------------------------------------------- |
|     GET     | localhost:8080/cities_airports      | What airports are in what cities?                      |
|     GET     | localhost:8080/aircraft_passengers  | List all aircraft passengers have travelled on?        |
|     GET     | localhost:8080/aircraft_airports    | Which airports can aircraft take off from and land at? |
|     GET     | localhost:8080/airports_passengers  | What airports have passengers used?                    |

#### **Passengers**

|   Method    | URI                           |
| :---------: | :---------------------------- |
|  GET (All)  | localhost:8080/passengers     |
| GET (By ID) | localhost:8080/passenger/{id} |

#### **Cites**

|              Method                | URI                          |
| :--------------------------------: | :--------------------------- |
|             GET (All)              | localhost:8080/cities        |
|            GET (By ID)             | localhost:8080/city/{id}     |

#### **Airports**

|              Method                | URI                          |
| :--------------------------------: | :--------------------------- |
|             GET (All)              | localhost:8080/airports      |
|            GET (By ID)             | localhost:8080/airport/{id}  |

#### **Aircraft**

|              Method                | URI                          |
| :--------------------------------: | :--------------------------- |
|             GET (All)              | localhost:8080/aircraft      |
|            GET (By ID)             | localhost:8080/aircraft/{id} |

---

### Contributers

<table>
  <tr>
    <th>Author</th>
    <th>GitHub</th>
  </tr>
  <tr>
    <td>Makenzie Roberts</td>
    <td>
      <a href="https://github.com/MakenzieRoberts"><img height="50px" src="https://avatars.githubusercontent.com/u/100213075?v=4"></a>
    </td>
  </tr> 
  <tr>
    <td>Kara Balsom</td>
    <td>
      <a href="https://github.com/kbalsom"><img height="50px" src="https://avatars.githubusercontent.com/u/100210446?v=4"></a>
    </td>
  </tr>
  <tr>
    <td>David Turner</td>
    <td>
      <a href="https://github.com/DeToxFox"><img height="50px" src="https://avatars.githubusercontent.com/u/95373983?v=4"></a>
    </td>
  </tr>
      <td>Glen May</td>
    <td>
      <a href="https://github.com/ellis0n"><img height="50px" src="https://avatars.githubusercontent.com/u/100211236?v=4"></a>
    </td>
  </tr>
    </tr>
      <td>Terrance Power</td>
    <td>
      <a href="https://github.com/Tpower16"><img height="50px" src="https://avatars.githubusercontent.com/u/100700181?v=4"></a>
    </td>
  </tr>
</table>
