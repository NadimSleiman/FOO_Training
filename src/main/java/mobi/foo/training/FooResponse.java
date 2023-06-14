package mobi.foo.training;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FooResponse {
    private boolean status;
    private String message;
    private Object data;

    public FooResponse(){}

    public FooResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
