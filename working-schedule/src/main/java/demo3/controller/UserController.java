package demo3.controller;
import demo3.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("api/test")
public class UserController {

        private final WebClient.Builder webClientBuilder;

        @Autowired
        public UserController(WebClient.Builder webClientBuilder) {
            this.webClientBuilder = webClientBuilder;
        }


        @GetMapping("getuser")
        public ResponseEntity<UserDTO> getUser(@RequestHeader (name="Authorization") String token){
            UserDTO user =  webClientBuilder.build().get()
                    .uri("http://localhost:8091/api/v1/secured/authentication")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();

            return ResponseEntity.ok(user);
        }

//    WebClient webClient = WebClient.builder()
//            .baseUrl("http://localhost:8091")
//            .build();
//
//    public UserDTO getUserfrommAuthApi(String token) {
//        return webClient.get()
//                .uri("api/v1/secured/authentication")
//                .header("Authorization", token)
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .block();
//    }



    }

