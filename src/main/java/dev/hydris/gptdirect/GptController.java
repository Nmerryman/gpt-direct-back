package dev.hydris.gptdirect;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GptController {

    @PostMapping("/api/request")
    public GptResponse request(@RequestBody final GptRequest gptrequest) {
        return new GptResponse("Got it");
    }


}
