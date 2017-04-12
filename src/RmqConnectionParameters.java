/**
 * Created by D.I. on 27.03.2017.
 */
public interface RmqConnectionParameters {
    public static final String USERNAME_PROPERTY                 = "username";
    public static final String PASSWORD_PROPERTY                 = "password";
    public static final String HOST_PROPERTY                     = "host";
    public static final String PORT_PROPERTY                     = "port";
    public static final String VHOST_PROPERTY                    = "vhost";
    public static final String CONNECTION_RETRY_TIMEOUT_PROPERTY = "connectionRetryTimeout";
    public static final String SSL_PROPERTY 					 = "ssl";
    public static final String SSL_TRUSTSTORE_PROPERTY           = "truststore";
    public static final String SSL_KEYSTORE_PROPERTY             = "keystore";
    public static final String SSL_TRUSTSTORE_PASSWORD_PROPERTY  = "truststore.password";
    public static final String SSL_KEYSTORE_PASSWORD_PROPERTY    = "ekeystore.password";

    public String getValue(String key, String defaultValue);

    public void setValue(String key, String value);
}
