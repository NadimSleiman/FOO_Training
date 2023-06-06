package mobi.foo.training;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FooResponse {
    private boolean status;
    private String message;
    private Object data;

}
