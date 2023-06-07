package mobi.foo.training;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class MyException {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<FooResponse> handleNullValue(MethodArgumentNotValidException ex)
    {
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        FooResponse response = FooResponse.builder().data(errorMap).message("Handled by Exception").status(false).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
