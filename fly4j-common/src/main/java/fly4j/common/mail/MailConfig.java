package fly4j.common.mail;

/**
 * Created by qryc on 2020/2/2.
 */
public class MailConfig {
    private String smtp;
    private String from;
    private String copyto;
    private String username;
    private String password;


    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCopyto() {
        return copyto;
    }

    public void setCopyto(String copyto) {
        this.copyto = copyto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
