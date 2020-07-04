package fly4j.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FlyResult {
    private boolean success;
    private String msg;

    public FlyResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public FlyResult fail() {
        this.success = false;
        return this;
    }

    public FlyResult success() {
        this.success = true;
        return this;
    }

    public FlyResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
