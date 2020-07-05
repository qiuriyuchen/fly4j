package fly4j.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FlyResult {
    private boolean success;
    private StringBuilder msg = new StringBuilder();

    public FlyResult(boolean success, String msg) {
        this.success = success;
        this.msg.append(msg);
    }

    public FlyResult fail() {
        this.success = false;
        return this;
    }

    public FlyResult success() {
        this.success = true;
        return this;
    }

    public FlyResult append(String msg) {
        this.msg.append(msg);
        return this;
    }

    public void merge(FlyResult flyResult) {
        if (flyResult.isFail()) {
            this.fail();
        }
        this.msg.append(flyResult.getMsg());
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFail() {
        return !success;
    }

    public String getMsg() {
        return msg.toString();
    }
}
