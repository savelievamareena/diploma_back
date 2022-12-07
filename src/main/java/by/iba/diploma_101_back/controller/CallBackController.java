package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.CallBackForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.CallRequest;
import by.iba.diploma_101_back.repository.CallBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CallBackController {
    private final CallBackRepository callBackRepository;

    @Autowired
    public CallBackController(CallBackRepository callBackRepository) {
        this.callBackRepository = callBackRepository;
    }

    @GetMapping("/callback")
    public List<CallRequest> getAllUsers() {
        return callBackRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    @PostMapping("/callback")
    public ResponseEntity<?> updateUser(@RequestBody CallBackForm callBackForm, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();

        CallRequest callRequest = new CallRequest();
        callRequest.setName(callBackForm.getFirstName() + " " + callBackForm.getLastName());
        callRequest.setPhoneNumber(callBackForm.getPhoneNumber());
        callRequest.setQuestion(callBackForm.getQuestion());
        callRequest.setCalled(false);

        try{
            callBackRepository.save(callRequest);
        }catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/callback/{id}")
    public ResponseEntity<?> deleteCallBackRequest(@PathVariable(value = "id") int id) {
        ApiResponse apiResponse = new ApiResponse();

        CallRequest callBackRequest = callBackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CallRequest", "id", id));

        try{
            callBackRepository.delete(callBackRequest);
        }catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
